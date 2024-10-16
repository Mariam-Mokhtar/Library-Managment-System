package com.system.Library.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.Library.application.entity.Book;
import com.system.Library.application.filter.BookFilter;
import com.system.Library.application.mapper.BookMapper;
import com.system.Library.application.model.request.BookReqModel;
import com.system.Library.application.model.response.BookResModel;
import com.system.Library.application.repository.BookRepository;
import com.system.Library.application.repository.BorrowingRecordsRepository;
import com.system.Library.application.service.BookService;
import com.system.Library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookMapper bookMapper;

	@Autowired
	BorrowingRecordsRepository borrowingRecordsRepository;

	@Override
	@Cacheable(value = "getAllBooks", key = "#root.methodName")
	public List<BookResModel> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return bookMapper.mapFromBookToBookResModel(books);
	}

	@Override
	@Cacheable(value = "getAllFilteredBooks", key = "#root.methodName + #sortingField + #sortingOrder + #pageIndex + #pageSize")
	public List<BookResModel> getAllFilteredBooks(BookFilter bookFilter, String sortingOrder, String sortingField,
			int pageIndex, int pageSize) {
		Pageable pageableReq = null;
		if (sortingField != null && !sortingField.isBlank() && sortingOrder != null && !sortingOrder.isBlank())
			pageableReq = PageRequest.of(pageIndex, pageSize,
					sortingOrder.equalsIgnoreCase("asc") ? Sort.by(sortingField).ascending()
							: Sort.by(sortingField).descending());
		else
			pageableReq = PageRequest.of(pageIndex, pageSize);
		Page<Book> books = bookRepository.findAllBookWithFilter(bookFilter, pageableReq);
		if (books.hasContent())
			return bookMapper.mapFromBookToBookResModel(books);
		return new ArrayList<>();
	}

	@Override
	@Cacheable(value = "getBookById", key = "#id")
	public BookResModel getBookById(int id) {
		Optional<Book> book = bookRepository.findById(id);
		if (!book.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ID_NOT_FOUND.name());
		return bookMapper.mapFromBookToBookResModel(book.get());
	}

	@Override
	@CacheEvict(value = "getAllBooks", allEntries = true)
	@CachePut(value = "getBookById", key = "#result.id")
	public BookResModel createBook(BookReqModel bookReqModel) {
		validateUniqueness(bookReqModel, 0); // id is int in the book entity
		Book book = bookMapper.mapFromBookReqModelToBook(bookReqModel);
		bookRepository.save(book);
		return bookMapper.mapFromBookToBookResModel(book);
	}

	@Override
	@CacheEvict(value = "getAllBooks", allEntries = true)
	@CachePut(value = "getBookById", key = "#result.id")
	public BookResModel updateBook(BookReqModel bookReqModel, int id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (!optionalBook.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ID_NOT_FOUND.name());
		validateUniqueness(bookReqModel, id);
		Book book = optionalBook.get();
		bookMapper.updateBookFromRequestModel(bookReqModel, book);
		bookRepository.save(book);
		return bookMapper.mapFromBookToBookResModel(book);
	}

	private void validateUniqueness(BookReqModel bookReqModel, Integer id) {
		// Check if the ISBN exists and is not the current book being updated
		if (bookRepository.existsByIsbnAndIdNot(bookReqModel.getIsbn(), id)) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_ISBN_ALREADY_EXISTS.name());
		}

		// Check if the title and author combination exists and is not the current book
		// being updated
		if (bookRepository.existsByTitleAndAuthorAndIdNot(bookReqModel.getTitle(), bookReqModel.getAuthor(), id)) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_EXSITS.name());
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "getBookById", "getAllBooks" }, allEntries = true)
	public void deleteBook(int id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (!optionalBook.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ID_NOT_FOUND.name());
		Book book = optionalBook.get();
		if (!book.getAvailable())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_IS_ALREADY_BORROWED.name());
		borrowingRecordsRepository.deleteAllByBookId(id);
		bookRepository.delete(optionalBook.get());
	}

}

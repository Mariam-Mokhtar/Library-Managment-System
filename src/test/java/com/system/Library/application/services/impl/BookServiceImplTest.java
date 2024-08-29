package com.system.Library.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.system.Library.application.entity.Book;
import com.system.Library.application.filter.BookFilter;
import com.system.Library.application.mapper.BookMapper;
import com.system.Library.application.model.request.BookReqModel;
import com.system.Library.application.model.response.BookResModel;
import com.system.Library.application.repository.BookRepository;
import com.system.Library.application.service.impl.BookServiceImpl;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class) // tells JUnit to use the Mockito extension, which allows you to use Mockito
									// annotations like @Mock and @InjectMocks.
public class BookServiceImplTest {

	/*
	 * InjectMocks This annotation tells Mockito to inject the mocks (like
	 * bookRepository and bookMapper) into the bookService instance. This means that
	 * when bookService calls methods on bookRepository or bookMapper, it will call
	 * the mocked methods instead of the real ones.
	 */
	@InjectMocks
	private BookServiceImpl bookService;

	@Mock // creates a mock object
	private BookRepository bookRepository;

	@Mock
	private BookMapper bookMapper;

	private Book book;
	private BookReqModel bookReqModel;
	private BookResModel bookResModel;
	private BookFilter bookFilter;

	@BeforeEach // run before each test case
	void setUp() {
		book = new Book();
		book.setId(1);
		book.setTitle("Test Book");
		book.setAuthor("Test Author");
		book.setIsbn("123456789");

		bookReqModel = new BookReqModel();
		bookReqModel.setTitle("Test Book");
		bookReqModel.setAuthor("Test Author");
		bookReqModel.setIsbn("123456789");

		bookResModel = new BookResModel();
		bookResModel.setTitle("Test Book");
		bookResModel.setAuthor("Test Author");
		bookFilter = new BookFilter();
	}

	@Test
	void testGetAllBooks() {
		List<Book> books = new ArrayList<>();
		books.add(book);
		// return the books list when findAll() is called on the bookRepository mock
		when(bookRepository.findAll()).thenReturn(books);
		when(bookMapper.mapFromBookToBookResModel(books)).thenReturn(List.of(bookResModel));

		// it uses the mocked repository and mapper to return the results.
		List<BookResModel> result = bookService.getAllBooks();

		assertEquals(1, result.size());
		assertEquals("Test Book", result.get(0).getTitle());
		// This verifies that the findAll() method on bookRepository was called exactly
		// once.
		verify(bookRepository, times(1)).findAll();
		// This verifies that the mapFromBookToBookResModel(books) method on bookMapper
		// was called exactly once.
		verify(bookMapper, times(1)).mapFromBookToBookResModel(books);
	}

	@Test
	void testGetAllFilteredBooks() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
		List<Book> bookList = List.of(book);
		Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

		when(bookRepository.findAllBookWithFilter(any(BookFilter.class), any(Pageable.class))).thenReturn(bookPage);
		when(bookMapper.mapFromBookToBookResModel(bookPage)).thenReturn(List.of(bookResModel));

		List<BookResModel> result = bookService.getAllFilteredBooks(bookFilter, "asc", "title", 0, 10);

		assertEquals(1, result.size());
		verify(bookRepository, times(1)).findAllBookWithFilter(any(BookFilter.class), any(Pageable.class));
		verify(bookMapper, times(1)).mapFromBookToBookResModel(bookPage);
	}

	@Test
	void testGetAllFilteredBooks_EmptyResult() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> emptyPage = Page.empty(pageable);

		when(bookRepository.findAllBookWithFilter(any(BookFilter.class), any(Pageable.class))).thenReturn(emptyPage);

		List<BookResModel> result = bookService.getAllFilteredBooks(bookFilter, "asc", "title", 0, 10);

		assertEquals(0, result.size());
		verify(bookRepository, times(1)).findAllBookWithFilter(any(BookFilter.class), any(Pageable.class));
		verify(bookMapper, times(0)).mapFromBookToBookResModel(emptyPage);
	}

	@Test
	void testGetAllFilteredBooks_NoSortingField() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Book> bookList = List.of(book);
		Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

		when(bookRepository.findAllBookWithFilter(any(BookFilter.class), any(Pageable.class))).thenReturn(bookPage);
		when(bookMapper.mapFromBookToBookResModel(bookPage)).thenReturn(List.of(bookResModel));

		List<BookResModel> result = bookService.getAllFilteredBooks(bookFilter, null, null, 0, 10);

		assertEquals(1, result.size());
		verify(bookRepository, times(1)).findAllBookWithFilter(any(BookFilter.class), any(Pageable.class));
		verify(bookMapper, times(1)).mapFromBookToBookResModel(bookPage);
	}

	@Test
	void testGetAllFilteredBooks_InvalidSortingField() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Book> bookList = List.of(book);
		Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

		when(bookRepository.findAllBookWithFilter(any(BookFilter.class), any(Pageable.class))).thenReturn(bookPage);
		when(bookMapper.mapFromBookToBookResModel(bookPage)).thenReturn(List.of(bookResModel));
		List<BookResModel> result = bookService.getAllFilteredBooks(bookFilter, "asc", "invalidField", 0, 10);

		assertEquals(1, result.size());
		verify(bookRepository, times(1)).findAllBookWithFilter(any(BookFilter.class), any(Pageable.class));
		verify(bookMapper, times(1)).mapFromBookToBookResModel(bookPage);
	}

	@Test
	void testGetBookById_Found() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(bookMapper.mapFromBookToBookResModel(book)).thenReturn(bookResModel);

		BookResModel result = bookService.getBookById(1);

		assertNotNull(result);
		assertEquals("Test Book", result.getTitle());
		verify(bookRepository, times(1)).findById(1);
		verify(bookMapper, times(1)).mapFromBookToBookResModel(book);
	}

	@Test
	void testGetBookById_NotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
			bookService.getBookById(1);
		});

		assertEquals("BCV_BOOK_ID_NOT_FOUND", exception.getMessage());
		verify(bookRepository, times(1)).findById(1);
		verify(bookMapper, never()).mapFromBookToBookResModel(any(Book.class));
	}

	@Test
	void testCreateBook() {
		when(bookMapper.mapFromBookReqModelToBook(bookReqModel)).thenReturn(book);
		when(bookRepository.existsByIsbnAndIdNot(anyString(), anyInt())).thenReturn(false);
		when(bookRepository.existsByTitleAndAuthorAndIdNot(anyString(), anyString(), anyInt())).thenReturn(false);

		bookService.createBook(bookReqModel);

		verify(bookRepository, times(1)).save(book);
		verify(bookMapper, times(1)).mapFromBookReqModelToBook(bookReqModel);
	}

	@Test
	void testUpdateBook() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(bookRepository.existsByIsbnAndIdNot(anyString(), anyInt())).thenReturn(false);
		when(bookRepository.existsByTitleAndAuthorAndIdNot(anyString(), anyString(), anyInt())).thenReturn(false);

		BookResModel updatedBook = new BookResModel();
		updatedBook.setTitle("Updated Title");

		when(bookMapper.mapFromBookToBookResModel(book)).thenReturn(updatedBook);

		BookResModel result = bookService.updateBook(bookReqModel, 1);

		assertEquals("Updated Title", result.getTitle());
		verify(bookRepository, times(1)).save(book);
		verify(bookMapper, times(1)).updateBookFromRequestModel(bookReqModel, book);
		verify(bookMapper, times(1)).mapFromBookToBookResModel(book);
	}

	@Test
	void testDeleteBook_Found() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));

		bookService.deleteBook(1);

		verify(bookRepository, times(1)).delete(book);
	}

	@Test
	void testDeleteBook_NotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
			bookService.deleteBook(1);
		});

		assertEquals("BCV_BOOK_ID_NOT_FOUND", exception.getMessage());
		verify(bookRepository, never()).delete(any());
	}

}

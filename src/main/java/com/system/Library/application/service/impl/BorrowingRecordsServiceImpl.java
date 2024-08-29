package com.system.Library.application.service.impl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.Library.application.entity.Book;
import com.system.Library.application.entity.BorrowingRecords;
import com.system.Library.application.entity.Patron;
import com.system.Library.application.mapper.BookMapper;
import com.system.Library.application.mapper.BorrowingRecordsMapper;
import com.system.Library.application.model.response.BorrowingRecordsResModel;
import com.system.Library.application.repository.BookRepository;
import com.system.Library.application.repository.BorrowingRecordsRepository;
import com.system.Library.application.repository.PatronRepository;
import com.system.Library.application.service.BorrowingRecordsService;
import com.system.Library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookMapper bookMapper;

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	BorrowingRecordsRepository borrowingRecordsRepository;

	@Autowired
	BorrowingRecordsMapper borrowingRecordsMapper;

	@Override
	@Transactional
	public BorrowingRecordsResModel borrowBook(int bookId, int patronId) {
		BorrowingRecords borrowingRecord = new BorrowingRecords();
		Book book = bookRepository.findById(bookId).orElse(null);
		;
		if (book == null)
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ID_NOT_FOUND.name());
		Patron patron = patronRepository.findById(patronId).orElse(null);
		if (patron == null)
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_ID_NOT_FOUND.name());

		if (book.getAvailable()) {
			borrowingRecord.setBook(book);
			borrowingRecord.setPatron(patron);
			borrowingRecord.setBorrowingDate(LocalDate.now());
			borrowingRecordsRepository.save(borrowingRecord);
			book.setAvailable(false);
			bookRepository.save(book);
		} else
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_IS_NOT_AVAILABLE.name());
		return borrowingRecordsMapper.mapBorrowingRecordsToResModel(borrowingRecord);
	}

	@Override
	@Transactional
	public BorrowingRecordsResModel returnBook(int bookId, int patronId) {
		BorrowingRecords borrowingRecord = borrowingRecordsRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId,
				patronId);
		if (borrowingRecord == null)
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_HAS_ALREADY_BEEN_RETURNED.name());
		borrowingRecord.setReturnDate(LocalDate.now());
		Book book = borrowingRecord.getBook();
		book.setAvailable(true);
		borrowingRecordsRepository.save(borrowingRecord);
		bookRepository.save(book);
		return borrowingRecordsMapper.mapBorrowingRecordsToResModel(borrowingRecord);
	}
}

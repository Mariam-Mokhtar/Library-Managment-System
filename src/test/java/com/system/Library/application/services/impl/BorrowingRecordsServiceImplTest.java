package com.system.Library.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.system.Library.application.entity.Book;
import com.system.Library.application.entity.BorrowingRecords;
import com.system.Library.application.entity.Patron;
import com.system.Library.application.mapper.BorrowingRecordsMapper;
import com.system.Library.application.model.response.BorrowingRecordsResModel;
import com.system.Library.application.repository.BookRepository;
import com.system.Library.application.repository.BorrowingRecordsRepository;
import com.system.Library.application.repository.PatronRepository;
import com.system.Library.application.service.impl.BorrowingRecordsServiceImpl;
import com.system.Library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordsServiceImplTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private BorrowingRecordsRepository borrowingRecordsRepository;

	@Mock
	private BorrowingRecordsMapper borrowingRecordsMapper;

	@InjectMocks
	private BorrowingRecordsServiceImpl borrowingRecordsService;

	private Book book;
	private Patron patron;
	private BorrowingRecords borrowingRecord;
	private BorrowingRecordsResModel borrowingRecordsResModel;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		book = new Book();
		book.setId(1);
		book.setAvailable(true);

		patron = new Patron();
		patron.setId(1);

		borrowingRecord = new BorrowingRecords();
		borrowingRecord.setBook(book);
		borrowingRecord.setPatron(patron);
		borrowingRecord.setBorrowingDate(LocalDate.now());

		borrowingRecordsResModel = new BorrowingRecordsResModel();
		borrowingRecordsResModel.setBorrowingDate(LocalDate.now());
	}

	@Test
	void testBorrowBook_Success() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		when(patronRepository.findById(anyInt())).thenReturn(Optional.of(patron));
		when(borrowingRecordsRepository.save(any(BorrowingRecords.class))).thenReturn(borrowingRecord);
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(borrowingRecordsMapper.mapBorrowingRecordsToResModel(any(BorrowingRecords.class)))
				.thenReturn(borrowingRecordsResModel);

		BorrowingRecordsResModel result = borrowingRecordsService.borrowBook(1, 1);

		assertEquals(borrowingRecordsResModel, result);
		verify(bookRepository, times(1)).findById(1);
		verify(patronRepository, times(1)).findById(1);
		verify(borrowingRecordsRepository, times(1)).save(any(BorrowingRecords.class));
		verify(bookRepository, times(1)).save(book);
	}

	@Test
	void testBorrowBook_BookNotFound() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

		BusinessLogicViolationException thrown = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordsService.borrowBook(2, 1));

		assertEquals(ApiErrorMessageEnum.BCV_BOOK_ID_NOT_FOUND.name(), thrown.getMessage());
	}

	@Test
	void testBorrowBook_PatronNotFound() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		when(patronRepository.findById(anyInt())).thenReturn(Optional.empty());

		BusinessLogicViolationException thrown = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordsService.borrowBook(1, 2));

		assertEquals(ApiErrorMessageEnum.BCV_PATRON_ID_NOT_FOUND.name(), thrown.getMessage());
	}

	@Test
	void testBorrowBook_BookNotAvailable() {
		book.setAvailable(false);
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		when(patronRepository.findById(anyInt())).thenReturn(Optional.of(patron));

		BusinessLogicViolationException thrown = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordsService.borrowBook(1, 1));

		assertEquals(ApiErrorMessageEnum.BCV_BOOK_IS_NOT_AVAILABLE.name(), thrown.getMessage());
	}

	@Test
	void testReturnBook_Success() {
		when(borrowingRecordsRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyInt(), anyInt()))
				.thenReturn(borrowingRecord);
		when(borrowingRecordsMapper.mapBorrowingRecordsToResModel(any(BorrowingRecords.class)))
				.thenReturn(borrowingRecordsResModel);

		BorrowingRecordsResModel result = borrowingRecordsService.returnBook(1, 1);

		assertEquals(borrowingRecordsResModel, result);
		verify(borrowingRecordsRepository, times(1)).findByBookIdAndPatronIdAndReturnDateIsNull(1, 1);
		verify(borrowingRecordsRepository, times(1)).save(any(BorrowingRecords.class));
		verify(borrowingRecordsMapper, times(1)).mapBorrowingRecordsToResModel(borrowingRecord);
	}

	@Test
	void testReturnBook_RecordNotFound() {
		when(borrowingRecordsRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyInt(), anyInt()))
				.thenReturn(null);

		BusinessLogicViolationException thrown = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordsService.returnBook(1, 1));

		assertEquals(ApiErrorMessageEnum.BCV_BOOK_HAS_ALREADY_BEEN_RETURNED.name(), thrown.getMessage());
	}
}

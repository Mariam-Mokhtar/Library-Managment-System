package com.system.Library.application.service;

import com.system.Library.application.model.response.BorrowingRecordsResModel;

public interface BorrowingRecordsService {

	public BorrowingRecordsResModel borrowBook(int bookId, int patronId);

	public BorrowingRecordsResModel returnBook(int bookId, int patronId);
}

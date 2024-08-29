package com.system.Library.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.Library.application.entity.BorrowingRecords;

public interface BorrowingRecordsRepository extends JpaRepository<BorrowingRecords, Integer> {
	BorrowingRecords findByBookIdAndPatronIdAndReturnDateIsNull(int bookId, int patronId);
}

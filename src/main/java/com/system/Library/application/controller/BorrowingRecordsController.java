package com.system.Library.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.Library.application.model.response.BorrowingRecordsResModel;
import com.system.Library.application.service.BorrowingRecordsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class BorrowingRecordsController {

	@Autowired
	BorrowingRecordsService borrowingRecordsService;

	@Operation(summary = "Patron borrows a specific book")
	@PostMapping("/borrow/{bookId}/patron/{patronId}")
	public ResponseEntity<BorrowingRecordsResModel> borrowBook(@PathVariable("bookId") int bookId,
			@PathVariable("patronId") int patronId) {
		return new ResponseEntity<>(borrowingRecordsService.borrowBook(bookId, patronId), HttpStatus.OK);
	}

	@Operation(summary = "Patron returns a specific book")
	@PutMapping("/return/{bookId}/patron/{patronId}")
	public ResponseEntity<BorrowingRecordsResModel> returnBook(@PathVariable("bookId") int bookId,
			@PathVariable("patronId") int patronId) {
		return new ResponseEntity<>(borrowingRecordsService.returnBook(bookId, patronId), HttpStatus.OK);
	}

	public BorrowingRecordsController() {
		// TODO Auto-generated constructor stub
	}

}

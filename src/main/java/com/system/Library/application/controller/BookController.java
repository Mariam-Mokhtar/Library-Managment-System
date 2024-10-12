package com.system.Library.application.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.Library.application.filter.BookFilter;
import com.system.Library.application.model.request.BookReqModel;
import com.system.Library.application.model.response.BookResModel;
import com.system.Library.application.service.BookService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	ObjectMapper objectMapper;

	@Operation(summary = "Retrieve a list of all books")
	@GetMapping
	public ResponseEntity<List<BookResModel>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieve a list of filtered books")
	@GetMapping("/filter")
	public ResponseEntity<List<BookResModel>> getAllFilteredBooks(@RequestParam(required = false) String filter,
			@RequestParam(required = false) String sortingOrder, @RequestParam(required = false) String sortingField,
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		try {
			BookFilter bookFilter = new BookFilter();
			if (filter != null && !filter.isBlank())
				bookFilter = objectMapper.readValue(filter, BookFilter.class);
			return new ResponseEntity<>(
					bookService.getAllFilteredBooks(bookFilter, sortingOrder, sortingField, pageIndex, pageSize),
					HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException("Invalid filter format", e);
		}
	}

	@Operation(summary = "Retrieve details of a specific book by ID")
	@GetMapping("/{id}")
	public ResponseEntity<BookResModel> getBookById(@PathVariable("id") int id) {
		return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
	}

	@Operation(summary = "Add a new book to the library")
	@PostMapping
	public ResponseEntity<BookResModel> createBook(@Valid @RequestBody BookReqModel bookReqModel) {
		return new ResponseEntity<>(bookService.createBook(bookReqModel), HttpStatus.OK);
	}

	@Operation(summary = "Update an existing book's information")
	@PutMapping("/{id}")
	public ResponseEntity<BookResModel> updateBookById(@Valid @RequestBody BookReqModel bookReqModel,
			@PathVariable("id") int id) {
		return new ResponseEntity<>(bookService.updateBook(bookReqModel, id), HttpStatus.OK);
	}

	@Operation(summary = "Remove a book from the library")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
		bookService.deleteBook(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

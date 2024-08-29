package com.system.Library.application.service;

import java.util.List;

import com.system.Library.application.filter.BookFilter;
import com.system.Library.application.model.request.BookReqModel;
import com.system.Library.application.model.response.BookResModel;

public interface BookService {

	public List<BookResModel> getAllBooks();

	public List<BookResModel> getAllFilteredBooks(BookFilter bookFilter, String sortingOrder, String sortingField,
			int pageIndex, int pageSize);

	public BookResModel getBookById(int id);

	public void createBook(BookReqModel bookReqModel);

	public BookResModel updateBook(BookReqModel bookReqModel, int id);

	public void deleteBook(int id);

}

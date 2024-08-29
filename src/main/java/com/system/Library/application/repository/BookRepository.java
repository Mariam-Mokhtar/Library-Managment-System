package com.system.Library.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.Library.application.entity.Book;
import com.system.Library.application.filter.BookFilter;

//Integer type of id of the book
//The primary key type should be a wrapper class
public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT b FROM Book b WHERE (((:#{#bookFilter.title} IS NULL) OR (b.title LIKE %:#{#bookFilter.title}%))"
			+ "AND ((:#{#bookFilter.author} IS NULL) OR (b.author LIKE %:#{#bookFilter.author}%))"
			+ "AND ((:#{#bookFilter.year} IS NULL) OR (b.publish_year = :#{#bookFilter.year}))"
			+ "AND ((:#{#bookFilter.isbn} IS NULL) OR (b.isbn LIKE %:#{#bookFilter.isbn}%)))")
	public Page<Book> findAllBookWithFilter(@Param("bookFilter") BookFilter bookFilter, Pageable pageableReq);

	public boolean existsByIsbnAndIdNot(String isbn, Integer existingBookId);

	public boolean existsByTitleAndAuthorAndIdNot(String title, String author, Integer existingBookId);

}

package com.system.Library.application.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "borrowing_records", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "borrowingDate", "book_id", "patron_id" }) })
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecords implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2583137053509393341L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate borrowingDate;

//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate returnDate;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false/* , foreignKey = @ForeignKey(name = "BorrowingRecord_Book") */)
	private Book book;

	@ManyToOne
	@JoinColumn(name = "patron_id", nullable = false/* , foreignKey = @ForeignKey(name = "BorrowingRecord_Patron") */)
	private Patron patron;

}

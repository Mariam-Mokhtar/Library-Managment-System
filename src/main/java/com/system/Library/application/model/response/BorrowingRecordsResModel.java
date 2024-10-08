package com.system.Library.application.model.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowingRecordsResModel {
	private Long id;
	private PatronResModel patron;
	private BookResModel book;
	private LocalDate borrowingDate;
	private LocalDate returnDate;

	public BorrowingRecordsResModel() {
		// TODO Auto-generated constructor stub
	}

}

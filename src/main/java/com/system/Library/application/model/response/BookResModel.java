package com.system.Library.application.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResModel {

	private int id;
	private String title;
	private String author;
	private int publishYear;
	private String isbn;

}

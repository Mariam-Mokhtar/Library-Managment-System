package com.system.Library.application.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookReqModel {

	@NotNull
	@NotBlank
	private String title;

	@NotNull
	@NotBlank
	private String author;

	@Min(value = 1)
	private int publishYear;

	@NotNull
	@NotBlank
	@Length(min = 13, max = 13)
	private String isbn;

	private boolean available = true;

	public BookReqModel() {

	}

}

package com.system.Library.application.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Book", uniqueConstraints = { @UniqueConstraint(columnNames = { "title", "author" }) })
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208851331152155825L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length = 150)
	private String author;

	@Column(nullable = false)
	private int publish_year;

	@Column(nullable = false, unique = true, length = 13)
	private String isbn; // (International Standard Book Number)

	@OneToMany(mappedBy = "book")
	private Set<BorrowingRecords> borrowingRecords;

	@Column(name = "available")
	private Boolean available = true;

}

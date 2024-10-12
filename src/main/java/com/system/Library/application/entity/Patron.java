package com.system.Library.application.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.system.Library.auditing.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Patron", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Patron extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4151869354613686096L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Embedded
	private ContactInfo contactInfo;

	@OneToMany(mappedBy = "patron")
	private Set<BorrowingRecords> borrowingRecords;
}

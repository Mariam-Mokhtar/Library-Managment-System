package com.system.Library.application.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5146527311953705183L;

	@NotNull
	@NotBlank
	@Length(min = 11, max = 11)
	@Column(nullable = false, length = 11)
	private String phoneNum;

	@NotNull
	@NotBlank
	@Email(message = "Email should be valid")
	@Column(nullable = false, unique = true)
	private String email;

	@Embedded
	@NotNull
	private Address address;

	@Embedded
	@Null
	@AttributeOverrides({
			@AttributeOverride(name = "street", column = @Column(name = "work_street", nullable = true, length = 60)),
			@AttributeOverride(name = "state", column = @Column(name = "work_state", nullable = true, length = 60)),
			@AttributeOverride(name = "city", column = @Column(name = "work_city", nullable = true, length = 60)),
			@AttributeOverride(name = "postalCode", column = @Column(name = "work_postalCode")) })
	private Address workAddress;

}

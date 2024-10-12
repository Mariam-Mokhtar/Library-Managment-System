package com.system.Library.auditing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.system.Library.security.entity.UserCredential;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// This class won't be mapped to its own database table but will pass the fields to its child classes.
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	@CreatedBy
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER_ID")
	private UserCredential createdBy;

	@LastModifiedBy
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_USER_ID")
	private UserCredential lastModifiedBy;

}

package com.system.Library.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.Library.security.entity.UserCredential;

public interface UserRepository extends JpaRepository<UserCredential, Integer> {
	UserCredential findByUsername(String username);
}

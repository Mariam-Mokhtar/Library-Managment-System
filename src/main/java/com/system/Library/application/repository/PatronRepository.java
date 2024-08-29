package com.system.Library.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.Library.application.entity.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {

	public boolean existsByContactInfoEmailAndIdNot(String email, Integer id);

}

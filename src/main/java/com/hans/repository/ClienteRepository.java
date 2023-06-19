package com.hans.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hans.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	
	
}

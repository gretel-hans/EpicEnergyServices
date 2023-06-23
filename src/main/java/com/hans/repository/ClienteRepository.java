package com.hans.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hans.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //GreaterThan indica maggiore di
    //perciò con questa quesry andiamo a cercare ogni cliente con un fatturato annuale
    //di quello che gli passiamo come parametro

		public List<Cliente> findByFatturatoAnnualeGreaterThanEqual(double FatturatoAnnuale);
        
	
}

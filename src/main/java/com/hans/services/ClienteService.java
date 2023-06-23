package com.hans.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hans.model.Cliente;
import com.hans.repository.ClienteRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	
	public Cliente searchCliente(Long id) {
		return clienteRepository.findById(id).get();
	}
	
	public Cliente updateCliente(Long id, Cliente cliente) {
		if(!clienteRepository.existsById(id)) {
			throw new EntityExistsException("Cliente già esistente!");
		}
		return clienteRepository.save(cliente);
	}
	

	public List<Cliente> cercaPerFatturatoAnnualeMaggiore(double importo){
		return clienteRepository.findByFatturatoAnnualeGreaterThanEqual(importo);
	}
	
	
    public List<Cliente> searchAllClienti(){
    	return clienteRepository.findAll();
    	
    } 
}
  
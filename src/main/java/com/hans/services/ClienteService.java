package com.hans.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hans.model.Cliente;
import com.hans.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente createCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	
	public Cliente searchCliente(Long id) {
		return clienteRepository.findById(id).get();
	}
	
	public Cliente updateCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
    public List<Cliente> searchAllClienti(){
    	return clienteRepository.findAll();
    	
    } 
}
  
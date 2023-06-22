package com.hans.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hans.model.Fattura;
import com.hans.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;
	
    public Fattura saveFattura(Fattura fattura) {
    	return fatturaRepository.save(fattura);
    }
    public Fattura searchFattura(Long id) {
    	return fatturaRepository.findById(id).get();
    }
    public Fattura updateFattura(Fattura fattura) {
    	return fatturaRepository.save(fattura);
    }
   public List<Fattura> searchAllFatture(){
	   return fatturaRepository.findAll();
   }
}
 
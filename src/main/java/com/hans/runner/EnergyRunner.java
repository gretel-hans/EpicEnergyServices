package com.hans.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.hans.enumeration.Stato_Fatture;
import com.hans.model.StatoFattura;
import com.hans.repository.StatoFatturaRepository;

@Component
public class EnergyRunner implements CommandLineRunner{
	
	@Autowired
	StatoFatturaRepository statoFatturaRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runner epic energy sevrices.... ");
		
		if(statoFatturaRepository.findAll().size() == 0) {
			setStatoFatture();
		}
		
	}
	
	
	public void setStatoFatture() {
		StatoFattura st = new StatoFattura(null, Stato_Fatture.PAGATO);
		StatoFattura st2 = new StatoFattura(null, Stato_Fatture.NON_PAGATO);
		statoFatturaRepository.save(st);
		statoFatturaRepository.save(st2);
	}

}

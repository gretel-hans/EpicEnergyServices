package com.hans.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.hans.enumeration.Stato_Fatture;
import com.hans.model.Comune;
import com.hans.model.Provincia;
import com.hans.model.StatoFattura;
import com.hans.repository.ComuneRepository;
import com.hans.repository.ProvinciaRepository;
import com.hans.repository.StatoFatturaRepository;

@Component
public class EnergyRunner implements CommandLineRunner {

	@Autowired
	StatoFatturaRepository statoFatturaRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;
	
	@Autowired
	ComuneRepository comuneRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runner epic energy sevrices.... ");

		if (statoFatturaRepository.findAll().size() == 0) {
			setStatoFatture();
		}

		if (provinciaRepository.findAll().size() == 0) {
			setProvince();
		}
		
		if (comuneRepository.findAll().size() == 0) {
			setComune();
		}

	}

	public void setStatoFatture() {
		StatoFattura st = new StatoFattura(null, Stato_Fatture.PAGATO);
		StatoFattura st2 = new StatoFattura(null, Stato_Fatture.NON_PAGATO);
		statoFatturaRepository.save(st);
		statoFatturaRepository.save(st2);
	}

	public void setProvince() {
		String provinceTotali;
		String[] rigaProvincia;
		String[] unicaProvincia;
		
		Scanner sc;
		try {
			sc = new Scanner(new File("comuniEProvince/province-italiane.csv"));
			sc.useDelimiter(","); 
			while (sc.hasNext()) 
			{
				provinceTotali = sc.next();
				rigaProvincia = provinceTotali.split("\n");
				for (int i = 0; i < rigaProvincia.length; i++) {
					unicaProvincia = rigaProvincia[i].split(";");
					Provincia p = new Provincia(null, unicaProvincia[0], unicaProvincia[1], unicaProvincia[2]);
					provinciaRepository.save(p);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
    
	public void setComune() {
		Scanner sc2;
		String comuniTotali;
		String[] rigaComuni;
		String[] unicoComune;
		
		try {
			sc2 = new Scanner(new File("comuniEProvince/comuni-italiani.csv"));
			sc2.useDelimiter(","); 
			while (sc2.hasNext()) 
			{
				comuniTotali = sc2.next();
				rigaComuni = comuniTotali.split("\n");
				for (int i = 0; i < rigaComuni.length; i++) {
					unicoComune = rigaComuni[i].split(";");
					Long idProvincia=Long.parseLong(unicoComune[1]);
					if(idProvincia<=110 && unicoComune.length==5){
						Provincia p1 = provinciaRepository.findById(idProvincia).get();
						Comune c = new Comune(null, p1, unicoComune[2], unicoComune[3],unicoComune[4]);
						comuneRepository.save(c);
					}
				}
				
			}
			sc2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

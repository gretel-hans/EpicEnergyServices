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

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runner epic energy sevrices.... ");

		if (statoFatturaRepository.findAll().size() == 0) {
			setStatoFatture();
		}

		if (provinciaRepository.findAll().size() == 0) {
			setProvince();
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
		// parsing a CSV file into Scanner class constructor
		Scanner sc;
		try {
			sc = new Scanner(new File("comuniEProvince/province-italiane.csv"));
			sc.useDelimiter(","); // sets the delimiter pattern
			while (sc.hasNext()) // returns a boolean value
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

}

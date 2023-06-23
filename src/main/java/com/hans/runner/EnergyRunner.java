package com.hans.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.hans.enumeration.Stato_Fatture;
import com.hans.enumeration.TipoCliente;
import com.hans.model.Cliente;
import com.hans.model.Comune;
import com.hans.model.Fattura;
import com.hans.model.Indirizzo;
import com.hans.model.Provincia;
import com.hans.model.StatoFattura;
import com.hans.repository.ComuneRepository;
import com.hans.repository.ProvinciaRepository;
import com.hans.repository.StatoFatturaRepository;
import com.hans.services.ClienteService;
import com.hans.services.FatturaService;
import com.hans.services.IndirizzoService;
import com.hans.services.ProvinciaService;

@Component
public class EnergyRunner implements CommandLineRunner {

	@Autowired
	StatoFatturaRepository statoFatturaRepository;
	
	@Autowired
	FatturaService fatturaService;

	@Autowired
	ProvinciaRepository provinciaRepository;

	@Autowired
	ProvinciaService provinciaService;
	
	@Autowired
	ComuneRepository comuneRepository;
	
	@Autowired
	IndirizzoService indirizzoService;
	
	@Autowired
	ClienteService clienteService;

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
		
		for(int i = 0; i < 30; i++) {
			setClienti();
		}

	}
	
	
//	METODO PER GENERARE LO STATO DELLE FATTURE
	public void setStatoFatture() {
		StatoFattura st = new StatoFattura(null, Stato_Fatture.PAGATO);
		StatoFattura st2 = new StatoFattura(null, Stato_Fatture.NON_PAGATO);
		statoFatturaRepository.save(st);
		statoFatturaRepository.save(st2);
	}

//	METODO PER COLLEGARE LE PROVINCE SUL DB
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
    
//	METODO PER COLLEGARE I COMUNI SUL DB
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
					
					if( !(unicoComune[1].equals("#RIF!"))  ){
						Provincia p1 = provinciaService.cercaProvinciaConNome(unicoComune[3].trim());
						if(p1!=null){
							Comune c = new Comune(null,unicoComune[0],unicoComune[1],unicoComune[2],p1);
							comuneRepository.save(c);
						}
					}
				}
				}
			sc2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	METODO PER GENERARE I CLIENTI
	public void setClienti() {
		
		//ISTANZIAMO FAKER
		Faker fake= Faker.instance(new Locale("it-IT"));
		
		//ISTANZIAMO CLASSE FATTURA
		List<Fattura> fat = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
		Fattura f = new Fattura();
		f.setData(LocalDateTime.now().minusDays(fake.number().numberBetween(1l, 2190l)));
		f.setAnno(f.getData().getYear());
		f.setImporto(fake.number().randomDouble(3, 10000, 900000)+" €");
		f.setNumeroFattura(fake.number().numberBetween(1, 1000));
		f.setStato(statoFatturaRepository.findById(fake.number().numberBetween(1l, 3l)).get());
		fat.add(fatturaService.saveFattura(f));
		}
		
		
		
		
		//ISTANZIAMO CLASSE INDIRIZZO
		List <Indirizzo> ind = new ArrayList<>();
		
		//ciclo for eseguito due volte per generare due indirizzi fake(per sedeLegale e sedeOperativa)
		for(int i = 0; i < 2; i++) {
			
		Indirizzo indirizzo = new Indirizzo();
		
		indirizzo.setVia("Via - "+fake.address().streetName());
		indirizzo.setCivico(fake.address().buildingNumber());
		indirizzo.setLocalita(fake.address().city());
		indirizzo.setCap(fake.address().zipCode());
		indirizzo.setComune(comuneRepository.findById(fake.number().numberBetween(1l, 7000l)).get());
		ind.add(indirizzoService.saveIndirizzo(indirizzo));
		
		}
		
		
	
		TipoCliente[] type = {TipoCliente.PA, TipoCliente.SAS, TipoCliente.SPA, TipoCliente.SRL};
		
		
		
		//ISTANZIAMO CLASSE CLIENTE
		Cliente c = new Cliente();
		c.setRagione_sociale(fake.company().name());
		c.setPartita_iva(fake.number().randomNumber(10, true));
		c.setNomeContatto(fake.name().firstName());
		c.setCongomeContatto(fake.name().lastName());
		c.setEmail(c.getNomeContatto()+"."+c.getCongomeContatto()+"@gmail.com");
		c.setDataInserimento(LocalDate.now().minusDays(fake.number().numberBetween(1l, 366l)));
		c.setDataUltimoContatto(c.getDataInserimento().plusDays(fake.number().numberBetween(1l, 366l)));
		c.setFatturatoAnnuale(fake.number().randomNumber(8, false)+" €");
		c.setPec(c.getNomeContatto()+"@pec.com");
		c.setTelefono(fake.phoneNumber().cellPhone());
		c.setEmailContatto(c.getNomeContatto()+"."+c.getCongomeContatto()+"@yahoo.it");
		c.setTelefonoContatto(fake.phoneNumber().phoneNumber());
		c.setSedeLegale(indirizzoService.searchIndirizzo(ind.get(0).getId()));
		c.setSedeOperativa(indirizzoService.searchIndirizzo(ind.get(1).getId()));
		int numeroCasuale= fake.number().numberBetween(0, 3);
		c.setTipoCliente(type[numeroCasuale]);
		c.setFattura(fat);
		clienteService.saveCliente(c);
		
				
		
	}
}

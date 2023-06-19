package com.hans.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnergyRunner implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runner epic energy sevrices.... ");
		
	}

}

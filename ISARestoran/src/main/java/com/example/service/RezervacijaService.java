package com.example.service;

import com.example.beans.restoran.Rezervacija;

public interface RezervacijaService {
	
	 Iterable<Rezervacija> getAllRezervacije();
	 Rezervacija getRezervacijaById(Integer id);
	 Rezervacija saveRezervacija(Rezervacija contact);
	 void deleteRezervacija(Integer id);
}

package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Rezervacija;
import com.example.repository.restoran.RezervacijaRepository;
import com.example.service.RezervacijaService;

@Service
public class RezervacijaServiceImpl implements RezervacijaService{

	private RezervacijaRepository rezervacijaRepozitorijum;
	
	@Autowired
	public void setRezervacijaRepository(RezervacijaRepository rezervacijaRepo) {
		this.rezervacijaRepozitorijum = rezervacijaRepo;
	}
	
	@Override
	public Iterable<Rezervacija> getAllRezervacije() {
		return this.rezervacijaRepozitorijum.findAll();
	}

	@Override
	public Rezervacija getRezervacijaById(Integer id) {
		return this.rezervacijaRepozitorijum.findOne(id);
	}

	@Override
	public Rezervacija saveRezervacija(Rezervacija contact) {
		return this.rezervacijaRepozitorijum.save(contact);
	}

	@Override
	public void deleteRezervacija(Integer id) {
		this.rezervacijaRepozitorijum.delete(id);
		
	}

}

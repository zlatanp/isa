package com.example.service.korisniciImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.enums.TipKorisnika;
import com.example.repository.MenadzerRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class MenadzerService {

	@Autowired
	private MenadzerRepository menadzerRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	public MenadzerRestorana create(MenadzerDTO menadzer){
		MenadzerRestorana zaBazu = new MenadzerRestorana(menadzer.getIme(), menadzer.getPrezime(), menadzer.getEmail(), 
										menadzer.getPassword(), TipKorisnika.MENADZERRESTORANA);
	    Restoran r = restoranRepository.findOne(menadzer.getRadi_u());
		zaBazu.setRadi_u(r);
		zaBazu = menadzerRepository.save(zaBazu);
		return zaBazu;
	}
	
}

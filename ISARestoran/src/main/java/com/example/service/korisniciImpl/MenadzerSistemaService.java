package com.example.service.korisniciImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerSistema;
import com.example.dto.korisnici.MenadzerSistemaDTO;
import com.example.enums.TipKorisnika;
import com.example.repository.MenadzerSistemaRepository;


@Service
public class MenadzerSistemaService{

	@Autowired
	private MenadzerSistemaRepository menadzerSisRepository;
	
	
	public MenadzerSistemaDTO findById(int id){
		MenadzerSistema trazeni = menadzerSisRepository.findOne(id);
		if(trazeni != null){
			return new MenadzerSistemaDTO(trazeni);
		}else {
			return null;
		}
	}
	
	public List<MenadzerSistema> getAll(){
		List<MenadzerSistema> svi = menadzerSisRepository.findAll();
		return svi;
	}
	
	
	public MenadzerSistemaDTO create(MenadzerSistemaDTO menadzer){
		MenadzerSistema menSisZaBazu = new MenadzerSistema(menadzer.getIme(), menadzer.getPrezime(), menadzer.getEmail(), 
				menadzer.getPassword(), TipKorisnika.MENADZERSISTEMA);
		menadzerSisRepository.save(menSisZaBazu);
		return menadzer;
	}
	

}

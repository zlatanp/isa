package com.example.service.korisniciImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerSistema;
import com.example.repository.MenadzerSistemaRepository;
import com.example.service.MenadzerSistemaService;

@Service
public class MenadzerSistemaServiceImpl implements MenadzerSistemaService{

	private MenadzerSistemaRepository menadzerSisRepository;
	
	@Autowired
	public void setMenadzerSisRepository(MenadzerSistemaRepository rep) {
		this.menadzerSisRepository = rep;
	}
	
	
	@Override
	public Iterable<MenadzerSistema> getAllMenadzeriSistema() {
		return menadzerSisRepository.findAll();
	}

	@Override
	public MenadzerSistema getMenadzerSistemaById(Integer id) {
		return menadzerSisRepository.findOne(id);
	}

	@Override
	public MenadzerSistema saveMenadzerSistema(MenadzerSistema entity) {
		return menadzerSisRepository.save(entity);
	}

	@Override
	public void deleteMenadzerSistem(Integer id) {
		menadzerSisRepository.delete(id);
	}

}

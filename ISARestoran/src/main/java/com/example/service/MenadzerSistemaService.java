package com.example.service;

import com.example.beans.korisnici.MenadzerSistema;

public interface MenadzerSistemaService {

	 Iterable<MenadzerSistema> getAllMenadzeriSistema();
	 MenadzerSistema getMenadzerSistemaById(Integer id);
	 MenadzerSistema saveMenadzerSistema(MenadzerSistema contact);
	 void deleteMenadzerSistem(Integer id);
}

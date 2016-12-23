package com.example.service;

import com.example.beans.korisnici.Gost;

public interface GostService {

	 Iterable<Gost> getAllGosti();
	 Gost getGostById(Integer id);
	 Gost saveGost(Gost contact);
	 void deleteGost(Integer id);
}

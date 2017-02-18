package com.example.service;

import com.example.beans.korisnici.Prijateljstvo;

public interface PrijateljstvoService {
	
	Iterable<Prijateljstvo> getAllPrijateljstva();
	Prijateljstvo getPrijateljstvoById(Integer id);
	Prijateljstvo savePrijateljstvo(Prijateljstvo contact);
	void deletePrijateljstvo(Integer id);

}

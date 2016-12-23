package com.example.service;


import com.example.beans.korisnici.Korisnik;

public interface KorisnikService {

	 Iterable<Korisnik> getAllKorisnici();
	 Korisnik getKorisnikById(Integer id);
	 Korisnik saveKorisnik(Korisnik contact);
	 void deleteKorisnik(Integer id);
}

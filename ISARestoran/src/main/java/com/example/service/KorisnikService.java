package com.example.service;


import com.example.beans.korisnici.Korisnik;
import com.example.dto.korisnici.KorisnikDTO;

public interface KorisnikService {

	 Iterable<Korisnik> getAllKorisnici();
	 Korisnik getKorisnikById(Integer id);
	 Korisnik saveKorisnik(Korisnik contact);
	 void deleteKorisnik(Integer id);
	 
	 KorisnikDTO findByEmail(String email);
	 KorisnikDTO findByEmailAndPassword(String email, String password);
	 KorisnikDTO updatePassword(String email, String stara, String nova);
}

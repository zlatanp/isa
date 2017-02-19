package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Korisnik;


public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	
	Korisnik findByEmailAndPassword( String email, String password);
	
	Korisnik findByEmail(String email);
	
}




package com.example.repository;


import org.springframework.data.repository.CrudRepository;

import com.example.beans.korisnici.Korisnik;





public interface KorisnikRepository extends CrudRepository<Korisnik, Integer> {
	
	
}




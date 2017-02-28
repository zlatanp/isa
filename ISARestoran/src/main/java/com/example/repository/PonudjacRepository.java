package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Ponudjac;

public interface PonudjacRepository extends JpaRepository<Ponudjac, Integer>{

	Ponudjac findById(int id);
	
	Ponudjac findByEmail(String email);
	
	Ponudjac findByEmailAndPassword(String email, String password);
}

package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Kuvar;

public interface KuvarRepository extends JpaRepository<Kuvar, Integer>{

	Kuvar findById(int id);
	
	Kuvar findByEmail(String email);
	
	Kuvar findByEmailAndPassword(String email, String password);
	
}

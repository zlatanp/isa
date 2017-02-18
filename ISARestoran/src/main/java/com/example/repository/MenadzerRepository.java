package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.MenadzerRestorana;

public interface MenadzerRepository extends JpaRepository<MenadzerRestorana, Integer>{
	
	
	MenadzerRestorana findById(int id);
	
	MenadzerRestorana findByEmail(String email);
	
	MenadzerRestorana findByEmailAndPassword(String email, String password);

}

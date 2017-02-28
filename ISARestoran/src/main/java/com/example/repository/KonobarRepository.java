package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Konobar;

public interface KonobarRepository extends JpaRepository<Konobar, Integer>{

	Konobar findById(int id);
	
	Konobar findByEmail(String email);
	
	Konobar findByEmailAndPassword(String email, String password);
}

package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Sanker;

public interface SankerRepository extends JpaRepository<Sanker, Integer>{

	Sanker findById(int id);
	
	Sanker findByEmail(String email);
	
	Sanker findByEmailAndPassword(String email, String password);
}

package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Zaposleni;

public interface ZaposleniRepository extends JpaRepository<Zaposleni, Integer> {

	Zaposleni findByEmail(String email);
	
	Zaposleni findById(int id);
	
	List<Zaposleni> findAll();
}

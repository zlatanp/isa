package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.MenadzerSistema;

public interface MenadzerSistemaRepository extends JpaRepository<MenadzerSistema, Integer> {
	
}

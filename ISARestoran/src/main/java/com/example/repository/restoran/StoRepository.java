package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Sto;

public interface StoRepository extends JpaRepository<Sto, Integer> {

	Sto findById(int id);
	Sto findByNaziv(String naziv);
}

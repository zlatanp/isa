package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Ponuda;

public interface PonudaRepository extends JpaRepository<Ponuda, Integer>{

	Ponuda findById(int id);
	
	List<Ponuda> findAll();
}

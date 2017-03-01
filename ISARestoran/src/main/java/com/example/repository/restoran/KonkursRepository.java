package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Konkurs;

public interface KonkursRepository extends JpaRepository<Konkurs, Integer> {

	Konkurs findById(int id);
	
	List<Konkurs> findAll();
}

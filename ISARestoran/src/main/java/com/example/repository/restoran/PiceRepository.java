package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Pice;
import com.example.beans.restoran.Restoran;

public interface PiceRepository extends JpaRepository<Pice, Integer>{

	Pice findById(int id);
	List<Pice> findAll();
	List<Pice> findByRestoran(Restoran r);
}

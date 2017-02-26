package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Reon;

public interface ReonRepository extends JpaRepository<Reon, Integer>{

	Reon findById(int id);
}

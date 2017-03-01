package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Namirnica;

public interface NamirnicaRepository extends JpaRepository<Namirnica, Integer>{

	Namirnica findById(int id);
}

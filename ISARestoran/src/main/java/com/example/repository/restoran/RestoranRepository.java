package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Restoran;

public interface RestoranRepository extends JpaRepository<Restoran, Integer>{

}

package com.example.repository.restoran;

import org.springframework.data.repository.CrudRepository;

import com.example.beans.restoran.Restoran;

public interface RestoranMyRepository extends  CrudRepository<Restoran, Integer> {

}

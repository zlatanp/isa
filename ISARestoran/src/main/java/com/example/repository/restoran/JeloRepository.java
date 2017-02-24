package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Jelo;
import com.example.beans.restoran.Restoran;

public interface JeloRepository extends JpaRepository<Jelo, Integer>{
	
	Jelo findById(int id);
	List<Jelo> findAll();
	List<Jelo> findByRestoran(Restoran restoran);

}

package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.korisnici.Gost;
import com.example.beans.restoran.Rezervacija;


public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
	
	Rezervacija findById(int id);
	
	List<Rezervacija> findByRezervisao(Gost rezervisao);
	
	

}
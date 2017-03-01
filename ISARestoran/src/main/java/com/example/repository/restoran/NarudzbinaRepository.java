package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beans.korisnici.Gost;
import com.example.beans.restoran.Narudzbina;
import com.example.beans.restoran.Rezervacija;

public interface NarudzbinaRepository extends JpaRepository<Narudzbina, Integer>{

	Narudzbina findById(int id);
	
	Narudzbina findByRezervacijaAndGost(Rezervacija rezervacija, Gost gost);
	
	@Query("select n from Narudzbina n inner join n.jela j where :id IN (j)")
	Narudzbina findKolicinaByStavkaJelo(@Param("id") int id);

	@Query("select n from Narudzbina n inner join n.pica p where :id IN (p)")
	Narudzbina findKolicinaByStavkaPice(@Param("id")int id);
}

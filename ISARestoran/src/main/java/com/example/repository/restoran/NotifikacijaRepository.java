package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beans.korisnici.Korisnik;
import com.example.beans.restoran.Notifikacija;

public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Integer>{

	Notifikacija findById(int id);
	
	List<Notifikacija> findByKorisnik(Korisnik korisnik);
	
	@Query(value="select n.id, n.pregledana, n.text_notif, n.korisnik, n.vezana_za_gosta, "
			+ "n.vezana_za_ponudu, n.vezana_za_restoran, n.vezana_za_rezervaciju, n.tip "
			+ "from notifikacija n inner join korisnik k on n.korisnik=k.id "
			+ "where k.email = :email and n.pregledana = false", nativeQuery=true)
	List<Notifikacija> findByEmail(@Param("email") String email);
	
	@Query(value="select * from notifikacija n inner join rezervacija r on n.vezana_za_rezervaciju = r.id "
			+ "where r.id = :id ", nativeQuery=true)
	List<Notifikacija> findByRezervacija(@Param("id") int id);
}

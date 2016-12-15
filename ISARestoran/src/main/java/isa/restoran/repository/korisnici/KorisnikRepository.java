package isa.restoran.repository.korisnici;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.restoran.beans.korisnici.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{

	Korisnik findById(int id);
	
	Korisnik findByEmail(String email);
	
	Korisnik findByEmailAndPassword( String email, String password);
	
	List<Korisnik> findByImeOrPrezime(String ime, String prezime);
	
	List<Korisnik> findByIme(String ime);
	
	List<Korisnik> findByPrezime(String prezime);
}

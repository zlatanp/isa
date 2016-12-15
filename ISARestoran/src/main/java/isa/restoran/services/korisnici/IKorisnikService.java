package isa.restoran.services.korisnici;

import java.util.List;

import org.springframework.stereotype.Service;

import isa.restoran.beans.korisnici.Korisnik;
import isa.restoran.dto.korisnici.KorisnikDTO;

@Service
public interface IKorisnikService<BEAN extends Korisnik, DTO extends KorisnikDTO> {

	BEAN create(DTO korisnik);
	
	DTO findByEmailAndPassword(String email, String password);
	
	List<DTO> findByImeOrPrezime(String ime, String prezime, String self_email);
	    
	List<DTO> findByIme(String ime, String email);
	    
	List<DTO> findByPrezime(String prezime, String email);
	
	void saveRegisteredUser(Korisnik user);
}

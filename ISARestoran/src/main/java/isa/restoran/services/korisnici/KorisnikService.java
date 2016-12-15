package isa.restoran.services.korisnici;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.restoran.beans.korisnici.Korisnik;
import isa.restoran.dto.korisnici.KorisnikDTO;
import isa.restoran.repository.korisnici.KorisnikRepository;

@Service
public class KorisnikService implements IKorisnikService<Korisnik, KorisnikDTO> {

	@Autowired
	KorisnikRepository korisnikRepository;

	@Override
	public Korisnik create(KorisnikDTO korisnik) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KorisnikDTO findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KorisnikDTO> findByImeOrPrezime(String ime, String prezime, String self_email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KorisnikDTO> findByIme(String ime, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KorisnikDTO> findByPrezime(String prezime, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRegisteredUser(Korisnik user) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

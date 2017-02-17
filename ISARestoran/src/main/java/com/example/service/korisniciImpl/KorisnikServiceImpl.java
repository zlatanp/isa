package com.example.service.impl;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.beans.korisnici.Korisnik;
import com.example.repository.KorisnikRepository;
import com.example.service.KorisnikService;

@Service	
public class KorisnikServiceImpl implements KorisnikService {

	private KorisnikRepository korisnikRepo;
	
	 @Autowired
	    public void setGuestRepository(KorisnikRepository guestRepo) {
	        this.korisnikRepo = guestRepo;
	    }
	
	@Override
	public Iterable<Korisnik> getAllKorisnici() {
		return this.korisnikRepo.findAll();
	}

	@Override
	public Korisnik getKorisnikById(Integer id) {
		return this.korisnikRepo.findOne(id);
	}

	@Override
	public Korisnik saveKorisnik(Korisnik contact) {
		return this.korisnikRepo.save(contact);
	}

	@Override
	public void deleteKorisnik(Integer id) {
		this.korisnikRepo.delete(id);
	}

}

package com.example.service.korisniciImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.beans.korisnici.Korisnik;
import com.example.dto.korisnici.FactoryKorisnikDTO;
import com.example.dto.korisnici.KorisnikDTO;
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

	@Override
	public KorisnikDTO findByEmailAndPassword(String email, String password) {
		KorisnikDTO korisnikDTO = FactoryKorisnikDTO
				.makeKorisnikDTO(korisnikRepo.findByEmailAndPassword(email, password));
		return korisnikDTO;
	}

	@Override
	public KorisnikDTO updatePassword(String email, String stara, String nova) {
		Korisnik kZaUpdate = korisnikRepo.findByEmail(email);
		if (kZaUpdate == null)
			return null;
		if (!kZaUpdate.getPassword().equals(stara))
			return null;
		kZaUpdate.setPassword(nova);
		kZaUpdate.setPromenioLozinku(true);
		Korisnik zaBazu = korisnikRepo.save(kZaUpdate);
		if (zaBazu == null)
			return null;
		return FactoryKorisnikDTO.makeKorisnikDTO(zaBazu);

	}

	@Override
	public KorisnikDTO findByEmail(String email) {
		Korisnik k = korisnikRepo.findByEmail(email);
		if(k == null)
			return null;
		return FactoryKorisnikDTO.makeKorisnikDTO(k);
	}

}

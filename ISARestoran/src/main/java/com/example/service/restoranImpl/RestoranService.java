package com.example.service.restoranImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.korisnici.Zaposleni;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipValute;
import com.example.repository.MenadzerRepository;
import com.example.repository.ZaposleniRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class RestoranService {

	@Autowired
	RestoranRepository restoranRepostitory;

	@Autowired
	MenadzerRepository menadzerRepository;

	@Autowired
	ZaposleniRepository zaposleniRepository;
	
	
	private static final String default_restoran_path = "restoran.jpg";

	public RestoranDTO create(RestoranDTO restoran) {
		Restoran restoranZaBazu = new Restoran(restoran.getNaziv(), restoran.getTip(), restoran.getAdresa(),
				restoran.getGrad(), restoran.getOpis(), restoran.getTelefon(), restoran.getEmail(),
				restoran.getVremeOD(), restoran.getVremeDO());
		if (restoran.slika.equals(""))
			System.out.println("slika mu je nuls");
		restoranZaBazu.setSlika(default_restoran_path);
		Restoran r = restoranRepostitory.save(restoranZaBazu);
		RestoranDTO ret = new RestoranDTO(r.getId(), r.getNaziv(), r.getAdresa(), r.getGrad(), r.getTelefon(),
				r.getEmail(), r.getOpis(), r.getRadnoVremeDo(), r.getRadnoVremeOd(), r.getRestaurantType(),
				TipValute.DINAR);
		ret.slika = r.getSlika();
		return ret;
	}

	public RestoranDTO findById(int id) {
		Restoran trazeni = restoranRepostitory.findOne(id);
		if (trazeni != null) {
			return new RestoranDTO(trazeni);
		} else {
			return null;
		}
	}

	public List<RestoranDTO> findAll() {
		List<Restoran> restorani = restoranRepostitory.findAll();
		List<RestoranDTO> restoraniDTO = new ArrayList<>();
		for (Restoran r : restorani) {
			RestoranDTO restoranDTO = new RestoranDTO(r);
			restoraniDTO.add(restoranDTO);
		}
		return restoraniDTO;
	}

	public boolean updateRestoran(RestoranDTO restoran, String email) {
		String emailManagera = email;
		Restoran r = restoranRepostitory.findOne(restoran.getId());
		if (r == null || !jesteMenadzer(r, emailManagera)) {
			return false;
		}
		r.setEmail(restoran.getEmail());
		r.setNaziv(restoran.getNaziv());
		r.setAdresa(restoran.getAdresa());
		r.setGrad(restoran.getGrad());
		r.setTelefon(restoran.getTelefon());
		r.setOpis(restoran.getOpis());
		r.setRadnoVremeDo(restoran.getVremeDO());
		r.setRadnoVremeOd(restoran.getVremeOD());
		r.setValuta(TipValute.DINAR);
		r.setTipRestorana(restoran.getTip());

		Restoran zaBazu = restoranRepostitory.save(r);
		if (zaBazu == null)
			return false;
		return true;
	}

	public boolean jesteMenadzer(Restoran r, String email) {

		for (MenadzerRestorana m : r.getMenadzeri()) {
			if (m.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public void namestiSliku(int id, String slika) {
		Restoran r = restoranRepostitory.findOne(id);
		if (r == null)
			return;
		r.setSlika(slika);
		restoranRepostitory.save(r);
	}

	// novo
	public boolean update(String canvasJSON, String email) {
		MenadzerRestorana men = menadzerRepository.findByEmail(email);
		if (men == null)
			return false;
		Restoran restoran = men.getRadi_u();
		if (restoran == null)
			return false;
		restoran.setRaspored(canvasJSON);
		restoranRepostitory.save(restoran);
		return true;
	}

	public String getRaspored(KorisnikDTO k){
		// ovde isprati sta ce da ispise
		Restoran restoran;
		if(k instanceof MenadzerDTO){
			MenadzerRestorana menadzer = menadzerRepository.findByEmail(k.email);
			if(menadzer == null){
				System.out.println("menadzer: menadzer==null");
				return "";
			}
			restoran = menadzer.getRadi_u();
			if(restoran == null){
				System.out.println("menadzer: restoran==null");
				return "";	
			}
		} else {
			// Zaposleni
			Zaposleni zap = zaposleniRepository.findByEmail(k.email);
			if(zap == null){
				System.out.println("zaposleni: zaposleni==null");
				return "";
			}
			restoran = zap.getRadi_u();
			if(restoran == null){
				System.out.println("zaposleni: restoran==null");
				return "";
			}
		}
		if(restoran.getRaspored()!=null){
			System.out.println("kraj: raspored != null");
			return restoran.getRaspored();
		}
		else {
			System.out.println("kraj: raspored == null");  // novi restoran vrati prazno
			return "";
		}
	}
	
	public String getRasporedById(int id){
		Restoran r = restoranRepostitory.findOne(id);
		if(r == null){
			return "";
		}else {
			return r.getRaspored();
		}
	}

}

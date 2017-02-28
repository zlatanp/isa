package com.example.service.korisniciImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.korisnici.Zaposleni;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.FactoryKorisnikDTO;
import com.example.dto.korisnici.ZaposleniDTO;
import com.example.repository.MenadzerRepository;
import com.example.repository.ZaposleniRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class ZaposlenService {

	@Autowired
	private ZaposleniRepository zaposleniRepository;
	
	@Autowired
	private MenadzerRepository menadzerRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	public ZaposleniDTO findByEmailEndPassword(String email, String password){
		ZaposleniDTO z = (ZaposleniDTO) FactoryKorisnikDTO.makeKorisnikDTO(zaposleniRepository.findByEmailAndPassword(email, password));
		return z;
	}
	
	public ZaposleniDTO findByEmail(String email){
		ZaposleniDTO z = (ZaposleniDTO) FactoryKorisnikDTO.makeKorisnikDTO(zaposleniRepository.findByEmail(email));
		return z;
	}
	
	public List<ZaposleniDTO> nadjiZaposleneZaRestoran(String email){
		MenadzerRestorana m = menadzerRepository.findByEmail(email);
		if(m == null){
			return null;
		}
		Restoran restoran = m.getRadi_u();
		if(restoran == null){
			return null;
		}
		List<ZaposleniDTO> retVal = new ArrayList<ZaposleniDTO>();	
		for(Zaposleni z : restoran.getZaposleni()){
			ZaposleniDTO zap = (ZaposleniDTO) FactoryKorisnikDTO.makeKorisnikDTO(z);
			retVal.add(zap);
		}
		
		return retVal;
	}
	
	public List<ZaposleniDTO> findByParameters(String ime, String prezime, int idRest){
		List<ZaposleniDTO> filter = new ArrayList<ZaposleniDTO>();
		Restoran r = restoranRepository.findOne(idRest);
		if(r == null){
			return null;
		}
		List<Zaposleni> sviZaposleni = zaposleniRepository.findAll();
		for(Zaposleni z : sviZaposleni){
			if(z.getRadi_u().equals(r)){
				if(prezime.equals("")){
					if(z.getIme().toLowerCase().contains(ime.toLowerCase())){
						filter.add((ZaposleniDTO) FactoryKorisnikDTO.makeKorisnikDTO(z));
					}
				}else {
					if(z.getIme().toLowerCase().contains(ime.toLowerCase()) || z.getPrezime().toLowerCase().contains(prezime.toLowerCase())){
						filter.add((ZaposleniDTO) FactoryKorisnikDTO.makeKorisnikDTO(z));
					}
				}
			}
		}
		return filter;
	}
}

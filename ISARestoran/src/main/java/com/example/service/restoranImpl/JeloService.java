package com.example.service.restoranImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Cena;
import com.example.beans.restoran.Jelo;
import com.example.beans.restoran.Restoran;
import com.example.dto.restoran.JeloDTO;
import com.example.enums.KlasaJela;
import com.example.enums.TipJela;
import com.example.repository.MenadzerRepository;
import com.example.repository.restoran.JeloRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class JeloService {
	
	@Autowired
	JeloRepository jeloRepository;
	
	@Autowired
	RestoranRepository restoranRepository;
	
	@Autowired
	MenadzerRepository menadzerRepository;
	
	private static final String default_jelo_path = "default_jelo.jpg";
	
	
	public JeloDTO findById(int id){
		Jelo j = jeloRepository.findById(id);
		if(j == null)
			return null;
		return new JeloDTO(j, null);
	}
	
	public List<JeloDTO> findAll(){
		List<Jelo> jela = jeloRepository.findAll();
		List<JeloDTO> jelaDTO = new ArrayList<>();
		if(jela == null)
			return null;
		for(Jelo j : jela){
			if(!j.isObrisano()){
				jelaDTO.add(new JeloDTO(j, null));
			}
		}
		return jelaDTO;
	}
	
	public List<JeloDTO> findAll(int id){
		Restoran r = restoranRepository.findOne(id);
		if(r == null)
			return null;
		List<Jelo> jela = jeloRepository.findByRestoran(r);
		List<JeloDTO> jelaDTO = new ArrayList<>();
		if(jela == null){
			return null;
		}else {
			for(Jelo j : jela){
				if(!j.isObrisano()){
					jelaDTO.add(new JeloDTO(j, null));
				}
			}
		}
		return jelaDTO;
	}
	
	public JeloDTO create(JeloDTO jelo, String email){
		MenadzerRestorana men = menadzerRepository.findByEmail(email);
		if(men == null)
			return null;
		Restoran r = men.getRadi_u();
		if(r == null)
			return null;
		Date datum = new Date();
		Cena cena = new Cena(jelo.getCena(), datum, null);
		TipJela tip = jelo.tipJela;
		KlasaJela klasa = jelo.klasaJela;
		Jelo jeloZaBazu = new Jelo(r, jelo.getNaziv(), jelo.getOpis(), cena, tip, klasa);
		if(jelo.slika.equals("")){
			jeloZaBazu.setSlika(default_jelo_path);
		}
		jeloZaBazu = jeloRepository.save(jeloZaBazu);
		return new JeloDTO(jeloZaBazu, null);		
	}
}

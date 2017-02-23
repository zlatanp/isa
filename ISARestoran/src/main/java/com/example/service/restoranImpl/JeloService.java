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
		if(men == null){
			System.out.println("JaSam1");
			return null;
		}
		Restoran r = men.getRadi_u();
		if(r == null){
			System.out.println("JaSam2");
			return null;
		}
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
	
	public void namestiSliku(int id, String slika){
		Jelo jelo = jeloRepository.findById(id);
		if(jelo == null)
			return;
		jelo.setSlika(slika);
		jeloRepository.save(jelo);
	}
	
	public JeloDTO update(JeloDTO jelo){
		Jelo j = jeloRepository.findById(jelo.id);
		if(j == null){
			System.out.println("ovde");
		}
		if(j.trenutnaCena().getIznos() != jelo.cena){
			Date date = new Date();
			Cena cena = new Cena(jelo.getCena(), date, null);
			for(Cena c : j.getCene()){
				if(c.getDatumDo() == null){
					c.setDatumDo(date);
				}
			}
			j.getCene().add(cena);
		}
		j.setNaziv(jelo.naziv);
		j.setOpis(jelo.opis);
		j.setKlasa(jelo.klasaJela);
		j.setTip(jelo.tipJela);
		j = jeloRepository.save(j);
		return new JeloDTO(j, null);
	}
	
	public boolean delete(JeloDTO jelo){
		Jelo jeloIzBaze = jeloRepository.findById(jelo.id);
		if(jeloIzBaze == null)
			return false;
		jeloIzBaze.setObrisano(true);
		jeloRepository.save(jeloIzBaze);
		return true;
	}
}

package com.example.service.korisniciImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Konobar;
import com.example.beans.restoran.Ocena;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.KonobarDTO;
import com.example.dto.restoran.OcenaDTO;
import com.example.repository.KonobarRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class KonobarService {

	@Autowired
	private KonobarRepository konobarRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	public Konobar create(KonobarDTO konobarDTO){
		Restoran restoran = restoranRepository.findOne(konobarDTO.getRadi_u());
		if(restoran == null){
			return null;
		}
		Konobar noviKonobar = new Konobar(konobarDTO.getIme(), konobarDTO.getPrezime(), konobarDTO.getEmail(), konobarDTO.getPassword(), konobarDTO.getTip(), 
				konobarDTO.getDatum_rodjenja(), konobarDTO.getKonfekcijski_broj(), konobarDTO.getVelicina_obuce(), restoran);
		
		noviKonobar = konobarRepository.save(noviKonobar);
		return noviKonobar;	
	}
	
	public KonobarDTO findByEmail(String email){
		KonobarDTO k = new KonobarDTO(konobarRepository.findByEmail(email));
		return k;
	}
	
	public KonobarDTO findByEmailAndPassword(String email, String password){
		KonobarDTO k = new KonobarDTO(konobarRepository.findByEmailAndPassword(email, password));
		return k;
	}
	
	public List<KonobarDTO> findAll(){
		List<Konobar> listaKonobara = konobarRepository.findAll();
		List<KonobarDTO> konobariDTO = new ArrayList<KonobarDTO>();
		if(listaKonobara == null)
			return null;
		for(Konobar k : listaKonobara){
			konobariDTO.add(new KonobarDTO(k));
		}
		
		return konobariDTO;
	}
	
	public List<OcenaDTO> dobaviOceneZaKonobara(String email){
		Konobar k = konobarRepository.findByEmail(email);
		if(k == null){
			return null;
		}
		Restoran r = k.getRadi_u();
		if(r == null){
			return null;
		}
		List<OcenaDTO> oceneDTO = new ArrayList<OcenaDTO>();
		for(Ocena o : r.getOcene()){
			if(o.getKonobar().equals(k)){
				oceneDTO.add(new OcenaDTO(o));
			}
		}
		
		return oceneDTO;
	}
}

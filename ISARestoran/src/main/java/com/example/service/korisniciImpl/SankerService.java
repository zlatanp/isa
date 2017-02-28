package com.example.service.korisniciImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Sanker;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.SankerDTO;
import com.example.repository.SankerRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class SankerService {

	
	@Autowired
	private SankerRepository sankerRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	
	public Sanker create(SankerDTO konobarDTO){
		Restoran restoran = restoranRepository.findOne(konobarDTO.getRadi_u());
		if(restoran == null){
			return null;
		}
		Sanker noviSanker = new Sanker(konobarDTO.getIme(), konobarDTO.getPrezime(), konobarDTO.getEmail(), konobarDTO.getPassword(), konobarDTO.getTip(), 
				konobarDTO.getDatum_rodjenja(), konobarDTO.getKonfekcijski_broj(), konobarDTO.getVelicina_obuce(), restoran);
		
		noviSanker = sankerRepository.save(noviSanker);
		return noviSanker;	
	}
	
	public SankerDTO findByEmail(String email){
		SankerDTO k = new SankerDTO(sankerRepository.findByEmail(email));
		return k;
	}
	
	public SankerDTO findByEmailAndPassword(String email, String password){
		SankerDTO k = new SankerDTO(sankerRepository.findByEmailAndPassword(email, password));
		return k;
	}
	
	public List<SankerDTO> findAll(){
		List<Sanker> listaKonobara = sankerRepository.findAll();
		List<SankerDTO> sankeriDTO = new ArrayList<SankerDTO>();
		if(listaKonobara == null)
			return null;
		for(Sanker k : listaKonobara){
			sankeriDTO.add(new SankerDTO(k));
		}
		
		return sankeriDTO;
	}
}

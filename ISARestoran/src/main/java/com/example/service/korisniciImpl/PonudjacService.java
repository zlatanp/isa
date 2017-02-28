package com.example.service.korisniciImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Ponudjac;
import com.example.dto.korisnici.PonudjacDTO;
import com.example.repository.PonudjacRepository;

@Service
public class PonudjacService {

	@Autowired
	private PonudjacRepository ponudjacRepository;
	
	public Ponudjac create(PonudjacDTO ponudjacDTO){
		Ponudjac zaBazu = new Ponudjac(ponudjacDTO.ime, ponudjacDTO.prezime, ponudjacDTO.email, ponudjacDTO.password, ponudjacDTO.tip);
		zaBazu = ponudjacRepository.save(zaBazu);
		return zaBazu;
	}
	
	public PonudjacDTO findByEmail(String email){
		PonudjacDTO retPonudjac = new PonudjacDTO(ponudjacRepository.findByEmail(email));
		return retPonudjac;
	}
	
	public PonudjacDTO findByEmailAndPassword(String email, String password){
		PonudjacDTO retPonudjac = new PonudjacDTO(ponudjacRepository.findByEmailAndPassword(email, password));
		return retPonudjac;
	}
}

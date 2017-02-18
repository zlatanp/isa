package com.example.service.korisniciImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Prijateljstvo;
import com.example.repository.PrijateljstvoRepository;
import com.example.service.PrijateljstvoService;

@Service
public class PrijateljstvoServiceImpl implements PrijateljstvoService{

	private PrijateljstvoRepository prijateljstvoRepository;
	
	@Autowired
	public void setPrijateljstvoRepository(PrijateljstvoRepository prijateljstvoRepo) {
		this.prijateljstvoRepository = prijateljstvoRepo;
	}
	
	@Override
	public Iterable<Prijateljstvo> getAllPrijateljstva() {
		return this.prijateljstvoRepository.findAll();
	}

	@Override
	public Prijateljstvo getPrijateljstvoById(Integer id) {
		return this.prijateljstvoRepository.findOne(id);
	}

	@Override
	public Prijateljstvo savePrijateljstvo(Prijateljstvo contact) {
		return this.prijateljstvoRepository.save(contact);
	}

	@Override
	public void deletePrijateljstvo(Integer id) {
		this.prijateljstvoRepository.delete(id);
		
	}

}

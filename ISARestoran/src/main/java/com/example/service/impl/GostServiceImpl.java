package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Gost;
import com.example.repository.GostRepository;
import com.example.service.GostService;

@Service
public class GostServiceImpl implements GostService{

	
	private GostRepository gostRepository;
	
	 @Autowired
	    public void setGuestRepository(GostRepository guestRepo) {
	        this.gostRepository = guestRepo;
	    } 
	 
	@Override
	public Iterable<Gost> getAllGosti() {
		return this.gostRepository.findAll();
	}

	@Override
	public Gost getGostById(Integer id) {
		return this.gostRepository.findOne(id);
	}

	@Override
	public Gost saveGost(Gost contact) {
		return this.gostRepository.save(contact);
	}

	@Override
	public void deleteGost(Integer id) {
		this.gostRepository.delete(id);
	}

}

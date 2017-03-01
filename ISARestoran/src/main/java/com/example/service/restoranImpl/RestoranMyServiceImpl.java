package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Restoran;
import com.example.repository.restoran.RestoranMyRepository;
import com.example.service.RestoranMyService;

@Service
public class RestoranMyServiceImpl implements RestoranMyService{

	private RestoranMyRepository restoranrep;

	@Autowired
	public void setGuestRepository(RestoranMyRepository restRepo) {
		this.restoranrep = restRepo;
	}

	@Override
	public Iterable<Restoran> getAllRestoran() {
		return this.restoranrep.findAll();
	}

	@Override
	public Restoran getRestoranById(Integer id) {
		return this.restoranrep.findOne(id);
	}

	@Override
	public Restoran saveRestoran(Restoran contact) {
		return this.restoranrep.save(contact);
	}

	@Override
	public void deleteRestoran(Integer id) {
		this.restoranrep.delete(id);
		
	}
	
	

}

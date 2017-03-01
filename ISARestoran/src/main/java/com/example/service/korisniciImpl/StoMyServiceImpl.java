package com.example.service.korisniciImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Sto;
import com.example.repository.restoran.StoMyRepository;
import com.example.service.StoMyService;

@Service
public class StoMyServiceImpl implements StoMyService{
	
	private StoMyRepository stoRepo;

	@Autowired
	public void setStoMyRepository(StoMyRepository stoRepo) {
		this.stoRepo = stoRepo;
	}

	@Override
	public Iterable<Sto> getAllStolovi() {
		return this.stoRepo.findAll();
	}

	@Override
	public Sto getRestoranById(Integer id) {
		return this.stoRepo.findOne(id);
	}

	@Override
	public Sto saveRestoran(Sto contact) {
		return this.stoRepo.save(contact);
	}

	@Override
	public void deleteSto(Integer id) {
		this.stoRepo.delete(id);
		
	}

}

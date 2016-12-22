	package com.example.service;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Guest;
import com.example.repository.GuestRepository;

@Service	
public class GuestServiceImpl implements GuestService {

	private GuestRepository guestRepo;
	
	 @Autowired
	    public void setGuestRepository(GuestRepository guestRepo) {
	        this.guestRepo = guestRepo;
	    }
	
	@Override
	public Iterable<Guest> getAllGuests() {
		return this.guestRepo.findAll();
	}

	@Override
	public Guest getGuestById(Long id) {
		return this.guestRepo.findOne(id);
	}

	@Override
	public Guest saveGuest(Guest contact) {
		return this.guestRepo.save(contact);
	}

	@Override
	public void deleteGuest(Long id) {
		this.guestRepo.delete(id);
	}

}

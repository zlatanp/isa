package com.example.service;


import com.example.model.Guest;

public interface GuestService {

	 Iterable<Guest> getAllGuests();
	 Guest getGuestById(Long id);
	 Guest saveGuest(Guest contact);
	 void deleteGuest(Long id);
}

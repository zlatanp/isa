package com.example.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Guest;
import com.example.service.GuestService;



/*
 * prepravi da umesto guestcontroller bude usercontroller, svi mogu da se loguju, da ne bilo
 * redudantnog koda
 * 
 * */



@RestController
@RequestMapping("/gostkontroler")
public class GuestController {
	
	@Autowired
	private GuestService guestService;

	
    
	
	@RequestMapping(value = "/vrati")
	public synchronized String vrati(@ModelAttribute("username") String email, @ModelAttribute("password") String password){
		String rezultat = "";
		
		if (email.isEmpty() || password.isEmpty()) {
			
			rezultat = "F";	//nema ga u bazi
		}
		
		Iterable<Guest> listagostiju = guestService.getAllGuests();
		ArrayList<Guest> list = new ArrayList<Guest>();
	    for (Guest item : listagostiju){
	        list.add(item);
	    }
	    
	    
	    for(int i=0;i<list.size();i++){
	    	if(list.get(i).getEmail().equals(email)){
	    		if(list.get(i).getPassword().equals(password))
	    			rezultat = "T"; //ima ga u bazi
	    	}
	    }
	    if(rezultat=="")
	    	rezultat = "F";
	    
		return rezultat;
	}
}

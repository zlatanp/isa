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
@RequestMapping("/gost")
public class GuestController {
	
	@Autowired
	private GuestService guestService;

	
    
	
	@RequestMapping(value = "/login")
	public synchronized String login(@ModelAttribute("username") String email, @ModelAttribute("password") String password){
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
	
	@RequestMapping(value = "/register")
	public synchronized String register(@ModelAttribute("name") String name, @ModelAttribute("email") String email,  @ModelAttribute("password") String password){
		String rezultat = "";
		
		if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
			
			rezultat = "F";	//nema ga u bazi
		}
		
		Guest gost = new Guest(name, "", password, email);
		guestService.saveGuest(gost);
	   
	    
		return rezultat;
	}
	
	@RequestMapping(value = "/obrisi")
	public synchronized String obrisi(){
		guestService.deleteGuest((long) 2);
		return "obrisao";
	}
}

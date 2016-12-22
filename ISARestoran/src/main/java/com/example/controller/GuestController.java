package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
	public synchronized void login(HttpServletResponse httpServletResponse, @ModelAttribute("username") String email, @ModelAttribute("password") String password) throws IOException{
		boolean uspesno = false;
		
		if (email.isEmpty() || password.isEmpty()) {
			
			uspesno = false;	//nema ga u bazi
		}else{
		
		Iterable<Guest> listagostiju = guestService.getAllGuests();
		ArrayList<Guest> list = new ArrayList<Guest>();
	    for (Guest item : listagostiju){
	        list.add(item);
	    }
	    
	    
	    for(int i=0;i<list.size();i++){
	    	if(list.get(i).getEmail().equals(email)){
	    		if(list.get(i).getPassword().equals(password))
	    			uspesno = true; //ima ga u bazi
	    	}
	    }
		}
	    
		if(uspesno){
			httpServletResponse.sendRedirect("/restoran.html");
			}else{
			httpServletResponse.sendRedirect("/home.html");	
			}
	}
	
	@RequestMapping(value = "/register")
	public synchronized void register(HttpServletResponse httpServletResponse, @ModelAttribute("name") String name, @ModelAttribute("email") String email,  @ModelAttribute("password") String password) throws IOException{
		boolean uspesno = false;
		
		if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
			
			uspesno = false;	//nema ga u bazi
		}else{
		
		Guest gost = new Guest(name, "", password, email);
		guestService.saveGuest(gost);
	   
		uspesno = true;
		}
		
		
		httpServletResponse.sendRedirect("/home.html");
	}
	
	@RequestMapping(value = "/obrisi")
	public synchronized String obrisi(){
		guestService.deleteGuest((long) 2);
		return "obrisao";
	}
}

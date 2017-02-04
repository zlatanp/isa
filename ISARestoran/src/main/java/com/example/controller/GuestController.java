package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.enums.TipKorisnika;
import com.example.service.GostService;
import com.example.service.KorisnikService;



/*
 * prepravi da umesto guestcontroller bude usercontroller, svi mogu da se loguju, da ne bilo
 * redudantnog koda
 * 
 * */



@RestController
@RequestMapping("/gost")
public class GuestController {
	
	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private GostService gostService;
    
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void login(HttpServletResponse httpServletResponse, @ModelAttribute("email") String email, @ModelAttribute("password") String password) throws IOException{
		boolean postoji = false;
		System.out.println("loguje se: " + email + " " + password);
		
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		ArrayList<Korisnik> list = new ArrayList<Korisnik>();
		for (Korisnik item : listaKorisnika){
	        list.add(item);
	    }
	    
	    for(int i=0;i<list.size();i++){
	    	if(list.get(i).getEmail().equals(email)){
	    		if(list.get(i).getPassword().equals(password)){
	    			 System.out.println("imaga");
	    			 postoji = true;
	    		}
	    	}
	    }
	    
	    if(postoji){
	    	httpServletResponse.sendRedirect("/home.html");
	    }else{
	    	httpServletResponse.sendRedirect("/index.html");
	    }
		}
	
	//public synchronized void login(@RequestParam("email") String email, @RequestParam("password") String password) throws IOException{

	
	
	@RequestMapping(value = "/register")
	public synchronized void register(HttpServletResponse httpServletResponse, @ModelAttribute("name") String name, @ModelAttribute("lastname") String lastname,
			@ModelAttribute("email") String email,  @ModelAttribute("password") String password) throws IOException{
			
		if (!(name.isEmpty() || email.isEmpty() || password.isEmpty())) {				
			Gost gost = new Gost(name, lastname, email, password, TipKorisnika.GOST);
			korisnikService.saveKorisnik(gost);	
			gostService.saveGost(gost);
		}
				
		httpServletResponse.sendRedirect("/home.html");
	}
	
	@RequestMapping(value = "/obrisi")
	public synchronized String obrisi(){
		korisnikService.deleteKorisnik(2);
		return "obrisao";
	}
}

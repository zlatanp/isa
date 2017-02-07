package com.example.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.korisnici.Korisnik;
import com.example.service.KorisnikService;

@RestController
@RequestMapping("/profile")
public class ProfilController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value = "/gost", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized Korisnik mojProfil(@RequestParam("email") String email){
		Korisnik k = new Korisnik();
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		for (Korisnik item : listaKorisnika){
	        if(item.getEmail().equals(email))
	        	k = item;
	    }
		
		return k;
	}

}

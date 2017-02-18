package com.example.controller;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.Prijateljstvo;
import com.example.enums.FriendshipStatus;
import com.example.service.GostService;
import com.example.service.KorisnikService;
import com.example.service.PrijateljstvoService;

@RestController
@RequestMapping("/profile")
public class ProfilController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private PrijateljstvoService prijateljstvoService;
	
	@Autowired
	GostService gostService;
	
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
	
	@RequestMapping(value = "/edit", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized Korisnik izmeniProfil(@RequestParam("ime") String ime, @RequestParam("prezime") String prezime, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("password1") String password1){
		
		
		Korisnik k = new Korisnik();
		
		if(ime.isEmpty() || prezime.isEmpty() || email.isEmpty() || password.isEmpty() || password1.isEmpty()){
			return k;
		}else if(!password.equals(password1)){
			return k;
		}else{
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			for (Korisnik item : listaKorisnika){
				System.out.println(item.getEmail());
		        if(item.getEmail().equals(email)){
		        	item.setIme(ime);
					item.setPrezime(prezime);
					item.setPassword(password);
					korisnikService.saveKorisnik(item);
					return item;
		        }
		    }
			return k;
		}
		
	}
	
	@RequestMapping(value = "/trazi", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> traziPrijatelje(@RequestParam("kogaTrazim") String kogaTrazim){
		
		System.out.println(kogaTrazim);
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		if(kogaTrazim.isEmpty()){
			return korisnici;
		}else{
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			for (Korisnik item : listaKorisnika){
		        if(item.getIme().contains(kogaTrazim) || item.getPrezime().contains(kogaTrazim)){
		        	korisnici.add(item);
		        }
		    }
			return korisnici;
		}
		
	}
	
	@RequestMapping(value = "/dodajPrijatelja", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void dodajPrijatelja(@RequestParam("mojEmail") String mojEmail, @RequestParam("prijateljevEmail") String prijateljEmail){
		
		System.out.println(mojEmail + prijateljEmail);
		
		Gost ja = null;
		Gost mojPrijatelj = null;
//		
//		
//		if(mojEmail.contains("@") && prijateljEmail.contains("@")){
//		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
//		for (Korisnik item : listaKorisnika){
//	        if(item.getEmail().contains(mojEmail)){
//	        	ja = new Gost(item.getIme(), item.getPrezime(), item.getEmail(), item.getPassword(), item.getTipKorisnika());
//	        }else if(item.getEmail().contains(prijateljEmail)){
//	        	mojPrijatelj = new Gost(item.getIme(), item.getPrezime(), item.getEmail(), item.getPassword(), item.getTipKorisnika());
//	        }
//	    }
//		Prijateljstvo p = new Prijateljstvo(ja, mojPrijatelj, FriendshipStatus.U_PROCEDURI);
//		prijateljstvoService.savePrijateljstvo(p);
//		}
//		
		Iterable<Gost> listaGostiju = gostService.getAllGosti();
		for(Gost item : listaGostiju){
			if(item.getEmail().equals(mojEmail))
				ja = item;
			if(item.getEmail().equals(prijateljEmail))
				mojPrijatelj = item;
		}
		
		Prijateljstvo p = new Prijateljstvo(ja, mojPrijatelj, FriendshipStatus.U_PROCEDURI);
		prijateljstvoService.savePrijateljstvo(p);
		
	}


}

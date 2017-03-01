package com.example.controller.restoran;



import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.MenadzerRestorana;
import com.example.dto.hellpers.RadniciSmena;
import com.example.dto.restoran.SmenaDTO;
import com.example.enums.TipKorisnika;
import com.example.sekjuriti.LoggedUser;
import com.example.service.korisniciImpl.MenadzerService;
import com.example.service.restoranImpl.RadiUSmeniService;
import com.example.service.restoranImpl.SmenaService;

@RestController
@RequestMapping("/smene")
public class SmeneController {

	@Autowired
	private SmenaService smenaService;
	
	@Autowired
	private RadiUSmeniService radiUSmeniService;
	
	@Autowired
	private MenadzerService menadzerService;
	
	
	@RequestMapping(value="/novaSmena", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean novaSmena(@RequestBody @Valid SmenaDTO smenaDTO){
		LoggedUser u = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Korisnik k = u.getKorisnik();
		if(k.getTipKorisnika().equals(TipKorisnika.MENADZERRESTORANA)){
			MenadzerRestorana mr = menadzerService.findById(k.getId());
			return smenaService.create(smenaDTO, mr.getRadi_u().getId());	
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/getAllSmene", method= RequestMethod.GET, produces="application/json")
	public List<SmenaDTO> getSmene(){
		LoggedUser u = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Korisnik k = u.getKorisnik();
		if(k.getTipKorisnika().equals(TipKorisnika.MENADZERRESTORANA)){
			MenadzerRestorana mr = menadzerService.findById(k.getId());
			return smenaService.findAll(mr.getRadi_u().getId());	
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/smenaZaKonobara/{id}", method=RequestMethod.GET, produces="application/json")
	public boolean smenaZaKonobara(@PathVariable("id") int idKonobara){
		return radiUSmeniService.smenaJeKonobarova(idKonobara);
	}
	
	@RequestMapping(value="/dobaviSmeneText", method=RequestMethod.POST, produces="application/json", consumes="text/plain")
	public List<RadniciSmena> dobaviSmene(@RequestBody String month){
		LoggedUser u = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Korisnik k = u.getKorisnik();
		if(k.getTipKorisnika().equals(TipKorisnika.MENADZERRESTORANA)){
			MenadzerRestorana mr = menadzerService.findById(k.getId());
			return radiUSmeniService.getByMonth(month, mr.getRadi_u().getId());
		}else {
			return null;
		}
	}
	
}

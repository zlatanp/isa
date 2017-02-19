package com.example.controller;

import java.util.ArrayList;

import org.hibernate.validator.constraints.Email;
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

	@RequestMapping(value = "/gost", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized Korisnik mojProfil(@RequestParam("email") String email) {
		Korisnik k = new Korisnik();
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		for (Korisnik item : listaKorisnika) {
			if (item.getEmail().equals(email))
				k = item;
		}

		return k;
	}

	@RequestMapping(value = "/edit", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized Korisnik izmeniProfil(@RequestParam("ime") String ime, @RequestParam("prezime") String prezime,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("password1") String password1) {

		Korisnik k = new Korisnik();

		if (ime.isEmpty() || prezime.isEmpty() || email.isEmpty() || password.isEmpty() || password1.isEmpty()) {
			return k;
		} else if (!password.equals(password1)) {
			return k;
		} else {
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			for (Korisnik item : listaKorisnika) {
				System.out.println(item.getEmail());
				if (item.getEmail().equals(email)) {
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

	@RequestMapping(value = "/trazi", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> traziPrijatelje(@RequestParam("kogaTrazim") String kogaTrazim,
			@RequestParam("email") String mojEmail) {

		System.out.println(kogaTrazim + "mojemail: " + mojEmail);
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();

		Iterable<Prijateljstvo> pr = prijateljstvoService.getAllPrijateljstva();

		if (kogaTrazim.isEmpty()) {
			return korisnici;
		} else {
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			for (Korisnik item : listaKorisnika) {
				if (item.getIme().contains(kogaTrazim) || item.getPrezime().contains(kogaTrazim)) {
					if (!item.getEmail().equals(mojEmail)) {
						korisnici.add(item);
					}
				}
			}

			for (Prijateljstvo p : pr) {
				for (int i = 0; i < korisnici.size(); i++) {
					if((p.getJa().getEmail().equals(mojEmail) || p.getMojprijatelj().getEmail().equals(mojEmail)) && (p.getMojprijatelj().getEmail().equals(korisnici.get(i).getEmail()) || p.getJa().getEmail().equals(korisnici.get(i).getEmail()))){
						if(p.getStatus().equals(FriendshipStatus.PRIJATELJI) || p.getStatus().equals(FriendshipStatus.U_PROCEDURI)){
						korisnici.remove(i);
						}
					}
				}
			}

			return korisnici;
		}

	}

	@RequestMapping(value = "/dodajPrijatelja", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void dodajPrijatelja(@RequestParam("mojEmail") String mojEmail,
			@RequestParam("prijateljevEmail") String prijateljEmail) {

		System.out.println(mojEmail + prijateljEmail);

		Gost ja = null;
		Gost mojPrijatelj = null;

		Iterable<Gost> listaGostiju = gostService.getAllGosti();
		for (Gost item : listaGostiju) {
			if (item.getEmail().equals(mojEmail))
				ja = item;
			if (item.getEmail().equals(prijateljEmail))
				mojPrijatelj = item;
		}

		Prijateljstvo p = new Prijateljstvo(ja, mojPrijatelj, FriendshipStatus.U_PROCEDURI);
		prijateljstvoService.savePrijateljstvo(p);

	}

	@RequestMapping(value = "/dajNotifikaciju", method = { RequestMethod.GET })
	public synchronized boolean dajNotifikaciju(@RequestParam("mojEmail") String mojEmail) {

		System.out.println(mojEmail);
		boolean imaNotifikaciju = false;

		Iterable<Prijateljstvo> svaPrijateljstva = prijateljstvoService.getAllPrijateljstva();
		if (svaPrijateljstva != null) {
			for (Prijateljstvo p : svaPrijateljstva) {
				if (p.getMojprijatelj().getEmail().equals(mojEmail)
						&& p.getStatus().equals(FriendshipStatus.U_PROCEDURI)) {
					imaNotifikaciju = true;
				}
			}
		}
		return imaNotifikaciju;
	}

	@RequestMapping(value = "/dajLjude", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> dajLjudeKojiMeDodali(@RequestParam("email") String mojEmail) {

		System.out.println(mojEmail);

		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();

		Iterable<Prijateljstvo> svaPrijateljstva = prijateljstvoService.getAllPrijateljstva();
		if (svaPrijateljstva != null) {
			for (Prijateljstvo p : svaPrijateljstva) {
				if (p.getMojprijatelj().getEmail().equals(mojEmail)
						&& p.getStatus().equals(FriendshipStatus.U_PROCEDURI)) {
					korisnici.add(p.getJa());
				}
			}
		}

		return korisnici;
	}

	@RequestMapping(value = "/odbaciPrijatelja", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void odbaciPrijatelja(@RequestParam("email") String mojEmail,
			@RequestParam("kogaOdbacujem") String kogaOdbacujem) {

		System.out.println(mojEmail);

		Iterable<Prijateljstvo> svaPrijateljstva = prijateljstvoService.getAllPrijateljstva();
		if (svaPrijateljstva != null) {
			for (Prijateljstvo p : svaPrijateljstva) {
				if (p.getMojprijatelj().getEmail().equals(mojEmail) && p.getJa().getEmail().equals(kogaOdbacujem)
						&& p.getStatus().equals(FriendshipStatus.U_PROCEDURI)) {
					p.setStatus(FriendshipStatus.NISU_PRIJATELJI);
					prijateljstvoService.savePrijateljstvo(p);
					break;
				}
			}
		}

	}

}

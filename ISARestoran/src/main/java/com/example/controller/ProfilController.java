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

	@RequestMapping(value = "/gost", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized Korisnik profil(@RequestParam("email") String email) {
		Korisnik k = new Korisnik();
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();

		for (Korisnik item : listaKorisnika) {
			if (item.getEmail().equals(email))
				
				k = item;
		}
		return k;

	}

	@RequestMapping(value = "/svimojiprijateljibisurirasuti", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> svimojiprijateljibisurirasuti(@RequestParam("email") String email) {
		ArrayList<Korisnik> prijateljiMoji = new ArrayList<Korisnik>();

		Iterable<Prijateljstvo> pr = prijateljstvoService.getAllPrijateljstva();
		for (Prijateljstvo p : pr) {
			if (p.getJa().getEmail().equals(email) && p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getMojprijatelj());
			} else if (p.getMojprijatelj().getEmail().equals(email)
					&& p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getJa());
			}
		}

		return prijateljiMoji;
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

		
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		String realText = kogaTrazim.toLowerCase();

		Iterable<Prijateljstvo> pr = prijateljstvoService.getAllPrijateljstva();

		if (kogaTrazim.isEmpty()) {
			return korisnici;
		} else {
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			for (Korisnik item : listaKorisnika) {
				if (item.getIme().toLowerCase().contains(realText) || item.getPrezime().toLowerCase().contains(realText)) {
					if (!item.getEmail().equals(mojEmail)) {
						korisnici.add(item);
					}
				}
			}

			for (Prijateljstvo p : pr) {
				for (int i = 0; i < korisnici.size(); i++) {
					if ((p.getJa().getEmail().equals(mojEmail) || p.getMojprijatelj().getEmail().equals(mojEmail))
							&& (p.getMojprijatelj().getEmail().equals(korisnici.get(i).getEmail())
									|| p.getJa().getEmail().equals(korisnici.get(i).getEmail()))) {
						if (p.getStatus().equals(FriendshipStatus.PRIJATELJI)
								|| p.getStatus().equals(FriendshipStatus.U_PROCEDURI)) {
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

		

		Iterable<Prijateljstvo> svaPrijateljstva = prijateljstvoService.getAllPrijateljstva();
		if (svaPrijateljstva != null) {
			for (Prijateljstvo p : svaPrijateljstva) {
				if ((p.getMojprijatelj().getEmail().equals(mojEmail) && p.getJa().getEmail().equals(kogaOdbacujem))
						|| (p.getMojprijatelj().getEmail().equals(kogaOdbacujem)
								&& p.getJa().getEmail().equals(mojEmail))) {
					p.setStatus(FriendshipStatus.NISU_PRIJATELJI);
					prijateljstvoService.savePrijateljstvo(p);
					break;
				}
			}
		}

	}

	@RequestMapping(value = "/prihvatiPrijatelja", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void prihvatiPrijatelja(@RequestParam("mojEmail") String mojEmail,
			@RequestParam("kogaDodajem") String kogaOdbacujem) {

		

		Iterable<Prijateljstvo> svaPrijateljstva = prijateljstvoService.getAllPrijateljstva();
		if (svaPrijateljstva != null) {
			for (Prijateljstvo p : svaPrijateljstva) {
				if (p.getMojprijatelj().getEmail().equals(mojEmail) && p.getJa().getEmail().equals(kogaOdbacujem)
						&& p.getStatus().equals(FriendshipStatus.U_PROCEDURI)) {
					p.setStatus(FriendshipStatus.PRIJATELJI);
					prijateljstvoService.savePrijateljstvo(p);
					break;
				}
			}
		}

	}

	@RequestMapping(value = "/sortPoImenu", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> sortPoImenu(@RequestParam("email") String email) {
		ArrayList<Korisnik> prijateljiMoji = new ArrayList<Korisnik>();

		Iterable<Prijateljstvo> pr = prijateljstvoService.getAllPrijateljstva();
		for (Prijateljstvo p : pr) {
			if (p.getJa().getEmail().equals(email) && p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getMojprijatelj());
			} else if (p.getMojprijatelj().getEmail().equals(email)
					&& p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getJa());
			}
		}

		ArrayList<Korisnik> prijateljiSortiraniPoImenu = new ArrayList<Korisnik>();

		ArrayList<String> svaslova = new ArrayList<String>();
		svaslova.add("A");
		svaslova.add("a");
		svaslova.add("B");
		svaslova.add("b");
		svaslova.add("C");
		svaslova.add("c");
		svaslova.add("D");
		svaslova.add("d");
		svaslova.add("E");
		svaslova.add("e");
		svaslova.add("F");
		svaslova.add("f");
		svaslova.add("G");
		svaslova.add("g");
		svaslova.add("H");
		svaslova.add("h");
		svaslova.add("I");
		svaslova.add("i");
		svaslova.add("J");
		svaslova.add("j");
		svaslova.add("K");
		svaslova.add("k");
		svaslova.add("L");
		svaslova.add("l");
		svaslova.add("M");
		svaslova.add("m");
		svaslova.add("N");
		svaslova.add("n");
		svaslova.add("O");
		svaslova.add("o");
		svaslova.add("P");
		svaslova.add("p");
		svaslova.add("Q");
		svaslova.add("q");
		svaslova.add("R");
		svaslova.add("r");
		svaslova.add("S");
		svaslova.add("s");
		svaslova.add("T");
		svaslova.add("t");
		svaslova.add("U");
		svaslova.add("u");
		svaslova.add("V");
		svaslova.add("v");
		svaslova.add("W");
		svaslova.add("w");
		svaslova.add("X");
		svaslova.add("x");
		svaslova.add("Y");
		svaslova.add("y");
		svaslova.add("Z");
		svaslova.add("z");

		for (int j = 0; j < svaslova.size(); j += 2) {
			for (int i = 0; i < prijateljiMoji.size(); i++) {
				if (prijateljiMoji.get(i).getIme().startsWith(svaslova.get(j))
						|| prijateljiMoji.get(i).getIme().startsWith(svaslova.get(j + 1))) {
					prijateljiSortiraniPoImenu.add(prijateljiMoji.get(i));

				}
			}
		}

		return prijateljiSortiraniPoImenu;
	}
	
	@RequestMapping(value = "/sortPoPrezimenu", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<Korisnik> sortPoPrezimenu(@RequestParam("email") String email) {
		ArrayList<Korisnik> prijateljiMoji = new ArrayList<Korisnik>();

		Iterable<Prijateljstvo> pr = prijateljstvoService.getAllPrijateljstva();
		for (Prijateljstvo p : pr) {
			if (p.getJa().getEmail().equals(email) && p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getMojprijatelj());
			} else if (p.getMojprijatelj().getEmail().equals(email)
					&& p.getStatus().equals(FriendshipStatus.PRIJATELJI)) {
				prijateljiMoji.add(p.getJa());
			}
		}

		ArrayList<Korisnik> prijateljiSortiraniPrezimen = new ArrayList<Korisnik>();

		ArrayList<String> svaslova = new ArrayList<String>();
		svaslova.add("A");
		svaslova.add("a");
		svaslova.add("B");
		svaslova.add("b");
		svaslova.add("C");
		svaslova.add("c");
		svaslova.add("D");
		svaslova.add("d");
		svaslova.add("E");
		svaslova.add("e");
		svaslova.add("F");
		svaslova.add("f");
		svaslova.add("G");
		svaslova.add("g");
		svaslova.add("H");
		svaslova.add("h");
		svaslova.add("I");
		svaslova.add("i");
		svaslova.add("J");
		svaslova.add("j");
		svaslova.add("K");
		svaslova.add("k");
		svaslova.add("L");
		svaslova.add("l");
		svaslova.add("M");
		svaslova.add("m");
		svaslova.add("N");
		svaslova.add("n");
		svaslova.add("O");
		svaslova.add("o");
		svaslova.add("P");
		svaslova.add("p");
		svaslova.add("Q");
		svaslova.add("q");
		svaslova.add("R");
		svaslova.add("r");
		svaslova.add("S");
		svaslova.add("s");
		svaslova.add("T");
		svaslova.add("t");
		svaslova.add("U");
		svaslova.add("u");
		svaslova.add("V");
		svaslova.add("v");
		svaslova.add("W");
		svaslova.add("w");
		svaslova.add("X");
		svaslova.add("x");
		svaslova.add("Y");
		svaslova.add("y");
		svaslova.add("Z");
		svaslova.add("z");

		
		
		for (int j = 0; j < svaslova.size(); j += 2) {
			for (int i = 0; i < prijateljiMoji.size(); i++) {
				if (prijateljiMoji.get(i).getPrezime().startsWith(svaslova.get(j))
						|| prijateljiMoji.get(i).getPrezime().startsWith(svaslova.get(j + 1))) {
					prijateljiSortiraniPrezimen.add(prijateljiMoji.get(i));

				}
			}
		}

		return prijateljiSortiraniPrezimen;
	}

}

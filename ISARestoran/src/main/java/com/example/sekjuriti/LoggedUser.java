package com.example.sekjuriti;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.security.SocialUser;

import com.example.beans.korisnici.Korisnik;
import com.example.dto.korisnici.KorisnikDTO;


public class LoggedUser  extends SocialUser{

private Korisnik korisnik;
	
	public LoggedUser(Korisnik korisnik) {
		super(korisnik.getEmail(), korisnik.getPassword(),  AuthorityUtils.createAuthorityList("ROLE_" + korisnik.getTipKorisnika().toString().toUpperCase()));
		this.korisnik= korisnik;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2446818140785053446L;
	
	public Korisnik getKorisnik(){
		return korisnik;
	}
}

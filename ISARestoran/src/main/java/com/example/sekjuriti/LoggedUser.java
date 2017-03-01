package com.example.sekjuriti;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.security.SocialUser;

import com.example.beans.korisnici.Korisnik;


public class LoggedUser  extends SocialUser{

	private Korisnik korisnik;
	
	public LoggedUser(Korisnik korisnikDTO) {
		super(korisnikDTO.getEmail(), korisnikDTO.getPassword(),  AuthorityUtils.createAuthorityList("ROLE_" + korisnikDTO.getTipKorisnika().toString().toUpperCase()));
		this.korisnik = korisnikDTO;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2446818140785053446L;
	
	public Korisnik Korisnik(){
		return korisnik;
	}
}

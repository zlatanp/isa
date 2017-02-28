package com.example.sekjuriti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beans.korisnici.Korisnik;
import com.example.service.korisniciImpl.KorisnikServiceImpl;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired 
	KorisnikServiceImpl korisnikService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Korisnik korisnik = null;
		
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();

		for (Korisnik k : listaKorisnika) {
			if (k.getEmail().equals(email)) {
					korisnik=k;
				}
			
		}
		
		if (korisnik == null){
			
			throw new UsernameNotFoundException("No user found with username: " + email); 
		}
		
		System.out.println("User details service :" + email);
		return new LoggedUser(korisnik);

	}

}

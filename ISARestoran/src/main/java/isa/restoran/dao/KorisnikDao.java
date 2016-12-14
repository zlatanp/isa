package isa.restoran.dao;

import isa.restoran.entity.Korisnik;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class KorisnikDao {
	
	private static Map<Integer,Korisnik> korisnici;
	
	static {
			korisnici = new HashMap<Integer, Korisnik>(){
				{	
					put(1,new Korisnik(1,"admin"));
					put(2,new Korisnik(2,"drugi"));
					put(3,new Korisnik(2,"treci"));
				}
			};
	}
	

	public Collection<Korisnik> getAllUsers(){
		return korisnici.values();
	}
}

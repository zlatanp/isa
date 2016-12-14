package isa.restoran.service;

import isa.restoran.dao.KorisnikDao;
import isa.restoran.entity.Korisnik;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KorisnikService {

	@Autowired
	private KorisnikDao korisnikDao;
	
	public Collection<Korisnik> getAllUsers(){
		return korisnikDao.getAllUsers();
	}
}

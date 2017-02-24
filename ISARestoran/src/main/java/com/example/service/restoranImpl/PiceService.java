package com.example.service.restoranImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Cena;
import com.example.beans.restoran.Pice;
import com.example.beans.restoran.Restoran;
import com.example.dto.restoran.PiceDTO;
import com.example.repository.MenadzerRepository;
import com.example.repository.restoran.PiceRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class PiceService {

	@Autowired
	PiceRepository piceRepository;
	
	@Autowired
	RestoranRepository restoranRepository;
	
	@Autowired
	MenadzerRepository menadzerRepository;
	
	private static final String default_pice_path = "defPice.jpg";
	
	public PiceDTO findById(int id){
		Pice pice = piceRepository.findById(id);
		if(pice == null)
			return null;
		return new PiceDTO(pice, null);
	}
	
	public List<PiceDTO> findAll(){
		List<Pice> pica = piceRepository.findAll();
		List<PiceDTO> retList = new ArrayList<PiceDTO>();
		if(pica == null)
			return null;
		for(Pice p : pica){
			if(!p.isObrisano()){
				retList.add(new PiceDTO(p, null));
			}
		}
		return retList;
	}
	
	public List<PiceDTO> findAll(int id){
		Restoran restoran = restoranRepository.findOne(id);
		if(restoran == null)
			return null;
		List<Pice> pica = piceRepository.findByRestoran(restoran);
		if(pica == null)
			return null;
		List<PiceDTO> retPica = new ArrayList<PiceDTO>();
		for(Pice p : pica){
			if(!p.isObrisano()){
				retPica.add(new PiceDTO(p, null));
			}
		}
		
		return retPica;
	}
	
	public List<PiceDTO> findByParameters(String s1, String s2, int idRestorana){
		Restoran r = restoranRepository.findOne(idRestorana);
		if(r == null)
			return null;
		List<Pice> pica = piceRepository.findByRestoran(r);
		List<PiceDTO> filter = new ArrayList<PiceDTO>();
		
		if(pica == null)
			return null;
		
		for(Pice p : pica){
			if(p.getRestoran() == r){
				if(s1.equals("")){
					if(p.getNaziv().toLowerCase().contains(s1.toLowerCase())){
						filter.add(new PiceDTO(p, null));
					}
				}else {
					if(p.getNaziv().toLowerCase().contains(s1.toLowerCase()) || p.getNaziv().toLowerCase().contains(s2.toLowerCase()) ){
						filter.add(new PiceDTO(p, null));
					}
				}
			}
		}
		
		return filter;
	}
	
	
	public PiceDTO create(PiceDTO pice, String email){
		MenadzerRestorana menadzer = menadzerRepository.findByEmail(email);
		if(menadzer == null)
			return null;
		Restoran restoran = menadzer.getRadi_u();
		if(restoran == null)
			return null;
		Date datum = new Date();
		Cena cena = new Cena(pice.getCena(), datum, null);
		Pice piceZaBazu = new Pice(restoran, pice.naziv, pice.opis, cena);
		if(pice.slika.equals("")){
			piceZaBazu.setSlika(default_pice_path);
		}
		piceZaBazu = piceRepository.save(piceZaBazu);
		return new PiceDTO(piceZaBazu, null);
	}
	
	public void namestiSliku(int id, String slika){
		Pice pice = piceRepository.findById(id);
		if(pice == null)
			return;
		pice.setSlika(slika);
		piceRepository.save(pice);
	}
	
	public PiceDTO update(PiceDTO pice){
		Pice p = piceRepository.findById(pice.id);
		if(p == null){
			System.out.println("ovde");
		}
		if(p.trenutnaCena().getIznos() != pice.cena){
			Date date = new Date();
			Cena cena = new Cena(pice.getCena(), date, null);
			for(Cena c : p.getCene()){
				if(c.getDatumDo() == null){
					c.setDatumDo(date);
				}
			}
			p.getCene().add(cena);
		}
		p.setNaziv(pice.naziv);
		p.setOpis(pice.opis);
		p = piceRepository.save(p);
		return new PiceDTO(p, null);
	}
	
	public boolean delete(PiceDTO pice){
		Pice piceIzBaze = piceRepository.findById(pice.id);
		if(piceIzBaze == null)
			return false;
		piceIzBaze.setObrisano(true);
		piceRepository.save(piceIzBaze);
		return true;
	}
}

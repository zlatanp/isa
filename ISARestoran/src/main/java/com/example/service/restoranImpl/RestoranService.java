package com.example.service.restoranImpl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipValute;
import com.example.repository.restoran.RestoranRepository;

@Service
public class RestoranService {

	@Autowired
	RestoranRepository restoranRepostitory;
	
	public RestoranDTO create(RestoranDTO restoran){
		Restoran restoranZaBazu = new Restoran(restoran.getNaziv(), restoran.getTip(), restoran.getAdresa(), restoran.getGrad(), 
				restoran.getOpis(), restoran.getTelefon(), restoran.getEmail(), restoran.getVremeOD(), restoran.getVremeDO());
		restoranRepostitory.save(restoranZaBazu);
		return restoran;	
	}
	
	public RestoranDTO findById(int id){
		Restoran trazeni = restoranRepostitory.findOne(id);
		if(trazeni != null){
			return new RestoranDTO(trazeni);	
		}else {
			return null;
		}
	}
	
	public List<RestoranDTO> findAll(){
		List<Restoran> restorani = restoranRepostitory.findAll();
		List<RestoranDTO> restoraniDTO = new ArrayList<>();
		for(Restoran r : restorani){
			RestoranDTO restoranDTO = new RestoranDTO(r);
			restoraniDTO.add(restoranDTO);
		}
		return restoraniDTO;
	}
	
	public boolean updateRestoran(RestoranDTO restoran, String email){
		String emailManagera = email;
		Restoran r = restoranRepostitory.findOne(restoran.getId());
		if(r == null || !jesteMenadzer(r, emailManagera)){
			return false;
		}
		r.setEmail(restoran.getEmail());
		r.setNaziv(restoran.getNaziv());
		r.setAdresa(restoran.getAdresa());
		r.setGrad(restoran.getGrad());
		r.setTelefon(restoran.getTelefon());
		r.setOpis(restoran.getOpis());
		r.setRadnoVremeDo(restoran.getVremeDO());
		r.setRadnoVremeOd(restoran.getVremeOD());
		r.setValuta(TipValute.DINAR);
		r.setTipRestorana(restoran.getTip());
		
		Restoran zaBazu = restoranRepostitory.save(r);
		if(zaBazu == null)
			return false;
		return true;
	}
	
	public boolean jesteMenadzer(Restoran r, String email){
		
		for(MenadzerRestorana m : r.getMenadzeri()){
			if(m.getEmail().equals(email)){
				return true;
			}
		}
		return false;
	}
}

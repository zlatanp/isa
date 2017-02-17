package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Restoran;
import com.example.dto.restoran.RestoranDTO;
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
}

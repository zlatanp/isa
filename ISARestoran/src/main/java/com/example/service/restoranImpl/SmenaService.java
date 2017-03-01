package com.example.service.restoranImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Restoran;
import com.example.beans.restoran.Smena;
import com.example.dto.restoran.SmenaDTO;
import com.example.repository.restoran.RestoranRepository;
import com.example.repository.restoran.SmenaRepository;

@Service
public class SmenaService {

	@Autowired
	private SmenaRepository smenaRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	public boolean create(SmenaDTO smenaDTO, int idRest){
		Smena s = new Smena(smenaDTO.naziv, smenaDTO.vremeOD, smenaDTO.vremeDO);
		smenaRepository.save(s);
		Restoran r = restoranRepository.findOne(idRest);
		if(r == null){
			return false;
		}
		r.getSmene().add(s);
		restoranRepository.save(r);
		return true;
	}
	
	public List<SmenaDTO> findAll(int idRest){
		List<SmenaDTO> retVal = new ArrayList<SmenaDTO>();
		Restoran r = restoranRepository.findOne(idRest);
		if(r == null){
			return null;
		}
		Set<Smena> smene = r.getSmene();
		if(smene != null){
			for(Smena s : smene){
				retVal.add(new SmenaDTO(s));				
			}
		}
		return retVal;
	}
}

package com.example.dto.restoran;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.beans.restoran.Konkurs;
import com.example.beans.restoran.Namirnica;

public class KonkursDTO {

	public int id;
	public String restoran;
	public int restoranId;
	public Date datumObjave;
	public Date datumZavrsetka;
	public int prihvacenaId;
	public List<NamirnicaDTO> namirnice = new ArrayList<NamirnicaDTO>();
	
	public KonkursDTO() {}

	public KonkursDTO(Date datumZavrsetka, List<NamirnicaDTO> namirnice) {
		super();
		this.datumZavrsetka = datumZavrsetka;
		this.namirnice = namirnice;
	}
	
	public KonkursDTO(Konkurs k){
		this.id = k.getId();
		this.restoran = k.getRestoran().getNaziv();
		this.restoranId = k.getRestoran().getId();
		this.datumObjave = k.getDatumObjave();
		this.datumZavrsetka = k.getDatumZavrsetka();
		this.prihvacenaId = k.getPrihvacenaPonuda().getId();
		if(k.getPrihvacenaPonuda() == null){
			this.prihvacenaId = -1;
		}else {
			this.prihvacenaId = k.getPrihvacenaPonuda().getId(); 
		}
		for(Namirnica n : k.getNamirnice()){
			this.namirnice.add(new NamirnicaDTO(n));
		}
	}
	
}

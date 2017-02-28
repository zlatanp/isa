package com.example.dto.restoran;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.beans.restoran.Narudzbina;
import com.example.beans.restoran.Poseta;
import com.example.beans.restoran.Sto;
import com.example.dto.korisnici.KonobarDTO;



public class PosetaDTO {

	public int id;
	public Set<NarudzbinaDTO> narudzbine = new HashSet<NarudzbinaDTO>();
	public Date datumDolaska;
	public Date datumOdlaska;
	public Set<Integer> stolovi = new HashSet<Integer>();
	public KonobarDTO konobar;
	public RezervacijaDTO rezervacijaDTO;
	public RestoranDTO restoranDTO;
	public RacunDTO racunDTO;
	
	public PosetaDTO() {};
	
	public PosetaDTO(Poseta p){
		Date datum  = null;
		if(p.getRezervacija() != null){
			datum = p.getRezervacija().getDatum();
		}
		this.id = p.getId();
		this.datumDolaska = p.getVremeDolaska();
		this.konobar = new KonobarDTO(p.getKonobar());
		this.restoranDTO = new RestoranDTO(p.getRestoran());
		if(p.getVremeOdlaska() != null){
			this.datumOdlaska = p.getVremeOdlaska();
		}
		if(p.getNarudzbine() != null){
			for(Narudzbina n : p.getNarudzbine()){
				this.narudzbine.add(new NarudzbinaDTO(n, datum));
			}
		}
		if(p.getStolovi() != null){
			for(Sto s : p.getStolovi()){
				this.stolovi.add(s.getId());
			}
		}
		if(p.getRezervacija() != null){
			this.rezervacijaDTO = new RezervacijaDTO(p.getRezervacija());
		}
		if(p.getRacun() != null) {
			this.racunDTO = new RacunDTO(p.getRacun());
		}
	}
	
	public boolean jednakoPoseti(Poseta p){
		if(p.getId() == this.id){
			return true;
		}else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<NarudzbinaDTO> getNarudzbine() {
		return narudzbine;
	}

	public void setNarudzbine(Set<NarudzbinaDTO> narudzbine) {
		this.narudzbine = narudzbine;
	}

	public Date getDatumDolaska() {
		return datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public Date getDatumOdlaska() {
		return datumOdlaska;
	}

	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}

	public Set<Integer> getStolovi() {
		return stolovi;
	}

	public void setStolovi(Set<Integer> stolovi) {
		this.stolovi = stolovi;
	}

	public KonobarDTO getKonobar() {
		return konobar;
	}

	public void setKonobar(KonobarDTO konobar) {
		this.konobar = konobar;
	}

	public RezervacijaDTO getRezervacijaDTO() {
		return rezervacijaDTO;
	}

	public void setRezervacijaDTO(RezervacijaDTO rezervacijaDTO) {
		this.rezervacijaDTO = rezervacijaDTO;
	}

	public RestoranDTO getRestoranDTO() {
		return restoranDTO;
	}

	public void setRestoranDTO(RestoranDTO restoranDTO) {
		this.restoranDTO = restoranDTO;
	}

	public RacunDTO getRacunDTO() {
		return racunDTO;
	}

	public void setRacunDTO(RacunDTO racunDTO) {
		this.racunDTO = racunDTO;
	}
}

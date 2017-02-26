package com.example.dto.restoran;

import com.example.beans.restoran.Sto;
import com.example.enums.TipSegmenta;

public class StoDTO {

	public int id;
	public String naziv;
	public int brojStolica;
	public TipSegmenta tipSegmenta;
	
	public StoDTO(){
		
	}
	
	public StoDTO(Sto s){
		this.id = s.getId();
		this.naziv = s.getNaziv();
		this.brojStolica = s.getBrojStolica();
		this.tipSegmenta = s.getTipSegmenta();
	}

	public StoDTO(String naziv, int brojStolica, TipSegmenta tipSegmenta) {
		super();
		this.naziv = naziv;
		this.brojStolica = brojStolica;
		this.tipSegmenta = tipSegmenta;
	}	
	

	public StoDTO(String naziv, int brojStolica) {
		super();
		this.naziv = naziv;
		this.brojStolica = brojStolica;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getBrojStolica() {
		return brojStolica;
	}

	public void setBrojStolica(int brojStolica) {
		this.brojStolica = brojStolica;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipSegmenta getTipSegmenta() {
		return tipSegmenta;
	}

	public void setTipSegmenta(TipSegmenta tipSegmenta) {
		this.tipSegmenta = tipSegmenta;
	}
	
	
}

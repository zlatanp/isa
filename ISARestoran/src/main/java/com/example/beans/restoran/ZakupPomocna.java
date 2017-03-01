package com.example.beans.restoran;

import java.util.ArrayList;

import com.example.dto.restoran.StoDTO;

public class ZakupPomocna {

	public String email;
	public String datum;
	public String sati;
	public String idRestorana;
	ArrayList<StoDTO> listaStolova;
	
	
	public ZakupPomocna() {
		super();
	}
	public ZakupPomocna(String email, String datum, String sati, String idRestorana, ArrayList<StoDTO> listaStolova) {
		super();
		this.email = email;
		this.datum = datum;
		this.sati = sati;
		this.idRestorana = idRestorana;
		this.listaStolova = listaStolova;
	}




	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDatum() {
		return datum;
	}


	public void setDatum(String datum) {
		this.datum = datum;
	}


	public String getSati() {
		return sati;
	}


	public void setSati(String sati) {
		this.sati = sati;
	}


	public String getIdRestorana() {
		return idRestorana;
	}


	public void setIdRestorana(String idRestorana) {
		this.idRestorana = idRestorana;
	}


	public ArrayList<StoDTO> getListaStolova() {
		return listaStolova;
	}


	public void setListaStolova(ArrayList<StoDTO> listaStolova) {
		this.listaStolova = listaStolova;
	}
	
	
}

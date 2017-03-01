package com.example.dto.hellpers;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RadniciSmena {

	public String radnikIme;   //ko radi
	public String radnikPrezime;
	public String datumOD;     // od kad do kad radi
	public String datumDO;
	public int smenaID;			// koja je to smena
	public int radiUSmeniID;	// koji je to radnikUsmeni
	
	@JsonIgnore
	public Date eventID;
	
	public RadniciSmena() {}
	
	
	public RadniciSmena(String radnikIme, String radnikPrezime, String datumOD) {
		super();
		this.radnikIme = radnikIme;
		this.radnikPrezime = radnikPrezime;
		this.datumOD = datumOD;
	}


	public String getRadnikIme() {
		return radnikIme;
	}

	public void setRadnikIme(String radnikIme) {
		this.radnikIme = radnikIme;
	}

	public String getRadnikPrezime() {
		return radnikPrezime;
	}

	public void setRadnikPrezime(String radnikPrezime) {
		this.radnikPrezime = radnikPrezime;
	}

	public String getDatumOD() {
		return datumOD;
	}

	public void setDatumOD(String datumOD) {
		this.datumOD = datumOD;
	}

	public String getDatumDO() {
		return datumDO;
	}

	public void setDatumDO(String datumDO) {
		this.datumDO = datumDO;
	}

	public int getSmenaID() {
		return smenaID;
	}

	public void setSmenaID(int smenaID) {
		this.smenaID = smenaID;
	}

	public int getRadiUSmeniID() {
		return radiUSmeniID;
	}

	public void setRadiUSmeniID(int radiUSmeniID) {
		this.radiUSmeniID = radiUSmeniID;
	}

	public Date getEventID() {
		return eventID;
	}

	public void setEventID(Date eventID) {
		this.eventID = eventID;
	}
}

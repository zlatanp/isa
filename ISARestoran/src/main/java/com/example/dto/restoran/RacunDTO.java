package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.Racun;
import com.example.dto.korisnici.KonobarDTO;


public class RacunDTO {

	public int id;
	public int restoran;
	public KonobarDTO konobarDTO;
	public Date vreme;
	public int poseta;
	public double iznos;
	
	public RacunDTO() {};
	
	public RacunDTO(Racun r){
		this.id = r.getId();
		this.restoran = r.getRestoran().getId();
		this.konobarDTO = new KonobarDTO(r.getIzdao_racun());
		this.vreme = r.getVreme();
		this.poseta = r.getPoseta().getId();
		this.iznos = r.getIznos();
	}

	public RacunDTO(int restoran, KonobarDTO konobarDTO, Date vreme, int poseta, double iznos) {
		super();
		this.restoran = restoran;
		this.konobarDTO = konobarDTO;
		this.vreme = vreme;
		this.poseta = poseta;
		this.iznos = iznos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRestoran() {
		return restoran;
	}

	public void setRestoran(int restoran) {
		this.restoran = restoran;
	}

	public KonobarDTO getKonobarDTO() {
		return konobarDTO;
	}

	public void setKonobarDTO(KonobarDTO konobarDTO) {
		this.konobarDTO = konobarDTO;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public int getPoseta() {
		return poseta;
	}

	public void setPoseta(int poseta) {
		this.poseta = poseta;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}
	
}

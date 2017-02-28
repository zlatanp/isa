package com.example.beans.restoran;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Konobar;

@Entity
@Table(name="ocena")
public class Ocena implements Serializable{


	private static final long serialVersionUID = 8611856460532879422L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=false)
	protected int id;
	
	@Column(name="ocena_restorana", nullable=false)
	protected int ocenaRestorana;
	
	@Column(name="ocena_usluge", nullable=false)
	protected int ocenaUsluge;
	
	@Column(name="ocena_narucenog", nullable=false)
	protected int ocenaNarucenogJela;
	
	@Column(name="datum", nullable=false)
	protected Date vreme;
	
	@Column(name="opis", nullable=false)
	protected String opis;
	
	@ManyToOne
	@JoinColumn(name="restoran", referencedColumnName="id", nullable=false)
	protected Restoran restoran;
	
	@ManyToOne
	@JoinColumn(name="recenzent", referencedColumnName="id", nullable=false)
	protected Gost gost;
	
	@ManyToOne
	@JoinColumn(name="konobar", referencedColumnName="id", nullable=false)
	protected Konobar konobar;

	@ManyToOne
	@JoinColumn(name="poseta", referencedColumnName="id", nullable = false)
	protected Poseta poseta;
	
	public Ocena() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOcenaRestorana() {
		return ocenaRestorana;
	}

	public void setOcenaRestorana(int ocenaRestorana) {
		this.ocenaRestorana = ocenaRestorana;
	}

	public int getOcenaUsluge() {
		return ocenaUsluge;
	}

	public void setOcenaUsluge(int ocenaUsluge) {
		this.ocenaUsluge = ocenaUsluge;
	}

	public int getOcenaNarucenogJela() {
		return ocenaNarucenogJela;
	}

	public void setOcenaNarucenogJela(int ocenaNarucenogJela) {
		this.ocenaNarucenogJela = ocenaNarucenogJela;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date datum) {
		this.vreme = datum;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Gost getGost() {
		return gost;
	}

	public void setGost(Gost gost) {
		this.gost = gost;
	}

	public Konobar getKonobar() {
		return konobar;
	}

	public void setKonobar(Konobar konobar) {
		this.konobar = konobar;
	}

	public Poseta getPoseta() {
		return poseta;
	}

	public void setPoseta(Poseta poseta) {
		this.poseta = poseta;
	}
	
}

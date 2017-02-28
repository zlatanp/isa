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

import com.example.beans.korisnici.Zaposleni;

@Entity
@Table(name="u_smeni_rade")
public class USmeniRadnik implements Serializable{

	private static final long serialVersionUID = 8560151487009123386L;

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	protected int id;
	
	@ManyToOne()
	@JoinColumn(name="zaposlen",referencedColumnName="id", nullable=false)
	protected Zaposleni zaposlen;
	
	@ManyToOne
	@JoinColumn(name="smena",referencedColumnName="id", nullable=false)
	protected Smena smena;
	
	@ManyToOne
	@JoinColumn(name="reon",referencedColumnName="id", nullable=true)
	protected Reon reon;
	
	@Column(name="dan",nullable=false)
	protected Date dan;
	
	public USmeniRadnik() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Zaposleni getZaposlen() {
		return zaposlen;
	}

	public void setZaposlen(Zaposleni zaposlen) {
		this.zaposlen = zaposlen;
	}

	public Smena getSmena() {
		return smena;
	}

	public void setSmena(Smena smena) {
		this.smena = smena;
	}

	public Reon getReon() {
		return reon;
	}

	public void setReon(Reon reon) {
		this.reon = reon;
	}

	public Date getDan() {
		return dan;
	}

	public void setDan(Date dan) {
		this.dan = dan;
	}
}

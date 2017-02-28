package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.beans.korisnici.Sanker;

@Entity
@Table(name="stavka_pica")
public class StavkaPica implements Serializable{


	private static final long serialVersionUID = -2187472807426207164L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="pice", nullable=false)
	protected Pice pice;
	
	@Column(name="kolicina", nullable=false)
	protected int kolicina;
	
	@ManyToOne
	@JoinColumn(name="spremio")
	protected Sanker spremio;
	
	@Column(name="spremljeno", nullable=false)
	protected boolean spremljeno = false;
	
	@ManyToOne
	@JoinColumn(name="narudzbinaStavkePica", referencedColumnName="id")
	protected Narudzbina narudzbinaStavkePica;
	
	@Version
	private Integer version;
	
	public StavkaPica() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pice getPice() {
		return pice;
	}

	public void setPice(Pice pice) {
		this.pice = pice;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Sanker getSpremio() {
		return spremio;
	}

	public void setSpremio(Sanker spremio) {
		this.spremio = spremio;
	}

	public boolean isSpremljeno() {
		return spremljeno;
	}

	public void setSpremljeno(boolean spremljeno) {
		this.spremljeno = spremljeno;
	}

	public Narudzbina getNarudzbinaStavkePica() {
		return narudzbinaStavkePica;
	}

	public void setNarudzbinaStavkePica(Narudzbina narudzbinaStavkePica) {
		this.narudzbinaStavkePica = narudzbinaStavkePica;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}

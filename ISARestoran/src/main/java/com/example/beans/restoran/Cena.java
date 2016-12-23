package com.example.beans.restoran;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cena")
public class Cena implements Serializable {

	
	private static final long serialVersionUID = -5331731702440944661L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable= false, unique=true)
	protected int id;
	
	@Column(name="iznos", nullable=false)
	protected double iznos;
	
	@Column(name="datumOd", nullable=false)
	protected Date datumOd;
	
	@Column(name="datumDo")
	protected Date datumDo;
	
	public Cena() {
		
	}

	public Cena(double iznos, Date datumOd, Date datumDo) {
		super();
		this.iznos = iznos;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}

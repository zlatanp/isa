package com.example.beans.restoran;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Konobar;


@Entity
@Table(name="racun")
public class Racun implements Serializable{

	private static final long serialVersionUID = 2857107066075552185L;

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="restoran",referencedColumnName="id", nullable=false)
	protected Restoran restoran;
	
	@ManyToOne
	@JoinColumn(name="izdao_racun",referencedColumnName="id", nullable=false)
	protected Konobar izdao_racun;
	
	@Column(name="vreme", nullable=false)
	protected Date vreme;
	
	@OneToOne(mappedBy="racun")
	protected Poseta poseta;
	
	@Column(name="iznos", nullable=false)
	protected double iznos;
	
	public Racun() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Konobar getIzdao_racun() {
		return izdao_racun;
	}

	public void setIzdao_racun(Konobar izdao_racun) {
		this.izdao_racun = izdao_racun;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public Poseta getPoseta() {
		return poseta;
	}

	public void setPoseta(Poseta poseta) {
		this.poseta = poseta;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}
}

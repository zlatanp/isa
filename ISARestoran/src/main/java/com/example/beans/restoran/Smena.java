package com.example.beans.restoran;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="smena")
public class Smena implements Serializable{

	private static final long serialVersionUID = 3312452966904125677L;

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	protected int id;
	
	@Column(name="naziv", nullable=false)
	protected String naziv;
	
	@Column(name="od_vreme",nullable=false)
	protected String od_vreme;
	
	@Column(name="do_vreme",nullable=true)
	protected String do_vreme;
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,mappedBy="smena")
	protected Set<USmeniRadnik> rade_u_smeni = new HashSet<>();
	
	public Smena() {}

	public Smena(String naziv, String vremeOD, String vremeDO){
		super();
		this.naziv = naziv;
		this.do_vreme = vremeDO;
		this.od_vreme = vremeOD;
	} 

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getOd_vreme() {
		return od_vreme;
	}


	public void setOd_vreme(String od_vreme) {
		this.od_vreme = od_vreme;
	}


	public String getDo_vreme() {
		return do_vreme;
	}


	public void setDo_vreme(String do_vreme) {
		this.do_vreme = do_vreme;
	}
	
	
}

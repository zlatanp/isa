package com.example.beans.restoran;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.enums.TipSegmenta;

@Entity
@Table(name="Segment")
public class Segment implements Serializable {

	
	private static final long serialVersionUID = -441087054031433716L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable= false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="restoran", referencedColumnName="id", nullable= false)
	protected Restoran restoran;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tip", nullable=false)
	protected TipSegmenta tip;
	
	@OneToMany(cascade={CascadeType.ALL},fetch= FetchType.LAZY,mappedBy="segment")
	protected Set<Sto> stolovi = new HashSet<Sto>();
	
	
	public Segment(){}

	
	public Segment(Restoran restoran, TipSegmenta tip) {
		super();
		this.restoran = restoran;
		this.tip = tip;
	}
	
	public Segment(Restoran restoran, TipSegmenta tip, Sto sto){
		super();
		this.restoran = restoran;
		this.tip = tip;
		this.stolovi.add(sto);
	}


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

	public TipSegmenta getTip() {
		return tip;
	}

	public void setTip(TipSegmenta tip) {
		this.tip = tip;
	}


	public Set<Sto> getStolovi() {
		return stolovi;
	}


	public void setStolovi(Set<Sto> stolovi) {
		this.stolovi = stolovi;
	}
}

package com.example.beans.korisnici;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.enums.FriendshipStatus;



@Entity
@Table(name="Prijateljstvo")
public class Prijateljstvo implements Serializable {
	private static final long serialVersionUID = 5996848583590762249L;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false , unique = true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="ja", nullable=false, referencedColumnName="id")
	protected Gost ja;
	
	@ManyToOne
	@JoinColumn(name="mojprijatelj", nullable=false, referencedColumnName="id")
	protected Gost mojprijatelj;
	
	@Column(name="status", nullable=false)
	protected FriendshipStatus status;
	
	public Prijateljstvo() {}
	
	public Prijateljstvo(Gost ja, Gost mojprijatelj, FriendshipStatus status){
		this.ja = ja;
		this.mojprijatelj = mojprijatelj;
		this.status = status;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Gost getJa() {
		return ja;
	}

	public void setJa(Gost ja) {
		this.ja = ja;
	}

	public Gost getMojprijatelj() {
		return mojprijatelj;
	}

	public void setMojprijatelj(Gost moj_prijatelj) {
		this.mojprijatelj = moj_prijatelj;
	}

	public FriendshipStatus getStatus() {
		return status;
	}

	public void setStatus(FriendshipStatus status) {
		this.status = status;
	}

	
	
	

}

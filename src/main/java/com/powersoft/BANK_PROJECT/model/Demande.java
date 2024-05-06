package com.powersoft.BANK_PROJECT.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity

public class Demande {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private String username;
	private String numcni;
	private String password;
	private String email;
	private String recto;
	private String verso;
	@OneToOne
	private Banque banque;
	
	
	public Demande() {
		super();
	}






	public Demande(String username, String numcni, String password, String email, String recto, String verso,
			Banque banque) {
		super();
		this.username = username;
		this.numcni = numcni;
		this.password = password;
		this.email = email;
		this.recto = recto;
		this.verso = verso;
		this.banque = banque;
	}






	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getNumcni() {
		return numcni;
	}



	public void setNumcni(String numcni) {
		this.numcni = numcni;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getRecto() {
		return recto;
	}



	public void setRecto(String recto) {
		this.recto = recto;
	}



	public String getVerso() {
		return verso;
	}



	public void setVerso(String verso) {
		this.verso = verso;
	}






	public Banque getBanque() {
		return banque;
	}






	public void setBanque(Banque banque) {
		this.banque = banque;
	}



}

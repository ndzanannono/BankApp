package com.powersoft.BANK_PROJECT.model;

import java.util.Collection;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity

public class Banque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String Nom;
	
	private String Imgbank;
	private double Capital;
	private double Tauxretrait;
	private double Tauxdepot;
	private String email;
	
	@OneToMany(mappedBy = "banque")
	private Collection<Account> Account;
	
	@OneToMany(mappedBy = "banque")
	private Collection<Demande> demande;
	
	public Banque() {
		super();
	}

	

	


	public Banque(String nom, String imgbank, double capital, double tauxretrait, double tauxdepot, String email,
			Collection<com.powersoft.BANK_PROJECT.model.Account> account, Collection<Demande> demande) {
		super();
		Nom = nom;
		Imgbank = imgbank;
		Capital = capital;
		Tauxretrait = tauxretrait;
		Tauxdepot = tauxdepot;
		this.email = email;
		Account = account;
		this.demande = demande;
	}






	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	

	public String getImgbank() {
		return Imgbank;
	}

	public void setImgbank(String imgbank) {
		Imgbank = imgbank;
	}

	public double getCapital() {
		return Capital;
	}

	public void setCapital(double capital) {
		Capital = capital;
	}

	public double getTauxretrait() {
		return Tauxretrait;
	}

	public void setTauxretrait(double tauxretrait) {
		Tauxretrait = tauxretrait;
	}

	public double getTauxdepot() {
		return Tauxdepot;
	}

	public void setTauxdepot(double tauxdepot) {
		Tauxdepot = tauxdepot;
	}

	public Collection<Account> getAccount() {
		return Account;
	}

	public void setAccount(Collection<Account> account) {
		Account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}






	public Collection<Demande> getDemande() {
		return demande;
	}






	public void setDemande(Collection<Demande> demande) {
		this.demande = demande;
	}
	

	
	
}

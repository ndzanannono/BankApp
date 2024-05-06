package com.powersoft.BANK_PROJECT.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idcompte;
	private double solde;
	private String numcompte;
	private boolean status;
	@OneToOne
	private Banque banque;
	
	@OneToOne
	private User user;
	
	public Account() {
		super();
	}

	

	public Account(double solde, String numcompte, boolean status, Banque banque, User user) {
		super();
		this.solde = solde;
		this.numcompte = numcompte;
		this.status = status;
		this.banque = banque;
		this.user = user;
	}



	public int getIdcompte() {
		return idcompte;
	}

	public void setIdcompte(int idcompte) {
		this.idcompte = idcompte;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public String getNumcompte() {
		return numcompte;
	}

	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}

	public Banque getBanque() {
		return banque;
	}

	public void setBanque(Banque banque) {
		this.banque = banque;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}



	public boolean getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	
}
	
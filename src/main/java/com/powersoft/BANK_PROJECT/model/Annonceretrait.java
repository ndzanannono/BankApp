package com.powersoft.BANK_PROJECT.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Annonceretrait {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titre;
	private String description;
	private String numdest;
	private Double somme;
	@OneToOne
	private User user;
	
	public Annonceretrait(String titre, String description, String numdest, Double somme, User user) {
		super();
		this.titre = titre;
		this.description = description;
		this.numdest = numdest;
		this.somme = somme;
		this.user = user;
	}

	public Annonceretrait() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumdest() {
		return numdest;
	}

	public void setNumdest(String numdest) {
		this.numdest = numdest;
	}

	public Double getSomme() {
		return somme;
	}

	public void setSomme(Double somme) {
		this.somme = somme;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}

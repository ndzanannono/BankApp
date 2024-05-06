package com.powersoft.BANK_PROJECT.model;

import java.util.Collection;

import jakarta.persistence.DiscriminatorColumn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity

@Inheritance(strategy =InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name = "Role")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String numcni;
	private String password;
	private String email;
	@OneToMany(mappedBy = "user")
	private Collection<Annonceretrait>annonceretrait;
	
	
	public User() {
		super();
	}


	


	public User(String username, String numcni, String password, String email,
			Collection<Annonceretrait> annonceretrait) {
		super();
		this.username = username;
		this.numcni = numcni;
		this.password = password;
		this.email = email;
		this.annonceretrait = annonceretrait;
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


	public Collection<Annonceretrait> getAnnonceretrait() {
		return annonceretrait;
	}


	public void setAnnonceretrait(Collection<Annonceretrait> annonceretrait) {
		this.annonceretrait = annonceretrait;
	}


	




}
	

package com.powersoft.BANK_PROJECT.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@DiscriminatorValue("Admin")
@Entity
public class Admin extends User {
	

}

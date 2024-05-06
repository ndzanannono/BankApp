package com.powersoft.BANK_PROJECT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.powersoft.BANK_PROJECT.model.Banque;

@Repository

public interface Banque_Repository extends JpaRepository<Banque, Integer>{

}

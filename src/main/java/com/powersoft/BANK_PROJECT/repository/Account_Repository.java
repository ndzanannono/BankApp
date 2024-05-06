package com.powersoft.BANK_PROJECT.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.powersoft.BANK_PROJECT.model.Account;



@Repository

public interface Account_Repository extends JpaRepository<Account, Integer> {

	Account findByUserId(int id);

	Account findByNumcompte(String numero);

}

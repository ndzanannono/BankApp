package com.powersoft.BANK_PROJECT.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.powersoft.BANK_PROJECT.model.User;


@Repository

public interface User_Repository extends JpaRepository<User, Integer>{

	Optional<User> findByPassword(String pass);

	Optional<User> findByNumcni(String cni);

	Optional<User> findByNumcniAndPassword(String cni, String pass);

	
}

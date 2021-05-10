package com.rothar.simplehomebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rothar.simplehomebook.entity.Login;

public interface LoginRepository extends JpaRepository<Login, String>{

}
	
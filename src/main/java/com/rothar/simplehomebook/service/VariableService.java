package com.rothar.simplehomebook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.entity.Variables;
import com.rothar.simplehomebook.repository.VariablesRepository;
import com.rothar.simplehomebook.util.Cache;

@Service
public class VariableService {

	
	@Autowired
	Cache cache;

	@Autowired
	VariablesRepository repo;
	
	
	public boolean crearVariable(String key, String valor) {
		return repo.save(new Variables(key,valor))!=null;
	}
	
	public Variables getVariable(String key) {
		Optional<Variables> var = repo.findById(key);
		if(var.isPresent()) {
			return var.get();
		}else {
			return null;
		}
	}

	
}

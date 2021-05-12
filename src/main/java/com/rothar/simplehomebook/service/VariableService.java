package com.rothar.simplehomebook.service;

import java.util.ArrayList;
import java.util.List;
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

	public boolean crearVariable(String key, String valor) throws Exception{
		return repo.save(new Variables(key, valor)) != null;
	}
	
	public void deleteAll() {
		repo.deleteAll();
	}

	public Variables getVariable(String key) {
		Optional<Variables> var = repo.findById(key);
		if (var.isPresent()) {
			return var.get();
		} else {
			return null;
		}
	}

	public List<String> getAllVariables() {
		List<String> out =  new ArrayList<String>();
		List<Variables> list = repo.findAll();
		list.stream().forEach(item -> {
			out.add(item.getKey());
		});
		return out;
	}

}

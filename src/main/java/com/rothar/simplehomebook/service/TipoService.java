package com.rothar.simplehomebook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.entity.Tipo;
import com.rothar.simplehomebook.repository.TipoRespository;
import com.rothar.simplehomebook.util.Cache;

@Service
public class TipoService {

	@Autowired
	TipoRespository repository;

	@Autowired
	Cache cache;

	/**
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Boolean create(String name) throws Exception {
		return repository
				.save(new Tipo(UUID.randomUUID().toString(), name, cache.getUsuarioConectado().getUser())) != null;
	}

	public Boolean delTipoByName(String name) throws Exception{
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, name, cache.getUsuarioConectado().getUser())));
		if(out==null || out.isEmpty()) {
			throw new Exception("No se ha encontrado ningun registro para borrar");
		}
		out.stream().forEach(item -> {
			repository.delete(item);
		});
		return true;
	}

	public Tipo getTipoById(String id) {
		return repository.findById(id).get();
	}

	public Tipo getTipoByName(String name) throws Exception {
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, name, cache.getUsuarioConectado().getUser())));
		if (out != null && !out.isEmpty()) {
			return out.get(0);
		} else {
			throw new Exception("Tipo no encontrado");
		}
	}

	public List<String> getAllTipos() {
		List<String> listOut = new ArrayList<String>();
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, null, cache.getUsuarioConectado().getUser())));
		if (out != null && !out.isEmpty()) {
			out.parallelStream().forEach(item -> {
				listOut.add(item.getTipo());
			});
		}
		return listOut;
	}

}

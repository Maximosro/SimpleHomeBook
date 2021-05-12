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

	public Boolean create(String name, String user) throws Exception {
		return repository.save(new Tipo(UUID.randomUUID().toString(), name, user)) != null;
	}

	public void deleteAll() {
		repository.deleteAll();
	}

	public Boolean delTipoByName(String name) throws Exception {
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, name, null)));
		if (out == null || out.isEmpty()) {
			throw new Exception("No se ha encontrado ningun registro para borrar");
		}
		out.stream().forEach(item -> {
			repository.delete(item);
		});
		return true;
	}

	public Boolean update(String name, String newName, String user) {
		Tipo tipo = getTipoByNameAllUser(name);
		tipo.setTipo(newName);
		if (user != null) {
			tipo.setUser(user);
		}
		return repository.save(tipo) != null;
	}

	public Tipo getTipoById(String id) {
		return repository.findById(id).get();
	}

	public Tipo getTipoByName(String name) {
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, name, cache.getUsuarioConectado().getUser())));
		if (out != null && !out.isEmpty()) {
			return out.get(0);
		} else {
			return new Tipo("", "", "");
		}
	}

	public Tipo getTipoByNameAllUser(String name) {
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, name, null)));
		if (out != null && !out.isEmpty()) {
			return out.get(0);
		} else {
			return new Tipo("", "", "");
		}
	}

	public List<String> getAllTiposbuActualUser(boolean editable) {
		List<String> listOut = new ArrayList<String>();
		if (!editable) {
			listOut.add("Todos");
		}
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, null, cache.getUsuarioConectado().getUser())));
		if (out != null && !out.isEmpty()) {
			out.parallelStream().forEach(item -> {
				listOut.add(item.getTipo());
			});
		}
		return listOut;
	}

	public boolean existUserInTipos(String user) {
		List<Tipo> out = repository.findAll(Example.of(new Tipo(null, null, user)));
		if (out == null || out.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<String> getAllTipos() {
		List<String> listOut = new ArrayList<String>();
		List<Tipo> out = repository.findAll();
		out.stream().forEach(tipo -> {
			listOut.add(tipo.getTipo());
		});
		return listOut;
	}
}

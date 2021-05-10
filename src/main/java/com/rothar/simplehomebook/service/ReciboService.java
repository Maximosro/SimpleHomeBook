package com.rothar.simplehomebook.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.entity.Recibo;
import com.rothar.simplehomebook.repository.ReciboRepository;
import com.rothar.simplehomebook.util.Utils;

@Service
public class ReciboService {

	@Autowired
	ReciboRepository repo;

	@Autowired
	Utils util;

	public List<Recibo> buscar(String anio, String mes, String tipo, Boolean pagado) {
		List<Recibo> out = new ArrayList<Recibo>();
		Recibo r = new Recibo();
		if (anio != null && !anio.equalsIgnoreCase("Todos")) {
			r.setAnio(anio);
		}
		if (mes != null && !mes.equalsIgnoreCase("Todos")) {
			r.setMes(util.getNumberMonth(mes));
		}
		if (tipo != null && !tipo.equalsIgnoreCase("Todos")) {
			r.setTipo(tipo);
		}
		if (pagado != null) {
			r.setPagado(pagado);
		}
		List<Order> ordenacion =  new ArrayList<Sort.Order>();
		ordenacion.add(Order.desc("anio"));
		ordenacion.add(Order.asc("mes"));
		out = repo.findAll(Example.of(r),Sort.by(ordenacion));
		out.parallelStream().forEach(rec -> {
			rec.setMes(util.getNameMonth(rec.getMes()));
		});
		return out;
	}

	public boolean eliminar(Recibo r) {
		try {
			repo.delete(r);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean insertar(String anio, String mes, String tipo, BigDecimal importe, Boolean pagado, String url)
			throws Exception {
		Recibo r = new Recibo();
		r.setId(UUID.randomUUID().toString());
		r.setAnio(anio);
		r.setImporte(importe);
		r.setMes(util.getNumberMonth(mes));
		r.setPagado(pagado);
		r.setTipo(tipo);
		r.setUrl(url);
		return repo.save(r) != null;
	}

}

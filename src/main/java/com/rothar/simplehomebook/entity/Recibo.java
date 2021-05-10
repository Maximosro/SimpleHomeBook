package com.rothar.simplehomebook.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="RECIBO")
public class Recibo {
	
	@Id
	String id;
	@Column
	String anio;
	@Column
	String mes;
	@Column
	String tipo;
	@Column
	String url;
	@Column
	BigDecimal importe;
	@Column
	Boolean pagado;
	
}

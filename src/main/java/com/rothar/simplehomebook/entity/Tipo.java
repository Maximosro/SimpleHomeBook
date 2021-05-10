package com.rothar.simplehomebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TIPO")
@AllArgsConstructor
@NoArgsConstructor
public class Tipo {

	@Id
	String id;

	@Column
	String tipo;
	
	@Column
	String user;

}

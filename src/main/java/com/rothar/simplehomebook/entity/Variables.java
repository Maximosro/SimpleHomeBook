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
@Table(name = "VARIABLES")
@AllArgsConstructor
@NoArgsConstructor
public class Variables {
	
	@Id
	String key;
	
	@Column
	String valor;
	
	
}

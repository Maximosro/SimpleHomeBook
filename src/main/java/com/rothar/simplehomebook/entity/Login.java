package com.rothar.simplehomebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LOGIN")
public class Login {
	@Id
	String user;
	
	@Column
	byte[] pass;
	
}

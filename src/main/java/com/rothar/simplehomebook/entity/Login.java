package com.rothar.simplehomebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.TypeAlias;

import lombok.Data;

@Data
@Entity
@Table(name = "LOGIN")
public class Login {
	@Id
	String user;
	
	@Column
	Boolean superuser;
	
	@Column
	byte[] pass;

	@Column
	Boolean rememberThis;
	
	
}

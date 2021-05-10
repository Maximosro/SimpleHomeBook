package com.rothar.simplehomebook.util;

import org.springframework.stereotype.Component;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.entity.Recibo;

import lombok.Data;

@Component
@Data
public class Cache {
	
	Recibo reciboLectura;
	boolean isLectura;
	
	Login usuarioConectado;

}

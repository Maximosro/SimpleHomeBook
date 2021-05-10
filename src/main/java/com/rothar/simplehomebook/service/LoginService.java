package com.rothar.simplehomebook.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.repository.LoginRepository;
import com.rothar.simplehomebook.util.Cache;
import com.rothar.simplehomebook.util.CryptoUtils;

@Service
public class LoginService {

	@Autowired
	CryptoUtils cryptoUtils;
	
	@Autowired
	Cache cache;

	@Autowired
	LoginRepository repo;

	public boolean login(String user, String pass) {
		try {
			if(user==null || pass==null) {
				return false;
			}
			Optional<Login> userBean = repo.findById(user);
			byte[] cryptoPass = cryptoUtils.encodePass(pass);
			String userBeanPass = Base64.getEncoder().encodeToString(userBean.get().getPass());
			String userBeanPass2 = Base64.getEncoder().encodeToString(cryptoPass);
			if (user.equals(userBean.get().getUser()) && userBeanPass2.equals(userBeanPass)) {
				cache.setUsuarioConectado(userBean.get());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createUser(String user, String pass) {
		try {
			Login l = new Login();
			l.setPass(cryptoUtils.encodePass(pass));
			l.setUser(user);
			l.setSuperuser(false);
			Login out = repo.save(l);
			if (out == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
}

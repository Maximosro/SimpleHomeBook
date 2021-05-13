package com.rothar.simplehomebook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.repository.LoginRepository;
import com.rothar.simplehomebook.util.Cache;
import com.rothar.simplehomebook.util.EncryptDecrypt;

@Service
public class LoginService {


	@Autowired 
	EncryptDecrypt crypto;

	@Autowired
	Cache cache;

	@Autowired
	LoginRepository repo;

	public boolean login(String user, String pass) {
		try {
			if (user == null || pass == null) {
				return false;
			}
			Optional<Login> userBean = repo.findById(user);
			String userBeanPass = crypto.decrypt(userBean.get().getPass());
			if (user.equals(userBean.get().getUser()) && pass.equals(userBeanPass)) {
				cache.setUsuarioConectado(userBean.get());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public void deleteAll() {
		repo.deleteAll();
	}

	public boolean eliminar(Login r) {
		try {
			repo.delete(r);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<String> getAllUsers() {
		List<String> out = new ArrayList<String>();
		List<Login> list = repo.findAll();
		list.stream().forEach(user -> {
			out.add(user.getUser());
		});
		return out;
	}

	public List<Login> getAllUser() {
		return repo.findAll();
	}

	public boolean createUser(String user, String pass, boolean adm) {
		try {
			Login l = new Login();
			l.setPass(crypto.encrypt(pass));
			l.setUser(user);
			l.setSuperuser(adm);
			l.setRememberThis(false);
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

	public boolean setRecordar(String user) {
		List<Login> users = repo.findAll();
		users.stream().forEach(item -> {
			if (item.getUser().equals(user)) {
				item.setRememberThis(true);
			} else {
				item.setRememberThis(false);
			}
		});

		repo.saveAll(users);

		return true;
	}

	public Login getRecordar() {
		Login logUser = new Login();
		logUser.setRememberThis(true);
		List<Login> out = repo.findAll(Example.of(logUser));
		if (out != null && !out.isEmpty()) {
			return out.get(0);
		} else {
			return null;
		}

	}
}

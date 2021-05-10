package com.rothar.simplehomebook.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("login.fxml")
public class LoginController {
	
	LoginService service;
	Utils util;
	WindowUtils wUtil;

	@Autowired
	public LoginController(LoginService service,Utils util , WindowUtils wUtil ) {
		this.service=service;
		this.util=util;
		this.wUtil = wUtil;
	}

	boolean creando = false;

	@FXML
	TextField loginUser;

	@FXML
	Label lblError;

	@FXML
	Button bttCancel;

	@FXML
	Button bttLogin;

	@FXML
	Button btnCrear;

	@FXML
	PasswordField loginPass;

	@FXML
	private void cancel() throws IOException {
		if (creando) {
			creando = false;
			changeLoginAspect();
		} else {
			Stage stage = (Stage) bttCancel.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	private void crear() throws IOException {
		creando = true;
		changeLoginAspect();
	}

	@FXML
	private void enter(KeyEvent evt) throws IOException {
		if (evt.getCode().compareTo(KeyCode.ENTER) == 0) {
			bttLogin.requestFocus();
			login();
		}
	}

	@FXML
	private void login() throws IOException {
		if (creando) {
			if (service.createUser(loginUser.getText(), loginPass.getText())) {
				util.mostrarError(lblError, "Usuario creado correctamente", true);
				creando = false;
				changeLoginAspect();
			} else {
				util.mostrarError(lblError, "No fue posible crear el usuario", false);
			}
		} else {
			if (service.login(loginUser.getText(), loginPass.getText())) {
				lblError.setVisible(false);
				Stage stage = (Stage) bttCancel.getScene().getWindow();
				wUtil.showWindow(stage, MainController.class, false);
			} else {
				util.mostrarError(lblError, "El usuario o la contraseña es incorrecta", false);
				loginUser.requestFocus();
			}
		}
	}

	private void changeLoginAspect() {
		loginUser.requestFocus();
		loginPass.clear();
		loginUser.clear();
		if (creando) {
			bttLogin.setText("Aceptar");
			bttCancel.setText("Cancelar");
			btnCrear.setVisible(false);
		} else {
			bttLogin.setText("Login");
			bttCancel.setText("Salir");
			btnCrear.setVisible(true);
		}
	}

}

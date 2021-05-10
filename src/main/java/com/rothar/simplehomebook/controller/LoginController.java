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
	public LoginController(LoginService service, Utils util, WindowUtils wUtil) {
		this.service = service;
		this.util = util;
		this.wUtil = wUtil;
	}


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

		Stage stage = (Stage) bttCancel.getScene().getWindow();
		stage.close();

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

		if (service.login(loginUser.getText(), loginPass.getText())) {
			lblError.setVisible(false);
			Stage stage = (Stage) bttCancel.getScene().getWindow();
			wUtil.showWindow(stage, MenuController.class, false);
		} else {
			util.mostrarError(lblError, "El usuario o la contraseña es incorrecta", false);
			loginUser.requestFocus();
		}

	}

}

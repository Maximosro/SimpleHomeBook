package com.rothar.simplehomebook.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.util.EncryptDecrypt;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
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
	EncryptDecrypt crypto;

	@Autowired
	public LoginController(LoginService service,EncryptDecrypt crypto, Utils util, WindowUtils wUtil) {
		this.service = service;
		this.util = util;
		this.wUtil = wUtil;
		this.crypto=crypto;
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
	CheckBox chkRecordar;

	@FXML
	PasswordField loginPass;

	@FXML
	public void initialize() {
		Login userRecordado = service.getRecordar();
		if (userRecordado != null) {
			loginUser.setText(userRecordado.getUser());
			loginPass.setText(crypto.decrypt(userRecordado.getPass()));
			chkRecordar.setSelected(true);
			bttLogin.requestFocus();
		}
	}

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
			if (chkRecordar.isSelected()) {
				service.setRecordar(loginUser.getText());
			}else {
				service.setRecordar("");
			}
			Stage stage = (Stage) bttCancel.getScene().getWindow();
			wUtil.showWindow(stage, MenuController.class, false);
		} else {
			wUtil.showLabelText(lblError, "Usuario o contraseña incorrecta", false);
			loginUser.requestFocus();
		}

	}

}

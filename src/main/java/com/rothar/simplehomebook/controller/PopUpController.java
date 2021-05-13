package com.rothar.simplehomebook.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.util.EncryptDecrypt;
import com.rothar.simplehomebook.util.PopUpType;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("popup.fxml")
public class PopUpController {

	Utils util;
	WindowUtils wUtil;
	EncryptDecrypt crypto;

	@Autowired
	public PopUpController(Utils util, WindowUtils wUtil) {
		this.util = util;
		this.wUtil = wUtil;
	}

	@FXML
	Label lblTitle;
	
	@FXML
	TextArea txtBody;

	@FXML
	Button bttCancelar;

	@FXML
	Button bttAceptar;

	@FXML
	public void initialize() {
		lblTitle.setText(wUtil.popType.toString());
		txtBody.setText(wUtil.popBody);
		if (wUtil.popType.equals(PopUpType.INFORMACION)) {
			bttAceptar.setVisible(false);
			bttCancelar.setText("Cerrar");
		}
	}

	@FXML
	private void cancelar() throws IOException {
		wUtil.aceptar=false;
		wUtil.popBody=null;
		wUtil.popType=null;
		Stage stage = (Stage) bttCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void enter(KeyEvent evt) throws IOException {
		if (evt.getCode().compareTo(KeyCode.ENTER) == 0) {
			if (wUtil.popType.equals(PopUpType.INFORMACION)) {
				cancelar();
			}else {
				aceptar();
			}
		}
	}

	@FXML
	private void aceptar() throws IOException {
		wUtil.aceptar=true;
		wUtil.popBody=null;
		wUtil.popType=null;
		Stage stage = (Stage) bttAceptar.getScene().getWindow();
		stage.close();
	}

}

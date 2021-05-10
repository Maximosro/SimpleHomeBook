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
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("usuarios.fxml")
public class UsuarioController {

	LoginService service;
	Utils util;
	WindowUtils wUtil;

	@Autowired
	public UsuarioController(LoginService service, Utils util, WindowUtils wUtil) {
		this.service = service;
		this.util = util;
		this.wUtil = wUtil;
	}

	@FXML
	TextField loginUser;

	@FXML
	Label lblError;

	@FXML
	CheckBox chkAdmin;

	@FXML
	Button bttCancel;

	@FXML
	Button btnCrear;

	@FXML
	PasswordField loginPass;

	@FXML
	private void cancel() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, MenuController.class, false);
	}

	@FXML
	private void crear() throws IOException {
		if(service.createUser(loginUser.getText(), loginPass.getText(),chkAdmin.isSelected())) {
			util.mostrarError(lblError, "Usuario creado correctamente", true);
			limpiar();
		}else {
			util.mostrarError(lblError, "Error al crear el usuario", false);
		}
	}

	@FXML
	private void enter(KeyEvent evt) throws IOException {
		if (evt.getCode().compareTo(KeyCode.ENTER) == 0) {
			crear();
		}
	}
	
	private void limpiar() {
		loginPass.setText("");
		loginUser.clear();
		chkAdmin.setSelected(false);
	}

}

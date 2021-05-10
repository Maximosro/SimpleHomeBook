package com.rothar.simplehomebook.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.util.Cache;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("menu.fxml")
public class MenuController {
	

	Utils util;
	WindowUtils wUtil;
	Cache cache;
	
	@FXML
	Button buttonRecibos;
	@FXML
	Button buttonUsuarios;
	@FXML
	Button buttonCrearRecibo;
	@FXML
	Button bttCancel;

	@Autowired
	public MenuController( Utils util , WindowUtils wUtil, Cache cache) {
		this.util=util;
		this.cache=cache;
		this.wUtil=wUtil;
	}


	@FXML
	private void usuarios() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, UsuarioController.class, false);
	}
	@FXML
	private void recibos() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, RecibosController.class, false);
	}
	
	@FXML
	private void sesion() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, LoginController.class, false);
	}
	@FXML
	private void crearRecibo() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		cache.setVentanaPadre("MENU");
		wUtil.showWindow(stage, NuevoController.class, false);
	}
	@FXML
	private void cancel() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		stage.close();
	}

	

}

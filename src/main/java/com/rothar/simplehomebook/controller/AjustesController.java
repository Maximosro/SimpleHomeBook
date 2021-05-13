package com.rothar.simplehomebook.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.entity.Tipo;
import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.service.ReciboService;
import com.rothar.simplehomebook.service.TipoService;
import com.rothar.simplehomebook.service.VariableService;
import com.rothar.simplehomebook.util.Cache;
import com.rothar.simplehomebook.util.PopUpType;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("ajustes.fxml")
public class AjustesController {

	TipoService tipoS;
	VariableService vService;
	ReciboService reciboService;
	LoginService usuarioService;
	Utils util;
	Cache cache;
	WindowUtils wUtil;

	@Autowired
	public AjustesController(LoginService usuarioService, VariableService vService, ReciboService reciboService,
			TipoService tipoS, Cache cache, Utils util, WindowUtils wUtil) {
		this.tipoS = tipoS;
		this.util = util;
		this.wUtil = wUtil;
		this.reciboService = reciboService;
		this.vService = vService;
		this.usuarioService = usuarioService;
		this.cache = cache;
	}

	@FXML
	TextField textTipo;

	@FXML
	ComboBox<String> comboTipos;

	@FXML
	ComboBox<String> comboUser;

	@FXML
	TextArea textVariableValue;

	@FXML
	ComboBox<String> comboVariableKey;

	@FXML
	Label lblError;

	@FXML
	Button bttCancel;

	@FXML
	Button buttonCrearTipo;

	@FXML
	Button buttonEditarTipo;

	@FXML
	Button buttonEliminarTipo;

	@FXML
	Button butttonEditarVariable;

	@FXML
	public void initialize() {
		refreshTipos();
		refreshVariables();
		refresUser();
		textTipo.setDisable(true);
	}

	@FXML
	private void cancel() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, MenuController.class, false);
	}

	@FXML
	private void selectKey() throws IOException {
		textVariableValue.clear();
		textVariableValue.setText(vService.getVariable(comboVariableKey.getValue()).getValor());
		textVariableValue.setDisable(false);
	}

	@FXML
	private void selectTipos() throws Exception {
		if (comboTipos.getValue() != null) {
			textTipo.setDisable(false);

			textTipo.clear();
			if (comboTipos.getValue().equalsIgnoreCase("Nuevo Tipo")) {
				textTipo.setText("New value");
				comboUser.setDisable(false);
				comboUser.setValue(cache.getUsuarioConectado().getUser());
			} else {
				textTipo.setText(tipoS.getTipoByNameAllUser(comboTipos.getValue()).getTipo());
				setComboUser();
			}
			textTipo.requestFocus();
			textTipo.selectAll();
		}
	}

	private void setComboUser() {
		comboUser.setDisable(false);
		Tipo tipo = tipoS.getTipoByNameAllUser(comboTipos.getValue());
		comboUser.setValue(tipo.getUser());

	}

	@FXML
	private void crearTipo() throws IOException {
		try {
			if (textTipo.getText().isEmpty()) {
				throw new Exception();
			} else if (comboTipos.getValue().equalsIgnoreCase("Nuevo Tipo")) {
				if (tipoS.create(textTipo.getText(), comboUser.getValue())) {
					wUtil.showLabelText(lblError, "Nuevo Tipo creado correctamente", true);
					refreshTipos();
					refresUser();
					textTipo.clear();
					textTipo.setDisable(true);

				}
			} else if (!textTipo.getText().equalsIgnoreCase("Nuevo Tipo")) {
				if (tipoS.update(comboTipos.getValue(), textTipo.getText(), comboUser.getValue())) {
					wUtil.showLabelText(lblError, "Tipo " + textTipo.getText() + " actualizado correctamente", true);
					refreshTipos();
					refresUser();
					textTipo.clear();
					textTipo.setDisable(true);
				}
			}
		} catch (Exception e) {
			wUtil.showLabelText(lblError, "No se ha podido crear/actualizar el tipo", false);
		}
	}

	@FXML
	private void editarVariable() throws Exception {
		try {
			if (vService.crearVariable(comboVariableKey.getValue(), textVariableValue.getText())) {
				wUtil.showLabelText(lblError, "Variable de sistema actualiazda", true);
				refreshVariables();
				textVariableValue.clear();
				textVariableValue.setDisable(true);
			} else {
				throw new Exception("Error al actualizar la variable de sistema");
			}
		} catch (Exception e) {
			wUtil.showLabelText(lblError, e.getMessage(), false);
		}

	}

	@FXML
	private void eliminarTipo() throws IOException {
		try {
			if (comboTipos.getValue() != null && !comboTipos.getValue().equalsIgnoreCase("Nuevo Tipo")) {
				if (reciboService.existeTipo(comboTipos.getValue())) {
					wUtil.showLabelText(lblError, "Error al borrar, tipo está en uso", false);
				} else {
					if (tipoS.delTipoByName(comboTipos.getValue())) {
						wUtil.showLabelText(lblError, "Tipo borrado correctamente", true);
						refreshTipos();
						refresUser();
						textTipo.clear();
						textTipo.setDisable(true);
					}
				}
			} else {
				wUtil.showLabelText(lblError, "No se ha seleccionado ningun tipo para eliminar", false);
			}
		} catch (Exception e) {
			wUtil.showLabelText(lblError, e.getMessage(), false);
		}
	}

	@FXML
	private void reset() throws IOException {
		wUtil.showPopUp((Stage) bttCancel.getScene().getWindow(), PopUpType.ADVERTENCIA,
				"Se van a eliminar todos datos almacenados, y la aplicacion se reestablecera con los valores por defecto. ¿Desea continuar?.");
		if (wUtil.aceptar) {
			tipoS.deleteAll();
			reciboService.deleteAll();
			usuarioService.deleteAll();
			usuarioService.createUser("root", "ootr", true);
			wUtil.showLabelText(lblError, "La base de datos ha sido reestablecida", false);
			initialize();
		}
	}

	private void refreshTipos() {
		comboTipos.getItems().clear();
		List<String> list = tipoS.getAllTipos();
		LinkedList<String> linkList = new LinkedList<String>();
		linkList.addFirst("Nuevo Tipo");
		list.stream().forEach(item -> {
			linkList.add(item);
		});
		comboTipos.setItems(FXCollections.observableArrayList(linkList));
	}

	private void refreshVariables() {
		try {
			comboVariableKey.getItems().clear();
			comboVariableKey.setItems(FXCollections.observableArrayList(vService.getAllVariables()));
		} catch (Exception e) {
			//
		}
	}

	private void refresUser() {
		try {
			comboUser.setDisable(false);
			comboUser.getItems().clear();
			comboUser.setItems(FXCollections.observableArrayList(usuarioService.getAllUsers()));
			comboUser.setDisable(true);
		} catch (Exception e) {
			//
		}
	}

}

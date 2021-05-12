package com.rothar.simplehomebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.entity.Login;
import com.rothar.simplehomebook.entity.Recibo;
import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.service.TipoService;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("usuarios.fxml")
public class UsuarioController {

	LoginService service;
	TipoService tipoS;
	Utils util;
	WindowUtils wUtil;

	@Autowired
	public UsuarioController(LoginService service,TipoService tipoS, Utils util, WindowUtils wUtil) {
		this.service = service;
		this.util = util;
		this.wUtil = wUtil;
		this.tipoS=tipoS;
	}

	@FXML
	TextField loginUser;

	@FXML
	TableView<Login> tableUser;

	@FXML
	Label lblError;

	@FXML
	CheckBox chkAdmin;

	@FXML
	Button bttCancel;

	@FXML
	Button btnCrear;

	@FXML
	Button buttonDelete;

	@FXML
	PasswordField loginPass;

	@FXML
	public void initialize() {
		buscar();
	}

	private void buscar() {
		List<Login> out = service.getAllUser();
		ObservableList<Login> listaTabla = FXCollections.observableArrayList(out);
		ObservableList<TableColumn<Login, ?>> listColumns = tableUser.getColumns();
		for (TableColumn<Login, ?> col : listColumns) {
			if (col.getId().equals("colName")) {
				col.setCellValueFactory(new PropertyValueFactory<>("user"));
			}
			if (col.getId().equals("colAdmin")) {
				col.setCellValueFactory(new PropertyValueFactory<>("superuser"));
			}
		}
		tableUser.setItems(listaTabla);
		tableUser.refresh();
	}

	@FXML
	private void cancel() throws IOException {
		Stage stage = (Stage) bttCancel.getScene().getWindow();
		wUtil.showWindow(stage, MenuController.class, false);
	}

	@FXML
	private void delete() throws IOException {
		Login item = tableUser.getSelectionModel().getSelectedItem();
		if(item==null) {
			util.mostrarError(lblError, "Ningun usuario seleccionado", false);
		}else if(tipoS.existUserInTipos(item.getUser())) {
			util.mostrarError(lblError, "Error: El usuario aun tiene datos asociados", false);
		}else if (service.eliminar(item)) {
			buscar();
			util.mostrarError(lblError, "Se ha eliminado el usuario correctamente", true);
		} else {
			util.mostrarError(lblError, "No se ha podido usuario el recibo", false);
		}
	}

	@FXML
	private void crear() throws IOException {
		if (service.createUser(loginUser.getText(), loginPass.getText(), chkAdmin.isSelected())) {
			util.mostrarError(lblError, "Usuario creado correctamente", true);
			limpiar();
			buscar();
		} else {
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

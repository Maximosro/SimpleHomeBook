package com.rothar.simplehomebook.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.service.ReciboService;
import com.rothar.simplehomebook.util.Utils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("nuevo.fxml")
public class NuevoController extends Application {

	@Autowired
	public NuevoController(ReciboService service, Utils util) {
		this.service = service;
		this.util = util;
	}

	@Value("${app.path}")
	String pathInicial;

	ReciboService service;
	Utils util;

	@FXML
	Label lblError;

	@FXML
	Button btnSalir;

	@FXML
	TextArea textUrl;

	@FXML
	TextField textImporte;

	@FXML
	CheckBox chkPagado;

	@FXML
	Button btnLimpiar;

	@FXML
	Button btnAceptar;

	@FXML
	Button btnFile;

	@FXML
	ComboBox<String> comAnio;
	@FXML
	ComboBox<String> comMes;
	@FXML
	ComboBox<String> comTipo;

	@FXML
	public void initialize() {
		comAnio.setItems(FXCollections.observableArrayList(util.generateListYears()));
		int y = Calendar.getInstance().get(Calendar.YEAR);
		comAnio.setValue("" + y);
		chkPagado.setSelected(true);
		comMes.setValue(StringUtils
				.capitalize(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))));
	}

	@FXML
	private void aceptar() throws Exception {
		boolean out = false;
		try {
			out = service.insertar(comAnio.getValue(), comMes.getValue(), comTipo.getValue(),
					new BigDecimal(textImporte.getText()), chkPagado.isSelected(), textUrl.getText());
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (out) {
				limpiar();
				util.mostrarError(lblError, "Recibo creado con exito", true);
			} else {
				util.mostrarError(lblError, "Error al crear el recibo", false);
			}
		}
	}

	@FXML
	private void fileSearch() throws Exception {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		FileChooser fileComponent = new FileChooser();
		File f = new File(pathInicial);
		if (f.isDirectory() && f.exists()) {
			fileComponent.setInitialDirectory(f);
			fileComponent.setTitle("Busqueda Recibos");
			File file = fileComponent.showOpenDialog(stage.getOwner());
			if (file != null) {
				textUrl.setText(file.getAbsolutePath());
			}
		}else {
			util.mostrarError(lblError, "No hay un directorio base configurado", false);
		}

	}

	@FXML
	private void limpiar() throws IOException {
		comTipo.setValue("");
		textImporte.setText("");
		textUrl.setText("");
		initialize();
	}

	@FXML
	private void salir() throws IOException {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void enter(KeyEvent evt) throws IOException {
		if (evt.getCode().compareTo(KeyCode.ENTER) == 0) {

		}
	}

	@FXML
	private void numericValidation(KeyEvent evt) throws IOException {
		if (!textImporte.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
			textImporte.clear();
			textImporte.setStyle("-fx-text-fill: red");
		} else {
			textImporte.setStyle("");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}

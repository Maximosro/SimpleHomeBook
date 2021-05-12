package com.rothar.simplehomebook.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.service.ReciboService;
import com.rothar.simplehomebook.service.TipoService;
import com.rothar.simplehomebook.service.VariableService;
import com.rothar.simplehomebook.util.Cache;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

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
	public NuevoController(ReciboService service, VariableService varService, TipoService tipoS, Utils util,
			Cache cache, WindowUtils wUtil) {
		this.service = service;
		this.util = util;
		this.tipoS = tipoS;
		this.cache = cache;
		this.wUtil = wUtil;
		this.varService = varService;
	}

	boolean multipleMonth = false;

	ReciboService service;
	Utils util;
	TipoService tipoS;
	Cache cache;
	WindowUtils wUtil;
	VariableService varService;

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
	ComboBox<String> comMes2;
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
		comMes2.setValue(StringUtils
				.capitalize(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))));
		multipleMonth = false;
		refreshTipos();
	}

	private void refreshTipos() {
		comTipo.getItems().clear();
		comTipo.setItems(FXCollections.observableArrayList(tipoS.getAllTiposbuActualUser(true)));
	}

	@FXML
	private void aceptar() throws Exception {
		boolean out = false;
		try {
			if (!multipleMonth) {
				out = service.insertar(comAnio.getValue(), comMes.getValue(), comTipo.getValue(),
						new BigDecimal(textImporte.getText()), chkPagado.isSelected(), textUrl.getText());
			} else {
				Integer numMes1 = Integer.parseInt(util.getNumberMonth(comMes.getValue()));
				Integer numMes2 = Integer.parseInt(util.getNumberMonth(comMes2.getValue()));
				if (numMes1 >= numMes2) {
					util.mostrarError(lblError, "El mes inicio es mayor o igual que el mes final", false);
				} else {
					Integer numMeses = (numMes2 - numMes1) + 1;
					BigDecimal importe = new BigDecimal(textImporte.getText());
					BigDecimal importePorMes = importe.divide(BigDecimal.valueOf(numMeses), 2, RoundingMode.HALF_UP);
					for (Integer i = numMes1; i <= numMes2; i++) {
						service.insertar(comAnio.getValue(), util.getNameMonth(i.toString()), comTipo.getValue(),
								importePorMes, chkPagado.isSelected(), textUrl.getText());
					}
					out = true;
				}

			}
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
		File f = new File(varService.getVariable("RECIBOS_PATH").getValor());
		if (f.isDirectory() && f.exists()) {
			fileComponent.setInitialDirectory(f);
			fileComponent.setTitle("Busqueda Recibos");
			File file = fileComponent.showOpenDialog(stage.getOwner());
			if (file != null) {
				textUrl.setText(file.getAbsolutePath());
				varService.crearVariable("RECIBOS_PATH", file.getParent());
			}
		} else {
			varService.crearVariable("RECIBOS_PATH", "C:\\");
			fileSearch();
		}

	}

	@FXML
	private void limpiar() throws IOException {
		comTipo.setValue("");
		textImporte.setText("");
		textUrl.setText("");
		comMes2.setVisible(false);
		initialize();
	}

	@FXML
	private void more() throws IOException {
		comMes2.setVisible(true);
		multipleMonth = true;
	}



	@FXML
	private void salir() throws IOException {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		if (cache.getVentanaPadre().equals("MENU")) {
			cache.setVentanaPadre("");
			wUtil.showWindow(stage, MenuController.class, false);
		} else {
			stage.close();
		}
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

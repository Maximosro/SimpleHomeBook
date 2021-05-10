package com.rothar.simplehomebook.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.entity.Recibo;
import com.rothar.simplehomebook.service.ReciboService;
import com.rothar.simplehomebook.service.TipoService;
import com.rothar.simplehomebook.util.Utils;
import com.rothar.simplehomebook.util.WindowUtils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

@Controller
@FxmlView("main.fxml")
public class RecibosController extends Application {

	Logger log = LoggerFactory.getLogger(getClass());
	boolean ejecutado = false;

	@Autowired
	public RecibosController(ReciboService service, TipoService tipoS, WindowUtils wUtil, Utils util) {
		this.service = service;
		this.wUtil = wUtil;
		this.util = util;
		this.tipoS = tipoS;
	}

	WindowUtils wUtil;

	ReciboService service;
	
	TipoService tipoS;

	Utils util;

	@FXML
	TableView<Recibo> tblRecibos;

	@FXML
	ComboBox<String> comboAnio;

	@FXML
	ComboBox<String> comboMes;

	@FXML
	ComboBox<String> comboTipo;

	@FXML
	CheckBox chkPagados;

	@FXML
	CheckBox chkNoPagados;

	@FXML
	Label lblError;

	@FXML
	Button btnSalir;

	@FXML
	Button btnBuscar;

	@FXML
	public void initialize() {
		comboAnio.setItems(FXCollections.observableArrayList(util.generateListYears()));
		int y = Calendar.getInstance().get(Calendar.YEAR);
		comboAnio.setValue("" + y);
		comboTipo.getItems().clear();
		comboTipo.setItems(getTipos());
		
	}
	
	private ObservableList<String> getTipos() {
		return FXCollections.observableArrayList(tipoS.getAllTipos(false));
	}

	@FXML
	private void cancel() throws IOException {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		wUtil.showWindow(stage, MenuController.class, false);
	}

	@FXML
	private void delete() throws IOException {
		Recibo item = tblRecibos.getSelectionModel().getSelectedItem();
		if(item==null) {
			util.mostrarError(lblError, "Ningun recibo seleccionado", false);
		}else if (service.eliminar(item)) {
			buscar();
			util.mostrarError(lblError, "Se ha eliminado el recibo correctamente", true);
		} else {
			util.mostrarError(lblError, "No se ha podido eliminar el recibo", false);
		}
	}

	@FXML
	private void create() throws IOException {
		Stage stage = (Stage) btnSalir.getScene().getWindow();
		wUtil.showWindow(stage, NuevoController.class, true);
	}

	@FXML
	private void detalle(MouseEvent mouseEvent) throws IOException {
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
			Recibo recibo = tblRecibos.getSelectionModel().getSelectedItem();
			if (!ejecutado) {
				ejecutado = true;
				if (recibo.getUrl() != null && !recibo.getUrl().isEmpty()) {
					getHostServices().showDocument(recibo.getUrl());
				}else {
					util.mostrarError(lblError, "No existe recibo asociado", false);
				}
			} else {
				ejecutado = false;
			}
		}

	}

	@FXML
	private void buscar() throws IOException {
		Boolean pagados;
		if (chkPagados.isSelected() && !chkNoPagados.isSelected()) {
			pagados = true;
		} else if (!chkPagados.isSelected() && chkNoPagados.isSelected()) {
			pagados = false;
		} else {
			pagados = null;
		}

		List<Recibo> listaRecibos = service.buscar(comboAnio.getValue(), comboMes.getValue(), comboTipo.getValue(),
				pagados);
		ObservableList<Recibo> listaTabla = FXCollections.observableArrayList(listaRecibos);

		ObservableList<TableColumn<Recibo, ?>> listColumns = tblRecibos.getColumns();
		for (TableColumn<Recibo, ?> col : listColumns) {
			if (col.getId().equals("colAnio")) {
				col.setCellValueFactory(new PropertyValueFactory<>("anio"));
			}
			if (col.getId().equals("colMes")) {
				col.setCellValueFactory(new PropertyValueFactory<>("mes"));
			}
			if (col.getId().equals("colTipo")) {
				col.setCellValueFactory(new PropertyValueFactory<>("tipo"));
			}
			if (col.getId().equals("colImporte")) {
				col.setCellValueFactory(new PropertyValueFactory<>("importe"));
			}
			if (col.getId().equals("colRecibo")) {
				col.setCellValueFactory(new PropertyValueFactory<>("url"));
			}
			if (col.getId().equals("colPagado")) {
				col.setCellValueFactory(new PropertyValueFactory<>("pagado"));
			}
		}

		tblRecibos.setItems(listaTabla);
		tblRecibos.refresh();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
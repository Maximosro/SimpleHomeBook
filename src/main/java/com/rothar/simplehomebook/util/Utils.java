package com.rothar.simplehomebook.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

@Component
public class Utils {

	public String getNumberMonth(String mes) {
		if (mes.equals("Enero")) {
			return "01";
		} else if (mes.equals("Febrero")) {
			return "02";
		} else if (mes.equals("Marzo")) {
			return "03";
		} else if (mes.equals("Abril")) {
			return "04";
		} else if (mes.equals("Mayo")) {
			return "05";
		} else if (mes.equals("Junio")) {
			return "06";
		} else if (mes.equals("Julio")) {
			return "07";
		} else if (mes.equals("Agosto")) {
			return "08";
		} else if (mes.equals("Septiembre")) {
			return "09";
		} else if (mes.equals("Octubre")) {
			return "10";
		} else if (mes.equals("Noviembre")) {
			return "11";
		} else if (mes.equals("Diciembre")) {
			return "12";
		} else {
			return null;
		}
	}
	
	public String getNameMonth(String mes) {
		if (mes.equals("01") || mes.equals("1")) {
			return "Enero";
		} else if (mes.equals("02")|| mes.equals("2")) {
			return "Febrero";
		} else if (mes.equals("03")|| mes.equals("3")) {
			return "Marzo";
		} else if (mes.equals("04")|| mes.equals("4")) {
			return "Abril";
		} else if (mes.equals("05")|| mes.equals("5")) {
			return "Mayo";
		} else if (mes.equals("06")|| mes.equals("6")) {
			return "Junio";
		} else if (mes.equals("07")|| mes.equals("7")) {
			return "Julio";
		} else if (mes.equals("08")|| mes.equals("8")) {
			return "Agosto";
		} else if (mes.equals("09")|| mes.equals("9")) {
			return "Septiembre";
		} else if (mes.equals("10")) {
			return "Octubre";
		} else if (mes.equals("11")) {
			return "Noviembre";
		} else if (mes.equals("12")) {
			return "Diciembre";
		} else {
			return null;
		}
	}
	
	public List<String> generateListYears() {
		List<String> listaAños =  new ArrayList<String>();
		listaAños.add("Todos");
		for(int i=-3;i<30;i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, -i);
			listaAños.add(c.get(Calendar.YEAR)+"");
		}

		return listaAños;
	}
	
	public void mostrarError(Label lblError, String msg, boolean ok) {
		lblError.setVisible(true);
		lblError.setText(msg);
		if (ok) {
			lblError.setStyle("-fx-text-fill: green");
		} else {
			lblError.setStyle("-fx-text-fill: red");
		}
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
			lblError.setVisible(false);
			lblError.setText(null);
		}));
		timeline.setCycleCount(1);
		timeline.play();

	}

}

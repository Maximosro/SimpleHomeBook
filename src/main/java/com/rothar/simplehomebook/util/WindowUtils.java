package com.rothar.simplehomebook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rothar.simplehomebook.controller.PopUpController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxWeaver;

@Component
public class WindowUtils {

	@Autowired
	FxWeaver fxWeaver;
	
	//POPupVars:
	public PopUpType popType;
	public String popBody;
	public boolean aceptar;

	public <T> void showWindow(Stage stage, Class<T> clase, boolean modal) {
		Parent root = fxWeaver.loadView(clase);
		if (modal) {
			Stage dialog = new Stage();
			dialog.setScene(new Scene(root));
			dialog.initStyle(StageStyle.TRANSPARENT);
			dialog.setResizable(false);
			dialog.initOwner(stage);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} else {
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		}
	}
	
	public void showLabelText(Label lblError, String msg, boolean ok) {
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
	
	public void showPopUp(Stage stage, PopUpType type, String body ) {
		this.popBody=body;
		this.popType=type;
		showWindow(stage, PopUpController.class, true);
	}

}

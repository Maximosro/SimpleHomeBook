package com.rothar.simplehomebook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;

@Component
public class WindowUtils {

	@Autowired
	FxWeaver fxWeaver;

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

}

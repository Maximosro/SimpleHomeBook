package com.rothar.simplehomebook.controller;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.rothar.simplehomebook.SBApplication;
import com.rothar.simplehomebook.service.LoginService;
import com.rothar.simplehomebook.service.VariableService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;

@Controller
public class JavaFxApplication extends Application {
	

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SBApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        validateFirst();
        
        Parent root = fxWeaver.loadView(LoginController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void validateFirst() {
    	LoginService userService = applicationContext.getBean(LoginService.class);
    	if(!userService.login("root", "ootr")) {
    		userService.createUser("root", "ootr", true);
    	}
    	
    	VariableService varService = applicationContext.getBean(VariableService.class);
    	
    	if(varService.getVariable("RECIBOS_PATH")==null) {
    		varService.crearVariable("RECIBOS_PATH", "C:\\");
    	}

	}

	@Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

}

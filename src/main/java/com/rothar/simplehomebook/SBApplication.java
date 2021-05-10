package com.rothar.simplehomebook;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.rothar.simplehomebook.controller.JavaFxApplication;

import javafx.application.Application;

@SpringBootApplication
@ComponentScan(basePackages = "com.rothar.simplehomebook")
public class SBApplication {

    public static void main(String[] args) {

        Application.launch(JavaFxApplication.class, args);
    }
}

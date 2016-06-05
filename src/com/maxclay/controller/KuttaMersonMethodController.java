package com.maxclay.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.maxclay.Main;
import com.maxclay.model.KuttaMersonMethodAlgorithm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class KuttaMersonMethodController implements Initializable {

	@FXML
	private Pane pane;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ImageView defaultFunctionImage = Main.loadDefaultFunctionImage();
		defaultFunctionImage.setLayoutX(415);
		defaultFunctionImage.setLayoutY(8);
		pane.getChildren().add(defaultFunctionImage);
		
		KuttaMersonMethodAlgorithm kuttaMersonMethodAlgorithm = new KuttaMersonMethodAlgorithm();
		kuttaMersonMethodAlgorithm.run(true);
		
		System.out.println(kuttaMersonMethodAlgorithm.getReport().get());
	}

}

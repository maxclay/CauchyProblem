package com.maxclay.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.maxclay.Main;
import com.maxclay.model.MiddlePointMethodAlgorithm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MiddlePointMethodController implements Initializable {

	@FXML
	private Pane pane;
	
	@FXML
	private Button startButton;
	
	@FXML
	private Button resetButton;
	
	@FXML
	private TextArea textArea;
	
	private MiddlePointMethodAlgorithm middlePoint;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ImageView defaultFunctionImage = Main.loadDefaultFunctionImage();
		defaultFunctionImage.setLayoutX(415);
		defaultFunctionImage.setLayoutY(8);
		pane.getChildren().add(defaultFunctionImage);
		
		NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X axis");
        yAxis.setLabel("Y axis");

        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);      
        lineChart.setTitle("Cauchy problem");
        lineChart.setLayoutX(410);
        lineChart.setLayoutY(69);
        lineChart.setPrefHeight(546);
        lineChart.setPrefWidth(800);
        
        final XYChart.Series<Number, Number> analyticalSolution = new XYChart.Series<>();
        analyticalSolution.setName("Analytical solution");
        lineChart.getData().add(analyticalSolution);
        
        final XYChart.Series<Number, Number> kuttaMersonSolution = new XYChart.Series<>();
        kuttaMersonSolution.setName("Middle point solution");        
        lineChart.getData().add(kuttaMersonSolution);
        
        pane.getChildren().add(lineChart);
		
        middlePoint = new MiddlePointMethodAlgorithm();
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				middlePoint.run(true);
				textArea.appendText(middlePoint.getReport().get());
				
				
				if(analyticalSolution.getData().size() == 0)
			        for(int i = 0; i < middlePoint.getArrayX().length; i++) {
			        	analyticalSolution.getData().add(new XYChart.Data<Number, Number>(middlePoint.getArrayX()[i],
			        			MiddlePointMethodAlgorithm.analyticalSolutionForDefaultEquation(middlePoint.getArrayX()[i])));
			        	
			        	kuttaMersonSolution.getData().add(new XYChart.Data<Number, Number>(middlePoint.getArrayX()[i], middlePoint.getArrayY()[i]));
			        }
		        	        
			}
		});
		
		resetButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				textArea.clear();
				analyticalSolution.getData().clear();
				kuttaMersonSolution.getData().clear();
			}
		}); 
		
	}

}

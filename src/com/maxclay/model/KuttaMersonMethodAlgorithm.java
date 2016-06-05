package com.maxclay.model;

import java.util.Optional;

public class KuttaMersonMethodAlgorithm {

	/**
	 * Algorithm accuracy.
	 */
	public static final double E = 1e-5;
	
	/**
	 * Number of stages. As Kutta-Merson method is five-stage method, value of STAGE_NUM is 5.
	 */
	public static final int STAGES_NUM = 5;
	
	/**
	 * Default number of control points.
	 */
	public static final int DEFAULT_CONTROL_POINTS_NUM = 33;
	
	/**
	 * Starting value x0 according to the next default proviso (x0;y0) = (0;1);
	 */
	public static final double STARTING_X = 0;
	
	/**
	 * Starting value y0 according to the next default proviso (x0;y0) = (0;1);
	 */
	public static final double STARTING_Y = 1;
	
	/**
	 * Default lower x boundary in x <mo>&#x2208;</mo> [LOWER_BOUNDARY; HIGHER_BOUNDARY];
	 */
	public static final int LOWER_BOUNDARY = 0;
	
	/**
	 * Default higher x boundary in x <mo>&#x2208;</mo> [LOWER_BOUNDARY; HIGHER_BOUNDARY];
	 */
	public static final int HIGHER_BOUNDARY = 5;
	
	
	private double[] functionValues;
	private double[] valuesX;
	private double[] valuesY;
	
	private StringBuilder reportBuilder;
	
	public KuttaMersonMethodAlgorithm() {
		
		functionValues = new double[STAGES_NUM];
		valuesX = new double[DEFAULT_CONTROL_POINTS_NUM];
		valuesY = new double[DEFAULT_CONTROL_POINTS_NUM];
		
	}
	
	/**
	 * Solves default differential equation <msup><mi>y</mi><mo>&#x2032;</mo></msup> = y - 2 * x / y;<br/>
	 * Finds corresponding 'y' values to each 'x' of set values, that formed using default boundaries and default number of control points.
	 * @param generateReport - specifies whether to generate string report or not. 
	 * String report can be available by using {@link #getReport() getReport()} method. 
	 */
	public void run(boolean generateReport) {
		
		if(generateReport)
			reportBuilder = new StringBuilder();
		
		double h = findStep(LOWER_BOUNDARY, HIGHER_BOUNDARY, DEFAULT_CONTROL_POINTS_NUM);
		double R = 1;
		
		valuesX[0] = STARTING_X;
		valuesY[0] = STARTING_Y;
		
		fillArrayX(valuesX, h);
		
         
		double tempY = 0;
        int i = 0;
        while(i < DEFAULT_CONTROL_POINTS_NUM - 1) {
         
        	// first stage
        	functionValues[0] = defaultFunction(valuesX[i], valuesY[i]);
        	 
        	// second stage
        	functionValues[1] = defaultFunction(valuesX[i] + h / 3, valuesY[i] + h * functionValues[0] / 3);
        	 
        	// third stage
        	functionValues[2] = defaultFunction(valuesX[i] + h / 3, valuesY[i] + h * functionValues[0] / 6 + h * functionValues[1] / 6);
        	 
        	// fourth stage
        	functionValues[3] = defaultFunction(valuesX[i] + h / 2, valuesY[i] + h * functionValues[0] / 8 + h * 3 * functionValues[2] / 8);
        	 
        	// fifth stage
        	functionValues[4] = defaultFunction(valuesX[i] + h, valuesY[i] + h * functionValues[0] / 2 - h * 3 * functionValues[2] / 2 + h * 2 * functionValues[3]);
        	         	 
        	valuesY[i + 1] = valuesY[i] + h / 6 * (functionValues[0] + 4 * functionValues[3] + functionValues[4]);
        	tempY = valuesY[i] + h / 2 * (functionValues[0] - 3 * functionValues[2] + 4 * functionValues[3]);
            R = 0.2 * Math.abs(valuesY[i + 1] - tempY);
            
            if(generateReport) {
    			reportBuilder.append("\nFunction values:");
    			reportBuilder.append("\nk1 = " + functionValues[0]);
    			reportBuilder.append("\nk2 = " + functionValues[1]);
    			reportBuilder.append("\nk3 = " + functionValues[2]);
    			reportBuilder.append("\nk4 = " + functionValues[3]);
    			reportBuilder.append("\nk5 = " + functionValues[4]);
    			
    			reportBuilder.append("\n\ny(" + (i + 1) + "): " + valuesY[i + 1]);
            }
            
            if (R > E) {
            	
            	h = h / 2;
            	if(generateReport) {
            		reportBuilder.append("\n\nR > E!(" + R + " > " + E + ")");
         			reportBuilder.append("\nDividing the integration step by 2");
         			reportBuilder.append("\nh = " + h);
            	}
            }
            else {
            	 
            	if(generateReport) {
            		reportBuilder.append("\n\nR < E(" + R + " < " + E + ")");
         			reportBuilder.append("\nObtained approximate value for y[" + (i + 1) + "] = " + valuesY[i + 1]);
         			reportBuilder.append("\nContinue iteration process");
         			reportBuilder.append("\n*****************************************************************\n\n");
            	}
            	i++;
            }
             
        }
        
        if(generateReport)
        	reportBuilder.append("\nIteration process ended");
         
	}
	
	/**
	 * Provides report about iteration process of solving Cauchy problem.
	 * @return report as an instance of {@link java.util.Optional Optional<T>} class.
	 */
	public Optional<String> getReport() {
		return (reportBuilder != null) ? Optional.of(reportBuilder.toString()) : Optional.<String>empty();
	}
	
	/**
	 * 
	 * @return array of x values.
	 */
	public double[] getArrayX() {
		return valuesX;
	}
	
	/**
	 * 
	 * @return array of y values.
	 */
	public double[] getArrayY() {
		return valuesY;
	}
	
	
	/**
	 * Represents function y - 2 * x / y in default differential equation
	 * <msup><mi>y</mi><mo>&#x2032;</mo></msup> = y - 2 * x / y;
	 * @param x - x parameter.
	 * @param y - y parameter.
	 * @return function result.
	 */
	public static double defaultFunction(double x, double y) {
		return y - 2 * x / y;
	}
	
	private void fillArrayX(double[] x, double h) {
        
		x[0] = 0;
        for (int i = 1; i < 33; i++)
            x[i] = x[i - 1] + h;
        
    }
	
	private double findStep(double lowerBoundary, double higherBoundary, int controlPointsNum) {
		return (higherBoundary - lowerBoundary) / controlPointsNum;
		
	}
}

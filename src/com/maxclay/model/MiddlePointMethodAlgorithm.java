package com.maxclay.model;

import java.util.Optional;

public class MiddlePointMethodAlgorithm {

	/**
	 * Algorithm accuracy.
	 */
	public static final double E = 1e-5;
	
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
	
	
	private double[] valuesX;
	private double[] valuesY;
	
	private StringBuilder reportBuilder;
	
	public MiddlePointMethodAlgorithm() {
		
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
		
		if(generateReport) {
			reportBuilder = new StringBuilder();
			reportBuilder.append("Solving Cauchy problem using middle point method");
		}
		
		double h = findStep(LOWER_BOUNDARY, HIGHER_BOUNDARY, DEFAULT_CONTROL_POINTS_NUM);
		
		valuesX[0] = STARTING_X;
		valuesY[0] = STARTING_Y;
		
		fillArrayX(valuesX, h);	
        
		if(generateReport)
			reportBuilder.append("\n\nStep h = " + h);
        int i = 0;
        while(i < DEFAULT_CONTROL_POINTS_NUM - 1) {
         
        	double k1 = defaultFunction(valuesX[i], valuesY[i]);
        	double xp = valuesX[i] + h / 2;
        	double yp = valuesY[i] + k1* h / 2;
        	double k2 = defaultFunction(xp, yp);
        	
        	valuesX[i + 1] = valuesX[i] + h;
        	valuesY[i + 1] = valuesY[i] + h * k2;
            
            if(generateReport) {
    			   		
            	reportBuilder.append("\n\nx(" + (i + 1) + "): " + valuesX[i + 1]);
    			reportBuilder.append("\ny(" + (i + 1) + "): " + valuesY[i + 1]);
    			reportBuilder.append("\n*****************************************************************\n\n");
            }
            
            i++;
             
        }
        
        if(generateReport) {
        	reportBuilder.append("\nIteration process ended");
        	reportBuilder.append("\n\nResults:");
        	printResult(reportBuilder, valuesX, valuesY);
        	reportBuilder.append("\n*****************************************************************\n");
        }
         
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
	
	/**
	 * Represents function sqrt(2x + 1) that is an analytical solution for default differential equation
	 * <msup><mi>y</mi><mo>&#x2032;</mo></msup> = y - 2 * x / y;
	 * @param x - x parameter.
	 * @return function result.
	 */
	public static double analyticalSolutionForDefaultEquation(double x) {
		return Math.sqrt(2 * x + 1);
	}
	
	private void fillArrayX(double[] x, double h) {
        
        for (int i = 1; i < x.length; i++)
            x[i] = x[i - 1] + h;
        
    }
	
	private void printResult(StringBuilder report, double[] valuesX, double[] valuesY) {
		
		for(int i = 0; i < valuesX.length; i++)
			report.append(String.format("\nx[%d] = %10.5f\ty[%d] = %10.5f", i, valuesX[i], i, valuesY[i]));
	}
	
	private double findStep(double lowerBoundary, double higherBoundary, int controlPointsNum) {
		return (higherBoundary - lowerBoundary) / controlPointsNum;
		
	}
}
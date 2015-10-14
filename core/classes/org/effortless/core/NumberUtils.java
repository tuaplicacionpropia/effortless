package org.effortless.core;

public class NumberUtils extends Object {

	public static double roundUp(double value, int places) {
		double result = 0.0;
	    if (places < 0) {
	    	throw new IllegalArgumentException();
	    }

	    java.math.BigDecimal bd = new java.math.BigDecimal(value);
	    bd = bd.setScale(places, java.math.RoundingMode.HALF_UP);
	    result = bd.doubleValue();
	    
	    return result;
	}
	
	public static double roundDown(double value, int places) {
		double result = 0.0;
	    if (places < 0) {
	    	throw new IllegalArgumentException();
	    }

	    java.math.BigDecimal bd = new java.math.BigDecimal(value);
	    bd = bd.setScale(places, java.math.RoundingMode.HALF_DOWN);
	    result = bd.doubleValue();
	    
	    return result;
	}
	
}

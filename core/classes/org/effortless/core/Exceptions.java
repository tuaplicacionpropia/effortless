package org.effortless.core;

/**
 * org.effortless.core.Exceptions.whereAmI();
 *
 */
public class Exceptions extends Object {

	protected Exceptions () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public static void printAllException (Throwable e) {
		if (e != null) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			if (cause != null && cause != e) {
				printAllException(cause);
			}
		}
	}
	
	public static void whereAmI () {
		try {
			throw new Exception();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
}

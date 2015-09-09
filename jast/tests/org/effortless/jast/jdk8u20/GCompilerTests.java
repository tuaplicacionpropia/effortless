package org.effortless.jast.jdk8u20;

import org.effortless.jast.jdk8u20.GAppJdk8u20;
import org.effortless.jast.jdk8u20.GCompilerJdk8u20;

public class GCompilerTests {

	public static void main (String[] args) {
		System.out.println("TEST");
		try {
			GAppJdk8u20 app = new GAppJdk8u20();
			app.setRootFile("/home/jmramoss/Descargas/projects/effortless-community/jast/tests/org/effortless/jast/jdk8u20/TestParse.java");
//			app.setBase("org.company.appname");
			
			GCompilerJdk8u20 compiler = new GCompilerJdk8u20();
			compiler.generate(app);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

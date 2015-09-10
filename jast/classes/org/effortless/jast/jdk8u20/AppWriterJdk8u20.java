package org.effortless.jast.jdk8u20;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;

import org.effortless.jast.GUnit;
import org.effortless.jast.GApp;
import org.effortless.jast.transforms.AppWriter;

public class AppWriterJdk8u20 extends Object implements AppWriter {

	public void write (GApp app) throws java.io.IOException {
		if (app != null) {
			java.util.List units = app.getUnits();
			int unitsLength = (units != null ? units.size() : 0);
			for (int i = 0; i < unitsLength; i++) {
				GUnit unit = (GUnit)units.get(i);
				write(unit);
			}
		}
	}

	public void write (GUnit unit) throws java.io.IOException {
		FileOutputStream out = null;
		java.io.BufferedWriter writer = null;
		
		try {
			String charset = "UTF-8";
			GAppJdk8u20 app = (GAppJdk8u20)unit.getApp();
			String rootDir = app.getBase();
			String base = unit.getPackageName();
			String unitName = unit.getName();
			
			File root = new File(rootDir);
			String baseDir = (base != null ? base.replaceAll("\\.", "/") : null);
			File folder = (baseDir != null ? new File(root, baseDir) : root);
			folder.mkdirs();
			File outFile = new File(folder, unitName + ".java");
//			outFile.getParentFile().mkdirs();
	
			try {
				out = new FileOutputStream(outFile);
			} catch (FileNotFoundException e) {
				throw new java.io.IOException(e.getMessage());
			}
			java.io.Writer rawWriter = new java.io.OutputStreamWriter(out, charset);
	
			writer = new java.io.BufferedWriter(rawWriter);
		
		
			JCCompilationUnit cu = ((GUnitJdk8u20)unit).getNode();
//			JavaFileObject sourceFile = cu.getSourceFile();
//			CharSequence content = sourceFile.getCharContent(true);
			CharSequence content = cu.toString();

			writer.append(content);
//			cu.accept(new PrettyPrinter(out, cu));
		} finally {
			if (out != null) {
				writer.flush();
				writer.close();
			}
		}
		
	}

}

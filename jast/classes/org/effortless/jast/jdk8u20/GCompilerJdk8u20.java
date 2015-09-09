package org.effortless.jast.jdk8u20;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

import org.effortless.jast.AppTransformer;
import org.effortless.jast.AppWriter;
import org.effortless.jast.GApp;

public class GCompilerJdk8u20 extends Object implements org.effortless.jast.GCompiler {

	public GCompilerJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.context = new Context();
		this.compiler = new JavaCompiler(context);
		this.compiler.keepComments = true;
		this.compiler.genEndPos = true;
		this.transformer = null;
		this.writer = new AppWriterJdk8u20();
	}
	
	protected AppTransformer transformer;
	
	public AppTransformer getTransformer () {
		return this.transformer;
	}
	
	public void setTransformer (AppTransformer newValue) {
		this.transformer = newValue;
	}
	
	protected AppWriter writer;
	
	public AppWriter getWriter () {
		return this.writer;
	}
	
	public void setWriter (AppWriter newValue) {
		this.writer = newValue;
	}
	
	private Context context;
	protected JavaCompiler compiler;
	
	protected JavaCompiler getCompiler () {
		return this.compiler;
	}

	public void parse (GApp app) throws java.io.IOException {
		((GAppJdk8u20)app).setCtx(this.context);
		String rootFile = app.getRootFile();

		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
		Iterable<? extends JavaFileObject> iterable = fm.getJavaFileObjects(rootFile);
		java.util.Iterator<? extends JavaFileObject> files = (iterable != null ? iterable.iterator() : null);
		if (files != null && files.hasNext()) {
			JavaFileObject jfo = files.next();

			JavaCompiler javac = getCompiler();
			javac.keepComments = true;
			JCCompilationUnit unit = javac.parse(jfo);
			if (javac.errorCount() > 0) {// 	At least one parse error. No point continuing (a real javac run doesn't either).
				throw new java.io.IOException("ERROR");
			}

			GUnitJdk8u20 rootUnit = new GUnitJdk8u20(unit);
			app.setRootUnit(rootUnit);
		}
	}
	
	public void transform (GAppJdk8u20 app) {
		if (app != null && this.transformer != null) {
			this.transformer.transform(app);
		}
	}
	
	public void write (GApp app) throws java.io.IOException {
		if (app != null && this.writer != null) {
			this.writer.write(app);
		}
	}
	
	public void generate (GAppJdk8u20 app) throws java.io.IOException {
		parse(app);
		transform(app);
		write(app);
	}

}

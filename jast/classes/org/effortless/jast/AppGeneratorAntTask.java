package org.effortless.jast;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.effortless.core.StringUtils;
import org.effortless.jast.jdk8u20.GAppJdk8u20;
import org.effortless.jast.jdk8u20.GCompilerJdk8u20;
import org.effortless.jast.transforms.BaseAppTransformer;

public class AppGeneratorAntTask extends Task {

	public AppGeneratorAntTask () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateSrc();
		initiateExt();
		initiateOut();
	}
	
	protected String src;
	
	protected void initiateSrc () {
		this.src = null;
	}
	
	public String getSrc () {
		return this.src;
	}
	
	public void setSrc (String newValue) {
		this.src = newValue;
	}

	protected String ext;
	
	protected void initiateExt () {
		this.ext = "app";
	}
	
	public String getExt () {
		return this.ext;
	}
	
	public void setExt (String newValue) {
		this.ext = newValue;
	}

	protected String out;
	
	protected void initiateOut () {
		this.out = null;
	}
	
	public String getOut () {
		return this.out;
	}
	
	public void setOut (String newValue) {
		this.out = newValue;
	}

    public void execute() {
    	try {
    		log("START");
    		String src = StringUtils.nullNotAllow(this.src);
    		File fileSrc = new File(src);
    		if (fileSrc.exists()) {
    			if (fileSrc.isDirectory()) {
    				File[] files = fileSrc.listFiles();
    				String ext = "." + this.ext;
    				for (File file : files) {
    					if (file != null && file.isFile() && file.getName().endsWith(ext)) {
    						generate(file.getAbsolutePath());
    					}
    				}
    			}
    			else {
    				generate(fileSrc.getAbsolutePath());
    			}
    		}
    		else {
    			log("You must define 'src' attribute.");
    		}
		} catch (IOException e) {
			throw new BuildException(e);
		}
    }
    
    protected void generate (String path) throws IOException {
		log("BEGIN: Generando la aplicación '" + path + "'");
    	GAppJdk8u20 app = new GAppJdk8u20();
    	app.setRootFile(path);
    	app.setBase(this.out);
    	
//    	GWebAppTransformer transformer = new GWebAppTransformer();
    	BaseAppTransformer transformer = new BaseAppTransformer();
    	GCompilerJdk8u20 compiler = new GCompilerJdk8u20();
    	compiler.setTransformer(transformer);
    	
    	compiler.generate(app);
		log("END: Generando la aplicación '" + path + "'");
    }

}

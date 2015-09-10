package org.effortless.jast;

import java.io.File;
import java.io.IOException;

import org.effortless.core.FilesystemChangeListener;
import org.effortless.jast.jdk8u20.GAppJdk8u20;
import org.effortless.jast.jdk8u20.GCompilerJdk8u20;
import org.effortless.jast.transforms.BaseAppTransformer;

public class AutoRefresh extends FilesystemChangeListener {

	public AutoRefresh () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateOutputDir();
	}
	
	protected void initiateDebug () {
		this.debug = false;
	}
	
	protected void initiateExtension () {
		this.extension = ".app";
	}
	
	protected void initiateListenFile () {
		this.listenFile = true;
	}
	
	protected String outputDir;
	
	protected void initiateOutputDir () {
		this.outputDir = null;
	}
	
	public String getOutputDir () {
		return this.outputDir;
	}
	
	public void setOutputDir (String newValue) {
		this.outputDir = newValue;
	}
	
    protected void fileCreateChange (File file) {
		generate(file);
    }

    public void start () {
    	initGenerate();
    	super.start();
    }
    
    protected void initGenerate () {
		File rootFile = new File(this.rootFolder);
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file != null && file.isFile() && file.getName().endsWith(".app")) {
				generate(file);
			}
		}
    }
    
    protected void generate (File file) {
    	try {
        	GAppJdk8u20 app = new GAppJdk8u20();
        	app.setRootFile(file.getAbsolutePath());
        	app.setBase(this.outputDir);
        	
//        	GWebAppTransformer transformer = new GWebAppTransformer();
        	BaseAppTransformer transformer = new BaseAppTransformer();
        	GCompilerJdk8u20 compiler = new GCompilerJdk8u20();
        	compiler.setTransformer(transformer);
        	
        	compiler.generate(app);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

}

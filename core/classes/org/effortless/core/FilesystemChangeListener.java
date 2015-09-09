package org.effortless.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.io.File;
import java.util.Date;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileAlterationListener;

public class FilesystemChangeListener extends Object {

	public FilesystemChangeListener () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateRootFolder();
		initiateExtension();
		initiateInterval();
		initiateDebug();
		initiateListenMonitor();
		initiateListenDirectory();
		initiateListenFile();
		initiateListenOnlyRootContent();
	}
	
	protected String rootFolder;
	
	protected void initiateRootFolder () {
		this.rootFolder = null;
	}
	
	public String getRootFolder () {
		return this.rootFolder;
	}
	
	public void setRootFolder (String newValue) {
		this.rootFolder = newValue;
	}
	
	protected String extension;
	
	protected void initiateExtension () {
		this.extension = null;
	}
	
	public String getExtension () {
		return this.extension;
	}
	
	public void setExtension (String newValue) {
		this.extension = newValue;
	}
	
	protected long interval;//unit ms
	
	protected void initiateInterval () {
		this.interval = 1000;
	}
	
	public long getInterval () {
		return this.interval;
	}
	
	public void setInterval (long newValue) {
		this.interval = newValue;
	}
	
	protected boolean debug;
	
	protected void initiateDebug () {
		this.debug = false;
	}
	
	public boolean getDebug () {
		return this.debug;
	}
	
	public void setDebug (boolean newValue) {
		this.debug = newValue;
	}
	
	protected boolean listenMonitor;
	
	protected void initiateListenMonitor () {
		this.listenMonitor = false;
	}
	
	public boolean getListenMonitor () {
		return this.listenMonitor;
	}
	
	public void setListenMonitor (boolean newValue) {
		this.listenMonitor = newValue;
	}
	
	protected boolean listenDirectory;
	
	protected void initiateListenDirectory () {
		this.listenDirectory = false;
	}
	
	public boolean getListenDirectory () {
		return this.listenDirectory;
	}
	
	public void setListenDirectory (boolean newValue) {
		this.listenDirectory = newValue;
	}
	
	protected boolean listenFile;
	
	protected void initiateListenFile () {
		this.listenFile = true;
	}
	
	public boolean getListenFile () {
		return this.listenFile;
	}
	
	public void setListenFile (boolean newValue) {
		this.listenFile = newValue;
	}
	
	protected boolean listenOnlyRootContent;
	
	protected void initiateListenOnlyRootContent () {
		this.listenOnlyRootContent = true;
	}
	
	public boolean getListenOnlyRootContent () {
		return this.listenOnlyRootContent;
	}
	
	public void setListenOnlyRootContent (boolean newValue) {
		this.listenOnlyRootContent = newValue;
	}
	
//	public static final String D1 = "/home/jmramoss/Descargas/apache-tomcat-8.0.14/webapps/jc/WEB-INF/classes";
    
    
    public void start() {
    	try {
    		// Change this to match the environment you want to watch.
    		final File directory = new File(this.rootFolder);
    		FileAlterationObserver fao = new FileAlterationObserver(directory);
    		fao.addListener(new FileAlterationListener () {

    			public void onStart(final FileAlterationObserver observer) {
    				_runOnStart(observer);
    			}

				public void onDirectoryCreate(final File directory) {
					_runOnDirectoryCreate(directory);
    			}

				public void onDirectoryChange(final File directory) {
					_runOnDirectoryChange(directory);
    			}

				public void onDirectoryDelete(final File directory) {
					_runOnDirectoryDelete(directory);
    			}

				public void onFileCreate(final File file) {
					_runOnFileCreate(file);
    			}

				public void onFileChange(final File file) {
					_runOnFileChange(file);
    			}

				public void onFileDelete(final File file) {
					_runOnFileDelete(file);
    			}

    			public void onStop(final FileAlterationObserver observer) {
    				_runOnStop(observer);
    			}

    		});
    	
    		final FileAlterationMonitor monitor = new FileAlterationMonitor(this.interval);
	
    		monitor.addObserver(fao);
//       System.out.println("Starting monitor. CTRL+C to stop.");
    		monitor.start();
			if (true) {
				Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

					@Override
					public void run() {
						try {
//							System.out.println("Stopping monitor.");
							monitor.stop();
						} catch (Exception ignored) {
						}
					}
				}));
			}
    	}
    	catch (Exception e) { throw new RuntimeException(e); }
    }
    
    
    
	protected void logDebugOnStart(FileAlterationObserver observer) {
		if (this.debug) {
			System.out.println("The WindowsFileListener has started on " + observer.getDirectory().getAbsolutePath());
		}
	}

	protected void logDebugOnDirectoryCreate(File directory) {
		if (this.debug) {
			logDebug(directory.getAbsolutePath() + " was created.");
		}
	}

	protected void logDebugOnDirectoryChange(File directory) {
		if (this.debug) {
			logDebug(directory.getAbsolutePath() + " was modified.");
		}
	}

	protected void logDebugOnDirectoryDelete(File directory) {
		if (this.debug) {
			logDebug(directory.getAbsolutePath() + " was deleted.");
		}
	}

	protected void logDebugOnFileCreate(File file) {
		if (this.debug) {
			logDebug(file.getAbsolutePath() + " was created.");
		}
	}

	protected void logDebugOnFileChange(File file) {
		if (this.debug) {
			logDebug(file.getAbsolutePath() + " was modified.");
		}
	}

	protected void logDebugOnFileDelete(File file) {
		if (this.debug) {
			logDebug(file.getAbsolutePath() + " was deleted.");
		}
	}

	protected void logDebugOnStop(FileAlterationObserver observer) {
		if (this.debug) {
		       System.out.println("The WindowsFileListener has stopped on " + observer.getDirectory().getAbsolutePath());
		}
	}

    protected void logDebug (String msg) {
    	if (this.debug) {
    		System.out.println(msg);
    	}
    }

    
    protected void _runOnStart(final FileAlterationObserver observer) {
    	if (this.listenMonitor) {
    		logDebugOnStart(observer);
    		runOnStart(observer);
    	}
	}

    protected void _runOnDirectoryCreate(final File directory) {
		if (this.listenDirectory) {
			logDebugOnDirectoryCreate(directory);
			runOnDirectoryCreate(directory);
		}
	}

    protected void _runOnDirectoryChange(final File directory) {
		if (this.listenDirectory) {
			logDebugOnDirectoryChange(directory);
			runOnDirectoryChange(directory);
		}
	}

    protected void _runOnDirectoryDelete(final File directory) {
		if (this.listenDirectory) {
			logDebugOnDirectoryDelete(directory);
			runOnDirectoryDelete(directory);
		}
	}

    protected void _runOnFileCreate(final File file) {
		if (this.listenFile && checkValidFile(file)) {
			logDebugOnFileCreate(file);
			runOnFileCreate(file);
		}
	}

    protected void _runOnFileChange(final File file) {
		if (this.listenFile && checkValidFile(file)) {
			logDebugOnFileChange(file);
			runOnFileChange(file);
		}
	}

    protected void _runOnFileDelete(final File file) {
		if (this.listenFile && checkValidFile(file)) {
			logDebugOnFileDelete(file);
			runOnFileDelete(file);
		}
	}

    protected void _runOnStop(final FileAlterationObserver observer) {
		if (this.listenMonitor) {
			logDebugOnStop(observer);
			runOnStop(observer);
		}
	}
    

    
    
    
    
    protected void runOnStart(final FileAlterationObserver observer) {
	}

    protected void runOnDirectoryCreate(final File directory) {
	}

    protected void runOnDirectoryChange(final File directory) {
	}

    protected void runOnDirectoryDelete(final File directory) {
	}

    protected void runOnFileCreate(final File file) {
    	fileCreateChange(file);
	}

    protected void runOnFileChange(final File file) {
    	fileCreateChange(file);
	}

    protected void runOnFileDelete(final File file) {
	}

    protected void runOnStop(final FileAlterationObserver observer) {
	}
    
    protected boolean checkValidFile (File file) {
    	boolean result = false;
    	String addr = file.getAbsolutePath();
		 if (addr.startsWith(this.rootFolder)) {
			 if (this.extension == null || this.extension.length() <= 0 || addr.endsWith(this.extension)) {
				 result = true;
			 }
			 if (result && this.listenOnlyRootContent) {
				 String addrParent = file.getParentFile().getAbsolutePath();
				 if (!addrParent.equals(this.rootFolder)) {
					 result = false;
				 }
			 }
		 }
    	return result;
    }

    protected String getRelativeFile (File file) {
    	String result = null;
    	result = file.getAbsolutePath();
    	result = result.substring((this.rootFolder + File.separator).length());
    	return result;
    }
    
    protected String getRelativeFileNoExtension (File file) {
    	String result = null;
    	result = getRelativeFile(file);
    	if (this.extension != null && this.extension.length() > 0) {
    		result = result.substring(0, result.length() - this.extension.length());
    	}
    	return result;
    }
    
    protected String getFullClassName (File file) {
    	String result = null;
    	result = getRelativeFileNoExtension(file);
    	result = result.replaceAll(File.separator, ".");
    	return result;
    }
    
    protected void fileCreateChange (File file) {
    }

}

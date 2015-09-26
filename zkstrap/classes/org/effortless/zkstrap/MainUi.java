package org.effortless.zkstrap;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Richlet;
import org.zkoss.zk.ui.RichletConfig;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.metainfo.LanguageDefinition;

public class MainUi extends Object implements Richlet {

	public MainUi () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public LanguageDefinition getLanguageDefinition() {
		return LanguageDefinition.lookup("xul/html");
	}

	@Override
	public void init(RichletConfig config) {
	}

	@Override
	public void service(Page page) throws Exception {
		String requestPath = page.getRequestPath();
		Session session = Sessions.getCurrent();
//		setupApps();
		Object nativeSession = (session != null ? session.getNativeSession() : null);
		AdminApp app = null;
		if (false && requestPath.contains("myapp")) {
			String className = "org.effortless.myapp.MainUI";
			Class clazz = Class.forName(className);
			app = (AdminApp)clazz.newInstance();
		}
		final String _prefixApp = "/app/";
		if (requestPath.startsWith(_prefixApp)) {
			String appName = requestPath.substring(_prefixApp.length());
			appName = (appName != null ? appName.trim() : "");
			if (appName.length() > 0) {
				String className = "org.effortless." + appName + ".MainUI";
				Class clazz = Class.forName(className);
				app = (AdminApp)clazz.newInstance();
			}
		}
//		app = (app != null ? app : new MyApp());
		if (app != null) {
			app.setAttribute("CTRL", app);
			app.buildApp();
			app.setPage(page);
		}
	}

	protected void setupApps() throws Exception {
		findPackageNamesStartingWith(null);
		String resName = "META-INF/annotations/";
		Enumeration resources = getClass().getClassLoader().getResources(resName);
		while (resources.hasMoreElements()) {
	    	URL nextElement = (URL)resources.nextElement();
	    	URI uri = (nextElement != null ? nextElement.toURI() : null);
	    	if (uri != null) {
	    		java.io.File file = new java.io.File(uri);
	    		if (file != null && file.exists()) {
	    			if (file.isDirectory()) {
	    	    		System.out.println(">>>>>>>>>> DIRECTORIO = " + file);
	    			}
	    			else {
	    	    		System.out.println(">>>>>>>>>> ARCHIVO = " + file);
	    			}
	    		}
	    		System.out.println(">>>>>>>>>> nextElement = " + nextElement);
	    	}
		}
	}
	
	public List<String> findPackageNamesStartingWith(String prefix) {
	    List<String> result = new ArrayList<>();
	    Package[] packages = Package.getPackages();
	    for(Package p : packages) {
	    	System.out.println(">>>>>>> pkg = " + p.getName());
	        if (prefix != null && p.getName().startsWith(prefix)) {
	           result.add(p.getName());
	        }
	    }
	    return result;
	}	
	
}

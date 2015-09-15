package org.effortless.zkstrap;

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
		Object nativeSession = (session != null ? session.getNativeSession() : null);
		AdminApp app = null;
		if (requestPath.contains("myapp")) {
			String className = "org.effortless.myapp.MainUI";
			Class clazz = Class.forName(className);
			app = (AdminApp)clazz.newInstance();
		}
		app = (app != null ? app : new MyApp());
		app.setAttribute("CTRL", app);
		app.buildApp();
		app.setPage(page);
	}
	
	
}

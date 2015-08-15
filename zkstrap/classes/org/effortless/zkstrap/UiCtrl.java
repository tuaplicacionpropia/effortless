package org.effortless.zkstrap;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class UiCtrl extends org.zkoss.zk.ui.GenericRichlet {

	public UiCtrl () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.app = null;
	}
	
	public void service(Page page) throws Exception {
		String requestPath = page.getRequestPath();
		Session session = Sessions.getCurrent();
		Object nativeSession = (session != null ? session.getNativeSession() : null);
		if (true || this.app == null) {
			this.app = new AdminApp();
			if (this.app != null) {
				this.app.setAttribute("CTRL", this);
				buildApp();
			}
		}
		this.app.setPage(page);
	}

	protected AdminApp app;
	
	protected void buildApp() {
	}

}

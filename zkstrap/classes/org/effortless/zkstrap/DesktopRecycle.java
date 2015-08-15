package org.effortless.zkstrap;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;

public class DesktopRecycle extends Object implements org.zkoss.zk.ui.util.DesktopRecycle {

	@Override
	public void afterRemove(Session session, Desktop desktop) {
		System.out.println("DesktopRecycle.afterRemove");
	}

	@Override
	public void afterService(Desktop desktop) {
		System.out.println("DesktopRecycle.afterService");
	}

	@Override
	public boolean beforeRemove(Execution exec, Desktop desktop, int cause) {
		boolean result = true;
		System.out.println("DesktopRecycle.beforeRemove");
		return result;
	}

	@Override
	public Desktop beforeService(Execution exec, String uri) {
		Desktop result = null;
		result = exec.getDesktop();
		System.out.println("DesktopRecycle.beforeService");
		return result;
	}
	 
}

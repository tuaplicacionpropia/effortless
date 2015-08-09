package org.effortless.zkstrap;

import java.io.IOException;
import java.util.Locale;

import org.zkoss.util.Locales;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.device.AjaxDevice;
import org.zkoss.zk.device.Devices;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.http.Wpds;
import org.zkoss.zk.ui.metainfo.LanguageDefinition;
import org.zkoss.zk.ui.metainfo.MessageLoader;
import org.zkoss.zk.ui.util.Clients;

public class AppDevice extends AjaxDevice {

	public void reloadMessages(Locale locale) throws IOException {
		if (locale == null)
			locale = Locales.getCurrent();
System.out.println("HOLA");
		final StringBuffer sb = new StringBuffer(4096);
		final Locale oldl = Locales.setThreadLocal(locale);
		try {
			final Execution exec = Executions.getCurrent();
			sb.append(Devices.loadJavaScript(exec, "~./js/zk/lang/msgzk*.js"));
			sb.append(Wpds.outLocaleJavaScript());
			for (LanguageDefinition langdef : LanguageDefinition.getByDeviceType(getType()))
				for (MessageLoader loader : langdef.getMessageLoaders())
					loader.load(sb, exec);
		} finally {
			Locales.setThreadLocal(oldl);
		}
		Clients.response("zk.reload", new AuScript(null, sb.toString()));
	}	
	
}

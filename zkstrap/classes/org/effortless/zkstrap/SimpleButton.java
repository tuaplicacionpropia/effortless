package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentCtrl;

public class SimpleButton extends org.zkoss.zk.ui.HtmlBasedComponent {

	private String _value = ""; // a data member

	public String getValue() {
		return _value;
	}
	
	public void setValue(String value) {
		if (!_value.equals(value)) {
			_value = value;
			smartUpdate("value", _value);
		}
	}
	
	private boolean _cleared = false; // a data member

	public boolean getCleared() {
		return _cleared;
	}
	
	public void setCleared(boolean cleared) {
		if (_cleared != cleared) {
			_cleared = cleared;
			smartUpdate("cleared", _cleared);
		}
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "value", _value);
		render(renderer, "cleared", _cleared);
	}
	
	
	static {
		addClientEvent(SimpleButton.class, ClearEvent.NAME, CE_IMPORTANT|CE_REPEAT_IGNORE);
		}	
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}
	
	
	
	public void service(org.zkoss.zk.au.AuRequest request, boolean
			everError) {
			final String cmd = request.getCommand();
//			System.out.println("SSSSSSSSERVICE >>>>>>>>>>>" + cmd);
			if (cmd.equals(ClearEvent.NAME)) {
			ClearEvent evt = ClearEvent.getClearEvent(request);
			_cleared = evt.getCleared();
//			setCleared(!_cleared);
			Events.postEvent(evt);
			} else
			super.service(request, everError);
			}

}

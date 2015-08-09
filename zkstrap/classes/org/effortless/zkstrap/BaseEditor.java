package org.effortless.zkstrap;

import java.util.Map;

import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;

public class BaseEditor extends org.zkoss.zk.ui.HtmlBasedComponent {

	public BaseEditor () {
		super();
		initiate();
	}
	
	protected void initiate () {
//		this.appendChild(new Layout());
//		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
	protected Object _value = ""; // a data member

	public Object getValue() {
		return this._value;
	}
	
	public void setValue(Object value) {
		Object _oldValue = this._value;
		if (!ObjectAccess.equals(_oldValue, value)) {
			this._value = value;
			ObjectAccess.setBean(this, this._value);
		}
	}
	
	public boolean appendChild (Component child) {
		boolean result = false;
		if (child != null) {
			Object value = getValue();
			ObjectAccess.setBean(child, value);
		}
		result = super.appendChild(child);
		return result;
	}
	
	public boolean insertBefore (Component newChild, Component refChild) {
		boolean result = false;
		if (newChild != null) {
			Object value = getValue();
			ObjectAccess.setBean(newChild, value);
		}
		result = super.insertBefore(newChild, refChild);
		return result;
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
	}

//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}

//	static {
//		addClientEvent(BaseEditor.class, Events.ON_CHANGE, CE_IMPORTANT|CE_REPEAT_IGNORE);
//	}
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}

//	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
//		final String cmd = request.getCommand();
//		if (Events.ON_CHANGE.equals(cmd)) {
//			final Component comp = request.getComponent();
//			final Map data = request.getData();
//			Object oldValue = getValue();
//			InputEvent evt = InputEvent.getInputEvent(request, oldValue);
//			Object value = _fromClient(data.get("value"));
//			setValue(value);
//			Events.postEvent(evt);
//		} 
//		else {
//			super.service(request, everError);
//		}
//	}

}

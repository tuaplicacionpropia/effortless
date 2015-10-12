package org.effortless.zkstrap;

import java.util.Map;

import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.event.Events;

public class Btn extends AbstractComponent {

	public Btn () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.setZclass("btn btn-default");
//		this.setSclass("default");
	}
	
	protected String label = "";

	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String newValue) {
		if (!this.label.equals(newValue)) {
			this.label = newValue;
			smartUpdate("label", this.label);
		}
	}
	
	protected String name = "";

	public String getName() {
		return this.name;
	}
	
	public void setName(String newValue) {
		boolean startWith = (newValue.startsWith("@"));
		newValue = (startWith ? newValue.substring(1) : newValue);
		if (startWith) {
			this.setAttribute("ON_SELECTION", Boolean.TRUE);
		}
		else {
			this.removeAttribute("ON_SELECTION");
		}
		if (!this.name.equals(newValue)) {
			this.name = newValue;
			smartUpdate("name", this.name);
		}
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "label", this.label);
		render(renderer, "name", this.name);
	}

	public void setBclass (String newValue) {
		setZclass("btn btn-" + newValue);
	}

	static {
		addClientEvent(Btn.class, Events.ON_CLICK, CE_IMPORTANT|CE_REPEAT_IGNORE);
	}	
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}
	
	
	
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		if (cmd.equals(Events.ON_CLICK)) {
			final Map data = request.getData();
			String btnName = (String)data.get("name");
			btnName = (btnName != null ? btnName.trim() : "");
			boolean onCmp = (btnName.indexOf("#") == 0);
			Boolean _onSelection = (Boolean)this.getAttribute("ON_SELECTION");
			boolean onSelection = (_onSelection != null && _onSelection.booleanValue());
			if (onSelection) {
				ObjectAccess.runMethodCmpBean(this, "executeCustom", btnName);
			}
			else if (!onCmp) {
				ObjectAccess.runMethod(this, btnName);
			}
			else {
				btnName = btnName.substring(1);
				ObjectAccess.runMethodCmpBean(this, btnName);
			}
		}
		else {
			super.service(request, everError);
		}
	}

}

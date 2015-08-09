package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Events;

public class AdminApp extends org.zkoss.zk.ui.HtmlBasedComponent {

	public AdminApp () {
		super();
		initiate();
	}
	
	protected void initiate () {
//		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
	public String label = ""; // a data member

	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String newValue) {
		if (!this.label.equals(newValue)) {
			this.label = newValue;
			smartUpdate("label", this.label);
		}
	}

	
	public String skin = ""; // a data member

	public String getSkin() {
		return this.skin;
	}
	
	public void setSkin(String newValue) {
		if (!this.skin.equals(newValue)) {
			this.skin = newValue;
			smartUpdate("skin", this.skin);
		}
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "label", this.label);
		render(renderer, "skin", this.skin);
	}

	
	
	static {
		addClientEvent(AdminApp.class, Events.ON_CLICK, CE_IMPORTANT|CE_REPEAT_IGNORE);
	}
	
//	public void service(org.zkoss.zk.au.AuRequest request, boolean
//			everError) {
//			final String cmd = request.getCommand();
////			System.out.println("SSSSSSSSERVICE >>>>>>>>>>>" + cmd);
//			if (cmd.equals(ClearEvent.NAME)) {
//			ClearEvent evt = ClearEvent.getClearEvent(request);
//			_cleared = evt.getCleared();
////			setCleared(!_cleared);
//			Events.postEvent(evt);
//			} else
//			super.service(request, everError);
//			}


	
//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}
	
//	public void setSclass (String newValue) {
//		newValue = "label-" + newValue;
//		super.setSclass(newValue);
//	}
	
}

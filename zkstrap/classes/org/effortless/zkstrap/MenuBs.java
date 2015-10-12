package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Events;

public class MenuBs extends AbstractComponent {

	public MenuBs () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
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
	
	public String name = ""; // a data member

	public String getName() {
		return this.name;
	}
	
	public void setName(String newValue) {
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
	
	static {
		addClientEvent(MenuBs.class, Events.ON_CLICK, CE_IMPORTANT|CE_REPEAT_IGNORE);
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

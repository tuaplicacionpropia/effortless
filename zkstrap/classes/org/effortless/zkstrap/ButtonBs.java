package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Events;

public class ButtonBs extends org.zkoss.zk.ui.HtmlBasedComponent {

	public ButtonBs () {
		super();
		initiate();
	}
	
	protected void initiate () {
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
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "label", this.label);
	}

	public void setBclass (String newValue) {
		setZclass("btn btn-" + newValue);
	}
/*	
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
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
*/
}

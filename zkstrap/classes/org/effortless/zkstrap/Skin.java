package org.effortless.zkstrap;

public class Skin extends org.zkoss.zk.ui.HtmlBasedComponent {

	public Skin () {
		super();
		initiate();
	}
	
	protected void initiate () {
//		this.setZclass("label label-default");
////		this.setSclass("default");
	}
	
	protected String name = ""; // a data member

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
		render(renderer, "name", this.name);
	}

//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}
	
//	public void setSclass (String newValue) {
//		newValue = "label-" + newValue;
//		super.setSclass(newValue);
//	}
	
}

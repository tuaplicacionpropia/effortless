package org.effortless.zkstrap;

public class Layout extends AbstractComponent {

	public Layout () {
		super();
		initiate();
	}
	
	protected void initiate () {
//		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
	protected String _type = "";

	public String getType() {
		return _type;
	}
	
	public void setType(String newValue) {
		if (!this._type.equals(newValue)) {
			this._type = newValue;
			smartUpdate("type", this._type);
		}
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "type", this._type);
	}

//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}
	
}

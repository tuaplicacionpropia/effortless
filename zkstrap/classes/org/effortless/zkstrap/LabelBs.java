package org.effortless.zkstrap;

public class LabelBs extends org.zkoss.zk.ui.HtmlBasedComponent {

	public LabelBs () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
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
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "value", _value);
	}

	public void setBclass (String newValue) {
		setZclass("label label-" + newValue);
	}
	
//	public void setSclass (String newValue) {
//		newValue = "label-" + newValue;
//		super.setSclass(newValue);
//	}
	
}

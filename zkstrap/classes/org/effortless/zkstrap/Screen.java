package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class Screen extends org.zkoss.zk.ui.HtmlBasedComponent {

	public Screen () {
		super();
		initiate();
	}

	public Screen (Component parent, Object value, String name) {
		this();
		setValue(value);
		setName(name);
		
		this.cmpRoot = buildSkeleton(parent);

		parent.appendChild(this);
	}
	
	protected Component buildSkeleton(Component parent) {
		Component result = null;

		Layout layout = new Layout();
		layout.setId("layout");
		this.appendChild(layout);
		
		result = layout;
		return result;
	}

	protected void initiate () {
		this.name = null;
//		this.appendChild(new Layout());
//		this.setZclass("label label-default");
//		this.setSclass("default");
		this.status = INIT;
		this.cmpRoot = null;
		this.lastCmp = null;
	}
	
	protected String name;
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
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

	protected byte status = 0;
	public static final byte INIT = 0;
	
	public byte getStatus () {
		return this.status;
	}
	
	public void setStatus (byte newValue) {
		this.status = newValue;
		updateCmpRoot();
	}

	protected void updateCmpRoot() {
	}

	protected Component cmpRoot;
	protected Component lastCmp;
	
	public boolean appendChild (Component child) {
		return insertBefore(child, null);
//		boolean result = false;
//		if (child != null) {
//			Object value = getValue();
//			ObjectAccess.setBean(child, value);
//		}
//		if (this.status == INIT || this.cmpRoot == null) {
//			result = nativeAppendChild(child);
//		}
//		else {
//			result = doAppendChild(child);
//		}
//		if (result) {
//			this.lastCmp = child;
//		}
//		return result;
	}
	

	public boolean insertBefore (Component child, Component refChild) {
		boolean result = false;
		if (child != null) {
			Object value = getValue();
			ObjectAccess.setBean(child, value);
		}
		if (this.status == INIT || this.cmpRoot == null) {
			result = nativeInsertBefore(child, null);
		}
		else {
			result = doInsertBefore(child, refChild);
		}
		if (result) {
			this.lastCmp = child;
		}
		return result;
	}
	
	
	
//	protected boolean doAppendChild(Component child) {
//		boolean result = false;
//		result = this.cmpRoot.appendChild(child);
//		return result;
//	}

	
	protected boolean doInsertBefore(Component child, Component refChild) {
		boolean result = false;
//		if (refChild == null) {
//			result = this.cmpRoot.appendChild(child);
//		}
//		else {
			result = this.cmpRoot.insertBefore(child, refChild);
//		}
		return result;
	}

	protected boolean nativeAppendChild (Component child) {
		return nativeInsertBefore(child, null);
//		return super.appendChild(child);
	}
	
	protected boolean nativeInsertBefore (Component child, Component refChild) {
		return super.insertBefore(child, refChild);
	}
	
	
//	protected void addCmp (Component cmp) {
//		this.cmpRoot.appendChild(cmp);
//	}
	
	public Screen addInput (String type, String name) {
		Input input = new Input();
		input.setType(type);
		input.setName(name);
		appendChild(input);
		return this;
	}

	public Screen addText (String name) {
		return addInput("text", name);
	}

	public Screen addInteger(String name) {
		return addInput("count", name);
	}

	public Screen addBoolean(String name) {
		return addInput("checkbox", name);
	}

	public Screen addPhone(String name) {
		return addInput("phone", name);
	}

	public Screen addIp(String name) {
		return addInput("ip", name);
	}

	public Screen addNumber(String name) {
		return addInput("number", name);
	}

	public Screen addCurrency(String name) {
		return addInput("currency", name);
	}

	public Screen addEmail(String name) {
		return addInput("email", name);
	}

	public Screen addColor(String name) {
		return addInput("color", name);
	}

	public Screen addSelect(String name) {
		return addInput("select", name);
	}

	public Screen addRadio(String name) {
		return addInput("radio", name);
	}

	public Screen addPassword(String name) {
		return addInput("password", name);
	}

	public Screen addTextArea(String name) {
		return addInput("comment", name);
	}

	public Screen addDate(String name) {
		return addInput("date", name);
	}

	public Screen addTime(String name) {
		return addInput("time", name);
	}
	
	public Screen addDateTime(String name) {
		return addInput("datetime", name);
	}

	public Screen addTable(String name) {
		return addInput("table", name);
	}

	public Screen addBtn(String name) {
		Btn btn = new Btn();
//		SimpleButton btn = new SimpleButton();
		btn.setName(name);
		appendChild(btn);
		return this;
	}

}

package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class EditorBuilder extends PageBuilder {

	protected EditorBuilder () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.status = 0;
	}
	
	public static EditorBuilder create (AdminApp app, Object value) {
		EditorBuilder result = null;
		result = new EditorBuilder();
		
		Editor editor = new Editor();
		editor.setValue(value);
		
		Layout layout = new Layout();
		editor.appendChild(layout);
		
		result.cmpRoot = layout;
		result.parentBuilder = null;
		
		app.appendChild(editor);
		
		return result;
	}
	
	public void addCmp (Component cmp) {
		this.cmpRoot.appendChild(cmp);
	}
	
	protected byte status = 0;
	
	protected static final byte INIT = 0;
	protected static final byte BUTTONS = 0;
	
	
}

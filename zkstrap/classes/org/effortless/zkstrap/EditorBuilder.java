package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class EditorBuilder extends PageBuilder {

	protected EditorBuilder () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.status = INIT;
	}
	
	public static EditorBuilder create (AdminApp app, Object value) {
		EditorBuilder result = null;
		result = new EditorBuilder();
		
		Editor editor = new Editor();
		editor.setValue(value);
		
		Layout layout = new Layout();
		editor.appendChild(layout);

		Layout layoutContent = new Layout();
		layout.appendChild(layoutContent);

		Layout layoutButtons = new Layout();
		layout.appendChild(layoutButtons);
		result.cmpRoot = layoutButtons;
		
		result.addBtn("#save");
		result.btnSave = result.lastCmp;
		result.addBtn("#cancel");
		

		
		result.status = CONTENT;
		result.cmpRoot = layoutContent;
		result.parentBuilder = null;
		
		app.appendChild(editor);
		
		return result;
	}
	
	public void addCmp (Component cmp) {
		if (this.status == INIT) {
			super.addCmp(cmp);
		}
		else {
			Btn _btn = null; try { _btn = (Btn)cmp; } catch (ClassCastException e) {}
			if (_btn != null) {
				if (this.status == CONTENT) {
					this.status = BUTTONS;
					Component layoutButtons = this.cmpRoot.getNextSibling();
					this.cmpRoot = layoutButtons;
				}
				this.cmpRoot.insertBefore(cmp, this.btnSave);
			}
			else {
				super.addCmp(cmp);
			}
		}
	}
	
	protected Component btnSave;
	protected byte status = 0;
	
	protected static final byte INIT = 0;
	protected static final byte CONTENT = 1;
	protected static final byte BUTTONS = 2;

}

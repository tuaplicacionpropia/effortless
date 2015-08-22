package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class Finder extends Screen {

	public Finder () {
		super();
//		org.zkoss.zk.ui.util.Template t = null;
//		
//		t.	
//		div filter
//		div top_buttons
//		div list
//		div bottom buttons
	}
	
	public Finder (Component parent, Object value, String name) {
		super(parent, value, name);
	}
	

	protected Object selection;
	
	public Object getSelection () {
		return selection;
	}
	
	public void setSelection (Object newValue) {
		this.selection = newValue;
	}
	
	public void search () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		data.put("selection", this.selection);
		data.put("op", "search");
		Event evt = new Event("onSearch", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder search " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void create () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "create");
		Event evt = new Event("onCreate", this, data);
		ObjectAccess.execAppAction(evt);
//		ObjectAccess.close(this);
	}
	
	public void read () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "read");
		Event evt = new Event("onRead", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder read " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void update () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "update");
		Event evt = new Event("onUpdate", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder update " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void delete () {
//		System.out.println("finder delete " + this._value);

		if (this.selection != null) {
			java.util.Map data = new java.util.HashMap();
			data.put("name", this.name);
			data.put("value", this.selection);
			data.put("op", "delete");
			Event evt = new Event("onDelete", this, data);
			ObjectAccess.execAppAction(evt);
		}
		
		
//		ObjectAccess.close(this);
	}
	
	public void onSelect (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object select = data.get("value");
		setSelection(select);
	}
	
	public void executeCustom (String action) {
		if (this.selection != null) {
			ObjectAccess.runMethodDirectly(this.selection, action);
			this.invalidate();
		}
	}
	
	
	
	
	
	
	
	
	
	public void setProperties (String newValue) {
		((Input)this.listTable).setProperties(newValue);
	}
	
	
	protected void buildLayoutFilter (Component parent) {
		Layout layoutFilter = null;
		{
			setStatus(INIT);
			
			layoutFilter = new Layout();
			parent.appendChild(layoutFilter);
			this.layoutFilter = layoutFilter;
			setStatus(FILTER);
			
//			this.status = FILTER;
//			this.cmpRoot = layoutFilterButtons;
		}
	}
	
	protected void buildLayoutFilterButtons (Component parent) {
		Layout layoutFilterButtons = null;
		{
			setStatus(INIT);
			
			layoutFilterButtons = new Layout();
			parent.appendChild(layoutFilterButtons);
			this.layoutFilterButtons = layoutFilterButtons;
			
//			this.status = FILTER_BUTTONS;
//			this.cmpRoot = layoutFilterButtons;
			
			setStatus(FILTER_BUTTONS);
			addBtn("#search");
			this.btnSearch = this.lastCmp;
		}
	}
	
	protected void buildLayoutList (Component parent) {
		Layout layoutList = null;
		{
			setStatus(INIT);
			
			layoutList = new Layout();
			parent.appendChild(layoutList);
			this.layoutList = layoutList;
			
			setStatus(LIST);
			this.addTable("#");
			this.listTable = this.lastCmp;
			
//			result.addText("name");
		}
	}
	
	protected void buildLayoutListButtons (Component parent) {
		Layout layoutListButtons = null;
		{
			setStatus(INIT);
			
			layoutListButtons = new Layout();
			parent.appendChild(layoutListButtons);
			this.layoutListButtons = layoutListButtons;
			setStatus(LIST_BUTTONS);
			
			this.addBtn("#create");
			Component btnCreate = this.lastCmp;
			this.addBtn("#read");
			this.addBtn("#update");
			this.addBtn("#delete");
			
			this.btnCreate = btnCreate;
		}
	}
	
	protected Component buildSkeleton(Component parent) {
		Component result = null;

		result = super.buildSkeleton(parent);
		
		buildLayoutFilter(result);
		buildLayoutFilterButtons(result);
		buildLayoutList(result);
		buildLayoutListButtons(result);
		
		setStatus(LIST_BUTTONS);
		result = this.layoutListButtons;
		
		return result;
	}

	protected void updateCmpRoot() {
		if (this.status == INIT) {
			this.cmpRoot = this.getFirstChild();
		}
		else if (this.status == FILTER) {
			this.cmpRoot = this.layoutFilter;
		}
		else if (this.status == FILTER_BUTTONS) {
			this.cmpRoot = this.layoutFilterButtons;
		}
		else if (this.status == LIST) {
			this.cmpRoot = this.layoutList;
		}
		else if (this.status == LIST_BUTTONS) {
			this.cmpRoot = this.layoutListButtons;
		}
	}

	protected boolean doInsertBefore(Component child, Component refChild) {
		boolean result = false;
		
		Btn btn = null; try { btn = (Btn)child; } catch (ClassCastException e) {}
		if (btn == null && this.status == LIST_BUTTONS) {
			result = this.layoutFilter.insertBefore(child, refChild);
		}
		else if (this.status == LIST_BUTTONS) {
			refChild = (refChild != null ? refChild : this.btnCreate);
			result = super.doInsertBefore(child, refChild);
		}
		else {
			result = super.doInsertBefore(child, refChild);
		}

		return result;
	}
	
	protected Component btnSearch;
	protected Component btnCreate;
	protected Component listTable;
	
	protected Component layoutFilter;
	protected Component layoutFilterButtons;
	protected Component layoutList;
	protected Component layoutListButtons;

	
	public static final byte FILTER = 1;
	public static final byte FILTER_BUTTONS = 2;
	public static final byte LIST = 3;
	public static final byte LIST_BUTTONS = 4;

}

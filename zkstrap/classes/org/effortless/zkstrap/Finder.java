package org.effortless.zkstrap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.effortless.orm.Entity;
import org.effortless.orm.Filter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
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
		Filter filter = null;
		try { filter = (Filter)this._value; } catch (ClassCastException e) {}
		if (filter != null && this.listTable != null) {
			this.listTable.invalidate();
		}
		else {
			java.util.Map data = new java.util.HashMap();
			data.put("name", this.name);
			data.put("value", this._value);
			data.put("selection", this.selection);
			data.put("op", "search");
			Event evt = new Event("onSearch", this, data);

			try {
				ObjectAccess.execAppAction(evt);
			}
			catch (UiException e) {
				Object _cause = e.getCause();
				NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
				if (cause != null) {
					System.out.println("DEFAULT myFinder$onSearch");

					AdminApp app = ObjectAccess.getApp(this);

					app.reopen(this.name);
				}
				else {
					throw e;
				}
			}
//			System.out.println("finder search " + this._value);
//			ObjectAccess.close(this);
		}
	}
	
	public void create () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "create");
		data.put("CALLER", this);
		Event evt = new Event("onCreate", this, data);

		try {
			ObjectAccess.execAppAction(evt);
		}
		catch (UiException e) {
			Object _cause = e.getCause();
			NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
			if (cause != null) {
				System.out.println("myFinder$onCreate");
				
//				MyBean obj = MyBean.buildMyBean("New");
				Object obj = null;
				
				Class classObject = null;
				java.util.List list = (java.util.List)this._value;
				
				Filter filter = null;
				try { filter = (Filter)list; } catch (ClassCastException e1) {}
				classObject = (filter != null ? filter.targetClass() : null);
				
				if (classObject == null) {
					try {
						classObject = (Class)MethodUtils.invokeStaticMethod(list.getClass(), "getTargetClass", null);
					} catch (NoSuchMethodException e1) {
						throw new UiException(e1);
					} catch (IllegalAccessException e1) {
						throw new UiException(e1);
					} catch (InvocationTargetException e1) {
						throw new UiException(e1);
					}
				}

				try {
					obj = classObject.newInstance();
				} catch (InstantiationException e2) {
					throw new UiException(e2);
				} catch (IllegalAccessException e2) {
					throw new UiException(e2);
				}
				
				
				data = (java.util.Map)evt.getData();
				data.put("value", obj);

				AdminApp app = ObjectAccess.getApp(this);
				String method = "menuEditor";
				try {
					MethodUtils.invokeExactMethod(app, method, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e1) {
					throw new UiException(e1);
				} catch (IllegalAccessException e1) {
					throw new UiException(e1);
				} catch (InvocationTargetException e1) {
					throw new UiException(e1);
				}
			}
			else {
				throw e;
			}
		}
//		ObjectAccess.close(this);
	}
	
	public void read () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "read");
		data.put("CALLER", this);
		Event evt = new Event("onRead", this, data);

		try {
			ObjectAccess.execAppAction(evt);
		}
		catch (UiException e) {
			Object _cause = e.getCause();
			NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
			if (cause != null) {
				System.out.println("myFinder$onRead");

				AdminApp app = ObjectAccess.getApp(this);
				String method = "menuEditor";
				try {
					MethodUtils.invokeExactMethod(app, method, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e1) {
					throw new UiException(e1);
				} catch (IllegalAccessException e1) {
					throw new UiException(e1);
				} catch (InvocationTargetException e1) {
					throw new UiException(e1);
				}

			}
			else {
				throw e;
			}
		}
//		System.out.println("finder read " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void update () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "update");
		data.put("CALLER", this);
		Event evt = new Event("onUpdate", this, data);

		try {
			ObjectAccess.execAppAction(evt);
		}
		catch (UiException e) {
			Object _cause = e.getCause();
			NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
			if (cause != null) {
				System.out.println("myFinder$onUpdate");

				AdminApp app = ObjectAccess.getApp(this);
				String method = "menuEditor";
				try {
					MethodUtils.invokeExactMethod(app, method, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e1) {
					throw new UiException(e1);
				} catch (IllegalAccessException e1) {
					throw new UiException(e1);
				} catch (InvocationTargetException e1) {
					throw new UiException(e1);
				}

			}
			else {
				throw e;
			}
		}
//		System.out.println("finder update " + this._value);
//		ObjectAccess.close(this);
	}

//	public void myFinder$onCreate (Event evt) {
//		System.out.println("myFinder$onCreate");
//		
//		MyBean obj = MyBean.buildMyBean("New");
//		java.util.Map data = (java.util.Map)evt.getData();
//		data.put("value", obj);
//		menuEditor(evt);
//	}
	
	
	
//	public void myFinder$onRead (Event evt) {
//		menuEditor(evt);
//	}
//	
//	public void myFinder$onUpdate (Event evt) {
//		System.out.println("myFinder$onUpdate");
//		menuEditor(evt);
//	}
	
	
	
	public void delete () {
//		System.out.println("finder delete " + this._value);

		if (this.selection != null) {
//			java.util.Map data = new java.util.HashMap();
//			data.put("name", this.name);
//			data.put("value", this.selection);
//			data.put("op", "delete");
//			data.put("CALLER", this);
//			Event evt = new Event("onDelete", this, data);
			
//			try {
//				ObjectAccess.execAppAction(evt);
//			}
//			catch (UiException e) {
//				Object _cause = e.getCause();
//				NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
//				if (cause != null) {
					AdminApp app = ObjectAccess.getApp(this);
					ConfirmScreen screen = new ConfirmScreen(app, this.selection, "myConfirm");
					screen.setType("delete");
					screen.setAttribute("CALLER", this);
//					System.out.println("myFinder$onDelete");
//				}
//				else {
//					throw e;
//				}
//			}
		}
		
		
//		ObjectAccess.close(this);
	}

	public void myConfirm (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");
		String op = (String)data.get("op");
		if ("ok".equals(op)) {
			
			Entity entity = null;
			try { entity = (Entity)value; } catch (ClassCastException e) {}
			if (entity != null) {
				entity.delete();
			}
			else {
				java.util.List list = (java.util.List)this._value;
				if (list.contains(value)) {
					list.remove(value);
				}
			}
		}
		ObjectAccess.close(evt.getTarget());
	}
	
	
	public void onSelect (Event evt) {
//		Input _list = null; try { _list = (Input)this.listTable; } catch (ClassCastException e) {}
//		_list.onSelect(evt);
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
	
	
	
	public void myEditor$onSave (Event evt) {
		Input _list = null; try { _list = (Input)this.listTable; } catch (ClassCastException e) {}
		_list.myEditor$onSave(evt);
//		System.out.println("myEditor$onSave");
//		java.util.Map data = (java.util.Map)evt.getData();
//		Object value = data.get("value");
//		
//		Entity entity = null;
//		try { entity = (Entity)value; } catch (ClassCastException e) {}
//		if (entity != null) {
//			entity.persist();
//		}
//		else {
//			java.util.List list = (java.util.List)this._value;
//			if (!list.contains(value)) {
//				list.add(value);
//			}
//		}
//		ObjectAccess.close(evt.getTarget());
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
//			addBtn("#search");
//			this.btnSearch = this.lastCmp;
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
			((Input)this.listTable).setSettings("modeFinder", "true");
			
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
			
//			this.addBtn("#create");
//			Component btnCreate = this.lastCmp;
//			this.addBtn("#read");
//			this.addBtn("#update");
//			this.addBtn("#delete");
			
//			this.btnCreate = btnCreate;
		}
	}
	
	protected Component buildSkeleton(Component parent) {
		Component result = null;

		result = this;//super.buildSkeleton(parent);
		
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
			refChild = (refChild != null ? refChild : this.lastBtn);
			result = super.doInsertBefore(child, refChild);
			this.lastBtn = child;
		}
		else {
			result = super.doInsertBefore(child, refChild);
		}

		return result;
	}
	
//	protected Component btnSearch;
//	protected Component btnCreate;
	protected Component lastBtn;
	protected Component listTable;
	
	protected Component layoutFilter;
	protected Component layoutFilterButtons;
	protected Component layoutList;
	protected Component layoutListButtons;

	
	public static final byte FILTER = 1;
	public static final byte FILTER_BUTTONS = 2;
	public static final byte LIST = 3;
	public static final byte LIST_BUTTONS = 4;

	static {
		addClientEvent(Finder.class, "onReq", CE_IMPORTANT|CE_REPEAT_IGNORE);
	}
	
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final Component comp = request.getComponent();
		final Map data = request.getData();
		final String cmd = (String)data.get("command");
		System.out.println(">>>>>>>>> command = " + cmd);
		if ("create".equals(cmd)) {
			this.create();
		}
		else if ("read".equals(cmd)) {
			this.read();
		}
		else if ("update".equals(cmd)) {
			this.update();
		}
		else if ("delete".equals(cmd)) {
			this.delete();
		}
		else if ("search".equals(cmd)) {
			this.search();
		}
		else {
			super.service(request, everError);
		}
	}

}

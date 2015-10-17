package org.effortless.zkstrap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.effortless.core.DateUtils;
import org.effortless.core.JsonUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.orm.Entity;
import org.effortless.orm.Filter;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.PropertyEntity;
import org.effortless.orm.impl.PropertyList;
import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;

public class Input extends AbstractComponent {

	public Input () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this._rawValue = null;
		this._readValue = true;
//		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
	protected Object _rawValue; // a data member
	protected boolean _readValue;
	
	protected void _setupListProperties (Object value) {
		PropertyList pList = null; try { pList = (PropertyList)value; } catch (ClassCastException e) {}
		if (pList != null && StringUtils.forceNotNull(this.properties).length() <= 0) {
			Class clazz = pList.getType();
			String listProperties = "";
			EntityDefinition def = pList.getTargetEntityDefinition();
			listProperties = StringUtils.forceNotNull(def.getFinderProperties());
			if (listProperties.length() <= 0) {
				java.util.List properties = def.getProperties();
				int length = (properties != null ? properties.size() : 0);
				for (int i = 0; i < length; i++) {
					PropertyEntity propertyEntity = (PropertyEntity)properties.get(i);
					String propertyName = (propertyEntity != null ? propertyEntity.getPropertyName() : null);
					listProperties = StringUtils.concat(listProperties, propertyName, ",");
					if (i > 4) {
						break;
					}
				}
			}
//			setProperties(listProperties);
			this.properties = listProperties;
//			System.out.println(">>>>>>>>>>> ");
		}
	}
	
	public Object getValue() {
		Object result = null;
		result = (this._readValue ? ObjectAccess.getProperty(this, this._name) : this._rawValue);
		_setupListProperties(result);
		this._rawValue = result;
		this._readValue = false;
		return result;
//		return _value;
	}
	
	public void setValue(Object value) {
		Object _oldValue = this.getValue();
		if (!ObjectUtils.equals(_oldValue, value)) {
			this._rawValue = value;
			this._readValue = false;
			ObjectAccess.setProperty(this, this._name, value);
			if (!this._$processingClient) {
				smartUpdate("value", _toClientValue(value));
			}
		}
	}
	
	public void reloadValue (Object value) {
		boolean hasChanges = (!this._readValue ? !ObjectUtils.equals(this._rawValue, value) : true);
		if (!this._$processingClient && hasChanges) {
			this._readValue = false;
			this._rawValue = value;
			smartUpdate("value", _toClientValue(value));
		}
	}

	protected Object selection;
	
	public Object getSelection() {
		return this.selection;
	}
	
	public void setSelection(Object newValue) {
		Object oldValue = this.selection;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.selection = newValue;
			
			java.util.Map data = new java.util.HashMap();
			data.put("name", this._name);
			data.put("oldValue", oldValue);
			data.put("value", this.selection);
			Event evt = new Event("onSelect", this, data);
			
			ObjectAccess.execEditorAction(evt);
		}
	}
	
	protected String properties = "";
	
	public String getProperties () {
		return this.properties;
	}
	
	public void setProperties (String newValue) {
		if (!this.properties.equals(newValue)) {
			this.properties = newValue;
			smartUpdate("properties", this.properties);
		}
	}

	protected Object _toClientValue (Object value) {
		Object result = null;
		if ("table".equals(this._type)) {
			String[] arrayProperties = (this.properties != null ? this.properties.split(",") : null);
			int numProperties = (arrayProperties != null ? arrayProperties.length : 0);
			String[][] array = null;
			java.util.Collection collection = (java.util.Collection)this.getValue();
			java.util.Iterator iterator = (collection != null ? collection.iterator() : null);
			int size = (collection != null ? collection.size() : 0);
			
			array = new String[size][numProperties];
			if (iterator != null) {
				int idx = 0;
				while (iterator.hasNext()) {
					Object item = iterator.next();
					if (item != null) {
						String[] itemProperties = new String[numProperties];
						for (int i = 0; i < numProperties; i++) {
							String property = arrayProperties[i];
							Object itemValue = ObjectAccess.readProperty(item, property);
							String itemValueStr = (String)itemValue;//_toClient(itemValue);
							itemProperties[i] = itemValueStr;
						}
						array[idx] = itemProperties;
						idx += 1;
					}
				}
			}
			result = array;
		}
		else {
			if ("checkbox".equals(this._type)) {
				result = (Boolean)value;
			}
			else {
				result = (value != null ? value.toString() : null);
			}
		}
		return result;
	}
	
	
	protected Object _toClient (Object value) {
		return (value != null ? value.toString() : null);
	}
	
	protected Object _fromClient (Object value) {
		Object result = null;
		if ("integer".equals(this._type) || "count".equals(this._type)) {
			result = (value != null ? Integer.valueOf((String)value) : null);
		}
		else if ("checkbox".equals(this._type)) {
//			result = (value != null ? Boolean.valueOf((String)value) : null);
			result = (Boolean)value;
		}
		else if ("number".equals(this._type)) {
			result = (value != null ? Double.valueOf((String)value) : null);
		}
		else if ("currency".equals(this._type)) {
			String text = org.effortless.core.StringUtils.forceNotNull((String)value);
			if (text.length() > 0) {
				String lastChar = text.substring(text.length() - 1);
				boolean lastNumber = org.effortless.core.StringUtils.contains(lastChar, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
				text = (!lastNumber ? text.substring(0, text.length() - 1) : text);
				result = Double.valueOf(text);
			}
		}
		else if ("date".equals(this._type)) {
			java.util.Date date = (value != null ? DateUtils.parse((String)value, "dd/MM/yyyy") : null);
			result = (date != null ? new java.util.Date(date.getTime()) : null);
		}
		else if ("time".equals(this._type)) {
			java.util.Date date = (value != null ? DateUtils.parse((String)value, "HH:mm") : null);
			result = (date != null ? new java.sql.Time(date.getTime()) : null);
		}
		else if ("datetime".equals(this._type)) {
			java.util.Date date = (value != null ? DateUtils.parse((String)value, "dd/MM/yyyy HH:mm") : null);
			result = (date != null ? new java.sql.Timestamp(date.getTime()) : null);
		}
		else {
			result = (value != null ? value.toString() : null);
		}
		return result;
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
	
	protected String _skin = "";

	public String getSkin() {
		return _skin;
	}
	
	public void setSkin(String newValue) {
		if (!this._skin.equals(newValue)) {
			this._skin = newValue;
			smartUpdate("skin", this._type);
		}
	}
	
	protected String _label = "";

	public String getLabel() {
		return _label;
	}
	
	public void setLabel(String newValue) {
		if (!this._label.equals(newValue)) {
			this._label = newValue;
			smartUpdate("label", this._label);
		}
	}
	
	protected String _name = "";

	public String getName() {
		return _name;
	}
	
	public void setName(String newValue) {
		if (!this._name.equals(newValue)) {
			this._name = newValue;
			smartUpdate("name", this._name);
		}
	}
	
	protected java.util.Collection _values;
	
	public java.util.Collection getValues () {
		return this._values;
	}
	
	public void setValues (java.util.Collection newValue) {
		if (!this._values.equals(newValue)) {
			this._values = newValue;
			smartUpdate("_values", this._valuesToClient());
		}
	}
	
	protected Object _valuesToClient () {
		Object result = null;
		result = this._values;
		result = new String[] {"Masculino", "Femenino"};
		return result;
	}

	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "value", _toClientValue(this.getValue()));
		render(renderer, "type", this._type);
		render(renderer, "skin", this._skin);
		render(renderer, "label", this._label);
		render(renderer, "name", this._name);
		render(renderer, "values", this._valuesToClient());
		render(renderer, "properties", this.properties);
	}

//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}

//	static {
////		addClientEvent(Input.class, Events.ON_CHANGE, CE_IMPORTANT|CE_REPEAT_IGNORE);
////		addClientEvent(Input.class, "onSelect", CE_IMPORTANT|CE_REPEAT_IGNORE);
////
////		addClientEvent(Input.class, "onCreateItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
////		addClientEvent(Input.class, "onReadItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
////		addClientEvent(Input.class, "onUpdateItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
////		addClientEvent(Input.class, "onDeleteItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
//
//		addClientEvent(Input.class, "onReq", CE_IMPORTANT|CE_REPEAT_IGNORE);
//	}
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}

	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
//		final String cmd = request.getCommand();
		final Component comp = request.getComponent();
		final Map data = request.getData();
		final String cmd = (String)data.get("command");
		
		if (Events.ON_CHANGE.equals(cmd)) {
//			Object oldValue = getValue();
//			InputEvent evt = InputEvent.getInputEvent(request, oldValue);
			Object value = _fromClient(data.get("value"));
			this._$processingClient = true;
			setValue(value);
			this._$processingClient = false;
//			Events.postEvent(evt);
		}
		else if ("onSelect".equals(cmd)) {
			String selectIdx = (String)data.get("value");
			int idx = Integer.valueOf(selectIdx).intValue();
			java.util.List value = null;
			try { value = (java.util.List)this._rawValue; } catch (ClassCastException e) {}
			if (value != null) {
				Object selection = value.get(idx);
				setSelection(selection);
			}
			System.out.println("onSelect");
		}
		else if ("onCreateItem".equals(cmd)) {
			System.out.println("onCreateItem");
			_doCreateItem();
		}
		else if ("onReadItem".equals(cmd)) {
			System.out.println("onReadItem");
			_doReadItem();
		}
		else if ("onUpdateItem".equals(cmd)) {
			System.out.println("onUpdateItem");
			_doUpdateItem();
		}
		else if ("onDeleteItem".equals(cmd)) {
			System.out.println("onDeleteItem");
			_doDeleteItem();
		}
		else {
			super.service(request, everError);
		}
	}

	public void _doCreateItem () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this._name);
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
				java.util.List list = (java.util.List)this._rawValue;
				
				Filter filter = null;
				try { filter = (Filter)list; } catch (ClassCastException e1) {}
				classObject = (filter != null ? filter.targetClass() : null);
				
				if (classObject == null && list != null) {
					try {
//						classObject = (Class)MethodUtils.invokeStaticMethod(list.getClass(), "targetClass", null);
						classObject = (Class)MethodUtils.invokeMethod(list, "targetClass", null);
					} catch (NoSuchMethodException e1) {
						throw new UiException(e1);
					} catch (IllegalAccessException e1) {
						throw new UiException(e1);
					} catch (InvocationTargetException e1) {
						throw new UiException(e1);
					}
				}
				
				data.put("name", "menuEditor" + StringUtils.capFirst(classObject.getSimpleName()));
				try {
					ObjectAccess.execAppAction(evt);
				}
				catch (UiException e2) {
					Object _cause2 = e2.getCause();
					NoSuchMethodException cause2 = null; try { cause2 = (NoSuchMethodException)_cause2; } catch (ClassCastException e22) {}
					if (cause2 != null) {
						data.put("name", this._name);
						try {
							obj = classObject.newInstance();
						} catch (InstantiationException _e2) {
							throw new UiException(_e2);
						} catch (IllegalAccessException _e2) {
							throw new UiException(_e2);
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
				}
			}
			else {
				throw e;
			}
		}
//		ObjectAccess.close(this);
	}
	
	public void _doReadItem () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this._name);
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

				data.put("name", "menuEditor" + StringUtils.capFirst(this.selection.getClass().getSimpleName()));
				try {
					ObjectAccess.execAppAction(evt);
				}
				catch (UiException e2) {
					Object _cause2 = e2.getCause();
					NoSuchMethodException cause2 = null; try { cause2 = (NoSuchMethodException)_cause2; } catch (ClassCastException e22) {}
					if (cause2 != null) {
						data.put("name", this._name);
						
						
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
				}
			}
			else {
				throw e;
			}
		}
//		System.out.println("finder read " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void _doUpdateItem () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this._name);
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

				data.put("name", "menuEditor" + StringUtils.capFirst(this.selection.getClass().getSimpleName()));
				try {
					ObjectAccess.execAppAction(evt);
				}
				catch (UiException e2) {
					Object _cause2 = e2.getCause();
					NoSuchMethodException cause2 = null; try { cause2 = (NoSuchMethodException)_cause2; } catch (ClassCastException e22) {}
					if (cause2 != null) {
						data.put("name", this._name);

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
	
	
	
	public void _doDeleteItem () {
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
				java.util.List list = (java.util.List)this._rawValue;
				if (list.contains(value)) {
					list.remove(value);
				}
			}
		}
		ObjectAccess.close(evt.getTarget());
	}
	
	public void myEditor$onSave (Event evt) {
		System.out.println("myEditor$onSave");
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");
		
		Screen screen = ObjectAccess.getFirstScreen(this);
		Finder finder = null; try { finder = (Finder)screen; } catch (ClassCastException e) {}
		boolean valid = false;
		if (finder != null) {
			Entity entity = null;
			try { entity = (Entity)value; } catch (ClassCastException e) {}
			if (entity != null) {
				entity.persist();
				valid = true;
			}
		}
		
		if (!valid) {
			java.util.List list = (java.util.List)this._rawValue;
			if (!list.contains(value)) {
				list.add(value);
			}
		}
		
		ObjectAccess.close(evt.getTarget());
	}
	
	public void onSelect (Event evt) {
		Component target = evt.getTarget();
		if (target != this) {
			java.util.Map data = (java.util.Map)evt.getData();
			Object select = data.get("value");
			setSelection(select);
		}
	}
	
	

	
	
}

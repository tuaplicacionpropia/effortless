package org.effortless.zkstrap;

import java.util.Map;

import org.effortless.core.DateUtils;
import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.PropertyEntity;
import org.effortless.orm.impl.PropertyList;
import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;

public class Input extends org.zkoss.zk.ui.HtmlBasedComponent {

	public Input () {
		super();
		initiate();
	}
	
	protected boolean _$processingClient;
	
	protected void initiate () {
		this._$processingClient = false;
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
			result = (value != null ? Double.valueOf((String)value) : null);
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

	static {
		addClientEvent(Input.class, Events.ON_CHANGE, CE_IMPORTANT|CE_REPEAT_IGNORE);
		addClientEvent(Input.class, "onSelect", CE_IMPORTANT|CE_REPEAT_IGNORE);
	}
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}

	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		if (Events.ON_CHANGE.equals(cmd)) {
			final Component comp = request.getComponent();
			final Map data = request.getData();
//			Object oldValue = getValue();
//			InputEvent evt = InputEvent.getInputEvent(request, oldValue);
			Object value = _fromClient(data.get("value"));
			this._$processingClient = true;
			setValue(value);
			this._$processingClient = false;
//			Events.postEvent(evt);
		}
		else if ("onSelect".equals(cmd)) {
			final Map data = request.getData();
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
		else {
			super.service(request, everError);
		}
	}

}

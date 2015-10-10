package org.effortless.zkstrap;

import org.effortless.core.JsonUtils;
import org.effortless.core.ObjectUtils;

public class AbstractComponent extends org.zkoss.zk.ui.HtmlBasedComponent {

	public AbstractComponent () {
		super();
		initiate();
	}
	
	protected boolean _$processingClient;
	
	protected void initiate () {
		this._$processingClient = false;
	}
	
	protected java.util.Map options = null;
	
	public java.util.Map getOptions () {
		return this.options;
	}
	
	public void setOptions (java.util.Map newValue) {
		if (!this.options.equals(newValue)) {
			this.options = newValue;
			smartUpdate("options", this.options);
		}
	}
	
	public Object getOptions (String name) {
		Object result = null;
		if (this.options != null && name != null) {
			result = this.options.get(name);
		}
		return result;
	}

	public Object setOptions (String name, Object newValue) {
		Object result = null;
		if (name != null) {
			this.options = (this.options != null ? this.options : new java.util.HashMap());
			result = this.options.put(name, newValue);
			if (!ObjectUtils.equals(result, newValue)) {
				String optionsJson = JsonUtils.toJson(this.options);
				smartUpdate("options", optionsJson);
			}
		}
		return result;
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);

		if (this.options != null) {
			String optionsJson = JsonUtils.toJson(this.options);
			render(renderer, "options", optionsJson);
		}
	}

	static {
//		addClientEvent(Input.class, Events.ON_CHANGE, CE_IMPORTANT|CE_REPEAT_IGNORE);
//		addClientEvent(Input.class, "onSelect", CE_IMPORTANT|CE_REPEAT_IGNORE);
//
//		addClientEvent(Input.class, "onCreateItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
//		addClientEvent(Input.class, "onReadItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
//		addClientEvent(Input.class, "onUpdateItem", CE_IMPORTANT|CE_REPEAT_IGNORE);
//		addClientEvent(Input.class, "onDeleteItem", CE_IMPORTANT|CE_REPEAT_IGNORE);

		addClientEvent(AbstractComponent.class, "onReq", CE_IMPORTANT|CE_REPEAT_IGNORE);
	}
	
	
//	 protected void updateByClient(String name, Object value) {
//			if ("cleared".equals(name))
//				System.out.println(">>>>>>>>>>>>HOLA");
//			else
//				super.updateByClient(name, value);
//		}

}

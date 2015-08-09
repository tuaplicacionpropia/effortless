package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class PageBuilder extends Object {

	protected PageBuilder () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.cmpRoot = null;
		this.parentBuilder = null;
	}
	
	protected Component cmpRoot;
	
	protected PageBuilder parentBuilder;
	
	public static PageBuilder createBaseEditor (AdminApp app, Object value) {
		PageBuilder result = null;
		result = new PageBuilder();
		BaseEditor editor = new BaseEditor();
		editor.setValue(value);
		Layout layout = new Layout();
		editor.appendChild(layout);
		result.cmpRoot = layout;
//		result.cmpRoot = editor;
		result.parentBuilder = null;
		app.appendChild(editor);
		return result;
	}
	
	public PageBuilder addInput (String type, String name) {
		PageBuilder result = null;
		Input input = new Input();
		input.setType(type);
		input.setName(name);
		this.cmpRoot.appendChild(input);
		result = this;
		return result;
	}

	public PageBuilder addText (String name) {
		return addInput("text", name);
	}

	public PageBuilder addInteger(String name) {
		return addInput("count", name);
	}

	public PageBuilder addBoolean(String name) {
		return addInput("checkbox", name);
	}

	public PageBuilder addPhone(String name) {
		return addInput("phone", name);
	}

	public PageBuilder addIp(String name) {
		return addInput("ip", name);
	}

	public PageBuilder addNumber(String name) {
		return addInput("number", name);
	}

	public PageBuilder addCurrency(String name) {
		return addInput("currency", name);
	}

	public PageBuilder addEmail(String name) {
		return addInput("email", name);
	}

	public PageBuilder addColor(String name) {
		return addInput("color", name);
	}

	public PageBuilder addSelect(String name) {
		return addInput("select", name);
	}

	public PageBuilder addRadio(String name) {
		return addInput("radio", name);
	}

	public PageBuilder addPassword(String name) {
		return addInput("password", name);
	}

	public PageBuilder addTextArea(String name) {
		return addInput("comment", name);
	}

	public PageBuilder addDate(String name) {
		return addInput("date", name);
	}

	public PageBuilder addTime(String name) {
		return addInput("time", name);
	}
	
	public PageBuilder addDateTime(String name) {
		return addInput("datetime", name);
	}

	
}

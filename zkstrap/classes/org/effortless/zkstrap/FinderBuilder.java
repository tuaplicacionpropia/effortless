package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class FinderBuilder extends PageBuilder {

	protected FinderBuilder () {
		super();
	}
	
	public static FinderBuilder create (AdminApp app, Object value) {
		FinderBuilder result = null;
		result = new FinderBuilder();
		
		Finder finder = new Finder();
		finder.setValue(value);
		
		Layout layout = new Layout();
		finder.appendChild(layout);
		
		result.cmpRoot = layout;
		result.parentBuilder = null;
		
		app.appendChild(finder);
		
		return result;
	}
	
	public void addCmp (Component cmp) {
		this.cmpRoot.appendChild(cmp);
	}
	
	
	
	
}

package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;

public class FinderBuilder extends PageBuilder {

	protected FinderBuilder () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.status = 0;
	}
	
	public static FinderBuilder create (AdminApp app, Object value) {
		FinderBuilder result = null;
		result = new FinderBuilder();
		
		Finder finder = new Finder();
		finder.setValue(value);
		
		Layout layout = new Layout();
		finder.appendChild(layout);

		Layout layoutFilter = null;
		{
			layoutFilter = new Layout();
			layout.appendChild(layoutFilter);
		}
		
		Layout layoutFilterButtons = null;
		{
			layoutFilterButtons = new Layout();
			layout.appendChild(layoutFilterButtons);
		}
		
		Layout layoutList = null;
		{
			layoutList = new Layout();
			layout.appendChild(layoutList);
		}
		
		Layout layoutListButtons = null;
		{
			layoutListButtons = new Layout();
			layout.appendChild(layoutListButtons);
		}
		
		result.status = FILTER;
		result.cmpRoot = layoutFilter;
		result.parentBuilder = null;
		
		app.appendChild(finder);
		
		return result;
	}
	
	public void addCmp (Component cmp) {
		this.cmpRoot.appendChild(cmp);
	}
	
	

	protected byte status = 0;
	
	protected static final byte INIT = 0;
	protected static final byte FILTER = 1;
	protected static final byte FILTER_BUTTONS = 2;
	protected static final byte LIST = 3;
	protected static final byte LIST_BUTTONS = 4;

}

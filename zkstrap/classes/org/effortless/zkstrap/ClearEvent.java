package org.effortless.zkstrap;

import java.util.Map;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentCtrl;

public class ClearEvent extends Event {

public ClearEvent(String name, Component target, Object data) {
		super(name, target, data);
		// TODO Auto-generated constructor stub
	}


public ClearEvent(String name, Component target) {
		super(name, target);
		// TODO Auto-generated constructor stub
	}


	public ClearEvent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	public static final String NAME = "onClear";

	
	private boolean _cleared = false; // a data member

	public boolean getCleared() {
		return _cleared;
	}
	
	public void setCleared(boolean cleared) {
		if (_cleared != cleared) {
			_cleared = cleared;
		}
	}
	
	
	
	public static final ClearEvent getClearEvent(AuRequest request) {
		final Component comp = request.getComponent();
		final Map data=request.getData();
		boolean cleared = AuRequests.getBoolean(data, "cleared");
		ClearEvent evt = new ClearEvent(request.getCommand(), comp, data);
		evt.setCleared(cleared);
		return evt;
		}
	
}

package org.effortless.orm.restrictions;

import java.util.List;

import org.effortless.core.UnusualException;

public class AndRestriction extends CompositeRestriction {

	public AndRestriction () {
		super();
	}
	
	public void check (Object value) {
		List items = getItems();
		int length = (items != null ? items.size() : 0);
		length = (length >= 0 ? length : 0);
		if (length > 0) {
			java.util.List listModelException = new java.util.ArrayList();
			for (int i = 0; i < length; i++) {
				Restriction restriction = (Restriction)items.get(i);
				if (restriction != null) {
					try {
						restriction.check(value);
					}
					catch (UnusualException e) {
						listModelException.add(e);
					}
				}
			}
			RestrictionUtils.throwList(listModelException);
		}
	}

}

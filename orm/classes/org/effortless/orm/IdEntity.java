package org.effortless.orm;

import org.effortless.core.UnusualException;

public interface IdEntity extends MarkDeleted {

	public Long getId();
	
//	public void setId (Integer newValue);
	
	public Integer getVersion();
	
//	public void setVersion (Integer newValue);
	
	public Boolean getDeleted();
	
//	public void setDeleted (Boolean newValue);

	public boolean isSameVersion() throws UnusualException;
	
	
}

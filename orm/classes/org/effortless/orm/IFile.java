package org.effortless.orm;

import java.io.File;

public interface IFile {

	public String getName();
	
	public void setName(String newValue);

	public String getDescription();

	public void setDescription(String newValue);

	public String getComment();

	public void setComment(String newValue);

	public String getContentType();

	public void setContentType(String newValue);

	public String getFormat();

	public void setFormat(String newValue);

	public File getContent();

	public void setContent(File newValue);

	public void setContent (String file);
	
	public void setContent (java.io.Reader reader);
	
	public void setContent (java.io.InputStream input);

	public void setContent (java.io.InputStream input, String name);

	public boolean hasContent ();
	
	public void clearContent ();
	
	public boolean isClearContent ();

	public Long getSize();

	public void setSize(Long newValue);

	public String getSizeText ();
	
	public String getPath();

	public void setPath(String newValue);
	
	public Boolean getEmbedded();

	public void setEmbedded(Boolean newValue);

}

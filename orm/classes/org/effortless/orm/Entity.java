package org.effortless.orm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Externalizable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Entity extends Externalizable, BindableBean, Referenciable, Bindable {

//	public Object doGetIdentifier();

//	public List<Type> listBy (String methodName, Object... parameters);
//	
//	public List<Type> listBy (String methodName, String orderBy, Object... parameters);
//
//	public Type findBy (String methodName, Object... parameters);
//	
//	public Type findBy (String methodName, String orderBy, Object... parameters);
//	
//	public Long countBy (String methodName, Object... parameters);

	public Serializable doGetIdentifier ();

	public static final String CALL_ON_READ = "Entity.CALL_ON_READ";
	
	public void onRead ();
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public boolean containsPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
	
	//@javax.persistence.Transient
	public List getPropertyChangeListeners();
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public List getPropertyChangeListeners(String propertyName);
	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
	public void firePropertyChange(PropertyChangeEvent evt);
	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue);

	public boolean hasListeners(String propertyName);

	public void fireIndexedPropertyChange(String propertyName, int index, boolean oldValue, boolean newValue);
	public void fireIndexedPropertyChange(String propertyName, int index, int oldValue, int newValue);
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue);
	public void firePropertyChange(String propertyName, int oldValue, int newValue);
	
	public PropertyChangeListener[] getPropertyChangeListenersArray();
	public PropertyChangeListener[] getPropertyChangeListenersArray(String propertyName);

	
	
	
	public boolean hasBeenChanged();
	public boolean hasBeenCreated();
	public boolean hasBeenDeleted();
	
	
	
	public java.util.Map getAttributes ();
	
	public void setAttributes (java.util.Map newValue);
	
	public boolean existsAttribute (String attribute);
	
	public Object getAttribute (String attribute, Object defaultValue);
	
	public Object getAttribute (String attribute);
	
	public Object setAttribute (String attribute, Object newValue);

	
	
	
	
	public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException;
	  
	public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException;

	
	
	
	
	public Boolean getCheckHasId ();
	
	public Object clone ();
	
	
	
	
	
	//RestrictionsEntity
	public void checkCreate ();// throws ModelException;
	
	public void checkCreate (String properties);// throws ModelException;
	
	public void checkUpdate ();// throws ModelException;
	
	public void checkUpdate (String properties);// throws ModelException;
	
	public boolean checkAccessible ();// throws ModelException;

	public void checkErase ();// throws ModelException;
	
	public void checkErase (String properties);// throws ModelException; 															

	public void checkDelete ();// throws ModelException;

	public void checkDelete (String properties);// throws ModelException;

	public void checkUndelete ();// throws ModelException;

	public void checkUndelete (String properties);// throws ModelException;

	public Boolean isReadonly ();
	
	public void checkPersist ();// throws ModelException;

	public void checkPersist (String properties);// throws ModelException;
	
	public boolean doCanSaveFinalData(boolean check, boolean create, boolean update);// throws ModelException;

	public boolean doCanLoadFinalData();// throws ModelException;

	public boolean checkForUpdate ();// throws ModelException;
	
	public void checkCreateCopy ();// throws ModelException;
	
	public void checkCreateCopy (String properties);// throws ModelException; 													

	public boolean checkPropertyLoadSecurity (String propertyName);// throws SecurityException;

	public boolean checkPropertySaveSecurity (String propertyName);// throws SecurityException;

	public boolean checkPropertySecurity (String propertyName);// throws SecurityException;

//	public boolean checkPropertySecurity (String propertyName, PropertyResourceMode mode);// throws SecurityException;
	
	
	
	
	//EntityEvents
	public Object onPreSaveReplace();// throws ModelException;

	public void enableNotifyChanges ();
	
	public void enableDisableChangeEvents ();

	public void disableDisableChangeEvents ();

	public void disableNotifyChanges ();

	
	public void onPreCreate ();// throws ModelException;
	public void onPostCreate ();// throws ModelException;
	
	public void onPreRead();// throws ModelException;
	public void onPostRead();// throws ModelException;

	public void onPreUpdate ();// throws ModelException;
	public void onPostUpdate ();// throws ModelException;

	public void onPreDelete();// throws ModelException;
	public void onPostDelete();// throws ModelException;

	public void onPreErase();// throws ModelException;
	public void onPostErase();// throws ModelException;

	public void onPreDeleteErase();// throws ModelException;
	public void onPostDeleteErase();// throws ModelException;

	public void onPrePersist();// throws ModelException;
	public void onPostPersist();// throws ModelException;

	public void onPreUndelete();// throws ModelException;
	public void onPostUndelete();// throws ModelException;



	
	
	



	public boolean checkLoaded(String property, boolean save);
	
	public boolean unloadProperty (String property);
	
	public boolean unloadProperty (String property, Object oldValue, Object newValue, boolean notify);
	
	public boolean unloadProperty (String property, boolean notify);
	
	public void unloadProperties ();
	
	public String doGetLoadedProperties ();

	public boolean checkRead(String property, boolean force);

	public void unreadProperty (String property);

	public void unreadProperties ();

	public String doGetReadProperties ();
	

	
	
	
	
	
	//EntityLog
	public boolean doCheckLogCreate ();
	
	public boolean doCheckLogRead ();
	
	public boolean doCheckLogUpdate ();
	
	public boolean doCheckLogUpdateChanges ();
	
	public boolean doCheckLogDelete ();
	
	public boolean doCheckLogUndelete ();
	
	public boolean doCheckLogErase ();
	
	public boolean doCheckLog ();

	public void saveLogException (Throwable e, String actionName);// throws ModelException;
	
	public void saveLogException (Throwable e, String actionName, Long time);// throws ModelException;
	
	public void saveActionLog (String actionName);// throws ModelException;

	public void saveActionLog (String actionName, String comment);// throws ModelException;

	public void saveActionLog (String actionName, Long time);// throws ModelException;

	public void saveActionLog (String actionName, String comment, Long time);// throws ModelException;

//	public void trySaveActionLog (String actionName);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, String comment);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, Long time);// throws ModelException;
//
//	public void trySaveActionLog (String actionName, String comment, Long time);// throws ModelException;

//	public void saveCreateLog ();// throws ModelException;

//	public void trySaveCreateLog ();// throws ModelException;

//	public void saveReadLog ();// throws ModelException;

//	public void trySaveReadLog ();// throws ModelException;

//	public void saveUpdateLog ();// throws ModelException;

//	public void trySaveUpdateLog ();// throws ModelException;

//	public void saveUpdateLogChanges (LogChanges changes);// throws ModelException;

//	public void trySaveUpdateLogChanges (LogChanges changes);// throws ModelException;

//	public void saveDeleteLog ();// throws ModelException;

//	public void trySaveDeleteLog ();// throws ModelException;

//	public void saveUndeleteLog ();// throws ModelException;
	
//	public void trySaveUndeleteLog ();// throws ModelException;
	
//	public void saveEraseLog ();// throws ModelException;

//	public void trySaveEraseLog ();// throws ModelException;

	public LogData getLogCreation ();// throws ModelException;

	public java.util.List getLogChanges ();// throws ModelException;

	public LogData getLogLastChange ();// throws ModelException;

	public LogData getLogPrevLastChange ();// throws ModelException;

	public LogData getLogLastAction (String actionName);// throws ModelException;

	public LogData getLogPrevLastAction (String actionName);// throws ModelException;

	public java.util.List getLogActions (String actionName);// throws ModelException;

	public java.util.List getLogDeletes ();// throws ModelException;

	public LogData getLogLastDelete ();// throws ModelException;

	public LogData getLogPrevLastDelete ();// throws ModelException;

	public java.util.List getLogReads ();// throws ModelException;

	public LogData getLogLastRead ();// throws ModelException;

	public LogData getLogPrevLastRead (Object data);// throws ModelException;

//	public java.lang.Number countLogBy (jejbutils.filter.Filter filter);// throws ModelException; 
//
//	public java.util.List listLogBy (jejbutils.filter.Filter filter);// throws ModelException;
//
//	public LogData findLogBy (jejbutils.filter.Filter filter);// throws ModelException;
	
	
	
	//AutoEntity
	public void refresh ();// throws ModelException;
	
	public void refresh (String properties);// throws ModelException;

//	public void refresh (boolean forceRefresh);// throws ModelException;
//	
//	public void refresh (String properties, boolean forceRefresh);// throws ModelException;

	
	
	public boolean hasChanges ();// throws ModelException;
	
	public boolean hasChanges (String properties);// throws ModelException;

//	public long doSaveProperties(String properties, boolean check, boolean create, boolean update, LogChanges changes);// throws ModelException;
	
//	public boolean isSameVersion ();// throws ModelException;


	
	public void createIfNotExists ();// throws ModelException;
	
	
	
	public void create ();// throws ModelException;
			
	public void create (boolean validate);// throws ModelException;
					
	public void create (boolean validate, boolean enableOnPre);// throws ModelException;

	public void create (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
						
					
									
	public Entity createCopy ();// throws ModelException;
	
	public Entity createCopy (String properties);// throws ModelException;

	public void copy (Entity source, String properties);// throws ModelException;
	
	public void copy (Entity source);// throws ModelException;

	
	
	
	public void erase ();// throws ModelException;

	public void erase (boolean validate);// throws ModelException;														
	
//	public void doEraseProperties ();// throws ModelException;

	

												
	public void delete ();// throws ModelException;

//	public void doDeleteProperties ();// throws ModelException;

//	public void saveDelete (boolean save);// throws ModelException;
																
//	public void deleteBind ();// throws ModelException;

	
	
	
	public void undelete();// throws ModelException;

	public void undelete(boolean validate);// throws ModelException;

	public void undelete(boolean validate, boolean enableOnPre);// throws ModelException;

	public void undelete(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
	
	
	
	public void cancelChanges ();

	public void cancelChanges (String properties);
	
	//Usa el log para volver al estado anterior
	public void restore ();// throws ModelException;

	//Usa el log para volver al estado del día indicado
	public void restore (Date date);// throws ModelException;

	public void restore(LogData log);// throws ModelException;
	
	public boolean hasId ();// throws ModelException;
	
	public boolean exists ();// throws ModelException;

//	public java.lang.Integer getDefaultPageSize ();// throws ModelException;

	public Entity reload ();// throws ModelException;

//	public boolean saveProperties (String properties, boolean validate, boolean create);// throws ModelException;



	public void save ();// throws ModelException;

	public void save (boolean validate);// throws ModelException;

	public void save (boolean validate, boolean enableOnPre);// throws ModelException;

	public void save (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void save (String properties);// throws ModelException;

	public void save (String properties, boolean validate);// throws ModelException;

	public void save (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void save (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
	
	

	
	public void persist ();// throws ModelException;

	public void persist (boolean validate);// throws ModelException;

	public void persist (boolean validate, boolean enableOnPre);// throws ModelException;

	public void persist (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void persist (String properties);// throws ModelException;

	public void persist (String properties, boolean validate);// throws ModelException;

	public void persist (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void persist (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
		
	
	
	
	
	public void update ();// throws ModelException;

	public void update (boolean validate);// throws ModelException;
	
	public void update (boolean validate, boolean enableOnPre);// throws ModelException;

	public void update (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void update (String properties);// throws ModelException;

	public void update (String properties, boolean validate);// throws ModelException;

	public void update (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void update (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	
	
//	public boolean doCheckPropertyPersist ();// throws ModelException;
//
//	public Boolean doGetPropertyPersist ();// throws ModelException;
//
//	public void doSetPropertyPersist (Boolean newValue);// throws ModelException;

	public void erase(boolean validate, boolean enableOnPre);// throws ModelException;

	public void erase(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void delete(boolean validate);// throws ModelException;

	public void delete(boolean validate, boolean enableOnPre);// throws ModelException;

	public void delete(boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	
	

	
	
	public void resync ();// throws ModelException;

	public void resync (boolean validate);// throws ModelException;

	public void resync (boolean validate, boolean enableOnPre);// throws ModelException;

	public void resync (boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;

	public void resync (String properties);// throws ModelException;

	public void resync (String properties, boolean validate);// throws ModelException;

	public void resync (String properties, boolean validate, boolean enableOnPre);// throws ModelException;

	public void resync (String properties, boolean validate, boolean enableOnPre, boolean enableOnPost);// throws ModelException;
		
	

	public Entity doClone ();

	public Entity doClone (String properties);
	
	public void copyTo (Entity target);

	public void copyTo (String properties, Entity target);

}

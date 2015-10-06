package org.effortless.zkstrap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.effortless.orm.IBean;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;

public class ObjectAccess extends Object {

	protected ObjectAccess () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}
	
	public static Object getProperty (Component cmp, String name) {
		return getProperty(cmp, name, true);
	}
	
	public static Object getProperty (Component cmp, String name, boolean binding) {
		Object result = null;
		Object bean = getBean(cmp);
		if (bean != null) {
			if ("#".equals(name)) {
				result = bean;
			}
			else {
				try {
					result = PropertyUtils.getProperty(bean, name);
				} catch (IllegalAccessException e) {
					throw new UiException(e);
				} catch (InvocationTargetException e) {
					throw new UiException(e);
				} catch (NoSuchMethodException e) {
					throw new UiException(e);
				}
				
				if (binding) {
					IBean ibean = null;
					try { ibean = (IBean)bean; } catch (ClassCastException e) {}
					Input input = null;
					try { input = (Input)cmp; } catch (ClassCastException e) {}
					final Input _input = input;
					if (ibean != null && _input != null) {
						ibean.addPropertyChangeListener(name, new PropertyChangeListener() {
		
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								Object newValue = evt.getNewValue();
								_input.reloadValue(newValue);
							}
							
						});
					}
				}
			}
		}
		return result;
	}
	
	public static void setProperty (Component cmp, String name, Object value) {
		Object bean = getBean(cmp);
		if (bean != null) {
			try {
				PropertyUtils.setProperty(bean, name, value);
			} catch (IllegalAccessException e) {
				throw new UiException(e);
			} catch (InvocationTargetException e) {
				throw new UiException(e);
			} catch (NoSuchMethodException e) {
				throw new UiException(e);
			}
		}
	}
	
	protected static final String _BEAN = "ObjectAccess.Bean";
//	protected static final String _CMP_CONTAINER = "ObjectAccess.CmpContainer";
	
	public static Object getBean (Component cmp) {
		Object result = null;
		if (cmp != null) {
			java.util.Map atts = cmp.getAttributes();
			boolean contains = atts.containsKey(_BEAN);
			if (contains) {
				result = atts.get(_BEAN);
			}
			else {
				Component parent = cmp.getParent();
				result = getBean(parent);
				if (result != null) {
					cmp.setAttribute(_BEAN, result);
				}
//				contains = atts.containsKey(_CMP_CONTAINER);
//				if (contains) {
//					Component container = (Component)atts.get(_CMP_CONTAINER);
//					result = getBean(container);
//				}
//				else {
//					
//				}
			}
		}
		return result;
	}

	public static Screen getFirstScreen (Component cmp) {
		Screen result = null;
		if (cmp != null) {
			Screen baseEditor = null; try { baseEditor = (Screen)cmp; } catch (ClassCastException e) {}
			if (baseEditor != null) {
				result = baseEditor;
			}
			else {
				Component parent = cmp.getParent();
				result = getFirstScreen(parent);
			}
		}
		return result;
	}

	public static Object getCmpBean (Component cmp) {
		Object result = null;
		if (cmp != null) {
			java.util.Map atts = cmp.getAttributes();
			boolean contains = atts.containsKey(_BEAN);
			Screen baseEditor = null; try { baseEditor = (Screen)cmp; } catch (ClassCastException e) {}
			if (contains && baseEditor != null) {
				result = cmp;
			}
			else {
				Component parent = cmp.getParent();
				result = getCmpBean(parent);
			}
		}
		return result;
	}

	public static void setBean (Component cmp, Object newValue) {
		if (cmp != null) {
			cmp.setAttribute(_BEAN, newValue);
		}
	}

	public static void runMethod(Component cmp, String method) {
		Object bean = getBean(cmp);
		runMethodDirectly(bean, method);
	}
	
	public static void runMethodCmpBean(Component cmp, String method) {
		Object cmpBean = getCmpBean(cmp);
		runMethodDirectly(cmpBean, method);
	}
	
	public static void runMethodDirectly(Object bean, String method) {
		method = (method != null ? method.trim() : "");
		if (bean != null && method.length() > 0) {
			try {
				MethodUtils.invokeExactMethod(bean, method, (Object[])null);
			} catch (NoSuchMethodException e) {
				throw new UiException(e);
			} catch (IllegalAccessException e) {
				throw new UiException(e);
			} catch (InvocationTargetException e) {
				throw new UiException(e);
			}
		}
	}

	public static void close(Component cmp) {
		if (cmp != null) {
			AdminApp app = getApp(cmp);
			app.close(cmp);
		}
	}

	public static AdminApp getApp(Component cmp) {
		AdminApp result = null;
		try { result = (AdminApp)cmp; } catch (ClassCastException e) {}
		result = (result != null ? result : (cmp != null ? getApp(cmp.getParent()) : null));
		return result;
	}

	
	
	public static void execEditorAction(Event evt) {
		Object ctrl = ObjectAccess.getCmpBean(evt.getTarget());
		java.util.Map data = (java.util.Map)evt.getData();
		String name = (String)data.get("name");
		String evtName = evt.getName();
		boolean checkSimple = "#".equals(name);
		String prefix = (checkSimple ? "" : name + "$");
		String method = prefix + evtName;
		try {
			MethodUtils.invokeExactMethod(ctrl, method, new Object[] {evt}, new Class[] {Event.class});
		} catch (NoSuchMethodException e) {
			String method2 = evtName;
			try {
				MethodUtils.invokeExactMethod(ctrl, method2, new Object[] {evt}, new Class[] {Event.class});
			}
			catch (NoSuchMethodException e0) {
				String newMethod = (checkSimple ? "processEvent" : name);
				try {
					MethodUtils.invokeExactMethod(ctrl, newMethod, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e1) {
					String defaultMethod = "processEvent";
					try {
						MethodUtils.invokeExactMethod(ctrl, defaultMethod, new Object[] {evt}, new Class[] {Event.class});
					} catch (NoSuchMethodException e2) {
						throw new UiException(e2);
					} catch (IllegalAccessException e2) {
						throw new UiException(e2);
					} catch (InvocationTargetException e2) {
						throw new UiException(e2);
					}
				} catch (IllegalAccessException e1) {
					throw new UiException(e1);
				} catch (InvocationTargetException e1) {
					throw new UiException(e1);
				}
			} catch (IllegalAccessException e0) {
				throw new UiException(e0);
			} catch (InvocationTargetException e0) {
				throw new UiException(e0);
			}
		} catch (IllegalAccessException e) {
			throw new UiException(e);
		} catch (InvocationTargetException e) {
			throw new UiException(e);
		}
	}

	
	
	public static void execAppAction(Event evt) {
		AdminApp app = getApp(evt.getTarget());
		Object ctrl = app.getAttribute("CTRL");
		java.util.Map data = (java.util.Map)evt.getData();
		String name = (String)data.get("name");
		String evtName = evt.getName();
		boolean checkSimple = "#".equals(name);
		String prefix = (checkSimple ? "" : name + "$");
		String method = prefix + evtName;
		try {
			MethodUtils.invokeExactMethod(ctrl, method, new Object[] {evt}, new Class[] {Event.class});
		} catch (NoSuchMethodException e) {
			String newMethod = (checkSimple ? "processEvent" : name);
			try {
				MethodUtils.invokeExactMethod(ctrl, newMethod, new Object[] {evt}, new Class[] {Event.class});
			} catch (NoSuchMethodException e1) {
				String defaultMethod = "processEvent";
				try {
					MethodUtils.invokeExactMethod(ctrl, defaultMethod, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e2) {
					throw new UiException(e2);
				} catch (IllegalAccessException e2) {
					throw new UiException(e2);
				} catch (InvocationTargetException e2) {
					throw new UiException(e2);
				}
			} catch (IllegalAccessException e1) {
				throw new UiException(e1);
			} catch (InvocationTargetException e1) {
				throw new UiException(e1);
			}
		} catch (IllegalAccessException e) {
			throw new UiException(e);
		} catch (InvocationTargetException e) {
			throw new UiException(e);
		}
	}

	public static Object readProperty(Object item, String property) {
		Object result = null;
		if (item != null && property != null) {
			try {
				result = PropertyUtils.getProperty(item, property);
			} catch (IllegalAccessException e) {
				throw new UiException(e);
			} catch (InvocationTargetException e) {
				throw new UiException(e);
			} catch (NoSuchMethodException e) {
				throw new UiException(e);
			}
		}
		return result;
	}

	public static void runMethodCmpBean(Component cmp, String method, String actionName) {
		Object cmpBean = getCmpBean(cmp);
		method = (method != null ? method.trim() : "");
		if (cmpBean != null && method.length() > 0) {
			try {
				MethodUtils.invokeExactMethod(cmpBean, method, new Object[] {actionName});
			} catch (NoSuchMethodException e) {
				throw new UiException(e);
			} catch (IllegalAccessException e) {
				throw new UiException(e);
			} catch (InvocationTargetException e) {
				throw new UiException(e);
			}
		}
	}
	
}

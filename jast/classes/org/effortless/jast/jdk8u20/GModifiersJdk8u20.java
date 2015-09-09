package org.effortless.jast.jdk8u20;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.TreeMaker;

import org.effortless.jast.GUnit;

public class GModifiersJdk8u20 extends GNodeJdk8u20 {

	protected JCModifiers setModifier (GUnit unit, JCModifiers oldValue, boolean enabled, long modifier) {
		JCModifiers result = null;
		TreeMaker tm = ((GUnitJdk8u20)unit).getTm();
		if (oldValue != null) {
			if (enabled) {
				result = tm.Modifiers(oldValue.flags | modifier);
			}
			else {
				result = tm.Modifiers(oldValue.flags & ~modifier);
			}
		}
		else if (enabled) {
			result = tm.Modifiers(modifier);
		}
		return result;
	}

	protected boolean isModifier (JCModifiers oldValue, int modifier) {
		boolean result = false;
		if (oldValue != null) {
			result = (oldValue.flags & modifier) != 0;
		}
		return result;
	}
	
	
	protected boolean isPublic (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.PUBLIC);
	}
	
	protected boolean isProtected (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.PROTECTED);
	}
	
	protected boolean isPrivate (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.PRIVATE);
	}

	protected boolean isStatic (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.STATIC);
	}
	
	protected boolean isFinal (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.FINAL);
	}
	
	protected boolean isAbstract (JCModifiers oldValue) {
		return isModifier(oldValue, Flags.ABSTRACT);
	}
	
	protected JCModifiers setPublic (GUnit unit, JCModifiers oldValue, boolean enabled) {
		if (enabled) {
			oldValue = setModifier(unit, oldValue, false, Flags.PROTECTED);
			oldValue = setModifier(unit, oldValue, false, Flags.PRIVATE);
		}
		return setModifier(unit, oldValue, enabled, Flags.PUBLIC);
	}
	
	protected JCModifiers setProtected (GUnit unit, JCModifiers oldValue, boolean enabled) {
		if (enabled) {
			oldValue = setModifier(unit, oldValue, false, Flags.PUBLIC);
			oldValue = setModifier(unit, oldValue, false, Flags.PRIVATE);
		}
		return setModifier(unit, oldValue, enabled, Flags.PROTECTED);
	}
	
	protected JCModifiers setPrivate (GUnit unit, JCModifiers oldValue, boolean enabled) {
		if (enabled) {
			oldValue = setModifier(unit, oldValue, false, Flags.PROTECTED);
			oldValue = setModifier(unit, oldValue, false, Flags.PUBLIC);
		}
		return setModifier(unit, oldValue, enabled, Flags.PRIVATE);
	}
	
	protected JCModifiers setFinal (GUnit unit, JCModifiers oldValue, boolean enabled) {
		return setModifier(unit, oldValue, enabled, Flags.FINAL);
	}
	
	protected JCModifiers setStatic (GUnit unit, JCModifiers oldValue, boolean enabled) {
		return setModifier(unit, oldValue, enabled, Flags.STATIC);
	}
	
	protected JCModifiers setAbstract (GUnit unit, JCModifiers oldValue, boolean enabled) {
		return setModifier(unit, oldValue, enabled, Flags.ABSTRACT);
	}
	
}

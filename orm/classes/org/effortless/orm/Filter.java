package org.effortless.orm;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.effortless.orm.Bindable;

public interface Filter extends List, Bindable {

	public Integer getPageIndex ();
	public Filter setPageIndex (Integer newValue);

	public Integer getPageSize ();
	public Filter setPageSize (Integer newValue);

	public Boolean getPaginated();
	public Filter setPaginated (Boolean newValue);

	public Filter previousPage ();
	public Filter nextPage ();

	public Filter offset (int index);
	public Filter limit (int index);
	
	public Integer getTotalPages ();
	public Filter setTotalPages (Integer newValue);
	
	public Integer getSize ();

	public Boolean getDeleted();
	public Filter setDeleted (Boolean newValue);


	public Filter like (String name, Object param);
	public Filter like (String name, Filter param);
	public Filter notLike (String name, Object param);
	public Filter notLike (String name, Filter param);
	public Filter ilike (String name, Object param);
	public Filter ilike (String name, Filter param);
	public Filter inotLike (String name, Object param);
	public Filter inotLike (String name, Filter param);
	
	
	public Filter lk (String name, Object param);
	public Filter lk (String name, Filter param);
	public Filter nlk (String name, Object param);
	public Filter nlk (String name, Filter param);
	public Filter ilk (String name, Object param);
	public Filter ilk (String name, Filter param);
	public Filter inlk (String name, Object param);
	public Filter inlk (String name, Filter param);

	
	public Filter eq (String name, Object param);
	public Filter eq (String name, Boolean param);
	public Filter eq (String name, Filter param);
	public Filter eq (String name, Boolean valueTrue, Boolean valueFalse);
	public Filter ieq (String name, Object param);
	public Filter ieq (String name, Filter param);


	public Filter ne (String name, Object param);
	public Filter ne (String name, Filter param);
	public Filter ine (String name, Object param);
	public Filter ine (String name, Filter param);

	public Filter gt (String name, Object param);
	public Filter gt (String name, Filter param);
	public Filter ge (String name, Object param);
	public Filter ge (String name, Filter param);
	public Filter lt (String name, Object param);
	public Filter lt (String name, Filter param);
	public Filter le (String name, Object param);
	public Filter le (String name, Filter param);
	public Filter bt (String name, Object lo, Object hi);
	public Filter bt (String name, Filter lo, Filter hi);
	public Filter nbt (String name, Object lo, Object hi);
	public Filter nbt (String name, Filter lo, Filter hi);

	public Filter between (String name, Object lo, Object hi);
	public Filter between (String name, Filter lo, Filter hi);
	public Filter notBetween (String name, Object lo, Object hi);
	public Filter notBetween (String name, Filter lo, Filter hi);
	
	
	public Filter isTrue (String name);
	public Filter isFalse (String name);
	public Filter isZero (String name);
	public Filter isNotZero (String name);
	public Filter isPositive (String name);
	public Filter isNegative (String name);
	
	public Filter in (String name, Object... param);
	public Filter in (String name, Collection param);
	public Filter in (String name, Filter param);
	public Filter nin (String name, Object... param);
	public Filter nin (String name, Collection param);
	public Filter nin (String name, Filter param);

	public Filter notIn (String name, Object... param);
	public Filter notIn (String name, Collection param);
	public Filter notIn (String name, Filter param);
	
	public Filter isEmpty (String name);
	public Filter isNotEmpty (String name);
	
	public Filter isNotNull (String name);
	public Filter isNull (String name);

	public Filter sizeEq (String name, int size);
	public Filter sizeEq (String name, Filter size);
	public Filter sizeGe(String name, int size);
	public Filter sizeGe(String name, Filter size);
	public Filter sizeGt(String name, int size);
	public Filter sizeGt(String name, Filter size);
	public Filter sizeLe(String name, int size);
	public Filter sizeLe(String name, Filter size);
	public Filter sizeLt(String name, int size);
	public Filter sizeLt(String name, Filter size);
	public Filter sizeNe(String name, int size);
	public Filter sizeNe(String name, Filter size);
	
	
	
	
	
	
	

	
	
	
	public Filter orderBy(String newValue);

	public Filter sortBy(String orderBy);
	

	public String getOrderBy();
	public Filter setOrderBy (String newValue);
	
	
	
	//left join PAIS_PROVINCIAS p ON p.item_id = o.id AND p.owner_id = ?
	public Filter on(Entity result, String propertyName);

	public Filter exclude(Entity o);


	public void reset();
	
	public Filter batch (EntityProcess process);

	public Filter persistAll (EntityProcess process);
	
	public Filter deleteAll (EntityProcess process);
	
	public Filter eraseAll (EntityProcess process);
	
//	public Map toMap ();
	
	public List listPage ();
	
	

	
	public boolean exists ();
	
	public Filter negative ();
	
//	public Filter<Type> add (Criterion criterion);
	public Filter and ();
	public Filter or ();
	public Filter nor ();
	public Filter nand ();
	public Filter not ();
	public Filter end ();
	
	public Filter link (Class clazz, String name);
	
	
	public Entity first ();
	public Entity second ();
	public Entity third ();
	public Entity antepenultimate ();
	public Entity penultimate ();
	public Entity last ();
	public Entity nth (int index);
	
	
	public Number min (String name);
	public Number max (String name);
	public Number sum (String name);
	public Number avg (String name);
	
	public Number count();
	
	public Filter reverse ();

	
	
	
	
	

	public void addPropertyChangeListener (String name, PropertyChangeListener listener);
	public void addPropertyChangeListener (PropertyChangeListener listener);
	public void propertyChange(PropertyChangeEvent event);
	
	
	
}

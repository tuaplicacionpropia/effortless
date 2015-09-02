package org.effortless.orm.impl;

//import java.util.AbstractSequentialList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractPropertyList<Type extends Object> extends Object implements List<Type> {//AbstractSequentialList {

	protected AbstractPropertyList () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	@Override
	public boolean add(Type e) {
		boolean result = false;
		int size = size();
		add(size, e);
		result = true;
		return result;
	}
	
	@Override
	public boolean addAll(Collection<? extends Type> c) {
		boolean result = false;
		if (c != null) {
			result = true;
			boolean flag = false;
			for (Type item : c) {
				flag = add(item);
				result = result && flag;
			}
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Type> c) {
		boolean result = false;
		if (c != null) {
			int idx = index;
			for (Type item : c) {
				add(idx++, item);
			}
			result = true;
		}
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean result = true;
		if (c != null) {
			for (Object item : c) {
				boolean flag = contains(item);
				if (!flag) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		result = size() <= 0;
		return result;
	}

	@Override
	public Iterator<Type> iterator() {
		Iterator<Type> result = null;
		final AbstractPropertyList<Type> fThis = this;
		result = new Iterator<Type>() {

			protected int _idx = -1;
			protected int _size = -1;
			
			@Override
			public boolean hasNext() {
				boolean result = false;
				this._size = (this._size >= 0 ? this._size : fThis.size());
				result = (this._size > 0 && (this._idx + 1) < this._size);
				return result;
			}

			@Override
			public Type next() {
				Type result = null;
				this._idx++;
				result = fThis.get(this._idx);
				return result;
			}

			@Override
			public void remove() {
				fThis.remove(this._idx);
			}
			
		};
		return result;
	}

	@Override
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public ListIterator<Type> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<Type> listIterator(int index) {
		ListIterator<Type> result = null;
		final AbstractPropertyList<Type> fThis = this;
		final int fIndex = index;
		result = new ListIterator<Type>() {

			protected int _idx = fIndex - 1;
			protected int _size = -1;
			
			@Override
			public void add(Type element) {
				fThis.add(element);
			}

			@Override
			public boolean hasNext() {
				boolean result = false;
				this._size = (this._size >= 0 ? this._size : fThis.size());
				result = (this._size > 0 && (this._idx + 1) < this._size);
				this._idx += (result ? 1 : 0);
				return result;
			}

			@Override
			public boolean hasPrevious() {
				boolean result = false;
				this._size = (this._size >= 0 ? this._size : fThis.size());
				result = (this._size > 0 && (this._idx - 1) > 0);
				return result;
			}

			@Override
			public Type next() {
				Type result = null;
				this._idx++;
				result = fThis.get(this._idx);
				return result;
			}

			@Override
			public int nextIndex() {
				return this._idx + 1;
			}

			@Override
			public Type previous() {
				Type result = null;
				this._idx--;
				result = fThis.get(this._idx);
				return result;
			}

			@Override
			public int previousIndex() {
				return (this._idx > fIndex ? this._idx - 1 : fIndex);
			}

			@Override
			public void remove() {
				fThis.remove(this._idx);
			}

			@Override
			public void set(Type element) {
				fThis.set(this._idx, element);
			}
			
		};
		return result;
	}

	@Override
	public Type remove(int index) {
		Type result = null;
		result = get(index);
		remove(result);
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = true;
		if (c != null) {
			for (Object item : c) {
				boolean flag = remove(item);
				if (!flag) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Type set(int index, Type element) {
		Type result = null;
		result = get(index);
		remove(result);
		add(index, element);
		return result;
	}

	@Override
	public List<Type> subList(int fromIndex, int toIndex) {
		List<Type> result = null;
		result = new java.util.ArrayList<Type>();
		for (int i = fromIndex; i < toIndex; i++) {
			result.add(get(i));
		}
		return result;
	}

	@Override
	public Object[] toArray() {
		Object[] result = null;
		int size = size();
		result = new Object[size];
		for (int i = 0; i < size; i++) {
			result[i] = get(i);
		}
		return result;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		T[] result = null;
		int size = size();
		if (a != null && a.length >= size) {
			result = a;
		}
		else {
			result = (T[])new Object[size];
		}
		for (int i = 0; i < size; i++) {
			result[i] = (T)get(i);
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		clear();
		result = addAll((Collection<Type>)c);
		return result;
	}

	
	public abstract void clear();

	public abstract boolean contains(Object o);

	public abstract boolean remove(Object o);

	public abstract void add(int index, Type element);

	public abstract int indexOf(Object o);
	
	public abstract Type get(int index);

	public abstract int size();
	
}

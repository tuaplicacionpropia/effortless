package org.effortless.jast.jdk8u20.util;

import java.util.List;
import java.util.ListIterator;

public class Utils {

	public static <T> com.sun.tools.javac.util.List<T> toJavacList(List<T> list) {
		com.sun.tools.javac.util.List<T> out = com.sun.tools.javac.util.List.nil();
		ListIterator<T> li = list.listIterator(list.size());
		while (li.hasPrevious()) out = out.prepend(li.previous());
		return out;
	}
	
}

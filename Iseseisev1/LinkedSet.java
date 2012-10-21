package ise1;

import java.util.*;

public class LinkedSet {

	private HashSet<Object> innerHashSet;
	private ArrayList<Object> aList;
	
	public LinkedSet() {
		this.innerHashSet = new HashSet<Object>();
		this.aList = new ArrayList<Object>();
	}
	
	public void add(Object o) {
		if(this.innerHashSet.add(o)) {
			aList.add(this.size()-1, o);
		}
	}
	
	public void remove(Object o) {
		this.innerHashSet.remove(o);
	}
	
	public boolean contains(Object o) {
		return this.innerHashSet.contains(o);
	}
	
	public boolean containsAll(List<Object> list) {
		return this.innerHashSet.containsAll(list);
	}
	
	public boolean removeAll(List<Object> list) {
		return this.innerHashSet.removeAll(list);
	}
	
	public boolean retainAll(List<Object> list) {
		return this.innerHashSet.retainAll(list);
	}
	
	public int size() {
		return this.innerHashSet.size();
	}
	
	public List<Object> asList() {
		return this.aList;
	}
}
	

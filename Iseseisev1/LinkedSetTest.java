package test;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;
import ise1.LinkedSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LinkedSetTest {

	@Test
	public void getSize_newInstance_returnZero() throws Exception{
		LinkedSet ls = new LinkedSet();
		assertThat(ls.size(), is(0));
	}

	@Test
	public void getSize_addObject_returnsOne() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		assertThat(ls.size(), is(1));
	}
	
	@Test
	public void getSize_addSameObjects_returnsOne() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add(1);
		assertThat(ls.size(), is(1));
	}
	
	@Test
	public void getSize_addDifferentObjects_returnsThree() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(false);
		assertThat(ls.size(), is(3));
	}
	
	@Test
	public void getSize_removeElement_returnsZero() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.remove(1);
		assertThat(ls.size(), is(0));
	}
	
	@Test
	public void getSize_removeElements_returnsOne() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(true);
		ls.remove(1);
		ls.remove(false);
		ls.remove("");
		assertThat(ls.size(), is(1));
	}
	
	@Test
	public void contains_containsElements_returnsTrue() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(false);
		assertThat(ls.contains(""), is(true));
	}
	
	@Test
	public void contains_containsElements_returnsFalse() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(false);
		assertThat(ls.contains("15"), is(false));
	}
	
	@Test
	public void containsAll_containsAllElements_returnsTrue() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("");
		list.add(false);
		
		assertTrue(ls.containsAll(list));
	}
	
	@Test
	public void containsAll_containsAllElements_returnsFalse() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("12");
		
		assertFalse(ls.containsAll(list));
	}
	
	@Test
	public void removeAll_checkSize_returnsTwo() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(10);
		ls.add(1);
		ls.add("12");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("12");
		
		ls.removeAll(list);
		
		assertThat(ls.size(), is(2));
	}
	
	@Test
	public void removeAll_removesAllElements_returnsTrue() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(10);
		ls.add(1);
		ls.add("12");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("12");
		
		assertTrue(ls.removeAll(list));
	}
	
	@Test
	public void removeAll_removesSomeElements_returnsFalse() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(10);
		ls.add(1);
		ls.add("12");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("121");
		
		assertFalse(ls.removeAll(list));
	}
	
	@Test
	public void retainAll_removesAllNotInList() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(10);
		ls.add(1);
		ls.add("12");
		ls.add(false);
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("121");
		
		ls.retainAll(list);
		
		assertThat(ls.size(), is(0));
	}
	
	@Test
	public void retainAll_removesAllNotInList_returnsOne() throws Exception {
		LinkedSet ls = new LinkedSet();
		ls.add(10);
		ls.add(1);
		ls.add("12");
		ls.add("121");
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("121");
		
		ls.retainAll(list);
		
		assertThat(ls.size(), is(1));
	}
	
	@Test
	public void asList_checkOrder_returnsTrue() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(true);
		
		LinkedList<Object> testList = new LinkedList<Object>();
		testList.add(1);
		testList.add("");
		testList.add(true);
		
		assertEquals(ls.asList(), testList);
	}
	
	@Test
	public void asList_checksSize_returnsSize() throws Exception{
		LinkedSet ls = new LinkedSet();
		ls.add(1);
		ls.add("");
		ls.add(true);
		
		assertThat(ls.asList().size(), is(3));
	}
}

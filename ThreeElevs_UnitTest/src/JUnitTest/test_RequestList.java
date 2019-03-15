package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Element;
import Elev.Request;
import Elev.RequestList;

public class test_RequestList {

	@Test
	public void testAdd_Request() {
		RequestList requestList = new RequestList();
		
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		requestList.add_Request(request);
	}

	@Test
	public void testGet_Request() {
		RequestList requestList = new RequestList();
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		requestList.add_Request(request);
		Request new_request = requestList.get_Request(0);
	}

	@Test
	public void testRemove_Request() {
		RequestList requestList = new RequestList();
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		requestList.add_Request(request);
		requestList.remove_Request(0);
	}

	@Test
	public void testIs_Empty() {
		RequestList requestList = new RequestList();
		System.out.println("List is empty " + requestList.is_Empty());
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		requestList.add_Request(request);
		System.out.println("List is empty " + requestList.is_Empty());
	}

	@Test
	public void testGet_Number() {
		RequestList requestList = new RequestList();
		System.out.println("List length is " + requestList.get_Number());
	}
	
	@Test
	public void testrepOK(){
		RequestList requestList = new RequestList();
		assertTrue(requestList.repOK());
		System.out.println("repOK is OK");
	}

}

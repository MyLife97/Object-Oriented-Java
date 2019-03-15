package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Element;
import Elev.Floor;
import Elev.Request;

public class test_Request {

	@Test
	public void testRequest() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
	}

	@Test
	public void testSee_symbol() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		System.out.println(request.see_symbol());
	}

	@Test
	public void testSee_destination() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		System.out.println(request.see_destination());
	}

	@Test
	public void testSee_dirction() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		System.out.println(request.see_direction());
	}

	@Test
	public void testSee_time() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Request request = new Request(sym, des, dir, tim);
		System.out.println(request.see_time());
	}

}

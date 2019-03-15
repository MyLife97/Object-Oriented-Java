package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Element;
import Elev.Elevator;
import Elev.Request;
import Elev.Scheduler;
import junit.framework.Assert;

public class test_Elevator {

	@Test
	public void testMake_Request() {
		Elevator elevator = new Elevator();
		Request maked_request;
		maked_request = elevator.make_Request(0, 1, 1, 0);
		
	}

	@Test
	public void testMove_to() {
		Elevator elevator = new Elevator();
		int des = 5;
		elevator.move_to(des);
		assertEquals(elevator.get_floor(), des);
		System.out.print("testMove_to OK\n");
	}

	@Test
	public void testSet_time() {
		Elevator elevator = new Elevator();
		double tim = 10.0;
		elevator.set_time(tim);
		Assert.assertEquals(elevator.get_time(), tim);
		System.out.print("testSet_time OK\n");
	}

	@Test
	public void testSet_dirction() {
		Elevator elevator = new Elevator();
		int dir = Element.Elevator_up;
		elevator.set_dirction(dir);
		assertEquals(elevator.get_direction(), dir);
		System.out.print("test OK\n");
	}



	@Test
	public void testPrint_direction() {
		Elevator elevator = new Elevator();
		int dir = Element.Elevator_up;
		elevator.set_dirction(dir);
		System.out.printf("test OK %s\n", elevator.print_direction());
		dir = Element.Elevator_still;
		elevator.set_dirction(dir);
		System.out.printf("test OK %s\n", elevator.print_direction());
		dir = Element.Elevator_down;
		elevator.set_dirction(dir);
		System.out.printf("test OK %s\n", elevator.print_direction());
	}

	@Test
	public void testToString() {
		Elevator elevator = new Elevator();
		System.out.println(elevator.toString());
	}
	
	@Test
	public void testrepOK(){
		Elevator elevator = new Elevator();
		assertTrue(elevator.repOK());
		System.out.println("repOK is OK");
	}

}

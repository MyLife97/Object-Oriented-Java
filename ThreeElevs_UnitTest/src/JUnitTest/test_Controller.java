package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Controller;
import Elev.Scheduler;

public class test_Controller {

	@Test
	public void testController(){
		Controller controller = new Controller();
	}
	
	@Test
	public void testrepOK(){
		Controller controller = new Controller();
		assertTrue(controller.repOK());
		System.out.println("repOK is OK");
	}
}

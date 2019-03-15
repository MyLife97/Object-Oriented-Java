package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Element;
import Elev.Floor;

public class test_Floor {

	@Test
	public void testMake_Request() {
		int sym = Element.Floor;
		int des = 5;
		int dir = Element.Floor_down;
		long tim = 10;
		Floor floor = new Floor();
		floor.make_Request(sym, des, dir, tim);
	}

}

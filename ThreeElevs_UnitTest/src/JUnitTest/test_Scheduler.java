package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Elev.Elevator;
import Elev.Request;
import Elev.RequestList;
import Elev.Scheduler;
import junit.framework.Assert;
import Elev.Element;
public class test_Scheduler {

	@Test
	public void testScheduler() {
		Scheduler scheduler = new Scheduler();
	}

	@Test
	public void testNext_step() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		//****
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,0));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,0));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,3));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,3));
		
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,4));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,4));
		
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,6));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,6));
		
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,9));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,9));
		
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,13));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,13));
		
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,13));//Pick Duplicated

		
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,13));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,13));
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,14));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,14));
		
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,16));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,16));
		
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,19));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,19));
		
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,20));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,20));
		
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,25));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,25));
		
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,26));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,26));
		
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,26));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,26));
		
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,28));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,28));
		
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,29));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_down,30));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,32));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,32));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,36));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,40));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,41));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,41));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,41));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,43));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,43));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,45));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,46));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,47));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,47));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,49));
		requestList.add_Request(new Request(Element.Ele,10,Element.Ele,50));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,52));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,53));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,55));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_up,64));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,64));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,67));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,69));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,73));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,74));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,74));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,74));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,76));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,80));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,84));
		requestList.add_Request(new Request(Element.Ele,10,Element.Ele,84));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_down,84));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,88));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,90));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,94));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,96));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,97));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,99));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,103));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,106));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,106));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,108));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,108));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,110));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,114));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,115));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,116));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,118));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,119));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,122));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,127));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,128));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,130));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,134));
		
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,1000));
		
		
		
		
		while(requestList.is_Empty() == false){
			scheduler.next_step(requestList, elevator);
		}
		
		scheduler = new Scheduler();
		requestList = new RequestList();
		elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,0));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,3));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,5));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,5));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,8));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,9));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,11));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,11));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,11));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,13));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_down,13));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,16));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,18));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,21));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,23));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,24));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,25));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,25));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,26));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,28));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,28));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,30));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,31));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,32));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,35));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,36));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,39));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,41));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,43));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_down,44));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,46));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,48));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,49));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,50));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,50));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,52));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,53));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,54));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_down,56));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,58));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_up,58));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,59));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,59));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_down,59));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,62));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,63));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,63));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,63));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,64));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,66));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,66));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,66));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,66));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,66));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,68));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,68));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,68));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,69));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,70));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,71));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,72));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,75));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,77));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,79));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,81));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,82));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,84));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,85));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,86));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,88));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,89));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,91));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,93));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,96));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,96));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,96));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,97));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,97));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,99));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,100));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,100));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,101));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,103));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,103));
		requestList.add_Request(new Request(Element.Ele,10,Element.Ele,103));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,103));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,104));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,106));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,106));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,107));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_down,108));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,108));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,108));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,111));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,114));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,116));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,119));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,119));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,119));

		while(requestList.is_Empty() == false){
			scheduler.next_step(requestList, elevator);
		}
		
		scheduler = new Scheduler();
		requestList = new RequestList();
		elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,0));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,0));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,0));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,3));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,4));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,5));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,7));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,9));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,10));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_down,11));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,11));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,13));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,15));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,17));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,17));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,17));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,19));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,22));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_down,22));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,22));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,25));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_down,25));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_up,27));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_up,30));
		requestList.add_Request(new Request(Element.Floor,4,Element.Floor_up,30));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,31));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,33));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,33));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_up,33));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,36));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,38));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,39));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,41));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_down,42));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,45));
		requestList.add_Request(new Request(Element.Ele,1,Element.Ele,48));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,50));
		requestList.add_Request(new Request(Element.Floor,9,Element.Floor_up,50));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,50));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,53));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,54));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,54));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,55));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,55));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,56));
		requestList.add_Request(new Request(Element.Ele,7,Element.Ele,56));
		requestList.add_Request(new Request(Element.Floor,2,Element.Floor_up,56));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_up,58));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,60));
		requestList.add_Request(new Request(Element.Floor,3,Element.Floor_down,61));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,63));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_down,66));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,67));
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,68));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,70));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,70));
		requestList.add_Request(new Request(Element.Floor,8,Element.Floor_up,70));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,72));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,73));
		requestList.add_Request(new Request(Element.Ele,3,Element.Ele,76));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_up,78));
		requestList.add_Request(new Request(Element.Floor,6,Element.Floor_down,80));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,83));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_down,83));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,84));
		requestList.add_Request(new Request(Element.Floor,1,Element.Floor_down,87));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_up,88));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,89));
		requestList.add_Request(new Request(Element.Floor,7,Element.Floor_down,90));
		requestList.add_Request(new Request(Element.Floor,10,Element.Floor_up,91));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_up,94));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,94));
		requestList.add_Request(new Request(Element.Ele,10,Element.Ele,95));
		requestList.add_Request(new Request(Element.Ele,4,Element.Ele,97));
		requestList.add_Request(new Request(Element.Ele,8,Element.Ele,98));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,99));
		requestList.add_Request(new Request(Element.Ele,10,Element.Ele,99));

		while(requestList.is_Empty() == false){
			scheduler.next_step(requestList, elevator);
		}
		
		scheduler = new Scheduler();
		requestList = new RequestList();
		elevator = new Elevator();
		
		elevator.set_dirction(Element.Elevator_still);
		elevator.move_to(5);
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Floor,5,Element.Floor_down,0));
		while(requestList.is_Empty() == false){
			scheduler.next_step(requestList, elevator);
		}
		//****
		scheduler = new Scheduler();
		requestList = new RequestList();
		elevator = new Elevator();
		
		scheduler.ele[5] = 10;
		scheduler.main_request = 2;
		scheduler.picking_request = 0;
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,6,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,9,Element.Ele,0));
		
		scheduler.change_elevator_dirction(requestList, elevator, scheduler.main_request);
		while(requestList.is_Empty() == false){
			scheduler.next_step(requestList, elevator);
		}
		
		
		
	}

	@Test
	public void testSearch_picking_one() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,0));
		
		scheduler.main_request = scheduler.search_main_one(requestList, elevator);
		scheduler.change_elevator_dirction(requestList, elevator, scheduler.main_request);
		
		int mark;
		mark = scheduler.search_picking_one(requestList, elevator);
		System.out.println("Picking one is " + mark);
	}

	@Test
	public void testSearch_main_one() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,0));
		
		int mark;
		mark = scheduler.search_main_one(requestList, elevator);
		System.out.println("Main one is " + mark);

	}

	@Test
	public void testCheck_duplicate() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		
		scheduler.ele[5] = 10;

		boolean mark;
		mark = scheduler.check_duplicate(requestList, 0);
		
		Assert.assertEquals(mark, true);
		System.out.println("Check Duplicate OK");
	}

	@Test
	public void testExcecute() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,0));
		
		scheduler.main_request = scheduler.search_main_one(requestList, elevator);
		scheduler.change_elevator_dirction(requestList, elevator, scheduler.main_request);
		
		scheduler.excecute(requestList, elevator, 0);
		
		Assert.assertEquals(elevator.get_floor(), 5);
		System.out.println("Excecute OK");
	}

	@Test
	public void testChange_elevator_dirction() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,0));
		
		scheduler.main_request = scheduler.search_main_one(requestList, elevator);
		scheduler.change_elevator_dirction(requestList, elevator, scheduler.main_request);
		
		Assert.assertEquals(elevator.get_direction(), Element.Elevator_up);
		System.out.println("Change Direction OK");
	}

	@Test
	public void testIgnore() {
		Scheduler scheduler = new Scheduler();
		RequestList requestList = new RequestList();
		Elevator elevator = new Elevator();
		
		requestList.add_Request(new Request(Element.Ele,5,Element.Ele,0));
		requestList.add_Request(new Request(Element.Ele,2,Element.Ele,0));
		
		scheduler.ignore(requestList, 0);
		
		Assert.assertEquals(requestList.get_Number(), 1);
		System.out.println("Ignore OK");
	}

	@Test
	public void testController() {
		Scheduler scheduler = new Scheduler();
	}
	
	@Test
	public void testrepOK(){
		Scheduler scheduler = new Scheduler();
		assertTrue(scheduler.repOK());
		System.out.println("repOK is OK");
	}

}

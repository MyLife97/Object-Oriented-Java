package Elev;

import java.awt.List;

/**
 * @OVERVIEW:
 * 这个类是调度器类，继承前一次作业调度器类，存储有主请求，当前请求和各按钮亮灯情况等信息。
 * 可以根据请求队列RequestList内的请求和Elevator的状态，自动找到下一个主请求、可捎带请求，
 * 并因此控制电梯的运动以正确完成各个请求。
 */
public class Scheduler extends Controller{

	public int main_request = -1;
	public int picking_request = -1;
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : None;
		*/
	public Scheduler(){
		super();
	}	
	/**
	* 注释.......
		* @REQUIRES: list != null && elevator != null;
		* @MODIFIES : this.main_request, this.picking_request, this.list, this.elevator;
		* @EFFECTS :
		* (main_request == -1) ==> this.main_request == search_main_one(list, elevator);
		* (main_request == -1 && check_duplicate(list, main_request)) ==> (ignore(list , main_request) == true && main_request == -1);
		* (main_request == -1 && !check_duplicate(list, main_request)) ==> change_elevator_dirction(list, elevator, main_request) == true;
		* (main_request != -1) ==> this.picking_request = search_picking_one(list, elevator);
		* (main_request != picking_request && picking_request != -1 && check_duplicate(list, picking_request)) ==> ignore(list, picking_request) == true;
		* (main_request != picking_request && picking_request != -1 && !check_duplicate(list, picking_request)) ==> excecute(list, elevator, picking_request) == true;
		* !(main_request != picking_request && picking_request != -1) ==> excecute(list, elevator, main_request) == true;
		*/
	public void next_step(RequestList list , Elevator elevator){
		
		if(main_request == -1){
			main_request = search_main_one(list, elevator);
			if(check_duplicate(list, main_request) == true){
				ignore(list , main_request);
				main_request = -1;
			}
			else{
				if(elevator.get_time() < list.get_Request(main_request).see_time()){//ִ�еĿ죬�������
					elevator.set_time((double)list.get_Request(main_request).see_time());
				}
				change_elevator_dirction(list, elevator, main_request);
			}
		}
		else{
			picking_request = search_picking_one(list, elevator);
			
			if(main_request != picking_request && picking_request != -1){
				if(check_duplicate(list, picking_request) == true){
					System.out.println("Pick Duplicated");
					//*** Delete
					ignore(list, picking_request);
					if(main_request > picking_request)
						main_request--;
					//*** Delete End
				}
				else{
					excecute(list, elevator, picking_request);
					//*** Delete
					ignore(list, picking_request);
					if(main_request > picking_request)
						main_request--;
					//*** Delete End
				}
			}
			else{
				excecute(list, elevator, main_request);
				//*** Delete
				ignore(list, main_request);
				main_request = -1;
				//*** Delete End
			}
		}
		
	}
	/**
	* 注释.......
		* @REQUIRES: list != null && elevator != null;
		* @MODIFIES : None;
		* @EFFECTS :
		* (\exist int i; 0 <= i < list.get_Number();elevator.get_direction() == Element.ELEVATOR_UP && elevator.get_floor() < list.get_Request(i).see_destination() && list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination() && ((double)(list.get_Request(i).see_destination() - elevator.get_floor()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()) ==> \result == i;
		* (\exist int i; 0 <= i < list.get_Number();elevator.get_direction() == Element.ELEVATOR_DOWN &&  elevator.get_floor() > list.get_Request(i).see_destination() && list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination() && ((double)(elevator.get_floor() - list.get_Request(i).see_destination()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()) ==> \result == i;
		* (\exist int i; 0 <= i < list.get_Number();elevator.get_direction() == Element.ELEVATOR_UP &&  list.get_Request(i).see_direction() == Element.FLOOR_UP && elevator.get_floor() < list.get_Request(i).see_destination() && list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination() && list.get_Request(main_request).see_destination() >= list.get_Request(i).see_destination() && ((double)(list.get_Request(i).see_destination() - elevator.get_floor()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()) ==> \result = i;
		* (\exist int i; 0 <= i < list.get_Number();elevator.get_direction() == Element.ELEVATOR_DOWN && list.get_Request(i).see_direction() == Element.FLOOR_DOWN && elevator.get_floor() > list.get_Request(i).see_destination() && list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination() && list.get_Request(main_request).see_destination() <= list.get_Request(i).see_destination() && ((double)(elevator.get_floor() - list.get_Request(i).see_destination()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()) ==> \result = i;
		* (else == true) ==> \result = -1;
		*/
	public int search_picking_one(RequestList list , Elevator elevator){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.ELE){
				if(elevator.get_direction() == Element.ELEVATOR_UP &&
					elevator.get_floor() < list.get_Request(i).see_destination() &&
					list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination() &&

					((double)(list.get_Request(i).see_destination() - elevator.get_floor()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() > list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}

				else if(elevator.get_direction() == Element.ELEVATOR_DOWN && 
						elevator.get_floor() > list.get_Request(i).see_destination() &&
						list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination() &&
						((double)(elevator.get_floor() - list.get_Request(i).see_destination()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() < list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}
			}
			else{
				if( elevator.get_direction() == Element.ELEVATOR_UP && 
					list.get_Request(i).see_direction() == Element.FLOOR_UP &&
					elevator.get_floor() < list.get_Request(i).see_destination() &&
					list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination() &&
					list.get_Request(main_request).see_destination() >= list.get_Request(i).see_destination() &&
					((double)(list.get_Request(i).see_destination() - elevator.get_floor()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() > list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}

				else if(elevator.get_direction() == Element.ELEVATOR_DOWN &&
						list.get_Request(i).see_direction() == Element.FLOOR_DOWN &&
						elevator.get_floor() > list.get_Request(i).see_destination() &&
						list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination() &&
						list.get_Request(main_request).see_destination() <= list.get_Request(i).see_destination() &&
						((double)(elevator.get_floor() - list.get_Request(i).see_destination()) / 2 + elevator.get_time()) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() < list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}
			}
		}
		return mark;
	}
	/**
	* 注释.......
		* @REQUIRES: list != null && elevator != null;
		* @MODIFIES : None;
		* @EFFECTS :
		* (\exist int i; 0 <= i < list.get_Number();list.get_Request(i).see_symbol() == Element.ELE && elevator.get_direction() == Element.ELEVATOR_UP && elevator.get_floor() < list.get_Request(i).see_destination() && (double)(elevator.get_time() - Element.DOOR_OPENING) > list.get_Request(i).see_time()) ==> mark == i;
		* (\exist int i; 0 <= i < list.get_Number();list.get_Request(i).see_symbol() == Element.ELE && elevator.get_direction() == Element.ELEVATOR_DOWN && elevator.get_floor() > list.get_Request(i).see_destination() && (double)(elevator.get_time() - Element.DOOR_OPENING) > list.get_Request(i).see_time()) ==> mark == i;
		* (mark == -1) ==> \result == 0;
		* mark != -1 ==> \result == mark;
		*/
	public int search_main_one(RequestList list, Elevator elevator){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.ELE){

				if(elevator.get_direction() == Element.ELEVATOR_UP && //��������
					elevator.get_floor() < list.get_Request(i).see_destination() && //¥������

					(double)(elevator.get_time() - Element.DOOR_OPENING) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
				}

				else if(elevator.get_direction() == Element.ELEVATOR_DOWN
						&& elevator.get_floor() > list.get_Request(i).see_destination() &&
						(double)(elevator.get_time() - Element.DOOR_OPENING) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
				}
			}
		}
		if(mark == -1)
			return 0;
		else
			return mark;
	}
	
	/**
	* 注释.......
		* @REQUIRES: list != null && 0 <= now_request < list.length;
		* @MODIFIES : None;
		* @EFFECTS :
		* (list.get_Request(now_request).see_symbol() == Element.ELE && ele[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()) ==> \result == true; 
		* (list.get_Request(now_request).see_symbol() == Element.FLOOR) && (floor_up[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()) && (floor_up[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()) ==> \result == true;
		* (list.get_Request(now_request).see_symbol() == Element.FLOOR) && !(list.get_Request(now_request).see_direction() == Element.FLOOR_UP) && (floor_down[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time())) ==> \result == true;
		* (Else == true) ==> \result == false; 
		*/
	public boolean check_duplicate(RequestList list , int now_request){
		if(list.get_Request(now_request).see_symbol() == Element.ELE){
			if(ele[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()){
				System.out.printf("#SAME[ER,%d,%d]\n" , list.get_Request(now_request).see_destination() , list.get_Request(now_request).see_time());
				return true;
			}
		}
		else if(list.get_Request(now_request).see_symbol() == Element.FLOOR){
			if(list.get_Request(now_request).see_direction() == Element.FLOOR_UP){
				if(floor_up[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()){
					System.out.printf("#SAME[FR,%d,UP,%d]\n" , list.get_Request(now_request).see_destination() , list.get_Request(now_request).see_time());
					return true;
				}
			}
			else{
				if(floor_down[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()){
					System.out.printf("#SAME[FR,%d,DOWN,%d]\n" , list.get_Request(now_request).see_destination() , list.get_Request(now_request).see_time());
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	* 注释.......
		* @REQUIRES: list != null && elevator != null && 0 <= next_request < list.length;
		* @MODIFIES : this.list, this.elevator, this.ele, this.floor_up, this.floor_down;
		* @EFFECTS :
		* (elevator.get_direction() != Element.ELEVATOR_STILL && elevator.get_floor() < list.get_Request(next_request).see_destination()) && 
		*		elevator.set_time(elevator.get_time() + (double)(list.get_Request(next_request).see_destination() - elevator.get_floor())/2) && 
		*		elevator.move_to(list.get_Request(next_request).see_destination()) ==>  elevator.move_to(list.get_Request(next_request).see_destination())== true && elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
		*(elevator.get_direction() == Element.ELEVATOR_STILL && elevator.get_floor() < list.get_Request(next_request).see_destination()) && 
		*		elevator.set_time(elevator.get_time() + (double)(list.get_Request(next_request).see_destination() - elevator.get_floor())/2) && 
		*		elevator.move_to(list.get_Request(next_request).see_destination()) ==>  elevator.move_to(list.get_Request(next_request).see_destination())== true && elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
		*/
	public void excecute(RequestList list , Elevator elevator, int next_request){
		int i;
		int last_floor;
		if(elevator.get_direction() != Element.ELEVATOR_STILL){
			last_floor = elevator.get_floor();
			if(elevator.get_floor() < list.get_Request(next_request).see_destination()){//������
				elevator.set_time(elevator.get_time() + (double)(list.get_Request(next_request).see_destination() - elevator.get_floor())/2);
				elevator.move_to(list.get_Request(next_request).see_destination());
				if(list.get_Request(next_request).see_symbol() == Element.ELE){
					ele[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				else if(list.get_Request(next_request).see_symbol() == Element.FLOOR && list.get_Request(next_request).see_direction() == Element.FLOOR_UP){
					floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				else{
					floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
			}
			else{
				elevator.set_time(elevator.get_time() + (double)(elevator.get_floor() - list.get_Request(next_request).see_destination())/2);
				elevator.move_to(list.get_Request(next_request).see_destination());
				if(list.get_Request(next_request).see_symbol() == Element.ELE){
					ele[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				else if(list.get_Request(next_request).see_symbol() == Element.FLOOR && list.get_Request(next_request).see_direction() == Element.FLOOR_UP){
					floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				else{
					floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.DOOR_OPENING;
				}
				elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
			}


			for(i = 0; i < list.get_Number();i++){
				if(i == next_request){
					elevator.set_time(elevator.get_time() - Element.DOOR_OPENING);
					if(last_floor < list.get_Request(next_request).see_destination()){
						if(list.get_Request(next_request).see_symbol() == Element.ELE){
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
						else if(list.get_Request(next_request).see_symbol() == Element.FLOOR && list.get_Request(next_request).see_direction() == Element.FLOOR_UP){
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
						else{
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
					}
					else{//������
						if(list.get_Request(next_request).see_symbol() == Element.ELE){
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
						else if(list.get_Request(next_request).see_symbol() == Element.FLOOR && list.get_Request(next_request).see_direction() == Element.FLOOR_UP){
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
						else{
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
					}
					elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
				}
				
				else if(list.get_Request(i).see_symbol() == Element.ELE &&
					list.get_Request(i).see_destination() == elevator.get_floor() &&
					list.get_Request(i).see_time() < elevator.get_time() - Element.DOOR_OPENING &&
					i != next_request){
					
					
					if(check_duplicate(list, i) == true){
						//*** Delete
						ignore(list, i);
						i--;
						//*** Delete End
					}
					else{
						if(elevator.get_direction() == Element.ELEVATOR_UP){
							elevator.set_time(elevator.get_time() - Element.DOOR_OPENING);
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
							ele[list.get_Request(i).see_destination()] = elevator.get_time();
						}
						else if(elevator.get_direction() == Element.ELEVATOR_DOWN){
							elevator.set_time(elevator.get_time() - Element.DOOR_OPENING);
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
							ele[list.get_Request(i).see_destination()] = elevator.get_time();
						}
					
						//*** Delete
						ignore(list, i);
						i--;
						//*** Delete End
					}
				}
				else if(list.get_Request(i).see_symbol() == Element.FLOOR &&
						list.get_Request(i).see_direction() == elevator.get_direction() &&
						list.get_Request(i).see_destination() == elevator.get_floor() &&
						list.get_Request(i).see_time() < elevator.get_time() - Element.DOOR_OPENING &&
						i != next_request){
					
					if(check_duplicate(list, i) == true){ //
						//*** Delete
						ignore(list, i);
						if(main_request > i)
							main_request--;
						if(next_request > i)
							next_request--;
						i--;
						//*** Delete End
					}
					else{
						if(elevator.get_direction() == Element.ELEVATOR_UP){
							elevator.set_time(elevator.get_time() - Element.DOOR_OPENING);
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
							floor_up[list.get_Request(i).see_destination()] = elevator.get_time();
						}
						else{
							elevator.set_time(elevator.get_time() - Element.DOOR_OPENING);
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
							floor_down[list.get_Request(i).see_destination()] = elevator.get_time();
						}
						//*** Delete
						ignore(list, i);
						if(main_request > i)
							main_request--;
						if(next_request > i)
							next_request--;
						i--;
						//*** Delete End
					}
				}
			}
		}
		else{
				//ͬ��
			elevator.set_time(elevator.get_time() + Element.DOOR_OPENING);
			if(list.get_Request(next_request).see_symbol() == Element.ELE){
				System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				ele[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
			else if(list.get_Request(next_request).see_symbol() == Element.FLOOR && list.get_Request(next_request).see_direction() == Element.FLOOR_UP){
				System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
			else{
				System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: list != null && elevator != null && 0 <= next_main_request < list.length;
		* @MODIFIES : elevator;
		* @EFFECTS :
		* (list.get_Request(next_main_request).see_destination() > elevator.get_floor()) ==> elevator.set_dirction(Element.ELEVATOR_UP); 
		* (list.get_Request(next_main_request).see_destination() < elevator.get_floor()) ==> elevator.set_direction(Element.ELEVATOR_DOWN);
		* list.get_Request(next_main_request).see_destination() == elevator.get_floor()) ==> elevator.set_dirction(Element.ELEVATOR_STILL);
		*/
	public void change_elevator_dirction(RequestList list , Elevator elevator, int next_main_request){ 
		if(list.get_Request(next_main_request).see_destination() > elevator.get_floor()){
			elevator.set_dirction(Element.ELEVATOR_UP);
		}
		else if(list.get_Request(next_main_request).see_destination() < elevator.get_floor()){
			elevator.set_dirction(Element.ELEVATOR_DOWN);
		}
		else {
			elevator.set_dirction(Element.ELEVATOR_STILL);
		}
	}
	/**
	* 注释.......
		* @REQUIRES: list != null && 0 <= which_one < list.length;
		* @MODIFIES : list;
		* @EFFECTS :
		* list.remove(which_one) == true;
		*/
	public void ignore(RequestList list , int which_one){
		list.remove_Request(which_one);
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS :
		* (super.repOK() && main_request >= -1 && main_request < RequestList.get_Number() && picking_request >= -1 && picking_request <= RequestList.get_Number()) ==> \result == true;
		* !(super.repOK() && main_request >= -1 && main_request < RequestList.get_Number() && picking_request >= -1 && picking_request <= RequestList.get_Number()) ==> \result == false;
		*/
	public boolean repOK(){
		boolean result = false;
		if(super.repOK() && main_request >= -1 && main_request < RequestList.get_Number() && picking_request >= -1 && picking_request <= RequestList.get_Number())
			result = true;
		return result;
	}
}



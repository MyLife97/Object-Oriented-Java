package Elev;


public class Scheduler extends Controller{

	int main_request = -1;
	int picking_request;
	
	int counter = 0;
	
	public Scheduler(){
		super();
	}	
	
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
	public int search_picking_one(RequestList list , Elevator elevator){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.Ele){
				if(elevator.get_direction() == Element.Elevator_up &&
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

				else if(elevator.get_direction() == Element.Elevator_down && 
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
				if( elevator.get_direction() == Element.Elevator_up && 
					list.get_Request(i).see_dirction() == Element.Floor_up &&
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

				else if(elevator.get_direction() == Element.Elevator_down &&
						list.get_Request(i).see_dirction() == Element.Floor_down &&
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

	public int search_main_one(RequestList list, Elevator elevator){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.Ele){

				if(elevator.get_direction() == Element.Elevator_up && //��������
					elevator.get_floor() < list.get_Request(i).see_destination() && //¥������

					(double)(elevator.get_time() - Element.Door_opening) > list.get_Request(i).see_time()){
					if(mark == -1){
						mark = i;
					}
				}

				else if(elevator.get_direction() == Element.Elevator_down
						&& elevator.get_floor() > list.get_Request(i).see_destination() &&
						(double)(elevator.get_time() - Element.Door_opening) > list.get_Request(i).see_time()){
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
	

	public boolean check_duplicate(RequestList list , int now_request){
		if(list.get_Request(now_request).see_symbol() == Element.Ele){
			if(ele[list.get_Request(now_request).see_destination()] >= list.get_Request(now_request).see_time()){
				System.out.printf("#SAME[ER,%d,%d]\n" , list.get_Request(now_request).see_destination() , list.get_Request(now_request).see_time());
				return true;
			}
		}
		else if(list.get_Request(now_request).see_symbol() == Element.Floor){
			if(list.get_Request(now_request).see_dirction() == Element.Floor_up){
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

	public void excecute(RequestList list , Elevator elevator, int next_request){
		int i;
		int last_floor;
		if(elevator.get_direction() != Element.Elevator_still){
			last_floor = elevator.get_floor();
			if(elevator.get_floor() < list.get_Request(next_request).see_destination()){//������
				elevator.set_time(elevator.get_time() + (double)(list.get_Request(next_request).see_destination() - elevator.get_floor())/2);
				elevator.move_to(list.get_Request(next_request).see_destination());
				if(list.get_Request(next_request).see_symbol() == Element.Ele){
					ele[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				else if(list.get_Request(next_request).see_symbol() == Element.Floor && list.get_Request(next_request).see_dirction() == Element.Floor_up){
					floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				else{
					floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				elevator.set_time(elevator.get_time() + Element.Door_opening);
			}
			else{//������
				elevator.set_time(elevator.get_time() + (double)(elevator.get_floor() - list.get_Request(next_request).see_destination())/2);
				elevator.move_to(list.get_Request(next_request).see_destination());
				if(list.get_Request(next_request).see_symbol() == Element.Ele){
					ele[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				else if(list.get_Request(next_request).see_symbol() == Element.Floor && list.get_Request(next_request).see_dirction() == Element.Floor_up){
					floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				else{
					floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time() + Element.Door_opening;
				}
				elevator.set_time(elevator.get_time() + Element.Door_opening);
			}


			for(i = 0; i < list.get_Number();i++){
				if(i == next_request){
					elevator.set_time(elevator.get_time() - Element.Door_opening);
					if(last_floor < list.get_Request(next_request).see_destination()){
						if(list.get_Request(next_request).see_symbol() == Element.Ele){
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
						else if(list.get_Request(next_request).see_symbol() == Element.Floor && list.get_Request(next_request).see_dirction() == Element.Floor_up){
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
						else{
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(),elevator.toString());
						}
					}
					else{//������
						if(list.get_Request(next_request).see_symbol() == Element.Ele){
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
						else if(list.get_Request(next_request).see_symbol() == Element.Floor && list.get_Request(next_request).see_dirction() == Element.Floor_up){
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
						else{
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
						}
					}
					elevator.set_time(elevator.get_time() + Element.Door_opening);
				}
				
				else if(list.get_Request(i).see_symbol() == Element.Ele &&
					list.get_Request(i).see_destination() == elevator.get_floor() &&
					list.get_Request(i).see_time() < elevator.get_time() - Element.Door_opening &&
					i != next_request){
					
					
					if(check_duplicate(list, i) == true){
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
						if(elevator.get_direction() == Element.Elevator_up){
							elevator.set_time(elevator.get_time() - Element.Door_opening);
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.Door_opening);
							ele[list.get_Request(i).see_destination()] = elevator.get_time();
						}
						else if(elevator.get_direction() == Element.Elevator_down){
							elevator.set_time(elevator.get_time() - Element.Door_opening);
							System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.Door_opening);
							ele[list.get_Request(i).see_destination()] = elevator.get_time();
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
				else if(list.get_Request(i).see_symbol() == Element.Floor &&
						list.get_Request(i).see_dirction() == elevator.get_direction() &&
						list.get_Request(i).see_destination() == elevator.get_floor() &&
						list.get_Request(i).see_time() < elevator.get_time() - Element.Door_opening &&
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
						if(elevator.get_direction() == Element.Elevator_up){
							elevator.set_time(elevator.get_time() - Element.Door_opening);
							System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.Door_opening);
							floor_up[list.get_Request(i).see_destination()] = elevator.get_time();
						}
						else{
							elevator.set_time(elevator.get_time() - Element.Door_opening);
							System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(i).see_time(), elevator.toString());
							elevator.set_time(elevator.get_time() + Element.Door_opening);
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
			elevator.set_time(elevator.get_time() + Element.Door_opening);
			if(list.get_Request(next_request).see_symbol() == Element.Ele){
				System.out.printf("[ER,%d,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				ele[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
			else if(list.get_Request(next_request).see_symbol() == Element.Floor && list.get_Request(next_request).see_dirction() == Element.Floor_up){
				System.out.printf("[FR,%d,UP,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				floor_up[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
			else{
				System.out.printf("[FR,%d,DOWN,%d]/%s\n",elevator.get_floor() , list.get_Request(next_request).see_time(), elevator.toString());
				floor_down[list.get_Request(next_request).see_destination()] = elevator.get_time();
			}
		}
	}
	
	public void change_elevator_dirction(RequestList list , Elevator elevator, int next_main_request){ 
		if(list.get_Request(next_main_request).see_destination() > elevator.get_floor()){
			elevator.set_dirction(Element.Elevator_up);
		}
		else if(list.get_Request(next_main_request).see_destination() < elevator.get_floor()){
			elevator.set_dirction(Element.Elevator_down);
		}
		else {
			elevator.set_dirction(Element.Elevator_still);
		}
	}
	
	public void ignore(RequestList list , int which_one){
		list.remove_Request(which_one);
	}
	
}



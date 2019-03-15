package Elev;

import java.io.IOException;

public class mult_Scheduler extends Scheduler implements Runnable{
	private RequestList main_List = new RequestList();
	

	
	private Elevator[] elevators;
	private Floor floor;
	
	
	
	public int search_ele(Elevator[] elevators, Request request) {
		if(request.see_symbol() == Element.Ele){
			if(elevators[request.see_number()].can_take(request) == true ||
				elevators[request.see_number()].see_dirction() == Element.Elevator_still &&
				elevators[request.see_number()].see_ele_requestList().is_Empty() == true)
				return request.see_number();
			else
				return Element.No_target;
		}
		else{
			if(elevators[get_first(elevators)].can_take(request) == true){
				
				return get_first(elevators);//�����ʾ����
			}
			else if(elevators[get_second(elevators)].can_take(request) == true){
				return get_second(elevators);//�����ʾ����
			}
			else if(elevators[get_third(elevators)].can_take(request) == true){
				return get_third(elevators);//�����ʾ����
			}
			else if(elevators[get_first(elevators)].see_dirction() == Element.Elevator_still &&
					elevators[get_first(elevators)].see_ele_requestList().is_Empty() == true){
				return get_first(elevators);//�����ʾ����
			}
			else if(elevators[get_second(elevators)].see_dirction() == Element.Elevator_still &&
					elevators[get_second(elevators)].see_ele_requestList().is_Empty() == true){
				return get_second(elevators);//�����ʾ����
			}
			else if(elevators[get_third(elevators)].see_dirction() == Element.Elevator_still &&
					elevators[get_third(elevators)].see_ele_requestList().is_Empty() == true){
				return get_third(elevators);//�����ʾ����
			}
			else if(elevators[Element.First_ele].see_dirction() == Element.Elevator_still &&
					elevators[Element.First_ele].see_ele_requestList().is_Empty() == true){
				return Element.First_ele;
			}
			else if(elevators[Element.Second_ele].see_dirction() == Element.Elevator_still &&
					elevators[Element.Second_ele].see_ele_requestList().is_Empty() == true){
				return Element.Second_ele;
			}
			else if(elevators[Element.Third_ele].see_dirction() == Element.Elevator_still &&
					elevators[Element.Third_ele].see_ele_requestList().is_Empty() == true){
				return Element.Third_ele;
			}
			if(elevators[Element.First_ele].can_take(request) == true){
				return Element.First_ele;//�����ʾ����
			}
			else if(elevators[Element.Second_ele].can_take(request) == true){
				return Element.Second_ele;//�����ʾ����
			}
			else if(elevators[Element.Third_ele].can_take(request) == true){
				return Element.Third_ele;//�����ʾ����
			}
			else{
				return Element.No_target;
			}
		}
	}
	
	public int get_first(Elevator[] elevators){ //�ܵ����ٵģ�Ҳ����mile��С��
		int i;
		int mark;
		int mile = elevators[1].see_mile();
		for(i = mark = 1; i < 4; i++){
			if(mile > elevators[i].see_mile()){
				mile = elevators[i].see_mile();
				mark = i;
			}
		}
		//System.out.println(mark);
		return mark;
	}
	
	public int get_second(Elevator[] elevators){
		int i;
		int max_mark;
		int sec_max_mark;
		int max_mile = elevators[1].see_mile();
		int sec_max_mile = Integer.MIN_VALUE;
		
		for(i = max_mark = sec_max_mark = 1; i < 4; i++){
			if(max_mile < elevators[i].see_mile()){
				sec_max_mile = max_mile;
				sec_max_mark = max_mark;
				max_mile = elevators[i].see_mile();
				max_mark = i;
			}
			else{
				if(sec_max_mile < elevators[i].see_mile()){
					sec_max_mile = elevators[i].see_mile();
					sec_max_mark = i;
				}
			}
		}
		return sec_max_mark;	
	}
	
	public int get_third(Elevator[] elevators){
		int i;
		int mark;
		int mile = elevators[1].see_mile();
		for(i = mark = 1; i < 4; i++){
			if(mile < elevators[i].see_mile()){
				mile = elevators[i].see_mile();
				mark = i;
			}
		}
		return mark;
	}
	
	public boolean check_duplicate(Elevator[] elevators, Floor floor, Request request){
		if(request.see_symbol() == Element.Ele){
			switch (request.see_number()) {
			case Element.First_ele:
				return elevators[Element.First_ele].see_button(request.see_destination());
			case Element.Second_ele:
				return elevators[Element.Second_ele].see_button(request.see_destination());
			case Element.Third_ele:
				return elevators[Element.Third_ele].see_button(request.see_destination());
			default:
				return false;
			}
		}
		else{
			switch (request.see_dirction()) {
			case Element.Floor_up:
				return floor.see_button(request.see_destination(), Element.Elevator_up);
			case Element.Floor_down:
				return floor.see_button(request.see_destination(), Element.Floor_down);

			default:
				return false;
			}
		}
	}
	
	public void push_button(Elevator[] elevators,Floor floor, Request request){
		if(request.see_symbol() == Element.Ele){
			switch (request.see_number()) {
			case Element.First_ele:
				elevators[Element.First_ele].set_button(request.see_destination(), Element.Button_on);
				break;
			case Element.Second_ele:
				elevators[Element.Second_ele].set_button(request.see_destination(), Element.Button_on);
				break;
			case Element.Third_ele:
				elevators[Element.Third_ele].set_button(request.see_destination(), Element.Button_on);
				break;
			}
		}
		else{
			switch (request.see_dirction()) {
			case Element.Floor_up:
				floor.set_button(request.see_destination(), Element.Floor_up, Element.Button_on);
				break;
			case Element.Floor_down:
				floor.set_button(request.see_destination(), Element.Floor_down, Element.Button_on);
				break;
			}
		}
	}

	public RequestList see_requestList(){
		return main_List;
	}
	
	public void set_elevators(Elevator[] elevators){
		this.elevators = elevators;
	}
	
	public void set_floor(Floor floor){
		this.floor = floor;
	}
	
	public void run(){
		
		int i;
		int j;
		int mark;
		while(true){
			//System.out.println(main_List.get_Number());
			/******************************************
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				
			}******************************************/
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				
			}
			
				
			for(i = 0; i < main_List.get_Number(); i++){
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					
				}
				if(search_ele(elevators, main_List.get_Request(i)) == Element.No_target){
					//System.out.println("no target");
					continue;
				}
				else{
					//�Ͱ�������˼���
					if(check_duplicate(elevators, floor, main_List.get_Request(i)) == false){
						try {
							mark = search_ele(elevators, main_List.get_Request(i));
							if(mark == 0)
								continue;
							else
								elevators[mark].add_Request(main_List.get_Request(i));
							for(j = 1; j < 4; j++){
								System.out.println(j + ":" + elevators[j].see_mile());
							}
						} catch (Exception e) {
							System.out.println("1:ERROR");
							System.out.println(i+"/"+main_List.get_Number());
						}
						
						System.out.println("Distributed Succeed");
						
						push_button(elevators, floor, main_List.get_Request(i));
						main_List.remove_Request(i);
						i--;
					}
					else{//SAME
						System.out.printf("#%d:SAME[%s,%.1f]\n" ,System.currentTimeMillis(), main_List.get_Request(i).see_self(),(double)(main_List.get_Request(i).see_time()-Begin.begin_time)/1000);
						try {
							Begin.bufWriter.write(String.format("#%d:SAME[%s,%.1f]\n" ,System.currentTimeMillis(), main_List.get_Request(i).see_self(),(double)(main_List.get_Request(i).see_time()-Begin.begin_time)/1000));
							Begin.bufWriter.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						main_List.remove_Request(i);
						i--;
					}
				}
			}
		
		}
	}
}



























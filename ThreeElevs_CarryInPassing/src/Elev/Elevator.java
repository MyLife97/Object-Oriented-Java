package Elev;

import java.io.IOException;


public class Elevator extends Thread{
	private boolean ele[] = new boolean[Element.Floor_number];
	private int direction = Element.Elevator_still;
	private int now_floor = 1;
	private int number;
	private int destination = Element.No_destination;
	private int mile;
	private double done_time = 0;
	private RequestList ele_requestList = new RequestList();
	
	public Elevator(int number){
		this.number = number;
	}
	
	
	public static Request make_Request(int num , int des ,long tim, String self){
		Request new_Request = new Request(Element.Ele , num , des , -1 , tim, self);
		return new_Request;
	}
	
	public RequestList see_ele_requestList(){
		return ele_requestList;
	}
	
	public void add_Request(Request new_Request){
		ele_requestList.add_Request(new_Request);
	}
	
	public boolean see_button(int floor){
		return ele[floor];
	}
	
	public void set_button(int floor, boolean state){
		ele[floor] = state;
	}
	
	public int see_dirction(){
		return direction;
	}
	
	public int see_mile(){
		return mile;
	}
	
	public boolean can_take(Request request){
		if(request.see_symbol() == Element.Ele){
			
			if(direction == Element.Elevator_up &&
				number == request.see_number()&&
				now_floor < request.see_destination()){
				return true;
			}
			else if(direction == Element.Elevator_down && 
					now_floor > request.see_destination() &&
					number == request.see_number()){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			if(direction == Element.Elevator_up && 
				request.see_dirction() == Element.Floor_up &&
				now_floor < request.see_destination() &&
				request.see_destination() <= destination){
				return true;
			}
			else if(direction == Element.Elevator_down &&
						request.see_dirction() == Element.Floor_down &&
						now_floor > request.see_destination() &&
						destination < request.see_destination()){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	public void run(){
		while(true){//扫描队列执行
			if(ele_requestList.is_Empty() == false){
				//System.out.println("Not empty:" + number + "-" + ele_requestList.get_Number());
				next_step(ele_requestList);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				direction = Element.Elevator_still;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Empty" + number);
			}
		}
	}
	
	int main_request = -1;
	int picking_request;
	
	public void next_step(RequestList list){
		
		if(main_request == -1){
			main_request = search_main_one(list);
			change_elevator_dirction(list, main_request);
			destination = list.get_Request(main_request).see_destination();
		}
		else{
			picking_request = search_picking_one(list);
			if(main_request != picking_request && picking_request != -1){

				excecute(list, picking_request);
				//*** Delete
				if(main_request > picking_request)
					main_request--;
				//*** Delete End

			}
			else{
				excecute(list, main_request);
				//*** Delete
				destination = Element.No_destination;
				//System.out.println("Here:" + ele_requestList.get_Number());
				
				
				main_request = -1;
				//*** Delete End
			}
		}
	}
	
	public int search_picking_one(RequestList list){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.Ele){
				if(direction == Element.Elevator_up &&
					now_floor < list.get_Request(i).see_destination() &&
					list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination()){
					
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() > list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}
				else if(direction == Element.Elevator_down && 
						now_floor > list.get_Request(i).see_destination() &&
						list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination()){

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
				if( direction == Element.Elevator_up && 
					list.get_Request(i).see_dirction() == Element.Floor_up &&
					now_floor < list.get_Request(i).see_destination() &&
					list.get_Request(i).see_destination() < list.get_Request(main_request).see_destination() &&
					list.get_Request(main_request).see_destination() >= list.get_Request(i).see_destination()){
					if(mark == -1){
						mark = i;
					}
					else{
						if(list.get_Request(mark).see_destination() > list.get_Request(i).see_destination()){
							mark = i;
						}
					}
				}

				else if(direction == Element.Elevator_down &&
						list.get_Request(i).see_dirction() == Element.Floor_down &&
						now_floor > list.get_Request(i).see_destination() &&
						list.get_Request(i).see_destination() > list.get_Request(main_request).see_destination() &&
						list.get_Request(main_request).see_destination() <= list.get_Request(i).see_destination()){
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
	
	public int search_main_one(RequestList list){
		int i;
		int mark = -1;
		for(i = 0; i < list.get_Number(); i++){
			if(list.get_Request(i).see_symbol() == Element.Ele){
				if(direction == Element.Elevator_up && //方向满足
					now_floor < list.get_Request(i).see_destination()){  //楼层满足
					
					if(mark == -1){
						mark = i;
					}
				}
				else if(direction == Element.Elevator_down && 
						now_floor > list.get_Request(i).see_destination()){
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
	
	public void change_elevator_dirction(RequestList list, int next_main_request){ 
		if(list.get_Request(next_main_request).see_destination() > now_floor){
			set_dirction(Element.Elevator_up);
		}
		else if(list.get_Request(next_main_request).see_destination() < now_floor){
			set_dirction(Element.Elevator_down);
		}
		else {
			set_dirction(Element.Elevator_still);
		}
	}
	
	public void ignore(RequestList list , int which_one){
		list.remove_Request(which_one);
	}

	public void excecute(RequestList list, int next_request){
		int i;
		boolean should_open;
		int mark_floor;

		if(direction != Element.Elevator_still){

			if(now_floor < list.get_Request(next_request).see_destination()){//往上走
				try {
					Thread.sleep(3000);
					done_time += 3;
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				should_open = false;
				now_floor++;
				mile ++;
				mark_floor = now_floor;
				
				for(i = 0; i < list.get_Number();i++){
					if(list.get_Request(i).see_destination() == now_floor){

						if(direction == Element.Elevator_up){
							System.out.printf("%d:[%s,%.1f]/(#%d,%d,UP,%d,%.1f)\n",System.currentTimeMillis(),
									list.get_Request(i).see_self(),(double)(list.get_Request(i).see_time() - Begin.begin_time)/1000,
									this.number ,this.now_floor,this.mile, (double)(list.get_Request(i).see_time() - Begin.begin_time)/1000 + done_time);
							try {
								Begin.bufWriter.write(String.format("%d:[%s,%.1f]/(#%d,%d,UP,%d,%.1f)\n",System.currentTimeMillis(),
										list.get_Request(i).see_self(),(double)(list.get_Request(i).see_time() - Begin.begin_time)/1000,
										this.number ,this.now_floor,this.mile, (double)(list.get_Request(i).see_time() - Begin.begin_time)/1000 + done_time));
								Begin.bufWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						else{
							System.out.printf("Waring!Maybe Error!");
						}
						//*** Delete
						
						ignore(list, i);
						if(main_request > i)
							main_request--;
						if(next_request > i)
							next_request--;
						i--;
						//*** Delete End
						should_open = true;
						
					}
				}
				if(should_open)
					try {
						Thread.sleep(6000);
						done_time += 6;
						ele[mark_floor] = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else if(now_floor > list.get_Request(next_request).see_destination()){//往下走
				try {
					Thread.sleep(3000);
					done_time += 3;
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				should_open = false;
				now_floor--;
				mile ++;
				mark_floor = now_floor;
				
				for(i = 0; i < list.get_Number();i++){
					if(list.get_Request(i).see_destination() == now_floor){

						if(direction == Element.Elevator_down){
							System.out.printf("%d:[%s,%.1f]/(#%d,%d,DOWN,%d,%.1f)\n",System.currentTimeMillis(),
									list.get_Request(i).see_self(),(double)(list.get_Request(i).see_time() - Begin.begin_time)/1000,
									this.number ,this.now_floor,this.mile, (double)(list.get_Request(i).see_time() - Begin.begin_time)/1000 + done_time);
							try {
								Begin.bufWriter.write(String.format("%d:[%s,%.1f]/(#%d,%d,DOWN,%d,%.1f)\n",System.currentTimeMillis(),
										list.get_Request(i).see_self(),(double)(list.get_Request(i).see_time() - Begin.begin_time)/1000,
										this.number ,this.now_floor,this.mile, (double)(list.get_Request(i).see_time() - Begin.begin_time)/1000 + done_time));
								Begin.bufWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else{
							System.out.printf("Waring!Maybe Error!");
						}
						//*** Delete
						ignore(list, i);
						if(main_request > i)
							main_request--;
						if(next_request > i)
							next_request--;
						i--;
						//*** Delete End
						should_open = true;
					}
				}
				if(should_open){
					try {
						Thread.sleep(6000);
						done_time += 6;
						ele[mark_floor] = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else{
			mark_floor = now_floor;
				//同层
			try {
				Thread.sleep(6000);
				done_time += 6;
				ele[mark_floor] = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			System.out.printf("%d:[%s,%.1f]/(#%d,%d,STILL,%d,%.1f)\n",System.currentTimeMillis(),
					list.get_Request(next_request).see_self(),(double)(list.get_Request(next_request).see_time()-Begin.begin_time)/1000,
					this.number ,this.now_floor,this.mile, (double)(list.get_Request(next_request).see_time()-Begin.begin_time)/1000 + done_time);
			try {
				Begin.bufWriter.write(String.format("%d:[%s,%.1f]/(#%d,%d,STILL,%d,%.1f)\n",System.currentTimeMillis(),
						list.get_Request(next_request).see_self(),(double)(list.get_Request(next_request).see_time()-Begin.begin_time)/1000,
						this.number ,this.now_floor,this.mile, (double)(list.get_Request(next_request).see_time()-Begin.begin_time)/1000 + done_time));
				Begin.bufWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//*** Delete
			ignore(list, next_request);
			if(main_request > next_request)
				main_request--;
			//*** Delete End
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	private double now_time = 0;
	    
	
	
	public void move_to(int des){
		now_floor = des;
	}
	
	public void set_time(double time){
		now_time = time;
	}
	
	public double get_time(){
		return now_time;
	}
	
	public int get_floor(){
		return now_floor;
	}
	
	public void set_dirction(int dir){
		this.direction = dir;
	}
	
	public int get_direction(){
		return this.direction;
	}
	
	public String print_direction(){
		if(direction == 0)
			return "UP";
		else if(direction == 1)
			return "DOWN";
		else {
			return "STILL";
		}
	}
	
	public String toString(){
		return String.format("(%d,%s,%.1f)", now_floor , print_direction() , now_time);
	}	
}

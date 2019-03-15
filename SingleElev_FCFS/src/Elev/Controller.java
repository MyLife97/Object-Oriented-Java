package Elev;

public class Controller {
	
	private double now_time = 0;
	private Request now_request;
	private double last_time = 0;
	
	private double ele[] = new double[Element.Floor_number];
	private double floor_up[] = new double[Element.Floor_number];
	private double floor_down[] = new double[Element.Floor_number];
	
	public Controller(){
		int i;
		for(i = 0; i < Element.Floor_number; i++){
			ele[i] = -1;
			floor_up[i] = -1;
			floor_down[i] = -1;
		}
	}
	
	public void next_step(RequestList list , Elevator elevator){
		if(list.is_Empty() != true){
			if((now_request = list.get_Request(0)).see_symbol() == 0){ //is Ele
				if(ele[now_request.see_destination()] < now_request.see_time() && now_request.see_time() >= last_time){//同地点上次请求时间小于等于这次
					if(now_time < now_request.see_time()){//执行的快，请求的慢
						now_time = last_time = now_request.see_time();
						ele[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
						list.remove_Request(0);
					}
					else{//请求的快，执行的慢
						last_time = now_request.see_time();
						ele[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
						list.remove_Request(0);
					}
				}
				else{//同地点上次请求完成时间大于这次，忽略
					ignore(list);
					System.out.println("#Duplicated Request!");
				}
			}
			else{ // is floor
				if(now_request.see_dirction() == Element.Floor_up){
					if(floor_up[now_request.see_destination()] < now_request.see_time() && now_request.see_time() >= last_time){//同地点上次请求时间小于等于这次
						if(now_time < now_request.see_time()){//执行的快，请求的慢
							now_time = last_time = now_request.see_time();
							floor_up[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
							list.remove_Request(0);
						}
						else{//请求的快，执行的慢
							last_time = now_request.see_time();
							floor_up[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
							list.remove_Request(0);
						}
					}
					else{//同地点上次请求完成时间大于这次，忽略
						ignore(list);
						System.out.println("#Duplicated Request!");
					}
				}
				else{
					if(floor_down[now_request.see_destination()] < now_request.see_time() && now_request.see_time() >= last_time){//同地点上次请求时间小于等于这次
						if(now_time < now_request.see_time()){//执行的快，请求的慢
							now_time = last_time = now_request.see_time();
							floor_down[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
							list.remove_Request(0);
						}
						else{//请求的快，执行的慢
							last_time = now_request.see_time();
							floor_down[now_request.see_destination()] = now_time = excecute_and_print(now_time, now_request, elevator);
							list.remove_Request(0);
						}
					}
					else{//同地点上次请求完成时间大于这次，忽略
						ignore(list);
						System.out.println("#Duplicated Request!");
					}
				}
			}
		}
		else{
			ExHandler.exit();
		}
	}
	
	public double excecute_and_print(double now_time , Request now_request , Elevator elevator){
		double finish_time = 0;
		if(elevator.get_floor() == now_request.see_destination()){
			finish_time = now_time + 1;//open the door     ele still
			//System.out.println("(" + elevator.get_floor() + ",STILL," + finish_time + ")");
			System.out.printf("(%d,STILL,%.1f)\n", elevator.get_floor() , finish_time);
		}
		else if(elevator.get_floor() < now_request.see_destination()){//往上走
			finish_time = now_time + (double)(now_request.see_destination() - elevator.get_floor())/2;
			elevator.move_to(now_request.see_destination());
			//System.out.println("(" + elevator.get_floor() + ",UP," + finish_time%.1f + ")");
			System.out.printf("(%d,UP,%.1f)\n", elevator.get_floor() , finish_time);
			finish_time += 1;
		}
		else{
			finish_time = now_time + (double)(elevator.get_floor() - now_request.see_destination())/2;
			elevator.move_to(now_request.see_destination());
			//System.out.println("(" + elevator.get_floor() + ",DOWN," + finish_time%.1f + ")");
			System.out.printf("(%d,DOWN,%.1f)\n", elevator.get_floor() , finish_time);
			finish_time += 1;
		}
		return finish_time;
	}
	
	public void ignore(RequestList list){
		list.remove_Request(0);
	}
}

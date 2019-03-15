package stupid_Taxi;


public class Scheduler extends Thread{
	
	int i;
	
	public void run(){
		int i;
		int number;
		int index;
		long systime;
		Request now_request;
		while(true){
			number = Begin.requestList.getLength();
			for(i = 0; i < number; i++){
				now_request = Begin.requestList.getRequest(i);
				systime = System.currentTimeMillis() /100 * 100;
				if(systime - now_request.see_systime() >= Element.WindowClose){
					if(now_request.calling.isEmpty() != true && now_request.isPicked == false){
						//派送
						index = Select(now_request);
						if(index != -1 && now_request.isOut == false){
							setTaxi(index, now_request);
							System.out.println(now_request.self + " is picked");
							now_request.isOut = true;
						}
						else{
							if(now_request.isOut == false){
								System.out.println("OtherFirst - No One Pick " + now_request.self);
								now_request.isOut = true;
							}
						}
						//
					}
					else{
						//nothing
						if(now_request.isOut == false){
							System.out.println("Empty - No One Pick " + now_request.self);
							now_request.isOut = true;
						}
					}
				}
				else{
					//司机抢单
					System.out.println(now_request.self);
					allPick(now_request);
				}
				//sleep(1);
			}
			//sleep(1);
		}
	}
	
	public void setTaxi(int index , Request now_request){
		Taxi now_taxi;
		now_taxi = now_request.calling.get(index);
		
		now_taxi.status = Element.Picking;
		now_taxi.x_departure = now_request.x_departure;
		now_taxi.y_departure = now_request.y_departure;
		now_taxi.x_destination = now_request.x_destination;
		now_taxi.y_destination = now_request.y_destination;
		
		
		now_request.isPicked = true;
		now_request.taxi_index = index;
		now_request.taxi_number = now_taxi.number;
		now_request.taxi_x_picked = now_taxi.x_now;
		now_request.taxi_y_picked = now_taxi.y_now;
	}
	
	public int Select(Request request){
		int i;
		int mark = -1;
		int max_rep = -1;
		Snapshot snapshot;
		int min_dis = Element.MaxInt;
		Taxi now_taxi;
		for(i = 0; i < request.calling.size(); i++){
			now_taxi = request.calling.get(i);
			snapshot = new Snapshot(now_taxi.number, now_taxi.x_now, now_taxi.y_now,
					now_taxi.status, now_taxi.reputation, System.currentTimeMillis() / 100 * 100);
			request.taxis.add(snapshot);
			if(now_taxi.status == Element.Waiting){
				if(now_taxi.reputation > max_rep){
					mark = i;
					max_rep = now_taxi.reputation;
					min_dis = Map.distance(now_taxi.x_now, now_taxi.y_now, request.x_departure, request.y_departure);
				}
				else if(now_taxi.reputation == max_rep &&
						Map.distance(now_taxi.x_now, now_taxi.y_now, request.x_departure, request.y_departure) < min_dis){
					mark = i;
					min_dis = Map.distance(now_taxi.x_now, now_taxi.y_now, request.x_departure, request.y_departure);
				}
			}
		}
		if(mark != -1){
			now_taxi = request.calling.get(mark);
			now_taxi.setRequest(request);
		}
		return mark;
	}
	//Map.distance(now_taxi.x_now, now_taxi.y_now, request.x_departure, request.y_departure) < 
	
	public void allPick(Request request){
		int i;
		Taxi now_taxi;
		
		for(i = 0; i < Element.TaxiNumber; i++){
			now_taxi = Begin.taxis[i];
			if(now_taxi.status == Element.Waiting &&
				Math.abs(now_taxi.x_now - request.x_departure) <= 2 &&
				Math.abs(now_taxi.y_now - request.y_departure) <= 2 &&
				request.checkSame(now_taxi.number) != true){
				request.calling.add(now_taxi);
				now_taxi.reputation += 1;
				System.out.println("Have One");
			}
		}
	}
	
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

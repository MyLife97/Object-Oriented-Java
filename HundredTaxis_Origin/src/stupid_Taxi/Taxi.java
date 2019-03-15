package stupid_Taxi;

import java.util.ArrayList;

import java.awt.Point;


public class Taxi extends Thread{
	int number;
	int x_now;
	int y_now;
	
	long systime;
	long last_resttime;
	int status;
	int reputation;
	
	int x_departure;
	int y_departure;
	
	int x_destination;
	int y_destination;
	
	Request request;
	
	ArrayList<Integer> path = null;
	
	public Taxi(int number , int x_now, int y_now, long systime){
		this.number = number;
		this.x_now = x_now;
		this.y_now = y_now;
		this.systime = systime;
		status = Element.Waiting;
		reputation = Element.Zero;
		last_resttime = systime;
	}
	
	public void run(){
		int dir;
		while(true){
			systime = System.currentTimeMillis() / 100 * 100;
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			if(status == Element.Waiting){
				dir = hang_dir(x_now , y_now);
				hang_around(dir);
				sleep(500);
			}
			else if(status == Element.Picking){
				goto_departure(x_departure, y_departure);
			}
			else if(status == Element.Serving){
				
				goto_destination(x_destination , y_destination);
			}
			else{
				sleep(999);
				last_resttime = System.currentTimeMillis() / 100 * 100;
				status = Element.Waiting;
			}
			
			if((systime - last_resttime >= 20 * 1000) && status == Element.Waiting){
				status = Element.Stop;
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
	
	public void hang_around(int hang_dir){
		if(hang_dir == Element.Up){
			x_now = x_now - 1;
		}
		else if(hang_dir == Element.Down){
			x_now = x_now + 1;
		}
		else if(hang_dir == Element.Left){
			y_now = y_now - 1;
		}
		else {
			y_now = y_now + 1;
		}
	}
	
	public int hang_dir(int x_now , int y_now){
		boolean up = false, down =false, left = false , right = false;
		
		if(Map.map[x_now][y_now] == 2 || Map.map[x_now][y_now] == 3){
			down = true;
		}
		
		if(Map.map[x_now][y_now] == 1 || Map.map[x_now][y_now] == 3){
			right = true;
		}
		
		if(x_now != 0 && (Map.map[x_now - 1][y_now] == 2 || Map.map[x_now - 1][y_now] == 3)){
			up = true;
		}
		
		if(y_now != 0 && (Map.map[x_now][y_now - 1] == 1 || Map.map[x_now][y_now - 1] == 3)){
			left = true;
		}
		
		return random_dir(up, down, left, right);
	}
	
	
	
	public int random_dir(boolean up, boolean down, boolean left, boolean right){
		ArrayList<Integer> List = new ArrayList<Integer>();
		int i;
		if(up)
			List.add(Element.Up);
		if(down)
			List.add(Element.Down);
		if(left)
			List.add(Element.Left);
		if(right)
			List.add(Element.Right);
		
		i = (int)(Math.random() * List.size());
		
		return List.get(i);
	}
	
	public void goto_destination(int x , int y){
		int i;
		getPath(x, y);
		Snapshot snapshot;
		
		for(i = 0; i < path.size(); i++){
			x_now = path.get(i) / 80;
			y_now = path.get(i) % 80;
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis() /100 * 100);
			request.arriveRoute.add(snapshot);
			
			if(i != path.size() - 1)
				sleep(199);
			else{
				status = Element.Stop;
				sleep(999);
				status = Element.Waiting;
				last_resttime = System.currentTimeMillis() / 100 * 100;
				path = null;
				reputation += 3;
				break;
			}
		}
	}
	
	public void goto_departure(int x , int y){
		int i;
		getPath(x, y);
		Snapshot snapshot;
		for(i = 0; i < path.size(); i++){
			x_now = path.get(i) / 80;
			y_now = path.get(i) % 80;
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);	
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis() /100 * 100);
			request.pickRoute.add(snapshot);
			
			if(i != path.size() - 1)
				sleep(199);
			else{
				sleep(999);
				status = Element.Serving;
			}
		
		}
	}
	
	
	public void getPath(int x , int y){
		//int i;
		path = Map.getPath(x_now, y_now, x, y);
		//for(i= 0; i < path.size(); i++){
			//System.out.printf("x_now:%d , y_now:%d\n" , path.get(i)/80 , path.get(i)%80);
		//}
	}
	
	public boolean checkArrived(){
		if((status == Element.Picking || status == Element.Serving) &&
			x_now == x_destination && y_now == y_destination){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setRequest(Request request){
		this.request = request;
	}
}

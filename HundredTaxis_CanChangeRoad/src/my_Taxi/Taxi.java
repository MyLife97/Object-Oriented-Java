package my_Taxi;

import java.util.ArrayList;

import java.awt.Point;


public class Taxi extends Thread{
	int number;
	int x_now;
	int y_now;
	
	long systime;
	long systimemark1;
	long systimemark2;
	long last_resttime;
	int status;
	int reputation;
	
	int x_departure;
	int y_departure;
	
	int x_destination;
	int y_destination;
	
	boolean force = false;
	
	Request request;
	
	ArrayList<Integer> path = null;
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this;
		* @EFFECTS : 
		* this.number = number;
		* this.x_now = x_now;
		* this.y_now = y_now;
		* this.systime = systime;
		* status = Element.Waiting;
		* reputation = Element.Zero;
		* last_resttime = systime;
		*/	
	public Taxi(int number , int x_now, int y_now, long systime){
		this.number = number;
		this.x_now = x_now;
		this.y_now = y_now;
		this.systime = systime;
		status = Element.Waiting;
		reputation = Element.Zero;
		last_resttime = systime;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this_taxi.info;
		* @EFFECTS : 
		* \exist one Request for taxi.cantake() ==> complete this request;
		*/
	public void run(){
		int dir;
		while(true){
			systime = System.currentTimeMillis() / 100 * 100;
			systimemark1 = System.currentTimeMillis();
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			if(status == Element.Waiting){
				dir = hang_dir(x_now , y_now);
				hang_around(dir);
				systimemark2 = System.currentTimeMillis();
				if(500 + systimemark1 - systimemark2 > 0)
					sleep(500 + systimemark1 - systimemark2);//sleep 500
			}
			else if(status == Element.Picking){
				goto_departure(x_departure, y_departure);
			}
			else if(status == Element.Serving){
				goto_destination(x_destination , y_destination);
			}
			else{
				systimemark2 = System.currentTimeMillis();//sleep 1000
				if(1000 + systimemark1 - systimemark2 > 0)
					sleep(1000 + systimemark1 - systimemark2);
				
				last_resttime = System.currentTimeMillis() / 100 * 100;
				status = Element.Waiting;
			}
			
			if((systime - last_resttime > 20 * 1000) && status == Element.Waiting){
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
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.x_now, this.y_now;
		* @EFFECTS : 
		* Update this taxi's direction;
		*/
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
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Find a random way base on the Map;
		*/
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
	
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == random_dircation;
		*/
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
	/**
	* 注释.......
		* @REQUIRES:  None;
		* @MODIFIES : Taxi;
		* @EFFECTS : 
		* Update taxi's location base on destination;
		*/
	public void goto_destination(int x , int y){
		Snapshot snapshot;
		
		do{
			systimemark1 = System.currentTimeMillis();
			path = guiInfo.getPath(x_now * 80 + y_now , x * 80 + y);
			
			if(x_now == x && y_now == y){
				//nothing
			}
			else{
				x_now = path.get(Element.nextStep) / 80;
				y_now = path.get(Element.nextStep) % 80;
			}
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis() /100 * 100);
			if(force == false)
				request.arriveRoute.add(snapshot);
			
			if(x_now*80 + y_now != x*80 + y){
				systimemark2 = System.currentTimeMillis();
				if(500 + systimemark1 - systimemark2 > 0)
					sleep(500 + systimemark1 - systimemark2);
				else{
					sleep(1);
				}
			}
			else{
				status = Element.Stop;
				systimemark2 = System.currentTimeMillis();
				if(1500 + systimemark1 - systimemark2 > 0)
					sleep(1500 + systimemark1 - systimemark2);		
				else{
					sleep(1);
				}				
				status = Element.Waiting;
				last_resttime = System.currentTimeMillis() / 100 * 100;
				path = null;
				reputation += 3;
				force = false;
				break;
			}
		}while(x_now*80 + y_now != x*80 + y);
	}
	/**
	* 注释.......
		* @REQUIRES:  None;
		* @MODIFIES : Taxi;
		* @EFFECTS : 
		* Update taxi's location based on the departure;
		*/
	public void goto_departure(int x , int y){
		
		Snapshot snapshot;
		
		do{
			systimemark1 = System.currentTimeMillis();
			path = guiInfo.getPath(x_now * 80 + y_now , x * 80 + y);
			
			if(x_now == x && y_now == y){
				//nothing
			}
			else{
				x_now = path.get(Element.nextStep) / 80;
				y_now = path.get(Element.nextStep) % 80;
			}
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis() /100 * 100);
			if(force == false)
				request.pickRoute.add(snapshot);
			
			if(x_now*80 + y_now != x*80 + y){
				systimemark2 = System.currentTimeMillis();
				if(500 + systimemark1 - systimemark2 > 0)
					sleep(500 + systimemark1 - systimemark2);
				else{
					sleep(1);
				}
			}
			else{
				systimemark2 = System.currentTimeMillis();
				if(1500 + systimemark1 - systimemark2 > 0)
					sleep(1500 + systimemark1 - systimemark2);	
				else{
					sleep(1);
				}
				force = false;
				status = Element.Serving;
			}
		}while(x_now*80 + y_now != x*80 + y);
	}
	
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == isArrived();
		*/
	public boolean checkArrived(){
		if((status == Element.Picking || status == Element.Serving) &&
			x_now == x_destination && y_now == y_destination){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.request;
		* @EFFECTS : 
		* this.request = request;
		*/
	public void setRequest(Request request){
		this.request = request;
	}
}

package my_Taxi;

import java.util.ArrayList;
import java.util.Vector;
import java.awt.Point;

/**
 * @OVERVIEW:
 * 这个类是出租车类
 * 包括的成员信息有车辆当前位置，编号，运动方向，系统时间，状态，信誉，出发地，目的地等
 * run方法用于完成被分配的请求，在没有请求的情况下随机运动
 */
public class Taxi extends Thread{
	int number;
	int x_now;
	int y_now;
	
	int moveTimes = 0;
	
	DirVector last_direction = null;
	DirVector next_direction = null;
	
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
	int[] offset = new int[] {0, -80, 80, -1, 1};
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
		sleep(100);
		int dir;
		
		while(true){
			systime = System.currentTimeMillis();
			systimemark1 = System.currentTimeMillis();
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			if(status == Element.Waiting){
				
				do {
					dir = hangDir(x_now , y_now);
					next_direction = makeVector(dir);
					
					if(checkWait(last_direction, next_direction)){
						synchronized(Begin.lightMap){
							try {
								System.out.println("Taxi " + number + " is waiting");
								LightMap.waitTaxi ++;
								Begin.lightMap.wait();
								systimemark1 = System.currentTimeMillis();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
						}
					}
				} while (!stillCanGo(dir));

				hangAround(dir);
				moveTimes ++;
				last_direction = next_direction;
				
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
				
				last_resttime = System.currentTimeMillis();
				status = Element.Waiting;
				moveTimes = 0;
			}
			
			if(moveTimes >= 40 && status == Element.Waiting){
				status = Element.Stop;
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 1 <= dir <= 4;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (dir == Element.Up && (Map.map[x_now - 1][y_now] == 3 || Map.map[x_now - 1][y_now] == 2)) ==> \result == true;
		* (dir == Element.Down && (Map.map[x_now][y_now] == 3 || Map.map[x_now][y_now] == 2)) ==> \result == true;
		* (dir == Element.Left && (Map.map[x_now][y_now - 1] == 3 || Map.map[x_now][y_now - 1] == 1)) ==> \result == true; 
		* (dir == Element.Right && (Map.map[x_now][y_now] == 3 || Map.map[x_now][y_now] == 1)) ==> \result == true;
		*/
	public boolean stillCanGo(int dir){
		if(dir == Element.Up && (Map.map[x_now - 1][y_now] == 3 || Map.map[x_now - 1][y_now] == 2))
			return true;
		else if(dir == Element.Down && (Map.map[x_now][y_now] == 3 || Map.map[x_now][y_now] == 2))
			return true;
		else if(dir == Element.Left && (Map.map[x_now][y_now - 1] == 3 || Map.map[x_now][y_now - 1] == 1))
			return true;
		else if(dir == Element.Right && (Map.map[x_now][y_now] == 3 || Map.map[x_now][y_now] == 1))
			return true;
		return false;
	}
	/**
	* 注释.......
		* @REQUIRES: last_direction != null, next_direction != null;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (Begin.lightMap.lightmap[x_now * 80 + y_now] == 1 && (last_direction.dir_x == 0 && last_direction.crossProduct(next_direction) > 0|| last_direction.dir_y == 0 && last_direction.dotProduct(next_direction) > 0)) ==> \result == true;
		* (Begin.lightMap.lightmap[x_now * 80 + y_now] == 2 && (last_direction.dir_y == 0 && last_direction.crossProduct(next_direction) > 0|| last_direction.dir_x == 0 && last_direction.dotProduct(next_direction) > 0)) ==> \result == true;
		*/
	public boolean checkWait(DirVector last_direction, DirVector next_direction){
		if(last_direction == null){
			return false;
		}
		else{
			if(Begin.lightMap.lightmap[x_now * 80 + y_now] == 1 && 
				(last_direction.dir_x == 0 && last_direction.crossProduct(next_direction) > 0
				|| last_direction.dir_y == 0 && last_direction.dotProduct(next_direction) > 0)){
				return true;
			}
			else if(Begin.lightMap.lightmap[x_now * 80 + y_now] == 2 && 
					(last_direction.dir_y == 0 && last_direction.crossProduct(next_direction) > 0
					|| last_direction.dir_x == 0 && last_direction.dotProduct(next_direction) > 0)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	* 注释.......
		* @REQUIRES: 1 <= dir <= 4;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == new_direction;
		*/
	public DirVector makeVector(int dir){
		DirVector new_direction;
		if(dir == Element.Up)
			new_direction = new DirVector(x_now, y_now, x_now - 1, y_now);
		else if(dir == Element.Down)
			new_direction = new DirVector(x_now, y_now, x_now + 1, y_now);
		else if(dir == Element.Left)
			new_direction = new DirVector(x_now, y_now, x_now, y_now - 1);
		else
			new_direction = new DirVector(x_now, y_now, x_now, y_now + 1);
		return new_direction;
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= x < 6400, 0 <= y < 6400;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == random_direction based on Map;
		* 随机出最小流量的方向
		*/
	public int hangDir(int x, int y){
		int direction;
		int number;
		int listNumber;
		listNumber = getListNumber(x, y);
		do {
			number = (int)(Math.random() * RandomList.randomList[listNumber].size());
			direction = RandomList.randomList[listNumber].get(number);
		} while (!checkFlow(x, y, direction));
		return direction;
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= x < 6400, 0 <= y < 6400, 1 <= direction <= 4;
		* @MODIFIES : None;
		* @EFFECTS : 
		* direction方向的流量是最小的 ==> \result == true;
		* 否则 ==> \result == false;
		*/
	public boolean checkFlow(int x, int y, int direction){
		int root;
		int destination;
		root = x * 80 + y;
		destination = root + offset[direction];
		
		int flow = FlowMap.getFlow(root, destination);
		
		if(Map.map[x_now][y_now] == 2 || Map.map[x_now][y_now] == 3){
			//down = true; 2
			if(flow > FlowMap.getFlow(root, root + offset[Element.Down]))
				return false;
		}
		
		if(Map.map[x_now][y_now] == 1 || Map.map[x_now][y_now] == 3){
			//right = true; 4
			if(flow > FlowMap.getFlow(root, root + offset[Element.Right]))
				return false;
		}
		
		if(x_now != 0 && (Map.map[x_now - 1][y_now] == 2 || Map.map[x_now - 1][y_now] == 3)){
			//up = true; 1
			if(flow > FlowMap.getFlow(root, root + offset[Element.Up]))
				return false;
		}
		
		if(y_now != 0 && (Map.map[x_now][y_now - 1] == 1 || Map.map[x_now][y_now - 1] == 3)){
			//left = true; 3
			if(flow > FlowMap.getFlow(root, root + offset[Element.Left]))
				return false;
		}		
		//System.out.println(true);
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Thread.Sleep(time);
		*/
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 1 <= hangDir <= 4;
		* @MODIFIES : this.x_now, this.y_now;
		* @EFFECTS : 
		* Update this taxi's direction;
		*/
	public void hangAround(int hangDir){
		if(hangDir == Element.Up){
			FlowMap flowMap = new FlowMap(x_now * 80 + y_now, (x_now - 1) * 80 + y_now, 1);
			flowMap.start();
			x_now = x_now - 1;
		}
		else if(hangDir == Element.Down){
			FlowMap flowMap = new FlowMap(x_now * 80 + y_now, (x_now + 1) * 80 + y_now, 1);
			flowMap.start();
			x_now = x_now + 1;
		}
		else if(hangDir == Element.Left){
			FlowMap flowMap = new FlowMap(x_now * 80 + y_now, x_now * 80 + y_now - 1, 1);
			flowMap.start();
			y_now = y_now - 1;
		}
		else {
			FlowMap flowMap = new FlowMap(x_now * 80 + y_now, x_now * 80 + y_now + 1, 1);
			flowMap.start();
			y_now = y_now + 1;
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= x_now < 80, 0 <= y_now < 80;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Find a random way base on the Map;
		*/
	public int getListNumber(int x_now , int y_now){
		int number = 0;
		
		if(Map.map[x_now][y_now] == 2 || Map.map[x_now][y_now] == 3){
			//down = true; 2
			number += 2;
		}
		
		if(Map.map[x_now][y_now] == 1 || Map.map[x_now][y_now] == 3){
			//right = true; 4
			number += 8;
		}
		
		if(x_now != 0 && (Map.map[x_now - 1][y_now] == 2 || Map.map[x_now - 1][y_now] == 3)){
			//up = true; 1
			number += 1;
		}
		
		if(y_now != 0 && (Map.map[x_now][y_now - 1] == 1 || Map.map[x_now][y_now - 1] == 3)){
			//left = true; 3
			number += 4;
		}
		
		return number;
	}
	
	
	/**
	* 注释.......
		* @REQUIRES:  None;
		* @MODIFIES : Taxi;
		* @EFFECTS : 
		* 根据目的地，实时找到流量最小的路径执行运动，直到到达目的地
		*/
	public void goto_destination(int x , int y){
		Snapshot snapshot;
		moveTimes = 0;
		do{
			systimemark1 = System.currentTimeMillis();
			path = Map.getPath(x_now * 80 + y_now , x * 80 + y);
			
			if(x_now == x && y_now == y){
				//nothing
			}
			else{
				next_direction = new DirVector(x_now, y_now, path.get(Element.nextStep) / 80, path.get(Element.nextStep) % 80);
				
				if(checkWait(last_direction, next_direction)){
					synchronized(Begin.lightMap){
						try {
							System.out.println("Taxi " + number + " is waiting");
							LightMap.waitTaxi ++;
							Begin.lightMap.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						}
					}
				}
				systimemark1 = System.currentTimeMillis();
				
				last_direction = next_direction;
				
				x_now = path.get(Element.nextStep) / 80;
				y_now = path.get(Element.nextStep) % 80;
			}
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis());
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
				last_resttime = System.currentTimeMillis();
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
		* 根据目的地，实时找到流量最小的路径执行运动，直到到达载客地
		*/
	public void goto_departure(int x , int y){
		
		Snapshot snapshot;
		moveTimes = 0;
		
		do{
			systimemark1 = System.currentTimeMillis();
			path = Map.getPath(x_now * 80 + y_now , x * 80 + y);
			
			if(x_now == x && y_now == y){
				//nothing
			}
			else{
					next_direction = new DirVector(x_now, y_now, path.get(Element.nextStep) / 80, path.get(Element.nextStep) % 80);
					
					if(checkWait(last_direction, next_direction)){
						synchronized(Begin.lightMap){
							try {
								System.out.println("Taxi " + number + " is waiting");
								LightMap.waitTaxi ++;
								Begin.lightMap.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
							}
						}
					}
				systimemark1 = System.currentTimeMillis();
				last_direction = next_direction;
				
				x_now = path.get(Element.nextStep) / 80;
				y_now = path.get(Element.nextStep) % 80;
			}
			
			Point point = new Point(x_now , y_now);
			Begin.gui.SetTaxiStatus(number, point, status);
			snapshot = new Snapshot(number, x_now, y_now, status, reputation, System.currentTimeMillis());
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
		* this.request == request;
		*/
	public void setRequest(Request request){
		this.request = request;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(number < 0 || number > 100)
			return false;
		if(x_now < 0 || x_now > 80)
			return false;
		if(y_now < 0 || y_now > 80)
			return false;
		if(moveTimes < 0)
			return false;
		if(systime < 0)
			return false;
		if(systimemark1 < 0)
			return false;
		if(systimemark2 < 0)
			return false;
		if(last_resttime < 0)
			return false;
		if(status < 0 || status > 3) 
			return false;
		if(reputation < 0)
			return false;
		if(x_departure < 0 || x_departure > 80)
			return false;
		if(y_departure < 0 || y_departure > 80)
			return false;
		if(x_destination < 0 || x_destination > 80)
			return false;
		if(y_destination < 0 || y_destination > 80)
			return false;
		if(offset == null)
			return false;
		return true;
	}
}

package my_Taxi;

import java.util.ArrayList;
/**
 * @OVERVIEW:
 * 这个类是为了存储请求相关的信息创建的
 * 存放所有指导书要求输出的信息，同时为Taxi和调度器提供地点
 */
public class Request {
	long systime;
	int x_departure;
	int y_departure;
	int x_destination;
	int y_destination;
	
	String self;
	
	boolean isPicked = false;
	boolean isOut = false;
	boolean ghostRequest = false;
	
	ArrayList<Taxi> calling = new ArrayList<Taxi>();
	
	ArrayList<Snapshot> taxis = new ArrayList<Snapshot>();
	ArrayList<Snapshot> pickRoute = new ArrayList<Snapshot>();
	ArrayList<Snapshot> arriveRoute = new ArrayList<Snapshot>();
	Snapshot receiveTime;
	Snapshot pickTime;
	Snapshot arriveTime;
	
	int taxi_index;
	int taxi_number;
	int taxi_x_picked;
	int taxi_y_picked;
	int taxi_arrive_time;
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= x_departure < 80 && 0 <= y_departure < 80 && 0 <= x_destination < 80 && 0 <= y_destination < 80;
		* @MODIFIES : None;
		* @EFFECTS : 
		* this.x_departure = x_departure;
		* this.y_departure = y_departure;
		* this.x_destination = x_destination;
		* this.y_destination = y_destination;
		* this.self = self;
		*/

	public Request(Long systime, int x_departure, int y_departure, int x_destination, int y_destination , String self){
		this.systime = systime;
		this.x_departure = x_departure;
		this.y_departure = y_departure;
		this.x_destination = x_destination;
		this.y_destination = y_destination;
		this.self = self;
	}
	public Request(Long systime, int x_destination, int y_destination , boolean ghostRequest){
		this.systime = systime;
		this.x_destination = x_destination;
		this.y_destination = y_destination;
		this.self = String.format("[GhostRequest,%d,(%d,%d)]", systime, x_destination, y_destination);
		this.ghostRequest = ghostRequest;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.x_departure;
		*/
	public int see_x_departure(){
		return x_departure;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.y_departure;
		*/	
	public int see_y_departure(){
		return y_departure;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.x_destination;
		*/	
	public int see_x_destination(){
		return x_destination;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.y_destination;
		*/	
	public int see_y_destination(){
		return y_destination;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.systime;
		*/	
	public long see_systime(){
		return systime;
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= number < RequestList.length;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == calling.contains(number);
		*/
	public boolean checkSame(int number){
		int i;
		for(i = 0; i < calling.size(); i++){
			if(number == calling.get(i).number){
				return true;
			}
		}
		return false;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(systime < 0)
			return false;
		if(x_departure < 0 || x_departure > 80)
			return false;
		if(y_departure < 0 || y_departure > 80)
			return false;
		if(x_destination < 0 || x_departure > 80)
			return false;
		if(y_destination < 0 || y_destination > 80)
			return false;
		if(self == null)
			return false;
		if(calling == null)
			return false;
		if(taxis == null)
			return false;
		if(pickRoute == null)
			return false;
		if(arriveRoute == null)
			return false;
		if(pickTime == null)
			return false;
		if(arriveTime == null)
			return false;
		if(taxi_number < 0 || taxi_number > 100)
			return false;
		if(taxi_x_picked < 0 || taxi_x_picked > 80)
			return false;
		if(taxi_y_picked < 0 || taxi_y_picked > 80)
			return false;
		if(taxi_arrive_time < 0)
			return false;
		return true;
	}
}

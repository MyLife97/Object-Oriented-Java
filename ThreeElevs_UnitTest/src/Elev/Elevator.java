package Elev;


/**
 * @OVERVIEW:
 * 这个类是电梯类，继承电梯运动接口，储存有当前楼层，当前运行时间和运动方向。
 * 可以进行产生电梯请求，移动等操作。
 */
public class Elevator implements Elevator_method{
	private int now_floor = 1;
	private double now_time = 0;
	private int direction = Element.Elevator_still;          //0 STILL    1   UP    2 DOWN
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= sym <= 1 && 1 <= des <= 10 && 0 <= dir <= 2 && tim >= 0;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == new Request(sym, des, dir, tim);
		*/
	public Request make_Request(int sym , int des , int dir , long tim){
		Request new_Request = new Request(sym, des, dir, tim);
		return new_Request;
	}
	
	/**
	* 注释.......
		* @REQUIRES: 1 <= des <= 10;
		* @MODIFIES : this.now_floor;
		* @EFFECTS : 
		* this.now_floor = des;
		*/	
	public void move_to(int des){
		now_floor = des;
	}
	
	/**
	* 注释.......
		* @REQUIRES: time >= 0;
		* @MODIFIES : this.now_time;
		* @EFFECTS : 
		* this.now_time = time;
		*/	
	public void set_time(double time){
		now_time = time;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.now_time;
		*/	
	public double get_time(){
		return now_time;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.now_floor;
		*/		
	public int get_floor(){
		return now_floor;
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= dir <= 2;
		* @MODIFIES : this.direction;
		* @EFFECTS : 
		* this.direction = dir;
		*/	
	public void set_dirction(int dir){
		this.direction = dir;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.direction;
		*/	
	public int get_direction(){
		return this.direction;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (direction == 0) ==> (\result == "UP");
		* (direction == 1) ==> (\result == "DOWN");
		* (direction != 0 && direction != 1) ==> (\result == "STILL");
		*/	
	public String print_direction(){
		if(direction == 0)
			return "UP";
		else if(direction == 1)
			return "DOWN";
		else {
			return "STILL";
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == String.format("(%d,%s,%.1f)", now_floor , print_direction() , now_time);
		*/	
	public String toString(){
		return String.format("(%d,%s,%.1f)", now_floor , print_direction() , now_time);
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS :
		* (now_floor >= 1 && now_floor <= 10 && now_time >= 0 && (direction == Element.Elevator_down || direction == Element.Elevator_still || direction == Element.Elevator_up)) ==> \result == true;
		* !(now_floor >= 1 && now_floor <= 10 && now_time >= 0 && (direction == Element.Elevator_down || direction == Element.Elevator_still || direction == Element.Elevator_up)) ==> \result == false;
		*/
	public boolean repOK(){
		boolean result = false;
		if(now_floor >= 1 && now_floor <= 10 && now_time >= 0 && (direction >= 0 && direction <= 2))
			result = true;
		return result;
	}
}

package Elev;

/**
 * @OVERVIEW:
 * 这个类是请求类，负责存储电梯or楼层请求，请求位置，请求方向，请求时间。
 * 只能通过构造方法初始化上述数据，其余时刻只能获取不能修改。
 * 
 */
public class Request {
	private int symbol;
	private int destination;
	private int direction;
	private long time;
	

	/**
	* 注释.......
		* @REQUIRES: 0 <= sym <= 1 && 1 <= des <= 10 && 0 <= dir <= 1 && 2^32 - 1 >= tim >= 0;
		* @MODIFIES : this.symbol, this.destination, this.direction, this.time;
		* @EFFECTS : 
		* \result == new Request(sym, des, dir, tim);
		*/
	public Request(int sym , int des , int dir , long tim){
		symbol = sym;
		destination = des;
		direction = dir;
		time = tim;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.symbol;
		*/
	public int see_symbol(){
		return symbol;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.destination;
		*/
	public int see_destination(){
		return destination;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.direction;
		*/
	public int see_direction(){
		return direction;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == this.time;
		*/
	public long see_time(){
		return time;
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (0 <= symbol <= 1 && 1 <= destination <= 10 && 0 <= direction <= 1 && 2^32 - 1 >= time >= 0) ==> \result == true;
		* !(0 <= symbol <= 1 && 1 <= destination <= 10 && 0 <= direction <= 1 && 2^32 - 1 >= time >= 0) ==> \result == false;
		*/
	public boolean repOK(){
		if(0 <= symbol && symbol <= 1 && 1 <= destination && destination <= 10 && 0 <= direction && direction <= 1 && Element.MAX_INT >= time && time >= 0)
			return true;
		else
			return false;
	}
}

package Elev;

public class Request {
	private int symbol;
	private int destination;
	private int direction;
	private long time;
	

	public Request(int sym , int des , int dir , long tim){
		symbol = sym;
		destination = des;
		direction = dir;
		time = tim;
	}
	
	public int see_symbol(){
		return symbol;
	}
	
	public int see_destination(){
		return destination;
	}
	
	public int see_direction(){
		return direction;
	}
	
	public long see_time(){
		return time;
	}
}

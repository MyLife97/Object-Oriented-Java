package Elev;


public class Request{
	private int symbol;   //0 Ele   1 Floor 
	private int number;   //#1 #2 #3
	private int destination;
	private int direction;
	private long time;
	private String self;
	

	public Request(int sym ,int num, int des , int dir , long tim, String self){
		symbol = sym;
		number = num;
		destination = des;
		direction = dir;
		time = tim;
		this.self = self;
	}
	
	public int see_symbol(){
		return symbol;
	}
	
	public int see_number(){
		return number;
	}
	
	public int see_destination(){
		return destination;
	}
	
	public int see_dirction(){
		return direction;
	}
	
	public long see_time(){
		return time;
	}
	
	public String see_self(){
		return self;
	}
}

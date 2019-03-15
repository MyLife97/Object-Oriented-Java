package Elev;

public class Elevator {
	private int now_floor = 1;	
	
	public Request make_Request(int sym , int des , int dir , long tim){
		Request new_Request = new Request(sym, des, dir, tim);
		return new_Request;
	}
	
	public void move_to(int des){
		now_floor = des;
	}
	
	public int get_floor(){
		return now_floor;
	}
}

package Elev;

public interface Elevator_method {
	public Request make_Request(int sym , int des , int dir , long tim);
	
	public void move_to(int des);
	
	public void set_time(double time);
	
	public double get_time();
	
	public int get_floor();
	
	public void set_dirction(int dir);
	
	public int get_direction();
	
	public String print_direction();
	
	public String toString();
}

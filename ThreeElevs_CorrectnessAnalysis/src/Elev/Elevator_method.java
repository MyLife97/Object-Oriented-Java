package Elev;

/**
 * @OVERVIEW:
 * 这个类是电梯接口，希望电梯可以实现产生电梯请求、移动、改变自身状态，
 * 获取自身状态、输出打印信息等操作。
 */
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

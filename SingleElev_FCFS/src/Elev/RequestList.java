package Elev;
import java.util.*;


public class RequestList {
	private ArrayList<Request> list = new ArrayList<Request>();
	
	public void add_Request(Request new_Request){
		list.add(new_Request);
	}
	
	public Request get_Request(int number){
		return list.get(number);
	}
	
	public void remove_Request(int number){
		list.remove(number);
	}
	
	public boolean is_Empty(){
		return list.isEmpty();
	}
	
	public int get_Number(){
		return list.size();
	}
}

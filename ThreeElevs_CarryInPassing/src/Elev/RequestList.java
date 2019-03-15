package Elev;
import java.util.*;


public class RequestList {
	private ArrayList<Request> list = new ArrayList<Request>();
	
	public void add_Request(Request new_Request){
		synchronized(list){
			list.add(new_Request);
		}
	}
	
	public Request get_Request(int number){
		synchronized(list){
			return list.get(number);
		}
	}
	
	public void remove_Request(int number){
		synchronized(list){
			list.remove(number);
		}
	}
	
	public boolean is_Empty(){
		synchronized(list){
			return list.isEmpty();
		}
	}
	
	public int get_Number(){
		synchronized(list){
			return list.size();
		}
	}
}
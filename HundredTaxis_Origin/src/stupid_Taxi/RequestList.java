package stupid_Taxi;

import java.util.ArrayList;


public class RequestList {
	ArrayList<Request> list = new ArrayList<Request>();
	
	
	
	public void addRequest(Request request){
		synchronized (list) {
			list.add(request);	
		}
	}
	
	public void deleteRequest(int number){
		synchronized(list){
			list.remove(number);
		}
	}
	
	public Request getRequest(int number){
		synchronized (list) {
			return list.get(number);	
		}
	}
	
	public int getLength(){
		synchronized (list) {
			return list.size();
		}
	}
}

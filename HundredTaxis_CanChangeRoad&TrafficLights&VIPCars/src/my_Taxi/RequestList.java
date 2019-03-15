package my_Taxi;

import java.util.ArrayList;

/**
 * @OVERVIEW:
 * 这个类是为了方便管理所有请求建立的
 * 以ArrayList的方式统一管理请求，便于检索和处理
 */
public class RequestList {
	ArrayList<Request> list = new ArrayList<Request>();
	/**
	* 注释.......
		* @REQUIRES: request.Object == Request;
		* @MODIFIES : this.list;
		* @EFFECTS : 
		* list.size == \old(list).size + 1 && list.contains(request);
		*/
	public void addRequest(Request request){
		synchronized (list) {
			list.add(request);	
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= number < list.length;
		* @MODIFIES : this.list;
		* @EFFECTS : 
		* list.size == \old(list).size - 1;
		*/
	public void deleteRequest(int number){
		synchronized(list){
			list.remove(number);
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= number < list.length;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == list.get(number);
		*/
	public Request getRequest(int number){
		synchronized (list) {
			return list.get(number);	
		}
	}
	/**
	* 注释.......
		* @REQUIRES: list != null;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == list.size();
		*/
	public int getLength(){
		synchronized (list) {
			return list.size();
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(list == null)
			return false;
		return true;
	}
}

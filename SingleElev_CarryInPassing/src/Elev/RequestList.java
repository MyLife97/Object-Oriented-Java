package Elev;
import java.util.*;


public class RequestList {
	private ArrayList<Request> list = new ArrayList<Request>();
	
	/**
	* 注释.......
		* @REQUIRES: new_Request != null;
		* @MODIFIES : None;
		* @EFFECTS : this.list;
		* this.list = \old(list) && list.contains(new_Request);
		*/
	public void add_Request(Request new_Request){
		list.add(new_Request);
	}
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= number <= list.length;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == list.get(number);
		*/
	public Request get_Request(int number){
		return list.get(number);
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= number <= list.length;
		* @MODIFIES : None;
		* @EFFECTS : 
		* this.list = \old(list) && !list.contains(new_Request);
		*/
	public void remove_Request(int number){
		list.remove(number);
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (list.length == 0) ==> \result == true;
		* !(list.length == 0) ==> \result == false;
		*/	
	public boolean is_Empty(){
		return list.isEmpty();
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == list.length;
		*/
	public int get_Number(){
		return list.size();
	}
}

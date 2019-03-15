package Elev;

public class Floor {
	
	public Request make_Request(int sym , int des , int dir , long tim){
		
		Request new_Request = new Request(sym , des , dir , tim);
		return new_Request;
		
	}
}

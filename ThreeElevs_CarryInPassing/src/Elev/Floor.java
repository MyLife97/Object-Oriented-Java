package Elev;


public class Floor {
	private boolean floor_up[] = new boolean[Element.Floor_number];
	private boolean floor_down[] = new boolean[Element.Floor_number];
	
	public boolean see_button(int floor , int dir){
		if(dir == Element.Floor_down){
			return floor_down[floor];
		}
		else{
			return floor_up[floor];
		}
	}
	
	public void set_button(int floor, int dir, boolean state){
		if(dir == Element.Floor_down){
			floor_down[floor] = state;
		}
		else{
			floor_up[floor] = state;
		}
	}
	
	
	public static Request make_Request(int des , int dir , long tim, String self){
		
		Request new_Request = new Request(Element.Floor , 0 , des , dir , tim, self);
		return new_Request;
		
	}	
}

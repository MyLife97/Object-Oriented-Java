package my_Taxi;

/**
 * @OVERVIEW:
 * 这是一个类似于C宏定义的类，里面存放所有需要用到的，又有一定意义的常量
 */
public class Element {
	public final static int Serving = 1;
	public final static int Picking = 3;
	public final static int Waiting = 2;
	public final static int Stop = 0;
	
	public final static int Zero = 0;
	public final static int MapSize = 80;
	public final static int TaxiNumber = 100;
	
	public final static int SameRequest = 100;
	
	public final static int Up = 1;
	public final static int Down = 2;
	public final static int Left = 3;
	public final static int Right = 4;
	
	public final static int nextStep = 1;
	public final static long WindowClose = 7500;
	
	public final static int Open = 1;
	public final static int Close = 0;
	
	public final static int MapBegin = 1;
	public final static int MapEnd = 2;
	public final static int FlowBegin = 3;
	public final static int FlowEnd = 4;
	public final static int TaxiBegin = 5;
	public final static int TaxiEnd = 6;
	public final static int RequestBegin = 7;
	public final static int RequestEnd = 8;
	
	public final static int LightBegin = 9;
	public final static int LightEnd = 10;
	
	public final static int MaxInt = Integer.MAX_VALUE;
	
	public final static int VIP_taxi = 1;
	public final static int Common_taxi = 0;
	
	public final static int OneCar = 1;
	
	public final static int Next = 1;
	public final static int Previous = 2;
	public final static int Now = 3;
	public final static int All = 4;
	public final static int HasNext = 5;
	public final static int HasPrevious = 6;
	
	/**
	* 注释.......不知道这个类写repOK有啥用
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == true;
		*/
	public boolean repOK(){
		if(Serving != 1) return false;
		if(Picking != 3) return false;
		if(Waiting != 2) return false;
		if(Stop != 0) return false;
		if(Zero != 0) return false;
		if(MapSize != 80) return false;
		if(TaxiNumber != 100) return false;
		if(Up != 1) return false;
		if(Down != 2) return false;
		if(Left != 3) return false;
		if(Right != 4) return false;
		if(nextStep != 1) return false;
		if( WindowClose != 7500) return false;
		if(Open != 1) return false;
		if(Close != 0) return false;
		if(MapBegin != 1) return false;
		if(MapEnd != 2) return false;
		if(FlowBegin != 3) return false;
		if(FlowEnd != 4) return false;
		if(TaxiBegin != 5) return false;
		if(TaxiEnd != 6) return false;
		if(RequestBegin != 7) return false;
		if(RequestEnd != 8) return false;
		if(LightBegin != 9) return false;
		if(LightEnd != 10) return false;
		if(MaxInt != Integer.MAX_VALUE) return false;
		return true;
	}
}

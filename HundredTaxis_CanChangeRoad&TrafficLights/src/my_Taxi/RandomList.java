package my_Taxi;

import java.util.ArrayList;

/**
 * @OVERVIEW:
 * 这个类是为了存储可能的随机方向，以简化计算，共16种可能
 */
public class RandomList {
	static ArrayList<Integer>[] randomList= new ArrayList[16];
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.randomList;
		* @EFFECTS :
		* init randomList[i];
		*/
	public RandomList(){
		int i;
		for(i = 0; i < 16; i++){
			initRandomList(i);
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.randomList;
		* @EFFECTS :
		* init randomList[i];
		*/
	public void initRandomList(int i){
		randomList[i] = makeList(i);
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.randomList[i];
		* @EFFECTS :
		* (i & 0b0001) == 1) ==> List.add(Element.Up);
		* (i & 0b0010) == 2) ==> List.add(Element.Down);
		* (i & 0b0100) == 4) ==> List.add(Element.Left);
		* (i & 0b1000) == 8) ==> List.add(Element.Right);
		* \result == List;
		*/
	public ArrayList<Integer> makeList(int i){
		ArrayList<Integer> List = new ArrayList<Integer>();

		if((i & 0b0001) == 1)
			List.add(Element.Up);
		if((i & 0b0010) == 2)
			List.add(Element.Down);
		if((i & 0b0100) == 4)
			List.add(Element.Left);
		if((i & 0b1000) == 8)
			List.add(Element.Right);
		
		return List;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(randomList == null)
			return false;
		return true;
	}
	

}

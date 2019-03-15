package Elev;

/**
 * @OVERVIEW:
 * 这个类是楼层类，不负责数据存储，只负责产生楼层请求。
 */
public class Floor {
	/**
	* 注释.......
		* @REQUIRES: 0 <= sym <= 1 && 1 <= des <= 10 && 0 <= dir <= 1 && 2^32 - 1 >= tim >= 0;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == new Request(sym, des, dir, tim);
		*/
	public Request make_Request(int sym , int des , int dir , long tim){
		Request new_Request = new Request(sym , des , dir , tim);
		return new_Request;
	}
}

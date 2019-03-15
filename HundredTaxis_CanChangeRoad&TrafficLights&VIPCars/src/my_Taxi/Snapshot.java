package my_Taxi;
/**
 * @OVERVIEW:
 * 这个类是为了存储指导书所要求的每个阶段的信息所创建的
 * 出租车每运动一次，都会产生一个Snapshot来存储当前状态信息，同时与Request相关联，以满足输出要求
 */
public class Snapshot {
	int number;
	int x;
	int y;
	int status;
	int reputation;
	long systime;
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* this.number = number;
		* this.x = x;
		* this.y = y;
		* this.status = status;
		* this.reputation = reputation;
		* this.systime = systime;
		*/
	public Snapshot(int number, int x, int y, int status, int reputation, long systime) {
		this.number = number;
		this.x = x;
		this.y = y;
		this.status = status;
		this.reputation = reputation;
		this.systime = systime;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(number < 0 || number > 100)
			return false;
		if(x < 0 || x > 80)
			return false;
		if(y < 0 || y > 80)
			return false;
		if(status < 0 || status > 3)
			return false;
		if(reputation < 0)
			return false;
		if(systime < 0)
			return false;
		return true;
	}
	
}

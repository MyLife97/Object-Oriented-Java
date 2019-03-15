package my_Taxi;

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
	
}

package stupid_Taxi;

public class Snapshot {
	int number;
	int x;
	int y;
	int status;
	int reputation;
	long systime;
	
	public Snapshot(int number, int x, int y, int status, int reputation, long systime) {
		this.number = number;
		this.x = x;
		this.y = y;
		this.status = status;
		this.reputation = reputation;
		this.systime = systime;
	}
	
}

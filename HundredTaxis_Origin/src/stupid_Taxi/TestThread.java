package stupid_Taxi;

public class TestThread extends Thread{
	
//	public final static int Serving = 1;
//	public final static int Picking = 3;
//	public final static int Waiting = 2;
//	public final static int Stop = 0;
	public static void searchStatus(int status){
		int i;
		for(i = 0; i < Element.TaxiNumber; i++){
			if(Begin.taxis[i].status == status){
				System.out.printf("Number:%d\n" , Begin.taxis[i].number);
			}
		}
	}
	
	public static void searchNumber(int number){
		String status;
		if(Begin.taxis[number].status == Element.Waiting)
			status = "Waiting";
		else if(Begin.taxis[number].status == Element.Serving){
			status = "Serving";
		}
		else if(Begin.taxis[number].status == Element.Picking){
			status = "Picking";
		}
		else{
			status = "Stop";
		}
		
		System.out.printf("SearchTime:%d Position:(%d,%d) Status:%s\n", 
				System.currentTimeMillis() / 100 *100 , Begin.taxis[number].x_now,
				Begin.taxis[number].y_now , status);
	}
}

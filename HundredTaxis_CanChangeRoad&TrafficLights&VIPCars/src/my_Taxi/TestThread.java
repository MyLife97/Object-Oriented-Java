package my_Taxi;
/**
 * @OVERVIEW:
 * 这个类是之前的测试线程类
 * 不包含成员变量
 * 实现的两个方法为指导书要求，一是根据状态查询，二是根据车辆编号查询
 */
public class TestThread extends Thread{
	
//	public final static int Serving = 1;
//	public final static int Picking = 3;
//	public final static int Waiting = 2;
//	public final static int Stop = 0;
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= status <= 3;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (\all Begin.taxis[i].status == status) ==> print(taxis[i].info) == true;
		*/
	public static void searchStatus(int status){
		int i;
		for(i = 0; i < Element.TaxiNumber; i++){
			if(Begin.taxis[i].status == status){
				System.out.printf("Number:%d\n" , Begin.taxis[i].number);
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= number < 100;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (\exist Begin.taxis[i].number == number) ==> print(taxis[number].info) == true;
		*/
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
				System.currentTimeMillis(), Begin.taxis[number].x_now,
				Begin.taxis[number].y_now , status);
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	//没有成员变量
	public boolean repOK(){
		return true;
	}
}

package Elev;

/**
 * @OVERVIEW:
 * 这个类是前一次作业调度器类，存储有当前系统时间，上一次指令执行时间和各个按钮亮灯情况等信息。
 * 可以通过构造方法就上述信息进行初始化操作。
 */
public class Controller {
	
	protected double now_time = 0;
	protected double last_time = 0;
	
	public double ele[] = new double[Element.Floor_number];
	protected double floor_up[] = new double[Element.Floor_number];
	protected double floor_down[] = new double[Element.Floor_number];
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : (\all int i;0 <= i <= 10) ==> ele[i] == -1;
		* (\all int i;0 <= i <= 10) ==> floor_up[i] == -1;
		* (\all int i;0 <= i <= 10) ==> floor_down[i] == -1;
		*/
	public Controller(){
		int i;
		for(i = 0; i < Element.Floor_number; i++){
			ele[i] = -1;
			floor_up[i] = -1;
			floor_down[i] = -1;
		}
	}
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS :
		* (now_time >= 0 && last_time >= 0 && ele != null && floor_up != null && floor_down != null) ==> \result == true;
		* !(now_time >= 0 && last_time >= 0 && ele != null && floor_up != null && floor_down != null) ==> \result == false;
		*/
	public boolean repOK(){
		boolean result = false;
		if(now_time >= 0 && last_time >= 0 && ele != null && floor_up != null && floor_down != null)
			result = true;
		return result;
	}
}

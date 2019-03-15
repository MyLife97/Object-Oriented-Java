package Elev;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @OVERVIEW:
 * 这个类是程序启动类，负责管理请求队列、电梯、楼层和调度器，同时具有读取数据
 * 并将合理请求添加进请求队列的功能。
 */
public class Begin {
	static private RequestList requestList = new RequestList();
	static private Elevator elevator = new Elevator();
	static private Floor floor = new Floor();
	static private Scheduler scheduler = new Scheduler();
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES: this.requestList;
		* @EFFECTS : Run the program == true;
		*/
	public static void main(String args[]){
		try{
			Begin.read(requestList);
			while(requestList.is_Empty() != true){
				scheduler.next_step(requestList, elevator);
			}
		}catch (Exception e) {
			System.out.println("ERROR");
		}
	}
	
	/**
	* 注释.......
	* @REQUIRES: requestList != null;
	* @MODIFIES: requestList;
	* @EFFECTS : 
	* (如果是第一个 == true && System.in == "FR,1,UP,0") ==>  (floor.make_Request() == true && requestList.add_Request() == true) 
	* (如果是第一个 == false && System.in.matches("FR,%d1,UP,%d2") == true && 1 <= %d1 < 10 && 2^32 - 1 >= %d2 >= 0 && 新请求满足指令数限制要求 && 满足时间顺序递增) ==> (floor.make_Request() == true && requestList.add_Request() == true)
	* (如果是第一个 == false && System.in.matches("FR,%d1,DOWN,%d2") == true && 1 < %d1 <= 10 && 2^32 - 1 >= %d2 >= 0 && 新请求满足指令数限制要求 && 满足时间顺序递增) ==> (floor.make_Request() == true && requestList.add_Request() == true)
	* (如果是第一个 == false && System.in.matches("ER,%d1,UP,%d2") == true && 1 <= %d1 < 10 && 2^32 - 1 >= %d2 >= 0 && 新请求满足指令数限制要求 && 满足时间顺序递增) ==> (floor.make_Request() == true && requestList.add_Request() == true)
	* (如果是第一个 == false && System.in.matches("ER,%d1,DOWN,%d2") == true && 1 < %d1 <= 10 && 2^32 - 1 >= %d2 >= 0 && 新请求满足指令数限制要求 && 满足时间顺序递增) ==> (floor.make_Request() == true && requestList.add_Request() == true)
	* (!System.in.matches("ER/FR,%d,UP/DOWN,%d") == true && !System.in.matches("RUN") || (如果是第一个 == true && !System.in == "FR,1,UP,0")) ==> Invalid(System.in) == true;
	* (!System.in.matches("ER/FR,%d,UP/DOWN,%d") == true && System.in.matches("RUN")) ==> 停止输入 == true;
	*/
	public static void read(RequestList requestList){
		int i;
		int count;
		long Max_time = (long)Math.pow(2, 32) - 1;
		int des;
		int dir;
		long time;
		long last_time = 0;
		Request new_Request;
		
		boolean is_over = false;		
		Scanner input = new Scanner(System.in);
		String regex = "\\(((FR,[+]?\\d{1,9},(UP|DOWN),[+]?\\d+)|(ER,[+]?\\d{1,9},[+]?\\d+))\\)";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher;
		
		while(is_over != true){
			String origin_line = input.nextLine();
			String processed_line = origin_line.replaceAll(" ", "");
			matcher = pattern.matcher(processed_line);
				
			if(matcher.matches() && requestList.get_Number() <= Element.MAX_INPUT){    
				String final_line = processed_line.replaceAll("\\(|\\)", "");
				String [] group = final_line.split(",");
				
				if(group[0].charAt(0) == 'E'){
					count = group[1].length();
					for(i = 0 , des = 0; i < count; i++){
						if(group[1].charAt(i) != '+')
							des = 10 * des + group[1].charAt(i) - '0';
					}
					count = group[2].length();
					for(i = 0, time = 0; i < count; i++){
						if(group[2].charAt(i) != '+')
							time = 10 * time + group[2].charAt(i) - '0';
						if(time > Max_time){
							System.out.printf("INVALID[%s]\n" , processed_line);
							break;
						}
					}
					if(time > Max_time)
						continue;

					if(des > 10 || des == 0){
						System.out.printf("INVALID[%s]\n" , processed_line);
						continue;
					}
					if(requestList.get_Number() == 0){
						System.out.printf("INVALID[%s]\n" , processed_line);
						continue;
					}
					if(last_time > time){
						System.out.printf("INVALID[%s]\n" , processed_line);
						continue;
					}
					last_time = time;
					new_Request = elevator.make_Request(Element.ELE, des, Element.ELE, time);
					requestList.add_Request(new_Request);
				}
				else if(group[0].charAt(0) == 'F'){ 
					count = group[1].length();
					for(i = 0 , des = 0; i < count; i++){
						if(group[1].charAt(i) != '+')
							des = 10 * des + group[1].charAt(i) - '0';
					}
					if(group[2].charAt(0) == 'U')
						dir = Element.FLOOR_UP;
					else
						dir = Element.FLOOR_DOWN;
					
					count = group[3].length();
					for(i = 0, time = 0; i < count; i++){
						if(group[3].charAt(i) != '+')
							time = 10 * time + group[3].charAt(i) - '0';
						if(time > Max_time){ 
							System.out.printf("INVALID[%s]\n" , processed_line);
							break;
						}
					}
					if(time > Max_time)
						continue;
					if(des > 10 || des == 0){
						System.out.printf("INVALID[%s]\n" , processed_line);

						continue;
					}					
						

					if(requestList.get_Number() == 0 && (time != 0 || des != 1 || group[2].charAt(0) != 'U')){
						System.out.printf("INVALID[%s]\n" , processed_line);

						continue;
					}			
					
					if(group[2].charAt(0) == 'D' && des == 1 || group[2].charAt(0) == 'U' && des == 10){
						System.out.printf("INVALID[%s]\n" , processed_line);

						continue;
					}
					
					if(last_time > time){
						System.out.printf("INVALID[%s]\n" , processed_line);

						continue;
					}
					last_time = time;
					new_Request = floor.make_Request(Element.FLOOR, des, dir, time);
					requestList.add_Request(new_Request);
				}
			}
			else{
				if(processed_line.matches("RUN")){
					is_over = true;
					input.close();
				}
				else{
					if(requestList.get_Number() <= 100){
						System.out.printf("INVALID[%s]\n" , processed_line);
						
					}
					else{
						System.out.printf("INVALID[%s]\n" , processed_line);
					}
				}
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS :
		* (requestList != null && elevator != null && floor != null && scheduler != null) ==> \result == true;
		* !(requestList != null && elevator != null && floor != null && scheduler != null) ==> \result == false;
		*/
	public boolean repOK(){
		if(requestList != null && elevator != null && floor != null && scheduler != null)
			return true;
		else {
			return false;
		}
	}
}

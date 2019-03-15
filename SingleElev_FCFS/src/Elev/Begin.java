package Elev;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Begin {
	static private RequestList requestList = new RequestList();
	static private Elevator elevator = new Elevator();
	static private Floor floor = new Floor();
	static private Controller controller = new Controller();
	
	public static void main(String args[]){
		try{
			Begin.read(requestList);
			while(true){
				controller.next_step(requestList , elevator);
			}
		}catch (Exception e) {
			System.out.println("ERROR");
			ExHandler.exit();
		}
	}
	
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
				
			if(matcher.matches() && requestList.get_Number() <= Element.Max_input){    
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
							System.out.println("ERROR");
							System.out.println("#Time Out!");
							break;
						}
					}
					if(time > Max_time)
						continue;

					if(des > 10 || des == 0){
						System.out.println("ERROR");
						System.out.println("#Destination Error!");
						continue;
					}
					if(requestList.get_Number() == 0 && time != 0){
						System.out.println("ERROR");
						System.out.println("#Time Initilizing Error!");
						continue;
					}
					if(last_time > time){
						System.out.println("ERROR");
						System.out.println("#Wrong Time Sequence!");
						continue;
					}
					last_time = time;
					new_Request = elevator.make_Request(Element.Ele, des, Element.Ele, time);
					requestList.add_Request(new_Request);
				}
				else if(group[0].charAt(0) == 'F'){ 
					count = group[1].length();
					for(i = 0 , des = 0; i < count; i++){
						if(group[1].charAt(i) != '+')
							des = 10 * des + group[1].charAt(i) - '0';
					}
					if(group[2].charAt(0) == 'U')
						dir = Element.Floor_up;
					else
						dir = Element.Floor_down;
					
					count = group[3].length();
					for(i = 0, time = 0; i < count; i++){
						if(group[3].charAt(i) != '+')
							time = 10 * time + group[3].charAt(i) - '0';
						if(time > Max_time){ 
							System.out.println("ERROR");
							System.out.println("#Time Out!");
							break;
						}
					}
					if(time > Max_time)
						continue;
					if(des > 10 || des == 0){
						System.out.println("ERROR");
						System.out.println("#Destination Error!");
						continue;
					}					
						
					if(group[2].charAt(0) == 'D' && des == 1 || group[2].charAt(0) == 'U' && des == 10){
						System.out.println("ERROR");
						System.out.println("#Wrong Direction!");
						continue;
					}
					if(requestList.get_Number() == 0 && time != 0){
						System.out.println("ERROR");
						System.out.println("#Time Initilizing Error!");
						continue;
					}					
					if(last_time > time){
						System.out.println("ERROR");
						System.out.println("#Wrong Time Sequence!");
						continue;
					}
					last_time = time;
					new_Request = floor.make_Request(Element.Floor, des, dir, time);
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
						System.out.println("ERROR");
						System.out.println("#Not Match");
					}
					else{
						System.out.println("ERROR");
						System.out.println("#Input Overflow");
					}
				}
			}
		}
	}
}

package Elev;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Begin {
	
	
	static boolean haveto_over = false;
	static Writer writer ;
    static BufferedWriter bufWriter;
	public static long begin_time;
	private static boolean time_is_set = false;
	private static RequestList main_List;
	
	public static void main(String args[]){
		try {
			writer = new FileWriter("result.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufWriter = new BufferedWriter(writer);
		
		Elevator[] elevators = new Elevator[4];
		elevators[1] = new Elevator(1);
		elevators[2] = new Elevator(2);
		elevators[3] = new Elevator(3);
		
		Floor floor = new Floor();
		
		mult_Scheduler scheduler = new mult_Scheduler();
		Thread scheduler_thread = new Thread(scheduler);
		
		scheduler.set_elevators(elevators);
		scheduler.set_floor(floor);
		
		
		scheduler_thread.start();
		elevators[1].start();
		elevators[2].start();
		elevators[3].start();
		
		
		read(scheduler, scheduler_thread);
		
		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
			}
			if(elevators[1].see_ele_requestList().is_Empty() &&
				elevators[2].see_ele_requestList().is_Empty()&&
				elevators[3].see_ele_requestList().is_Empty()&&
				scheduler.see_requestList().is_Empty()){
				try {
					Thread.sleep(500);
					if(elevators[1].see_ele_requestList().is_Empty() &&
							elevators[2].see_ele_requestList().is_Empty()&&
							elevators[3].see_ele_requestList().is_Empty()&&
							scheduler.see_requestList().is_Empty()){
						System.out.println("All Empty");
						elevators[1].stop();
						elevators[2].stop();
						elevators[3].stop();
						scheduler_thread.stop();
						ExHandler.exit();
					}
					else{
						continue;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void read(mult_Scheduler scheduler, Thread scheduler_thread){
		boolean is_over = false;
		int i;
		int count_line = 0;
		long receive_time;
		
		
		Scanner input = new Scanner(System.in);
		String regex = "\\(((FR,[+]?\\d{1,9},(UP|DOWN))|(ER,#[123],[+]?\\d{1,9}))\\)";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher;
		
		while(is_over != true){
			String origin_line = input.nextLine();
			if(time_is_set == false){
				begin_time = System.currentTimeMillis();
				time_is_set = true;
			}
			
			receive_time = System.currentTimeMillis();
			count_line ++;
			String processed_line = origin_line.replaceAll(" ", "");
			String group[] = processed_line.split(";" , -1);

			if(processed_line.matches("END")){
				is_over = true;
				input.close();
				continue;
			}
			else if(count_line > Element.Max_line){
				System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000);
				try {
					Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n", System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000));
					Begin.bufWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}

			for(i = 0; i < group.length; i++){
				if(i < Element.Max_request){
					matcher = pattern.matcher(group[i]);
					judge(matcher, group[i], receive_time, scheduler, scheduler_thread);
				}
				else{
					System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), group[i],(double)(receive_time-begin_time)/1000);
					try {
						Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), group[i],(double)(receive_time-begin_time)/1000));
						Begin.bufWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void judge(Matcher matcher, String processed_line, long receive_time, mult_Scheduler scheduler, Thread scheduler_thread){
		int i;
		int des;
		int dir;
		int ele_number;
		int count;
		
		if(matcher.matches()){    
			String final_line = processed_line.replaceAll("\\(|\\)", "");
			String [] group = final_line.split(",");
			
			if(group[0].charAt(0) == 'E'){
				ele_number = group[1].charAt(1) - '0';  //#1 #2 #3
				
				count = group[2].length();
				for(i = 0 , des = 0; i < count; i++){
					if(group[2].charAt(i) != '+')
						des = 10 * des + group[2].charAt(i) - '0';
				}
				

				if(des > Element.Floor_number - 1 || des == 0){
					//******
					System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000);
					try {
						Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000));
						Begin.bufWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//******
					return;
				}
				/*
				try {
					//??????ÎªÉ¶»á±¨´íÄØ
					scheduler_thread.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}*/
				
				Request new_Request = Elevator.make_Request(ele_number, des, receive_time, final_line);
				scheduler.see_requestList().add_Request(new_Request);
				

				
				//scheduler_thread.notify();
				
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
				

				if(des > Element.Floor_number - 1 || des == 0){
					System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000);
					try {
						Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000));
						Begin.bufWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				else if(group[2].charAt(0) == 'D' && des == 1 || group[2].charAt(0) == 'U' && des == 20){
					System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000);
					try {
						Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000));
						Begin.bufWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					return;
				}
				synchronized (scheduler.see_requestList()) {
					Request new_Request = Floor.make_Request(des, dir, receive_time, final_line);
					scheduler.see_requestList().add_Request(new_Request);
				}

			}
		}
		else{
			System.out.printf("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000);
			try {
				Begin.bufWriter.write(String.format("%d:INVALID[%s,%.1f]\n" ,System.currentTimeMillis(), processed_line,(double)(receive_time-begin_time)/1000));
				Begin.bufWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}	
}

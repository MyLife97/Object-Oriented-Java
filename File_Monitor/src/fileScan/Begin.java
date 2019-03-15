package fileScan;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Begin {
	
	static Writer writer ;
    static BufferedWriter bufWriter;
    static boolean program_over = false;
	static ArrayList<Monitor> monitors = new ArrayList<Monitor>();
    

	public static void main(String args[]){
		boolean is_over = false;
		String origin_line;
		String group[];
		String file_name;
		String trigger_type;
		String task_type;
		
		int i;
		boolean same_monitor;
		

		Monitor new_monitor;
		
		TestThread testThread = new TestThread();

		
		Modified_trigger modified_trigger = new Modified_trigger();
		Pathchange_trigger pathchange_trigger = new Pathchange_trigger();
		Rename_trigger rename_trigger = new Rename_trigger();
		Sizechange_trigger sizechange_trigger = new Sizechange_trigger();
		
		modified_trigger.start();
		pathchange_trigger.start();
		rename_trigger.start();
		sizechange_trigger.start();
		
		Detail_task detail_task = new Detail_task();
		Summary_task summary_task = new Summary_task();
		Recover_task recover_task = new Recover_task();
		
		detail_task.start();
		summary_task.start();
		recover_task.start();
		
		Scanner input = new Scanner(System.in);
		String regex_1 = "IF [^ ]{1,20} (renamed|modified|pathchanged|sizechanged) THEN (summary|detail)";
		String regex_2 = "IF [^ ]{1,20} (renamed|pathchanged) THEN recover";
		
		Pattern pattern_1 = Pattern.compile(regex_1);
		Pattern pattern_2 = Pattern.compile(regex_2);
		
		Matcher matcher_1;
		Matcher matcher_2;
		
		while(is_over != true){
			origin_line = input.nextLine();
			matcher_1 = pattern_1.matcher(origin_line);
			matcher_2 = pattern_2.matcher(origin_line);
			
			same_monitor = false;
			
			if(origin_line.matches("END")){
				is_over = true;
				break;
			}
			
			if((matcher_1.matches() || matcher_2.matches())){
				if(monitors.size() > Element.max_number){
					System.out.println("Monitors Number Over 120!");
					continue;
				}
				
				System.out.println(origin_line);
				group = origin_line.split(" ");
				//0 IF
				//1 file_name
				//2 trigger_type
				//3 THEN
				//4 task_type
				
				file_name = group[1];
				trigger_type = group[2];
				task_type = group[4];
				
				for(i = 0; i < monitors.size(); i++){
					if(file_name.equals(monitors.get(i).get_file_name()) &&
						trigger_type.equals(monitors.get(i).get_trigger_type()) &&
						task_type.equals(monitors.get(i).get_task_type())){
						same_monitor = true;
					}
				}
				if(same_monitor){
					System.out.println("Ignore Same Monitor!");
					continue;
				}
				
				new_monitor = new Monitor(file_name, trigger_type, task_type);
				monitors.add(new_monitor);
				new_monitor.start();
				System.out.println("Monitors Number " + monitors.size());
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				System.out.println("Not Match!");
			}
			

		}
		/*********
		 * Here
		 * 这里运行线程
		 * 运行时把注释删掉即可
		
		testThread.start();
		
		*********/
		
		while(program_over == false){
			try {
				Thread.sleep(3000);
				if(all_empty() == true && TestThread.file_operation_over == true)
					program_over = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	static public boolean all_empty(){
		if(Detail_task.snapshot_list.is_Empty() &&
			Modified_trigger.snapshot_list.is_Empty() &&
			Pathchange_trigger.snapshot_list.is_Empty() &&
			Rename_trigger.snapshot_list.is_Empty() &&
			Recover_task.snapshot_list.is_Empty() &&
			Rename_trigger.snapshot_list.is_Empty() &&
			Summary_task.snapshot_list.is_Empty())
			return true;
		else
			return false;
	}
	

}


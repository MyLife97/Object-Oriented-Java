package fileScan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Recover_task extends Thread{
	static Snapshot_list snapshot_list = new Snapshot_list();
	
	int i;
	int j;
	Snapshot snapshot;
	
	public void run(){
		File file_from;
		File file_to;
		while(Begin.program_over == false){
			for(i = 0; i < snapshot_list.get_Length();i++){
				snapshot = snapshot_list.get_Snapshot(i);
				if(judge(snapshot) == true){
					file_to = new File(snapshot.see_now_Path());
					file_from = new File(snapshot.see_previous_Path());
					file_to.renameTo(file_from);
					
					//????对哪些recover
					for(j = 0; j < Begin.monitors.size(); j++){
						//System.out.println("123123" + Begin.monitors.get(j).get_file_name());
						//System.out.println("123123" + snapshot.see_now_Path());
						if(Begin.monitors.get(j).get_file_name().equals(snapshot.see_now_Path())){
							Begin.monitors.get(j).set_file_name(snapshot.see_previous_Path());
							Begin.monitors.get(j).take_snapshot();
						}
						else if(father_path(Begin.monitors.get(j), snapshot)){
							////*********/////
							Begin.monitors.get(j).take_snapshot();
						}
					}
					System.out.println(print(snapshot));
				}
				snapshot_list.remove_Snapshot(i);
				i--;
				
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(29);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Recover task over.");
	}
	
	public String print(Snapshot snapshot){
		String output = "";
		
		output += snapshot.see_trigger_type() + " : " + "\n";
		output += "From " + snapshot.see_now_Size() + " back to " + snapshot.see_previous_Size() + "\n";
		output += "From " + snapshot.see_now_Name() + " back to " + snapshot.see_previous_Name() + "\n";
		output += "From " + snapshot.see_now_Parentpath() + " back to " + snapshot.see_previous_Parentpath() + "\n";
		output += "From " + snapshot.see_now_lastModified() + " back to " + snapshot.see_previous_lastModified() + "\n";
		
		return output;
	}
	
	public boolean father_path(Monitor monitor, Snapshot snapshot){
		if(snapshot.see_now_Path().contains(monitor.get_file_name()))
			return true;
		else
			return false;
	}
	
	public boolean judge(Snapshot snapshot){
		if(snapshot.see_task_type().equals(Element.recover) && snapshot.see_trigger_on() == Element.trigger_on){
			return true;
		}
		else {
			return false;
		}
	}
}

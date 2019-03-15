package fileScan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Summary_task extends Thread{
	static Snapshot_list snapshot_list = new Snapshot_list();
	Writer writer ;
    BufferedWriter bufWriter;
	
	int i;
	Snapshot snapshot;
	
	public void run(){
		try {
			writer = new FileWriter("summary.txt");
			bufWriter = new BufferedWriter(writer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(Begin.program_over == false){
			for(i = 0; i < snapshot_list.get_Length();i++){
				snapshot = snapshot_list.get_Snapshot(i);
				if(judge(snapshot) == true){
					//*****写入文件！！！
					try {
						count_plus(snapshot);
						System.out.println(print(snapshot));
						bufWriter.write(print(snapshot));
						bufWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				snapshot_list.remove_Snapshot(i);
				i--;
				
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(23);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Summary task over.");
	}
	
	public String print(Snapshot snapshot){
		if(snapshot.see_trigger_type().equals(Element.rename)){
			return snapshot.see_trigger_type() + " : " + Rename_trigger.count + "\n";
		}
		else if(snapshot.see_trigger_type().equals(Element.modified)){
			return snapshot.see_trigger_type() + " : " + Modified_trigger.count + "\n";
		}
		else if(snapshot.see_trigger_type().equals(Element.pathchange)){
			return snapshot.see_trigger_type() + " : " + Pathchange_trigger.count + "\n";
		}
		else{
			return snapshot.see_trigger_type() + " : " + Sizechange_trigger.count + "\n";
		}
	}
	
	public void count_plus(Snapshot snapshot){
		if(snapshot.see_trigger_type().equals(Element.rename)){
			Rename_trigger.count++;
		}
		else if(snapshot.see_trigger_type().equals(Element.modified)){
			Modified_trigger.count++;
		}
		else if(snapshot.see_trigger_type().equals(Element.pathchange)){
			Pathchange_trigger.count++;
		}
		else{
			Sizechange_trigger.count++;
		}
	}
	
	public boolean judge(Snapshot snapshot){
		if(snapshot.see_task_type().equals(Element.summary) && snapshot.see_trigger_on() == Element.trigger_on){
			return true;
		}
		else {
			return false;
		}
	}
}

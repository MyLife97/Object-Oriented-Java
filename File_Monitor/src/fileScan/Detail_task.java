package fileScan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Detail_task extends Thread{
	static Snapshot_list snapshot_list = new Snapshot_list();
	Writer writer ;
    BufferedWriter bufWriter;
	
	int i;
	Snapshot snapshot;
	
	public void run(){
		try {
			writer = new FileWriter("detail.txt");
			bufWriter = new BufferedWriter(writer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(Begin.program_over == false){
			for(i = 0; i < snapshot_list.get_Length();i++){
				snapshot = snapshot_list.get_Snapshot(i);
				if(judge(snapshot) == true){
					System.out.println(print(snapshot));
					
					//*****写入文件！！！
					try {
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
					Thread.sleep(4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(19);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Detail task over.");
	}
	
	public String print(Snapshot snapshot){
		String output = "";

		output += snapshot.see_trigger_type() + " Detail : " + "\n";
		output += "  >>  Size change from " + snapshot.see_previous_Size() + " to " + snapshot.see_now_Size() + "\n";
		output += "  >>  Name change from " + snapshot.see_previous_Name() + " to " + snapshot.see_now_Name() + "\n";
		output += "  >>  ParentPath change from " + snapshot.see_previous_Parentpath() + " to " + snapshot.see_now_Parentpath() + "\n";
		output += "  >>  LastModified change from " + snapshot.see_previous_lastModified() + " to " + snapshot.see_now_lastModified() + "\n";
		return output;
	}
	
	public boolean judge(Snapshot snapshot ){
		if(snapshot.see_task_type().equals(Element.detail) && snapshot.see_trigger_on() == Element.trigger_on){
			return true;
		}
		else {
			return false;
		}
	}

}

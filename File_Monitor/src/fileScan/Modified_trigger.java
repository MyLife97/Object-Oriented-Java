package fileScan;


public class Modified_trigger extends Thread{
	static Snapshot_list snapshot_list = new Snapshot_list();
	int i;
	Snapshot snapshot;
	static int count = 0;
	
	public void run(){
		while(Begin.program_over == false){
			for(i = 0; i < snapshot_list.get_Length();i++){
				System.out.println(snapshot_list.get_Length());
				snapshot = snapshot_list.get_Snapshot(i);
				if(judge(snapshot) == true){
					send(snapshot);
					System.out.println("send task" + snapshot.see_task_type());
					//count++;
				}
				snapshot_list.remove_Snapshot(i);
				i--;
				
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(113);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Modified trigger over.");
	}
	
	public void send(Snapshot snapshot){
		if(snapshot.see_task_type().equals(Element.detail)){
			Detail_task.snapshot_list.add_Snapshot(snapshot);
		}
		else if(snapshot.see_task_type().equals(Element.summary)){
			Summary_task.snapshot_list.add_Snapshot(snapshot);
		}
		else{
			Recover_task.snapshot_list.add_Snapshot(snapshot);
		}
	}
	
	public boolean judge(Snapshot snapshot){
		if(snapshot.see_trigger_type().equals(Element.modified) &&
			snapshot.see_previous_lastModified() != snapshot.see_now_lastModified() &&
			snapshot.see_previous_Path().equals(snapshot.see_now_Path()) &&
			snapshot.see_previous_Size() == snapshot.see_now_Size()){
			
			snapshot.set_trigger_on(Element.trigger_on);
			return Element.trigger_on;
		}
		else {
			return Element.trigger_off;
		}
	}
}

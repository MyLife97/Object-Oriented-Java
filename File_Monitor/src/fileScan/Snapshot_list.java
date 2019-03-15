package fileScan;

import java.util.ArrayList;


public class Snapshot_list {
	
	private ArrayList<Snapshot> list = new ArrayList<Snapshot>();
	
	public void add_Snapshot(Snapshot new_Snapshot){
		synchronized(list){
			list.add(new_Snapshot);
		}
	}
	
	public Snapshot get_Snapshot(int number){
		synchronized(list){
			return list.get(number);
		}
	}
	
	public void remove_Snapshot(int number){
		synchronized(list){
			list.remove(number);
		}
	}
	
	public boolean is_Empty(){
		synchronized(list){
			return list.isEmpty();
		}
	}
	
	public int get_Length(){
		synchronized(list){
			return list.size();
		}
	}	
}

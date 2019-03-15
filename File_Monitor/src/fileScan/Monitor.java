package fileScan;

import java.io.File;
import java.nio.file.*;

public class Monitor extends Thread{
	private String file_name;
	private String file_type;
	private String trigger_type;
	private String task_type;
	
	int count = 0;
	
	private boolean target_lost = false;
	private boolean already_exist = false;
	
	Snapshot_list previous_snapshot_list;
	Snapshot_list now_snapshot_list = new Snapshot_list();
	Snapshot_list gone_list;
	Snapshot_list come_list;
	
	public Monitor(String file_name, String trigger_type, String task_type){
		this.file_name = file_name;
		this.trigger_type = trigger_type;
		this.task_type = task_type;
	}
	
	public static void main(String args[]){
//		int i;
//		File file = new File("H:\\3");
//		File[] files = file.listFiles();
//		for(i = 0; i < files.length; i++)
//			System.out.println(files[i].getAbsolutePath());
	}
	//一个file文件是没有filelist的，
	//只有directory文件有
	public void scan(String path){
		int i;
		Snapshot new_Snapshot;
		File file = new File(path);
		
		File[] files;
		
		if(file.exists() && file.isDirectory()){
			files = file.listFiles();
			
			if(files == null)
				return;
			for(i= 0; i < files.length; i++){
				scan(files[i].getPath());
			}
			
		}
		else if(file.exists() && file.isFile()){
			new_Snapshot = new Snapshot(file.lastModified(), file.length(), file.getName(), file.getParent(), file.getAbsolutePath());
			now_snapshot_list.add_Snapshot(new_Snapshot);
			//System.out.println(count++);
			return ;
		}
		else{
			System.out.println(file.getAbsolutePath());
			System.out.println("Not a file not a directory ???");
		}
	}
	
	public void take_snapshot(){
		File file = new File(file_name);
		String parent_path = file.getParent();
		
		previous_snapshot_list = now_snapshot_list;
		now_snapshot_list = new Snapshot_list();
		if(file.isFile() || file.exists() == false)
			scan(parent_path);
		else
			scan(file_name);
	}
	
	public void compare_to_makelist(){
		int i , j;

		boolean file_exist;
		gone_list = new Snapshot_list();
		come_list = new Snapshot_list();
		
		for(i = 0; i < previous_snapshot_list.get_Length(); i++){
			file_exist = false;
			for(j = 0; j < now_snapshot_list.get_Length(); j++){
				//less
				if(previous_snapshot_list.get_Snapshot(i).see_now_Path().equals(
						now_snapshot_list.get_Snapshot(j).see_now_Path())){
					file_exist = true;
				}
			}
			if(file_exist == false){
				gone_list.add_Snapshot(previous_snapshot_list.get_Snapshot(i));
			}
		}
		
		for(i = 0; i < now_snapshot_list.get_Length(); i++){
			file_exist = false;
			for(j = 0; j < previous_snapshot_list.get_Length(); j++){
				//more
				if(now_snapshot_list.get_Snapshot(i).see_now_Path().equals(
						previous_snapshot_list.get_Snapshot(j).see_now_Path())){
					file_exist = true;
				}
			}
			if(file_exist == false){
				come_list.add_Snapshot(now_snapshot_list.get_Snapshot(i));
			}
		}
		System.out.println("previous_snapshot_list : " + previous_snapshot_list.get_Length());
		System.out.println("now_snapshot_list : " + now_snapshot_list.get_Length());
	
		
		System.out.println("Gone list : " + gone_list.get_Length());
		System.out.println("Come list : " + come_list.get_Length());
	}
	
	
	
	public void compare_to_send(){
		int i,j;
		File file = new File(file_name);
		Snapshot snapshot;
		Snapshot gone_snapshot;
		Snapshot come_snapshot;
		Snapshot previous_snapshot;
		Snapshot now_snapshot;
		//rename
		//同目录 少了 新增了 有相同的lastModified和Size
		//记得用file_name
		if(trigger_type.equals(Element.rename)){
			for(i = 0; i < gone_list.get_Length(); i++){
				gone_snapshot = gone_list.get_Snapshot(i);
				for(j = 0; j < come_list.get_Length(); j++){
					come_snapshot = come_list.get_Snapshot(j);
//					System.out.println(gone_snapshot.see_now_Size());
//					System.out.println(come_snapshot.see_now_Size());
					if(gone_snapshot.see_now_Parentpath().equals(come_snapshot.see_now_Parentpath()) &&
						gone_snapshot.see_now_lastModified() == come_snapshot.see_now_lastModified() &&
						gone_snapshot.see_now_Size() == come_snapshot.see_now_Size()){
						
						if(file.isDirectory()){
							snapshot = new Snapshot(gone_snapshot.see_now_lastModified(), gone_snapshot.see_now_Size(), gone_snapshot.see_now_Name(),
									gone_snapshot.see_now_Parentpath(), gone_snapshot.see_now_Path(), come_snapshot.see_now_lastModified(),
									come_snapshot.see_now_Size(), come_snapshot.see_now_Name(), come_snapshot.see_now_Parentpath(),
									come_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							System.out.println("Send Rename Trigger.");
						}
						else if((file.isFile() || !file.exists()) && file_name.equals(gone_snapshot.see_now_Path())){
							snapshot = new Snapshot(gone_snapshot.see_now_lastModified(), gone_snapshot.see_now_Size(), gone_snapshot.see_now_Name(),
									gone_snapshot.see_now_Parentpath(), gone_snapshot.see_now_Path(), come_snapshot.see_now_lastModified(),
									come_snapshot.see_now_Size(), come_snapshot.see_now_Name(), come_snapshot.see_now_Parentpath(),
									come_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							System.out.println("Send Rename Trigger.");
							this.file_name = come_snapshot.see_now_Path();
						}
					}
				}
			}
		}
		
		//modified
		//path相同 size相同 modified不同
		if(trigger_type.equals(Element.modified)){
			
			for(i = 0; i < previous_snapshot_list.get_Length(); i++){
				
				previous_snapshot = previous_snapshot_list.get_Snapshot(i);
				for(j = 0; j < now_snapshot_list.get_Length(); j++){
					
					now_snapshot = now_snapshot_list.get_Snapshot(j);
				
					
					if(previous_snapshot.see_now_Path().equals(now_snapshot.see_now_Path()) &&
						previous_snapshot.see_now_lastModified() != now_snapshot.see_now_lastModified() &&
						previous_snapshot.see_now_Size() == now_snapshot.see_now_Size()){
						
						//System.out.println(Element.modified);
						
						if(file.isDirectory()){
							snapshot = new Snapshot(previous_snapshot.see_now_lastModified(), previous_snapshot.see_now_Size(), previous_snapshot.see_now_Name(),
									previous_snapshot.see_now_Parentpath(), previous_snapshot.see_now_Path(), now_snapshot.see_now_lastModified(),
									now_snapshot.see_now_Size(), now_snapshot.see_now_Name(), now_snapshot.see_now_Parentpath(),
									now_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							//System.out.println("Send " + Element.modified);
						}
						else if(file.isFile() && file_name.equals(previous_snapshot.see_now_Path())){
							snapshot = new Snapshot(previous_snapshot.see_now_lastModified(), previous_snapshot.see_now_Size(), previous_snapshot.see_now_Name(),
									previous_snapshot.see_now_Parentpath(), previous_snapshot.see_now_Path(), now_snapshot.see_now_lastModified(),
									now_snapshot.see_now_Size(), now_snapshot.see_now_Name(), now_snapshot.see_now_Parentpath(),
									now_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							//System.out.println("Send " + Element.modified);
						}
					}
				}
			}
		}
		//pathchanged
		//name相同 size相同 modified相同 parentpath不同
		//且原文件少了
		if(trigger_type.equals(Element.pathchange)){
			for(i = 0; i < gone_list.get_Length(); i++){
				gone_snapshot = gone_list.get_Snapshot(i);
				for(j = 0; j < come_list.get_Length(); j++){
					come_snapshot = come_list.get_Snapshot(j);
					
					if(gone_snapshot.see_now_Name().equals(come_snapshot.see_now_Name()) &&
						gone_snapshot.see_now_lastModified() == come_snapshot.see_now_lastModified() &&
						gone_snapshot.see_now_Size() == come_snapshot.see_now_Size() &&
						!gone_snapshot.see_now_Parentpath().equals(come_snapshot.see_now_Parentpath()) ){
						
						if(file.isDirectory()){
							snapshot = new Snapshot(gone_snapshot.see_now_lastModified(), gone_snapshot.see_now_Size(), gone_snapshot.see_now_Name(),
									gone_snapshot.see_now_Parentpath(), gone_snapshot.see_now_Path(), come_snapshot.see_now_lastModified(),
									come_snapshot.see_now_Size(), come_snapshot.see_now_Name(), come_snapshot.see_now_Parentpath(),
									come_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
						}
						else if((file.isFile() || !file.exists()) && file_name.equals(gone_snapshot.see_now_Path())){
							snapshot = new Snapshot(gone_snapshot.see_now_lastModified(), gone_snapshot.see_now_Size(), gone_snapshot.see_now_Name(),
									gone_snapshot.see_now_Parentpath(), gone_snapshot.see_now_Path(), come_snapshot.see_now_lastModified(),
									come_snapshot.see_now_Size(), come_snapshot.see_now_Name(), come_snapshot.see_now_Parentpath(),
									come_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							this.file_name = come_snapshot.see_now_Path();
						}
					}
				}
			}
		}
		//sizechanged
		//path相同 但是size和modified不同
		if(trigger_type.equals(Element.sizechange) ){
			//System.out.println(Element.sizechange);
			//System.out.println("Sizechange");
			for(i = 0; i < previous_snapshot_list.get_Length(); i++){
				previous_snapshot = previous_snapshot_list.get_Snapshot(i);
				for(j = 0; j < now_snapshot_list.get_Length(); j++){
					now_snapshot = now_snapshot_list.get_Snapshot(j);
					if(previous_snapshot.see_now_Path().equals(now_snapshot.see_now_Path()) &&
						previous_snapshot.see_now_lastModified() != now_snapshot.see_now_lastModified() &&
						previous_snapshot.see_now_Size() != now_snapshot.see_now_Size()){
						if(file.isDirectory()){
							snapshot = new Snapshot(previous_snapshot.see_now_lastModified(), previous_snapshot.see_now_Size(), previous_snapshot.see_now_Name(),
									previous_snapshot.see_now_Parentpath(), previous_snapshot.see_now_Path(), now_snapshot.see_now_lastModified(),
									now_snapshot.see_now_Size(), now_snapshot.see_now_Name(), now_snapshot.see_now_Parentpath(),
									now_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							System.out.print("Send Sizechange");
						}
						else if(file.isFile() && file_name.equals(previous_snapshot.see_now_Path())){
							snapshot = new Snapshot(previous_snapshot.see_now_lastModified(), previous_snapshot.see_now_Size(), previous_snapshot.see_now_Name(),
									previous_snapshot.see_now_Parentpath(), previous_snapshot.see_now_Path(), now_snapshot.see_now_lastModified(),
									now_snapshot.see_now_Size(), now_snapshot.see_now_Name(), now_snapshot.see_now_Parentpath(),
									now_snapshot.see_now_Path(), trigger_type, task_type);
							send(snapshot);
							System.out.print("Send Sizechange");
						}
					}
				}
			}
		}		
	}
	
	public void check_file(){
		File file = new File(file_name);			
		
		if(file.exists() && file.isFile()){//存在并且是文件
			file_type = Element.file;
			already_exist = true;
			target_lost = false;
		}
		else if(file.exists() && file.isDirectory()){
			file_type = Element.directory;
			already_exist = true;
			target_lost = false;
		}
		else{//不存在了
			target_lost = true;
		}
	}
	
	synchronized public void send(Snapshot new_Snapshot){
		Modified_trigger.snapshot_list.add_Snapshot(new_Snapshot);
		Rename_trigger.snapshot_list.add_Snapshot(new_Snapshot);
		Sizechange_trigger.snapshot_list.add_Snapshot(new_Snapshot);
		Pathchange_trigger.snapshot_list.add_Snapshot(new_Snapshot);
	}
	
	
	public void run(){
		while(already_exist == false || target_lost == false){
			
			if(already_exist == true && target_lost == false){
				try {
					take_snapshot();
				} catch (Exception e) {
					System.out.println("层次结构过深导致栈溢出");
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				compare_to_makelist();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				compare_to_send();
			}
			check_file();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Begin.program_over == true)
				break;
		}
	}
	
	public String get_file_name(){
		return file_name;
	}
	public String get_trigger_type(){
		return trigger_type;
	}
	public String get_task_type(){
		return task_type;
	}
	
	public void set_file_name(String file_name){
		this.file_name = file_name;
	}
}

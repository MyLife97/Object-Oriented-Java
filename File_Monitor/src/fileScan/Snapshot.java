package fileScan;

public class Snapshot {
	private long previous_lastModified;
	private long previous_Size;
	private String previous_Name;
	private String previous_Parentpath;
	private String previous_Path;
	
	
	private long now_lastModified;
	private long now_Size;
	private String now_Name;
	private String now_Parentpath;
	private String now_Path;
	
	private String trigger_type = null;
	private String task_type = null;
	
	private boolean trigger_on = false;
	
	
//	long previous_lastModified,
//	long previous_Size,
//	String previous_Name,
//	String previous_Parentpath,
//	String previous_Path,
	public Snapshot(
			long now_lastModified,
			long now_Size,
			String now_Name,
			String now_Parentpath,
			String now_Path){
		
//		this.previous_lastModified = previous_lastModified;
//		this.previous_Size = previous_Size;
//		this.previous_Name = previous_Name;
//		this.previous_Parentpath = previous_Parentpath;
//		this.previous_Path = previous_Path;
		
		this.now_lastModified = now_lastModified;
		this.now_Size = now_Size;
		this.now_Name = now_Name;
		this.now_Parentpath = now_Parentpath;
		this.now_Path = now_Path;
		
	}
	
	public Snapshot(
			long previous_lastModified,
			long previous_Size,
			String previous_Name,
			String previous_Parentpath,
			String previous_Path,
			
			long now_lastModified,
			long now_Size,
			String now_Name,
			String now_Parentpath,
			String now_Path,
			
			String trigger_type,
			String task_type){
		
		this.previous_lastModified = previous_lastModified;
		this.previous_Size = previous_Size;
		this.previous_Name = previous_Name;
		this.previous_Parentpath = previous_Parentpath;
		this.previous_Path = previous_Path;
		
		this.now_lastModified = now_lastModified;
		this.now_Size = now_Size;
		this.now_Name = now_Name;
		this.now_Parentpath = now_Parentpath;
		this.now_Path = now_Path;
		
		this.trigger_type = trigger_type;
		this.task_type = task_type;
		
	}
	
	public void set_trigger_type(String trigger_type){
		this.trigger_type = trigger_type;
	}
	
	public void set_trigger_on(boolean trigger_on){
		this.trigger_on = trigger_on;
	}
	public void set_task_type(String task_type){
		this.task_type = task_type;
	}
	
	public String see_trigger_type(){
		return trigger_type;
	}
	
	public boolean see_trigger_on(){
		return trigger_on;
	}
	public String see_task_type(){
		return task_type;
	}
	
	public long see_previous_lastModified(){
		return previous_lastModified;
	}
	public long see_previous_Size(){
		return previous_Size;
	}
	public String see_previous_Name(){
		return previous_Name;
	}
	public String see_previous_Parentpath(){
		return previous_Parentpath;
	}
	public String see_previous_Path(){
		return previous_Path;
	}
	
	public long see_now_lastModified(){
		return now_lastModified;
	}
	public long see_now_Size(){
		return now_Size;
	}
	public String see_now_Name(){
		return now_Name;
	}
	public String see_now_Parentpath(){
		return now_Parentpath;
	}
	public String see_now_Path(){
		return now_Path;
	}
}

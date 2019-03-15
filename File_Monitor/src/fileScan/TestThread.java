package fileScan;

public class TestThread extends Thread{
	SafeFile safeFile = new SafeFile();
	static boolean file_operation_over = false;
	
	public boolean addFile(String file_name){
		return safeFile.addFile(file_name);
	}
	
	public boolean makeDirectory(String file_name){
		return safeFile.makeDirectory(file_name);
	}
	
	public boolean rename(String from, String to){
		return safeFile.rename(from, to);
	}
	
	public boolean deleteFile(String file_name){
		return safeFile.deleteFile(file_name);
	}
	
	public boolean move(String from, String to){
		return safeFile.move(from, to);
	}
	
	public boolean changeSize(String file_name){
		return safeFile.changeSize(file_name);
	}
	
	public boolean changeTime(String file_name){
		return safeFile.changeTime(file_name);
	}
	
	public boolean testcase(){
		int i;
		addFile("H:\\1.txt");
		for(i= 0; i < 10; i++){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!changeSize("H:\\3.txt"))
				return false;
		}
		if(!rename("H:\\1.txt", "H:\\2.txt"))
			return false;
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!deleteFile("H:\\2.txt"))
			return false;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		return true;
	}
	
	public void run(){
		if(!testcase()){
			System.out.println("File Operation Error.");
		}
		else{
			System.out.println("File Operation Over.");
		}
		file_operation_over = true;
	}
}

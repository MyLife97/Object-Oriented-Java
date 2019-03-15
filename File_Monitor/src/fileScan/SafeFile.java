package fileScan;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;

public class SafeFile{
	
	synchronized public boolean addFile(String file_name){
		
		File file = new File(file_name);
		try {
			if(file.createNewFile() == true){
				System.out.println("File " + file.getAbsolutePath() + " create.");

			}
			else{
				System.out.println("File " + file.getAbsolutePath() + " already existed.");
				return false;
			}
		} catch (IOException e) {
			
		}
		return true;
	}
	
	synchronized public boolean deleteFile(String file_name){
		File file = new File(file_name);

		if(file.exists() && file.isFile()){
			file.delete();
			System.out.println("File " + file.getAbsolutePath() + " delete.");
			return true;
		}
		else{
			System.out.println("No File called " + file.getAbsolutePath());
			return false;
		}
	}

	synchronized public boolean makeDirectory(String file_name){
		File file = new File(file_name);
		if(file.mkdirs() == false){
			System.out.println("Directory " + file.getAbsolutePath() + " already existed.");
			return false;
		}
		else{
			System.out.println("Directory " + file.getAbsolutePath() + " create.");
			return true;
		}
	}
	
	synchronized public boolean rename(String from, String to){
		
		File file_from = new File(from);
		File file_to = new File(to);
		if(file_from.exists() && file_from.isFile()){
			if(file_from.renameTo(file_to) == true){
				System.out.println("Rename " + file_from.getAbsolutePath() + " to " + file_to.getAbsolutePath());
				return true;
			}
			else{
				System.out.println("File " + file_to.getAbsolutePath() + " already existed.");
				return false;
			}
		}
		else{
			System.out.println("File " + file_from.getAbsolutePath() + " not exist.");
			return false;
		}
	}
	
	synchronized public boolean move(String from, String to){
		File file_from = new File(from);
		File file_to = new File(to);
		if(file_from.exists() && file_from.isFile()){
			if(file_from.renameTo(file_to) == true){
				System.out.println("Move " + file_from.getAbsolutePath() + " to " + file_to.getAbsolutePath());
				return true;
			}
			else{
				System.out.println("File " + file_to.getAbsolutePath() + " already existed.");
				return false;
			}
		}
		else{
			System.out.println("File " + file_from.getAbsolutePath() + " not exist.");
			return false;
		}
	}
	
	synchronized public boolean changeTime(String file_name){
		File file = new File(file_name);

		if(file.exists() && file.isFile()){
			file.setLastModified(System.currentTimeMillis());
			System.out.println("File " + file.getAbsolutePath() + " touched.");

			return true;
		}
		else{
			System.out.println("File " + file.getAbsolutePath() + " not exist.");
			return false;
		}
	}
	
	synchronized public boolean changeSize(String file_name){
		File file = new File(file_name);
		Writer writer = null;
		BufferedWriter bufferedWriter = null;
		
		if(file.exists() && file.isFile()){
			try {
				writer = new FileWriter(file.getAbsolutePath(), true);
				bufferedWriter = new BufferedWriter(writer);
				file.setLastModified(System.currentTimeMillis());
			} catch (IOException e) {
				System.out.println("Changesize Writer Error!");
			}
			try {
				bufferedWriter.write("Size\n");
				bufferedWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("Not In");
				e.printStackTrace();
			}
			
			System.out.println("File " + file.getAbsolutePath() + " changesize.");
			return true;
		}
		else{
			System.out.println("File " + file.getAbsolutePath() + " not exist.");
			return false;
		}
	}
	
	synchronized public long getSize(String file_name){
		File file = new File(file_name);
		if(file.exists() && file.isFile()){
			System.out.println("File " + file.getAbsolutePath() + " has " + file.length() + " Bytes");
			return file.length();
		}
		else{
			System.out.println("File " + file.getAbsolutePath() + " not exist.");
			return Element.not_exist_size;
		}
	}
	
	public static void main(String args[]){
		SafeFile safeFile = new SafeFile();
		File file = new File("H:\\10.txt");
		System.out.println(file.getParent());
	}
	
	
}

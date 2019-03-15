package stupid_Taxi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Begin {
	static Taxi[] taxis = new Taxi[Element.TaxiNumber];
	static RequestList requestList = new RequestList();
	static TaxiGUI gui=new TaxiGUI();
	static boolean is_over = false;
	
	public static void main(String args[]){
		int i;
		Taxi taxi;
		
		mapInfo mi=new mapInfo();
		mi.readmap("map.txt");//在这里设置地图文件路径
		gui.LoadMap(mi.map, 80);
		
		Map map = new Map();
		map.readFile();
		map.initmatrix();
		
		Scheduler scheduler = new Scheduler();
		
		for(i = 0; i < Element.TaxiNumber; i++){
			taxi = new Taxi(i, (int)(Math.random()*Element.MapSize),
					(int)(Math.random()*Element.MapSize),
					System.currentTimeMillis() / 100 * 100);
			taxis[i] = taxi;
			taxis[i].start();
		}
		scheduler.start();
		
		readRequest();
		
		sleep(4000);
		Output.outFile();
		
		while(is_over != true){
			is_over = true;
			for(i = 0; i < Element.TaxiNumber; i++){
				if(taxis[i].status != Element.Waiting){
					is_over = false;
				}
			}
			sleep(500);
		}
		scheduler.stop();
		for(i = 0; i < Element.TaxiNumber; i++){
			taxis[i].stop();
		}
		
		
		System.out.println("Simulation Over.");
		System.out.println("Please Turn Off The Map Window");
	}
	
	public static void readRequest(){
		boolean is_over = false;
		Scanner scanner = new Scanner(System.in);
		String regex = "\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		Request new_request;
		String processed_line;
		String []group;
		
		int x_departure, y_departure, x_destination, y_destination;
		
		while(is_over != true){
			String input = scanner.nextLine();
			
			if(input.matches("END")){
				is_over = true;
				scanner.close();
				continue;
			}
			matcher = pattern.matcher(input);
			if(matcher.matches()){
				processed_line = input.replaceAll("\\(", "");
				processed_line = processed_line.replaceAll("\\)", "");
				processed_line = processed_line.replaceAll("\\[CR,", "");
				processed_line = processed_line.replaceAll("\\]", "");
				group = processed_line.split(",");
				x_departure = Integer.parseInt(group[0]);
				y_departure = Integer.parseInt(group[1]);
				x_destination = Integer.parseInt(group[2]);
				y_destination = Integer.parseInt(group[3]);
				
				if(x_departure >=80 || y_departure >= 80 ||
					x_destination >= 80 || y_destination >= 80 ||
					(x_departure == x_destination && y_departure == y_destination)){
					System.out.println("Invalid");
					continue;
				}

				new_request = new Request(System.currentTimeMillis() / 100 * 100, x_departure, y_departure, x_destination, y_destination , input);
				if(checkSame(new_request) != true){
					requestList.addRequest(new_request);
					System.out.println("Matches");
				}
				else{
					System.out.println("Same Request");
				}
			}
			else{
				System.out.println("Not Match");
			}
		}
	}
	
	public static boolean checkSame(Request request){
		int i;
		int number;
		number = requestList.getLength();
		for(i = 0; i < number; i++){
			if(requestList.getRequest(i).see_x_departure() == request.see_x_departure() &&
				requestList.getRequest(i).see_x_destination() == request.see_x_destination() &&
				requestList.getRequest(i).see_y_departure() == request.see_y_departure() &&
				requestList.getRequest(i).see_y_destination() == request.see_y_destination() &&
				request.see_systime() == requestList.getRequest(i).see_systime()){
				return true;
			}
		}
		return false;
	}
	
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class mapInfo{
	int[][] map=new int[80][80];
	public void readmap(String path){//读入地图信息
		//Requires:String类型的地图路径,System.in
		//Modifies:System.out,map[][]
		//Effects:从文件中读入地图信息，储存在map[][]中
		Scanner scan=null;
		File file=new File(path);
		if(file.exists()==false){
			System.out.println("地图文件不存在,程序退出");
			System.exit(1);
			return;
		}
		try {
			scan = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			
		}
		for(int i=0;i<80;i++){
			String[] strArray = null;
			try{
				strArray=scan.nextLine().split("");
			}catch(Exception e){
				System.out.println("地图文件信息有误，程序退出");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					this.map[i][j]=Integer.parseInt(strArray[j]);
				}catch(Exception e){
					System.out.println("地图文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		scan.close();
	}
}
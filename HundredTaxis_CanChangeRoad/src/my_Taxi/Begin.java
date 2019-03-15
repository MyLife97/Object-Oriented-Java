package my_Taxi;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Begin {
	static Taxi[] taxis = new Taxi[Element.TaxiNumber];
	static RequestList requestList = new RequestList();
	static RequestList memrequestList = new RequestList();
	static TaxiGUI gui=new TaxiGUI();
	static boolean is_over = false;
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Run the Whole Program.
		*/
	public static void main(String args[]){
		int i;
		Taxi taxi;
		

		mapInfo mi=new mapInfo();
		
		
		Scheduler scheduler = new Scheduler();
		
		for(i = 0; i < Element.TaxiNumber; i++){
			taxi = new Taxi(i, (int)(Math.random()*Element.MapSize),
					(int)(Math.random()*Element.MapSize),
					System.currentTimeMillis() / 100 * 100);
			taxis[i] = taxi;
		}
		
		Loadfile.readFile();
		mi.readmap();//在这里设置地图文件路径
		gui.LoadMap(mi.map, 80);
		
//		Map map = new Map();
//		map.readFile();
//		map.initmatrix();		
		
		for(i = 0; i < Element.TaxiNumber; i++){
			taxis[i].start();
		}
		scheduler.start();
		
		readRequest();
		
		sleep(4000);

		
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
		Output.outFile();
		
		System.out.println("Simulation Over.");
		System.out.println("Please Turn Off The Map Window");
	}

	/**
	* 注释.......
		* @MODIFIES : Begin.requestList;
		* @EFFECTS : 
		*(\all String input; matcher.mathces) ==> new request(); 
		*(\all Request new_request; new_request.exsit) ==> Begin.requestList.add(new_request);
		*/
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
			
			if(readRoad(input)){
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
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : Map.map, Map.graph, TaxiGUI.map, TaxiGUI.graph;
		* @EFFECTS : 
		*(\exist String input; matcher.mathces()) ==> \result == true ; 
		*(\exist String input; !matcher.mathces()) ==> \result == false ; 
		*(\exist String input; matcher.mathces()) ==> Map.map.change(); 
		*(\exist String input; matcher.mathces()) ==> Map.graph.change(); 
		*(\exist String input; matcher.mathces()) ==> TaxiGUI.map.change(); 
		*(\exist String input; matcher.mathces()) ==> TaxiGUI.graph.change(); 
		*/
	public static boolean readRoad(String input){
		String regex_open = "\\[RR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\),OPEN\\]";
		String regex_close = "\\[RR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\),CLOSE\\]";
		
		Pattern pattern_open = Pattern.compile(regex_open);
		Pattern pattern_close = Pattern.compile(regex_close);
		Matcher matcher_open;
		Matcher matcher_close;
		
		matcher_open = pattern_open.matcher(input);
		matcher_close = pattern_close.matcher(input);
		
		String processed_line;
		String []group;
		int x_departure, y_departure, x_destination, y_destination;
		
		if(matcher_open.matches()){
			processed_line = input.replaceAll("\\(", "");
			processed_line = processed_line.replaceAll("\\)", "");
			processed_line = processed_line.replaceAll("\\[RR,", "");
			processed_line = processed_line.replaceAll("\\]", "");
			group = processed_line.split(",");
			x_departure = Integer.parseInt(group[0]);
			y_departure = Integer.parseInt(group[1]);
			x_destination = Integer.parseInt(group[2]);
			y_destination = Integer.parseInt(group[3]);
			
			if(x_departure >=80 || y_departure >= 80 ||
					x_destination >= 80 || y_destination >= 80 ||
					(x_departure == x_destination && y_departure == y_destination)){
					System.out.println("changeRoad Error!");
				}
			else{
				Map.SetRoadStatus(new Point(x_departure,y_departure), new Point(x_destination,y_destination), Element.Open);
				TaxiGUI.SetRoadStatus(new Point(x_departure,y_departure), new Point(x_destination,y_destination), Element.Open);
			}
			return true;
		}
		else if(matcher_close.matches()){
			processed_line = input.replaceAll("\\(", "");
			processed_line = processed_line.replaceAll("\\)", "");
			processed_line = processed_line.replaceAll("\\[RR,", "");
			processed_line = processed_line.replaceAll("\\]", "");
			group = processed_line.split(",");
			x_departure = Integer.parseInt(group[0]);
			y_departure = Integer.parseInt(group[1]);
			x_destination = Integer.parseInt(group[2]);
			y_destination = Integer.parseInt(group[3]);
			
			if(x_departure >=80 || y_departure >= 80 ||
					x_destination >= 80 || y_destination >= 80 ||
					(x_departure == x_destination && y_departure == y_destination)){
					System.out.println("changeRoad Error!");
				}
			else{
				Map.SetRoadStatus(new Point(x_departure,y_departure), new Point(x_destination,y_destination), Element.Close);
				TaxiGUI.SetRoadStatus(new Point(x_departure,y_departure), new Point(x_destination,y_destination), Element.Close);
			}			
			
			return true;
		}
		else{
			return false;
		}


	}
	/**
	* 注释.......
		* @REQUIRES: requestList != null;
		* @MODIFIES : None;
		* @EFFECTS : 
		*	\result == requestList.contains(request);
		*/
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
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Sleep(time);
		*/
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
/**
* 注释.......
	* @REQUIRES: None;
	* @MODIFIES : this.map;
	* @EFFECTS : 
	* this.map == Map.map.clone();
	*/

	class mapInfo{
		int[][] map=new int[80][80];
		public void readmap(){//读入地图信息
			//Requires:String类型的地图路径,System.in
			//Modifies:System.out,map[][]
			//Effects:从文件中读入地图信息，储存在map[][]中
			map = Map.map.clone();
		}
}
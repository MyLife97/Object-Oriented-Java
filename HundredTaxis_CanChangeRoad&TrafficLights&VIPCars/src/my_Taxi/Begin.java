package my_Taxi;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Overview:Begin are the Only entry of whole program and the connection of each Thread.
 * Begin类用于存储各种线程，同时负责开启关闭各个线程，以协调不同线程的先后顺序
 * 线程包括100辆Taxis，1个调度器，1个Gui，1个红绿灯，和自身的输入线程
 * 
 */
public class Begin {
	static Taxi[] taxis = new Taxi[Element.TaxiNumber];
	static RequestList requestList = new RequestList();
	static RequestList memrequestList = new RequestList();
	static TaxiGUI gui=new TaxiGUI();
	static boolean is_over = false;
	static LightMap lightMap = new LightMap();
	
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
		
		RandomList randomList = new RandomList();
		FlowMap.init_flowmap();
		Scheduler scheduler = new Scheduler();
		
		for(i = 0; i < Element.TaxiNumber; i++){
			if(i < 30){
				taxi = new VIPTaxi(i,(int)(Math.random()*Element.MapSize),
						(int)(Math.random()*Element.MapSize),
						System.currentTimeMillis());
			}
			else{
				taxi = new Taxi(i,(int)(Math.random()*Element.MapSize),
						(int)(Math.random()*Element.MapSize),
						System.currentTimeMillis());
			}

			taxis[i] = taxi;
		}
		
		Loadfile.readFile();
		mi.readmap();//在这里设置地图文件路径
		gui.LoadMap(mi.map, 80);
		
		Map map = new Map();
		map.initmatrix();
		
		LightMap lightMap = new LightMap();
		lightMap.initLightMap();
		lightMap.start();
		
		sleep(100);//等待初始化完成
		
		for(i = 0; i < Element.TaxiNumber; i++){
			taxis[i].start();
		}
		scheduler.start();
		
		readRequest();
		
		sleep(9000);//等待请求分配完

		while(is_over != true){
			is_over = true;
			for(i = 0; i < Element.TaxiNumber; i++){
				if(taxis[i].status != Element.Waiting &&
					taxis[i].status != Element.Stop){
					is_over = false;
				}
			}
			sleep(1500);
		}
		scheduler.stop();
		lightMap.stop();
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
		*(is_over == true) ==> return;
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
			
			if(readVIP(input))
				continue;
			
			if(readTest(input))
				continue;
			
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

				new_request = new Request(System.currentTimeMillis(), x_departure, y_departure, x_destination, y_destination , input);
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
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		*(\exist String input; matcher.mathces()) ==> \result == true ; 
		*(\exist String input; !matcher.mathces() || number >= 30) ==> \result == false ; 
		*(\exist String input; matcher_all.mathces()) ==> ((VIPTaxi)taxis[number]).printAll(); 
		*(\exist String input; matcher_now.mathces()) ==> ((VIPTaxi)taxis[number]).printNow(); 
		*(\exist String input; matcher_next.mathces()) ==> ((VIPTaxi)taxis[number]).Next(); 
		*(\exist String input; matcher_previous.mathces()) ==> ((VIPTaxi)taxis[number]).Previous();
		*(\exist String input; matcher_hasnext.mathces()) ==> ((VIPTaxi)taxis[number]).HasNext();
		*(\exist String input; matcher_hasprevious.mathces()) ==> ((VIPTaxi)taxis[number]).HasPrevious(); 
		*/
	public static boolean readVIP(String input){
		int symbol = 0;
		int number;
		
		String regex_next = "\\[VIP,\\d{1,2},Next\\]";
		String regex_previous = "\\[VIP,\\d{1,2},Previous\\]";
		String regex_now = "\\[VIP,\\d{1,2},PrintNow\\]";
		String regex_all = "\\[VIP,\\d{1,2},PrintAll\\]";
		String regex_hasnext = "\\[VIP,\\d{1,2},hasNext\\]";
		String regex_hasprevious = "\\[VIP,\\d{1,2},hasPrevious\\]";
		
		Pattern pattern_next = Pattern.compile(regex_next);
		Pattern pattern_previous = Pattern.compile(regex_previous);
		Pattern pattern_now = Pattern.compile(regex_now);
		Pattern pattern_all = Pattern.compile(regex_all);
		Pattern pattern_hasnext = Pattern.compile(regex_hasnext);
		Pattern pattern_hasprevious = Pattern.compile(regex_hasprevious);
		
		Matcher matcher_next;
		Matcher matcher_previous;
		Matcher matcher_now;
		Matcher matcher_all;
		Matcher matcher_hasnext;
		Matcher matcher_hasprevious;
		
		//******************************************
		
		
		
		matcher_next = pattern_next.matcher(input);
		matcher_previous = pattern_previous.matcher(input);
		matcher_now = pattern_now.matcher(input);
		matcher_all = pattern_all.matcher(input);
		matcher_hasnext = pattern_hasnext.matcher(input);
		matcher_hasprevious = pattern_hasprevious.matcher(input);
		
		if(matcher_next.matches())
			symbol = Element.Next;
		else if(matcher_previous.matches())
			symbol = Element.Previous;
		else if(matcher_now.matches())
			symbol = Element.Now;
		else if(matcher_all.matches())
			symbol = Element.All;
		else if(matcher_hasnext.matches())
			symbol = Element.HasNext;
		else if(matcher_hasprevious.matches())
			symbol = Element.HasPrevious;
		
		if(symbol == 0)
			return false;
		
		String processed_line;
		String []group;
		
		processed_line = input.replaceAll("\\(", "");
		processed_line = processed_line.replaceAll("\\)", "");
		processed_line = processed_line.replaceAll("\\[VIP,", "");
		processed_line = processed_line.replaceAll("\\]", "");
		group = processed_line.split(",");
		
		number = Integer.parseInt(group[0]);

		//System.out.println(number);
			
		if(number >= 30){
			System.out.println("VIP Visit Error!");
			return false;
		}
		switch (symbol) {
		case Element.All:
			((VIPTaxi)taxis[number]).printAll();
			break;
			
		case Element.Now:
			((VIPTaxi)taxis[number]).printNow();
			break;
			
		case Element.Previous:
			((VIPTaxi)taxis[number]).previous();
			break;
			
		case Element.Next:
			((VIPTaxi)taxis[number]).next();
			break;
			
		case Element.HasNext:
			if(((VIPTaxi)taxis[number]).hasNext())
				System.out.printf("VIP %d has Next\n" , number);
			else
				System.out.printf("VIP %d doesn't have Next\n" , number);
			break;
			
		case Element.HasPrevious:
			if(((VIPTaxi)taxis[number]).hasPrevious())
				System.out.printf("VIP %d has Previous\n" , number);
			else
				System.out.printf("VIP %d doesn't have Previous\n" , number);
			break;
			
		}
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		*(\exist String input; matcher.mathces()) ==> \result == true ; 
		*(\exist String input; !matcher.mathces()) ==> \result == false ; 
		*(\exist String input; matcher_status.mathces()) ==> TestThread.searchStatus(number); 
		*(\exist String input; matcher_number.mathces()) ==> TestThread.searchNumber(number); 
		*/
	public static boolean readTest(String input){
		String regex_status = "\\[Test,\\d{1},Status\\]";
		String regex_number = "\\[Test,\\d{1,2},Number\\]";
		
		Pattern pattern_status = Pattern.compile(regex_status);
		Pattern pattern_number = Pattern.compile(regex_number);
		
		Matcher matcher_status = pattern_status.matcher(input);
		Matcher matcher_number = pattern_number.matcher(input);
		
		String processed_line;
		String []group;
		int number;
		
		processed_line = input.replaceAll("\\(", "");
		processed_line = processed_line.replaceAll("\\)", "");
		processed_line = processed_line.replaceAll("\\[Test,", "");
		processed_line = processed_line.replaceAll("\\]", "");
		group = processed_line.split(",");
		
		if(matcher_status.matches()){
			number = Integer.parseInt(group[0]);
			if(0 <= number && number <= 3){
				TestThread.searchStatus(number);
				return true;
			}
			else
				return false;
		}
		else if(matcher_number.matches()){
			number = Integer.parseInt(group[0]);
			TestThread.searchNumber(number);
			return true;
		}
		else
			return false;
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
		synchronized (requestList) {
			number = requestList.getLength();
			for(i = 0; i < number; i++){
				if(requestList.getRequest(i).see_x_departure() == request.see_x_departure() &&
					requestList.getRequest(i).see_x_destination() == request.see_x_destination() &&
					requestList.getRequest(i).see_y_departure() == request.see_y_departure() &&
					requestList.getRequest(i).see_y_destination() == request.see_y_destination() &&
					request.see_systime() <= requestList.getRequest(i).see_systime() + Element.SameRequest){
					return true;
				}
			}
			return false;
		}

	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Thread.Sleep(time);
		*/
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (/exist int i; 0 <= i < Element.TaxiNumber; taxis[i] == null) ==> \result == false;
		* (requestList == null) ==> \result == false;
		* (memrequestList == null) ==> \result == false;
		* (gui == null) ==> \result == false;
		* (lightMap == null) ==> \result == false;
		* Else ==> \result == true; 
		*/
	public boolean repOK(){
		int i;
		for(i = 0; i < Element.TaxiNumber;i++){
			if(taxis[i] == null)
				return false;
		}
		if(requestList == null)
			return false;
		if(memrequestList == null)
			return false;
		if(gui == null)
			return false;
		if(lightMap == null)
			return false;
		
		return true;
	}
}
/**
* 注释.......
	* @REQUIRES: Map.map != null;
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
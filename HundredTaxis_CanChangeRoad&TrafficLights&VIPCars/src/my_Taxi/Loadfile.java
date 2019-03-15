package my_Taxi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @OVERVIEW:
 * 这个类是为了读取文件信息作为输入创建的
 * 静态变量file用于文件信息的获取
 * reader用于文件的内容读取
 * line用于统计行数
 * readFile用于读取文件内容，类似于状态机，没读取到标志符如#map就进入下一个状态
 * readMap用于读到Map时处理地图信息
 * readFlow用于读到流量时处理流量信息
 * readTaxi用于读到出租车信息时完成初始化
 * readLight用于读到Light信息时完成红绿灯地图的初始化
 * readRequest用于读到Request信息时完成记录
 */
public class Loadfile {
	static File file = new File("fileInfo.txt");
	static BufferedReader reader = null;
	
	static int line = 0;
	
	
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : ;
		* 这个方法用于将后面各个读取特殊信息的方法以一定顺序串联起来，本身不会产生任何副作用
		* 如果接受内部方法的IOException，会输出提示，并退出程序
		*/
	public static void readFile(){
		int i;
		int status = 0;
		
		String regexMapBegin = "#map";
		String regexMapEnd = "#end map";
		String regexLightBegin = "#light";
		String regexLightEnd = "#end light";
		String regexFlowBegin = "#flow";
		String regexFlowEnd = "#end flow";
		String regexTaxiBegin = "#taxi";
		String regexTaxiEnd = "#end taxi";
		String regexRequestBegin = "#request";
		String regexRequestEnd = "#end request";
		
		Pattern pattern;
		Matcher matcher;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String input = null;
			while((input = reader.readLine()) != null){
				
				if(status == Element.Zero){
					pattern = Pattern.compile(regexMapBegin);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.MapBegin;
					}
					else{
						System.out.println("0");
						throw(new IOException());
					}
				}
				else if(status == Element.MapBegin){
					pattern = Pattern.compile(regexMapEnd);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						if(line != Element.MapSize){
							throw(new IOException());
						}
						status = Element.MapEnd;
						line = 0;
					}
					else{
						if(readMap(input))
							;
						else{
							System.out.println("1");
							throw(new IOException());
						}
					}
				}
				
				else if(status == Element.MapEnd){
					pattern = Pattern.compile(regexLightBegin);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.LightBegin;
					}
					else{
						System.out.println("2");
						throw(new IOException());
					}
				}
				
				
				else if(status == Element.LightBegin){
					pattern = Pattern.compile(regexLightEnd);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						if(line != Element.MapSize){
							throw(new IOException());
						}
						status = Element.LightEnd;
					}
					else{
						if(readLight(input))
							;
						else{
							System.out.println("9");
							throw(new IOException());
						}
					}
				}
				
				

				else if(status == Element.LightEnd){
					pattern = Pattern.compile(regexFlowBegin);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.FlowBegin;
					}
					else{
						System.out.println("10");
						throw(new IOException());
					}
				}
				
				
				else if(status == Element.FlowBegin){
					pattern = Pattern.compile(regexFlowEnd);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.FlowEnd;
					}
					else{
						if(readFlow(input))
							;
						else{
							System.out.println("3");
						
							throw(new IOException());
						}
					}
				}
				else if(status == Element.FlowEnd){
					pattern = Pattern.compile(regexTaxiBegin);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.TaxiBegin;
					}
					else{
						System.out.println("4");
						throw(new IOException());
					}
				}
				else if(status == Element.TaxiBegin){
					pattern = Pattern.compile(regexTaxiEnd);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.TaxiEnd;
					}
					else{
						if(readTaxi(input))
							;
						else{
							System.out.println("5");
							throw(new IOException());
						}
					}
				}
				else if(status == Element.TaxiEnd){
					pattern = Pattern.compile(regexRequestBegin);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.RequestBegin;
					}
					else{
						System.out.println("6");
						throw(new IOException());
					}
				}
				else if(status == Element.RequestBegin){
					pattern = Pattern.compile(regexRequestEnd);
					matcher = pattern.matcher(input);
					if(matcher.matches()){
						status = Element.RequestEnd;
					}
					else{
						if(readRequest(input))
							;
						else{
							System.out.println("7");
							throw(new IOException());
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("File IO Error!");
			System.exit(0);
		}
	}
	/**
	* 注释.......
		* @REQUIRES: input != null;
		* @MODIFIES : Map.map , line;
		* @EFFECTS : 
		*(\exist input != null) ==> line++ ; 
		*(\all String input; matcher.matches() == true) ==> \result == true , Map.map.change();
		*(\exist String input; matcher.matches() != true) ==> \result == false;
		*(input.length() != Element.MapSize) ==> exceptional_behavior (IOException);
		*!('0' <= input.charAt(i) <= '3') ==> exceptional_behavior (IOException);
		*/
	public static boolean readMap(String input) throws IOException{
		int i;
		input = input.replaceAll(" ", "");
		input = input.replaceAll("\\t", "");		
		if(input.length() != Element.MapSize){
			throw(new IOException());
		}
		
		for(i = 0; i < input.length(); i++){
			if((input.charAt(i) >= '0' && input.charAt(i) <= '3')){
				Map.map[line][i] = input.charAt(i) - '0';
				Map.memmap[line][i] = input.charAt(i) - '0';
			}
			else{
				throw(new IOException());
			}
		}
		line ++;
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: input != null;
		* @MODIFIES : guigv.flowmap;
		* @EFFECTS : 
		* (input.matches) ==> upload guigv.flowmap;
		* (两点不邻接  || 格式不匹配 || 坐标越界) ==> exceptional_behavior (IOException);
		* !(两点不邻接  || 格式不匹配 || 坐标越界) ==> \result == true;
		*/
	public static boolean readFlow(String input) throws IOException{
		String regex = "\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\),\\d{1,5}";
		String processed;
		Pattern pattern;
		Matcher matcher;
		String [] group;
		int x_departure, y_departure, x_destination, y_destination;
		int value;
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(input);
		if(matcher.matches()){
			processed = input.replaceAll("\\(", "");
			processed = processed.replaceAll("\\)", "");
			group = processed.split(",");

			x_departure = Integer.parseInt(group[0]);
			y_departure = Integer.parseInt(group[1]);
			x_destination = Integer.parseInt(group[2]);
			y_destination = Integer.parseInt(group[3]);
			value = Integer.parseInt(group[4]);
			
			if(x_departure >=80 || y_departure >= 80 ||
					x_destination >= 80 || y_destination >= 80 ||
					(x_departure == x_destination && y_departure == y_destination)){
					System.out.println("Invalid");
					throw(new IOException());
			}
			
			int di = x_departure - x_destination;
			int dj = y_departure - y_destination;
			
			if (di == 0) {// 在同一水平线上
				if (dj == 1) {// p2-p1
					;
				} else if (dj == -1) {// p1-p2
					;
				} else {
					throw(new IOException());
				}
			} else if (dj == 0) {// 在同一竖直线上
				if (di == 1) {// p2-p1
					;
				} else if (di == -1) {// p1-p2
					;
				} else {
					throw(new IOException());
				}
			}
			FlowMap flowMap = new FlowMap(x_departure * 80 + y_departure, x_destination * 80 + y_destination, value);
			flowMap.start();
		}
		else{
			throw(new IOException());
		}
		return true;
	}

	/**
	* 注释.......
		* @REQUIRES: input != null;
		* @MODIFIES : Map.map , line;
		* @EFFECTS : 
		*(\exist input != null) ==> line++ ; 
		*(\all String input; matcher.matches() == true) ==> \result == true , LightMap.lightMap.change();
		*(\exist String input; matcher.matches() != true) ==> \result == false;
		*(input.length() != Element.MapSize ==> exceptional_behavior (IOException);
		*!('0' <= input.charAt(i) <= '1') ==> exceptional_behavior (IOException);
		*/
	public static boolean readLight(String input) throws IOException{
		int i;
		input = input.replaceAll(" ", "");
		input = input.replaceAll("\\t", "");
		if(input.length() != Element.MapSize){
			throw(new IOException());
		}
		for(i = 0; i < input.length(); i++){
			if(input.charAt(i) == '0'){
				LightMap.lightmap[line * 80 + i] = 0;
			}
			else if(input.charAt(i) == '1'){
				if(checkLightCorrect(line * 80 + i))
					LightMap.lightmap[line * 80 + i] = (int)(Math.random() * 2) + 1;
				else
					System.out.println("Light Position Error, Continue");
			}
			else{
				throw(new IOException());
			}
		}
		line ++;
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400;
		* @MODIFIES : Map.map;
		* @EFFECTS : 
		*(某一点有三条以上相邻边形成丁字路口或者十字路口) ==> \result == true;
		*Else == true ==> \result == false;
		*/
	public static boolean checkLightCorrect(int root){
		int number = 0;
		int x_now = root / 80;
		int y_now = root % 80;
		if(Map.map[x_now][y_now] == 2 || Map.map[x_now][y_now] == 3){
			//down = true; 2
			number ++;
		}
		
		if(Map.map[x_now][y_now] == 1 || Map.map[x_now][y_now] == 3){
			//right = true; 4
			number ++;
		}
		
		if(x_now != 0 && (Map.map[x_now - 1][y_now] == 2 || Map.map[x_now - 1][y_now] == 3)){
			//up = true; 1
			number ++;
		}
		
		if(y_now != 0 && (Map.map[x_now][y_now - 1] == 1 || Map.map[x_now][y_now - 1] == 3)){
			//left = true; 3
			number ++;
		}
		if(number >= 3)
			return true;
		else
			return false;
	}
	
	/**
	* 注释.......
		* @REQUIRES: input != null;
		* @MODIFIES : Begin.taxis;
		* @EFFECTS : 
		* (input.matches() == true) ==> Taxis[number].set == true;
		* (格式不匹配 || 坐标越界) ==> exceptional_behavior (IOException);
		* !(格式不匹配 || 坐标越界) ==> \result == true;
		*/

	public static boolean readTaxi(String input) throws IOException{
		String regex = "\\d{1,2},\\d{1},\\d{1,5},\\(\\d{1,2},\\d{1,2}\\)";
		Pattern pattern;
		Matcher matcher;
		
		String processed;
		String [] group;
		int number;
		int status;
		int reputation;
		int x, y;		
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(input);
		
		if(matcher.matches()){
			processed = input.replaceAll("\\(", "");
			processed = processed.replaceAll("\\)", "");
			group = processed.split(",");
			number = Integer.parseInt(group[0]);
			status = Integer.parseInt(group[1]);
			reputation = Integer.parseInt(group[2]);
			x = Integer.parseInt(group[3]);
			y = Integer.parseInt(group[4]);
			
			if(!((number >= 0 && number < 100) && (status >= 0 && status <= 3) &&
					(x >= 0 && x < 80) && (y >= 0 && y < 80))){
				throw(new IOException());
			}
			
			Begin.taxis[number].status = status;
			Begin.taxis[number].reputation = reputation;
			Begin.taxis[number].x_now = x;
			Begin.taxis[number].y_now = y;
			
			if(status == Element.Serving || status == Element.Picking){
				Begin.taxis[number].status = Element.Serving;
				Begin.taxis[number].x_destination = (int)(Math.random()*Element.MapSize);
				Begin.taxis[number].y_destination = (int)(Math.random()*Element.MapSize);
				Begin.taxis[number].force = true;
				
				Request request = new Request(System.currentTimeMillis(), Begin.taxis[number].x_destination, Begin.taxis[number].y_destination, true);
				Begin.taxis[number].setRequest(request);
				request.receiveTime = new Snapshot(number, x, y, status, reputation, System.currentTimeMillis());
			}
			
		}
		else{
			throw(new IOException());
		}
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: input != null;
		* @MODIFIES : Begin.requestList;
		* @EFFECTS : 
		*(\all String input; matcher.mathces) ==> new request(); 
		*(\all Request new_request; new_request.exsit) ==> Begin.requestList.add(new_request);
		*(格式不匹配 || 坐标越界) ==> exceptional_behavior (IOException);
		*!(格式不匹配 || 坐标越界) ==> \result == true;
		*/
	public static boolean readRequest(String input) throws IOException{
		String regex = "\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(input);
		
		Request new_request;
		String processed_line;
		String []group;
		
		int x_departure, y_departure, x_destination, y_destination;
		
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
				throw(new IOException());
			}

			new_request = new Request(System.currentTimeMillis(), x_departure, y_departure, x_destination, y_destination , input);
			
			Begin.requestList.addRequest(new_request);
		}
		else{
			throw(new IOException());
		}
		
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(file == null)
			return false;
		if(line < 0 || line > 80)
			return false;
		return true;
	}
}

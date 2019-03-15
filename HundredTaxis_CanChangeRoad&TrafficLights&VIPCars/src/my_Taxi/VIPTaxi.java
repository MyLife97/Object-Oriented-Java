package my_Taxi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @OVERVIEW:
 * 这个类是VIP出租车类
 * 包括的成员信息有父类所有成员变量，同时还有index、listLength和vipList组成双向迭代器供访问
 * 重写的方法为将部分寻路方法改为基于原始地图
 */
public class VIPTaxi extends Taxi implements BiIterator<Request>{
	
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= number < 100, 0 <= x_now < 80,
		* 0 <= y_now < 80, systime >= 0;
		* @MODIFIES : this;
		* @EFFECTS : 
		* this.number = number;
		* this.x_now = x_now;
		* this.y_now = y_now;
		* this.systime = systime;
		* this.type = Element.VIP_taxi;
		*/	
	public VIPTaxi(int number,int x_now, int y_now, long systime) {
		super(number, y_now, y_now, systime);
		this.type = Element.VIP_taxi;
		TaxiGUI.SetTaxiType(number, type);
	}
	
	private Integer index = 0;
	private Integer listLength = 0;
	private ArrayList<Request> vipList = new ArrayList<Request>();
	
	@Override
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.index;
		* @EFFECTS : 
		* (hasNext()) ==> (index++ == true) && \result == vipList.get(index);
		* (!hasNext()) ==> \result == null;
		*/
	public Request next() {
		// TODO Auto-generated method stub
		synchronized (this.index) {
			if(hasNext()){
				System.out.printf("VIP-%d go Next to Request-%d\n" , number, index + 1);
				return vipList.get(this.index ++);
			}
			else{
				System.out.printf("VIP-%d can't go Next\n" , number);
				return null;
			}
		}
	}

	@Override
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.index;
		* @EFFECTS : 
		* (hasPrevious()) ==> (index++ == true) && \result == vipList.get(index);
		* (!hasPrevious()) ==> \result == null;
		*/
	public Request previous() {
		// TODO Auto-generated method stub
		synchronized (this.index) {
			if(hasPrevious()){
				System.out.printf("VIP-%d go Previous to Request-%d\n" , number, index - 1);
				return vipList.get(-- this.index);
			}
			else{
				System.out.printf("VIP-%d can't go Previous\n" , number);
				return null;
			}
		}
	}

	@Override
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (index < listLength) ==> \result == true;
		* !(index < listLength) ==> \result == false;
		*/
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(index < listLength){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (index > 0) ==> \result == true;
		* !(index > 0) ==> \result == false;
		*/
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		if(index > 0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	* 注释.......
		* @REQUIRES: request != null;
		* @MODIFIES : this.vipList, listLength;
		* @EFFECTS : 
		* vipList == \old(vipList) + request;
		* listLength == \old(listLength) + 1;
		*/
	public void addList(Request request){
		this.vipList.add(request);
		listLength ++;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.index;
		* @EFFECTS : 
		* (hasNext() == true) ==> output the Next Request == true;
		* !(hasNext() == true) ==> output the Next Request == false;
		*/
	public void printNow(){
		Request request;
		if(hasNext() == true){
			System.out.println("Print current request");
			
			request = next();
			
			Writer writer ;
			BufferedWriter bufferedWriter = null;
			int mark;
			int j;
			
			try {
				writer = new FileWriter(String.format("VIP-%d.txt", this.number) , true);
				bufferedWriter = new BufferedWriter(writer);
			} catch (IOException e) {
				
			}
			try {
				bufferedWriter.write(String.format("This is the Request-%d of VIP-%d\n", this.index, this.number));
				bufferedWriter.write(String.format("Light Change Period is %d MS\n\n", LightMap.period));
				bufferedWriter.flush();
			} catch (IOException e1) {
			}
			if(request.ghostRequest == false){
				try {
					//Request Info
					bufferedWriter.write(String.format("Request:%s\n", request.self));
					bufferedWriter.flush();						
					bufferedWriter.write(String.format("  >>  Systime:%s Departure:(%d,%d) Destination:(%d,%d) TaxiNumber:%d\n", request.systime ,
						request.x_departure, request.y_departure , request.x_destination, request.y_destination, request.taxi_number));
					bufferedWriter.flush();	

					//Taxi Info
					bufferedWriter.write(String.format("VIP Taxi Info:\n"));
					bufferedWriter.flush();	

					//Taxi Info
					for(mark = 0; mark < request.taxis.size(); mark++){
						if(request.taxis.get(mark).number == this.number)
							break;
					}
					bufferedWriter.write(String.format("  >>  TaxiNumber:%d TaxiPosition:(%d,%d) TaxiReputation:%d\n",
							this.number , request.taxis.get(mark).x, request.taxis.get(mark).y, request.taxis.get(mark).reputation));
					bufferedWriter.flush();	
					
					//Pick Route
					bufferedWriter.write(String.format("PickRoute:\n"));
					bufferedWriter.flush();	
					for(j = 0; j < request.pickRoute.size(); j++){
						bufferedWriter.write(String.format("  >>  Systime:%s Position:(%d,%d)\n", request.pickRoute.get(j).systime ,
								request.pickRoute.get(j).x , request.pickRoute.get(j).y));
						bufferedWriter.flush();	
					}
					
					//Send Route
					bufferedWriter.write(String.format("SendRoute:\n"));
					bufferedWriter.flush();						
					for(j = 0; j < request.arriveRoute.size(); j++){
						bufferedWriter.write(String.format("  >>  Systime:%s Position:(%d,%d)\n", request.arriveRoute.get(j).systime ,
								request.arriveRoute.get(j).x , request.arriveRoute.get(j).y));
						bufferedWriter.flush();	
					}
					
						
				} catch (IOException e) {
				}
			}
			else{
				try {
					//GhostRequest Info
					bufferedWriter.write(String.format("GhostRequest:%s\n", request.self));
					bufferedWriter.flush();
					
					//Taxi Info
					bufferedWriter.write(String.format("VIP Taxi Info:\n"));
					bufferedWriter.flush();	
					bufferedWriter.write(String.format("  >>  TaxiNumber:%d TaxiPosition:(%d,%d) TaxiReputation:%d\n",
							request.receiveTime.number , request.receiveTime.x, request.receiveTime.y, request.receiveTime.reputation));
					bufferedWriter.flush();	
					
					//Send Route
					bufferedWriter.write(String.format("SendRoute:\n"));
					bufferedWriter.flush();						
					for(j = 0; j < request.arriveRoute.size(); j++){
						bufferedWriter.write(String.format("  >>  Systime:%s Position:(%d,%d)\n", request.arriveRoute.get(j).systime ,
								request.arriveRoute.get(j).x , request.arriveRoute.get(j).y));
						bufferedWriter.flush();	
					}
					
				} catch (IOException e) {
				}
			}
			try{
				bufferedWriter.write(String.format("\n"));
				bufferedWriter.flush();
			}catch(IOException e){
				
			}
		}
		else
			System.out.println("Can't print current request");
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.index;
		* @EFFECTS : 
		* while(hasNext() == true) ==> output the Next Request == true;
		* !(hasNext() == true) ==> return;
		*/
	public void printAll(){
		while(hasPrevious() == true)
			previous();
		while(hasNext()){
			printNow();
		}
	}
	
	  /**
	  * 注释.......
	    * @REQUIRES: 1 <= dir <= 4;
	    * @MODIFIES : None;
	    * @EFFECTS : 
	    * (dir == Element.Up && (Map.memmap[x_now - 1][y_now] == 3 || Map.memmap[x_now - 1][y_now] == 2)) ==> \result == true;
	    * (dir == Element.Down && (Map.memmap[x_now][y_now] == 3 || Map.memmap[x_now][y_now] == 2)) ==> \result == true;
	    * (dir == Element.Left && (Map.memmap[x_now][y_now - 1] == 3 || Map.memmap[x_now][y_now - 1] == 1)) ==> \result == true; 
	    * (dir == Element.Right && (Map.memmap[x_now][y_now] == 3 || Map.memmap[x_now][y_now] == 1)) ==> \result == true;
	    */
	@Override
	public boolean stillCanGo(int dir){
		if(dir == Element.Up && (Map.memmap[x_now - 1][y_now] == 3 || Map.memmap[x_now - 1][y_now] == 2))
			return true;
		else if(dir == Element.Down && (Map.memmap[x_now][y_now] == 3 || Map.memmap[x_now][y_now] == 2))
			return true;
		else if(dir == Element.Left && (Map.memmap[x_now][y_now - 1] == 3 || Map.memmap[x_now][y_now - 1] == 1))
			return true;
		else if(dir == Element.Right && (Map.memmap[x_now][y_now] == 3 || Map.memmap[x_now][y_now] == 1))
			return true;
		return false;
	}

	/**
	* 注释.......
		* @REQUIRES: 0 <= x < 6400, 0 <= y < 6400, 1 <= direction <= 4;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (The current in direction == lowest) ==> \result == true;
		* else ==> \result == false;
		*/
	@Override
	public boolean checkFlow(int x, int y, int direction){
		int root;
		int destination;
		root = x * 80 + y;
		destination = root + offset[direction];
		
		int flow = FlowMap.getFlow(root, destination);
		
		if(Map.memmap[x_now][y_now] == 2 || Map.memmap[x_now][y_now] == 3){
			//down = true; 2
			if(flow > FlowMap.getFlow(root, root + offset[Element.Down]))
				return false;
		}
		
		if(Map.memmap[x_now][y_now] == 1 || Map.memmap[x_now][y_now] == 3){
			//right = true; 4
			if(flow > FlowMap.getFlow(root, root + offset[Element.Right]))
				return false;
		}
		
		if(x_now != 0 && (Map.memmap[x_now - 1][y_now] == 2 || Map.memmap[x_now - 1][y_now] == 3)){
			//up = true; 1
			if(flow > FlowMap.getFlow(root, root + offset[Element.Up]))
				return false;
		}
		
		if(y_now != 0 && (Map.memmap[x_now][y_now - 1] == 1 || Map.memmap[x_now][y_now - 1] == 3)){
			//left = true; 3
			if(flow > FlowMap.getFlow(root, root + offset[Element.Left]))
				return false;
		}		
		return true;
	}
	/**
	* 注释.......
		* @REQUIRES: 1 <= hangDir <= 4;
		* @MODIFIES : this.x_now, this.y_now;
		* @EFFECTS : 
		* Update this taxi's direction based on hangDir;
		*/
	@Override
	public void hangAround(int hangDir){
		boolean isClosed = checkClose(x_now, y_now, hangDir);
		
		if(hangDir == Element.Up){
			if(isClosed == false){
				FlowMap flowMap = new FlowMap(x_now * 80 + y_now, (x_now - 1) * 80 + y_now, Element.OneCar);
				flowMap.start();
			}
			x_now = x_now - 1;
		}
		else if(hangDir == Element.Down){
			if(isClosed == false){
				FlowMap flowMap = new FlowMap(x_now * 80 + y_now, (x_now + 1) * 80 + y_now, Element.OneCar);
				flowMap.start();
			}
			x_now = x_now + 1;
		}
		else if(hangDir == Element.Left){
			if(isClosed == false){
				FlowMap flowMap = new FlowMap(x_now * 80 + y_now, x_now * 80 + y_now - 1, Element.OneCar);
				flowMap.start();
			}
			y_now = y_now - 1;
		}
		else {
			if(isClosed == false){
				FlowMap flowMap = new FlowMap(x_now * 80 + y_now, x_now * 80 + y_now + 1, Element.OneCar);
				flowMap.start();
			}
			y_now = y_now + 1;
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= x_now < 80, 0 <= y_now < 80, 1 <= dir <= 4;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (Map.memgraph[root][destination] == 1 && Map.graph[root][destination] == 0) ==> \result == true;
		* !(Map.memgraph[root][destination] == 1 && Map.graph[root][destination] == 0) ==> \result == false;
		*/
	public boolean checkClose(int x_now, int y_now, int dir){
		int root;
		int destination;
		root = x_now * 80 + y_now;
		destination = root + offset[dir];
		if(Map.memgraph[root][destination] == 1 && Map.graph[root][destination] == 0)
			return true;//road has been closed
		else
			return false;
	}
	
	@Override
	/**
	* 注释.......
		* @REQUIRES: 0 <= x < 80, 0 <= y < 80;
		* @MODIFIES : this.vipList;
		* @EFFECTS : 
		* do super.goto_destination() == true;
		* this.vipList == \old(this.vipList) + request;
		* \result == null;
		*/
	public Request goto_destination(int x , int y){
		Request request = super.goto_destination(x, y);
		addList(request);
		//System.out.println("VIP Finished");
		return null;
	}
	
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= x_now < 80, 0 <= y_now < 80;
		* @MODIFIES : None;
		* @EFFECTS : 
		* Find a random way base on the Map;
		* \result == number;
		*/
	@Override
	public int getListNumber(int x_now , int y_now){
		int number = 0;
		
		if(Map.memmap[x_now][y_now] == 2 || Map.memmap[x_now][y_now] == 3){
			//down = true; 2
			number += 2;
		}
		
		if(Map.memmap[x_now][y_now] == 1 || Map.memmap[x_now][y_now] == 3){
			//right = true; 4
			number += 8;
		}
		
		if(x_now != 0 && (Map.memmap[x_now - 1][y_now] == 2 || Map.memmap[x_now - 1][y_now] == 3)){
			//up = true; 1
			number += 1;
		}
		
		if(y_now != 0 && (Map.memmap[x_now][y_now - 1] == 1 || Map.memmap[x_now][y_now - 1] == 3)){
			//left = true; 3
			number += 4;
		}
		return number;
	}

	

	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (super.repOK() && listLength >= 0 && index >= 0 && vipList != null
		* && number >= 0 && number < 30 && type == 1) ==> \result == true;
		* else ==> \result == false;
		*/
	public boolean repOK(){
		if(super.repOK() && listLength >= 0 && index >= 0 && vipList != null
				&& number >= 0 && number < 30 && type == 1)
			return true;
		else
			return false;
	}
}

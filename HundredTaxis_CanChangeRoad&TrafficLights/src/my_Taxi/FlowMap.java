package my_Taxi;

/**
 * @OVERVIEW:
 * 这个类的静态变量flowmap用于存放流量数据，以邻接矩阵的方式存储
 * root和destination用于记录哪条路需要增删流量，以建立新线程形式使用
 * value用于存放流量增减的个数
 */
public class FlowMap extends Thread{
	public static int flowmap[][] = new int[6400][6400];
	
	int root;
	int destination;
	int value;
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400, 0 <= destination < 6400;
		* @MODIFIES : this.root, this.destination;
		* @EFFECTS :
		* this.root = root;
		* this.destination = destination;
		*/
	public FlowMap(int root, int destination, int value){
		this.root = root;
		this.destination = destination;
		this.value = value;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.flowmap;
		* @EFFECTS :
		* (\all int i,j; 0 <= i < 6400, 1 <= j <= 4) ==>
		* (flowmap[i][i + offset[j]] == 0 && flowmap[i + offset[j]][i] == 0);
		*/
	public static void init_flowmap(){
		int i;
		int j;
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		for(i = 0; i < 6400; i++){
			for(j = 1; j <= 4; j++){
				if(i + offset[j] >= 0 && i + offset[j] < 6400){
					flowmap[i][i + offset[j]] = 0;
					flowmap[i + offset[j]][i] = 0;
				}
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400, 0 <= destination < 6400;
		* @MODIFIES : this.flowmap;
		* @EFFECTS :
		* 用于计算车流量，实现流量的实时增减
		* @THREAD_REQUIRES: None;
		* @THREAD_EFFECTS: \locked(flowmap);
		*/
	public void run(){
		synchronized (flowmap) {
			flowmap[root][destination] += this.value;
			flowmap[destination][root] += this.value;
		}
		sleep(501);
		synchronized(flowmap){
			flowmap[root][destination] -= this.value;
			flowmap[destination][root] -= this.value;
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400, 0 <= destination < 6400;
		* @MODIFIES : None;
		* @EFFECTS :
		* \result == flowmap[root][destination];
		*/
	public static int getFlow(int root, int destination){
		return flowmap[root][destination];
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS :
		* (flowmap == null) ==> \result == false;
		* (0 > root || 6400 <= root || 0 > destination || 6400 <= root) ==> \result == false;
		* Else ==> \result == true;
		*/
	public boolean repOK(){
		if(flowmap == null)
			return false;
		if(0 > root || 6400 <= root || 0 > destination || 6400 <= root)
			return false;
		return true;
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
}
	
package my_Taxi;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @OVERVIEW:
 * 这个类是为了存放地图信息创建的
 * 静态变量map用于存放指导书要求格式的地图
 * 静态变量distance用于存放邻接矩阵表示的两点之间的距离信息
 * 静态变量graph用于存放邻接矩阵表示的两点之间的连通信息
 * memmap用于存放最开始的地图，供开关路使用
 * initMatrix用于初始化graph
 * getPath用于返回两点之间的流量最小的最短路径
 * pointbfs用于计算两点之间的距离
 * SetRoadStatus用于开关路径使用，修改地图
 */
public class Map {
	static int [][] map = new int[Element.MapSize][Element.MapSize];
	static int [][] distance = new int[Element.MapSize*Element.MapSize][Element.MapSize * Element.MapSize];
	static int [][] graph = new int[Element.MapSize*Element.MapSize][Element.MapSize*Element.MapSize];// 邻接矩阵
	static int [][] memmap = new int [Element.MapSize][Element.MapSize];
	static int [][] memgraph = new int[Element.MapSize*Element.MapSize][Element.MapSize*Element.MapSize];// 邻接矩阵
	File file = new File("map.txt");
	BufferedReader reader = null;
	
	/**
	* 注释.......
		* @REQUIRES: this.map != null;
		* @MODIFIES : this.graph;
		* @EFFECTS : 
		*(this.map != null) ==> this.graph[][].update();
		*\all(int i == int j) ==> graph[i][j] == 0;
		*\all (map[i][j] == 1 || map[i][j] == 3) ==> (graph[i * 80 + j][i * 80 + j + 1] == 1 && graph[i * 80 + j + 1][i * 80 + j] == 1);
		*\all (map[i][j] == 2 || map[i][j] == 3) ==> (graph[i * 80 + j][(i + 1) * 80 + j] == 1 && graph[(i + 1) * 80 + j][i * 80 + j] == 1);
		*/
	public void initmatrix() {// 初始化邻接矩阵
		// Requires:无
		// Modifies:graph[][]
		// Effects:对邻接矩阵赋初值
		System.out.println("Init Begin");
		int MAXNUM = gv.MAXNUM;
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j){
					graph[i][j] = 0;
					memgraph[i][j] = 0;
				}
				else{
					graph[i][j] = MAXNUM;
					memgraph[i][j] = MAXNUM;
				}
			}
		}
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (map[i][j] == 1 || map[i][j] == 3) {
					graph[i * 80 + j][i * 80 + j + 1] = 1;
					graph[i * 80 + j + 1][i * 80 + j] = 1;
					memgraph[i * 80 + j][i * 80 + j + 1] = 1;
					memgraph[i * 80 + j + 1][i * 80 + j] = 1;
				}
				if (map[i][j] == 2 || map[i][j] == 3) {
					graph[i * 80 + j][(i + 1) * 80 + j] = 1;
					graph[(i + 1) * 80 + j][i * 80 + j] = 1;
					memgraph[i * 80 + j][(i + 1) * 80 + j] = 1;
					memgraph[(i + 1) * 80 + j][i * 80 + j] = 1;
				}
			}
		}
		System.out.println("Init Over");
	}
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400;
		* @MODIFIES : D[][],System.out;
		* @EFFECTS : 
		*对整个地图进行广度优先搜索，获得任意点到root之间的最短路信息，存储在每个点的Path中
		*/
	public static ArrayList<Integer> getPath(int root , int destination, int type){
		
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		Vector<node> queue = new Vector<node>();
		int [][]temp_graph;
		
		node nextn;
		node n = null;
		
		boolean[] view = new boolean[6405];
		node [] nodes = new node[6405];
		
		int x = root;// 开始进行单点bfs
		for (int i = 0; i < 6400; i++)
			view[i] = false;
		queue.add(nextn = new node(x));
		nodes[nextn.NO] = nextn;
		nextn.depth = 0;
		nextn.current = 0;
		nextn.path.add(x);
		
		//修改**************************
		if(type == Element.VIP_taxi)
			temp_graph = memgraph;
		else
			temp_graph = graph;
		
		
		while (queue.size() > 0) {
			n = queue.get(0);
			
			if(n.NO == destination)
				break;
			
			view[n.NO] = true;
			for (int i = 1; i <= 4; i++) {
				int next = n.NO + offset[i];
				if (next >= 0 && next < 6400 && temp_graph[n.NO][next] == 1) {
					
					if(view[next] == false){
						queue.add(nextn = new node(next));// 加入遍历队列
						nodes[nextn.NO] = nextn;
						view[next] = true;
					}
					else{
						nextn = nodes[next];
					}

					if(nextn.depth == n.depth + 1){
						//compare current
						if(n.current + FlowMap.flowmap[n.NO][nextn.NO] < nextn.current){
							nextn.current = n.current + FlowMap.flowmap[n.NO][nextn.NO];
							nextn.path = (ArrayList<Integer>) n.path.clone();
							nextn.path.add(nextn.NO);
						}
						else{
							//nothing
						}
					}
					else if(nextn.depth > n.depth + 1){
						nextn.depth = n.depth + 1;
						nextn.current = n.current + FlowMap.flowmap[n.NO][nextn.NO];
						nextn.path = (ArrayList<Integer>) n.path.clone();
						nextn.path.add(nextn.NO);
					}
					else{
						//nothing
					}
					
				}
			}
			queue.remove(0);// 退出队列
		}
		return n.path;
	}
	
	
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400 && 0 <= destination < 6400;
		* @MODIFIES : Map.distance;
		* @EFFECTS : 
		* Use BFS to get the distance between root and other points, which have been stored in the distance[][];
		*/
	public static void pointbfs(int root , int destination, int type) {// 单点广度优先搜索
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		Vector<node> queue = new Vector<node>();
		boolean[] view = new boolean[6405];
		
		int [][]temp_graph;
		if(type == Element.VIP_taxi)
			temp_graph = memgraph;
		else
			temp_graph = graph;
		
		for (int i = 0; i < 6400; i++) {
			for(int j = 1; j <= 4; j++){
				if(i + offset[j] >= 0 && i + offset[j] < 6400){
					distance[i][i + offset[j]] = temp_graph[i][i + offset[j]];
					distance[i + offset[j]][i] = temp_graph[i + offset[j]][i];
				}
			}
		}
		
		int x = root;// 开始进行单点bfs
		for (int i = 0; i < 6400; i++)
			view[i] = false;
		queue.add(new node(x, 0));
		while (queue.size() > 0) {
			
			node n = queue.get(0);
			view[n.NO] = true;
			for (int i = 1; i <= 4; i++) {
				int next = n.NO + offset[i];
				if (next >= 0 && next < 6400 && view[next] == false && graph[n.NO][next] == 1) {
					view[next] = true;
					queue.add(new node(next, n.depth + 1));// 加入遍历队列
					distance[x][next] = n.depth + 1;
					distance[next][x] = n.depth + 1;
				}
			}
			queue.remove(0);// 退出队列
			if(n.NO == destination){
				break;
			}
		}

	}

	/**
	* 注释.......
		* @REQUIRES: 0 <= x1 < 80, 0 <= x2 < 80, 0 <= y1 < 80, 0 <= y2 < 80;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == distance[PointA][PointB];
		*/
	public static int distance(int x1, int y1, int x2, int y2, int type) {// 使用bfs计算两点之间的距离
		pointbfs(x1 * 80 + y1 , x2 * 80 + y2, type);
		return distance[x1 * 80 + y1][x2 * 80 + y2];
	}
	/**
	* 注释.......
		* @REQUIRES: status == 0 || status == 1;
		* @MODIFIES : Map.map, Map.graph;
		* @EFFECTS : 
		* Update the information of the Road between Point1 and Point2;
		*/
	public static void SetRoadStatus(Point p1, Point p2, int status) {// status 0关闭 1打开
		if(!(p1.x >= 0 && p1.x < 80 && p2.x >=0 && p2.x <80 && p1.y >= 0 && p1.y < 80 && p2.y >=0 && p2.y <80)){
			System.out.println("SetRoad Error!");
			return;
		}
		synchronized(map){
			synchronized (graph) {
				int di = p1.x - p2.x;
				int dj = p1.y - p2.y;
				Point p = null;
				if (di == 0) {// 在同一水平线上
					if (dj == 1) {// p2-p1
						p = p2;
					} else if (dj == -1) {// p1-p2
						p = p1;
					} else {
						return;
					}
					if (status == 0) {// 关闭
						if (map[p.x][p.y] == 3) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = gv.MAXNUM;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = gv.MAXNUM;
							map[p.x][p.y] = 2;
						} else if (map[p.x][p.y] == 1) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = gv.MAXNUM;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = gv.MAXNUM;
							map[p.x][p.y] = 0;
						}
					} else if (status == 1) {// 打开
						if (map[p.x][p.y] == 2 && memmap[p.x][p.y] == 3) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = 1;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 3;
						}else if (map[p.x][p.y] == 0 && (memmap[p.x][p.y] == 1 || memmap[p.x][p.y] == 3)) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = 1;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 1;
						}
						else {
							System.out.println("Open Road Error!");;
						}
					}
				} else if (dj == 0) {// 在同一竖直线上
					if (di == 1) {// p2-p1
						p = p2;
					} else if (di == -1) {// p1-p2
						p = p1;
					} else {
						return;
					}
					if (status == 0) {// 关闭
						if (map[p.x][p.y] == 3) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = gv.MAXNUM;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = gv.MAXNUM;
							map[p.x][p.y] = 1;
						} else if (map[p.x][p.y] == 2) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = gv.MAXNUM;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = gv.MAXNUM;
							map[p.x][p.y] = 0;
						}
					} else if (status == 1) {// 打开
						if (map[p.x][p.y] == 1 && memmap[p.x][p.y] == 3) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = 1;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 3;
						} else if (map[p.x][p.y] == 0 && (memmap[p.x][p.y] == 3 || memmap[p.x][p.y] == 2)) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = 1;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 2;
						}
						else {
							System.out.println("Open Road Error!");;
						}
					}
				}
				return;
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* None;
		*/
	public boolean repOK(){
		if(map == null)
			return false;
		if(distance == null)
			return false;
		if(graph == null)
			return false;
		if(memmap == null)
			return false;
		if(file == null)
			return false;
		return true;
	}
	
}

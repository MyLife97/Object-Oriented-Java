package my_Taxi;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Map {
	static int [][] map = new int[Element.MapSize][Element.MapSize];
	static int [][] distance = new int[Element.MapSize*Element.MapSize][Element.MapSize * Element.MapSize];
	static int[][] graph = new int[Element.MapSize*Element.MapSize][Element.MapSize*Element.MapSize];// 邻接矩阵
	
	static int [] path;
	
	File file = new File("map.txt");
	BufferedReader reader = null;
	
	/*
	public void readFile(){
		int i;
		int line = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String input = null;
			while((input = reader.readLine()) != null){
				input = input.replaceAll(" ", "");
				input = input.replaceAll("\\t", "");
				
				if(input.length() != Element.MapSize){
					throw(new IOException());
				}
				for(i = 0; i < input.length(); i++){
					if((input.charAt(i) >= '0' && input.charAt(i) <= '3')){
						map[line][i] = input.charAt(i) - '0';
					}
					else{
						throw(new IOException());
					}
				}	
				line ++;
			}
			if(line != Element.MapSize){
				throw(new IOException());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("File IO Error!");
			System.exit(0);
		}
	}
	*/
	
	
	/**
	* 注释.......
		* @REQUIRES: this.map != null;
		* @MODIFIES : this.graph;
		* @EFFECTS : 
		*(this.map != null) ==> this.graph[][].update();
		*/
	public void initmatrix() {// 初始化邻接矩阵
		// Requires:无
		// Modifies:graph[][]
		// Effects:对邻接矩阵赋初值
		int MAXNUM = gv.MAXNUM;
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j)
					graph[i][j] = 0;
				else
					graph[i][j] = MAXNUM;
			}
		}
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (map[i][j] == 1 || map[i][j] == 3) {
					graph[i * 80 + j][i * 80 + j + 1] = 1;
					graph[i * 80 + j + 1][i * 80 + j] = 1;
				}
				if (map[i][j] == 2 || map[i][j] == 3) {
					graph[i * 80 + j][(i + 1) * 80 + j] = 1;
					graph[(i + 1) * 80 + j][i * 80 + j] = 1;
				}
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: 0 <= root < 6400 && 0 <= destination < 6400;
		* @MODIFIES : Map.distance;
		* @EFFECTS : 
		* Use BFS to get the distance between root and other points, which have been stored in the distance[][];
		*/
	public static void pointbfs(int root , int destination) {// 单点广度优先搜索
		// Requires:int类型的点号root
		// Modifies:distance[][],System.out
		// Effects:对整个地图进行广度优先搜索，获得任意点到root之间的最短路信息，储存在distance[][]中
		path = new int [6401];
		
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		Vector<node> queue = new Vector<node>(6405);
		boolean[] view = new boolean[6405];
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j) {
					distance[i][j] = 0;
				} else {
					distance[i][j] = graph[i][j];// 赋初值
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
					
					path[next] = n.NO;
					
					queue.add(new node(next, n.depth + 1));// 加入遍历队列
					distance[x][next] = n.depth + 1;
					distance[next][x] = n.depth + 1;
				}
			}
			queue.remove(0);// 退出队列
			if(n.NO == destination)
				break;
		}
	}
	

	/**
	* 注释.......
		* @REQUIRES: 0 <= x1 < 80, 0 <= x2 < 80, 0 <= y1 < 80, 0 <= y2 < 80;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == distance[PointA][PointB];
		*/
	public static int distance(int x1, int y1, int x2, int y2) {// 使用bfs计算两点之间的距离
		pointbfs(x1 * 80 + y1 , x2 * 80 + y2);
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
						if (map[p.x][p.y] == 2) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = 1;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 3;
						} else if (map[p.x][p.y] == 0) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 1] = 1;
							graph[p.x*80 + p.y + 1][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 1;
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
						if (map[p.x][p.y] == 1) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = 1;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 3;
						} else if (map[p.x][p.y] == 0) {
							graph[p.x*80 + p.y][p.x*80 + p.y + 80] = 1;
							graph[p.x*80 + p.y + 80][p.x*80 + p.y] = 1;
							map[p.x][p.y] = 2;
						}
					}
				}
				return;
			}
		}
	}
}

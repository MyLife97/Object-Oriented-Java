package stupid_Taxi;

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
	
	public static void main(String args[]){
		Map map = new Map();
		map.readFile();
		//map.distance_init();
	}
	
	
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

	public static void pointbfs(int root) {// 单点广度优先搜索
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
		}
		// 检测孤立点

//		if (count > 0) {
//			System.out.println("地图并不是连通的,程序退出");
//			System.exit(1);
//		}
	}
	
	synchronized public static ArrayList<Integer> getPath(int x1, int y1, int x2, int y2) {
		ArrayList<Integer> p = new ArrayList<Integer>();
		int p1 = x1 * 80 + y1,	p2 = x2 * 80 + y2;
		pointbfs(p1);
		int temp = p2;
		while (path[temp] != p1) {
			p.add(0, temp);
			temp = path[temp];
		}
		p.add(0, temp);
		p.add(0, p1);
		return p;
	}

	synchronized public static int distance(int x1, int y1, int x2, int y2) {// 使用bfs计算两点之间的距离
		pointbfs(x1 * 80 + y1);
		return distance[x1 * 80 + y1][x2 * 80 + y2];
	}
}

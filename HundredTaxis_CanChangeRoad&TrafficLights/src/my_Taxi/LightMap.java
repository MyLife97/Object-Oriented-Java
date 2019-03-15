package my_Taxi;

import java.awt.Point;
/**
 * @OVERVIEW:
 * 这个类的静态变量period用于存放一开始随机出来的红绿灯变换周期，取值范围500~1000全闭
 * waitTaxi用于存放正在等待红绿灯的Taxi数量
 * lightmap用于存放每个点的红绿灯情况
 * offset用于简化计算而已
 * 通过initLightMap()进行初始化之后，画出地图
 * change()改变所有红绿灯的朝向
 * run()开始红绿灯线程
 */
public class LightMap extends Thread{
	static long period;
	static int waitTaxi = 0;
	
	static int lightmap[]= new int [6400];
	int[] offset = new int[] { 0, 1, -1, 80, -80 };
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.period;
		* @EFFECTS :
		* this.period = (long)(Math.random() * 501) + 500;
		*/
	public LightMap(){
		period = (long)(Math.random() * 501) + 5000;
	}
	/**
	* 注释.......
		* @REQUIRES: this.lightmap != null;
		* @MODIFIES : this.lightmap;
		* @EFFECTS :
		* (\all int i; 0 <= i < 6400; this.lightmap[i] == 1) ==> lightmap[i] == 2;
		* (\all int i; 0 <= i < 6400; this.lightmap[i] == 2) ==> lightmap[i] == 1;
		*/
	public void change(){
		int i;
		
		for(i = 0; i < 6400; i++){
			if(lightmap[i] == 1){
				lightmap[i] = 2;
				TaxiGUI.SetLightStatus(new Point(i / 80 , i % 80), 2);
			}
			else if(lightmap[i] == 2){
				lightmap[i] = 1;
				TaxiGUI.SetLightStatus(new Point(i / 80 , i % 80), 1);
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: this.lightmap != null;
		* @MODIFIES : Gui;
		* @EFFECTS :
		* Draw Lights on Gui.
		*/
	public void initLightMap(){
		int i;
		for(i = 0; i < 6400; i++){
			if(lightmap[i] == 1){
				TaxiGUI.SetLightStatus(new Point(i / 80 , i % 80), 1);
			}
			else if(lightmap[i] == 2){
				TaxiGUI.SetLightStatus(new Point(i / 80 , i % 80), 2);
			}
		}
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this.waitTaxi;
		* @EFFECTS :
		* 用于唤醒所有等待红绿灯的车辆
		* @THREAD_REQUIRES: None;
		* @THREAD_EFFECTS: \locked(Begin.lightap);
		*/
	public void run(){
		while(true){
			sleep(period);
			if(waitTaxi != 0){
				synchronized (Begin.lightMap) {
					waitTaxi = 0;
					Begin.lightMap.notifyAll();
				}
			}
			System.out.println("Light Change");
			change();
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
		* (period < 500 || period > 1000) ==> \result == false;
		* (waitTaxi < 0 || waitTaxi > 100) ==> \result == false;
		* (lightmap == null) ==> \result == false;
		* (offset == null) ==> \result == false;
		* (Else == true) ==> \result == true;
		*/
	public boolean repOK(){
		if(period < 500 || period > 1000)
			return false;
		if(waitTaxi < 0 || waitTaxi > 100)
			return false;
		if(lightmap == null)
			return false;
		if(offset == null)
			return false;
		return true;
	}
	
	/**
	* 注释.......
		* @REQUIRES: 0 <= location < 6400;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result = this.lightmap[location];
		*/
	public int getLight(int location){
		return lightmap[location];
	}
	

}

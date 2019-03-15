package my_Taxi;


/**
 * @OVERVIEW:
 * 这是一个向量数据类型，根据两个点的xy坐标，做差得到一个向量
 * dir_x为所得向量的x坐标，dir_y为所得向量的y坐标
 * crossProduct(DirVector destination)：可以与其他向量求叉乘
 * dotProduct(DirVector destination)：可以与其他向量求点乘
 */
public class DirVector {
	private int root_x;
	private int root_y;
	private int des_x;
	private int des_y;
	
	int dir_x;
	int dir_y;
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : this;
		* @EFFECTS : 
		* this.root_x = root_x;
		* this.root_y = root_y;
		* this.des_x = des_x;
		* this.des_y = des_y;
		* this.dir_x = des_x - root_x;
		* this.dir_y = des_y - root_y;
		*/
	public DirVector(int root_x, int root_y, int des_x, int des_y){
		this.root_x = root_x;
		this.root_y = root_y;
		this.des_x = des_x;
		this.des_y = des_y;
		
		this.dir_x = des_x - root_x;
		this.dir_y = des_y - root_y;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == (this.dir_x * destination.dir_y - this.dir_y * destination.dir_x);
		*/
	//left+ right- other 0
	public int crossProduct(DirVector destination){
		return this.dir_x * destination.dir_y - this.dir_y * destination.dir_x;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* \result == (destination.dir_x * dir_x + destination.dir_y * dir_y);
		*/
	//head+ back- other 0
	public int dotProduct(DirVector destination){
		return destination.dir_x * dir_x + destination.dir_y * dir_y;
	}
	/**
	* 注释.......
		* @REQUIRES: None;
		* @MODIFIES : None;
		* @EFFECTS : 
		* (root_x >= 0 && root_x < 80 && root_y >= 0 && root_y < 80 && des_x >= 0 && des_x < 80 && des_y >= 0 && des_y < 80) ==> \result == true;
		* Else ==> \result == false;
		*/
	public boolean repOK(){
		if(root_x >= 0 && root_x < 80
			&& root_y >= 0 && root_y < 80
			&& des_x >= 0 && des_x < 80
			&& des_y >= 0 && des_y < 80)
			return true;
		else
			return false;
	}
}

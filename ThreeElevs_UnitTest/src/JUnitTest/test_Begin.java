package JUnitTest;


import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;  
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class test_Begin {

	@Test
	public void AtestRead(){
		String testString = "(FR,3,DOWN,0)\n" + 
							"(ER,1,1)\n" +
							"(FR,1,UP,1)\n" +
							"(FR,1,DOWN,0)\n" +
							"(FR,1,UP,0)\n" + 
							"(FR,10,UP,0)\n" +
							"(FR,1,DOWN,0)\n" +
							"(FR,10,DOWN,0)\n" +
							"(FR,100,UP,9999987654321)\n" +
							"(FR,100,UP,0)\n" +
							"(FR,+1,UP,+0)\n" +
							"(FR,0,UP,0)\n" +
							"(FR,5,DOWN,0)\n" +
							"(FR,5,DOWN,10)\n" +
							"(FR,1,UP,10)\n" +
							"(FR,5,UP,0)\n" +
							
							"(ER,1,1)\n" +
							"(ER,1,0)\n" +
							"(ER,1,0)\n" + 
							"(ER,10,0)\n" +
							"(ER,1,0)\n" +
							"(ER,10,0)\n" +
							"(ER,100,9999987654321)\n" +
							"(ER,100,0)\n" +
							"(ER,+1,+0)\n" +
							"(ER,0,0)\n" +
							"(ER,5,0)\n" +
							"(ER,5,10)\n" +
							"(ER,1,10)\n" +
							"(ER,5,0)\n" +
							
							"()\n" +
							"(ER,5,100)\n" +
							"(ER,5,110)\n" +
							"(ER,5,120)\n" +
							"(ER,5,130)\n" +
							"(ER,5,140)\n" +
							"(ER,5,150)\n" +
							"(ER,5,160)\n" +
							"(ER,5,170)\n" +
							"(ER,5,180)\n" +
							"(ER,5,190)\n" +
							"(ER,5,200)\n" +
							"(ER,5,210)\n" +
							"(ER,5,220)\n" +
							"(ER,5,230)\n" +
							"(ER,5,240)\n" +
							"(ER,5,250)\n" +
							"(ER,5,260)\n" +
							"(ER,5,270)\n" +
							"(ER,5,280)\n" +
							"(ER,5,290)\n" +
							"(ER,5,300)\n" +
							"(ER,5,310)\n" +
							"(ER,5,320)\n" +
							"(ER,5,330)\n" +
							"(ER,5,340)\n" +
							"(ER,5,350)\n" +
							"(ER,5,360)\n" +
							"(ER,5,370)\n" +
							"(ER,5,380)\n" +
							"(ER,5,390)\n" +
							"(ER,5,400)\n" +
							"(ER,5,410)\n" +
							"(ER,5,420)\n" +
							"(ER,5,430)\n" +
							"(ER,5,440)\n" +
							"(ER,5,450)\n" +
							"(ER,5,460)\n" +
							"(ER,5,470)\n" +
							"(ER,5,480)\n" +
							"(ER,5,490)\n" +
							"(ER,5,500)\n" +
							"(ER,5,510)\n" +
							"(ER,5,520)\n" +
							"(ER,5,530)\n" +
							"(ER,5,540)\n" +
							"(ER,5,550)\n" +
							"(ER,5,560)\n" +
							"(ER,5,570)\n" +
							"(ER,5,580)\n" +
							"(ER,5,590)\n" +
							"(ER,5,600)\n" +
							"(ER,5,610)\n" +
							"(ER,5,620)\n" +
							"(ER,5,630)\n" +
							"(ER,5,640)\n" +
							"(ER,5,650)\n" +
							"(ER,5,660)\n" +
							"(ER,5,670)\n" +
							"(ER,5,680)\n" +
							"(ER,5,690)\n" +
							"(ER,5,700)\n" +
							"(ER,5,710)\n" +
							"(ER,5,720)\n" +
							"(ER,5,730)\n" +
							"(ER,5,740)\n" +
							"(ER,5,750)\n" +
							"(ER,5,760)\n" +
							"(ER,5,770)\n" +
							"(ER,5,780)\n" +
							"(ER,5,790)\n" +
							"(ER,5,800)\n" +
							"(ER,5,810)\n" +
							"(ER,5,820)\n" +
							"(ER,5,830)\n" +
							"(ER,5,840)\n" +
							"(ER,5,850)\n" +
							"(ER,5,860)\n" +
							"(ER,5,870)\n" +
							"(ER,5,880)\n" +
							"(ER,5,890)\n" +
							"(ER,5,900)\n" +
							"(ER,5,910)\n" +
							"(ER,5,920)\n" +
							"(ER,5,930)\n" +
							"(ER,5,940)\n" +
							"(ER,5,950)\n" +
							"(ER,5,960)\n" +
							"(ER,5,970)\n" +
							"(ER,5,980)\n" +
							"(ER,5,990)\n" +
							"(ER,5,1000)\n" +
							"(ER,5,1010)\n" +
							"(ER,5,1020)\n" +
							"(ER,5,1030)\n" +
							"(ER,5,1040)\n" +
							"(ER,5,1050)\n" +
							"(ER,5,1060)\n" +
							"(ER,5,1070)\n" +
							"(ER,5,1080)\n" +
							"(ER,5,1090)\n" +
							"()\n" +
							"RUN\n";
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(testString.getBytes());
		System.setIn(inputStream);
		
		Elev.Begin.read(Elev.Begin.requestList);
	}
	
    
	
}
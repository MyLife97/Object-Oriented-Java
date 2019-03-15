package stupid_Taxi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Output {
	public static void outFile(){
		int i;
		int j;
		int number;
		Request request;
		Writer writer ;
		BufferedWriter bufferedWriter = null;
		String status;
		
		try {
			writer = new FileWriter("result.txt");
			bufferedWriter = new BufferedWriter(writer);
		} catch (IOException e) {
			
		}
		number = Begin.requestList.getLength();
		for(i = 0; i < number; i++){
			request = Begin.requestList.getRequest(i);
			if(request.isPicked == false){
				try {
					bufferedWriter.write(String.format("Systime:%s Request:%s Not Picked\n", request.systime , request.self));
					bufferedWriter.flush();				
				} catch (IOException e) {

				}
			}
			else{
				try {
					bufferedWriter.write(String.format("Request:%s\n", request.self));
						bufferedWriter.flush();						
					bufferedWriter.write(String.format("  >>  Systime:%s Departure:(%d,%d) Destination:(%d,%d) TaxiNumber:%d\n", request.systime ,
						request.x_departure, request.y_departure , request.x_destination, request.y_destination, request.taxi_number));
					bufferedWriter.flush();	

					bufferedWriter.write(String.format("WillingDriver:\n"));
					bufferedWriter.flush();		
					for(j = 0; j <request.taxis.size(); j++){
						if(request.taxis.get(j).status == Element.Waiting)
							status = "Waiting";
						else if(request.taxis.get(j).status == Element.Serving){
							status = "Serving";
						}
						else if(request.taxis.get(j).status == Element.Picking){
							status = "Picking";
						}
						else{
							status = "Stop";
						}
						bufferedWriter.write(String.format("  >>  TaxiNumber:%d TaxiPosition:(%d,%d) TaxiStatus:%s TaxiReputation:%d\n",
								request.taxis.get(j).number , request.taxis.get(j).x, request.taxis.get(j).y, status, request.taxis.get(j).reputation));
						bufferedWriter.flush();	
					}
					
					bufferedWriter.write(String.format("PickRoute:\n"));
					bufferedWriter.flush();						
					for(j = 0; j < request.pickRoute.size(); j++){
						bufferedWriter.write(String.format("  >>  Systime:%s Position:(%d,%d)\n", request.pickRoute.get(j).systime ,
								request.pickRoute.get(j).x , request.pickRoute.get(j).y));
						bufferedWriter.flush();	
					}
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
			
		}
		
	}
}

package utils;

import java.util.HashMap;

public class Tme {
	public static void main(String[] args) throws InterruptedException {
		time("yolo");
		Thread.sleep(500);
		time("yolo");
	}

	
	private static HashMap<String,Long> timemap = new HashMap<>();
	public static void time(String in){
		if(timemap.containsKey(in)){
			long time = System.nanoTime()-timemap.get(in);
			double tim = time/1e9;
			System.out.println("Time " + in + " took " + tim + "s");
			timemap.remove(in);
		}
		else{
			timemap.put(in, System.nanoTime());
		}
	}
}

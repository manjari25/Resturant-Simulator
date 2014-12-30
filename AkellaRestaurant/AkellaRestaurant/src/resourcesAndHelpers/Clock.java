package resourcesAndHelpers;

//Singleton class to implement logical clock to be used by all threads
public class Clock {
	private static Clock clock = null;
	private int time; 
	
	//Initialize time to 0
	private Clock() {
		time = 0;
	}
	
	//Get instance of clock
	public static Clock getInstance() {
		if(clock == null) {
			clock = new Clock();
		}
		return clock;
	}
	
	//Get clock time
	public int getTime() {
		return time;
	}
		
	//Increment clock time
	public void incrementTime() {
		try {
			Thread.sleep(50);
		} catch(InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
		time++;
	}
}

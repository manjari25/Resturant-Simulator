package resourcesAndHelpers;

//Implements the Resource interface and is inherited by burger machine, fries machine and soda machine
public class Machine implements Resource {
	public int timeNeeded; //time required to use the machine 
	public boolean isOccupied; //Is it occupied?
	private String name; //name of the machine
	
	private OutputHelper oh; //output helper class
	
	//initialize the name and status of the machine
	public Machine(String name) {
		this.name = name;
		isOccupied = false;
		oh  = OutputHelper.getInstance();
	}
	
	//get the name of the machine
	public String getName() {
		return name;
	}

	//cook the order on the machine
	public void cook(Order order, Cook cook) {
		int timeToCook = timeNeeded;
		int numOfItems = -1;
		String item = "";
		//Check what to cook
		switch(name) {
		case "burger machine":
			timeToCook = order.numBurgers * timeNeeded;
			numOfItems = order.numBurgers;
			item = " burgers ";
			break;
		case "fries machine":
			timeToCook = order.numFries * timeNeeded;
			numOfItems = order.numFries;
			item = " fries ";
			break;
		case "soda machine":
			timeToCook = order.numCokes * timeNeeded;
			numOfItems = order.numCokes;
			item = " cokes ";
			break;
		}
		int startTime = Clock.getInstance().getTime();
		oh.writeToFile("Time " + Integer.toString(startTime) + ": " + Thread.currentThread().getName() + " is using " + name + " for time " + timeToCook + " minutes. " + Integer.toString(numOfItems) + item + "to make.");
		//Cook the food
		while(Clock.getInstance().getTime() < startTime + timeToCook) {
			try {
				synchronized(Clock.getInstance()) {
					Clock.getInstance().wait();
				}
			} catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
		//Finished cooking, release the machine
		switch(name) {
		case "burger machine":
				this.isOccupied = false;
				order.burgersReady = true;
			break;
		case "fries machine":
				this.isOccupied = false;
				order.friesReady = true;
			break;
		case "soda machine":
				this.isOccupied = false;
				order.cokeReady = true;
			break;
		}
		oh.writeToFile("Time " + Integer.toString(Clock.getInstance().getTime()) + ": " + Thread.currentThread().getName() + " releases " + name + ".");
	}
	
	//Occupy the machine
	synchronized public void occupy() {
		isOccupied = true;
	}
	
	//Check if machine is occupied
	synchronized public boolean isOccupied() {
		return isOccupied;
	}
}
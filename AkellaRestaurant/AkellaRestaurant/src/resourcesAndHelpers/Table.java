package resourcesAndHelpers;

//Implements the resource class
public class Table implements Resource {

	public int id; // Table ID
	private Order order; //Order at this table
	public Cook cook; //Cook assigned to this table
	public Diner diner; //Diner sitting at this table
	public boolean isOccupied; //Is table occupied? 
	public boolean cookAssigned; //Is Cook assigned to table
	public boolean foodServed; //Is food served?
	public int timeFoodServed; //time order is complete and food is brought to table
	
	private OutputHelper oh; //output helper class object
	
	//Initialize the table
	public Table(int id) {
		this.id = id;
		order = null;
		isOccupied = false;	
		cookAssigned = false;
		foodServed = false;			
		oh = OutputHelper.getInstance();
	}

	//Release the table
	public void vacate() {
		isOccupied = false;
		cookAssigned = false;
		foodServed = false;
		order = null;
		oh.writeToFile("Time " + Integer.toString(Clock.getInstance().getTime()) + ": " + Thread.currentThread().getName() + " leaves the resturant.");
	}

	//Set table order
	public synchronized void setOrder(Order order) {
		this.order = order;
		notifyAll();
	}

	//Get table order
	public Order getOrder() {
		return order;
	}

	//Wait for cook to be assigned to the table
	public synchronized void waitForCookAssigment() {
		try {
			while(!cookAssigned)
				wait();
		} catch(InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	//Assign cook to table
	public synchronized void assignCook(Cook cook) {
		cookAssigned = true;
		this.cook = cook;
		notifyAll();
	}
	
	//Wait for table to give order
	public synchronized void waitForOrder() {
		try {
			while(order == null)
				wait();
		} catch(InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	//Wait for food to be served
	public synchronized void waitForFoodServed() {
		try {
			while(!foodServed)
				wait();
		} catch(InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	//Serve food at table
	public synchronized void serveFood() {
		foodServed = true;
		timeFoodServed = Clock.getInstance().getTime();
		oh.writeToFile("Time " + Integer.toString(timeFoodServed) +": Cook " + Integer.toString(this.cook.getId()) + " completes order of Diner " + Integer.toString(this.diner.getId()) + ".");
		notifyAll();
	}
}

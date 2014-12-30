package resourcesAndHelpers;

//Thread for Diner
public class Diner implements Runnable{
	
	private int id; //Diner ID	
	private int timeArrived; // time of arrival of the dinner at the restaurant
	private int timeSeated;// time of seating of the dinner at the restaurant
	private int timeStartedEating; //time they start eating
	private Order order; //Order of the diner
	private Cook cook; //Cook assigned to the diner
	private Table tableSeatedIn; //Table ID in which they are seated 
	private boolean isInRestaurant; // Is the diner in the restaurant?
	
	private Thread t; // diner thread
	private OutputHelper oh; //Output helper object
	
	//Initialize variables and create instance of thread
	public Diner(int a, Order o, int i) {
		id = i;
		timeArrived = a;
		timeSeated = -1;
		order = o;
		isInRestaurant = false;
		t = new Thread(this, "Diner "+id);
		oh = OutputHelper.getInstance();
	}
	
	//Get Diner ID
	public int getId() {
		return id;
	}

	//Get time of arrival
	public int getTimeOfArrival() {
		return timeArrived;
	}

	//get time of seating
	public int getTimeSeated() {
		return timeSeated;
	}

	//get diner order
	public Order getOrder() {
		return order;
	}
	
	//check of diner is in restaurant
	public boolean isInRestaurant() {
		return isInRestaurant;
	}
	
	//Start the thread for a diner when he arrives
	public void dinerArrives() {
		isInRestaurant = true;
		t.start();
	}
	
	//Implement the run function of the thread
	public void run() {
		oh.writeToFile("Time " + Integer.toString(timeArrived) + ": " + Thread.currentThread().getName() + " arrives at the restaurant.");
		//Compete for table resource
		tableSeatedIn = Tables.getInstance().getTableForDiner(this);
		timeSeated = Clock.getInstance().getTime();
		oh.writeToFile("Time " + Integer.toString(timeSeated) + ": " + Thread.currentThread().getName() + " gets table " + tableSeatedIn.id + ".");
		//Set the order once seated
		tableSeatedIn.setOrder(this.order);
		oh.writeToFile("Time " + Integer.toString(timeSeated) + ": " + Thread.currentThread().getName() + " orders " + this.order.numBurgers + " burgers, " + this.order.numFries + " fries, " + this.order.numCokes + " cokes." );
		//Assign cook to diner
		tableSeatedIn.waitForCookAssigment();
		cook = tableSeatedIn.cook;
		oh.writeToFile("Time " + Integer.toString(Clock.getInstance().getTime()) + ": Cook " +  cook.getId() + " gets order from " + Thread.currentThread().getName() + ".");
		//Wait for order to be finished preparing
		tableSeatedIn.waitForFoodServed();
		//Start eating
		timeStartedEating = Clock.getInstance().getTime();
		oh.writeToFile("Time " + Integer.toString(timeStartedEating) + ": " + Thread.currentThread().getName() + " starts eating.");
		//Eat for 30 minutes
		while (Clock.getInstance().getTime() < timeStartedEating + 30) {
			try {
				synchronized(Clock.getInstance()) {
					Clock.getInstance().wait();
				}
			} catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
		//Done eating, release table
		Tables.getInstance().vacateTable(tableSeatedIn.id);
		//Leave the table
		this.leaveRestaurant();		
	}
	
	//Diner leaves the restaurant
	public void leaveRestaurant() {
		isInRestaurant = false;
		synchronized(Diners.getInstance()) {
			Diners.getInstance().leaveRestaurant();}
		synchronized(Tables.getInstance()) {
			Tables.getInstance().notifyAll(); }
	}
}
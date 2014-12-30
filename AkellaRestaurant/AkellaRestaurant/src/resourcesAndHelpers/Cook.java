package resourcesAndHelpers;

// Thread for cook
public class Cook implements Runnable {

	private int id; // Cook ID
	private Table tableServing; //table at which cook is serving
	private Order order; //Order cook is handling
	private Thread t; // cook thread
		
	//initialize cook ID and create a new thread for cook
	public Cook(int i) {
		id = i;
		t = new Thread(this, "Cook "+id);
		t.start();
	}
	
	//get Cook ID	
	public int getId() {
		return id;
	}

	//Implement the run function for the thread
	public void run() {
		while(Clock.getInstance().getTime() <= 120 || Diners.getInstance().getNumCurrentDiners() > 0) {
				//Find table for cook
				tableServing = Tables.getInstance().getTableForCook();
				if(tableServing != null) {
					//assign cook to table
					tableServing.assignCook(this);
					//Wait for table to give order
					tableServing.waitForOrder();
					//set order to be processed
					order = tableServing.getOrder();
					Machines machines = Machines.getInstance();
					//compete for machine to cook the order 
					while(!order.isComplete()) {
						Machine machine = machines.getMachine(order);
						machine.cook(order, this);
					}
					//Serve food 
					tableServing.serveFood();
				}
		}
	}
}

package resourcesAndHelpers;

//Initializes and synchronizes table class
public class Tables {
	private Table[] tables;
	private static Tables instance = null;
	private OutputHelper oh;
	
	private Tables() {
		oh = OutputHelper.getInstance();
	}
	
	//Initialize the tables
	public void initialize(int numTables) {
		if(tables == null) {
			tables = new Table[numTables];
			for(int i=0; i<tables.length; i++) {
				tables[i] = new Table(i);
			}
		}
	}
	
	//Retrieve an empty table for a waiting diner
	synchronized public Table getTableForDiner(Diner d) {
		boolean flag = false;
		int index = -1;
		while(index == -1) {
			//check to see which diner is next in line
			if(Diners.getInstance().isNext(d.getId())) {
				//find table which isn't occupied
				for(int i=0; i<tables.length; i++) {
					if(!tables[i].isOccupied) {
						flag = true;
						index = i;
						break;
					}
				}
			if(!flag)
				oh.writeToFile("Time " + Integer.toString(Clock.getInstance().getTime()) + ": " + Thread.currentThread().getName() + " is waiting for a table.");
			}
			try {
				if(index == -1)
					wait();
			} catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
		tables[index].isOccupied = true;
		tables[index].diner = d;
		notifyAll();
		return tables[index]; 
	}
	
	//Retrieve a table for a cook
	synchronized public Table getTableForCook() {
		int index = -1;
		while(index == -1) {
			//check to see if table is unassigned and is not occupied
			for(int i=0; i<tables.length; i++) {
				if(tables[i].isOccupied && !tables[i].cookAssigned) {
					index = i;
				}
			}
			//if no such table found, check to see the number of current diners
			if(index == -1) {
				if(Diners.getInstance().getNumCurrentDiners() == 0)
					break;
				try {
					wait();
				} catch(InterruptedException ie) {
					System.out.println(ie.getMessage());
				}
			}
		}
		if(index == -1)
			return null;
		tables[index].cookAssigned = true;
		notifyAll();
		return tables[index]; 
	}
		
	//Release table with given index
	synchronized public void vacateTable(int index) {
		tables[index].vacate();
	}
	
	//Get instance of current class
	public static Tables getInstance() {
		if (instance == null)
		{
			synchronized(Tables.class) {
				if(instance == null)
					instance = new Tables();
		    }
		}
		return instance;
	}
}
package resourcesAndHelpers;

//Initializes the diner class and synchronizes it
public class Diners {
	private static Diners instance = null;
	private Diner[] diners;
	private int numOfCurrentDiners; //current number of diners in the restaurant
	
	//Initialize current diners to 0
	private Diners() {
		numOfCurrentDiners = 0;
	}
	
	//get number of diners in restaurant
	public int getNumCurrentDiners() {
		return numOfCurrentDiners;
	}
	
	//initialize the diners
	public void initialize(int numDiners) {
		diners = new Diner[numDiners];
	}

	//Get specific diner
	public Diner get(int index) {
		if(index < diners.length)
			return diners[index];
		else
			return null;
	}
	
	//Set specific diner
	public void set(int index, Diner d) {
		diners[index] = d;
	}

	//get total number of diners
	public int getNumOfDiners() {
		return diners.length;
	}

	//Check if diner having id is next in line (is earliest among the diners left to process)
	public boolean isNext(int id) {
		for(int i=0; i<diners.length; i++) {
			if(diners[i].isInRestaurant() && diners[i].getTimeSeated() == -1) {
				if(id == i)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	//Process each diner by starting a thread(invoke Diner.dinerArrive()) for each 
	public void processDinersNow() {
		for(int i=0; i<diners.length; i++) {
			if(diners[i].getTimeOfArrival() == Clock.getInstance().getTime()) {
				diners[i].dinerArrives();
				numOfCurrentDiners++;
			}
		}
	}
	
	//Diner leaves restaurant
	public void leaveRestaurant() {
		this.numOfCurrentDiners--;
	}
	
	//Get instance of this class
	public static Diners getInstance() {
		if(instance == null) {
			instance = new Diners();
		}
		return instance;
	}
}

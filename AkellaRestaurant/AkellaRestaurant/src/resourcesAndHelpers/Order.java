package resourcesAndHelpers;

//Class for order of a diner
public class Order {
	
	public int numBurgers; //number of burgers
	public int numFries; //number of fries
	public int numCokes; //number of cokes
	public boolean burgersReady; // Are burgers ready?
	public boolean friesReady; //Are fries ready?
	public boolean cokeReady; //Is coke ready?
	
	//Initialize the variables
	public Order(int b, int f, int c) {
		numBurgers = b;
		numFries = f;
		numCokes = c;
		
		burgersReady = false;
		
		if(numFries > 0)
			friesReady = false;
		else
			friesReady = true;
		
		if(numCokes > 0) 
			cokeReady = false;
		else
			cokeReady = true;
	}

	//Returns true if order is complete
	public boolean isComplete() {
		if(burgersReady && friesReady && cokeReady)
			return true;
		else
			return false;
	}
}

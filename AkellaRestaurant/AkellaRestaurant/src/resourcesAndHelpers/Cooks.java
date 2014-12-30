package resourcesAndHelpers;

//Initializes the cook class and synchronizes it
public class Cooks {
	private static Cooks instance = null;
	private Cook[] cooks;
	
	private Cooks() {
	}
	
	//Initialize cooks
	public void initialize(int numCooks) {
		cooks = new Cook[numCooks];
		for (int i = 0; i < cooks.length; i++) {
			cooks[i] = new Cook(i);
		}
	}

	//Return number of cooks
	public int getNumCooks() {
		return cooks.length;
	}
	
	//Set cooks
	public void set(int index, Cook c) {
		cooks[index] = c;
	}
	
	//Get instance of this class
	public static Cooks getInstance() {
		if (instance == null)
		{
			synchronized(Cooks.class) {  
				if(instance == null)          
					instance = new Cooks();  
		    }
		}
		return instance;
	}
}

package resourcesAndHelpers;

// Initializes and synchronizes the access to machine class
public class Machines {
	private static Machines instance = null;
	private BurgerMachine bm;
	private FriesMachine fm;
	private SodaMachine sm;
	
	//Initialize the machines
	private Machines() {
		bm = new BurgerMachine(5);
		fm = new FriesMachine(3);
		sm = new SodaMachine(1);
	}
	
	//Get instance of class
	public static Machines getInstance() {
		if(instance == null) {
			instance = new Machines();
		}
		return instance;
	}
	
	//Get machine while order is still not processed
	public Machine getMachine(Order order) {
		while(Clock.getInstance().getTime() <=120 || Diners.getInstance().getNumCurrentDiners() > 0) {
			//Try to get burger machine if burgers left to process
			if(!order.burgersReady) {
				if(!bm.isOccupied()) {
					synchronized(bm) {
						if(!bm.isOccupied()) {	
							bm.occupy();
							return bm;
						}
					}
				}
			}
			//Try to get fries machine if fries left to process
			if(!order.friesReady) {
				if(!fm.isOccupied()) {
					synchronized(fm) {
						if(!fm.isOccupied()) {
							fm.occupy();
							return fm;
						}
					}
				}
			}
			//Try to get soda machine if cokes left to process
			if(!order.cokeReady) {
				if(!sm.isOccupied()) {
					synchronized(fm) {
						if(!sm.isOccupied()) {	
							sm.occupy();
							return sm;
						}
					}
				}
			}
		}	
		return null;
	}
}

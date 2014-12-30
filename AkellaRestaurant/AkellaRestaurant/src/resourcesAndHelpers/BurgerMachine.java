package resourcesAndHelpers;

//Define burger machine
public class BurgerMachine extends Machine {
	//constructor to define name and time machine is required for.
	public BurgerMachine(int t) {
		super("burger machine");
		super.timeNeeded = t;
	}
}

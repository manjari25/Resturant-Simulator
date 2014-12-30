package resourcesAndHelpers;

//define the soda machine
public class SodaMachine extends Machine {
	//constructor to define name and time machine is required for.
	public SodaMachine(int t) {
		super("soda machine");
		super.timeNeeded = t;
	}
}

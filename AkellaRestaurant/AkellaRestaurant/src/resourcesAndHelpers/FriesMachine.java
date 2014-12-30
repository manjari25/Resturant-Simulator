package resourcesAndHelpers;

//define fries machine
public class FriesMachine extends Machine {
	//constructor to define name and time machine is required for.
	public FriesMachine(int t) {
		super("fries machine");
		super.timeNeeded = t;
	}
}

package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import resourcesAndHelpers.*;

//Class containing the main method definition to simulate the restaurant 
public class Restaurant6431 {
	
	//Objects of classes made
	private Clock clock;
	private Diners diners;	
	private Cooks cooks;
	private Tables tables;
	private OutputHelper oh;	
	
	//Constructor initializing diners, cooks, tables and the output file
	Restaurant6431(int d, int c, int t, String filename) {
		oh = OutputHelper.getInstance(filename);
		clock = Clock.getInstance();
		diners = Diners.getInstance();
		tables = Tables.getInstance();		
		cooks = Cooks.getInstance();
		tables.initialize(t);
		diners.initialize(d);
		cooks.initialize(c);
	}
	
	//Function which opens restaurant and increments clock to perform various events, synchronized clock
	private void openRestaurant() {	
		while(clock.getTime() <=120 || Diners.getInstance().getNumCurrentDiners() > 0) {
			diners.processDinersNow();
			clock.incrementTime(); 
			synchronized(clock) {
				clock.notifyAll();
			}
		}
	}

	//Write output to the output file
	private void writeOutput(String message){
		oh.writeToFile(message);
	}
	
	//Main function
	public static void main(String args[]) {
			//Check for validity of call
			if (args.length < 1) {
			System.out.println("Incorrect call. Way to call : " + "java -jar AkellaRestaurant <InputFilName>");
			System.exit(0);
		}
		
		try {
			//Time program run
			long startTime = System.currentTimeMillis();
			//Read in input file
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));			
			String line = reader.readLine();
			int numDiners = Integer.parseInt(line.trim());			
			line = reader.readLine();
			int numTables = Integer.parseInt(line.trim());			
			line = reader.readLine();
			int numCooks = Integer.parseInt(line.trim());
			
			//Set up the restaurant after reading parameters from the input file
			Restaurant6431 restaurant = new Restaurant6431(numDiners, numCooks, numTables, ("Output"+args[0]));
			
			//Print first line to output file
			String firstLine = Integer.toString(numDiners) + " diners, " + Integer.toString(numTables)+ " tables, " + Integer.toString(numCooks) + " cooks." ;
			restaurant.writeOutput(firstLine);
			
			// For each diner, prepare order, set up diner
			int i = 0;
			while (((line = reader.readLine()) != null) && (i < numDiners)) {
				String in[] = line.split("\\s+");
				int timeDinerArrives = Integer.parseInt(in[0].trim());
				//in[1-end] contains order information
				Order o = new Order(Integer.parseInt(in[1].trim()), Integer.parseInt(in[2].trim()), Integer.parseInt(in[3].trim()));
				restaurant.diners.set(i, new Diner(timeDinerArrives,o,i));
				i++;
			}
			
			//Close input file
			reader.close();
			
			//Handle the diner orders and server them
			restaurant.openRestaurant();
			
			long endTime = System.currentTimeMillis();			
			//Print total time to run the simulation to the output file
			String lastLine = "Total cost time: " + Long.toString((endTime-startTime)/1000)+"s.";
			restaurant.writeOutput(lastLine);
		}
		//Handle IO/File not found exceptions
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.exit(0);
		}
	}
}
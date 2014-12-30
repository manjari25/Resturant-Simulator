package resourcesAndHelpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Singleton class used to write output to output file
public class OutputHelper {
	private static BufferedWriter outputWriter;
	private static OutputHelper outputHelperInstance;
	
	//Empty constructor
	private OutputHelper() {
	}
		
	//Open output file
	private OutputHelper(String filename) {
		try{
			outputWriter = new BufferedWriter(new FileWriter(filename, false));
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	//Get instance of this class using filename (First call)
	public synchronized static OutputHelper getInstance(String filename) {
		if(outputHelperInstance == null) {
			outputHelperInstance = new OutputHelper(filename);
		}
		return outputHelperInstance;
	}
	
	//Get instance of this class
	public synchronized static OutputHelper getInstance() {
		if(outputHelperInstance == null) {
			outputHelperInstance = new OutputHelper();
		}
		return outputHelperInstance;
	}
	
	//Write message to file
	public synchronized void writeToFile(String message) {
		try {
			System.out.println(message);
			outputWriter.write(message);
			outputWriter.newLine();
			outputWriter.flush();
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	//Flush the buffer
	public void flush() {
		try {
			outputWriter.flush();
		} catch(IOException ioe) {
			System.out.println("ERROR: Could not flush buffer.");
		}
	}
	
	//Close the buffer
	public void close() {
		try {
			outputWriter.close();
		} catch(IOException ioe) {
			System.out.println("ERROR: Could not close.");
		}
	}
}
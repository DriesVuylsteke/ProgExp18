package com.migapro.tester;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {

	public String logSudoku = "logSudoku.txt";
	public String logFile = "log.txt";
	public int numberOfSpaces = 10;
	
	
	public void log(String log){
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))){
			writer.write(log);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sudokuLog(String sudokuNumber, String log){
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("sudoku" + sudokuNumber + ".txt", true))){
			writer.write(log);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logFile(ArrayList<String> logger) {
		for (String line : logger) {
			log(line);
		}
	}
	
	public void logAvgRun(String version, int sudokuNumber, int iterations, long avg, long max, long min){
		
		String maxString = String.format("%," + numberOfSpaces + "d", max / 1000);
		String minString = String.format("%," + numberOfSpaces + "d", min / 1000);
		String avgString = String.format("%," + numberOfSpaces + "d", avg / 1000);
		String iterString = String.format("%04d", iterations);
		String sudString = String.format("%02d", sudokuNumber + 1);
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		
		String logString = timeStamp + "  -  Sudoku Solver s" + sudString + " i" + iterString + "  ||  avg = " +  avgString +
				"µs  |  max = " + maxString + "µs  |  min = " + minString + "µs";
		log(logString);
		sudokuLog(sudString, "V" + version + " --> " + logString);
	}
	
	private String getComputerDetails(){
		String nameOS= "Name => " + System.getProperty("os.name");
        String osType= "Type => " + System.getProperty("os.arch");
        String osVersion= "OS Version => " + System.getProperty("os.version");
         
        String processor = "Processor => " + System.getenv("PROCESSOR_IDENTIFIER");
        String architecture = "Architecture => " + System.getenv("PROCESSOR_ARCHITECTURE");
        
        /* Total number of processors or cores available to the JVM */
	    String cores = "Available processors (cores): " + 
	        Runtime.getRuntime().availableProcessors();
	 
	    /* Total amount of free memory available to the JVM */
	    String free = "Free memory (bytes): " + 
	        Runtime.getRuntime().freeMemory();
	 
	    /* This will return Long.MAX_VALUE if there is no preset limit */
	    long maxMemory = Runtime.getRuntime().maxMemory();
	    /* Maximum amount of memory the JVM will attempt to use */
	    String max = "Maximum memory (bytes): " + 
	        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory);
	 
	    /* Total memory currently in use by the JVM */
	    String total = "Total memory (bytes): " + 
	        Runtime.getRuntime().totalMemory();
		return nameOS + "\n" + osType + "\n" + osVersion + "\n" + processor + "\n" + architecture + "\n" + 
	        	cores + "\n" + free + "\n" + max + "\n" + total;
        
	}
	
	public void logSudokus(ArrayList<Sudoku> sudokus){
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(logSudoku, false))){
			for (int i = 0; i < sudokus.size(); i++) {
				writer.write("sudoku " + (i+1) + " - " + sudokus.get(i).countFilledNumbers() + " cells filled");
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startTest(String version) {
		log("<======================================================== INFO ========================================================>");
		log(getComputerDetails());
		log("Solver Version => " + version);
		log("<======================================================== DATA ========================================================>");
		
	}
	
	public void endTest(){
		log("<======================================================================================================================>");
		log("");
	}
	
	
}

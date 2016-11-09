package com.migapro.tester;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LoggerThread extends Thread{

	private static final LoggerThread instance = new LoggerThread();
	private BlockingQueue<LogValues> itemsToLog = new ArrayBlockingQueue<LogValues>(100);
	private static final String SHUTDOWN_REQ = "SHUTDOWN";
	private volatile boolean shuttingDown, loggerTerminated;
	
	private Logger logger;
	
	public static LoggerThread getLogger() {
		return instance;
	}
	private LoggerThread() {
		logger = new Logger();
		start();
	}
	
	public void run() {
		try {
			LogValues item;
			while ((item = itemsToLog.take()).SolverVersion != SHUTDOWN_REQ) {
				logger.logAvgRun(item);
			}
	    } catch (InterruptedException iex) {
	    } finally {
	    	loggerTerminated = true;
	    }	
	}
	
	public void log(LogValues values) {
	    if (shuttingDown || loggerTerminated) return;
	    try {
	    	itemsToLog.put(values);
	    } catch (InterruptedException iex) {
	    	Thread.currentThread().interrupt();
	    	throw new RuntimeException("Unexpected interruption");
	    }
	}
	
	public void shutDown() throws InterruptedException {
		shuttingDown = true;
		LogValues value = new LogValues();
		value.SolverVersion = SHUTDOWN_REQ;
		itemsToLog.put(value);
	}
}

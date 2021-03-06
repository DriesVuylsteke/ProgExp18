package com.migapro.tester;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.migapro.sudokusolverV1.SudokuSolver;

public class ProgramTester {
	
	private static ISolver solver;
	//private static ArrayList<String> logger = new ArrayList<>();
	private static SudokuReader reader;
	//private static Logger logger = new Logger();
	private static LoggerThread logger;

	public static void main(String[] args) {
		//solver = new SudokuSolver();
		//setSolver(2);
		//reader = new SudokuReader();
		//ArrayList<Sudoku> sudokus = reader.readAllSudokus();
		//avgTest(sudokus);
		
		//Runtime rt = Runtime.getRuntime();
		
		//averageSolveTime(1, sudokus.get(1), 1);
		
		logger = LoggerThread.getLogger();
		try {
			int i = System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runAll();
		try {
			logger.shutDown();
			logger.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("<== Program Finished ==>");
		System.exit(0);
	}
	
	public static void runAll(){
		
		reader = new SudokuReader();
		ArrayList<Sudoku> sudokus = reader.readAllSudokus();
		
		for (int i = 1; i <= 6; i++) {
			System.out.println("Start Solver V" + i);
			setSolver(i);
			avgTest(sudokus);
			if(solver != null){
				solver.destroy();
				solver = null;
			}
		}
		
		
	}
	
	public static void setSolver(int version){
		
		switch (version) {
		case 1:
			solver = new com.migapro.sudokusolverV1.SudokuSolver();
			break;
		case 2:
			solver = new com.migapro.sudokusolverV2.SudokuSolver();
			break;
		case 3:
			solver = new com.migapro.sudokusolverV3.SudokuSolver();
			break;
		case 4:
			solver = new com.migapro.sudokusolverV4.SudokuSolver();
			break;
		case 5:
			solver = new com.migapro.sudokusolverV5.SudokuSolver();
			break;
		case 6:
			solver = new com.migapro.sudokusolverV6.SudokuSolver();
			break;
		default:
			break;
		}
	}
	
	private static void avgTest(ArrayList<Sudoku> sudokus){
		for (int i = 0; i < sudokus.size(); i++) {
			averageSolveTime(25, sudokus.get(i), i);
		}
	}

	private static void averageSolveTime(int iterations, Sudoku sudoku, int sudokuNumber) {
		long[] times = new long[iterations];
		Runtime rt = Runtime.getRuntime();
		long[] runtimes = new long[iterations + 1];
		runtimes[0] = rt.totalMemory() - rt.freeMemory();
		
		for (int i = 0; i < iterations; i++) {
			solver.setCellValues(sudoku.cloneSudoku());
			long start = System.nanoTime();
			solver.solve(0, 0);
			times[i] = System.nanoTime() - start;
			runtimes[i+1] = rt.totalMemory() - rt.freeMemory();
			System.out.println(i + "  -  " + times[i]);
			//printSudoku(solver.getCellValues());
			solver.erase();
		}
		
		long sum = 0;
		long max = times[0];
		long min = times[0];
		for (int i = 0; i < iterations; i++) {
			sum += times[i];
			if(times[i] > max)
				max = times[i];
			else if(times[i] < min)
				min = times[i];
		}
		
		long avg = sum / iterations;
		
		LogValues values = new LogValues();
		values.SolverVersion = solver.getVersion();
		values.sudokuNumber = sudokuNumber;
		values.iterations = iterations;
		values.avg = avg;
		values.max = max;
		values.min = min;
		logger.log(values);
		//logger.logAvgRun(solver.getVersion(), sudokuNumber, iterations, avg, max, min);
	}

	private static void printSudoku(int[][] sudoku){
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
	}
}

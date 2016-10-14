package com.migapro.tester;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.migapro.sudokusolver.SudokuSolver;

public class ProgramTester {
	
	private static SudokuSolver solver;
	//private static ArrayList<String> logger = new ArrayList<>();
	private static SudokuReader reader;
	private static Logger logger = new Logger();

	public static void main(String[] args) {
		solver = new SudokuSolver();
		reader = new SudokuReader();
		ArrayList<Sudoku> sudokus = reader.readAllSudokus();
		avgTest(sudokus);
		
		Runtime rt = Runtime.getRuntime();
		
	}
	
	private static void avgTest(ArrayList<Sudoku> sudokus){
		logger.startTest(solver.VERSION);
		for (int i = 0; i < sudokus.size(); i++) {
			averageSolveTime(100, sudokus.get(i), i);
		}
		logger.endTest();
	}

	private static void averageSolveTime(int iterations, Sudoku sudoku, int sudokuNumber) {
		long[] times = new long[iterations];
		Runtime rt = Runtime.getRuntime();
		long[] runtimes = new long[iterations + 1];
		runtimes[0] = rt.totalMemory() - rt.freeMemory();
		
		for (int i = 0; i < iterations; i++) {
			solver.cellValues = sudoku.cloneSudoku();
			long start = System.nanoTime();
			solver.solve(0, 0);
			times[i] = System.nanoTime() - start;
			runtimes[i+1] = rt.totalMemory() - rt.freeMemory();
			System.out.println(i + "  -  " + times[i]);
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
		
		logger.logAvgRun(solver.VERSION, sudokuNumber, iterations, avg, max, min);
	}

	
}

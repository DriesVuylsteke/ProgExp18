package com.migapro.tester;

public class Sudoku {

	//0 is emty value.
	public int[][] sudoku;
	
	
	public Sudoku(int[][] sudoku){
		this.sudoku = sudoku;
	}
	
	public int[][] cloneSudoku(){
		int[][] copy = new int[sudoku.length][sudoku[0].length];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				copy[i][j] = sudoku[i][j];
			}
		}
		return copy;
	}
	
	public int countFilledNumbers(){
		int count = 0;
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				if(sudoku[i][j] != 0)
					count++;
			}
		}
		return count;
	}
	
}
package com.migapro.tester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SudokuReader {

	public String file = "sudokus.txt";
	
	public ArrayList<Sudoku> readAllSudokus(){
	
		ArrayList<Sudoku> list = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			int[][] sudoku = new int[9][9];
			String line;
			for (int i = 0; (line = reader.readLine()) != null ; i++) {
				for (int j = 0; j < 9; j++) {
					sudoku[i%9][j] = line.charAt(j) - '0';
				}
				if((i+1)%9 == 0){
					list.add(new Sudoku(sudoku));
					sudoku = new int[9][9];
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}

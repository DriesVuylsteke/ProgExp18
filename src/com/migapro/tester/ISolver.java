package com.migapro.tester;

public interface ISolver {

	public void setCellValues(int[][] values);
	public boolean solve(int i, int j);
	public String getVersion();
	public void erase();
}

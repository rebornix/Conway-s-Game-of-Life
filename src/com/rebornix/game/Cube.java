package com.rebornix.game;


public class Cube {
	private int state = 0; // 0 means dead , 1 means alive;
	private int nextState = 0;
	private int row = 0;
	private int column = 0;
	public static int[][] stateArray = new int[16][30];
	public Cube(){}
	public Cube(int row, int column){
		this.row = row;
		this.column = column;
		for(int i = 0; i < 16; ++i){
			for(int j = 0; j != 30; ++j){
				stateArray[i][j] = 0;
			}
		}
	}
	public void setNextState(){
		int count = 0;
		//left top
		if(row - 1 >= 0 && column - 1 >= 0 && stateArray[row-1][column-1] == 1) ++count;
		//top
		if(row - 1 >= 0  && stateArray[row-1][column] == 1) ++count;
		//right top
		if(row - 1 >= 0 && column + 1 <= 29 && stateArray[row-1][column+1] == 1) ++count;
		//left
		if(column - 1 >= 0 && stateArray[row][column-1] == 1) ++count;
		//right
		if(column + 1 <= 29 && stateArray[row][column+1] == 1) ++count;
		//left bottom
		if(row + 1 <= 15 && column - 1 >= 0 && stateArray[row+1][column - 1] == 1) ++count;
		//bottom
		if(row + 1 <= 15 && stateArray[row+1][column] == 1) ++count;
		//right bottom
		if(row + 1 <= 15 && column + 1 <= 29 && stateArray[row+1][column+1] == 1) ++count;
		
		if(count == 3)
			this.nextState = 1;
		else if(count == 2)
			this.nextState = this.state;
		else 
			this.nextState = 0;				
	}
	public int getNextState(){
		return this.nextState;
	}
	public int getState(){
		return state;
	}
	public void setState(int state){
		this.state = state;
		stateArray[row][column] = state;
	}
}

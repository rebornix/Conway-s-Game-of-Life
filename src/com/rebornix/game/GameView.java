package com.rebornix.game;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class GameView extends View {
	static int leftBorder = 40;
	static int topBorder = 20;
	//int status[][] = new int[16][30];  // 0 means white/dead ,1 means black
	Cube status[][] = new Cube[16][30];
	int xradius = 0;
	int yradius = 0;

	public GameView(Context context) {
		super(context);
		for(int i = 0; i != 16; ++i)
			for(int j = 0; j != 30; ++j)
				status[i][j] = new Cube(i, j);
	}
	public void setCount(int x, int y){
		xradius = x;
		yradius = y;
		Log.i("x radius", ""+xradius);
		Log.i("y radius", ""+yradius);
		int row = (yradius - 56 - topBorder) / 40;
		int column = (xradius - leftBorder) / 40;
		if(row < 16 && column < 30){
			status[row][column].setState((status[row][column].getState() + 1) % 2);
		}
		else
			return;
	}
	public void clear(){
		for(int i = 0; i != 16; ++i)
			for(int j = 0; j != 30; ++j)
				status[i][j].setState(0);
		this.invalidate();
	}
	public void onDraw(Canvas canvas){
		Paint mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		for(int i = 0; i < 1200; i = i + 40)
			for(int j = 0; j < 640; j = j + 40){
				int row = j / 40;
				int column = i / 40;
				if( status[row][column].getState() == 0)
					mPaint.setColor(Color.WHITE);
				else
					mPaint.setColor(Color.BLACK);
				canvas.drawRect(leftBorder + i, topBorder + j, leftBorder + i + 39, topBorder + j + 39, mPaint);

			}
	}
	public void changeStatus(){
		for( int i = 0; i != 16; ++i)
			for(int j = 0; j != 30; ++j){
				status[i][j].setNextState();
			}
		for( int i = 0; i != 16; ++i)
			for(int j = 0; j != 30; ++j){
				status[i][j].setState(status[i][j].getNextState());
			}
		this.invalidate();
	}
	
}

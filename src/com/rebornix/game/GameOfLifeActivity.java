package com.rebornix.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class GameOfLifeActivity extends Activity {
    /** Called when the activity is first created. */
	private GameView mGameView = null;
	private static final int REFRESH = 0x000001;
	private static final int CLEAR = 0x000002;
	private static final int START = 0x000003;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.mGameView = new GameView(this);
        setContentView(mGameView);
        //setContentView(R.layout.main);
    }
    Handler myHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case GameOfLifeActivity.REFRESH:
    			mGameView.invalidate();
    			break;
    		case GameOfLifeActivity.CLEAR:
    			mGameView.clear();
    			break;
    		case GameOfLifeActivity.START:
    			break;
    		}
    		super.handleMessage(msg);
    	}
    };
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, GameOfLifeActivity.CLEAR, 1, "Clear" );
		menu.add(0, GameOfLifeActivity.START, 2, "Start" );
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
        switch( id ) {
            case GameOfLifeActivity.CLEAR:
            	Message message = new Message();
    			message.what = GameOfLifeActivity.CLEAR;
        		GameOfLifeActivity.this.myHandler.sendMessage(message);
                break;
            case GameOfLifeActivity.START:
                break;
        }
        return true;
    }
    public boolean onTouchEvent(MotionEvent event) {
    	if(event.getAction() == event.ACTION_DOWN){
    		int xradius = (int) event.getX();
    		int yradius = (int) event.getY();
    		mGameView.setCount(xradius, yradius);
    		Message message = new Message();
			message.what = GameOfLifeActivity.REFRESH;
    		GameOfLifeActivity.this.myHandler.sendMessage(message);
    		return true;
    	}
    	else
    		return false;
	}
    
}
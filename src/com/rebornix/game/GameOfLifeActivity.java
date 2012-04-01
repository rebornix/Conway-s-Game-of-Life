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
	private static final int STOP = 0x000004;
	private Thread thread = new Thread(new GameThread());
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
    			mGameView.changeStatus();
    			break;
    		}
    		super.handleMessage(msg);
    	}
    };
    class GameThread implements Runnable{
    	private int alive = 1;
    	public void run(){
    		if(alive == 1)
    		while(!Thread.currentThread().isInterrupted()){
    			Message message = new Message();
    			message.what = GameOfLifeActivity.START;
    			GameOfLifeActivity.this.myHandler.sendMessage(message);
    			try{
    				Thread.sleep(100);
    			}
    			catch(InterruptedException e){
    				Thread.currentThread().interrupt();
    			}
    		}
    	}
    	public void suspend(){
    		alive = 0;
    	}
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, GameOfLifeActivity.CLEAR, 1, "Clear" );
		menu.add(0, GameOfLifeActivity.START, 2, "Start" );
		menu.add(0, GameOfLifeActivity.STOP, 3, "Stop");
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Message message = new Message();
		
        switch( id ) {
            case GameOfLifeActivity.CLEAR:
    			message.what = GameOfLifeActivity.CLEAR;
        		GameOfLifeActivity.this.myHandler.sendMessage(message);
                break;
            case GameOfLifeActivity.START:
            	if(thread == null){
            		thread = new Thread(new GameThread());
            		thread.start();
            	}
            	else if(!thread.isAlive()){
            		thread = new Thread(new GameThread());
            		thread.start();
            	}
                break;
            case GameOfLifeActivity.STOP:
            	if(thread != null){
            		thread.interrupt();
            		thread = null;
            	}
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
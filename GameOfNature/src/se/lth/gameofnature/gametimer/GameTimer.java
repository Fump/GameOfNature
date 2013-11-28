package se.lth.gameofnature.gametimer;

import java.util.Timer;
import java.util.TimerTask;

import se.lth.gameofnature.data.Database;
import android.content.Context;

public class GameTimer {
	private int seconds;
	
	private Timer t;
	private TimerTask task;
	private Context mContext;
	
	private Database db;
	
	private boolean running;
	
	public GameTimer(Context mContext){
		this.mContext = mContext;
		
		Database db = new Database(mContext);
		db.open();
		
		if(db.hasActiveSession()) {
			seconds = db.getTeamStatus().getGameTime();
		} else {
			seconds = 0;
		}
		
		db.close();
		db = null;
		
		running = false;
	}
	
	public void startTimer() {
		t = new Timer();
		
		db = new Database(mContext);
		db.open();
		
		task = new TimerTask() {
			
			@Override
			public void run() {
				seconds += 1000;
				db.setGameTime(seconds);
			}
		};
		
		t.scheduleAtFixedRate(task, 0, 1000);
		
		running = true;
	}
	
	public void stopTimer() {
		task.cancel();
		t.cancel();
		
		task = null;
		t = null;
		
		db.close();
		db = null;
		
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public int getTime() {
		return seconds;
	}
}

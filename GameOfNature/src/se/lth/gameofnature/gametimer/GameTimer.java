package se.lth.gameofnature.gametimer;

import java.util.Timer;
import java.util.TimerTask;

import se.lth.gameofnature.data.Database;
import android.content.Context;

public class GameTimer {
	public static GameTimer timer;
	
	public static void startTimer(Context mContext) {
		if(timer == null)
			timer = new GameTimer(mContext);
		
		timer.start();
	}
	
	public static void stopTimer() {
		if(timer != null) {
			timer.stop();
			timer = null;
		}
	}
	
	public static boolean isRunning() {
		return timer != null && timer.running;
	}
	
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
	
	public void start() {
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
		
		t.scheduleAtFixedRate(task, 1000, 1000);
		
		running = true;
	}
	
	public void stop() {
		task.cancel();
		t.cancel();
		
		task = null;
		t = null;
		
		db.close();
		db = null;
		
		running = false;
	}
	
	public int getTime() {
		return seconds;
	}
}

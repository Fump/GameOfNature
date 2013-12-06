package se.lth.gameofnature;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.Team;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class WinnerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winner);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		TextView teamName = (TextView)findViewById(R.id.teamNameWin);	
		TextView time = (TextView)findViewById(R.id.TimeWin);
		TextView distance = (TextView)findViewById(R.id.distanceWin);
		
		Database db = new Database(this);
		db.open();
		
		Team t = db.getTeamStatus();
		
		teamName.setText(t.getName());
		
		int hours = (t.getGameTime() / (60*60));
		int minutes = (t.getGameTime() / 60) % 60;
		int seconds = t.getGameTime() % 60;
		
		time.setText(hours + ":" + minutes + ":" + seconds);
		
		distance.setText(t.getDistanceTraveled() + " m");
	}
	
	public void GameBoardScreen(View view){
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, StartActivity.class);
		startActivity(i);
	}
}

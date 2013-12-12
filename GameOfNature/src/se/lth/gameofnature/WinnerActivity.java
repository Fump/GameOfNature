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
		
		findViewById(R.id.exitGame).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						v.setBackgroundResource(R.drawable.button_down);
						
						Database db = new Database(WinnerActivity.this);
						
						db.open();
						db.resetDatabase();
						db.close();
						
						db = null;
						
						Intent i = new Intent(WinnerActivity.this, StartActivity.class);
						WinnerActivity.this.startActivity(i);
					}
				});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		TextView teamName = (TextView)findViewById(R.id.teamNameWin);	
		TextView time = (TextView)findViewById(R.id.TimeWin);
		TextView distance = (TextView)findViewById(R.id.distanceWin);
		TextView winInfo = (TextView)findViewById(R.id.WinTitle);
		
		Database db = new Database(this);
		db.open();
		
		Team t = db.getTeamStatus();
		
		teamName.setText(t.getName());
		
		int hours = (t.getGameTime() / (60*60));
		int minutes = (t.getGameTime() / 60) % 60;
		int seconds = t.getGameTime() % 60;
		
		time.setText(hours + ":" + minutes + ":" + seconds);
		
		distance.setText(t.getDistanceTraveled() + " m");
		
		winInfo.setText("Grattis " + t.getName() + "\n" +
				"Ni har vunnit spelet!");
		
		db.close();
		db = null;
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

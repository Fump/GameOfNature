package se.lth.gameofnature;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.GameMapData;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	
		Database db = new Database(this);
		
		db.open();
		
		Button resume = (Button)findViewById(R.id.ContinueButton);

		if(!db.hasActiveSession()) {
			resume.setVisibility(View.INVISIBLE);
		} else {
			resume.setVisibility(View.VISIBLE);
		}
		
		db.close();
		db = null;
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public void nextScreen(View view){
		Database db = new Database(this);
		
		db.open();
		db.resetDatabase();
		GameMapData.resetGameData();
		
		db.close();
		db = null;
		
		Button button = (Button) findViewById(R.id.StartButton);
		button.setBackgroundResource(R.drawable.button_down);
		Intent intent = new Intent(this, Alternativsida.class);
		startActivity(intent);
	}
	
	public void changeButton(View view){
		Button button = (Button) findViewById(R.id.ContinueButton);
		button.setBackgroundResource(R.drawable.button_down);
		
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);
	}
}

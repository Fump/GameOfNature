package se.lth.gameofnature;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
	}
	
	public void GameBoardScreen(View view){
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);
	}
}

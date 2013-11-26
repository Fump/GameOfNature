package se.lth.gameofnature;

import se.lth.gameofnature.data.Database;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
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
		
		db.close();
		db = null;
		
		Intent intent = new Intent(this, Alternativsida.class);
		startActivity(intent);
	}
}

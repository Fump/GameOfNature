package se.lth.gameofnature;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Alternativsida extends Activity {
	private ImageButton currColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternativsida);
		//Förvald färg vid start
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.choose_colors);
		currColor = (ImageButton)paintLayout.getChildAt(0);
		currColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));
	}
	
	//Lyssna på knapptryck
	public void colorClicked(View view){
		if(view!=currColor){
			ImageButton imgView = (ImageButton)view;
			//ändra backgrundsfärgen vid tryck så att man se vilken färg man har valt
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));
			currColor.setImageDrawable(getResources().getDrawable(R.drawable.colorbackgroundproperties));
			currColor=(ImageButton)view;
			}	
	}
	
	public void nextScreen(View view) {
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alternativsida, menu);
		return true;
	}

}

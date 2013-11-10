package se.lth.gameofnature;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Alternativsida extends Activity {
	private ImageButton currentColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternativsida);
		//Förvald färg vid start
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.choose_colors);
		currentColor = (ImageButton)paintLayout.getChildAt(0);
		currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));
	}
	
	//Lyssna på knapptryck
	public void colorClicked(View view){
		if(view!=currentColor){
			ImageButton newColor = (ImageButton)view;
			//ändra backgrundsfärgen vid tryck så att man se vilken färg man har valt
			newColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));
			currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorbackgroundproperties));
			currentColor=newColor;
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

package se.lth.gameofnature;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameBoardActivity extends Activity {
	private GameMap map;
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;

	public static final String INTENT_SOURCE = "INTENT_SOURCE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
		
		initMapIfNeeded();
		initLocationHandlerIfNeeded();
		
		initTaskMarkers();
		
		handleIntent(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_board, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		initMapIfNeeded();
		initLocationHandlerIfNeeded();
		initTaskMarkers();
		
		handleIntent(getIntent());
	}
	
	private void handleIntent(Intent i) {
		/*Bundle extras = i.getExtras();
		
		if(extras != null) {
			String source = extras.getString(INTENT_SOURCE);
			
			Toast.makeText(this, "Handling intent from: " + source, Toast.LENGTH_SHORT).show();
			
			if(source.equals(ReceiveTransitionsIntentService.SENDER_NAME)) {
				String taskMarkerId = extras.getString(TASK_MARKER_ID);
				showTaskMarkerDialog(taskMarkerId);
			}
		}*/
	}
	
	private void showTaskMarkerDialog(String taskMarkerId) {
		TaskMarker m = PlayerSession.getTaskMarker(taskMarkerId);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 
		builder.setMessage("Framme! " +  m.title + " " + m.snippet);
		builder.create().show();
	}
	
	/* Sets up a GameMap connected to Google maps if one does not already exist.
	 */
	private void initMapIfNeeded() { 
		if(map == null) {
			GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
			
			map = new GameMap(gMap, this);
			
			Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.bug);
			
			myLocation = new MyLocationMarker(GameMap.ANDREASSONS_MEDOW, icon, 
					"My Location", "Here I am");
			
			map.addGameMarker(myLocation);
		}
	}
	
	private void initTaskMarkers() {
		addTaskMarker(new LatLng(55.594540, 13.021855),
				"Willys",
				"H�r handlar jag",
				R.drawable.ic_launcher,
				"0");
	}
	
	private void addTaskMarker(LatLng pos, String title, String snippet, 
			int iconId,String markerId) {
		TaskMarker test = new TaskMarker(pos, title, snippet, iconId, markerId);
		
		PlayerSession.addTaskMarker(markerId, test);

		map.addGameMarker(test);
		mLocationHandler.trackTaskMarker(test);
	}
	
	/* Sets up a locationHandler object to track the current location of the user 
	 * is only set up if one does not already exist. Also starts tracking the user.
	 */
	public void initLocationHandlerIfNeeded() {
		if(mLocationHandler == null) {
			if(mLocationHandler == null)
				mLocationHandler = new LocationHandler(this, map, myLocation);
		}
		
		mLocationHandler.startTracking();
	}
}

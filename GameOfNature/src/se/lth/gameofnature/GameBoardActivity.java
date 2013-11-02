package se.lth.gameofnature;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameBoardActivity extends Activity {
	private GameMap map;
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
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
		
		if(map == null);
			initMap();
			
		if(mLocationHandler == null)
			mLocationHandler = new LocationHandler(this, map, myLocation);
		
		mLocationHandler.startTracking();
	}
	
	private void initMap() {
		GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		
		map = new GameMap(gMap, this);
		
		myLocation = map.addMyLocationMarker("My Location", 
				"Here I am", 
				GameMap.ANDREASSONS_MEDOW, 
				R.drawable.bug);
	}
}

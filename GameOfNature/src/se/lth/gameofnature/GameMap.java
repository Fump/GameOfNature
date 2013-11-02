package se.lth.gameofnature;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GameMap {
	private GoogleMap map;
	private Context mContext;
	
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;
	
	public static final LatLng ANDREASSONS_MEDOW = 
			new LatLng(56.148370, 13.393320);
			
	
	private static final int START_ZOOM = 15;
	
	public GameMap(GoogleMap map, Context mContext) {
		this.map = map;
		this.mContext = mContext;
		
		setUpMap();
		setUpLocationHandler();
		setUpLocationMarker();
	}
	
	private void setUpMap() {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(ANDREASSONS_MEDOW, START_ZOOM));
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}
	
	private void setUpLocationHandler() {
		mLocationHandler = new LocationHandler(mContext);
	}
	
	private void setUpLocationMarker() {
		Marker locationMarker = map.addMarker(new MarkerOptions().position(ANDREASSONS_MEDOW)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.bug))
				.title("myLocation")
				.snippet("Here I am"));
		
		myLocation = new MyLocationMarker(locationMarker);
		mLocationHandler.setLocationMarker(myLocation);
	}
	
	public void stopListners() {
		mLocationHandler.stopTracking();
	}
	
	public void startListner() {
		mLocationHandler.startTracking();
	}
}

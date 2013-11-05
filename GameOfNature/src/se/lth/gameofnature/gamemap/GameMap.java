package se.lth.gameofnature.gamemap;

import se.lth.gameofnature.gamemap.markers.GameMarker;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
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
	
	public static final LatLng ANDREASSONS_MEDOW = 
			new LatLng(56.148370, 13.393320);
			
	
	private static final int START_ZOOM = 15;
	
	public GameMap(GoogleMap map, Context mContext) {
		this.map = map;
		this.mContext = mContext;
		
		setUpMap();
	}
	
	private void setUpMap() {
		setPosAndZoom(ANDREASSONS_MEDOW, START_ZOOM);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}
	
	public void setPosition(LatLng pos) {
		map.moveCamera(CameraUpdateFactory.newLatLng(pos));
	}
	
	public void setPosAndZoom(LatLng pos, int zoom) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
	}
	
	public void addGameMarker(GameMarker m) {
		Marker myLocation = map.addMarker(m.createMarker());
		m.setMarker(myLocation);
	}
}

package se.lth.gameofnature.gamemap;

import se.lth.gameofnature.gamemap.markers.GameMarker;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class GameMap {
	private GoogleMap map;
	
	public static final LatLng ANDREASSONS_MEDOW = 
			new LatLng(56.148370, 13.393320);
			
	private int zoom;
	private final static int[] zoomLevels = {15, 16, 17, 18, 20}; 
	
	public GameMap(GoogleMap map) {
		this.map = map;
		
		setUpMap();
	}
	
	private void setUpMap() {
		zoom = 1;
		
		setPosAndZoom(ANDREASSONS_MEDOW, zoomLevels[1]);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setAllGesturesEnabled(false);
	}
	
	public void setPosition(LatLng pos) {
		map.moveCamera(CameraUpdateFactory.newLatLng(pos));
	}
	
	public void setPosAndZoom(LatLng pos, int zoom) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
	}
	
	public void setZoom(int zoom) {
		map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
	}
	
	public boolean zoomIn() {
		if(zoom < zoomLevels.length - 1) {
			zoom = zoom + 1;
			setZoom(zoomLevels[zoom]);
		}
		
		if(zoom == zoomLevels.length - 1)
			return true;
		
		return false;
	}
	
	public boolean zoomOut() {
		if(zoom > 0) {
			zoom = zoom - 1;
			setZoom(zoomLevels[zoom]);
		}
		
		if(zoom == 0)
			return true;
		
		return false;
	}
	
	public void addGameMarker(GameMarker m) {
		Marker myLocation = map.addMarker(m.createMarker());
		m.setMarker(myLocation);
	}
}

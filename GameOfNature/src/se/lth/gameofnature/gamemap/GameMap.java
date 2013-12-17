package se.lth.gameofnature.gamemap;

import se.lth.gameofnature.gamemap.markers.GameMarker;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class GameMap {
	private GoogleMap map;
	
	public static final LatLng ANDREASSONS_MEDOW = 
			new LatLng(56.148370, 13.393320);
			
	public GameMap(GoogleMap map) {
		this.map = map;
		
		setUpMap();
	}
	
	private void setUpMap() {
		setZoom(16f);
		
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setRotateGesturesEnabled(false); 
		map.getUiSettings().setCompassEnabled(false);
	}
	
	public void setPosition(LatLng pos) {
		map.moveCamera(CameraUpdateFactory.newLatLng(pos));
	}
	
	public void setPosAndZoom(LatLng pos, int zoom) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
	}
	
	public void setZoom(float zoom) {
		map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
	}
	
	public void animateTo(GameMarker m) {
		setPosAndZoom(m.getPosition(), 16);
	}
	
	public boolean zoomIn() {
		float zoom = map.getCameraPosition().zoom;
		
		if(zoom < map.getMaxZoomLevel()) {
			setZoom(zoom + 1);
		}
		
		if(zoom == map.getMaxZoomLevel())
			return true;
		
		return false;
	}
	
	public boolean zoomOut() {
		float zoom = map.getCameraPosition().zoom;
		
		if(zoom > map.getMinZoomLevel()) {
			setZoom(zoom - 1);
		}
		
		if(zoom == map.getMinZoomLevel())
			return true;
		
		return false;
	}
	
	public void addGameMarker(GameMarker m) {
		Marker myLocation = map.addMarker(m.createMarker());
		m.setMarker(myLocation);
	}
}

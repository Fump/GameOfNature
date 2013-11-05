package se.lth.gameofnature.gamemap.markers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class GameMarker {
	protected LatLng position;
	protected String title;
	protected String snippet;
	
	protected Marker myMarker;
	
	public GameMarker(LatLng position, String title, String snippet) {
		this.position = position;
		this.title = title;
		this.snippet = snippet;
		
		myMarker = null;
	}
	
	public void setPosition(LatLng pos) {
		myMarker.setPosition(pos);
	}
	
	public LatLng getPosition() {
		return myMarker.getPosition();
	}
	
	public void setMarker(Marker m) {
		myMarker = m;
	}
	
	abstract public MarkerOptions createMarker();
}

package se.lth.gameofnature;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;

public class MyLocationMarker {
	
	private Marker myMarker;
	
	public MyLocationMarker(Marker myMarker) {
		this.myMarker = myMarker;
		
		myMarker.setFlat(true);
	}
	
	public void setRotation(float rot) {
		myMarker.setRotation(rot);
	}
	
	public void setPosition(LatLng pos) {
		myMarker.setPosition(pos);
	}
	
	
}

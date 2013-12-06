package se.lth.gameofnature.gamemap.markers;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationMarker extends GameMarker {
	
	private Bitmap icon;
	private float rotation;
	
	public MyLocationMarker(LatLng position, Bitmap icon, String title, String snippet) {
		super(position, title, snippet);
		
		this.icon = icon;
	}
	
	/*
	 * Rotates the markers icon.
	 */
	public void setRotation(float rotation) {
		if(Math.abs(this.rotation - rotation) >= 15) {
			this.rotation = rotation;
			
			Matrix matrix = new Matrix();
			
			matrix.postRotate(rotation);
			//Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), matrix, true);
			
			myMarker.setIcon(BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), matrix, true)));
		}
	}
	

	@Override
	public MarkerOptions createMarker() {
		MarkerOptions mOptions = new MarkerOptions();
		
		mOptions.position(position)
				.icon(BitmapDescriptorFactory.fromBitmap(icon))
				.title(title)
				.snippet(snippet);
		
		return mOptions;
	}
	

}

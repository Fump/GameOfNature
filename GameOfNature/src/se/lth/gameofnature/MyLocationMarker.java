package se.lth.gameofnature;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationMarker extends GameMarker {
	
	private Bitmap icon;

	public MyLocationMarker(LatLng position, Bitmap icon, String title, String snippet) {
		super(position, title, snippet);
		
		this.icon = icon;
	}
	
	public void setRotation(float rotation) {
		Matrix matrix = new Matrix();
		
		matrix.postRotate(rotation);
		icon = Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), matrix, true);
		
		myMarker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
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

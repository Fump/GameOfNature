package se.lth.gameofnature.gamemap.markers;

import java.util.ArrayList;

import se.lth.gameofnature.questions.Question;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//F�rsta version av en klass som ska representera uppgiftspunkter p� kartan.
//Beh�vs nog fler attribut och metoder, implementerade bara vad som beh�vdes
//F�r att f� ig�ng sp�rningen av punkter.
public class TaskMarker extends GameMarker {
	private int drawableId;
	private int status;
	
	private String id;
	
	private ArrayList<Question> questions;
	
	public static final int STATUS_ACTIVE = 0;
	public static final int STATUS_LOCKED = 1;
	public static final int STATUS_DONE = 2;

	public TaskMarker(LatLng position, String title, String snippet, int drawableId, String id) {
		super(position, title, snippet);
		
		this.drawableId = drawableId;
		this.id = id;
		
		questions = new ArrayList<Question>();
		
		status = STATUS_ACTIVE;
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}

	@Override
	public MarkerOptions createMarker() {
		MarkerOptions mOptions = new MarkerOptions();
		
		mOptions.position(position)
				.icon(BitmapDescriptorFactory.fromResource(drawableId))
				.title(title)
				.snippet(snippet);
		
		return mOptions;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSnippet() {
		return snippet;
	}
	
	public void setActive() {
		status = STATUS_ACTIVE;
		
		//Implementera ikonbyte osv h�r.
	}
	
	public void setLocked() {
		status = STATUS_LOCKED;
		
		//Implementera ikonbyte osv h�r.
	}
	
	public void setDone() {
		status = STATUS_DONE;
		
		//Implementera ikon byte osv h�r.
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId() {
		return id;
	}

}

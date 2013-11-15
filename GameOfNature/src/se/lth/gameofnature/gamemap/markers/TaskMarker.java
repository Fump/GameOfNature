package se.lth.gameofnature.gamemap.markers;

import java.util.ArrayList;
import java.util.Random;

import se.lth.gameofnature.questions.Question;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Första version av en klass som ska representera uppgiftspunkter på kartan.
//Behövs nog fler attribut och metoder, implementerade bara vad som behövdes
//För att få igång spårningen av punkter.
public class TaskMarker extends GameMarker {
	private int drawableId;
	private int status;
	
	private String id;
	private String infoTxt;
	
	private ArrayList<Question> questions;
	private String lastQuestionId;
	
	public static String TASK_MARKER_ID = "TASK_MARKER_ID";
	
	public static final int STATUS_ACTIVE = 0;
	public static final int STATUS_LOCKED = 1;
	public static final int STATUS_DONE = 2;

	public TaskMarker(String id, LatLng position, String title, String snippet, String infoTxt, int drawableId) {
		super(position, title, snippet);
		
		this.drawableId = drawableId;
		this.id = id;
		this.infoTxt = infoTxt;
		
		questions = new ArrayList<Question>();
		
		status = STATUS_ACTIVE;
		
		lastQuestionId = null;
		
		
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public Question getNextQuestion() {
		Random rand = new Random();
		
		Question nextQuestion = questions.get(rand.nextInt(questions.size()));
		
		while(nextQuestion.getId().equals(lastQuestionId))
			nextQuestion = questions.get(rand.nextInt(questions.size()));
		
		lastQuestionId = nextQuestion.getId();
		
		return nextQuestion;
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
	
	public String getInfoTxt() {
		return infoTxt;
	}
	
	public void setActive() {
		status = STATUS_ACTIVE;
		
		//Implementera ikonbyte osv här.
	}
	
	public void setLocked() {
		status = STATUS_LOCKED;
		
		//Implementera ikonbyte osv här.
	}
	
	public void setDone() {
		status = STATUS_DONE;
		
		//Implementera ikon byte osv här.
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId() {
		return id;
	}

}

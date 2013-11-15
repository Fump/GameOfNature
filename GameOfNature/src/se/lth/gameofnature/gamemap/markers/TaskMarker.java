package se.lth.gameofnature.gamemap.markers;

import java.util.ArrayList;
import java.util.Random;

import se.lth.gameofnature.questions.Question;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Första version av en klass som ska representera uppgiftspunkter på kartan.
//Behövs nog fler attribut och metoder, implementerade bara vad som behövdes
//För att få igång spårningen av punkter.
public class TaskMarker extends GameMarker {
	private static final String ICON_DRAWABLE_ID_BASE = "marker_icon_";
	
	private Context mContext;
	
	private String iconId;
	private String teamColorId;
	private int status;
	
	private String id;
	private String infoTxt;
	
	private ArrayList<Question> questions;
	private String lastQuestionId;
	
	public static String TASK_MARKER_ID = "TASK_MARKER_ID";
	
	public static final int STATUS_ACTIVE = 0;
	public static final int STATUS_LOCKED = 1;
	public static final int STATUS_DONE = 2;

	public TaskMarker(Context mContext, String id, LatLng position, String title, 
			String snippet, String infoTxt, String iconId) {
		super(position, title, snippet);
		
		this.mContext = mContext;
		
		this.iconId = iconId;
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
				.icon(BitmapDescriptorFactory.fromResource(getDrawableId(iconId, teamColorId, status)))
				.title(title)
				.snippet(snippet);
		
		return mOptions;
	}
	
	public void setTeamColor(String teamColorId) {
		this.teamColorId = teamColorId;
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
		
		myMarker.setIcon(BitmapDescriptorFactory.
				fromResource(getDrawableId(iconId, teamColorId, status)));
	}
	
	public void setLocked() {
		status = STATUS_LOCKED;
		
		myMarker.setIcon(BitmapDescriptorFactory.
				fromResource(getDrawableId(iconId, teamColorId, status)));
	}
	
	public void setDone() {
		status = STATUS_DONE;
		
		myMarker.setIcon(BitmapDescriptorFactory.
				fromResource(getDrawableId(iconId, teamColorId, status)));
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId() {
		return id;
	}
	
	private int getDrawableId(String iconId, String colorId, int status) {
		switch(status) {
			case STATUS_ACTIVE:
				return mContext.
						getResources().
						getIdentifier(ICON_DRAWABLE_ID_BASE + iconId + "_" + colorId, 
						"drawable", "se.lth.gameofnature");
			
			case STATUS_LOCKED:
				return mContext.
						getResources().
						getIdentifier(ICON_DRAWABLE_ID_BASE + "locked", 
						"drawable", "se.lth.gameofnature");
			default:
				return mContext
						.getResources()
						.getIdentifier(ICON_DRAWABLE_ID_BASE + iconId + "_" + "green", 
						"drawable", "se.lth.gameofnature");
		}
	}

}

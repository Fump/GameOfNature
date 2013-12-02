package se.lth.gameofnature.gamemap.markers;

import java.util.ArrayList;
import java.util.Random;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.questions.Question;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Första version av en klass som ska representera uppgiftspunkter på kartan.
//Behövs nog fler attribut och metoder, implementerade bara vad som behövdes
//För att få igång spårningen av punkter.
public class TaskMarker extends GameMarker implements Comparable<TaskMarker> {
	private static final String ICON_DRAWABLE_ID_BASE = "marker_";
	
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
		
		while(questions.size() > 1 && nextQuestion.getId().equals(lastQuestionId))
			nextQuestion = questions.get(rand.nextInt(questions.size()));
		
		lastQuestionId = nextQuestion.getId();
		
		Database db = new Database(mContext);
		
		db.open();
	
		db.setLastQuestionId(id, lastQuestionId);
		db.close();
		
		db = null;
		
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
		setStatus(STATUS_ACTIVE);
	}
	
	public void setLocked() {
		setStatus(STATUS_LOCKED);
	}
	
	public void setDone() {
		setStatus(STATUS_DONE);
	}
	
	public void setStatus(int status) {
		this.status = status;
		
		if(myMarker != null) {
			myMarker.setIcon(
					BitmapDescriptorFactory.
					fromResource(getDrawableId(iconId, teamColorId, status))
			);
		}
		
		Database db = new Database(mContext);
		
		db.open();
	
		db.setCurrentStatus(id, status);
		db.close();
		
		db = null;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId() {
		return id;
	}
	
	public void setLastQuestionId(String qId) {
		lastQuestionId = qId;
	}
	
	public String getLastQuestionId() {
		return lastQuestionId;
	}
	
	private int getDrawableId(String iconId, String colorId, int status) {
		switch(status) {
			case STATUS_ACTIVE:
				return mContext.
						getResources().
						getIdentifier(ICON_DRAWABLE_ID_BASE + iconId + "_white", 
						"drawable", "se.lth.gameofnature");
			
			case STATUS_LOCKED:
				return mContext.
						getResources().
						getIdentifier(ICON_DRAWABLE_ID_BASE + "locked", 
						"drawable", "se.lth.gameofnature");
			default:
				return mContext
						.getResources()
						.getIdentifier(ICON_DRAWABLE_ID_BASE + iconId + "_" + colorId, 
						"drawable", "se.lth.gameofnature");
		}
	}
	
	public int getDrawableId() {
		return getDrawableId(iconId, teamColorId, TaskMarker.STATUS_ACTIVE);
	}
	
	@Override
	public boolean equals(Object another) {
		if(another == null)
			return false;
		
		if(!(another instanceof TaskMarker))
			return false;
		
		TaskMarker anotherMarker = (TaskMarker)another;
		
		return compareTo(anotherMarker) == 0;
	}

	@Override
	public int compareTo(TaskMarker another) {
		return id.compareTo(another.id);
	}

}

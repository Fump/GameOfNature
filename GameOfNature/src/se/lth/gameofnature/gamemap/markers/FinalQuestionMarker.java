package se.lth.gameofnature.gamemap.markers;

import java.util.ArrayList;

import se.lth.gameofnature.questions.Clue;
import se.lth.gameofnature.questions.Question;
import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//TODO: Ej klar
//En separat klass för slutuppgiftspunkten. Skiljer sig lite från TaskMarker iom att den har vissa andra attribut och att den är unik.

public class FinalQuestionMarker extends GameMarker {
	private static final String ICON_DRAWABLE_ID_BASE = "marker_icon_";
	private Context mContext;
	private String iconId;
	private String questionId;
	private String infoTxt;
	private String timer;
	private int status;
	
	public static final int STATUS_FINAL = 3;
	public static String TASK_MARKER_ID = "FINAL_MARKER_ID";
	
	private ArrayList<Clue> clues;
	
	public FinalQuestionMarker(Context mContext, String questionId, LatLng position, String title, 
			String snippet, String infoTxt, String iconId, String timer) {
		super(position, title, snippet);
		
		this.mContext = mContext;
		this.iconId = iconId;
		this.questionId = questionId;
		this.infoTxt = infoTxt;
		this.timer = timer;
		status = STATUS_FINAL;
	}
	
	@Override
	public MarkerOptions createMarker() {
		MarkerOptions mOptions = new MarkerOptions();
		
		mOptions.position(position)
				.icon(BitmapDescriptorFactory.fromResource(getDrawableId(iconId)))
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
	
	public String getQuestionId() {
		return questionId; 
	}
	
	public String getTimer(){
		return timer;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void addClue(Clue c){
		clues.add(c);
	}
	
	private int getDrawableId(String iconId) {
		return mContext
				.getResources()
				.getIdentifier(ICON_DRAWABLE_ID_BASE + iconId + "_" + "green", 
				"drawable", "se.lth.gameofnature");
	}
	
	public void startFinalQuestionActivity(){
		
	}
}

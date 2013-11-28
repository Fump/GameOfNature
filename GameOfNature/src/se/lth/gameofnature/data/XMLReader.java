package se.lth.gameofnature.data;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import se.lth.gameofnature.R;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.questions.Clue;
import se.lth.gameofnature.questions.FinalQuestion;
import se.lth.gameofnature.questions.Question;
import se.lth.gameofnature.questions.TextQuestion;

public class XMLReader {

	static ArrayList<Clue> clues;
	public static TreeMap<String, TaskMarker> readTaskMarkers(Context mContext) {
		TreeMap<String, TaskMarker> markers = new TreeMap<String, TaskMarker>();
		clues = new ArrayList<Clue>();
		
		InputStream is = mContext.getResources().openRawResource(R.raw.map);
		
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is, "UTF-8");
			
			doc.getDocumentElement().normalize();
			
			NodeList markerNodes = doc.getElementsByTagName("TaskMarker");
			
			for(int i = 0; i < markerNodes.getLength(); i++) {
				Node markerNode = markerNodes.item(i);
				
				if(markerNode.getNodeType() == Node.ELEMENT_NODE) {
					Element markerElement = (Element) markerNode;
					TaskMarker m = getMarker(markerElement, mContext);
					
					/*Nytt: Hämta clues och lägg in i lokal Clue-arraylist.
					 * Denna skickas sedan med ifall frågan som läggs till är en slutfråga (se getQuestion)
					 */
					
					NodeList clueNodes = markerElement.getElementsByTagName("Clue");
					
					for(int j = 0; j < clueNodes.getLength(); j++){
						Node clueNode = clueNodes.item(j);
						
						if(clueNode.getNodeType() == Node.ELEMENT_NODE){
							Element clueElement = (Element) clueNode;
							
							Clue c = getClue(clueElement);
							clues.add(c);
						}
					}
					
					NodeList questionNodes = markerElement.getElementsByTagName("Question");
					
					for(int j = 0; j < questionNodes.getLength(); j++) {
						Node questionNode = questionNodes.item(j);
						
						if(questionNode.getNodeType() == Node.ELEMENT_NODE) {
							Element questionElement = (Element) questionNode;
							
							Question q = getQuestion(questionElement);
							m.addQuestion(q);
						}
					}
					
					m.setTeamColor("blue");
					
					markers.put(m.getId(), m);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return markers;
	}
	
	private static TaskMarker getMarker(Element e, Context mContext) {
		String id = e.getAttribute("id");
		
		double lat = Double.parseDouble(e.getAttribute("latitude"));
		double lng = Double.parseDouble(e.getAttribute("longitude"));
		LatLng pos = new LatLng(lat, lng);
		
		String title = e.getAttribute("title");
		String snippet = e.getAttribute("snippet");
		String infoTxt = e.getAttribute("infoTxt");
		
		String iconId = e.getAttribute("iconId");
		
		return new TaskMarker(mContext, id, pos, title, snippet, infoTxt, iconId);
	}
	
	private static Question getQuestion(Element e) {
		String id = e.getAttribute("id");
		String type = e.getAttribute("type");
		String questionTxt = e.getAttribute("questionTxt");

		String[] answers = new String[4];
		
		answers[0] = e.getAttribute("answer0");
		answers[1] = e.getAttribute("answer1");
		answers[2] = e.getAttribute("answer2");
		answers[3] = e.getAttribute("answer3");
		
		int correctAnswer = Integer.parseInt(e.getAttribute("correctAnswer"));
		
		if(type.equals(Question.QUESTION_TYPE_TEXT)) {
			return new TextQuestion(id, questionTxt, answers, correctAnswer);
		} else if(type.equals(Question.QUESTION_TYPE_FINAL)){
			
			return new FinalQuestion(id,questionTxt,answers,correctAnswer, clues);
		}else {
			return new TextQuestion(id, questionTxt, answers, correctAnswer);
		}
		
	}
	
	private static Clue getClue(Element e){
		String id = e.getAttribute("id");
		String code = e.getAttribute("code");
		String item = e.getAttribute("item");
		return new Clue(id,code,item);
	}
	

	
}

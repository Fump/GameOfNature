package se.lth.gameofnature.data;

import java.io.InputStream;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import se.lth.gameofnature.R;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.questions.FinalQuestion;
import se.lth.gameofnature.questions.Question;
import se.lth.gameofnature.questions.TextQuestion;

public class XMLReader {

	public static TreeMap<String, TaskMarker> readTaskMarkers(Context mContext) {
		TreeMap<String, TaskMarker> markers = new TreeMap<String, TaskMarker>();
		
		InputStream is = mContext.getResources().openRawResource(R.raw.test);
		
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is, "ISO-8859-1");
			
			doc.getDocumentElement().normalize();
			
			NodeList markerNodes = doc.getElementsByTagName("TaskMarker");
			
			for(int i = 0; i < markerNodes.getLength(); i++) {
				Node markerNode = markerNodes.item(i);
				
				if(markerNode.getNodeType() == Node.ELEMENT_NODE) {
					Element markerElement = (Element) markerNode;
					TaskMarker m = getMarker(markerElement, mContext);
					
					NodeList questionNodes = markerElement.getElementsByTagName("Question");
					
					for(int j = 0; j < questionNodes.getLength(); j++) {
						Node questionNode = questionNodes.item(j);
						
						if(questionNode.getNodeType() == Node.ELEMENT_NODE) {
							Element questionElement = (Element) questionNode;
							
							Question q = getQuestion(questionElement);
							m.addQuestion(q);
						}
					}
					
					markers.put(m.getId(), m);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return markers;
	}
	
	public static TaskMarker getFinalTaskMarker(Context mContext) {
		InputStream is = mContext.getResources().openRawResource(R.raw.map);
	
		TaskMarker m = null;
		
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is, "ISO-8859-1");
			
			doc.getDocumentElement().normalize();
			
			NodeList markerNodes = doc.getElementsByTagName("FinalTaskMarker");
			
			if(markerNodes.getLength() > 0) {
				Node markerNode = markerNodes.item(0);
				
				if(markerNode.getNodeType() == Node.ELEMENT_NODE) {
					Element markerElement = (Element) markerNode;
					
					m = getMarker(markerElement, mContext);
					
					NodeList questionNodes = markerElement.getElementsByTagName("FinalQuestion");
					
					if(questionNodes.getLength() > 0) {
						
						Node questionNode = questionNodes.item(0);
						
						if(questionNode.getNodeType() == Node.ELEMENT_NODE) {
							Element questionElement = (Element) questionNode;
							
							Question q = getFinalQuestion(questionElement);
							m.addQuestion(q);
						}
						
					}
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return m;
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
		String questionTxt = e.getAttribute("questionTxt");

		String[] answers = new String[4];
		
		answers[0] = e.getAttribute("answer0");
		answers[1] = e.getAttribute("answer1");
		answers[2] = e.getAttribute("answer2");
		answers[3] = e.getAttribute("answer3");
		
		int correctAnswer = Integer.parseInt(e.getAttribute("correctAnswer"));
		
		return new TextQuestion(id, questionTxt, answers, correctAnswer);
	}
	
	private static Question getFinalQuestion(Element e) {
		String id = e.getAttribute("id");
		String code = e.getAttribute("code");
		String questionTxt = e.getAttribute("questionTxt");
		
		return new FinalQuestion(id, code, questionTxt);

	}
}

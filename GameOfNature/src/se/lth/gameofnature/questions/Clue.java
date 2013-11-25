package se.lth.gameofnature.questions;

public class Clue {
	
	protected String id, code, item;
	
	public Clue(String id, String item, String code){
		this.id = id;
		this.item = item;
		this.code = code;
	}
	
	public String getId(){
		return id;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getItem(){
		return item;
	}
}

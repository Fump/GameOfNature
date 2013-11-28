package se.lth.gameofnature.questions;

public class Clue {
	
	protected String id,code,item;
	
	public Clue(String id, String code, String item){
		this.id = id;
		this.code = code;
		this.item = item;
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

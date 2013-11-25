package se.lth.gameofnature.data;

public class Team {
	
	private String name;
	private int iconId;
	private String color;
	private int game_time;
	private int distance_traveled;
	private int hasActiveSesseion;
	private int clues;
	
	public Team(String name, int iconId, String color,
			int game_time, int distance_traveled, int hasActiveSesseion,
			int clues){
		this.name = name;
		this.iconId = iconId;
		this.color = color;
		this.game_time = game_time;
		this.distance_traveled = distance_traveled;
		this.hasActiveSesseion = hasActiveSesseion;
		this.clues=clues;
		
	}

	public int getClue() {
		return clues;
	}

	public void setClue(int clue) {
		this.clues = clue;
	}
	
	public int getHasActiveSesseion() {
		return hasActiveSesseion;
	}

	public void setHasActiveSesseion(int hasActiveSesseion) {
		this.hasActiveSesseion = hasActiveSesseion;
	}
	
	public int getDistanceTraveled() {
		return distance_traveled;
	}

	public void setDistanceTraveled(int distance_traveled) {
		this.distance_traveled = distance_traveled;
	}
	
	public int getGameTime() {
		return game_time;
	}

	public void setGameTime(int game_time) {
		this.game_time = game_time;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public int getIconId() {
		return iconId;
	}

	public void setId(int iconId) {
		this.iconId = iconId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

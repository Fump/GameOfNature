package se.lth.gameofnature.data;

public class Team {
	
	private String name;
	private String iconId;
	private String color;
	private int game_time;
	private int distance_traveled;
	private int hasActiveSesseion;
	private int clue;
	/*KONSTRUKTER SAKNAS*/

	public int getClue() {
		return clue;
	}

	public void setClue(int clue) {
		this.clue = clue;
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
	
	public String getIconId() {
		return iconId;
	}

	public void setId(String iconId) {
		this.iconId = iconId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

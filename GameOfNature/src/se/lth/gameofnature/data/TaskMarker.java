package se.lth.gameofnature.data;

public class TaskMarker {

	private String id;
	private int currentStatus;
	private int lastQuestionId;
	/*KONSTRUKTER SAKNAS*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public int getLastQuestionId() {
		return lastQuestionId;
	}

	public void setLastQuestionId(int lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}
}

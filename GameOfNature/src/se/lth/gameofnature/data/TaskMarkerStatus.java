package se.lth.gameofnature.data;

public class TaskMarkerStatus {

	private String id;
	private int currentStatus;
	private String lastQuestionId;
	/*KONSTRUKTER SAKNAS*/
	public TaskMarkerStatus(String id, int currenStatus,String lasQuestionId){
		this.lastQuestionId = lasQuestionId;
		this.currentStatus = currenStatus;
		this.id = id;
	}

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
	public String getLastQuestionId() {
		return lastQuestionId;
	}

	public void setLastQuestionId(String lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}
}

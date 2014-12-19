package javafunctiontester;

public class Result {
	
	private boolean pass;
	private String answerKeyName;
	private String reason;
	
	public Result() {
		this(false, "", "");
	}
	
	public Result(boolean pass, String answerKeyName) {
		this(pass, answerKeyName, "");
	}
	
	public Result(boolean pass, String answerKeyName, String reason) {
		this.pass = pass;
		this.answerKeyName = answerKeyName;
		this.reason = reason;
	}
	
	public boolean didPass() {
		return pass;
	}
	
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	
	public String getAnswerKeyName() {
		return answerKeyName;
	}
	
	public void setAnswerKeyName(String answerKeyName) {
		this.answerKeyName = answerKeyName;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return getAnswerKeyName();
	}
	
	
}

package constant;

public enum OperationFeedback {
	
	VALID,
	INVALID_DATE_TIME,
	INVALID_DATE,
	INVALID_TIME,
	INVALID_TASK_DETAILS,
	INVALID_LABEL,
	NOT_FOUND,
	
	INVALID_INCORRECTLOGIN, //incorrect username or password
	INVALID_NOINTERNET; //not connect to internet
	
	
	private String msg;
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}
}

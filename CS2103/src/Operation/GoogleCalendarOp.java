/**
 * extends Operation
 * implements functionality pertaining to syncing with google calendar
 * 
 * @author Shubhendra Agrawal
 */
package operation;


import constant.OperationFeedback;
import data.Task;
import storagecontroller.StorageManager;
import gcal.GoogleCalendar;

public class GoogleCalendarOp extends Operation {

	
	
	@Override
	/**
	 * executes the required aspect of google calendar 
	 * 		synchronization depending upon the keyword entered by the user
	 * @param userCommand
	 * @return Task array
	 */
	public Task[] execute(String userCommand) {
		isUndoAble = false;

		// TODO Auto-generated method stub
		if(userCommand.toLowerCase().startsWith("logout")) {
			if (StorageManager.getGCalObject() == null) {
				feedback=OperationFeedback.USER_NOT_LOGGEDIN;
				return null;	
			} else if (StorageManager.getGCalObject().isLoggedIn()) {
				return logout();
			} else {
				feedback = OperationFeedback.USER_NOT_LOGGEDIN;
				return null;	
			} 
		} else if (userCommand.toLowerCase().startsWith("login")) {
			return login(userCommand);
		}
		else if (userCommand.toLowerCase().startsWith("sync.gcal")
				|| userCommand.toLowerCase().startsWith("syncgcal")) {
			return sync();
		}
		else if (userCommand.toLowerCase().startsWith("import.gcal")
				|| userCommand.toLowerCase().startsWith("importgcal")) {
			return importTasks();
		}
		else if (userCommand.toLowerCase().startsWith("export.gcal")
				|| userCommand.toLowerCase().startsWith("exportgcal")) {
			return exportTasks();
		}
		
		return null;
		
		
	}

	/**
	 * calls for the export task functionality of Google Calendar 
	 * 
	 * @return Task array
	 */
	private Task[] exportTasks() {
		// TODO Auto-generated method stub
		GoogleCalendar obj = StorageManager.getGCalObject();
		if (obj != null) {
			if (obj.isLoggedIn()) {
			
				if (StorageManager.getGCalObject().exportToGcal()) {
					return null;
				} else {
					feedback = OperationFeedback.INVALID_NOINTERNET;
					return null;
				}
			} else {
				feedback = OperationFeedback.USER_NOT_LOGGEDIN;
				return null;
			} 
		} else {
			feedback = OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
		
	}

	/**
	 * calls for the import task functionality of Google Calendar 
	 * 
	 * @return Task array
	 */
	private Task[] importTasks() {
		// TODO Auto-generated method stub
		GoogleCalendar obj=StorageManager.getGCalObject();
		if (obj != null){
			if (obj.isLoggedIn()){
				if (StorageManager.getGCalObject().importFromGcal()){
					return null;
				} else{
					feedback = OperationFeedback.INVALID_NOINTERNET;
					return null;
				}
			} else {
				feedback = OperationFeedback.USER_NOT_LOGGEDIN;
				return null;
			}
		} else {
			feedback = OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
	}

	/**
	 * calls for the sync task functionality of Google Calendar 
	 * under this tasks on our calendar take precedence over the google calendar tasks
	 * @return Task array
	 */
	private Task[] sync() {
		// TODO Auto-generated method stub
		GoogleCalendar obj = StorageManager.getGCalObject();
		if (obj != null){
			if (obj.isLoggedIn()){
				if (StorageManager.getGCalObject().sync()){
					return null;
				} else{
					feedback = OperationFeedback.INVALID_NOINTERNET;
					return null;
				}
			} else{
				feedback = OperationFeedback.USER_NOT_LOGGEDIN;
				return null;
			}
		} else{
			feedback = OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
	}

	/**
	 * 
	 * calls for the login task functionality of Google Calendar 
	 * 
	 * 
	 * @param userCommand
	 * @return Task array
	 */
	private Task[] login(String userCommand) {
		// TODO Auto-generated method stub
		if (StorageManager.getGCalObject() != null)
		{
			if(StorageManager.getGCalObject().isLoggedIn())
			{
				feedback = OperationFeedback.USER_ALREADY_LOGGED_IN;
				return null;
			}
		}
		userCommand.trim().replaceAll("login","");
	
		String params[] = userCommand.split("\\s+");
		if (params.length != 3)
		{
			feedback = OperationFeedback.INVALID_INCORRECTLOGIN;
			return null;
		}
		String username = params[1]; 
		String password = params[2];
		
		GoogleCalendar obj = new GoogleCalendar();
		obj.login(username, password);
		if (obj.isLoggedIn()){
			StorageManager.setGCalObject(obj);
			feedback=OperationFeedback.VALID;
			return null;
			
		} else{
			feedback = OperationFeedback.INVALID_INCORRECT_LOGIN_INTERNET_CONNECTION;
			return null;
		}
	
	}

	/**
	 * calls for the logout task functionality of Google Calendar 
	 * 
	 * @return Task array
	 */
	private Task[] logout() {
		// TODO Auto-generated method stub
		if (StorageManager.getGCalObject() != null) {
			if (StorageManager.getGCalObject().logout()){
				StorageManager.setGCalObject(null);
				feedback = OperationFeedback.LOGGED_OUT_SUCCESSFULLY;
				return null;
			} else {
				feedback = OperationFeedback.LOGOUT_FAILED;
				return null;
			}
		} else {
			feedback = OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
	}

	@Override
	/**
	 * undo is irrelevant
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * redo is irrelevant
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return whether undoable or not
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	/**
	 * @return the operation feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
    	
	@Override
	/**
	 * @return the operation name
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return "googlecalendar";
	}

}

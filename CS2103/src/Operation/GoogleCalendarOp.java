package operation;

import org.apache.log4j.Logger;

import constant.OperationFeedback;

import data.Task;
import storagecontroller.StorageManager;
import gcal.GoogleCalendar;
public class GoogleCalendarOp extends Operation {

	private static Logger logger=Logger.getLogger(GoogleCalendarOp.class);
	@Override
	public Task[] execute(String userCommand) {
		isUndoAble=false;
		logger.debug(userCommand);
		// TODO Auto-generated method stub
		if(userCommand.toLowerCase().startsWith("logout")){
			if (StorageManager.getGCalObject()==null){
				feedback=OperationFeedback.USER_NOT_LOGGEDIN;
				return null;	
			}else if (StorageManager.getGCalObject().isLoggedIn()){
				return logout();
			}
			else{
				feedback=OperationFeedback.USER_NOT_LOGGEDIN;
				return null;	
			}
		}
		else if (userCommand.toLowerCase().startsWith("login"))
		{
			return login(userCommand);
			
		
		}
		else if (userCommand.toLowerCase().startsWith("sync.gcal"))
		{
			return sync();
			
		}
		else if (userCommand.toLowerCase().startsWith("import.gcal"))
		{
			return importTasks();
			
		}
		else if (userCommand.toLowerCase().startsWith("export.gcal"))
		{
			return exportTasks();
			
		}
		
		return null;
		
		
	}

	private Task[] exportTasks() {
		// TODO Auto-generated method stub
		GoogleCalendar obj=StorageManager.getGCalObject();
		if (obj!=null){
			if (obj.isLoggedIn()){
			
			if (StorageManager.getGCalObject().exportToGcal()){
				logger.debug("logged and exported successfully");
				return null;
			}
			else{
				feedback=OperationFeedback.INVALID_NOINTERNET;
				return null;
				
				
			}
		}
		else
		{
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
		}
		else
		{
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
		
	}

	private Task[] importTasks() {
		// TODO Auto-generated method stub
		GoogleCalendar obj=StorageManager.getGCalObject();
		if (obj!=null){
		if (obj.isLoggedIn()){
			
			if (StorageManager.getGCalObject().importFromGcal()){
				logger.debug("logged and imported successfully");
				return null;
			}
			else{
				feedback=OperationFeedback.INVALID_NOINTERNET;
				return null;
				
			}
		}
		else {
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
		}
		else
		{
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
	}

	private Task[] sync() {
		// TODO Auto-generated method stub
		GoogleCalendar obj=StorageManager.getGCalObject();
		if (obj!=null){
		if (obj.isLoggedIn()){
		
			
			Thread t= new Thread(StorageManager.getGCalObject());
			t.start();
			return null;
		}
		else{
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
		}
		else{
			feedback=OperationFeedback.USER_NOT_LOGGEDIN;
			return null;
		}
	}

	private Task[] login(String userCommand) {
		// TODO Auto-generated method stub
		if (StorageManager.getGCalObject()!=null)
		{
			if(StorageManager.getGCalObject().isLoggedIn())
			{
				feedback=OperationFeedback.USER_ALREADY_LOGGED_IN;
				return null;
			}
		}
		userCommand.trim().replaceAll("login","");
		logger.debug(userCommand); 
		String params[]=userCommand.split("\\s+");
		if (params.length!=3)
		{
			feedback=OperationFeedback.INVALID_INCORRECTLOGIN;
			return null;
		}
		String username=params[1]; 
		String password=params[2];
		logger.debug(username);
		logger.debug(password);
		GoogleCalendar obj=new GoogleCalendar();
		obj.login(username, password);
		if (obj.isLoggedIn()){
			StorageManager.setGCalObject(obj);
			
			logger.debug("Logged in");
			return null;
			
		}
		else{
			feedback=OperationFeedback.INVALID_INCORRECT_LOGIN_INTERNET_CONNECTION;
			return null;
		}
	
	}

	private Task[] logout() {
		// TODO Auto-generated method stub
		if (StorageManager.getGCalObject().logout()){
			StorageManager.setGCalObject(null);
			feedback=OperationFeedback.LOGGED_OUT_SUCCESSFULLY;
			return null;
		}
		else {
			feedback=OperationFeedback.LOGOUT_FAILED;
			return null;
		}
	}

	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}


	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
    
	
	
	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return "googlecalendar";
	}

}

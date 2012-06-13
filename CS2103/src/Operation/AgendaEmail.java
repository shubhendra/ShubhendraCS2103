/**
 * extends Operation
 * this class is used to check for an email to send to the user abt reminder updates
 * used to activate the thread to send out the email automatically
 * 
 * @author Shubhendra Agrawal
 */
package operation;


import org.apache.log4j.Logger;
import sendMail.Agenda;
import storagecontroller.StorageManager;
import constant.OperationFeedback;
import data.Task;

public class AgendaEmail extends Operation{
	private static Logger logger = Logger.getLogger(AgendaEmail.class);
	private static String commandName;

	@Override
	/**
	 * @param userCommand
	 * 			- consists of the keyword for this class and the email address 
	 * 					of the user
	 * @return a Task[]
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		String email = "";
		
		userCommand = userCommand.toLowerCase().replace("agendaemail ", "");
		if (userCommand.trim().equals("")){
			logger.debug("true");
			feedback = OperationFeedback.NO_EMAIL_SPECIFIED;
			return null;	
		}
		else 
			email = userCommand;
		Thread mailThread = new Thread(new Agenda(18,23,0, email));
		StorageManager.saveEmailId(email);
		mailThread.run();
		return new Task[1];
	}

	@Override
	/**
	 * undo is irrelevant in this case
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * redo is irrelevant in this case
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return isUndoAble
	 * 			tells whether the function is undo able or not;
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	@Override
	/**
	 * @return feedback
	 * 			returns the feedback or status of the operation
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}

	@Override
	/**
	 * @return commandName
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

}


/**
 * extends Operation
 * implements the functionality to edit a given task's details
 * 
 * @author Shubhendra Agrawal
 */
package operation;

import constant.OperationFeedback;
import parser.Parser;
import data.Task;

import org.apache.log4j.Logger;

import storagecontroller.StorageManager;

public class Modify extends BaseSearch{
	
	private static Logger logger= Logger.getLogger(Modify.class);
	private Task oldTask;
	private Task newTask;
	private static Task taskBeingEdited = null;

	/**
	 * constructor
	 */
	public Modify(){
		commandName = "modify";
	}
	
	/**
	 * Constructor -
	 * 		cancels the edit operation if abandoned prematurely
	 * @param intendedOperation
	 */
	public Modify(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
		if (commandName == "canceledit") {
			taskBeingEdited = null;
		}
		
	}

	
	/**
	 * implements the edit operation on one task
	 * 
	 * @param taskToBeEdited
	 * @return Task array of the affected tasks
	 */
	public Task[] execute(Task taskToBeEdited){
		if(taskBeingEdited == null) {
			taskBeingEdited = StorageManager.getTaskById(taskToBeEdited.getTaskId());
			return new Task[] {taskBeingEdited};
		} else{
			
			logger.debug(taskToBeEdited.getName());
			boolean isEdited = modify(taskBeingEdited, taskToBeEdited);
			if(isEdited) {
				isUndoAble = true;
				
				oldTask = taskBeingEdited;
				newTask = taskToBeEdited;
				taskBeingEdited = null;
				return new Task[] {taskToBeEdited};
				
			}
			feedback = OperationFeedback.EDIT_FAILED;
			return null;	
		}
	}

	/**
	 * tells the task was replaced successfully
	 * 
	 * @param oldTask
	 * @param newTask
	 * @return true/false 
	 */
	private boolean modify(Task oldTask, Task newTask) {
		return StorageManager.replaceTask(oldTask, newTask);
		
	}

	@Override
	/**
	 * implements undo by replacing the edited task with old ones
	 * @return the old task that replaced the new Task
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		Task[] undoneArray = new Task[1];
		if (modify(newTask, oldTask)) {
			undoneArray[0] = oldTask;
			undoRedoFeedback=OperationFeedback.UNDO_SUCCESSFUL;
			return undoneArray;
		}
		return null;
		
	}

	@Override
	/**
	 * @return whether the task can be undone or not
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
		return commandName;
	}

	@Override
	/**
	 * 
	 * calls for the superclass object if no task is there to be edited
	 * if there is a task to be edited, calls the necessary edit functions
	 * @param userCommand
	 * @return Task array of edited tasks
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
				
		if (taskBeingEdited == null)
		{
			return super.execute(userCommand);
		}
		
		else
		{
			String params = userCommand.toLowerCase().replaceFirst(commandName+" ","");
			Task taskToBeEdited = parseTask(params);
			logger.debug(feedback);
			if (taskToBeEdited != null && feedback == OperationFeedback.VALID) {
				
					return execute(taskToBeEdited);
				
				
			}
			else if (taskToBeEdited != null && feedback != OperationFeedback.VALID ) {
				return null;
			}
			else {
				feedback = OperationFeedback.EDIT_FAILED;
				return null;
			}
				
			
		}
	}


	/**
	 * parses the task on the basis of User Input
	 * @param userCommand
	 * @return task
	 */
	private Task parseTask(String userCommand) {
		// TODO Auto-generated method stub
		
		Parser newParser = new Parser();
		Task parsedTask= newParser.parseForSearch(userCommand);
		feedback = newParser.getErrorCode();
		return parsedTask;
		
	}
	
	/**
	 * implements the redo functionality by replacing the old event with the 
	 * 		new event
	 * @return Task array with the new task that replaced the old Task
	 */
	public Task[] redo(){
		Task[] redoneArray = new Task[1];
		if (modify(oldTask, newTask)) {
			redoneArray[0] = newTask;
			undoRedoFeedback=OperationFeedback.REDO_SUCCESSFUL;
			return redoneArray;
		}
		return null;
	}


}


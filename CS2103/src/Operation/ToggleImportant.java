/**
 * extends Operation
 *  implements the toggle important functionality for the given tasks
 * 
 * @author Shubhendra Agrawal
 * 
 */
package operation;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import constant.OperationFeedback;
import storagecontroller.StorageManager;
import data.Task;


public class ToggleImportant extends BaseSearch {

	
	private static Logger logger = Logger.getLogger(BaseSearch.class);
	private ArrayList<Task> taskStarred = new ArrayList<Task>();
	
	/**
	 * constructor
	 */
	public ToggleImportant(){
		commandName = "star";
	}
	
	/**
	 * implements the toggle important functionality for all recurring tasks
	 * 		in one go
	 * 
	 * @param taskToStar
	 * @return task array of all tasks whose important status is toggled
	 */
	public Task[] executeAll(Task taskToStar) {
		// TODO Auto-generated method stub
		if (taskToStar.getRecurringId()=="") {
			return execute(taskToStar);
		}
		Task[] taskToBeStarred = StorageManager
				.getTaskByRecurrenceID(taskToStar.getRecurringId());
		logger.debug(taskToBeStarred.length);
		for (int i = 0 ; i < taskToBeStarred.length ; i++)
		{
			boolean starred = toggleImportant(taskToBeStarred[i]);
			if (starred) {
				isUndoAble = true;
				taskStarred.add(taskToBeStarred[i]);
				logger.debug("starred succesfully");
				
			} else {
				return null;
			}
		}
		if (taskStarred.size()!=0) {
			return (Task[])taskStarred.toArray(new Task[taskStarred.size()]);
		} else {
			return null;
		}
	}
	/**
	 * constructor
	 * @param intendedOperation
	 */
	public ToggleImportant(String intendedOperation){
		commandName = intendedOperation;
	}
	
	/**
	 implements the toggle important functionality for one task
	 * 
	 * @param taskToBeStarred
	 * @return task array of task whose important status is toggled 
	 */	
	public Task[] execute(Task taskToBeStarred){
		Task taskToStar = StorageManager
				.getTaskById(taskToBeStarred.getTaskId());

		boolean starred = toggleImportant(taskToStar);
		if (starred) {
			isUndoAble = true;
			taskStarred.add(taskToStar);
			Task[] resultOfStar = new Task[1];
			resultOfStar[0] = taskToStar;
			logger.debug("starred succesfully");
			return resultOfStar;
		}

		return null;
		
	}

	/**
	 * toggles the important status
	 * @param taskToStar
	 * @return true if toggle was successful
	 */
	private boolean toggleImportant(Task taskToStar) {
		// TODO Auto-generated method stub
		Task starredTask = StorageManager.getTaskById(taskToStar.getTaskId());
		if (starredTask != null){
			starredTask.toggleImportant();
		
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	/**
	 * undo reverts back the important status
	 * @return the tasks whose important status was toggled
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks = new ArrayList<Task>();
		for (int i = 0 ; i < taskStarred.size() ; i++){
			Task starredTask=StorageManager.getTaskById(taskStarred.get(i).getTaskId());
			if (starredTask != null){
				starredTask.toggleImportant();
				logger.debug("Can undo");
				undoneTasks.add(starredTask);
			}
			
		}
		if (undoneTasks.size() != 0) {
			return undoneTasks.toArray(new Task[undoneTasks.size()]);
		}
		else { 
			return null;
		}
	}

	@Override
	/**
	 * redo reverts back the important status that was undone
	 * @return the tasks whose important status was toggled
	 */
	public Task[] redo() {
		
		// TODO Auto-generated method stub
		ArrayList<Task> redoneTasks=new ArrayList<Task>();
		for (int i=0;i<taskStarred.size();i++){
			Task starredTask = StorageManager.getTaskById(taskStarred.get(i).getTaskId());
			if (starredTask != null){
				starredTask.toggleImportant();
				logger.debug("Can undo");
				redoneTasks.add(starredTask);
			}
			
		}
		if (redoneTasks.size() != 0) {
			return redoneTasks.toArray(new Task[redoneTasks.size()]);
		} else { 
			return null;
		}
	}

	@Override
	/**
	 * @return whether the task is undoable
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	/**
	 * @return the operation Feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
    
	
	@Override
	/**
	 * @return Operation Name
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

}

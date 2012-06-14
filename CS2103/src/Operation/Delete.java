/**
 * extends Operation
 * 
 * This class implements the functionality related to the delete Operation
 * 		on tasks.
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

public class Delete extends BaseSearch {
	
	private Logger logger = Logger.getLogger(Delete.class);
	private ArrayList<Task> taskDeleted = new ArrayList<Task>();
	
	/**
	 * Constructor
	 */
	public Delete(){
		commandName = "delete";
	}
	
	/**
	 * Constructor
	 * @param intendedOperation
	 */
	public Delete(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
	}

	/**
	 * Deletes a task
	 * @param taskToBeDeleted
	 * @return true if deleted successfully and false otherwise
	 */
	public boolean delete(Task taskToBeDeleted)
	
	{
		if (taskToBeDeleted != null)
		{
			return StorageManager.deleteTask(taskToBeDeleted);
		}
		return false;
	}

	/**
	 * executes the delete functionality
	 * @return the array of tasks deleted
	 */
	public Task[] execute(Task taskToBeDeleted)
	{
		Task taskToDelete = StorageManager
				.getTaskById(taskToBeDeleted.getTaskId());

		
		boolean deleted = delete(taskToDelete);
		if (deleted) {
			isUndoAble = true;
			logger.debug("isUndoAble value changed" );
			taskDeleted.add(taskToDelete);
			Task[] resultOfDelete = new Task[1];
			resultOfDelete[0] = taskToDelete;
			logger.debug("deleted succesfully");
			return resultOfDelete;
		} else {
			feedback = OperationFeedback.DELETE_FAILED;
			return null;
		}
		
		
	}
	@Override
	/**
	 * implements the undo functionality for delete by 
	 * 		adding the task back into liveStorage
	 * @return the array of tasks added back
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks=new ArrayList<Task>();
		Add addObject = new Add();
		for (int i = 0 ; i < taskDeleted.size() ; i++) {
			
			if (addObject.add(taskDeleted.get(i))) {
				undoneTasks.add(taskDeleted.get(i));
			}
			
		}
		if (undoneTasks.size()!=0) {
			return undoneTasks.toArray(new Task[undoneTasks.size()]);
		} else { 
			return null;
		}
		
		
	}

	@Override
	/**
	 * @return whether the functionality is undoable
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}


	/**
	 * @return the Operation feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
    
	/**
	 * 
	 * this function is used to delete the recurring tasks in one go
	 * 
	 * @param taskToBeDeleted
	 * @return array of tasks deleted
	 */
	public Task[] executeAll(Task taskToBeDeleted) {
		// TODO Auto-generated method stub
		if (taskToBeDeleted.getRecurringId() == "") {
			return execute(taskToBeDeleted);
		}
		Task[] taskToDelete = StorageManager
				.getTaskByRecurrenceID(taskToBeDeleted.getRecurringId());
		logger.debug(taskToDelete.length);
		for (int i=0;i<taskToDelete.length;i++) {
		
			boolean deleted = delete(taskToDelete[i]);
			if (deleted) {
				isUndoAble = true;
				logger.debug("isUndoAble value changed" );
				taskDeleted.add(taskToDelete[i]);
				
				logger.debug("deleted succesfully");
			} else {
				feedback=OperationFeedback.DELETE_FAILED;
				return null;
			}
		}
		if (taskDeleted.size() == 0) {
			feedback = OperationFeedback.NO_TASK_DELETED;
			return null;
		} else {
			return (Task[])taskDeleted.toArray(new Task[taskDeleted.size()]);
		}
	}

	@Override
	/**
	 * @return the operation name
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}
	/**
	 * implements the redo functionality by re - deleting the undone tasks
	 * @return the array of tasks deleted again;
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		ArrayList<Task> redoneTasks = new ArrayList<Task>();
		
		for (int i = 0 ; i < taskDeleted.size() ; i++) {
			
			if (delete(taskDeleted.get(i))) {
				redoneTasks.add(taskDeleted.get(i));
			}
			
		}
		if (redoneTasks.size() != 0) {
			return redoneTasks.toArray(new Task[redoneTasks.size()]);
		} else { 
			return null;
		}
		
	}


	
	

}

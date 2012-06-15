/**
 * extends Operation
 * implements the toggle completed functionality for the given tasks
 * 
 * @author Shubhendra Agrawal
 */
package operation;

import java.util.ArrayList;
import constant.OperationFeedback;
import storagecontroller.StorageManager;
import data.Task;

public class ToggleCompleted extends BaseSearch{
	

	private ArrayList<Task> taskCompleted = new ArrayList<Task>();
	
	/**
	 * constructor
	 */
	public ToggleCompleted(){
		commandName = "completed";
	}
	
	/**
	 * constructor
	 * @param intendedOperation
	 */
	public ToggleCompleted(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
	}
	
	/**
	 * implements the toggle complete functionality for all recurring tasks
	 * 		in one go
	 * 
	 * @param taskToComplete
	 * @return task array of all tasks whose complete status is toggled
	 */
	public Task[] executeAll(Task taskToComplete) {
		// TODO Auto-generated method stub
		if (taskToComplete.getRecurringId() == "") {
			return execute(taskToComplete);
		}
		Task[] taskToBeCompleted = StorageManager
				.getTaskByRecurrenceID(taskToComplete.getRecurringId());
		
		for (int i=0;i<taskToBeCompleted.length;i++)
		{
			boolean completed = toggleComplete(taskToBeCompleted[i]);
			if (completed) {
				isUndoAble = true;
				taskCompleted.add(taskToBeCompleted[i]);
			
				
				//return resultOfComplete;
			} else{
				feedback=OperationFeedback.COMPLETE_FAILED;
			}
		}
		if (taskCompleted.size() != 0) {
			return (Task[]) taskCompleted.toArray(new Task[taskCompleted.size()]);
		} else {
			feedback = OperationFeedback.NO_TASK_COMPLETED;
			return null;
		}
	}
	
	/**
	 implements the toggle complete functionality for one task
	 * 
	 * @param taskToBeComplete
	 * @return task array of task whose complete status is toggled 
	 */
	public Task[] execute(Task taskToBeCompleted){
		Task taskToComplete = StorageManager
				.getTaskById(taskToBeCompleted.getTaskId());

		boolean completed = toggleComplete(taskToComplete);
		if (completed) {
			isUndoAble = true;
			taskCompleted.add(taskToComplete);
			Task[] resultOfComplete = new Task[1];
			resultOfComplete[0] = taskToComplete;
			return resultOfComplete;
		}
		feedback = OperationFeedback.COMPLETE_FAILED;
		return null;
		
	}

	/**
	 * toggles the complete status
	 * @param taskToComplete
	 * @return true if toggle was successful
	 */
	private boolean toggleComplete(Task taskToComplete) {
		// TODO Auto-generated method stub
		Task completeTask=StorageManager.getTaskById(taskToComplete.getTaskId());
		if (completeTask != null){
			completeTask.toggleCompleted();
		
			return true;
		} else {
			return false; 
		}
		
	}
	@Override
	/**
	 * undo reverts back the complete status
	 * @return the tasks whose complete status was toggled
	 */
	public Task[] undo() {
		ArrayList<Task> undoneTasks=new ArrayList<Task>();
		for (int i = 0 ; i < taskCompleted.size() ; i++) {
			Task completeTask=StorageManager.getTaskById(taskCompleted.get(i).getTaskId());
			if (completeTask != null){
				completeTask.toggleCompleted();
				
				undoneTasks.add(completeTask);
			}
			
		}
		if (undoneTasks.size() != 0) {
			undoRedoFeedback=OperationFeedback.UNDO_SUCCESSFUL;
			return undoneTasks.toArray(new Task[undoneTasks.size()]);
		} else {
			return null;
		}
	}

	@Override
	/**
	 * @return whether the operation is undoable
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	/**
	 * @return operation feedback 
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
    @Override
    /**
     * @return Operation name
     */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}
	
	@Override
	/**
	 * redo reverts back the complete status of the task
	 * @return the task array of tasks whose complete status was toggled
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		ArrayList<Task> redoneTasks = new ArrayList<Task>();
		for (int i = 0 ; i < taskCompleted.size() ; i++){
			Task completeTask=StorageManager.getTaskById(taskCompleted.get(i).getTaskId());
			if (completeTask != null){
				completeTask.toggleCompleted();
				redoneTasks.add(completeTask);
			}
			
		}
		if (redoneTasks.size() != 0) {
			undoRedoFeedback=OperationFeedback.REDO_SUCCESSFUL;
			return redoneTasks.toArray(new Task[redoneTasks.size()]);
		} else { 
			return null;
		}
	}

	
	
	
	
}

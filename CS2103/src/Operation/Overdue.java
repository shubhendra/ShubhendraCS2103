/**
 * extends Operation
 * Implements the overdue feature by returning a list of task that need to be 
 * 		completed before current time and have not been completed
 * @author Shubhendra Agrawal
 */
package operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import data.TaskDateTime;
import data.Task;
import data.CompareByDate;
import org.apache.log4j.Logger;
import constant.OperationFeedback;
import storagecontroller.StorageManager;

public class Overdue extends Operation {
	
	private String commandName;
	private static Logger logger=Logger.getLogger(Overdue.class);
	
	/**
	 * constructor
	 */
	public Overdue(){
		commandName = "overdue";
	}
	
	/**
	 * constructor
	 * @param intendedOperation
	 */
	public Overdue(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
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
	 * @return whether the function is undoable
	 * 
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
	 * @return operation name
	 * 
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

	@Override
	/**
	 * implements the overdue functionality by returning overdue not completed tasks
	 * 
	 * @param userCommand
	 * @return the task array of overdue tasks
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		TaskDateTime currDateTime =	TaskDateTime.getCurrentDateTime();
		Comparator<Task> compareByDate = new CompareByDate();
		TaskDateTime defaultDateTime = new TaskDateTime();
		logger.debug(currDateTime.formattedToString());
		Task[] allTasks=StorageManager.getAllTasks();
		ArrayList<Task> overdueTasks = new ArrayList<Task>();
		for (Task curTask : allTasks)
		{
			if (curTask.getStart() != null
					&& curTask.getStart().getTimeMilli()
					!= defaultDateTime.getTimeMilli()) {
				if (curTask.getStart().compareTo(currDateTime) == -1 
						&& !curTask.getCompleted()) {
					overdueTasks.add(curTask);
				}
			} else if (curTask.getEnd() != null 
						&& curTask.getEnd().getTimeMilli() != defaultDateTime.getTimeMilli()) {
				if (curTask.getEnd().compareTo(currDateTime) == -1 
						&& !curTask.getCompleted()) {
					overdueTasks.add(curTask);
				}
			} else {}
		}
		if (overdueTasks.size() == 0) {
			feedback = OperationFeedback.NO_TASK_OVERDUE;
			return null;
		} else {
			Collections.sort(overdueTasks,compareByDate);
			return (Task[]) overdueTasks.toArray(new Task[overdueTasks.size()]);
		}
		
		
	
	}

	@Override
	/**
	 * redo is irrelevant
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

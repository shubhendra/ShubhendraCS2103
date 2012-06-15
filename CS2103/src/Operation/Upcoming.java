/**
 * 
 */
package operation;

import java.util.ArrayList;

import storagecontroller.StorageManager;
import constant.OperationFeedback;
import data.Task;
import data.TaskDateTime;

/**
 * @author Shubhendra Agrawal
 *
 */
public class Upcoming extends Operation {

	private static final long UPCOMING_TASK_DURATION_MILLI = 7*24*3600*100;
	/**
	 * executes the upcoming functionality
	 * @return the task array of tasks in the next week 
	 */
	@Override
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		
		Task[] allTasks = StorageManager.getAllTasks();
		ArrayList<Task> upcomingTasks = new ArrayList<Task>();
		for (int i = 0; i < allTasks.length; i++) {
			if (allTasks[i].getStart()!=null) {
				long timeDiff = allTasks[i].getStart().getTimeMilli() 
						- TaskDateTime.getCurrentDateTime().getTimeMilli();
				if (timeDiff<UPCOMING_TASK_DURATION_MILLI) {
					upcomingTasks.add(allTasks[i]);
				}
				
			}
		}
		
		
		if (upcomingTasks.size() != 0) {
			return (Task[]) upcomingTasks.toArray(new Task[upcomingTasks.size()]);
		}
		else {
			feedback = OperationFeedback.NO_UPCOMING_TASKS_IN_COMING_WEEK;
			return null;
		}
	}

	/**
	 * undo is irrelevant
	 */
	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * redo is irrelevant
	 */
	@Override
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return whether the Operation is undoable or not
	 */
	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return the operation feedback
	 */
	@Override
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}

	/**
	 * @return the operation name
	 */
	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return "upcoming";
	}

}

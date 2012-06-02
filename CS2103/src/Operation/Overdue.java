package operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import data.DateTime;
import data.Task;
import data.CompareByDate;

import org.apache.log4j.Logger;

import storagecontroller.StorageManager;
public class Overdue extends Operation {
	
	private String commandName;
	private static Logger logger=Logger.getLogger(Overdue.class);
	public Overdue(){
		commandName="overdue";
	}
	
	public Overdue(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName=intendedOperation;
	}

	
	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	@Override
	public boolean isInputCorrect(String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "Overdue Tasks cannot be displayed";
	}

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

	@Override
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		DateTime currDateTime =	DateTime.getCurrentDateTime();
		Comparator<Task> compareByDate=new CompareByDate();
		DateTime defaultDateTime = new DateTime();
		logger.debug(currDateTime.formattedToString());
		Task[] allTasks=StorageManager.getAllTasks();
		ArrayList<Task> overdueTasks=new ArrayList<Task>();
		for (Task curTask : allTasks)
		{
			if (curTask.getStartDateTime() != null
					&& curTask.getStartDateTime().getTimeMilli()
					!= defaultDateTime.getTimeMilli())
			{
				if (curTask.getStartDateTime().compareTo(currDateTime)==-1)
				{
					overdueTasks.add(curTask);
				}
			}
			else if (curTask.getEndDateTime()!=null 
						&& curTask.getEndDateTime().getTimeMilli()!= defaultDateTime.getTimeMilli())
			{
				if (curTask.getEndDateTime().compareTo(currDateTime)==-1)
				{
					overdueTasks.add(curTask);
				}
			}
			else
			{}
		}
		if (overdueTasks.size()==0)	{
			return null;
		} else {
			Collections.sort(overdueTasks,compareByDate);
			return (Task[]) overdueTasks.toArray(new Task[overdueTasks.size()]);
		}
		
		
	
	}

	@Override
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

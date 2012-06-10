package operation;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import constant.OperationFeedback;

import storagecontroller.StorageManager;

import data.Task;

public class Delete extends BaseSearch {
	
	private Logger logger=Logger.getLogger(Delete.class);
	private ArrayList<Task> taskDeleted=new ArrayList<Task>();
	public Delete(){
		commandName="delete";
	}
	public Delete(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName=intendedOperation;
	}

	
	public boolean delete(Task taskToBeDeleted)
	
	{
		if (taskToBeDeleted!=null)
		{
			return StorageManager.deleteTask(taskToBeDeleted);
		}
		return false;
	}

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
		}

		return null;
		
	}
	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks=new ArrayList<Task>();
		Add addObject = new Add();
		for (int i=0;i<taskDeleted.size();i++){
			
			if (addObject.add(taskDeleted.get(i))) {
				undoneTasks.add(taskDeleted.get(i));
			}
			
		}
		if (undoneTasks.size()!=0)
			return undoneTasks.toArray(new Task[undoneTasks.size()]);
		else 
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
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return null;
	}      
               
    
	
	

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}
	
	public Task[] redo() {
		// TODO Auto-generated method stub
		ArrayList<Task> redoneTasks=new ArrayList<Task>();
		//Add addObject = new Add();
		for (int i=0;i<taskDeleted.size();i++){
			
			if (delete(taskDeleted.get(i))) {
				redoneTasks.add(taskDeleted.get(i));
			}
			
		}
		if (redoneTasks.size()!=0)
			return redoneTasks.toArray(new Task[redoneTasks.size()]);
		else 
			return null;
		
	}


	
	

}

package operation;

import java.util.ArrayList;

import storagecontroller.StorageManager;
import constant.OperationFeedback;
import data.Task;

public class Archive extends Operation{
	
	private String commandName;
	public Archive(){
		commandName="archive";
	}
	
	public Archive(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName=intendedOperation;
	}

	public Task[] execute(){
		return null;
	}

	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInputCorrect(String command) {
		// TODO Auto-generated method stub
		return true;
	}

	

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return "archive";
	}

	@Override
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		
		if (userCommand.toLowerCase().startsWith("archive")){
			//return archiveTask();
		}
		else if(userCommand.toLowerCase()=="clear archive"){
			return clearArchive();
		}
		else if(userCommand.toLowerCase()=="export archive"){
			return exportArchive();
		}/*
		else if(userCommand.toLowerCase())
		Task[] allTasks=StorageManager.getAllTasks();
		ArrayList<Task> archiveTasks=new ArrayList<Task>();
		for (int i=0;i<allTasks.length;i++){
			if (allTasks[i].getCompleted()){
				StorageManager.addArchivedTask(allTasks[i]);
				StorageManager.deleteTask(allTasks[i]);
				archiveTasks.add(allTasks[i]);*
			}
		}*/
		
		
		return null;
	}

	private Task[] exportArchive() {
		// TODO Auto-generated method stub
		return null;
	}

	private Task[] clearArchive() {
		// TODO Auto-generated method stub
		return null;
	}

	private Task[] archiveTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

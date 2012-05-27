package operation;

import data.Task;

public class Delete extends BaseSearch {
	
	
	public Delete(){
		commandName="delete";
	}
	public Delete(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName=intendedOperation;
	}

	
	public boolean delete(Task taskToBeDeleted)
	
	{
		return false;
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
		return false;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

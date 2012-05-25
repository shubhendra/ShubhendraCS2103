package Operation;



import data.Task;
import parser.Parser;
import data.Storage2;

public class Add extends Operation {
	
	public Task[] execute (String userCommand)
	{
		
		String params = userCommand.toLowerCase().replace("add ","");
		Task newTask= Parser.parseCommand(params);
		

		boolean isAdded = Storage2.addTask(newTask);
		if (isAdded) {
			isundoable = true;
			Task[] resultOfAdd = new Task[1];
			resultOfAdd[0] = newTask;
			return resultOfAdd;
		} else {
			return null;
		}
		
	}
	@Override
	public boolean isInputCorrect(String command) {
		// TODO Auto-generated method stub
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
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

package operation;
import parser.Parser;
import data.Task;
import java.util.ArrayList;
import storagecontroller.StorageManager;

public class BaseSearch extends Operation {
	
	
	protected String commandName;
	
	public Task[] execute(String userCommand)
	{
		String params = userCommand.toLowerCase().replaceFirst(this.commandName+" ","");	
		
		ArrayList<task> deletedTasks=
		String[] extractedTaskIds=extractTaskIds(params);
		if(extractedTaskIds!=null)
		{
			for(int i=0;i<extractedTaskIds.length;i++)
			{
				
				StorageManager.deleteTask(extractedTaskIds[i]);
				
				
			}
			
		}
			
		
		return null;
	}

	private String[] extractTaskIds(String params) {
		// TODO Auto-generated method stub
		
		Parser newparser= new Parser();
		return newparser.extractTaskIds(params);
		
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

}

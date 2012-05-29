package operation;
import parser.Parser;
import data.Task;
import java.util.ArrayList;
import operation.Search;
import java.util.Collections;
import storagecontroller.StorageManager;
import org.apache.log4j.Logger;
public class BaseSearch extends Operation{
	
	
	protected String commandName;
	private static Logger logger=Logger.getLogger(BaseSearch.class);
	
	public Task[] execute(String userCommand)
	{
		String params = userCommand.toLowerCase().replaceFirst(this.commandName+" ","");
		logger.debug(params);
		logger.debug("inside basesearch");
		ArrayList<Task> foundTasks=new ArrayList<Task>();
				
		String[] extractedTaskIds=extractTaskIds(params);
		
		if(extractedTaskIds!=null)
		{
			logger.debug("going to the id part");
			logger.debug(extractedTaskIds[0]);
			logger.debug(extractedTaskIds[0].toUpperCase());
			//userCommand=userCommand.replace(extractedTaskIds[0],"");
			for(int i=0;i<extractedTaskIds.length;i++)
			{
				Task t=StorageManager.getTaskById(extractedTaskIds[i].toUpperCase());
				
				Task[] result=execute(t);
				
				if (result!=null)
				{
					Collections.addAll(foundTasks, result);
				}
				
				
			}
			
			return foundTasks.toArray(new Task[foundTasks.size()]);
		}
		
		else 
		{
			logger.debug("going for new search");
			Search f=new Search();
			Task findTask=parseEvent(params);
			logger.debug("finished searching");
			return f.search(findTask);
			
		}
			
		
		
	}

	private Task parseEvent(String userCommand) {
		// TODO Auto-generated method stub
		Parser newparser= new Parser();
		return newparser.parse(userCommand);
		
	}

	private String[] extractTaskIds(String params) {
		// TODO Auto-generated method stub
		
		Parser newparser= new Parser();
		String[] abc= new String[]{newparser.fetchTaskId(params)};
		if (abc[0]=="" || abc[0]==null)
			return null;
		return abc;
		
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
		return "baseSearch";
	}

}

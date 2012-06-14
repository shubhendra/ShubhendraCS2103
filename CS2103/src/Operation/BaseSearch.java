/**
 * extends Operation
 * 
 * this is the class that is used to provide the search functionality 
 * 		to various functions like delete, toggle completed, edit, toggle important etc.
 * After selection of a particular task it helps in performing operations on them 
 * 		individually as well as on multiple items.
 * 
 * @author Shubhendra Agrawal
 */
package operation;
import parser.Parser;
import data.Task;
import java.util.ArrayList;
import operation.Search;
import java.util.Collections;
import storagecontroller.StorageManager;
import constant.OperationFeedback;

public class BaseSearch extends Operation{
	
	
	protected String commandName;

	/**
	 * searches for the task based on the details entered by the user.
	 * if the UI sends in the task ID then executes the sub class functionality 
	 * 		on it using the execute/ execute all feature;
	 * 
	 * @param userCommand
	 * @return task array of the tasks on whom the operation is performed or the 
	 * 		search results
	 */
	public Task[] execute(String userCommand)
	{
		String params = userCommand.toLowerCase().replaceFirst(this.getOperationName() + " ", "");
	
		ArrayList<Task> foundTasks = new ArrayList<Task>();
				
		String[] extractedTaskIds = extractTaskIds(params);
		
		if(extractedTaskIds != null)
		{
			for(int i = 0 ; i < extractedTaskIds.length ; i++)
			{
				Task t = StorageManager.getTaskById(extractedTaskIds[i]);
				
				Task[] result;
				if (!commandName.contains(".all")){
					result = execute(t);
				} else {
					result = executeAll(t);
				}
				if (result != null) {
					Collections.addAll(foundTasks, result);
		
				}			
			}
			return foundTasks.toArray(new Task[foundTasks.size()]);
		} else {
		
			Search f = new Search();
			Task findTask = parseEvent(params);
			
			return f.search(findTask);
			
		}
			
		
		
	}
	/**
	 * protected function, implemented in the necessary subclasses
	 * @param t
	 * @return
	 */
	protected Task[] executeAll(Task t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * parses the string into a task on the basis of userInput
	 * @param userCommand
	 * @return Task that was parsed
	 * 			if cannot be parsed returns null
	 */
	private Task parseEvent(String userCommand) {
		// TODO Auto-generated method stub
		Parser newparser = new Parser();
		return newparser.parseForSearch(userCommand);
		
	}
	/**
	 * extracts the various task ids from the user input
	 * 
	 * @param params
	 * @return String array of task ids
	 */
	private String[] extractTaskIds(String params) {
		// TODO Auto-generated method stub
		
		Parser newparser = new Parser();
		
		String[] extractedIds = newparser.fetchTaskIds(params.toUpperCase());
		
		return extractedIds;
		
		
		
		
	}

	@Override
	/**
	 * undo is irrelevant for this class
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * none of the search operations in this class be undone
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return false;
	}


	

	@Override
	/**
	 * @return returns the name of this operation
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return "baseSearch";
	}

	@Override
	/**
	 * redo is irrelevant for this class
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return returns the operation feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}

}

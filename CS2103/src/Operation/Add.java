/**
 * extends Operation
 * 
 * This class implements the functionalities 
 * associated with adding a normal task or
 * recurring task.
 * 
 * @author Shubhendra Agrawal
 */
package operation;

//import org.apache.log4j.Level;
import java.util.ArrayList;
import constant.OperationFeedback;
import parser.Parser;
import storagecontroller.StorageManager;
import data.Task;



public class Add extends Operation {
	

    	private ArrayList<Task> addedTask = new ArrayList<Task>();
	private String commandName;

	/**
	 * Constructor
	 * @param command
	 */
	public Add (String command)
	{
		commandName = command;
	}

	/**
	 * Constructor
	 */
	public Add()
	{
		commandName = "add";
	}
	
	/**
	 * handles the core functionality of adding a task
	 * 
	 * @param userCommand
	 * 					- this is the command entered by the user
	 * @return Returns an array of tasks added if any
	 * 				in case nothing is added returns null value and 
	 * 				sets the required operation feedback for the ui to process
	 */
	public Task[] execute (String userCommand)
	{
		String params = null;
		params = userCommand.replaceFirst(getOperationName()+" ","");		
		ArrayList<Task> newTask = parseCommand(params);
		
		if (newTask != null)
		{
			for(int i = 0;i < newTask.size();i++){
				
				boolean isAdded = add(newTask.get(i));
				if (newTask.size() > 1){
				newTask.get(i).setRecurringId(newTask.get(0).getTaskId());
				} else{
					newTask.get(i).setRecurringId("");
				}
				if (isAdded) {
					isUndoAble = true;
					addedTask.add(newTask.get(i));
				}
				else {
					feedback = OperationFeedback.ADD_FAILED;
					return null;
				}
				
			}
			if (addedTask.size() != 0)
				return (Task[]) addedTask.toArray(new Task[addedTask.size()]);
			else 
				return null;
		}
		else{
			if (feedback == OperationFeedback.VALID){
				feedback = OperationFeedback.ADD_FAILED;
				
			}
			return null;
		}
		
	}
	
	/**
	 * parses the task details that the user has entered
	 * 			and returns a task ArrayList with multiple events in
	 * 			case of recurring events 
	 * @param params
	 * @return ArrayList<Task> 
	 * 			successfully parsed tasks, if none then returns null;
	 */
	private ArrayList<Task> parseCommand(String params) {
		// TODO Auto-generated method stub
		Parser newParser = new Parser();
		ArrayList<Task> TaskList = new ArrayList<Task>();
		Task[] parsedTasks = newParser.parseForAdd(params);
		feedback = newParser.getErrorCode();
		if (parsedTasks == null || parsedTasks.length == 0){
			return null;
		}
		
		for (int i = 0; i < parsedTasks.length ;i++){
			TaskList.add(parsedTasks[i]);
		}
		return TaskList;		
	}
	
	@Override
	/**
	 * @return Task array of the task that needs to be deleted in order 
	 * 					for this action to be undone
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks = new ArrayList<Task>();
		Delete delObject = new Delete();
		for (int i = 0 ; i < addedTask.size() ; i++){
			
			if (delObject.delete(addedTask.get(i))) {
				undoneTasks.add(addedTask.get(i));
			}
			
		}
		if (undoneTasks.size() != 0) {
			undoRedoFeedback=OperationFeedback.UNDO_SUCCESSFUL;
			return undoneTasks.toArray(new Task[undoneTasks.size()]);
		}
		else {
			return null;
		}
		
		
		
	}
	/**
	 * @return Task array of the Task that is added again 
	 * 					for redone to be completed
	 */
	public Task[] redo() {
		
		ArrayList<Task> redoneTasks = new ArrayList<Task>();
		
		for (int i = 0 ; i < addedTask.size() ; i++){
			
			if (add(addedTask.get(i))) {
				redoneTasks.add(addedTask.get(i));
			}
			
		}
		if (redoneTasks.size() != 0) {
			undoRedoFeedback=OperationFeedback.REDO_SUCCESSFUL;
			return redoneTasks.toArray(new Task[redoneTasks.size()]);
		}
		else { 
			return null;
		}
	}
	

	

	@Override
	/**
	 * @return the Operation name of add operation
	 * 
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

    
	/**
	 * 
	 * @param taskAdded
	 * @return True/false depending on whether the task can be added
	 */
    public boolean add(Task taskAdded) {
		// TODO Auto-generated method stub
		if (taskAdded != null)
		{
			return StorageManager.addTask(taskAdded);
		}
		return false;
	}

	@Override
	/**
	 * @return Various status messaged associated with adding of tasks
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}
	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}      
              
    
	
	
}

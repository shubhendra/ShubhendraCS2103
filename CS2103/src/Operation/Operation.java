

package operation;

import constant.OperationFeedback;

import org.apache.log4j.Logger;

import data.Task;


public abstract class Operation {

	
	
	
	protected boolean isUndoAble=false;
	private static Logger logger=Logger.getLogger(Operation.class);
	/**
	 * 
	 * @param userCommand
	 * @return
	 */
	public static Operation getOperationObj(String userCommand)
	{
		Operation object;
		
		String intendedOperation;
		intendedOperation=userCommand.trim().split("\\s+")[0];
		intendedOperation = intendedOperation.toLowerCase();
		logger.debug(intendedOperation);
		
		
		if (intendedOperation.equals("add") || intendedOperation.equals("insert")){
			object = new Add(intendedOperation);
			}
		else if (intendedOperation.equals("delete") || intendedOperation.equals("remove")){
			object = new Delete(intendedOperation);
		}
		else if (intendedOperation.equals("modify") || intendedOperation.equals("update") || 
				intendedOperation.equals("edit") || intendedOperation.equals("canceledit")) {
			object =new Modify(intendedOperation);
		}
		else if (intendedOperation.equals("search") || intendedOperation.equals("find")){
			object = new Search(intendedOperation);
		}
		else if (intendedOperation.equals("completed") || intendedOperation.equals("done")){
			object = new ToggleCompleted(intendedOperation);
		}
		else if(intendedOperation.equals("star") || intendedOperation.equals("important")){
			object = new ToggleImportant(intendedOperation);
		}
		else if (intendedOperation.equals("archive")){
			object = new Archive(intendedOperation);
		}
		else if (intendedOperation.equals("overdue")){
			object= new Overdue(intendedOperation);
		}
		else if (intendedOperation.equals("login")){
			object= new GoogleCalendarOp();
		}
		else
		{
			object=new Default();
		}

		
		
		return object; 
	}
	
	/**
	 * 
	 * @param userCommand
	 * @return
	 */
	public abstract Task[] execute(String userCommand);
	/**
	 * 
	 * @return
	 */
	public abstract Task[] undo();
	/**
	 * 
	 * @return
	 */
	public abstract Task[] redo();
	/**
	 * 
	 * @param taskToBeExecuted
	 * @return
	 */
	protected Task[] execute(Task taskToBeExecuted)
	{
		return null;
		
	}		
	public abstract boolean isUndoAble();
	/**
	 * 
	 * @param command
	 * @return
	 */
	public abstract boolean isInputCorrect(String command);
	/**
	 * 
	 * @return
	 */
	public abstract OperationFeedback getOpFeedback();
	/**
	 * 
	 * @return
	 */
	public abstract String getOperationName();
	
	

}

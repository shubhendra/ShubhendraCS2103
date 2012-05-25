package Operation;

import data.Task;
import parser.Parser;
public abstract class Operation {
	
	
	boolean isundoable=false;
	
	public static Operation getOperationObj(String userCommand)
	{
		Operation object;
		
		String intendedOperation;
		intendedOperation=userCommand.trim().split("\\s+")[0];
		intendedOperation = intendedOperation.toLowerCase();
		
		if (intendedOperation.equals("add") || intendedOperation.equals("insert")){
			object = new Add();
			}
		else if (intendedOperation.equals("delete") || intendedOperation.equals("remove")){
			object = new Delete();
		}
		else if (intendedOperation.equals("modify") || intendedOperation.equals("update") || 
				intendedOperation.equals("edit")) {
			object =new Modify();
		}
		else if (intendedOperation.equals("search") || intendedOperation.equals("find")){
			object = new Search();
		}
		else if (intendedOperation.equals("completed") || intendedOperation.equals("done")){
			object = new Completed();
		}
		else if (intendedOperation.equals("archive")){
			object = new Archive();
		}
		else if (intendedOperation.equals("overdue")){
			object= new Overdue();
		}
		else
		{
			object=new Default();
		}

		
		
		return object; 
	}
	
	
	protected Task[] execute(String userCommand){
		return null;
	}
	
	public abstract Task[] undo();
		
	public abstract boolean isUndoAble();
	
	public abstract boolean isInputCorrect(String command);
	public abstract String getErrorMessage();
	
	public abstract String getOperationName();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

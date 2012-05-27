package Operation;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import parser.Parser;
import storagecontroller.StorageManager;
import data.Task;

public class Add extends Operation {
	
	public Task[] execute (String userCommand)
	{
		
		String params = userCommand.toLowerCase().replace("add ","");
		Parser newParser=new Parser();
		Task newTask= newParser.parse(params);
		
		StorageManager handler=new StorageManager();
		boolean isAdded = handler.addTask(newTask);
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
		return "add";
	}
	
private static Logger logger = Logger.getLogger(Add.class);
    
    public static void main(String[] args) {
        
    	Add adder=new Add();
    	
    	Task[] abc=adder.execute("add 3/8/2012");
    	if (abc[0]!=null)
    	System.out.println(abc[0].getName());
    }      
               
    
	
	
}

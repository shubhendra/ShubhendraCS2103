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
		Task newTask= Parser.parseCommand(params);
		
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
		return null;
	}
	
private static Logger logger = Logger.getLogger(Add.class);
    
    public static void main(String[] args) {
        
        long time = System.currentTimeMillis();
        logger.info("main method called..");
        logger.info("another informative message");
        logger.warn("This one is a warning!");
        logger.log(Level.TRACE, 
                "And a trace message using log() method.");
        long logTime = System.currentTimeMillis() - time;
        
        logger.debug("Time taken to log the previous messages: " 
                + logTime + " msecs");

        // Exception logging example:
        try{
        //    String subs = "hello".substring(6);
        }catch (Exception e){
            logger.error("Error in main() method:", e);
        }      
               
    }
	
	
}

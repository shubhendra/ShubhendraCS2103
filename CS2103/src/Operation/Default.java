/**
 * extends Operation
 * this class can be used to implement any default functionality 
 * 		as wanted when the user does not enter a key word.
 * In our version we decided not to implement anything in it
 * 
 * @author Shubhendra Agrawal
 */
package operation;

import constant.OperationFeedback;
import data.Task;

public class Default extends Operation {

	
	private static String commandName;
	
	/**
	 * constructor
	 */
	public Default(){
		commandName="default";
	}
	@Override
	/**
	 * undo is irrelevant
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * this class is not undoable
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	/**
	 * @return the name of this operation 
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

	
	@Override
	/**
	 * currently this function does nothing
	 * 
	 * @param userCommand
	 * @return null
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * redo is irrelevant
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return the operation feedback which is null
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * extends Operation
 * 
 * This class is used to check whether a given time slot if free or not
 * 
 * @author Shubhendra Agrawal
 */
package operation;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import parser.Parser;
import constant.OperationFeedback;
import data.Task;


public class CheckFree extends Operation{
	
	private static Logger logger=Logger.getLogger(CheckFree.class);
	private String commandName;
	private final static long MILLISECONDS_IN_A_DAY=3600*24*1000;
	
	/**
	 * Constructor 
	 */
	public CheckFree (){
		commandName="check.free";
	}
	
	/**
	 * Constructor
	 * @param userInput
	 */
	public CheckFree(String userInput){
		commandName=userInput;
	}
	
	@Override
	/**
	 * implements whether a given slot is free or not
	 * 
	 * @param userCommand
	 * @return the tasks that class in the given schedule,
	 * 			null if no tasks clash and te given slot os free
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		userCommand.toLowerCase().trim().replaceFirst("check.free ", "");
		
		Search SearchObj=new Search();
	
		Task parsedTask=parseCommand(userCommand);
		if (parsedTask.getStart()==null || parsedTask.getEnd()==null){
			feedback=OperationFeedback.TASK_SPECIFIED_DOES_NOT_HAVE_BOTH_START_END_DATE_TIME;
			return null;
		}
		Task[] allSortedTasks=SearchObj.returnAllTasks();
		ArrayList<Task> betweenTasks= new ArrayList<Task>();
		for(int i=0;i<allSortedTasks.length;i++){
			if(SearchObj.isTaskBetween(parsedTask, allSortedTasks[i])){
				betweenTasks.add(allSortedTasks[i]);
			}
		}
		
		
		if (betweenTasks.size()==0){
			return null;
		}
		else {
			feedback = OperationFeedback.NOT_FREE;
			return (Task[]) betweenTasks.toArray(new Task[betweenTasks.size()]);
		}
	}

	/**
	 * parses the user Command into a task
	 * 
	 * @param userCommand
	 * @return Task that was parsed on the basis of userCommand
	 */
	private Task parseCommand(String userCommand) {
		// TODO Auto-generated method stub
		Parser newParser=new Parser();
		Task newTask=newParser.parseForSearch(userCommand);
		return newTask;
	}
	
	@Override
	/**
	 * undo is irrelevant here
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * redo is irrelevant here
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return whether this functionality is undo able or not
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	@Override
	/**
	 * @return the operation feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}

	@Override
	/**
	 * @return the name of this operation
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

}

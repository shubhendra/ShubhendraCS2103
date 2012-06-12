package operation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import parser.Parser;

import storagecontroller.StorageManager;
import constant.OperationFeedback;

import data.Task;
import data.TaskDateTime;
public class Schedule extends Operation{
	private static Logger logger=Logger.getLogger(Schedule.class);
	private String commandName;
	private final static long MILLISECONDS_IN_A_DAY=3600*24*1000;
	public Schedule (){
		commandName="checkfree";
	}
	public Schedule(String userInput){
		commandName=userInput;
	}
	
	@Override
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		userCommand.toLowerCase().trim().replaceFirst("checkfree ", "");
		
		Search SearchObj=new Search();
	
		Task parsedTask=parseCommand(userCommand);
		if (parsedTask.getStart()==null && parsedTask.getEnd()==null){
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
		/*	long timeDuration= getTimeDuration(parsedTask);
			if (timeDuration>MILLISECONDS_IN_A_DAY)
			{
				feedback=OperationFeedback.DURATION_LONGER_THAN_A_DAY;
				return null;
			}
			
			for (long i= )*/
			return null;
		}
		else {
			feedback = OperationFeedback.NOT_FREE;
			return (Task[]) betweenTasks.toArray(new Task[betweenTasks.size()]);
		}
	}

	private long getTimeDuration(Task parsedTask) {
		// TODO Auto-generated method stub
		TaskDateTime defaultTime= new TaskDateTime();
		long timeDuration=0;
		if (parsedTask.getStart()!=null && parsedTask.getEnd()!=null){
			if (parsedTask.getStart().getDate().getTimeMilli()!=defaultTime.getDate().getTimeMilli() &&
					parsedTask.getEnd().getDate().getTimeMilli()!=defaultTime.getDate().getTimeMilli() &&
					parsedTask.getStart().getTime().getTimeMilli()!=defaultTime.getTime().getTimeMilli() &&
					parsedTask.getEnd().getTime().getTimeMilli()!=defaultTime.getTime().getTimeMilli()){
				timeDuration = parsedTask.getStart().getTimeMilli()-parsedTask.getEnd().getTimeMilli();
			}				
				
			else if (parsedTask.getStart().getDate().getTimeMilli()==defaultTime.getDate().getTimeMilli() &&
					parsedTask.getEnd().getDate().getTimeMilli()==defaultTime.getDate().getTimeMilli() &&
					parsedTask.getStart().getTime().getTimeMilli()!=defaultTime.getTime().getTimeMilli() &&
					parsedTask.getEnd().getTime().getTimeMilli()!=defaultTime.getTime().getTimeMilli()){
				timeDuration = parsedTask.getStart().getTime().getTimeMilli()-parsedTask.getEnd().getTime().getTimeMilli();
			}
			else if (parsedTask.getStart().getDate().getTimeMilli()!=defaultTime.getDate().getTimeMilli() &&
					parsedTask.getEnd().getDate().getTimeMilli()!=defaultTime.getDate().getTimeMilli() &&
					parsedTask.getStart().getTime().getTimeMilli()==defaultTime.getTime().getTimeMilli() &&
					parsedTask.getEnd().getTime().getTimeMilli()==defaultTime.getTime().getTimeMilli()){
				
				timeDuration = parsedTask.getStart().getDate().getTimeMilli()-parsedTask.getEnd().getDate().getTimeMilli();
			}
		
		
		}
		
		
		return timeDuration;
	}
	private Task parseCommand(String userCommand) {
		// TODO Auto-generated method stub
		Parser newParser=new Parser();
		Task newTask=newParser.parseForSearch(userCommand);
		return newTask;
	}
	@Override
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	@Override
	public boolean isInputCorrect(String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

}

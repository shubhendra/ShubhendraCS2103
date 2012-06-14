/**
 * extends Operation
 * Implements the search functionality by taking in the user input parsing it into a task 
 * 		and then comparing the same attributes.
 * 
 * @author Shubhendra Agrawal
 */
package operation;

import constant.OperationFeedback;
import data.TaskDateTime;
import data.CompareByDate;
import java.util.ArrayList;
import java.util.Collections;
import parser.Parser;
import java.util.Comparator;
import storagecontroller.StorageManager;
import java.util.Arrays;
import data.Task;

public class Search extends Operation {
	

	private TaskDateTime defaultTime = new TaskDateTime();
	
	private String commandName;
	
	/**
	 * constructor
	 * @param intendedOperation
	 */
	public Search(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
	}
	
	/**
	 * constructor
	 */
	public Search(){
		commandName="search";
	}


	@Override
	/**
	 * undo is irrelevant for search
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * @return whether undoable or not
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}


	/**
	 * @return operation feedback
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}      
               
	@Override
	/**
	 * @return Operation name
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}
	
	@Override
	
	/**
	 * calls the required find function depending on user input
	 * 
	 * @param userCommand
	 * @return Task array of matching tasks
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub

		String params = "";
		
		if (userCommand.startsWith("search ")) {
			params = userCommand.replace("search ", "");
		} else if (userCommand.startsWith("find ")) {
			params = userCommand.replace("find ", "").trim();
		}
		
		if (params.toLowerCase().contains("*.*")) {
		
			return returnAllTasks();
		}
		
		Task parsedTask = parseCommand(params.toLowerCase());
		if (parsedTask == null) {
			feedback = OperationFeedback.NOT_FOUND;
			return null;
		}
		
		return search(parsedTask);
		
		
	}

	/**
	 * parses the given input into a task
	 * @param params
	 * @return task
	 */
	private Task parseCommand(String params) {
		// TODO Auto-generated method stub
		Parser newParser = new Parser();
		return newParser.parseForSearch(params);
	}

	/**
	 * 
	 * @return all the tasks in the storage manager sorted according to date/time
	 */
	public Task[] returnAllTasks() {
		// TODO Auto-generated method stub
		Task[] unsorted = StorageManager.getAllTasks();
		Comparator<Task> compareByDate = new CompareByDate();
		
		
		Arrays.sort(unsorted, compareByDate);
		
		
		return unsorted;
		//return null;
	}
	
	/**
	 * 
	 * @return tasks required to be sent in the email reminder
	 */
	public Task[] searchTodaysTasks(){
		Task [] allTasks=returnAllTasks();
		ArrayList<Task> todaysTasks=new ArrayList<Task>();;
		for(Task param:allTasks)
			if ((param.getStart() != null) && 
					(param.getStart().getDate().getTimeMilli()  <=  TaskDateTime.getCurrentDate().getTimeMilli()))
			{
				if (!param.getCompleted())
					todaysTasks.add(param);
			} else if ((param.getEnd() != null) && 
					(param.getEnd().getTimeMilli() <= TaskDateTime.getCurrentDate().getTimeMilli())){
				if (!param.getCompleted())
					todaysTasks.add(param);
			} else if ((param.getImportant()) && (!param.getCompleted())){
				todaysTasks.add(param);
			}
		
				
			
		
		if (todaysTasks.size() != 0){
			return (Task[]) todaysTasks.toArray(new Task[todaysTasks.size()]);
		}
		feedback = OperationFeedback.NOT_FOUND;
		return null;
	}
	
	/**
	 * 
	 * @param taskToSearch
	 * @return tasks that matched with the task To Search
	 */
	public Task[] search(Task taskToSearch) {
		// TODO Auto-generated method stub
		if (taskToSearch.getTaskId() != null) {
			return new Task[]{StorageManager.getTaskById(taskToSearch.getTaskId())};
		} else {
			return search(taskToSearch, StorageManager.getAllTasks());
		}
		
		
	}

	/**
	 * 
	 * @param findTask
	 * @param allTasks
	 * @return returns the matched tasks from a given array of tasks
	 */
	private Task[] search(Task findTask, Task[] allTasks) {
		// TODO Auto-generated method stub
		ArrayList<Task> foundTasks = new ArrayList<Task>();
		
		for(int i=0;i<allTasks.length;i++)
		{
			if (matches(findTask,allTasks[i])){
				Collections.addAll(foundTasks, allTasks[i]);
			}
			
		}
		if (foundTasks.size() > 0){
			return foundTasks.toArray(new Task[foundTasks.size()]);
		}
		feedback = OperationFeedback.NOT_FOUND;
		return null;
	}

	/**
	 * matches name
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesNameMatch(Task taskToSearch, Task existingTask){
		if (("".equals(taskToSearch.getName()) || 
				existingTask.getName().toLowerCase().contains((taskToSearch.getName().trim())))){
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * matches Start date
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesStartDateMatch(Task taskToSearch, Task existingTask){
		
		if (taskToSearch.getStart() == null
				|| taskToSearch.getStart().getDate().getTimeMilli()
				== defaultTime.getDate().getTimeMilli() || (existingTask.getStart() !=null 
				&& (existingTask.getStart().getDate().getTimeMilli()
				== taskToSearch.getStart().getDate().getTimeMilli()))
				|| (existingTask.getEnd() != null 
				&& (existingTask.getEnd().getDate().getTimeMilli()
				== taskToSearch.getStart().getDate().getTimeMilli()))){
		
			return true;
		
		}
		return false;
		
	}
	

	/**
	 * matches Start time
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesStartTimeMatch(Task taskToSearch, Task existingTask){
	//	logger.debug("Default date:"+defaultTime.getDate().getTimeMilli());
	//	logger.debug("Default Time:"+defaultTime.getTime().getTimeMilli());
		if (taskToSearch.getStart() == null
				|| taskToSearch.getStart().getTime().getTimeMilli()
				== defaultTime.getTime().getTimeMilli() || (existingTask.getStart() != null 
				&&  (existingTask.getStart().getTime().getTimeMilli()
				== taskToSearch.getStart().getTime().getTimeMilli()))
				|| (existingTask.getEnd() != null 
				&& (existingTask.getEnd().getTime().getTimeMilli()
				== taskToSearch.getStart().getTime().getTimeMilli()))){
		
			return true;
		}
		return false;
	}
	

	/**
	 * matches End date
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesEndDateMatch(Task taskToSearch, Task existingTask){
		if (taskToSearch.getEnd() == null 
				|| taskToSearch.getEnd().getDate().getTimeMilli()
				== defaultTime.getDate().getTimeMilli() || (existingTask.getEnd() != null 
				&& (existingTask.getEnd().getDate().getTimeMilli()
				== taskToSearch.getEnd().getDate().getTimeMilli())) 
				|| (existingTask.getStart() != null 
				&& (existingTask.getStart().getDate().getTimeMilli()
				== taskToSearch.getEnd().getDate().getTimeMilli()))){
		
			return true;
		}
		return false;
	}
	

	/**
	 * matches End Time
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesEndTimeMatch(Task taskToSearch, Task existingTask){
		if(taskToSearch.getEnd() == null 
				|| taskToSearch.getEnd().getTime().getTimeMilli()
				== defaultTime.getDate().getTimeMilli() || (existingTask.getEnd() != null 
				&& (existingTask.getEnd().getTime().getTimeMilli()
				== taskToSearch.getEnd().getTime().getTimeMilli()))
				|| (existingTask.getStart() != null 
				&& (existingTask.getStart().getTime().getTimeMilli()
				== taskToSearch.getEnd().getTime().getTimeMilli()))){
		
			return true;
		}
		return false;
	}
	

	/**
	 * matches if important status matches
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesImportantMatch(Task taskToSearch, Task existingTask){
		if  (taskToSearch.getImportant() == false || taskToSearch.getImportant() == 
				existingTask.getImportant())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * matches if any labels are same 
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean doesLabelMatch(Task taskToSearch, Task existingTask){
		
		if (taskToSearch.getLabels() == null || taskToSearch.getLabels().size() == 0) {
			return true;
		}
		else if (existingTask.getLabels() != null) {
						
			boolean flag = false;
			for (String searchlabel : taskToSearch.getLabels()) {
			
				searchlabel = searchlabel.toLowerCase();
				flag = false;
				for (String existingLabel : existingTask.getLabels()) {
							
						if (existingLabel.contains(searchlabel)) {
							flag = true;
							break;
						}
					
				}
				if(!flag)
					break;
			
				}
			if (flag) {
			
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns true if any of the attributes of taskToSearch match the given exisTask 
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if matches
	 */
	private boolean matches(Task taskToSearch, Task existingTask) {
		// TODO Auto-generated method stub
	
		if (doesNameMatch(taskToSearch, existingTask) 
				&& doesStartDateMatch(taskToSearch,existingTask)
				&& doesStartTimeMatch(taskToSearch,existingTask) 
				&& doesEndDateMatch(taskToSearch,existingTask) 
				&& doesEndTimeMatch(taskToSearch, existingTask) 
				&& doesImportantMatch(taskToSearch,existingTask)
				&& doesLabelMatch(taskToSearch,existingTask)){
		
			return true;
		} else if(isTaskBetween(taskToSearch,existingTask)){
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the task falls in between a given date time range,
	 *  the end points are inclusive
	 * @param taskToSearch
	 * @param existingTask
	 * @return true if it is between
	 */
	public boolean isTaskBetween(Task taskToSearch,Task existingTask){
		
		if (taskToSearch.getStart() != null && taskToSearch.getEnd() != null){
			if (taskToSearch.getStart().getDate().getTimeMilli() != defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getEnd().getDate().getTimeMilli() != defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getStart().getTime().getTimeMilli() != defaultTime.getTime().getTimeMilli() &&
					taskToSearch.getEnd().getTime().getTimeMilli() != defaultTime.getTime().getTimeMilli()){
				
				if (existingTask.getStart() != null && existingTask.getEnd() != null){
					if(taskToSearch.getStart().getTimeMilli() <= existingTask.getStart().getTimeMilli() && 
							taskToSearch.getEnd().getTimeMilli() >= existingTask.getEnd().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else if (existingTask.getStart() == null && existingTask.getEnd()!=null){
					if(taskToSearch.getEnd().getTimeMilli() >= existingTask.getEnd().getTimeMilli() &&
							taskToSearch.getStart().getTimeMilli() <= existingTask.getEnd().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else if (existingTask.getStart() != null && existingTask.getEnd() == null){
					if(taskToSearch.getStart().getTimeMilli() <= existingTask.getStart().getTimeMilli() &&
							taskToSearch.getEnd().getTimeMilli() >= existingTask.getStart().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else { 
					return false;
				}
			} else if (taskToSearch.getStart().getDate().getTimeMilli() == defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getEnd().getDate().getTimeMilli() == defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getStart().getTime().getTimeMilli() != defaultTime.getTime().getTimeMilli() &&
					taskToSearch.getEnd().getTime().getTimeMilli() != defaultTime.getTime().getTimeMilli()){
				
				if (existingTask.getStart() != null && existingTask.getEnd() != null){
					if(taskToSearch.getStart().getTime().getTimeMilli() <= existingTask.getStart().getTime().getTimeMilli() && 
							taskToSearch.getEnd().getTime().getTimeMilli() >= existingTask.getEnd().getTime().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else if (existingTask.getStart() == null && existingTask.getEnd() != null){
					if(taskToSearch.getEnd().getTime().getTimeMilli() >= existingTask.getEnd().getTime().getTimeMilli() &&
							taskToSearch.getStart().getTime().getTimeMilli() <= existingTask.getEnd().getTime().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else if (existingTask.getStart() != null && existingTask.getEnd() == null){
					if(taskToSearch.getStart().getTime().getTimeMilli() <= existingTask.getStart().getTime().getTimeMilli() &&
							taskToSearch.getEnd().getTime().getTimeMilli() >= existingTask.getStart().getTime().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if (taskToSearch.getStart().getDate().getTimeMilli() != defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getEnd().getDate().getTimeMilli() != defaultTime.getDate().getTimeMilli() &&
					taskToSearch.getStart().getTime().getTimeMilli() == defaultTime.getTime().getTimeMilli() &&
					taskToSearch.getEnd().getTime().getTimeMilli() == defaultTime.getTime().getTimeMilli()){
				
				if (existingTask.getStart() != null && existingTask.getEnd() != null){
					if(taskToSearch.getStart().getDate().getTimeMilli() <= existingTask.getStart().getDate().getTimeMilli() && 
							taskToSearch.getEnd().getDate().getTimeMilli() >= existingTask.getEnd().getDate().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
				} else if (existingTask.getStart() == null && existingTask.getEnd() != null){
					if(taskToSearch.getEnd().getDate().getTimeMilli() >= existingTask.getEnd().getDate().getTimeMilli() &&
							taskToSearch.getStart().getDate().getTimeMilli() <= existingTask.getEnd().getDate().getTimeMilli()){
		
						return true;
					} else {
						return false;
					}
					
				} else if (existingTask.getStart() != null && existingTask.getEnd() == null){
					if(taskToSearch.getStart().getDate().getTimeMilli() <= existingTask.getStart().getDate().getTimeMilli() &&
							taskToSearch.getEnd().getDate().getTimeMilli()  >=  existingTask.getStart().getDate().getTimeMilli()){
		
						return true;
					}
					else {
						return false;
					}
				} else 
					return false;
			}
			
			
		}
		return false;
	
	}
	@Override
	/**
	 * redo is irrelevant
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

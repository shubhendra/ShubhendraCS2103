/**
 * extends Operation
 * This class is used to manage the completed tasks in 
 * the list by storing them in an alternate location and 
 * allowing the user to export them back if needed  
 * 
 * @author Shubhendra Agrawal
 */

package operation;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import storagecontroller.StorageManager;
import constant.OperationFeedback;
import data.Task;

public class Archive extends Operation{
	
	private String commandName;
	private static Logger logger = Logger.getLogger(Archive.class);
	
	private enum archiveStatus{
		CLEAR_ARCHIVE, IMPORT_ARCHIVE, ARCHIVE, NONE;
	}
	
	private archiveStatus archiveCommand = archiveStatus.NONE;
	private ArrayList<Task> archiveTasks = new ArrayList<Task>();
	/**
	 * Constructor
	 */
	public Archive(){
		commandName = "archive";
	}
	
	/**
	 * Constructor with the given input initialized as the
	 * 			commandName
	 * @param intendedOperation
	 */
	public Archive(String intendedOperation) {
		// TODO Auto-generated constructor stub
		commandName = intendedOperation;
	}
	
	
	@Override
	/**
	 * Implements the undo functionality if needed
	 * @return Task[] of tasks that were undone
	 */
	public Task[] undo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks = new ArrayList<Task>();
		if (archiveCommand == archiveStatus.CLEAR_ARCHIVE){
			return null;
		} else if (archiveCommand == archiveStatus.IMPORT_ARCHIVE){
			for (int i = 0 ; i < archiveTasks.size() ; i++){
				if (archiveTasks.get(i).getCompleted()){
					
					if( StorageManager.deleteTask(archiveTasks.get(i)) 
							&& StorageManager.addArchivedTask(archiveTasks.get(i))) {
						undoneTasks.add(archiveTasks.get(i));
					}
				}
			}
			if (archiveTasks.size() == 0) {
				return null;
			} else {
				return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
			}
		}
		else if (archiveCommand == archiveStatus.ARCHIVE) {
			for (int i = 0 ; i < archiveTasks.size() ; i++){
				if (archiveTasks.get(i).getCompleted()){
					
					if( StorageManager.deleteArchivedTask(archiveTasks.get(i)) 
							&& StorageManager.addTask(archiveTasks.get(i))) {
						undoneTasks.add(archiveTasks.get(i));
					}
					
				}
			}
			if (archiveTasks.size() == 0) {
				return null;
			} else {
				return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
			}
		} else {
			return null;
		}
	}

	@Override
	/**
	 * 
	 * @returns isUndoAble
	 */
	public boolean isUndoAble() {
		// TODO Auto-generated method stub
		return isUndoAble;
	}

	

	@Override
	/**
	 * @return commandName
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return commandName;
	}

	@Override
	/**
	 * this calls the various different archive functions which need to be
	 * 			executed based on the user input
	 * @param userCommand
	 * @return returns an array of TAsk[] depending upon what tasks were affected
	 * 			if no matching functions are found returns null;
	 */
	public Task[] execute(String userCommand) {
		// TODO Auto-generated method stub
		logger.debug(userCommand.trim());
		if (userCommand.toLowerCase().startsWith("archive")){
			archiveCommand = archiveStatus.ARCHIVE;
			logger.debug("running archvie");
			return archiveTasks();
		}
		else if(userCommand.toLowerCase().trim().contains("clear.archive")){
			logger.debug("running clear archvie");
			archiveCommand = archiveStatus.CLEAR_ARCHIVE;
			return clearArchive();
		}
		else if(userCommand.toLowerCase().trim().contains("import.archive")){
			archiveCommand = archiveStatus.IMPORT_ARCHIVE;
			logger.debug("importing archive");
			return importArchive();
		}
		else {
			feedback = OperationFeedback.NO_MATCHING_ARCHIVE_FUNCTION;
			return null;
		}
	}

	/**
	 * imports the tasks if any from the archives to the
	 * 			live storage
	 * @return Task array of tasks imported
	 */
	private Task[] importArchive() {
		// TODO Auto-generated method stub
		Task[] allArchivedTasks = StorageManager.getAllArchivedTasks();
		logger.debug(archiveTasks.size());
		for (int i = 0 ; i < allArchivedTasks.length ; i++){
			if( StorageManager.deleteArchivedTask(allArchivedTasks[i]) 
					&& StorageManager.addTask(allArchivedTasks[i])) {
				isUndoAble = true;
				archiveTasks.add(allArchivedTasks[i]);
			} else {
				feedback = OperationFeedback.TASK_COULD_NOT_BE_EXPORTED_FROM_ARCHIVES;
				isUndoAble = false;
				return null;
			}
		}
		if (archiveTasks.size() == 0){
			feedback = OperationFeedback.NO_TASK_IN_ARCHIVE;
			return null;
		} else{
			logger.debug(archiveTasks.size());
			return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
		}
	}

	/**
	 * clears all the archives
	 * 
	 * @return null 
	 */
	private Task[] clearArchive() {
		// TODO Auto-generated method stub
		isUndoAble = false;
		StorageManager.clearArchive();
		feedback = OperationFeedback.VALID;
		return null;
		
	}
	
	/**
	 * archives the completed task and removes them from liveStorage
	 * 
	 * @return array of tasks archived
	 */
	private Task[] archiveTasks() {
		// TODO Auto-generated method stub
		
		Task[] allTasks = StorageManager.getAllTasks();
		logger.debug("inside archvietasks()");
		for (int i = 0 ; i < allTasks.length ; i++){
			if (allTasks[i].getCompleted()){
								
				if(StorageManager.deleteTask(allTasks[i]) 
						&& StorageManager.addArchivedTask(allTasks[i])) {
					isUndoAble = true;
					archiveTasks.add(allTasks[i]);
				}
				else{
					feedback = OperationFeedback.TASK_COULD_NOT_BE_EXPORTED_TO_ARCHIVES;
					isUndoAble = false;
					return null;
				}
				
			}
		}
		if (archiveTasks.size() == 0){
			feedback = OperationFeedback.NO_TASK_TO_ARCHIVE;
			return null;
			
		} else {
			return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
		}
	}

	@Override
	/**
	 * implements the redo functionality
	 * @return returns the array of tasks redone
	 */
	public Task[] redo() {
		// TODO Auto-generated method stub
		ArrayList<Task> undoneTasks = new ArrayList<Task>();
		if (archiveCommand == archiveStatus.CLEAR_ARCHIVE){
			return null;
		} else if (archiveCommand == archiveStatus.IMPORT_ARCHIVE){
			for (int i = 0 ; i < archiveTasks.size() ; i++){
				if (archiveTasks.get(i).getCompleted()){
					
					if( StorageManager.deleteArchivedTask(archiveTasks.get(i)) 
							&& StorageManager.addTask(archiveTasks.get(i)))
					{
						undoneTasks.add(archiveTasks.get(i));
					}
					
				}
			}
			if (archiveTasks.size() == 0){
				return null;
			} else {
				return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
			}
		} else if (archiveCommand == archiveStatus.ARCHIVE){
			for (int i = 0 ; i < archiveTasks.size() ; i++){
				if (archiveTasks.get(i).getCompleted()){
					
					if( StorageManager.deleteTask(archiveTasks.get(i)) 
							&& StorageManager.addArchivedTask(archiveTasks.get(i)))
					{
						undoneTasks.add(archiveTasks.get(i));
					}
					
				}
			}
			if (archiveTasks.size() == 0) {
				return null;
			} else {
				return (Task[]) archiveTasks.toArray(new Task[archiveTasks.size()]);
			}
		} else {
			return null;
		}

	}

	@Override
	/**
	 * @return returns the Operation feedback;
	 */
	public OperationFeedback getOpFeedback() {
		// TODO Auto-generated method stub
		return feedback;
	}
	

}

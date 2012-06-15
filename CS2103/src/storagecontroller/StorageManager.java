/** The Manager of the storage classes. Maintains a static instance of the TaskHashMap and of the GoogleCalendar class.
 * It is the class to which the Logic interacts with. 
 * 
 * @author Nirav Gandhi
 */
package storagecontroller;
import data.TaskHashMap;
import data.Task;
import gcal.GoogleCalendar;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class StorageManager {
	private static TaskHashMap liveStorage=new TaskHashMap();
	private static TaskHashMap liveArchives=new TaskHashMap();
	private static Logger logger = Logger.getLogger(StorageManager.class);
	private static GoogleCalendar gCal;
/** default constructor
 * 
 */
	
public StorageManager() {}
/** adds a task to the archived TaskHashMap
 * 
 * @param taskToBeAdded 
 * @return true if the task is successfully added, otherwise false.
 */

public static boolean addArchivedTask(Task taskToBeAdded) {
	return liveArchives.addTask(taskToBeAdded);
}
/** deletes a task from the archived TaskHashMap
 * 
 * @param taskToBeRemoved
 * @return true if the task has been successfully deleted, otherwise false;
 */

public static boolean deleteArchivedTask(Task taskToBeRemoved) {
	return liveArchives.deleteTask(taskToBeRemoved);
}
/** adds the task to liveStorage
 * 
 * @param taskToBeAdded the task to be added
 * @return true if the task is added without any errors, otherwise false.
 */

public static boolean addTask(Task taskToBeAdded) {
	return liveStorage.addTask(taskToBeAdded);
}
/** deletes the task from liveStorage
 * 
 * @param taskToBeRemoved the task to be removed
 * @return true if the task is deleted without any errors.
 */

public static boolean deleteTask(Task taskToBeRemoved) {
	return liveStorage.deleteTask(taskToBeRemoved);
}
/** gets all the tasks from liveStorage  
 * 
 * @return an array of all tasks in liveStorage
 */

public static Task[] getAllTasks() {
	ArrayList<Task> tasks=new ArrayList<Task>();
	for(String key: liveStorage.getKeySet())
		tasks.add(liveStorage.getTaskById(key));
	Task[] taskArray=new Task[tasks.size()];
	tasks.toArray(taskArray);
	return taskArray;
}	
/** gets all the tasks from archiveTasks
 * 
 * @return a task array of archived task
 */

public static Task[] getAllArchivedTasks() {
	ArrayList<Task> tasks=new ArrayList<Task>();
	for(String key: liveArchives.getKeySet()){
		tasks.add(liveArchives.getTaskById(key));
	}
	Task[] taskArray=new Task[tasks.size()];
	tasks.toArray(taskArray);
	return taskArray;
}
/** 
 * 
 * @param id id of the task to be returned
 * @return the task of the taskId 'id'
 */

public static Task getTaskById(String id) {
	return liveStorage.getTaskById(id.trim());
}
/** loads to the liveStorage from the file 
 * 
 * @return true if the file has been loaded without any errors, otherwise false
 */

public static boolean loadFile() {
	FileHandler handler = new FileHandler("JotItDownDatabase.xml");
	if(liveStorage.getKeySet().size()!=0) {
		logger.debug("Clearing HashMap");
		liveStorage.clearHashMap();
	}
	return handler.readFromFile(liveStorage);
}
/** loads the date from the file JotItDownAgenda.txt
 * 
 * @return the date loaded from the file.
 */

public static String loadDate() {
	FileHandler handler = new FileHandler("JotItDownAgenda.txt");
	return handler.readDate();
}
/** saves to the file from the liveStorage
 * 
 * @return true if all the tasks have been loaded without errors, otherwise false.
 */
public static String loadEmailId() {
	FileHandler handler = new FileHandler("JotItDownEMail.txt");
	return handler.readEmailId();
}
/** saves the liveStorage
 * 
 * @return true if the contents of liveStorage are written into the file successfully, otherwise false
 */

public static boolean saveFile() {
	FileHandler handler = new FileHandler("JotItDownDatabase.xml");
	if(handler.writeToFile(liveStorage)){
		return true;
	}
	else{ 
		return false;
	}
}
/** saves the email Id for the class SendMail into 'JotItDownEmail.txt'
 * 
 * @param emailId emailId to be stored
 * @return true if written without any errors, otherwise false
 */

public static boolean saveEmailId(String emailId) {
	FileHandler handler=new FileHandler("JotItDownEmail.txt");
	if(handler.writeEmailId(emailId)){
		return true;
	}
	else{
		return false;
	}
}
/** saves the date the mail was last sent on into the file
 * 
 * @param toWrite the date to be written
 * @return true if written to file successfully
 */

public static boolean saveDate(String toWrite) {
	FileHandler handler=new FileHandler("JotItDownAgenda.txt");
	if(handler.writeDate(toWrite)){
		return true;
	}
	else{
		return false;
	}
}
/** replaces tasks
 * 
 * @param taskToBeReplaced
 * @param taskToReplaceBy
 * @return true if the task has been replaced without errors, otherwise false
 */

public static boolean replaceTask(Task taskToBeReplaced,Task taskToReplaceBy) {
	if(taskToBeReplaced==null || taskToReplaceBy==null){
		return false;
	}
	System.out.println(getAllTasks().length);
	liveStorage.deleteTask(taskToBeReplaced);
	taskToReplaceBy.setTaskId(taskToBeReplaced.getTaskId());
	liveStorage.addTask(taskToReplaceBy);
	taskToReplaceBy.setGCalId(taskToBeReplaced.getGCalId());
	return true;
}
/** exports from the xml file to a text file with name as fileName
 * 
 * @param fileName name of the txt file
 */

public static void exportToTxt(String fileName) {
	FileHandler handler = new FileHandler(fileName);
	handler.writeToFile(liveStorage);
}
/** deletes task from liveStorage
 * 
 * @param id taskId of the task to be deleted
 * @return true if the task has been successfully deleted, otherwise false
 */

public static boolean deleteTask(String id) {
	return liveStorage.deleteTaskById(id);
}
/** Saves the contents of the liveArchives to an xml file
 * 
 * @return true if the file is written successfully, otherwise false
 */

public static boolean saveArchive() {
	FileHandler handler=new FileHandler("JotItDownArchives.xml");
	if(handler.writeToFile(liveArchives)){
		return true;
	}
	else{ 
		return false;
	}
}
/** Clears the liveArchives TaskHashMap
 * 
 */

public static void clearArchive() {
	liveArchives.clearHashMap();
}
/** loads archive from xml file to liveArchives.
 * 
 * @return boolean if loaded succesfully, otherwise false
 */

public static boolean loadArchive() {
	FileHandler handler = new FileHandler("JotItDownArchives.xml");
	if(liveArchives.getKeySet().size()!=0) {
		logger.debug("Clearing Archived HashMap");
		liveArchives.clearHashMap();
	}
	return handler.readFromFile(liveArchives);
}
/** 
 * 
 * @return the google calendar object
 */

public static GoogleCalendar getGCalObject() {
	return gCal;
}
/**
 * sets the google calendar object
 * @param obj the value to which gCal should be set to.
 */

public static void setGCalObject(GoogleCalendar obj) {
	gCal = obj;
}
/** 
 * 
 * @param Id recurring Id
 * @return a task array of all the tasks with the recurring id as 'Id'
 */

public static Task[] getTaskByRecurrenceID(String Id) {
	ArrayList<Task> tasks=new ArrayList<Task>();
	for(String key: liveStorage.getKeySet()) {
		if (liveStorage.getTaskById(key).getRecurringId().contains(Id)) {
			tasks.add(liveStorage.getTaskById(key));
		}
	}
	Task[] taskArray = new Task[tasks.size()];
	tasks.toArray(taskArray);
	return taskArray;
	}
}
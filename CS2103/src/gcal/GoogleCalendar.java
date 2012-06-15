/**
 * This class synchronizes JotItDown with Google Calendar. It keeps the google Calendar up-to-date with the changes made in the software.
 * 
 * @author Nirav Gandhi
 */
package gcal;
import storagecontroller.StorageManager;
import org.apache.log4j.Logger;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
//import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Reminder.Method;
import com.google.gdata.data.extensions.Reminder;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import data.Task;
import data.TaskDateTime;
import parser.Parser;

public class GoogleCalendar implements Runnable
{
private Logger logger = Logger.getLogger(GoogleCalendar.class.getName());
private static final String USER_CALENDAR_URL = "https://www.google.com/calendar/feeds/default/private/full";
private static final String APPLICATION_NAME = "JotItDown";
private CalendarService calenService=new CalendarService(APPLICATION_NAME);
public static final String APPLICATION_NAME_ID_PROPERTY = String.format(":%s:%s:", APPLICATION_NAME, "Id");
private String username;
private String password;
private static boolean loggedIn;
private static URL userCalendarUrl;
private Parser parser=new Parser();

/** logs into Google calendar
 * 
 * @param userName userName entered by the user	
 * @param passWord password entered by the user
 * @return true if the username and password are authenticated by google calendar
 */

public boolean login(String userName, String passWord)
{
	this.username = userName;
	this.password = passWord;
	try{
		calenService.setUserCredentials(username, password);
		userCalendarUrl = new URL(USER_CALENDAR_URL);
		loggedIn = true;
	}
	catch(AuthenticationException e){
		logger.warn("Authentication Exception");
		loggedIn = false;
	}
	catch(MalformedURLException e){
		logger.warn("MalformedURLException");
		loggedIn = false;
	}
	return loggedIn;
	
}
/**
 * 
 * @return true when logged out.
 */

public boolean logout(){
	calenService = new CalendarService(APPLICATION_NAME);
	this.username = null;
	this.password = null;
	loggedIn = false;
	return true;
}
/**Gets all the events from the Google Calendar in a list
 * 
 * @return a list of CalendarEventEntry type
 */

public List<CalendarEventEntry> getAllEntries(){
	CalendarEventFeed resultFeed=null;
	if(!loggedIn){
		return null;
	}
	try{
		resultFeed = calenService.getFeed(userCalendarUrl, CalendarEventFeed.class);
	}
	catch(IOException e){
		logger.warn("IO Exception");
	}
	catch(ServiceException e){
		logger.warn("Service Exception");
	}
	List<CalendarEventEntry> entries=resultFeed.getEntries();
	return entries;
}
/**Converts a list of entries of CalendarEventEntry type to a Task array.
 * 
 * @param userEntries entries from google Calendar
 * @return an array of Tasks
 */

public Task[] calendarEventListToTaskArray(List<CalendarEventEntry> userEntries){
	ArrayList<Task> list= new ArrayList<Task>();
	for(CalendarEventEntry entry : userEntries ){
		list.add(calendarEntryToTask(entry));
	}
	return list.toArray(new Task[list.size()]);
}
/** Converts a single entry from GCal into a Task object 
 * 
 * @param entry entry from GCal
 * @return a task object of the entry from GCal
 */

public Task calendarEntryToTask(CalendarEventEntry entry){
	Task googleCalTask = new Task();
	ArrayList<String> labelList=new ArrayList<String>();
	googleCalTask.setName(entry.getTitle().getPlainText());
	String entryDesc=entry.getPlainTextContent();
	logger.debug(entryDesc);
	String id=entry.getId().replaceAll("http://www.google.com/calendar/feeds/default/events/", "");
	googleCalTask.setGCalId(id);
	List<When> eventTime=entry.getTimes();
	googleCalTask.setStart(getTaskDateTime(eventTime.get(0).getStartTime()));
	googleCalTask.getStart().setHasDate(true);
	googleCalTask.getStart().setHasTime(!eventTime.get(0).getStartTime().isDateOnly());
	String[] descr = new String[6];
	
	descr=parser.fetchGCalDes(entryDesc);
	if (descr!=null){
	for(int i=0; i<5; i++){
		logger.debug(descr[i]);
	}
	if(descr[0] == "true"){
		googleCalTask.setCompleted(true);
	}
	else{
		googleCalTask.setCompleted(false);
	}
	if(descr[1] == "true"){
		googleCalTask.setImportant(true);
	}
	else{
		googleCalTask.setImportant(false);
	}
	if(descr[2] == "true"){
		googleCalTask.setDeadline(true);
	}
	else{
		googleCalTask.setDeadline(false);
	}
	googleCalTask.setRecurring(descr[3]);
	googleCalTask.setRecurringId(descr[4]);
	labelList=stringToArrayList(descr[5]);
	logger.debug(labelList.toString());
	}
	logger.debug(getTaskDateTime(eventTime.get(0).getStartTime()).toString());
	logger.debug(getTaskDateTime(eventTime.get(0).getEndTime()).toString());
	logger.debug(googleCalTask.getName());
	if(getTaskDateTime(eventTime.get(0).getStartTime()).toString().equals(getTaskDateTime(eventTime.get(0).getEndTime()).toString())){
		logger.debug("Start end date time is same");
		googleCalTask.setEnd(null);
	}
	else{
	googleCalTask.setEnd(getTaskDateTime(eventTime.get(0).getEndTime()));
	googleCalTask.getEnd().setHasDate(true);
	googleCalTask.getEnd().setHasTime(!eventTime.get(0).getEndTime().isDateOnly());
	}
	return googleCalTask;
}
/** converts the date of XML type into the form of TaskDateTime
 * 
 * @param dateTime the xml form of Date
 * @return TaskDateTime object converted from the xml form
 */

private TaskDateTime getTaskDateTime(DateTime dateTime) {
	return TaskDateTime.xmlToEventTime(dateTime.toString());
}
/** prevents loss of information. Attributes like 'important','deadline','recurring','labels' of the Task class do not exist in the class CalendarEventEntry of Google 
 * Calendar. Therefore the information is stored in the description of the CalendarEventEntry object. 
 *  
 * @param completed 
 * @param deadline
 * @param important
 * @param recurring
 * @param recurringId
 * @param labels
 * @return a string which is set to the description of CalendarEventEntry object.
 */

public String setGcalDescription(boolean completed,boolean deadline,boolean important,String recurring,String recurringId,String labels){
	String entryCompleted="<"+"CMPT:"+completed+">";
	String entryDeadline="<"+"IMPT:"+important+">";
	String entryImportant="<"+"DEAD:"+deadline+">";
	String entryRecurring="<"+"RECUR:"+recurring+">";
	String entryRecurringId;
	if(recurringId==null || recurringId==""){
		entryRecurringId="<"+"RECURID:"+">";
	}
	else{
		entryRecurringId="<"+"RECURID:"+recurringId+">";
	}
	String entryLabels;
	if(labels.equals(new ArrayList<String>())){
		entryLabels="<"+"LABEL:"+">";
	}
	else{
		entryLabels="<"+"LABEL:"+labels+">";
	}
	return entryCompleted+entryDeadline+entryImportant+entryRecurring+entryRecurringId+entryLabels;
}
/**Converts the arrayList of Strings into a string of labels separated by a space
 * 
 * @param labels arrayList of Strings
 * @return a string of labels
 */

private String toGcalString(ArrayList<String> labels){
	logger.debug("In Gcal String");
	String labelsString = "";
	if(labels!=null){
	for(int i=0; i<labels.size(); i++){
		labelsString+=labels.get(i)+ " ";
	}
	logger.debug("returning");
	return labelsString;
	}
	return "";
}
/** adds the task from JotIt down to Gcal
 * 
 * @param task task to be added to GCal
 * @param reminderMins Minutes before which the user should be reminder using the reminderMethod
 * @param reminderMethod 
 * @return a CalendarEventEntry object added to GCal
 */

public CalendarEventEntry addTask(Task task,int reminderMins,Method reminderMethod){
	logger.debug("In addTask");
	CalendarEventEntry newEntry = new CalendarEventEntry();
	newEntry.setTitle(new PlainTextConstruct(task.getName()));
	String description=setGcalDescription(task.getCompleted(),task.getImportant(),task.getDeadline(),task.getRecurring(),task.getRecurringId(),toGcalString(task.getLabels()));
	logger.debug(description);
	newEntry.setContent(new PlainTextConstruct(description));
	logger.debug(newEntry.getPlainTextContent());
	DateTime start=getDateTime(task.getStart());
	DateTime end=getDateTime(task.getEnd());
	When eventTime=new When();
	if(start == null){
		eventTime.setStartTime(end);
	}
	else{
		eventTime.setStartTime(start);
	}
	if(end!=null){
		eventTime.setEndTime(end);
	}
	newEntry.addTime(eventTime);
	try{
		CalendarEventEntry entry=calenService.insert(userCalendarUrl,newEntry);
		if(entry == null){
			return null;
		}
		task.setGCalId(entry.getId().replaceAll("http://www.google.com/calendar/feeds/default/events/", ""));
		Method type=reminderMethod;
		Reminder reminder = new Reminder();
		reminder.setMinutes(reminderMins);
		reminder.setMethod(type);
		entry.getReminder().add(reminder);
		entry.update();
		return entry;
	}
	catch(IOException e1){
		return null;
	}
	catch(ServiceException e2){
		return null;
	}
	
}
/** Deletes the event which matches with the name and 
 * 
 * @param taskToBeDeleted
 * @return
 */

public boolean deleteEvent(Task taskToBeDeleted){
	List<CalendarEventEntry> entriesList=getAllEntries();
	for(CalendarEventEntry entry : entriesList){
		Task gTask=calendarEntryToTask(entry);
		logger.debug(gTask.isEqual((taskToBeDeleted)));
		if(gTask.getName().equalsIgnoreCase(taskToBeDeleted.getName())
				&& gTask.getStart().isEqual(taskToBeDeleted.getStart())){
			try{
			logger.debug("Task=Event");
			entry.delete();
			return true;
			}
			catch(IOException e){
				logger.debug("IOException");
				return false;
			}
			catch(ServiceException e){
				logger.debug("Service Exception");
				return false;
			}
		}
	}
	return false;
}
/** Converts a String of labels into an ArrayList of labels
 * 
 * @param labels string of labels
 * @return 
 */

public ArrayList<String> stringToArrayList(String labels){
	ArrayList<String> temp=new ArrayList<String>();
	String[] label=labels.split(" ");
	for(int i=0; i<label.length; i++){
		temp.add(label[i]);
	}
	return temp;
}
/** updates the event on the Google calendar.
 * 
 * @param oldTask task to be deleted
 * @param newTask task to be added
 * @return true when 
 */

public boolean updateEvent(Task oldTask,Task newTask){
	if(deleteEvent(oldTask)){
		addTask(newTask,0,Method.NONE);
		return true;
		}
	else{
		return false;
	}
}
/** Converts an object of taskDateTime to DateTime object
 * 
 * @param taskDateTime object of TaskDateTime class
 * @return DateTime object
 */

public DateTime getDateTime(TaskDateTime taskDateTime){
	if(taskDateTime==null){
		return null;
	}
	else if(taskDateTime.getHasTime()){
		return DateTime.parseDateTime(taskDateTime.dateTimeToXml());
	}
	else{
		return DateTime.parseDate(taskDateTime.dateToXml());
	}
}
/** imports from Google Calendar. If an event of the same Id already exists, the entry is not imported. This is done
 * to prevent duplication of Tasks in the live Storage
 * 
 * @return try if importing is performed successfully
 */

public boolean importFromGcal(){
	logger.debug("In import");
	boolean isPresent = false;
	Task[] taskArray = calendarEventListToTaskArray(getAllEntries());
	for(int i=0; i<taskArray.length; i++){
		isPresent=false;
		logger.debug("isPresent resetted to false");
		if(StorageManager.getAllTasks().length==0){
			logger.debug("Length 0" + taskArray[0].getGCalId());
			StorageManager.addTask(taskArray[0]);
		}
		else{
			for(int j=0; j<StorageManager.getAllTasks().length; j++){
				logger.debug(taskArray[i].getGCalId() + " " + StorageManager.getAllTasks()[j].getGCalId());
				if(taskArray[i].getGCalId().equals(StorageManager.getAllTasks()[j].getGCalId())){
					logger.debug(" Is present");
					isPresent = true;
					break;
				}
			}
			if(!isPresent){
				logger.debug("Checking if in Archives");
				for(int k=0;k<StorageManager.getAllArchivedTasks().length;k++){
					if(taskArray[i].getGCalId().equals(StorageManager.getAllArchivedTasks()[k].getGCalId())){
						logger.debug("Is present in archives");
						isPresent = true;
						break;
					}
				}
			}
			if(!isPresent){
				StorageManager.addTask(taskArray[i]);
				}
	}
	}
	return true;
}
/** Exports a task into the google calendar. If a task with the same id already exists, task is not exported to avoid duplication.
 * 
 * @return true if exporting is done successfully.
 */

public boolean exportToGcal(){
	boolean isPresent2=false;
	Task[] taskArray = calendarEventListToTaskArray(getAllEntries());
	logger.debug("In export");
	for(int i=0; i<StorageManager.getAllTasks().length; i++){
		isPresent2 = false;
		logger.debug("Again!!!!!!!!!");
		if(taskArray.length == 0){
			logger.debug("Length of entries is 0");
			addTask(StorageManager.getAllTasks()[i], 0, Method.NONE);
		}
		else{
			for(int j=0; j<taskArray.length; j++){
				logger.debug(StorageManager.getAllTasks()[i].getGCalId() + " " + taskArray[j].getGCalId());
				logger.debug(StorageManager.getAllTasks()[i].toString());
				if(StorageManager.getAllTasks()[i].getGCalId().equals(taskArray[j].getGCalId())){
					isPresent2 = true;
					logger.debug("the google id of google cal task is same as live storage" );
					if(!(StorageManager.getAllTasks()[i].isEqual(taskArray[j]))){
						logger.debug(StorageManager.getAllTasks()[i].toString());
						logger.debug(taskArray[j].toString());
						logger.debug("The two tasks with same google id are not identical");
						updateEvent(taskArray[j],StorageManager.getAllTasks()[i]);
					}
					break;
				}
			}
				if(!isPresent2){
					addTask(StorageManager.getAllTasks()[i], 0, Method.NONE);
				}
		}
	}
	return true;
}
/** Firstly,deletes all the tasks which are not present in the software but are present in Google calendar. 
 * Then performs the import and export function.
 *
 * @return
 */

public boolean sync(){
	Task[] taskArray = calendarEventListToTaskArray(getAllEntries());
	if(StorageManager.getAllTasks().length!=0){
	for(int i=0; i<taskArray.length; i++){
		boolean isPresent3 = false;
		for(int k=0; k<StorageManager.getAllTasks().length; k++){
			if(taskArray[i].getGCalId().equalsIgnoreCase(StorageManager.getAllTasks()[k].getGCalId())){
				isPresent3 = true;
				break;
			}
		}
		if(!isPresent3){
			deleteEvent(taskArray[i]);
		}
	}
	}
	if(importFromGcal() && exportToGcal()){
		return true;
	}
	else{
		return false;
	}
}
/**
 * 
 * @return true if the user is logged in, otherwise false
 */

public boolean isLoggedIn(){
	return loggedIn;
}
@Override
public void run() {
	// TODO Auto-generated method stub
	sync();
	}
}


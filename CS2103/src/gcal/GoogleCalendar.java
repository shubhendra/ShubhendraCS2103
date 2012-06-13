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
//import parser.Parser;
public class GoogleCalendar
{
	private Logger logger = Logger.getLogger(GoogleCalendar.class.getName());
	private static final String USER_CALENDAR_URL = "https://www.google.com/calendar/feeds/default/private/full";
	private static final String APPLICATION_NAME="JotItDown";
	private CalendarService calenService=new CalendarService(APPLICATION_NAME);
	public static final String APPLICATION_NAME_ID_PROPERTY = String.format(":%s:%s:",APPLICATION_NAME, "Id");
	private String username;
	private String password;
	private static boolean loggedIn;
	private static URL userCalendarUrl;
	private Parser parser=new Parser();
	//Parser testParser=new Parser();
	/*private Task task1=testParser.parseForAdd("Go to Airport on 10 june 2012 at 4 pm");
	private Task task2=testParser.parseForAdd("Go for a movie on 8 june 2012 at 9 pm");
	private Task task3=testParser.parseForAdd("Go buy shoes at Vivo on 9 june 2012 at 10 am");*/
		
	public boolean login(String userName,String passWord)
	{
		this.username=userName;
		this.password=passWord;
		try
		{
			calenService.setUserCredentials(username,password);
			userCalendarUrl=new URL(USER_CALENDAR_URL);
			loggedIn=true;
		}
		catch(AuthenticationException e){
			logger.debug("Authentication Exception");
			loggedIn=false;
		}
		catch(MalformedURLException e){
			logger.debug("MalformedURLException");
			loggedIn=false;
		}
		return loggedIn;
		
	}
	public boolean logout(){
		calenService=new CalendarService(APPLICATION_NAME);
		this.username=null;
		this.password=null;
		loggedIn=false;
		return true;
	}
	public List<CalendarEventEntry> getAllEntries(){
		CalendarEventFeed resultFeed=null;
		if(!loggedIn){
			return null;
		}
		try
		{
			resultFeed=calenService.getFeed(userCalendarUrl, CalendarEventFeed.class);
		}
		catch(IOException e)
		{
			System.out.println("IO Exception");
		}
		catch(ServiceException e)
		{
			System.out.println("Service Exception");
		}
			List<CalendarEventEntry> entries=resultFeed.getEntries();
			return entries;
	}
	public Task[] calendarEventListToTaskArray(List<CalendarEventEntry> userEntries){
		ArrayList<Task> list= new ArrayList<Task>();
		for(CalendarEventEntry entry : userEntries ){
			list.add(calendarEntryToTask(entry));
		}
			return list.toArray(new Task[list.size()]);
	}
	public Task calendarEntryToTask(CalendarEventEntry entry)
	{
		Task googleCalTask=new Task();
		ArrayList<String> labelList=new ArrayList<String>();
		googleCalTask.setName(entry.getTitle().getPlainText());
		String entryDesc=entry.getPlainTextContent();
		logger.debug(entryDesc);
		String desc=entry.getId().replaceAll("http://www.google.com/calendar/feeds/default/events/", "");
		googleCalTask.setDescription(desc);
		List<When> eventTime=entry.getTimes();
		googleCalTask.setStart(getTaskDateTime(eventTime.get(0).getStartTime()));
		googleCalTask.getStart().setHasDate(true);
		googleCalTask.getStart().setHasTime(!eventTime.get(0).getStartTime().isDateOnly());
		String[] descr=new String[6];
		descr=parser.fetchGCalDes(entryDesc);
		for(int i=0;i<5;i++)
		{
			logger.debug(descr[i]);
		}
		if(descr[0]=="true")
			googleCalTask.setCompleted(true);
		else
			googleCalTask.setCompleted(false);
		if(descr[1]=="true")
			googleCalTask.setImportant(true);
		else
			googleCalTask.setImportant(false);
		if(descr[2]=="true")
			googleCalTask.setDeadline(true);
		else
			googleCalTask.setDeadline(false);
		googleCalTask.setRecurring(descr[3]);
		googleCalTask.setRecurringId(descr[4]);
		labelList=stringToArrayList(descr[5]);
		logger.debug(labelList.toString());
		
		logger.debug(getTaskDateTime(eventTime.get(0).getStartTime()).toString());
		logger.debug(getTaskDateTime(eventTime.get(0).getEndTime()).toString());
		logger.debug(googleCalTask.getName());
		if(getTaskDateTime(eventTime.get(0).getStartTime()).toString().equals(getTaskDateTime(eventTime.get(0).getEndTime()).toString())){
			logger.debug("Start end date time is same");
			googleCalTask.setEnd(null);
		}
		else
		{
		googleCalTask.setEnd(getTaskDateTime(eventTime.get(0).getEndTime()));
		googleCalTask.getEnd().setHasDate(true);
		googleCalTask.getEnd().setHasTime(!eventTime.get(0).getEndTime().isDateOnly());
		}
		return googleCalTask;
	}
	
	private data.TaskDateTime getTaskDateTime(DateTime dateTime) {
		return TaskDateTime.xmlToEventTime(dateTime.toString());
	}
	public String setGcalDescription(boolean completed,boolean deadline,boolean important,String recurring,String recurringId,String labels)
	{
		String entryCompleted="<"+"CMPT:"+completed+">";
		String entryDeadline="<"+"IMPT:"+important+">";
		String entryImportant="<"+"DEAD:"+deadline+">";
		String entryRecurring="<"+"RECUR:"+recurring+">";
		String entryRecurringId;
		if(recurringId==null || recurringId=="")
			entryRecurringId="<"+"RECURID:"+">";
		else
			entryRecurringId="<"+"RECURID:"+recurringId+">";
		String entryLabels;
		if(labels.equals(new ArrayList<String>()))
			entryLabels="<"+"LABEL:"+">";
		else
			entryLabels="<"+"LABEL:"+labels+">";
		return entryCompleted+entryDeadline+entryImportant+entryRecurring+entryRecurringId+entryLabels;
	}
	private String toGcalString(ArrayList<String> labels)
	{
		String labelsString = "";
		logger.debug(labels.size());
		if(!(labels.size()==0)){
		for(int i=0;i<labels.size();i++)
		{
			labelsString+=labels.get(i)+ " ";
		}
		return labelsString;
		}
		return "";
	}
	public CalendarEventEntry addTask(Task task,int reminderMins,Method reminderMethod)
	{
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
		if(start==null)
			eventTime.setStartTime(end);
		else
			eventTime.setStartTime(start);
		if(end!=null)
		{
			eventTime.setEndTime(end);
		}
		newEntry.addTime(eventTime);
		try
		{
			CalendarEventEntry entry=calenService.insert(userCalendarUrl,newEntry);
			if(entry==null)
				return null;
			task.setDescription(entry.getId().replaceAll("http://www.google.com/calendar/feeds/default/events/", ""));
			Method type=reminderMethod;
			Reminder reminder=new Reminder();
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
	public boolean deleteEvent(Task taskToBeDeleted)
	{
		List<CalendarEventEntry> entriesList=getAllEntries();
		for(CalendarEventEntry entry : entriesList){
			Task gTask=calendarEntryToTask(entry);
			logger.debug(gTask.isEqual((taskToBeDeleted)));
			if(gTask.getName().equalsIgnoreCase(taskToBeDeleted.getName())
					&& gTask.getStart().isEqual(taskToBeDeleted.getStart())){
				try
				{
				logger.debug("Task=Event");
				entry.delete();
				return true;
				}
				catch(IOException e)
				{
					logger.debug("IOException");
					return false;
				}
				catch(ServiceException e)
				{
					logger.debug("Service Exception");
					return false;
				}
			}
		}
		return false;
	}
	public ArrayList<String> stringToArrayList(String labels)
	{
		ArrayList<String> temp=new ArrayList<String>();
		String[] label=labels.split(" ");
		for(int i=0;i<label.length;i++)
		{
			temp.add(label[i]);
		}
		return temp;
	}
	public boolean updateEvent(Task oldTask,Task newTask)
	{
		/*List<CalendarEventEntry> entriesList=getAllEntries();
		for(CalendarEventEntry entry : entriesList){
			Task gTask=calendarEntryToTask(entry);
			logger.debug(gTask.isEqual((oldTask)));
			if(gTask.getName().equalsIgnoreCase(oldTask.getName())
					&& gTask.getStart().isEqual((oldTask.getStart()))){*/
				deleteEvent(oldTask);
				addTask(newTask,0,Method.NONE);
				return true;
	}
	public DateTime getDateTime(TaskDateTime taskDateTime){
		if(taskDateTime==null)
			return null;
		else if(taskDateTime.getHasTime())
		{
			return DateTime.parseDateTime(taskDateTime.dateTimeToXml());
		}
		else
			return DateTime.parseDate(taskDateTime.dateToXml());
	}
	public boolean importFromGcal()
	{
		boolean isPresent = false;
		Task[] taskArray=calendarEventListToTaskArray(getAllEntries());
		System.out.println(taskArray.length);
		
		for(int i=0;i<taskArray.length;i++)
		{
			isPresent=false;
			logger.debug("isPresent resetted to false");
			if(StorageManager.getAllTasks().length==0)
			{
				logger.debug("Length 0" + taskArray[0].getDescription());
				StorageManager.addTask(taskArray[0]);
				System.out.println(taskArray[0].getTaskId());
			}
			else
			{
				for(int j=0;j<StorageManager.getAllTasks().length;j++){
					logger.debug(taskArray[i].getDescription() + " " + StorageManager.getAllTasks()[j].getDescription());
					if(taskArray[i].getDescription().equals(StorageManager.getAllTasks()[j].getDescription()))
					{
						logger.debug(" Is present");
						isPresent=true;
						break;
					}
				}
				if(!isPresent){
					logger.debug("Checking if in Archives");
					for(int k=0;k<StorageManager.getAllArchivedTasks().length;k++){
						if(taskArray[i].getDescription().equals(StorageManager.getAllArchivedTasks()[k].getDescription()))
						{
							logger.debug("Is present in archives");
							isPresent=true;
							break;
						}
					}
				}
				if(!isPresent){
					StorageManager.addTask(taskArray[i]);
					System.out.println(taskArray[i].getTaskId());}
		}
		}
		return true;
	}
	public boolean exportToGcal()
	{
		boolean isPresent2=false;
		Task[] taskArray=calendarEventListToTaskArray(getAllEntries());
		logger.debug("In export");
		for(int i=0;i<StorageManager.getAllTasks().length;i++)
		{
			isPresent2=false;
			logger.debug("Again!!!!!!!!!");
			if(taskArray.length==0)
			{
				logger.debug("Length of entries is 0");
				addTask(StorageManager.getAllTasks()[i],0,Method.NONE);
			}
			else
			{
				for(int j=0;j<taskArray.length;j++)
				{
					logger.debug(StorageManager.getAllTasks()[i].getDescription() + " " + taskArray[j].getDescription());
					logger.debug(StorageManager.getAllTasks()[i].toString());
					if(StorageManager.getAllTasks()[i].getDescription().equals(taskArray[j].getDescription()))
					{
						isPresent2=true;
						logger.debug("the desc id of google cal task is same as live storage" );
						if(!(StorageManager.getAllTasks()[i].isEqual(taskArray[j])))
						{
							logger.debug(StorageManager.getAllTasks()[i].toString());
							logger.debug(taskArray[j].toString());
							logger.debug("The two tasks with same description are not identical");
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
	public boolean sync()
	{
		Task[] taskArray=calendarEventListToTaskArray(getAllEntries());
		for(int i=0;i<taskArray.length;i++)
		{
			boolean isPresent3=false;
			for(int k=0;k<StorageManager.getAllTasks().length;k++)
			{
				if(taskArray[i].getDescription().equalsIgnoreCase(StorageManager.getAllTasks()[k].getDescription()))
				{
					isPresent3=true;
					break;
				}
			}
			if(!isPresent3)
			{
				deleteEvent(taskArray[i]);
			}
		}
		if(importFromGcal() && exportToGcal())
			return true;
		else
			return false;
		
	}
	public boolean isLoggedIn(){
		return loggedIn;
	}
}


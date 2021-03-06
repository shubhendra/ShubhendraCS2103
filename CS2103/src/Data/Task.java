/** The Task class on which JotItDown is based on. 
 * 
 * @author Nirav Gandhi
 */
package data;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Task {
	private String taskId;// changed to string id for hashing.
	private String name;
	private String gCalId;
	private TaskDateTime start;
	private TaskDateTime end;
	private boolean completed;
	private boolean important;
	private boolean deadline;
	private ArrayList<String> labels;
	private String recurring;
	private String recurringId;
	
/** Default constructor
 * 
 */
public Task(){
	taskId = null;
	name = "";
	gCalId = "";
	start = null;
	end = null;
	completed = false;
	important = false;
	deadline = false;
	labels = new ArrayList<String>();
	recurring = "";
	recurringId = "";
}

/**Task Constructor 1*/
public Task(String Name, String desc, TaskDateTime startDateTime, TaskDateTime endDateTime, String recurring){
	this();
	name = Name;
	gCalId = desc;
	start = startDateTime;
	end = endDateTime;
	this.recurring = recurring;
	recurringId="";
}

/** Task Constructor 2
 *
 */
public Task(String Name, String desc, TaskDateTime startDateTime, TaskDateTime endDateTime,ArrayList<String> Labels, String recurring, boolean Deadline, boolean Important){
	this();
	name = Name;
	gCalId = desc;
	start = startDateTime;
	end = endDateTime;
	if(labels == null)
		labels = new ArrayList<String>();
	labels = Labels;
	this.recurring = recurring;
	deadline = Deadline;
	important = Important;
	recurringId = "";
}

/** Task Constructor 3
 * 
 */
public Task(String Name,String desc,TaskDateTime startDateTime,TaskDateTime endDateTime,ArrayList<String> Labels,String recurring){
	this();
	name = Name;
	gCalId = desc;
	start = startDateTime;
	end = endDateTime;
	if(labels == null){
		labels = new ArrayList<String>();
		}
	labels = Labels;
	this.recurring = recurring;
	recurringId = "";
}

/** Task Constructor 3
 * 
 */
public Task(String Name){
	this();
	this.name = Name;
}

/**
 * 
 * @return the taskId attribute of the Task
 */
public String getTaskId(){
	return taskId;
}

/** setter for attribute taskId
 * 
 * @param id String to which the taskId is set to
 */
public void setTaskId(String id){
	taskId = id;
}

/**
 * 
 * @return the arrayList of labels
 */
public ArrayList<String> getLabels(){
	return labels;
}

/**
 * setter for the attribute labels
 * @param labels2 ArrayList<String> to which the labels are set to.
 */
public void setLabels(ArrayList<String> labels2){
	if(labels2 == null){
		labels = new ArrayList<String>();
	}
	labels = labels2;
}

/** 
 * 
 * @return the attribute name 
 */
public String getName(){
	return name;
}

/** setter for attribute name
 * 
 * @param Name String to which attribute Name is set to. 
 */
public void setName(String Name){
	name = Name;
}

/**
 * 
 * @return the gCalId string
 */
public String getGCalId(){
	return gCalId;
}

/** setter for attribute gCalId
 * 
 * @param desc String to which attribute gCalId is set to.
 */
public void setGCalId(String desc){
	gCalId = desc;
}

/** 
 * 
 * @return the start attribute as a DateTime object
 */
public TaskDateTime getStart(){
	return start;
}

/** sets the start attribute
 * 
 * @param startDateTime the DateTime object to which start is set to
 */
public void setStart(TaskDateTime startDateTime){
	start = startDateTime;
}

/**
 * 
 * @return the end attribute as a DateTime object
 */

public TaskDateTime getEnd(){
	return end;
}
/** sets the end attribute
 * 
 * @param endDateTime the DateTime object to which end is set to
 */

public void setEnd(TaskDateTime endDateTime){
	end = endDateTime;
}
/** 
 * 
 * @return the completed attribute
 */

public boolean getCompleted(){
	return completed;
}
/** set the attribute completed 
 * 
 * @param value the value to which the completed attribute is set to
 */

public void setCompleted(boolean value){
	this.completed = value;
}
/** 
 * 
 * @return the important attribute
 */

public boolean getImportant(){
	return important;
}
/** set the attribute important
 * 
 * @param value the value to which the important attribute is set to
 */

public void setImportant(boolean value){
	this.important = value;
}
/**
 * 
 * @return the deadline attribute
 */

public boolean getDeadline(){
	return deadline;
}
/** function to set the deadline
 * 
 * @param value the value the deadline attribute is set to
 */

public void setDeadline(boolean value){
	this.deadline = value;
}
/**
 * 
 * @return the recurringId attribute
 */

public String getRecurringId(){
	return recurringId;
}
/** function to set the attribute recurring id
 * 
 * @param ID to which the recurring Id is set to. 
 */

public void setRecurringId(String ID){
	recurringId=ID;
}
/** 
 * 
 * @return the attribute recurring 
 */

public String getRecurring(){
	return recurring;
}
/** function to set the recurring attribute
 * 
 * @param recurringString the value the attribute is set to.
 */

public void setRecurring(String recurringString){
	recurring = recurringString;
}
/**
 * 
 * @param to the second object
 * @return true if the Task object is the same as second object, otherwise false
 */

public boolean isEqual(Object to) {
	String thisObjString = null;
	String compareToObjString = null;
	if (to == null) {
		return false;
	}
	if (! (to instanceof Task) ) {
		return false;
	}
	Task compareTo = (Task) to;
	thisObjString = this.toString();
	compareToObjString = compareTo.toString();
	return thisObjString.equals(compareToObjString);
}
/** 
 * 
 * @param the second task object
 * @return true if the Task object is identical to the second Task, otherwise false
 */

public boolean isIdenticalTo(Task second){
	if(this.gCalId == second.gCalId && this.name==second.name && 
			this.end == second.end && this.start==second.start && this.recurring==second.recurring && this.taskId==second.taskId){
		return true;
	}else{
		return false;
	}
}
/** toggles the attribute important 
 * 
 */

public void toggleImportant(){
	important = !important;
}

/** toggles the attribute complete 
 * 
 */

public void toggleCompleted(){
	completed = !completed;
}
/** toggles the attribute deadline
 * 
 */

public void toggleDeadline(){
	deadline = !deadline;
}
/**
 * 
 * @return the formatted string 
 */

public String toString(){
	String imp = "";
	TaskDateTime temp=new TaskDateTime();
	if (this.getImportant()){
		imp = "*";
	}
	if((start!=null && end!=null && start.compareTo(temp)!=0 && (end.compareTo(temp)!=0))){
		if(start.getDate().getTimeMilli()==end.getDate().getTimeMilli()){
			return imp+name + " from " + start.getTime().formattedToString() + " to " + end.getTime().formattedToString() + " on " + start.getDate().formattedToString() + " "+toStringLabels(); 
		}else{
			return imp+name + " from " + start.formattedToString() + " to " + end.formattedToString()  + " "+toStringLabels(); 
		}
	}
	else if (start==null && end ==null){
		return " ";
	}
	else if((end==null ||( start!=null && start.compareTo(temp)!=0 && end.compareTo(temp)==0))){
		if(start.getHasTime()){
			return imp+name + " at " + start.getTime().formattedToString() + " on " + start.getDate().formattedToString()  + " "+toStringLabels(); 
		}
		else{
			return imp+name + " on " +start.getDate().formattedToString() +" "+ toStringLabels();  
		}
	}
	else if((start ==null || ( end!=null && start.compareTo(temp)==0) && !(end.compareTo(temp)==0)) ){
		if(end.getHasTime())
			return imp+name + " by "+ end.getTime().formattedToString()+" on " + end.getDate().formattedToString() +" "+ toStringLabels(); 
		else
			return imp+name + " by " + end.formattedToString() + " "+ toStringLabels(); 
	}
	else
	{
		return " ";
	}
			
}
/**
 * 
 * @return another option to display the output of the Task
 */

public String toString2(){
	TaskDateTime temp=new TaskDateTime();
	if(((start!=null && end!=null && start.compareTo(temp)!=0 && (end.compareTo(temp)!=0))||(end==null) 
			||( start!=null && start.compareTo(temp)!=0 && end.compareTo(temp)==0))){
		long diffMilliSeconds=start.getTimeMilli()-TaskDateTime.getCurrentDateTime().getTimeMilli();
		long diffSeconds=diffMilliSeconds/1000;
		long diffMinutes=diffMilliSeconds/(60*1000);
		long diffHours=diffMilliSeconds/(60*60*1000);
		long diffDays=diffMilliSeconds/(60*60*1000*24);
		long diffWeeks=diffDays/7;
		if(diffMilliSeconds<0)
			return "Deadline missed!";
		else if(diffSeconds<2)
			return "Starts Now";
		else if(diffSeconds<60)
			return String.format("Starts in %d seconds", diffSeconds);
		else if(diffMinutes<60)
			return String.format("Starts in %d minutes", diffMinutes);
		else if(diffHours<24)
			return String.format("Starts in %d hours", diffHours);
		else if(diffDays<7)
			return String.format("On %s", displayNameOfWeek(start.get(GregorianCalendar.DAY_OF_WEEK)));
		else if(diffWeeks<5){
			if(diffWeeks<2)
				return "Next week";
			else 
				return String.format("%d weeks from now", diffWeeks);
		}
		else if(diffDays<365){
			int diffMonths=(int)diffDays/30;
			if(diffMonths<2)
				return "Next Month";
			else
				return String.format("%d months from now", diffMonths);
		}
		else if(diffDays<730)
			return "Next year";
		else
			return String.format("%d years from now", diffDays/365);
	}
	else if((start ==null || ( end!=null && start.compareTo(temp)==0) && !(end.compareTo(temp)==0))) 
	{
		long diffMilliSeconds=end.getTimeMilli()-TaskDateTime.getCurrentDateTime().getTimeMilli();
		long diffSeconds=diffMilliSeconds/1000;
		long diffMinutes=diffMilliSeconds/(60*1000);
		long diffHours=diffMilliSeconds/(60*60*1000);
		long diffDays=diffMilliSeconds/(60*60*1000*24);
		long diffWeeks=diffDays/7;
		if(diffMilliSeconds<0)
			return "deadline is over";
		else if(diffSeconds<2)
			return "due now";
		else if(diffSeconds<60)
			return "due in a minute";
		else if(diffMinutes<60)
			return String.format("due in %d minutes", diffMinutes);
		else if(diffHours<24)
			return String.format("due in %d hours", diffHours);
		else if(diffDays<7)
			return String.format("due on %s", displayNameOfWeek(start.get(GregorianCalendar.DAY_OF_WEEK)));
		else if(diffWeeks<5){	
			if(diffWeeks<2)
				return "due next week";
			else
				return String.format("due in %d weeks", diffWeeks);
		}
		else if(diffDays<365){
			int diffMonths=(int)diffDays/30;
			if(diffMonths<2)
				return "Due next Month";
			else
				return String.format("due %d months from now", diffMonths);
		}
		else if(diffDays<730)
			return "Next year";
		else
			return String.format("%d years from now", diffDays/365);
	}
	return "";
}
/** 
 * 
 * @param dayOfWeek the number of the day of the week
 * @return the string containing the name of the day of the week
 */

public String displayNameOfWeek(int dayOfWeek){
	switch(dayOfWeek)
	{
	case 1:return "Sunday";
	case 2:return "Monday";
	case 3:return "Tuesday";
	case 4:return "Wednesday";
	case 5:return "Thursday";
	case 6:return "Friday";
	case 7:return "Saturday";
	default:
		return "";
	}
}
/**
 * 
 * @return a string of the format in which the labels are printed.
 */

public String toStringLabels(){
	String stringLabels = "";
	if (labels != null){
	if (labels.size()!=0){
		for(int i=0; i<labels.size(); i++){
			stringLabels+="@"+labels.get(i)+" ";
		}	
	}
	}
	return stringLabels;
}
}
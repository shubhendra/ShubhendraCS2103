
import java.util.List;

public class Task {


	private String taskId;// changed to string id for hashing.
	private String name;
	private String description;
	private DateTime start;
	private DateTime end;
	private boolean completed;
	private boolean important;
	private boolean deadline;
	private List<String> labels;
	private String recurring;

public Task() 
{
	taskId=null;
	name="";
	description="";
	start=null;
	end=null;
	completed=false;
	important=false;
	deadline=false;
	labels=null;
	
}
/**Constructor*/
public Task(String Name,String desc,DateTime startDateTime,DateTime endDateTime,List<String> Labels, boolean complete, boolean impt, boolean duedate)
{
	this();
	name = Name;
	description = desc;
	start = startDateTime;
	end = endDateTime;
	labels=Labels;
	completed=complete;
	important=impt;
	deadline=duedate;
}
public Task(String Name)
{
	this();
	this.name=Name;
}
/** get the taskId of the Task*/
public String getTaskId()
{
	return taskId;
}
/** mark the Task as important or unimportant*/
public void toggleImportant()
{
	important = !important;
}

/** mark the Task as completed or unimportant*/
public void toggleCompleted()
{
	completed = !completed;
}
/*
public void toggleDeadline()
{
	deadline=!deadline;
}*/
public List<String> getLabels()
{
	return labels;
}
public void setLabels(List<String> labels2)
{
	labels=labels2;
}
/** get the name of the Task*/
public String getName()
{
	return name;
}
/** get the description of the Task*/
public String getDescription()
{
	return description;
}
/** get the startDate of the Task*/
public DateTime getStartDateTime()
{
	return start;
}
/**get the endDate of the Task*/
public DateTime getEndDateTime()
{
	return end;
}
/** check whether the Task is Completed
 * 
 * @return true if the task is Completed, otherwise false
 */
public boolean isCompleted()
{
	return completed;
}
/** check whether the task is Important
 * 
 * @return true if the task is important, otherwise false
 */
public boolean isImportant()
{
	return important;
}
public void setStartDateTime(DateTime startDateTime)
{
	start = startDateTime;
}
public void setEndDateTime(DateTime endDateTime)
{
	end = endDateTime;
}
public void setTaskId(String newid)
{
	taskId = newid;
}
public void setName(String taskName)
{
	name = taskName;
}
public void setDescription(String Desc)
{
	description = Desc;
}
public void setImportant(boolean value)
{
	this.important=value;
}
public void setDeadline(boolean value)
{
	this.deadline=value;
}
public void setCompleted(boolean value)
{
	this.completed=value;
}
public boolean isEqual(Object to) {
	String thisObjString = null;
	String compareToObjString = null;
	if (to == null) {
		return false;
	}
	if (!(to instanceof Task)) {
		return false;
	}
	Task compareTo = (Task) to;
	thisObjString = this.toString();
	compareToObjString = compareTo.toString();
	return thisObjString.equals(compareToObjString);
}
}
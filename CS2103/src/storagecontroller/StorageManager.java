package storagecontroller;
import data.TaskHashMap;
import data.Task;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StorageManager 
{
	private TaskHashMap liveStorage;
	public StorageManager()
	{
		liveStorage=new TaskHashMap();
	}
	
	public boolean addTask(Task taskToBeAdded)
	{
		return liveStorage.addTask(taskToBeAdded);
		
	}
	public boolean deleteTask(Task taskToBeRemoved)
	{
		return liveStorage.deleteTask(taskToBeRemoved);
			
	}
	public Task[] getAllTasks()
	{
		ArrayList<Task> tasks=new ArrayList<Task>();
		for(String key: liveStorage.getKeySet())
		tasks.add(liveStorage.getTaskById(key));
		Task[] taskArray=new Task[tasks.size()];
		tasks.toArray(taskArray);
		return taskArray;
	}	
	public Task getTaskById(String id)
	{
		return liveStorage.getTaskById(id);
	}
	public boolean loadFile() throws ArrayIndexOutOfBoundsException, FileNotFoundException
	{
		FileHandler handler=new FileHandler("JotItDownDatabase.xml");
		if(liveStorage.getKeySet().size()!=0)
			liveStorage.clearHashMap();
		return handler.readFromFile(liveStorage);
	}
	public boolean saveFile() throws FileNotFoundException
	{
	FileHandler handler=new FileHandler("JotItDownDatabase.xml");
	if(handler.writeToFile(liveStorage))
		return true;
	else 
		return false;
	}
	public boolean replaceTask(Task taskToBeReplaced,Task taskToReplaceBy)
	{
		return ((liveStorage.deleteTask(taskToBeReplaced)) && (liveStorage.addTask(taskToReplaceBy)));
	}
	public void exportToTxt(String fileName) throws FileNotFoundException
	{
		FileHandler handler=new FileHandler(fileName);
		handler.writeToFile(liveStorage);
	}
	public boolean deleteTask(String id)
	{
		return liveStorage.deleteTaskById(id);
	}
	public boolean saveArchive()
	{
		return false;
	}
	public boolean clearArchive()
	{
		return false;
	}
}


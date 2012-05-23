import java.util.Map;
import java.util.HashMap;
public class Storage2
{
	private Map<String,Task> taskList;
	/** constructor*/
	public Storage2()
	{
		taskList=new HashMap<String,Task>();
	}
	/** Member function to add task
	 * 
	 * @param newTask task to be added
	 */
	public boolean addTask(Task taskToBeAdded)
	{
		assert(taskToBeAdded!=null);
		taskToBeAdded.setTaskId(generateUniqueId(taskToBeAdded));
		taskList.put(taskToBeAdded.getTaskId(), taskToBeAdded);
		return true;
	}
	private String generateUniqueId(Task taskToBeAdded)
	{
		String taskId;
		taskId=taskToBeAdded.getEndDateTime().generateDateCode()+taskToBeAdded.getEndDateTime().generateTimeCode();
		return taskId;
	}
	/** Member function to delete task
	 * 
	 * @param taskId id of the task to be deleted
	 */
	public void delete(Task taskToRemove)
	{
		assert(taskToRemove!=null);
		taskList.remove(taskToRemove.getTaskId());
	}
	/** Member function to get task
	 * 
	 * @param taskId id of the task needed
	 * @return the task of the provided taskId
	 */
	public Task getTaskbyId(String id)
	{
		return taskList.get(id);
	}
	
}
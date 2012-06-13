package storagecontroller;

import data.Task;
import data.TaskArrayList;
import data.TaskHashMap;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



import org.apache.log4j.Level;
import org.apache.log4j.Logger;
public class FileHandler 
{
	
	private static Logger logger = Logger.getLogger(FileHandler.class);
	private static String fileName;
	private BufferedWriter writer;
	private BufferedReader reader;
	/** Constructor to set the filename 
	 * 
	 * @param name name of the file to which xml encoder writes and from which xml decoder reads
	 */
	public FileHandler(String name)
	{
		fileName=name;
		
	}
	/** function to write to the file with name as fileName 
	 * 
	 * @param instance the TaskHashMap instance. Is also the live storage.
	 * @return true if written to the file without any errors, otherwise false
	 */
	public boolean writeToFile(TaskHashMap instance) 
	{
		try
		{
		BufferedOutputStream xmlOut=new BufferedOutputStream(new FileOutputStream(fileName));
		XMLEncoder writeToXml=new XMLEncoder(xmlOut);
		logger.debug(instance.getKeySet().size());
		for(String key: instance.getKeySet())
		{
			writeToXml.writeObject(instance.getTaskById(key));
		}
		writeToXml.close();
		return true;
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found!");
			return false;
		}
	}
	/** function to write to the file with name as fileName 
	 * 
	 * @param instance the TaskHashMap instance. Is also the live storage.
	 * @return true if read from the file without any errors, otherwise false.
	 */
	public boolean readFromFile(TaskHashMap instance) 
	{
		try
		{
		BufferedInputStream xmlIn=new BufferedInputStream(new FileInputStream(fileName));
		XMLDecoder readFromXml=new XMLDecoder(xmlIn);
			while(true)
			{
				Task newTask=(Task)readFromXml.readObject();
				instance.addTask(newTask);
			}
			
		}
		catch(FileNotFoundException e)
		{
			logger.debug("File Not Found!");
			return false;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			logger.debug("array out of bounds!");
			return false;
		}
		catch(NullPointerException e)
		{
			logger.debug("null pointer exception");
			return false;
		}
	}
	public String readDate()
	{
		String buffer,result="";
		try
		{
			reader=new BufferedReader(new FileReader(fileName));
			while ((buffer = reader.readLine()) != null)
			{
				System.out.println(buffer);
				result+=buffer;
			}
			reader.close();
		}
		catch(IOException e)
		{
			logger.debug("IOException");
		}
	return result;
	}
	public boolean writeEmailId(String emailId)
	{
		try
		{
		writer=new BufferedWriter(new FileWriter("JotItDownEMail.txt"));
		writer.write(emailId);
		writer.close();
		return true;
		}
		catch(IOException e)
		{
			logger.debug("IOException");
			return false;
		}
	}
	public boolean writeDate(String toWrite)
	{
		try
		{
			writer=new BufferedWriter(new FileWriter(fileName));
			writer.write(toWrite);
			writer.close();
			return true;
		}
		catch(IOException e)
		{
			logger.debug("IOException");
			return false;
		}
	}
	public String readEmailId()
	{
		String buffer,result="";
		try
		{
		reader=new BufferedReader(new FileReader("JotItDownEMail.txt"));
		while ((buffer = reader.readLine()) != null)
		{ 
			System.out.println(buffer);
			result+=buffer;
		}
		reader.close();
		return result;
		}
		catch(IOException e)
		{
			logger.debug("IOException");
		}
		return "";
	}
}

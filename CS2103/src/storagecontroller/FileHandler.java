/** Class responsible for reading into and writing from files.
 * 
 * @author Nirav Gandhi
 */
package storagecontroller;

import data.Task;
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
		for(String key: instance.getKeySet())
		{
			writeToXml.writeObject(instance.getTaskById(key));
		}
		writeToXml.close();
		return true;
		}
		catch(FileNotFoundException e)
		{
			//System.out.println("File Not Found!");
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
			return true;
		}
		catch(NullPointerException e)
		{
			logger.debug("null pointer exception");
			return false;
		}
	}
	/** reads the date from the file
	 * 
	 * @return the string containing the date
	 */
	public String readDate()
	{
		String buffer,result="";
		try
		{
			reader=new BufferedReader(new FileReader(fileName));
			while ((buffer = reader.readLine()) != null)
			{
				//System.out.println(buffer);
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
	/** writes the date into the text file
	 * 
	 * @param emailId the email id to be written into the file.
	 * @return
	 */
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
	/** writes the date into the file 
	 * 
	 * @param toWrite the date to be written
	 * @return true if the date is successfully written.
	 */
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
	/** reads the email id from the file.
	 * 
	 * @return the string containing the email id
	 */
	public String readEmailId()
	{
		String buffer,result="";
		try
		{
		reader=new BufferedReader(new FileReader("JotItDownEMail.txt"));
		while ((buffer = reader.readLine()) != null)
		{
			//System.out.println(buffer);
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

package parser;


import java.util.Arrays;
import java.util.List;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import data.DateTime;
import data.Task;

public class Parser {
	/*
	public Task[] getTasks(String command) {
		
		return null;
	}*/
	
	/*
	 * make all helper functions non static
	 */
	private final  String RECUR_REGEX = "(?i)(weekly|monthly|yearly)";
	private final  String LABEL_REGEX = "@(\\w+)";
	
	 boolean important;
	 boolean deadline;
	 DateTime startDateTime, endDateTime;
	 String recurring = null;
	 List<String> labelList = null;
	 String taskDetails=null;
	
	public  String removeExtraSpaces (String s) {
		return s.replaceAll("\\s+", " ");
	}
	
	public boolean markImportant (String s) {
		if (s.startsWith("*")){
			//s = s.replace('*', '\0');
			//s = s.trim();
			important = true;
			return true;
		}
		return false;
	}
	
	public String getRecurString (String s) {
		Pattern p = Pattern.compile(RECUR_REGEX);
		Matcher m = p.matcher(s);
		
		String recurString=null;
		
		if (m.find()) {
			recurString = m.group();
			recurString = recurString.toLowerCase();
			//s = s.replaceFirst("(?i)(weekly|monthly|yearly)", "");
			//s = removeExtraSpaces(s);
		}
		
		return recurString;
	}
	
	public String[] getLabels(String s) {
		Pattern p = Pattern.compile(LABEL_REGEX);
		Matcher m = p.matcher(s);
		String labelString = null;
		String[] labelArr= new String[50];
		
		int i=0;
		while(m.find()) {
				labelString = m.group();
				labelString = labelString.replace('@',' ');
				labelString = labelString.trim();
				labelArr[i]=labelString;
				i++;
		}
		return labelArr;
	}
	
	public void setDateTimeAttributes () {
		TimeParser t = new TimeParser();
		DateParser d = new DateParser();
		boolean startDateTimeExists, endDateTimeExists;
		
		int[] startTimeArr = t.getStartTime();
		int[] endTimeArr = t.getEndTime();
		int[] startDateArr = d.getStartDate();
		int[] endDateArr = d.getEndDate();
		
		startDateTimeExists = ((startTimeArr[0]>=0) && (startTimeArr[1]>=0) && (startDateArr[0]>0) && (startDateArr[1]>0) && (startDateArr[2]>0));
		
		if (startDateTimeExists) {
			startDateTime = new DateTime(startDateArr[2],startDateArr[1],startDateArr[0],startTimeArr[0],startTimeArr[1]);
		}
		
		endDateTimeExists = ((endTimeArr[0]>=0) && (endTimeArr[1]>=0) && (endDateArr[0]>0) && (endDateArr[1]>0) && (endDateArr[2]>0));
		
		if (endDateTimeExists) {
			endDateTime = new DateTime(endDateArr[2],endDateArr[1],endDateArr[0],endTimeArr[0],endTimeArr[1]);
		}
		
		/*
		 * tester print functions
		 */
		
		if (startDateTime!=null)
			System.out.println("start date time: "+startDateTime.formattedToString());
		
		if(endDateTime!=null)
			System.out.println("end date time: "+endDateTime.formattedToString());
	}
	
	public void setDeadline () {
		if (startDateTime==null && endDateTime!=null)
			deadline=true;
	}
	
	public Task parse (String inputS) {
		
		inputS = inputS.trim();
		
		
		/*
		 * markImportant
		 */
		if(markImportant(inputS)) {
			System.out.println("IMPORTANT TASK!");
			inputS = inputS.replace('*', '\0');
			inputS = inputS.trim();
		}
		
		
		
		/*
		 * recurring 
		 */	
		String recurring = getRecurString (inputS);
		
		if (recurring != null)
			System.out.println("this task is "+recurring);
		else
			System.out.println("this task is not recurring");
		
		inputS = inputS.replaceFirst(RECUR_REGEX, "");
		inputS = removeExtraSpaces(inputS);
		inputS = inputS.trim();
		
		System.out.println("left over string after checking for recurring: "+inputS);
		
		
		
		/*
		 * setLabels
		 */

		String[] labelArr = getLabels (inputS);
		
		if(labelArr.length!=0) {
			int i=0;
			while(labelArr[i]!=null){
				System.out.println("label "+i+": "+labelArr[i]);
				inputS = inputS.replaceFirst(LABEL_REGEX, "");
				i++;
			}
			System.out.println("left over string after fetching labels: "+inputS);
		}
		//--------YET TO SET TO LIST-------
		//labelList = toList(labelVector);
		
		
		
		TimeParser timeParser = new TimeParser(inputS);
		
		if(timeParser.extractStartEnd())
			System.out.println("time/date extracted!");
		else
			System.out.println("time/date NOT extracted!");
		
		

		System.out.println();
		System.out.println();
		setDateTimeAttributes();
		if(important)
			System.out.println("is important!");
		else
			System.out.println("is NOT important!");
		
		if(recurring!=null)
			System.out.println("has to be done: "+recurring);
		else
			System.out.println("it is not recurring");
		
		System.out.println("task details: "+timeParser.getinputCommand());
		
		setDeadline ();
		
		if(deadline)
			System.out.println("this task has a deadline you dumbass!");
		else
			System.out.println("this task does NOT have deadline you numbskull!");
		
		List<String> labelList = Arrays.asList(labelArr);
		
		taskDetails = timeParser.getinputCommand();
		
		
		Task t = new Task(taskDetails,null,startDateTime,endDateTime,labelList,recurring);
		t.setDeadline(deadline);
		
		return t;
	}

}


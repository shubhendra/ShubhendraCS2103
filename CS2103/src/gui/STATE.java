package gui;


import org.apache.log4j.Logger;

/**
 * to see what command user was currently using
 * different commands result in different action from GUI
 * @author Ramon
 *
 */
public enum STATE {
	
	ADD, DELETE, EDIT, SEARCH, COMPLETED, ARCHIVE
	, OVERDUE, NULL, LIST, UNDO, EXIT, HELP, REDO
	, IMPORTANT, LOGIN, LOGOUT, DELETEALL, COMPLETEDALL
	, CLEARARCHIVE, IMPORTARCHIVE, SYNCGCAL, IMPORTGCAL, EXPORTGCAL
	, CHECKFREE, EXPAND, EMAIL, IMPORTANTALL;

	private static Logger logger=Logger.getLogger(STATE.class);
	
	private static STATE curState = NULL;
	private static STATE prevState = NULL;
	private static String command;
	
	/**
	 * set the state from the string
	 * @param str the string that have to be extracted state
	 */
	public static void setState(String str) {
		setState(checkCommand(str), true);
	}
	
	/**
	 * set the curState to newState
	 * @param newState
	 */
	public static void setState(STATE newState) {
		setState(newState, false);
	}
	
	/**
	 * set the curState to newState and update the command if string is not received.
	 * @param newState
	 * @param isCommandSent true: the user has sent the command and the command is set. 
	 * false: no command from users
	 */
	public static void setState(STATE newState, boolean isCommandSent) {
		prevState = curState;
		curState = newState;
		if(!isCommandSent)
			command = curState.toString();
	}
	
	/**
	 * reset state to null.
	 */
	public static void resetState() {
		curState = NULL;
		prevState = NULL;
	}
	
	/**
	 * get the current state.
	 * @return current state
	 */
	public static STATE getState() {
		return curState;
	}
	
	/**
	 * get the previous state.
	 * @return previous state.
	 */
	public static STATE getPrevState() {
		return prevState;
	}
	
	
	/**This function returns the ending statement of the feedback 
	 * which is different for each command and for the different number of tasks. 
	 * For example, if the current state is “add” and “one” task is added, 
	 * it will return “was added.”\
	 * 
	 * @param isOneTask true: singular false:plural.
	 * @return String that will appear after task detail.
	 */
	public static String getEndedString(boolean isOneTask) {
		if(isOneTask)
			switch(curState){
			case ADD: return "was added.";
			case DELETEALL:
			case DELETE: return "was deleted.";
			case EDIT: return "was edited.";
			case COMPLETEDALL:
			case COMPLETED: return "was toggled completed.";
			case IMPORTANT: return "was toggled important.";
			case OVERDUE: return "was overdue.";
			case ARCHIVE: return "was moved to archive.";
			case UNDO: return "was undone.";
			case REDO: return "was redone.";
			case SEARCH: return "was found.";
			case IMPORTARCHIVE: return "was imported from archive.";
			case IMPORTGCAL: return "was imported from Google Calendar.";
			case SYNCGCAL: return "was synced with Google Calendar.";
			default:
				logger.error(curState + " getEndedString singular form");
				return "successfully";
			}
		else
			switch(curState) {
			case ADD: return "were added.";
			case COMPLETEDALL: return "were completed.";
			case DELETEALL: return "were deleted.";
			case OVERDUE: return "were overdue.";
			case COMPLETED: return "were toggled completed.";
			case IMPORTANT: return "were toggled important.";
			case ARCHIVE: return "were moved to archive.";
			case UNDO: return "were undone.";
			case REDO: return "were redone.";
			case SEARCH: return "were found.";
			case DELETE: return "were deleted.";
			case IMPORTARCHIVE: return "were imported from archive.";
			case IMPORTGCAL: return "were imported from Google Calendar.";
			case SYNCGCAL: return "were synced with Google Calendar.";
			case IMPORTANTALL: return "were toggled important.";
			default:
				logger.error(curState + " getEndedString plural form");
				return "successfully";
			}
	}
	
	/**
	 * This function acts as a filter to extract command from the string
	 * and to check which state that command belongs to
	 * @param curText text that needs extracting.
	 * @return state from extracting curText
	 */
	public static STATE checkCommand(String curText) {
		if(curText.equals("")) {
			logger.warn("receive null string from the input");
			return STATE.NULL;
		}
		
		String delims = "[ ]+";
		String firstWord = curText.trim().split(delims)[0];
		command = firstWord;
		
		if(firstWord.equalsIgnoreCase("add") 
				|| firstWord.equalsIgnoreCase("insert"))
			return STATE.ADD;
		if(firstWord.equalsIgnoreCase("delete")
				|| firstWord.equalsIgnoreCase("remove"))
			return STATE.DELETE;
		if(firstWord.equalsIgnoreCase("modify")
				|| firstWord.equalsIgnoreCase("edit")
				|| firstWord.equalsIgnoreCase("update"))
			return STATE.EDIT;
		if(firstWord.equalsIgnoreCase("search")
				|| firstWord.equalsIgnoreCase("find"))
			return STATE.SEARCH;
		if(firstWord.equalsIgnoreCase("completed")
				|| firstWord.equalsIgnoreCase("done"))
			return STATE.COMPLETED;
		if(firstWord.equalsIgnoreCase("archive"))
			return STATE.ARCHIVE;
		if(firstWord.equalsIgnoreCase("overdue"))
			return STATE.OVERDUE;
		if(firstWord.equalsIgnoreCase("list"))
			return STATE.LIST;
		if(firstWord.equalsIgnoreCase("undo"))
			return STATE.UNDO;
		if(firstWord.equalsIgnoreCase("exit"))
			return STATE.EXIT;
		if(firstWord.equalsIgnoreCase("help"))
			return STATE.HELP;
		if(firstWord.equalsIgnoreCase("redo"))
			return STATE.REDO;
		if(firstWord.equalsIgnoreCase("important") 
			||firstWord.equalsIgnoreCase("star"))
			return STATE.IMPORTANT;
		if(firstWord.equalsIgnoreCase("important.all")
			||firstWord.equalsIgnoreCase("star.all"))
			return STATE.IMPORTANTALL;
		if(firstWord.equalsIgnoreCase("login"))
			return STATE.LOGIN;
		if(firstWord.equalsIgnoreCase("delete.all"))
			return STATE.DELETEALL;
		if(firstWord.equalsIgnoreCase("completed.all"))
			return STATE.COMPLETEDALL;
		if(firstWord.equalsIgnoreCase("archive"))
			return STATE.ARCHIVE;
		if(firstWord.equalsIgnoreCase("clear.archive"))
			return STATE.CLEARARCHIVE;
		if(firstWord.equalsIgnoreCase("import.archive"))
			return STATE.IMPORTARCHIVE;
		if(firstWord.equalsIgnoreCase("sync.gcal"))
			return STATE.SYNCGCAL;
		if(firstWord.equalsIgnoreCase("import.gcal"))
			return STATE.IMPORTGCAL;
		if(firstWord.equalsIgnoreCase("export.gcal"))
			return STATE.EXPORTGCAL;
		if(firstWord.equalsIgnoreCase("check.free"))
			return STATE.CHECKFREE;
		if(firstWord.equalsIgnoreCase("logout"))
			return STATE.LOGOUT;
		if(firstWord.equalsIgnoreCase("expand"))
			return STATE.EXPAND;
		if(firstWord.equalsIgnoreCase("email"))
			return STATE.EMAIL;
		command = "";
		return STATE.NULL;
	} 

	/**
	 * get the command string.
	 * @return command string
	 */
	public static String getCommand() {
		return command;
	}

	/**
	 * This function provides valid feedback 
	 * that do not require task detail to be shown
	 * @return string to be shown on feedback
	 */
	public static String getFeedbackText() {
		switch(curState) {
		case LOGIN:
			return "Logged in successfully.";
		case IMPORTGCAL:
			return "Imported from google calendar successfully.";
		case EXPORTGCAL:
			return "Exported to google calendar successfully.";
		case SYNCGCAL:
			return "Synced with google calendar successfully.";
		case CLEARARCHIVE:
			return "Archive was removed successfully.";
		case CHECKFREE:
			return "That timeslot is free.";
		case EMAIL:
			return "Email approved.";
		
		default:
			logger.error(curState + "getFeedbackText");
			return "Successfully";
		}	
	}
}

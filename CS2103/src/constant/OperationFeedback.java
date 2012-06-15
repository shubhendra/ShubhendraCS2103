/**
 * Defines the various kinds of feedback messages for various operations
 * 
 * @author Shubhendra Agrawal
 * @author Ramon
 */
package constant;

public enum OperationFeedback {
	
	VALID,
	NO_EMAIL_SPECIFIED,
	INVALID_EMAIL,
	NO_UPCOMING_TASKS_IN_COMING_WEEK,
	START_DATE_TIME_LESS_THAN_CURRENT_DATE_TIME,
	END_DATE_TIME_LESS_THAN_CURRENT_DATE_TIME,
	START_DATE_TIME_MORE_THAN_END_DATE_TIME,
	INVALID_DATE_TIME,
	RECURRING_TIMES_EXCEEDED,
	INVALID_DATE,
	INVALID_TIME,
	INVALID_TASK_DETAILS,
	INVALID_LABEL,
	NOT_FOUND,
	UNDO_UNSUCCESSFUL,
	REDO_UNSUCCESSFUL,
	UNDO_SUCCESSFUL,
	REDO_SUCCESSFUL,
	UNDO_EMPTY,
	REDO_EMPTY,
	LOGGED_OUT_SUCCESSFULLY,
	LOGOUT_FAILED,
	INVALID_INCORRECT_LOGIN_INTERNET_CONNECTION, //incorrect username or password
	INVALID_NOINTERNET,
	INVALID_INCORRECTLOGIN,
	ADD_FAILED,
	DELETE_FAILED,
	EDIT_FAILED,
	STAR_FAILED,
	COMPLETE_FAILED,
	OVERDUE_FAILED, 
	BASESEARCH_FAILED,
	ARCHIVE_FAILED,
	TASK_COULD_NOT_BE_EXPORTED_FROM_ARCHIVES,
	TASK_COULD_NOT_BE_EXPORTED_TO_ARCHIVES,
	NO_TASK_IN_ARCHIVE,
	NO_TASK_TO_ARCHIVE,
	NO_TASK_DELETED,
	NO_TASK_OVERDUE,
	NO_TASK_COMPLETED,
	NO_TASK_STARRED,
	NOT_FREE,
	TASK_SPECIFIED_DOES_NOT_HAVE_BOTH_START_END_DATE_TIME,
	DURATION_LONGER_THAN_A_DAY,
	NO_MATCHING_ARCHIVE_FUNCTION,
	USER_NOT_LOGGEDIN,
	GOOGLE_CALENDAR_FAILED,
	USER_ALREADY_LOGGED_IN;
	
	/**
	 * returns the set message with each feedback
	 * @param op
	 * @return Message String
	 */
	public static String getString(OperationFeedback op) {
		switch(op) {
		case	VALID:
			return null;
		case NO_EMAIL_SPECIFIED:
			return "Please specify a valid email";
		case INVALID_EMAIL:
			return "Please specify a valid email.";
		case USER_ALREADY_LOGGED_IN:
			return "You are already logged in. Please log out first";
		case START_DATE_TIME_LESS_THAN_CURRENT_DATE_TIME:
			return "Error: Start date time is before the current date time.";
		case END_DATE_TIME_LESS_THAN_CURRENT_DATE_TIME:
			return "Error: End time begins before the current date time.";
		case START_DATE_TIME_MORE_THAN_END_DATE_TIME:
			return "Error: The start date time is after the end date time.";
		case INVALID_DATE_TIME:
			return "Error: Time/Date input is in invalid format.";
		case RECURRING_TIMES_EXCEEDED:
			return "Error: Recurrint times exceeded.";
		case INVALID_DATE:
			return "Error: The input date is invalid.";
		case INVALID_TIME:
			return "Error: The input time is invalid.";
		case INVALID_TASK_DETAILS:
			return "Error: The task detail is invalid.";
		case INVALID_LABEL:
			return "Error: The input label is invalid.";
		case NOT_FOUND:
			return "Search not found.";
		case UNDO_UNSUCCESSFUL:
			return "Error: Undo is unsuccessful.";
		case REDO_UNSUCCESSFUL:
			return "Error: Redo is unsuccessful.";
		case UNDO_SUCCESSFUL:
			return "Undo successful.";
		case REDO_SUCCESSFUL:
			return "Redo successful.";
		case UNDO_EMPTY:
			return "Error: No more things to be undone.";
		case REDO_EMPTY:
			return "Error: No more things to be redone.";
		case LOGGED_OUT_SUCCESSFULLY:
			return "Logged out successfully.";
		case LOGOUT_FAILED:
			return "Error: Logged out failed.";
		case INVALID_INCORRECT_LOGIN_INTERNET_CONNECTION:
			return "Error: Invalid your username or password/ No internet";
		case INVALID_NOINTERNET:
			return "Error: please connect to the internet.";
		case INVALID_INCORRECTLOGIN:
			return "Error: incorrect username or password.";
		case ADD_FAILED:
			return "Error: Invalid input.";
		case DELETE_FAILED:
			return "Error: delete failed.";
		case EDIT_FAILED:
			return "Error: edit failed.";
		case STAR_FAILED:
			return "Error: toggle important failed.";
		case COMPLETE_FAILED:
			return "Error: toggle completed failed.";
		case OVERDUE_FAILED:
			return "Error: overdue failed.";
		case BASESEARCH_FAILED:
			return "Error: basesearch failed.";
		case ARCHIVE_FAILED:
			return "Error: archive failed.";
		case TASK_COULD_NOT_BE_EXPORTED_FROM_ARCHIVES:
			return "Error: tasks could not be exported from archives.";
		case TASK_COULD_NOT_BE_EXPORTED_TO_ARCHIVES:
			return "Error: tasks could not be exported to archives.";
		case NO_TASK_IN_ARCHIVE:
			return "There is no task in archive.";
		case NO_TASK_TO_ARCHIVE:
			return "There is no task to archive.";
		case NO_TASK_DELETED:
			return "There is no task deleted.";
		case NO_TASK_OVERDUE:
			return "There is no task overdue.";
		case NO_TASK_COMPLETED:
			return "There is no task toggled completed.";
		case NO_TASK_STARRED:
			return "There is no task starred.";
		case NOT_FREE:
			return "not free.";
		case TASK_SPECIFIED_DOES_NOT_HAVE_BOTH_START_END_DATE_TIME:
			return "Error: The specific task does not have both start and end time.";
		case DURATION_LONGER_THAN_A_DAY:
			return "Duration is longer than a day.";
		case NO_MATCHING_ARCHIVE_FUNCTION:
			return "There is no matching archive function.";
		case USER_NOT_LOGGEDIN:
			return "You are not logged in. Pleas log in first.";
		case GOOGLE_CALENDAR_FAILED:
			return "Error: google calendar failed.";
		}
		return null;
	}
	/**
	 * 
	 * @param op
	 * @return true if the operation is feedback is an error
	 */
	public static boolean isError(OperationFeedback op) {
		switch(op) {
		case VALID:
		case UNDO_SUCCESSFUL:
		case REDO_SUCCESSFUL:
		case NO_TASK_IN_ARCHIVE:
		case NO_TASK_TO_ARCHIVE:
		case NO_TASK_DELETED:
		case NO_TASK_OVERDUE:
		case NO_TASK_COMPLETED:
		case NO_TASK_STARRED:
			return false;
		default:
			return true;
		}
	}
}

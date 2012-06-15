/**
 * forms the interface between the UI and the back end
 * No command can be processed without processing through this class.
 * Also initializes the storage
 * @author Shubhendra Agrawal 
 */
package logic;


import gui.UIController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.Timer;
import operation.*;
import data.Task;
import org.apache.log4j.Logger;
import constant.OperationFeedback;
import storagecontroller.StorageManager;

public class JIDLogic {
	
	private static Logger logger = Logger.getLogger(JIDLogic.class);
	private static Stack<Operation> undoStack = new Stack<Operation>();
	private static Stack<Operation> redoStack = new Stack<Operation>();
	private static String command;

	/**
	 * decides which type of command to be called
	 * manages the undo and redo functionalities and their respective stacks
	 * @param commandFromUser
	 * @return array of tasks depending upon the output of the userCommand
	 */
	public static Task[] executeCommand (String commandFromUser) {
		Operation op = null;
		
		
		if (command == null || command.equals("")) {
			return null;
		} else if (commandFromUser.trim().toLowerCase().equals("undo") 
				&& !undoStack.empty()) {
			Operation undoOperation = undoStack.pop();
			if (undoOperation.isUndoAble()) {
				redoStack.push(undoOperation);
			}
			logger.info("Undo being performed for:" + undoOperation.getOperationName());
			Task[] undoResult =  undoOperation.undo();
			UIController.sendOperationFeedback(undoOperation.getUndoRedoFeedback());
			return undoResult;
			
		} else if (commandFromUser.trim().toLowerCase().equals("redo") 
				&& !redoStack.empty()) {
			
			Operation redoOperation = redoStack.pop();
			if (redoOperation.isUndoAble()) {
				undoStack.push(redoOperation);
			
			}
			logger.info("Redo Being performed for:" + redoOperation.getOperationName());
			Task[] redoResult = redoOperation.redo(); 
			UIController.sendOperationFeedback(redoOperation.getUndoRedoFeedback());
			return redoResult;
		} else {
			logger.debug("inside fourth cond");
			op = Operation.getOperationObj(commandFromUser);
						
			Task[] result =  op.execute(commandFromUser);
			UIController.sendOperationFeedback(op.getOpFeedback());
			
			if (op.isUndoAble()) {
				undoStack.push(op);
			}
			
			return result;
			
			
		}
		
	}
	
	/**
	 * initializes the storage, autosave and automatic email reminder features
	 */
	public static void JIDLogic_init()
	{
		
		StorageManager.loadFile();
		StorageManager.loadArchive();
		
		startAutoSave();
		
		
		AgendaEmail newEmail = new AgendaEmail();
		newEmail.execute("agendaemail "+StorageManager.loadEmailId());
		if (newEmail.getOpFeedback() == OperationFeedback.NO_EMAIL_SPECIFIED) {
			logger.debug("inside the prompt email");
			UIController.promptEmailInput();
		}
		
	}
	
	/**
	 * saves all the files to hard disk on closing of the program
	 */
	public static void JIDLogic_close()
	{
		
		StorageManager.saveFile();
		StorageManager.saveArchive();
		
		
	}
	/**
	 * Sets the command to the command specified
	 * 
	 * @param command
	 */
	public static void setCommand(String _command) {
		command = _command;
	}

	/**
	 * 
	 * @return command entered
	 */
	public static String getCommand() {
		return command;
	}
	
	/**
	 * runs a timer to automatically save the files every 10 minutes
	 */
	public static void startAutoSave() {
		Timer timer = new Timer(10*60*1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StorageManager.saveFile();
				StorageManager.saveArchive();
			}});
		
		timer.setRepeats(true);
		timer.start();
	}
}


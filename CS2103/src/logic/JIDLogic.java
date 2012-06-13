package logic;

//import java.io.FileNotFoundException;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.Stack;

import javax.swing.Timer;

import operation.*;
//import org.apache.log4j.Logger;
import data.Task;
//import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import constant.OperationFeedback;
import gui.UIController;


import storagecontroller.StorageManager;

public class JIDLogic {
	
		private static Logger logger=Logger.getLogger(JIDLogic.class);
		//private static String command;
		
	private static Stack<Operation> undoStack= new Stack<Operation>();
	private static Stack<Operation> redoStack= new Stack<Operation>();
	
	private static String command;
	//private Logger logger = Logger.getLogger(JIDLogic.class.getName());
	
	public static Task[] executeCommand (String commandFromUser) {
		Operation op = null;
		
		logger.debug("inside execute command");
		//logger.debug(commandFromUser);
		if (command == null || command.equals("")) {
			logger.debug("inside first cond");
			return null;
		} else if (commandFromUser.trim().toLowerCase().equals("undo") && !undoStack.empty()) {
			logger.debug("inside third cond");
			Operation undoOperation = undoStack.pop();
			if (undoOperation.isUndoAble()) {
				redoStack.push(undoOperation);
				logger.debug("isredoable");
			}
			logger.debug("popped last action from stack:"+undoOperation.getOperationName());
			return undoOperation.undo();
			
		} else if (commandFromUser.trim().toLowerCase().equals("redo") && !redoStack.empty()) {
			logger.debug("inside redo cond");
			Operation redoOperation = redoStack.pop();
			if (redoOperation.isUndoAble()) {
				undoStack.push(redoOperation);
				logger.debug("isundoable");
			}
			logger.debug("popped last action from stack:"+redoOperation.getOperationName());
			return redoOperation.redo(); 
		} else {
			logger.debug("inside fourth cond");
			op = Operation.getOperationObj(commandFromUser);
						
			Task[] result=  op.execute(commandFromUser);
			UIController.sendOperationFeedback(op.getOpFeedback());
			logger.debug("Operation feedback:"+op.getOpFeedback());
			logger.debug("THE OPERATION IS UNDOABLE:"+op.isUndoAble());
			if (op.isUndoAble()) {
				undoStack.push(op);
				logger.debug("isundoable");
			}
			//UIController.sendOperationFeedback(op.getOpFeedback());
			//UIController.showTopPopUpMsg(op.getErrorMessage());
			return result;
			
			
		}
		
		}
		
	public static void JIDLogic_init()
	{
		
		StorageManager.loadFile();
		StorageManager.loadArchive();/*
		String email="";
		if (StorageManager.loadEmailId()==""){
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				email=reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		else 
			email= StorageManager.loadEmailId();*/
		startAutoSave();
		
		
		AgendaEmail newEmail= new AgendaEmail();
		Task result[] = newEmail.execute("agendaemail "+StorageManager.loadEmailId());
		if (newEmail.getOpFeedback()==OperationFeedback.NO_EMAIL_SPECIFIED)
		{
			logger.debug("inside the prompt email");
			UIController.promptEmailInput();
		}
		
	}
	
	public static void JIDLogic_close()
	{
		
		StorageManager.saveFile();
		StorageManager.saveArchive();
		
		
	}

	/**
	 * Sets the view to the specified value
	 * 
	 * @param view
	 */
	
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
	
	public static void startAutoSave() {
		Timer timer = new Timer(30*60*1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StorageManager.saveFile();
				StorageManager.saveArchive();
			}});
		
		timer.setRepeats(true);
		timer.start();
	}
}


package gui;

import gui.mainWindow.MainJFrame;
import gui.mainWindow.extended.ExpandComponent;
import gui.mainWindow.extended.HelpFrame;
import gui.mainWindow.extended.TopPopUp;
import gui.reminder.Reminder;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import org.apache.log4j.Logger;

import constant.OperationFeedback;
import data.Task;

import logic.JIDLogic;

public class UIController {

	private static Logger logger=Logger.getLogger(UIController.class);
	
	public static MainJFrame mainJFrame;
	Reminder reminder;
	static JotItDownTray JIDtray;
	static OperationFeedback operationFeedback = OperationFeedback.VALID;
	static boolean loginOn = false;
	
	/**
	 * constructor
	 */
	public UIController() {
		TopPopUp.createTopPopUp();
		ExpandComponent.initialize();
		HelpFrame.initialize();
		mainJFrame = new MainJFrame();
		HelpFrame.setPosition();
		
		Timer timer = new Timer(100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JIDtray = new JotItDownTray();
				Reminder reminder = new Reminder(JIDtray.getTray());
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	
	/**
	 * where the program starts
	 * @param args
	 */
	public static void main(String[] args) {
		JIDLogic.JIDLogic_init();
		
		new UIController();
	}
	
	/**
	 * getting text from clipboard
	 * @return text
	 */
	public static String getClipboard() {
	    Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

	    try {
	        if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	            String text = (String)t.getTransferData(DataFlavor.stringFlavor);
	            return text;
	        }
	    } catch (UnsupportedFlavorException e) {
	    } catch (IOException e) {
	    }
	    return null;
	}
	
	/**
	 * for showing pop up message on the top or mainJFrame
	 * @param str shown message
	 */
	public static void showTopPopUpMsg(String str) {
		mainJFrame.showPopup(str);
	}
	
	/**
	 * show message at tray
	 * @param caption header
	 * @param text message
	 */
	public static void showTrayMsg(String caption, String text) {
		JIDtray.showText(caption, text);
	}
	
	/**
	 * updating GUI
	 */
	public static void refresh() {
		ExpandComponent.updateJTable();
		Reminder.update();
	}
	
	/**
	 * check whether the window is visible or not
	 * @return true if window is visible
	 */
	public static boolean isWindowVisible() {
		return mainJFrame.isVisible();
	}
	
	/**
	 * show the main Window
	 */
	public static void showWindow() {
		mainJFrame.showFrame();
	}
	
	/**
	 * hide the main Window
	 */
	public static void hideWindow() {
		mainJFrame.hideFrame();
	}
	
	/**
	 * check whether the frame is expanded or not
	 * @return true if the frame is expanded
	 */
	public static boolean isFrameExpand() {
		return mainJFrame.isExpand();
	}
	
	/**
	 * expand the frame
	 */
	public static void expandFrame() {
		mainJFrame.expandFrame();
	}
	
	/**
	 * contract the frame
	 */
	public static void contractFrame() {
		mainJFrame.contractFrame();
	}
	
	/**
	 * log in to google calendar
	 * @param username username for Google login
	 * @param password password for Google login
	 */
	public static void logInToGCalendar(String username, char[] password) {
		JIDLogic.setCommand("login");
		String execmd = "login " + username + " ";
		for(int i=0; i<password.length; i++)
			execmd += password[i];
		System.out.println(execmd);
		JIDLogic.executeCommand(execmd);
		if(operationFeedback == OperationFeedback.VALID)
			UIController.showTopPopUpMsg("Log in successfully!");
		UIController.showFeedbackDisplay();
		UIController.refresh();
	}
	
	/**
	 * receive feedback after each operation
	 * @param newOPFeedback
	 */
	public static void sendOperationFeedback(OperationFeedback newOPFeedback) {
		operationFeedback = newOPFeedback;
	}
	
	//UI.sendOperationFeedback(OperationFeedback.INVALID_);
	
	/**
	 * get operation feedback
	 * @return operation feedback
	 */
	public static OperationFeedback getOperationFeedback() {
		return operationFeedback;
	}
	

	/**
	 * show display when there is a feedback from the operation
	 */
	public static void showFeedbackDisplay() {
		showFeedbackDisplay(null);
	}
	
	/**
	 * show display when there is a feedback from the operation
	 */
	public static void showFeedbackDisplay(Task[] tasks) {
		String displayText;
		
		//some tasks does not need operation feedback. i.e. help
		if(operationFeedback == null)
			return; 
		
		switch(operationFeedback) {
		case VALID:
			if(tasks == null)
				logger.warn("UIController.showFeedbackDisplay : " +
						"Valid feedback should not have null tasks");
			if(tasks.length == 1) {
				displayText = tasks[0].getName() + " " 
							  +	STATE.getEndedString(true);
				if(displayText.length() > 50) {
					displayText = tasks[0].getName().substring(0, 25) + "... " + STATE.getEndedString(true);
				}
			}
			else
				displayText = tasks.length + " tasks " + STATE.getEndedString(false);
			break;
		default:
			displayText = OperationFeedback.getString(getOperationFeedback());
			break;
		}
		
		if(mainJFrame.isVisible())
			mainJFrame.showPopup(displayText);
		else 
			showTrayMsg("Jot It Down", displayText);
	}
	
	/**
	 * 
	 * @return true if login dialog is opened
	 */
	public static boolean isLoginOn() {
		return loginOn;
	}
	
	/**
	 * 
	 * @param status of log in dialog
	 */
	public static void setLoginOn(boolean status) {
		loginOn = status;
	}
}
package gui;

import gui.mainWindow.MainJFrame;
import gui.mainWindow.extended.ExpandComponent;
import gui.mainWindow.extended.HelpFrame;
import gui.mainWindow.extended.MailDialog;
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

/**
 * for controlling UI and initializing the program.
 * @author Ramon
 *
 */
public class UIController {
	private static Logger	 logger=Logger.getLogger(UIController.class);

	public static MainJFrame mainJFrame;
	Reminder reminder;
	static JotItDownTray JIDtray;
	static OperationFeedback operationFeedback = OperationFeedback.VALID;
	static boolean loginOn = false;
	
	/**
	 * constructor
	 */
	public UIController() {
		initializeMainWindowComponent();		
		initializeTray();
	}


	/**
	 * initialize main window components which consist of the pop up,
	 * the table, and the help frame.	
	 */
	private void initializeMainWindowComponent() {
		TopPopUp.createTopPopUp();
		ExpandComponent.initialize();
		HelpFrame.initialize();
		mainJFrame = new MainJFrame();
		HelpFrame.setPosition();
		//mainJFrame.showFrame();
	}

	/**
	 * Initialize the tray after waiting for some time,
	 * so that the mainJFrame is successfully created.
	 * <pre>
	 * mainJFrame has successfully created.
	 * </pre>
	 */
	private void initializeTray() {
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
		new UIController();
		JIDLogic.JIDLogic_init();
		UIController.refresh();
		mainJFrame.showFrame();
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
		
		//clear edit
		JIDLogic.setCommand("canceledit");
		JIDLogic.executeCommand("canceledit");
		
		if(STATE.getState() != STATE.SEARCH
				&&STATE.getState() != STATE.OVERDUE) {
			logger.debug("refresh: in " + STATE.getState()+ ": update JTable");
			ExpandComponent.updateJTable();
		}
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
	 * receive feedback after each operation
	 * @param newOPFeedback
	 */
	public static void sendOperationFeedback(OperationFeedback newOPFeedback) {
		operationFeedback = newOPFeedback;
	}
	
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
		if(operationFeedback == null) {
			logger.warn(STATE.getState() + ": no operationfeedback sent!");
			UIController.refresh();
			return;
		}
		
		switch(	operationFeedback) {
		case VALID:
			if(tasks == null) {
				displayText = STATE.getFeedbackText();
			}
			else if(tasks.length == 1) {
				displayText = tasks[0].getName() + " " 
							  +	STATE.getEndedString(true);
				if(displayText.length() > 50) {
					displayText = tasks[0].getName().substring(0, 40) + "... " + STATE.getEndedString(true);
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
		
		UIController.refresh();
		UIController.sendOperationFeedback(null);
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
	
	/** promt email input box
	 * 
	 */
	public static void promptEmailInput() {
		new MailDialog(null, true);
	}
	
	/** clear the command line
	 * 
	 */
	public static void clearCommandLine() {
		MainJFrame.clearCommandLine();
	}
}
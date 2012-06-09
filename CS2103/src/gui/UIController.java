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

import constant.OperationFeedback;

import logic.JIDLogic;

public class UIController {
	public static MainJFrame mainJFrame;
	Reminder reminder;
	static JotItDownTray JIDtray;
	static OperationFeedback operationFeedback = OperationFeedback.VALID;
	
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

				JIDtray = new JotItDownTray(mainJFrame);
				Reminder reminder = new Reminder(JIDtray.getTray());
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	
	public static void main(String[] args) {
		JIDLogic.JIDLogic_init();
		
		new UIController();
	}
	
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
	
	public static void showTopPopUpMsg(String str) {
		mainJFrame.showPopup(str);
	}
	
	public static void showTrayMsg(String caption, String text) {
		JIDtray.showText(caption, text);
	}
	
	public static void refresh() {
		ExpandComponent.updateJTable();
		Reminder.update();
	}
	
	public static boolean isWindowVisible() {
		return mainJFrame.isVisible();
	}
	
	public static void showWindow() {
		mainJFrame.showFrame();
	}
	
	public static void hideWindow() {
		mainJFrame.hideFrame();
	}
	
	public static boolean isFrameExpand() {
		return mainJFrame.isExpand();
	}
	
	public static void expandFrame() {
		mainJFrame.expandFrame();
	}
	
	public static void contractFrame() {
		mainJFrame.contractFrame();
	}
	
	public static void logInToGCalendar(String username, char[] password) {
		JIDLogic.setCommand("login");
		String execmd = "login " + username + " ";
		for(int i=0; i<password.length; i++)
			execmd += password[i];
		System.out.println(execmd);
		JIDLogic.executeCommand(execmd);
		UIController.refresh();
		UIController.showInvalidDisplay();
	}
	
	/**
	 * receive feedback after each operation
	 * @param newOPFeedback
	 */
	public static void sendOperationFeedback(OperationFeedback newOPFeedback) {
		operationFeedback = newOPFeedback;
	}
	
	//UI.sendOperationFeedback(OperationFeedback.INVALID_);
	
	public static OperationFeedback getOperationFeedback() {
		return operationFeedback;
	}
	
	public static void showInvalidDisplay() {
		if(mainJFrame.isVisible())
			switch(operationFeedback) {
			case INVALID_DATE:
				mainJFrame.showPopup("incorrect date input");
			case INVALID_TIME:
				mainJFrame.showPopup("incorret time input");
			case INVALID_TASK_DETAILS:
				mainJFrame.showPopup("incorrect task details");
			case INVALID_LABEL:
				mainJFrame.showPopup("incorrect label");
			case INVALID_INCORRECTLOGIN:
				mainJFrame.showPopup("wrong username or password");
			case INVALID_NOINTERNET:
				mainJFrame.showPopup("no internet connection");
			case NOT_FOUND:
				mainJFrame.showPopup("search not found!");
			}
		else {
			switch(operationFeedback) {
			case INVALID_DATE:
				JIDtray.showText("Jot It Down!", "incorrect date input");
			case INVALID_TIME:
				JIDtray.showText("Jot It Down!", "incorret time input");
			case INVALID_TASK_DETAILS:
				JIDtray.showText("Jot It Down!", "incorrect task details");
			case INVALID_LABEL:
				JIDtray.showText("Jot It Down!", "incorrect label");
			case INVALID_INCORRECTLOGIN:
				JIDtray.showText("Jot It Down!", "wrong username or password");
			case INVALID_NOINTERNET:
				JIDtray.showText("Jot It Down!", "no internet connection");
			case NOT_FOUND:
				JIDtray.showText("Jot It Down!", "search not found!");
			}
		}
	}
}

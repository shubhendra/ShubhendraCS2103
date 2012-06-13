package gui.mainWindow;

import gui.STATE;
import gui.UIController;
import gui.mainWindow.extended.ExpandComponent;
import gui.mainWindow.extended.HelpFrame;
import gui.mainWindow.extended.LogInDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

import logic.JIDLogic;
import data.Task;

/**
 * Action class for using with key binding
 * 
 * @author Ramon
 *
 */
public class Action {
	
	private static Logger logger=Logger.getLogger(JIDLogic.class);
	
	/**
	 * exit the program
	 * @author Ramon
	 */
	static class ExitAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.EXIT);
			JIDLogic.JIDLogic_close();
			System.exit(0);
		}
	}
	
	/**
	 * undo the most recent task
	 * @author Ramon
	 *
	 */
    static class UndoAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.UNDO);
        	logger.debug("*****EXECMD: UNDO*******");
        	JIDLogic.setCommand("UNDO");
        	Task[] task = JIDLogic.executeCommand("UNDO");
        	UIController.showFeedbackDisplay(task);
			STATE.setState(STATE.NULL);
        }
    }
	
    /**
     * Delete task
     * @author Ramon
     *
     */
    static class DeleteAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {     
			STATE.setState(STATE.DELETE); 
        	
        	Task[] taskList = ExpandComponent.getSeletedTask();
        	
        	if(taskList.length == 0)
        		return;
	        else {
	        	String exeCmd = "DELETE ";
	        	for(Task t: taskList) {
	        		exeCmd += t.getTaskId() + " ";
	        	}
	        	
	        	JIDLogic.setCommand("DELETE");
	        	Task[] result = JIDLogic.executeCommand(exeCmd);
	        	
		        UIController.showFeedbackDisplay(result);
	        }
        	
			STATE.setState(STATE.NULL);
        }
    }
    
    /**
     * toggle completion of tasks
     * @author Ramon
     *
     */
    static class CompletedAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.COMPLETED);
			
        	Task[] taskList = ExpandComponent.getSeletedTask();
        	
        	if(taskList.length == 0)
        		return;
	        else {
	        	String exeCmd = "COMPLETED ";
	        	for(Task t: taskList) {
	        		exeCmd += t.getTaskId() + " ";
	        	}
	        	
	        	JIDLogic.setCommand("COMPLETED");
	        	Task[] result = JIDLogic.executeCommand(exeCmd);

		        UIController.showFeedbackDisplay(result);
	        	
	        	UIController.refresh();
	        }

			STATE.setState(STATE.COMPLETED);
        }
    }
    
    /**
     * toggle important of tasks
     * @author Ramon
     *
     */
    static class ImportantAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.IMPORTANT);
			
        	Task[] taskList = ExpandComponent.getSeletedTask();
        	
        	if(taskList.length == 0)
        		return;
	        else {
	        	String exeCmd = "IMPORTANT ";
	        	for(Task t: taskList) {
	        		exeCmd += t.getTaskId() + " ";
	  
	        	}
	        	
	        	logger.debug(taskList[0].getName());
	        	logger.debug("******" + exeCmd);
	        	
	        	JIDLogic.setCommand("IMPORTANT");
	        	Task[] result = JIDLogic.executeCommand(exeCmd);
	        	

		        UIController.showFeedbackDisplay(result);
	        	UIController.refresh();
	        }
        	
			STATE.setState(STATE.NULL);
        }
    }
    
    /**
     * check overdue
     * @author Ramon
     *
     */
    static class OverdueAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.OVERDUE);
			
    		JIDLogic.setCommand("overdue");
    		logger.debug("*********exeCmd(inside Action): Overdue");
    		Task[] task = JIDLogic.executeCommand("OVERDUE");

	        UIController.showFeedbackDisplay(task);
	        

			STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * redo operations
     * @author Ramon
     *
     */
    static class RedoAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.REDO);
    		JIDLogic.setCommand("redo");
    		logger.debug("******exeCmd(inside Action: Redo");
    		Task[] task = JIDLogic.executeCommand("redo");

	        UIController.showFeedbackDisplay(task);
			STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * list all tasks
     * @author Ramon
     *
     */
    static class ListAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.LIST);
    		UIController.refresh();
    		UIController.expandFrame();
    		UIController.sendOperationFeedback(null);

			STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * expand the mainJFrame
     * @author Ramon
     *
     */
    static class ExpandAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			//STATE.setState(STATE.EXPAND);
    		if(UIController.isFrameExpand())
    			UIController.contractFrame();
    		else
    			UIController.expandFrame();
    		UIController.sendOperationFeedback(null);
			STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * toggle help
     * @author Ramon
     *
     */
    static class HelpAction extends AbstractAction {
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.HELP);
    		HelpFrame.toggleShown();
    		UIController.sendOperationFeedback(null);
			STATE.setState(STATE.NULL);
    	}
    }

    /**
     * login to google Calendar
     * @author Ramon
     *
     */
    static class GCalendarAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.LOGIN);
    		new LogInDialog(UIController.mainJFrame
    				, UIController.mainJFrame.getLocation().x + 60
    				, UIController.mainJFrame.getLocation().y - 120
    				);
    	}
    }
    
    /**
     * logout from google calendar
     */
    static class GCalendarOutAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
			STATE.setState(STATE.LOGOUT);
    		JIDLogic.setCommand("logout");
    		Task[] task = JIDLogic.executeCommand("logout");

	        UIController.showFeedbackDisplay(task);
			STATE.setState(STATE.NULL);
    	}
    }
}

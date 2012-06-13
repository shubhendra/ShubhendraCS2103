package gui;

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
	public static class ExitAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
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
    public static class UndoAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.UNDO);
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
    public static class DeleteAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {     
    		logger.debug("HOTKEY: " + this.getClass().toString());
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
    		UIController.clearCommandLine();
        }
    }
    
    /**
     * toggle completion of tasks
     * @author Ramon
     *
     */
    public static class CompletedAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
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
    		UIController.clearCommandLine();
        }
    }
    
    /**
     * toggle important of tasks
     * @author Ramon
     *
     */
    public static class ImportantAction extends AbstractAction {
    	Task[] task;
        @Override
        public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
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
    		UIController.clearCommandLine();
        }
    }
    
    /**
     * check overdue
     * @author Ramon
     *
     */
    public static class OverdueAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.OVERDUE);
			
    		JIDLogic.setCommand("overdue");
    		logger.debug("*********exeCmd(inside Action): Overdue");
    		Task[] task = JIDLogic.executeCommand("OVERDUE");

	        UIController.showFeedbackDisplay(task);
	        ExpandComponent.updateJTable(task);
	        UIController.expandFrame();
    		
	        STATE.setState(STATE.NULL);
    		UIController.clearCommandLine();
    	}
    }
    
    /**
     * redo operations
     * @author Ramon
     *
     */
    public static class RedoAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
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
    public static class ListAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.LIST);
    		UIController.refresh();
    		UIController.expandFrame();
    		UIController.sendOperationFeedback(null);

			STATE.setState(STATE.NULL);
    		UIController.clearCommandLine();
    	}
    }
    
    /**
     * expand the mainJFrame
     * @author Ramon
     *
     */
    public static class ExpandAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			//STATE.setState(STATE.EXPAND);
    		if(UIController.isFrameExpand())
    			UIController.contractFrame();
    		else
    			UIController.expandFrame();
    		UIController.sendOperationFeedback(null);
			STATE.setState(STATE.NULL);
    		UIController.clearCommandLine();
    	}
    }
    
    /**
     * toggle help
     * @author Ramon
     *
     */
    public static class HelpAction extends AbstractAction {
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.HELP);
    		HelpFrame.toggleShown();
    		UIController.sendOperationFeedback(null);
			STATE.setState(STATE.NULL);
    		UIController.clearCommandLine();
    	}
    }

    /**
     * login to google Calendar
     * @author Ramon
     *
     */
    public static class GCalendarAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.LOGIN);
    		new LogInDialog(UIController.mainJFrame
    				, UIController.mainJFrame.getLocation().x + 60
    				, UIController.mainJFrame.getLocation().y - 120
    				);
    		UIController.clearCommandLine();
    	}
    }
    
    /**
     * logout from google calendar
     */
    public static class GCalendarOutAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
			STATE.setState(STATE.LOGOUT);
    		JIDLogic.setCommand("logout");
    		Task[] task = JIDLogic.executeCommand("logout");

    		UIController.clearCommandLine();
	        UIController.showFeedbackDisplay(task);
			STATE.setState(STATE.NULL);
    	}
    }	
    
    /**
     * Sync data with google calendar
     */
    public static class GCalendarSyncAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.SYNCGCAL);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
    
    public static class GCalendarImportAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.IMPORTGCAL);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
    
    public static class GCalendarExportAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.EXPORTGCAL);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * move completed tasks to archive
     * @author Ramon
     *
     */
    public static class ArchiveAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.ARCHIVE);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
    /**
     * import tasks from archive
     * @author Ramon
     *
     */
    public static class ImportArchiveAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.IMPORTARCHIVE);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
    
    /**
     * clear archive
     * @author Ramon
     *
     */
    public static class ClearArchiveAction extends AbstractAction {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		logger.debug("HOTKEY: " + this.getClass().toString());
    		STATE.setState(STATE.CLEARARCHIVE);
    		JIDLogic.setCommand(STATE.getCommand());
    		Task[] task = JIDLogic.executeCommand(STATE.getCommand());
    		
    		UIController.clearCommandLine();
    		UIController.showFeedbackDisplay(task);
    		STATE.setState(STATE.NULL);
    	}
    }
}

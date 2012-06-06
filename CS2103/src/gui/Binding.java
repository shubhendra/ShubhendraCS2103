package gui;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import logic.JIDLogic;
import data.Task;

import org.apache.log4j.*;

public class Binding {
	
	private static Logger logger=Logger.getLogger(JIDLogic.class);
	
	InputMap inputMap;
	ActionMap actionMap;
	
	Binding(InputMap inputMap, ActionMap actionMap) {
		this.inputMap = inputMap;
		this.actionMap = actionMap;
		addKeyBinding();
	}
	
	private void addKeyBinding() {
	       KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
	        inputMap.put(key, "undo");
	        actionMap.put("undo", new UndoAction());


	        key = KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK);
	        inputMap.put(key, "completed");
	        actionMap.put("completed", new CompletedAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
	        inputMap.put(key, "delete");
	        actionMap.put("delete", new DeleteAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
	        inputMap.put(key, "important");
	        actionMap.put("important", new ImportantAction());
	}
	
    class UndoAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.debug("*****EXECMD: UNDO*******");
        	JIDLogic.setCommand("UNDO");
        	Task[] task = JIDLogic.executeCommand("UNDO");
        	if(task == null)
        		MainJFrame.showPopup("UNDO unsuccessfully!");
        	else {
        		MainJFrame.showPopup("UNDO: "+task[0].getName());
            	ExpandJPanel.updateJTable();
        	}
        }
    }
	
    class DeleteAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
        	Task[] taskList = ExpandJPanel.getTasks();
        	
        	JIDLogic.setCommand("DELETE");
        	Task[] task = JIDLogic.executeCommand("DELETE " 
        			+ taskList[ExpandJPanel.jTable1.getSelectedRow()].getTaskId());
        	
        	if(task == null)
        		MainJFrame.showPopup("DELETE unsuccessfully!");
        	else {
        		MainJFrame.showPopup("DELETE: "+task[0].getName());
            	ExpandJPanel.updateJTable();
        	}        	
        }
    }
    
    class CompletedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
        	Task[] taskList = ExpandJPanel.getTasks();
        	
        	JIDLogic.setCommand("COMPLETED");
        	logger.debug("COMPLETED "
        			+ taskList[ExpandJPanel.jTable1.getSelectedRow()].getTaskId());
        	Task[] task = JIDLogic.executeCommand("COMPLETED " 
        			+ taskList[ExpandJPanel.jTable1.getSelectedRow()].getTaskId());
        	
        	if(task == null)
        		MainJFrame.showPopup("COMPLETED unsuccessfully!");
        	else {
        		MainJFrame.showPopup("COMPLETED: "+task[0].getName());
            	ExpandJPanel.updateJTable();
        	}        	
        }
    }
    
    class ImportantAction extends AbstractAction {
    	@Override
        public void actionPerformed(ActionEvent e) {
        	Task[] taskList = ExpandJPanel.getTasks();
        	
        	JIDLogic.setCommand("IMPORTANT");
        	logger.debug("IMPORTANT "
        			+ taskList[ExpandJPanel.jTable1.getSelectedRow()].getTaskId());
        	Task[] task = JIDLogic.executeCommand("IMPORTANT " 
        			+ taskList[ExpandJPanel.jTable1.getSelectedRow()].getTaskId());
        		
        	if(task == null)
        		MainJFrame.showPopup("IMPORTANT: unsuccessfully!");
        	else {
        		MainJFrame.showPopup("IMPORTANT: "+task[0].getName());
            	ExpandJPanel.updateJTable();
        	}        	
        }
    }
}

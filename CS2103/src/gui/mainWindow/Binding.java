package gui.mainWindow;


import gui.Action;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;


/**
 * for making hotkey
 * @author Ramon
 *
 */
public class Binding {
	
	
	InputMap inputMap;
	ActionMap actionMap;
	JFrame jFrame;
	
	/**
	 * constructor
	 * @param jframe the focused window
	 * @param inputMap inputMap from that window
	 * @param actionMap actionMap from that window
	 */
	Binding(JFrame jframe, InputMap inputMap, ActionMap actionMap) {
		this.jFrame = jFrame;
		this.inputMap = inputMap;
		this.actionMap = actionMap;
		addKeyBinding();
	}
	
	/**
	 * mapping hot keys
	 */
	private void addKeyBinding() {
	       KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
	        inputMap.put(key, "undo");
	        actionMap.put("undo", new Action.UndoAction());


	        key = KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK);
	        inputMap.put(key, "completed");
	        actionMap.put("completed", new Action.CompletedAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
	        inputMap.put(key, "delete");
	        actionMap.put("delete", new Action.DeleteAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
	        inputMap.put(key, "delete");
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
	        inputMap.put(key, "important");
	        actionMap.put("important", new Action.ImportantAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK);
	        inputMap.put(key, "exit");
	        actionMap.put("exit", new Action.ExitAction());
	
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK);
	        inputMap.put(key, "expand");
	        actionMap.put("expand", new Action.ExpandAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
	        inputMap.put(key, "redo");
	        actionMap.put("redo", new Action.RedoAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK);
	        inputMap.put(key, "list");
	        actionMap.put("list", new Action.ListAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK);
	        inputMap.put(key, "overdue");
	        actionMap.put("overdue", new Action.OverdueAction());
	        

	        key = KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.CTRL_MASK);
	        inputMap.put(key, "login");
	        actionMap.put("login", new Action.GCalendarAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
	        inputMap.put(key, "logout");
	        actionMap.put("logout", new Action.GCalendarOutAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
	        inputMap.put(key, "help");
	        actionMap.put("help", new Action.HelpAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.ALT_MASK);
	        inputMap.put(key, "syncGCal");
	        actionMap.put("syncGCal", new Action.GCalendarSyncAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.ALT_MASK);
	        inputMap.put(key, "importGCal");
	        actionMap.put("importGCal", new Action.GCalendarImportAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.ALT_MASK);
	        inputMap.put(key, "exportGCal");
	        actionMap.put("exportGCal", new Action.GCalendarExportAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.ALT_MASK);
	        inputMap.put(key, "archive");
	        actionMap.put("archive", new Action.ArchiveAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK);
	        inputMap.put(key, "importArchive");
	        actionMap.put("importArchive", new Action.ImportArchiveAction());
	        
	        key = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK);
	        inputMap.put(key, "clearArchive");
	        actionMap.put("clearArchive", new Action.ClearArchiveAction());
	        
	        
	}
	

}

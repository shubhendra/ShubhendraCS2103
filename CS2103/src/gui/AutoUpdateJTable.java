package gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import logic.JIDLogic;

import data.DateTime;
import data.Task;
import data.TaskHashMap;

public class AutoUpdateJTable {
	private JTable jTable;
	private DefaultTableModel model;
    private Vector<String> listLabel = new Vector<String>();
    private Task[] tasks;
	
	AutoUpdateJTable(final JTable jTable){
		this.jTable = jTable;
		model = (DefaultTableModel) this.jTable.getModel();
		updateJTable();

	}
	
	private void setAppearance() {
		jTable.setRowHeight(60);
		while(model.getRowCount()>0)
			model.removeRow(0);
		jTable.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer());
		for(int i=0; i<listLabel.size(); i++) {
			model.addRow(new Object[]{listLabel.get(i)});
		}
	}
	

    private void makeJLabel(Task task) {
    	String str;
    	String completedFont = "<font color = \"#BBBBBB\">";
    	
    	str = "<HTML><b>";
    	if(task.getCompleted()) {
    		str+=completedFont;
    		System.out.println("completed task");
    	}
    	else if(task.getImportant()) {
    		str += "<font color=\"red\">";
    	}
    	str += task.getName();
    	str += "<br/></b>";
    	if(task.getCompleted())
    		str+=completedFont;
    	if(task.getDescription()!= null)
    		str+=task.getDescription();
    	if(task.getStartDateTime()!= null) {
    		str+="<br/><i>start: </i>"+task.getStartDateTime().presentableToString();
    	}
    	if(task.getEndDateTime()!=null) {
    		str+="<i>                  end: </i>"+task.getEndDateTime().presentableToString();
    	}
    	str += "</HTML>";
    	
    	listLabel.add(str);
    }
    
    private void makeAllJLabel(Task[] tasks) {
    	
    	for(int i=0; i<tasks.length; i++) {
    		makeJLabel(tasks[i]);
    	}
    }
    
    
    public void updateJTable() {

    	Timer timer = new Timer(100, new ActionListener(){

    	  		@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
		    	listLabel = new Vector<String>();
		    	JIDLogic.setCommand("find");
		    	tasks = JIDLogic.executeCommand("find *.*");
		    	makeAllJLabel(tasks);
		    	setAppearance();
		    	}
			});
    	timer.setRepeats(false);
    	timer.start();
    	
    }
    
    public void updateJTable(final Task[] tasks) {
    	Timer timer = new Timer(100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

		    	if(tasks==null)
		    		while(model.getRowCount()>0)
		    			model.removeRow(0);
		    	else {
			    	listLabel = new Vector<String>();
			    	makeAllJLabel(tasks);
			    	setAppearance();
		    	}
			}});
    	timer.setRepeats(false);
    	timer.start();
    }
    
    class MyRenderer extends DefaultTableCellRenderer {
    	  /*
    	   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
    	   */
    	  public Component getTableCellRendererComponent(JTable table, Object value,
    	                                                 boolean isSelected, boolean hasFocus, 
    	                                                 int row, int column) {
    		JLabel label = new JLabel(value.toString());
    		if(isSelected){
    			label.setBackground(table.getSelectionBackground());
    			label.setForeground(table.getSelectionForeground());
    		}
    		else {
    			label.setBackground(table.getBackground());
    			label.setForeground(table.getForeground());
    		}
    		label.setOpaque(true);
    		return label;   
    	  }
    }
    	  
    public Task[] getTasks() {
    	return tasks;
    }
    	  
    class MyDefaultTableModel extends DefaultTableModel {  
    	public MyDefaultTableModel() {  
    		super();  
    	}  
    		  
    	public boolean isCellEditable(int row, int col) {  
    		return false;  
    	}    
    }
}

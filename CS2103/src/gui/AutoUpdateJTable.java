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

import data.DateTime;
import data.Task;
import data.TaskHashMap;

public class AutoUpdateJTable {
	private JTable jTable;
	private DefaultTableModel model;
    private Vector<String> listLabel = new Vector<String>();
	
	AutoUpdateJTable(final JTable jTable){
		this.jTable = jTable;
		model = (DefaultTableModel) this.jTable.getModel();
		/*
		SwingWorker<JTable, Void> worker = new SwingWorker<JTable, Void>() {

			@Override
			protected JTable doInBackground() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("enter swing");
				Timer timer = new Timer(2000, new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//updateJTable();
						makeJLabel(new Task());
						setAppearance();
						System.out.println(listLabel.get(0).toString());
					}
					
				});
				timer.start();
				timer.setRepeats(true);
				return null;
			}
		};
		*/

		Timer timer = new Timer(2000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateJTable();
				//System.out.println(listLabel.get(0).toString());
			}
			
		});
		timer.start();
		timer.setRepeats(true);
		
	}
	
	private void setAppearance() {
		jTable.setRowHeight(60);
		while(model.getRowCount()>0)
			model.removeRow(0);
		jTable.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer());
		for(int i=0; i<listLabel.size(); i++) {
			model.addRow(new Object[]{listLabel.get(i)});
		}
		System.out.println(listLabel.toString());
	}
	

    private void makeJLabel(Task task) {
    	task = new Task("test");
    	
    	String str;
    	
    	str = "<HTML><b>";
    	if(task.getImportant())
    		str+= "<color = \"red\">";
    	str += task.getName();
    	str += "</br></b>";
    	if(task.getDescription()!= null)
    		str+=task.getDescription();
    	if(task.getStartDateTime()!= null) {
    		str+="<i>start</i>"+task.getStartDateTime().formattedToString();
    	}
    	if(task.getEndDateTime()!=null) {
    		str+="<i>end</i>"+task.getEndDateTime().formattedToString();
    	}
    	str += "</HTML>";
    	listLabel.add(str);
    }
    
    private void makeAllJLabel(TaskHashMap taskHashMap) {
    	int length = 0;
    	for(int i=0; i<length; i++) {
    		makeJLabel(new Task());
    	}
    }
    
    private void updateJTable() {
    	//retrieve TaskHashMap
    	listLabel = new Vector<String>();
    	TaskHashMap taskHashMap = new TaskHashMap();
    	makeAllJLabel(taskHashMap);
    	setAppearance();
    }
    
    class MyRenderer extends DefaultTableCellRenderer {

    	  /*
    	   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
    	   */
    	  public Component getTableCellRendererComponent(JTable table, Object value,
    	                                                 boolean isSelected, boolean hasFocus, 
    	                                                 int row, int column) {
    		JLabel label = new JLabel(value.toString());
    		return label;   
    	  }
    }

}

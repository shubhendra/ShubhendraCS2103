/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import data.*;
/**
 *
 * @author Ramon
 */
public class ExpandJPanel extends javax.swing.JPanel {

	AutoUpdateJTable autoJTable;
	
    /**
     * Creates new form ExpandJPanel
     */
    public ExpandJPanel() {
        initComponents();
        autoJTable = new AutoUpdateJTable(jTable1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(378, 300));
        setRequestFocusEnabled(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            		null
            },
            new String [] {
                "Title 1"
            }
        ));
        jTable1.setTableHeader(null);
        //jTable1.setCellEditor(jTable1.getCellEditor());
        jTable1.setColumnSelectionAllowed(false);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setEnabled(false);
        jTable1.setFocusable(false);
        jTable1.setMaximumSize(new java.awt.Dimension(370, 30));
        jTable1.setMinimumSize(new java.awt.Dimension(370, 370));
        jTable1.setRowSelectionAllowed(false);
        jTable1.setRowSorter(null);
        
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>
    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    
    // End of variables declaration
    public void updateJTable(Task[] tasks) {
    	autoJTable.updateJTable(tasks);
    }
    
    public void updateJTable() {
    	autoJTable.updateJTable();
    }
}

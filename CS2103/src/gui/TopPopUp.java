/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JFrame;


/**
 *
 * @author Ramon
 */
public class TopPopUp extends JFrame {

    /**
     * Creates new form TopPopUp
     */
    public static void createTopPopUp() {
    	jFrame = new JFrame();
    	jFrame.setUndecorated(true);
    	initComponents();
    	jFrame.setFocusableWindowState(false);
    	//showBox();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private static void initComponents() {
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setEditable(false);
        jTextField1.setText("jTextField1");

        jButton1.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jFrame.getContentPane());
        jFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1))
        );

        
        jButton1.setText("");
        jButton1.setIcon( Resource.exitImg);
        jButton1.addActionListener( new 
        	ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(SHOW) {
						SHOW = !SHOW;
						jFrame.setVisible(false);
					}
				}
        	
        });
        

        jFrame.setIconImage((Resource.bigLogo).getImage());
        jFrame.pack();
    }// </editor-fold>

    // Variables declaration - do not modify
    public static javax.swing.JFrame jFrame;
    private static javax.swing.JTextField jTextField1;
    private static javax.swing.JButton jButton1;
    private static boolean SHOW = true;
    private static Timer timer;
    private final static int VISIBLE_TIME = 5;
    //private Timer timer;
    // End of variables declaration
    
    
    public static void setPosition(int x, int y) {
    	jFrame.setLocation(x, y);
    }
    
    public static void setText(String str) {
    	jTextField1.setText(str);
    }
    
    public static void showBox() {

    	SHOW = true;
    	jFrame.setVisible(SHOW);
    	jFrame.toFront();
    	
    	if(timer != null){
    		timer.stop();
    	}
    	timer = new Timer(VISIBLE_TIME * 1000, new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hideBox();
			}
	    	
	    });
	    timer.setRepeats(false);
	    timer.start(); 

    }
    
    public static void hideBox() {
    	SHOW = false;
    	jFrame.setVisible(SHOW);
    }
    
    public static boolean isShow() {
    	return SHOW;
    }
    
}
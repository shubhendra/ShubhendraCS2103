/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import org.apache.log4j.*;

import data.Task;
import logic.JIDLogic;

//import com.seaglasslookandfeel.*;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import javax.swing.JTextField;

import data.Task;
import data.TaskArrayList;

/**
 * 
 * @author Ramon
 */
public class MainJFrame extends javax.swing.JFrame {

	private static Logger logger=Logger.getLogger(JIDLogic.class);
	
	enum STATE {
		ADD, DELETE, EDIT, SEARCH, COMPLETED, ARCHIVE, OVERDUE, NULL, LIST, UNDO
	};
	
	boolean edit = false;
	STATE curState;
	STATE prevState = STATE.NULL;
	Task[] prevTasks;
	Task[] tasks;
	String prevText;
	String id;
	int prevIndex;
	boolean expand = false;
	
	// Variables declaration - do not modify
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private ExpandJPanel expandJPanel = new ExpandJPanel();
	// End of variables declaration

	private static Point point = new Point();
	private static Point currentLocation = new Point(0,0);
	private final boolean TEST = true;

	// End of variables declaration

	/**
	 * Creates new form MainJFrame
	 */
	public MainJFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			/*
			 * for (javax.swing.UIManager.LookAndFeelInfo info :
			 * javax.swing.UIManager.getInstalledLookAndFeels()) { if
			 * ("Nimbus".equals(info.getName())) {
			 * javax.swing.UIManager.setLookAndFeel(info.getClassName());
			 * UIManager. break; } }
			 */
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		initComponents();
		setAction();
		this.setFocusable(true);
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				setVisible(true);
			}
		});
		
		addBindings();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		
		jLabel1 = new javax.swing.JLabel("", Resource.bigLogo,
				SwingConstants.CENTER);
		jLabel2 = new javax.swing.JLabel("", Resource.exitImg,
				SwingConstants.CENTER);
		jLabel3 = new javax.swing.JLabel("", Resource.down,
				SwingConstants.CENTER);

		jPanel1 = new javax.swing.JPanel();
		jComboBox1 = new javax.swing.JComboBox();

		setPreferredSize(new java.awt.Dimension(378, 300));

		jComboBox1.setEditable(true);
		jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jLabel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												56,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jLabel2))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jComboBox1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				291,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				7,
																				Short.MAX_VALUE)
																		.addComponent(
																				jLabel3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				22,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jComboBox1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								29,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel3,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addGap(0,
																				11,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jLabel1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		setIconImage((Resource.bigLogo).getImage());
		setUndecorated(true);
		setSize(400, 100);
		// pack();

	}// </editor-fold>


	/**
	 * setting drag option
	 * 
	 */
	private void setAction() {
		setJFrameAction();
		// setButtonAction();
		setJComboBox1Action();
		setJLabel1Action();
		setJLabel2Action();
		setJLabel3Action();
	}

	private void setJLabel3Action() {
		// TODO Auto-generated method stub
		jLabel3.setToolTipText("Expand");

		jLabel3.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				jLabel3.setIcon(Resource.downPress);
				Timer timer = new Timer(100, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						jLabel3.setIcon(Resource.down);
						if (MainJFrame.this.getSize().equals(
								new Dimension(400, 400))) {
							contractFrame();
						} else {
							expandFrame();
						}
					}

				});
				timer.setRepeats(false);
				timer.start();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

				jLabel3.setIcon(Resource.downOn);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

				jLabel3.setIcon(Resource.down);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

				jLabel3.setIcon(Resource.downPress);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

				jLabel3.setIcon(Resource.down);
			}
		});
	}

	private void setJLabel2Action() {
		// TODO Auto-generated method stub
		jLabel2.setToolTipText("Close");

		jLabel2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (TopPopUp.isShow())
					TopPopUp.hideBox();
				MainJFrame.this.setVisible(false);
				//JIDLogic.JIDLogic_close();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				jLabel2.setIcon(Resource.exitOn);
				//MainJFrame.this.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				jLabel2.setIcon(Resource.exitImg);
				//MainJFrame.this.revalidate();
			}

		});

	}

	private void setJLabel1Action() {
		// TODO Auto-generated method stub

	}

	private void setJComboBox1Action() {
		
		int index;
		// TODO Auto-generated method stub
		this.getButtonSubComponent(jComboBox1).setVisible(false);
		final AutoCompletion jBoxCompletion = new AutoCompletion(jComboBox1);

		final JTextField editorcomp = (JTextField) jComboBox1.getEditor()
				.getEditorComponent();
		editorcomp.setText("");
		
		editorcomp.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				final KeyEvent e = arg0;
				 SwingUtilities.invokeLater(
				      new Runnable() {
					    	int curIndex;
					    	String command;
					    	String curText;
							@Override
							public void run() {

						    	curText = editorcomp.getText();
								jBoxCompletion.stopWorking();
								//curText= editorcomp.getText();
								curState= checkCommand(curText);curIndex= getIndex();
								logger.debug("curText:" + curText);
								logger.debug("prevText: " + prevText);
								//logger.debug("cmd: " + command);
								logger.debug("state: " +curState);
								logger.debug("prev: " +prevState);
								logger.debug("index: "+ curIndex);

								if(prevState == STATE.NULL && curState!=prevState) {
									command = new String(curText);
								}
								
								if(curState == STATE.NULL && curState!=prevState) {
									jBoxCompletion.setStandardModel();
									//jBoxCompletion.startWorking();
									jComboBox1.setSelectedIndex(-1);
								}
								
								if(((curState == STATE.EDIT && !edit)
									|| curState == STATE.DELETE
									|| curState == STATE.SEARCH
									|| curState == STATE.COMPLETED)
									&& curText.length() > curState.toString().length() +1) {
									if((e.getKeyCode() == KeyEvent.VK_BACK_SPACE || !e.isActionKey())
									&& e.getKeyCode() != KeyEvent.VK_ENTER
									&& e.getKeyCode() != KeyEvent.VK_UP
									&& e.getKeyCode() != KeyEvent.VK_DOWN){
										
									 SwingUtilities.invokeLater(
									new Runnable() {
	
										@Override
										public void run() {
											// TODO Auto-generated method stub
											System.out
													.println("***enter interstate: ");
	
											JIDLogic.setCommand(curState.toString().toLowerCase());
	
											logger.debug("********exeCmd: "
													+ curText);
											tasks = JIDLogic
													.executeCommand(curText);
	
											//logger.debug(tasks[0].getName());
	
											jBoxCompletion.stopWorking();
											jBoxCompletion
													.setNewModel(taskArrayToString(tasks));
											
											jComboBox1.setPopupVisible(true);
	
											jComboBox1.setSelectedIndex(-1);
											editorcomp.setText(curText);
											//editorcomp.setText(curState.toString() + tasks[0]);
	
											if (tasks != null)
												id = tasks[0].getTaskId();
											else
												id = "dummyString!@#$";
										}
	
									});
								}

								if(curState != STATE.NULL &&
										(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN)) {
										curText = prevText;
										tasks = JIDLogic.executeCommand(curText);
										id = tasks[jComboBox1.getSelectedIndex()].getTaskId();
										editorcomp.setText(curText);
										return;
									}
								}
									
								if(e.getKeyCode() == KeyEvent.VK_ENTER && curState!=STATE.NULL) {
									String exeCmd = new String();
									
									logger.debug("*********************enter");
									//logger.debug(prevTasks[0].getName());
									
									if(curState != STATE.EDIT)
										edit = false;
									
									switch (curState ){
									case DELETE:
									case COMPLETED:
										
										exeCmd = curState.toString().toLowerCase() + " "
												+ id + " ";
										break;
									case EDIT:
										if(!edit) {
											exeCmd = curState.toString().toLowerCase() + " "
													+ id;
											editorcomp.setText(
													curState.toString().toLowerCase() + " " 
													+ storagecontroller.StorageManager.getTaskById(id));
											edit = true;
										}
										else {
											exeCmd = curText;
											edit = false;
										}
										break;
									case ADD:
										exeCmd = curText;
										break;
									case SEARCH:
										exeCmd = curText;
										break;
									case LIST:
									case UNDO:
										exeCmd = curText;
									}
									
									logger.debug("********exeCmd: " + exeCmd);
									JIDLogic.setCommand(curState.toString());
									tasks = JIDLogic.executeCommand(exeCmd);
									
									switch(curState) {
									case DELETE:
									case COMPLETED:
									case ADD:
									case EDIT:
										if(!edit) {
											if(tasks!=null) {
												showPopup( curState.toString()+ " " 
														+ tasks[0]);
												expandJPanel.updateJTable();
											}else
												showPopup("invalid input");
										}
									break;									
									case SEARCH:
										expandJPanel.updateJTable(tasks);
										expandFrame();
									break;
									case LIST:
										expandJPanel.updateJTable();
										expandFrame();
									break;
									case UNDO:
										expandJPanel.updateJTable();
										expandFrame();
										if(tasks!=null)
											showPopup("UNDO: " + tasks[0].getName());
										else
											showPopup("error!!!");
									break;
									}
									
									/*
									if(tasks==null)
										logger.debug("error");
									else
										logger.debug(tasks[0].toString());
									*/
								}
								
								prevState = curState;
								prevIndex = curIndex;
								prevText = curText;
								prevTasks = tasks;
							}
							
							private int getIndex() {
								int idx = jComboBox1.getSelectedIndex();
								
								if(idx <0 ) return idx;
								
								String selected = (String)jComboBox1.getItemAt(idx);
								
						//		if(curText.length() <= selected.length() 
							//			&& selected.substring(0, curText.length()).equalsIgnoreCase(curText))
									return idx;
								
							//	return -1;
							}

							private String[] taskArrayToString (Task[] tasks) {
								if(tasks!=null) {
									String[] strings = new String[tasks.length];
									for(int i=0; i<tasks.length; i++)
										strings[i]= curState.toString() + " " 
												+ tasks[i];
									
									logger.debug("str[0]: "+strings[0]);
									return strings;
								}
								else {
									return null;
								}
							}
							
							private STATE checkCommand(String curText) {
								String delims = "[ ]+";
								String firstWord = curText.split(delims)[0];
								if(firstWord.equalsIgnoreCase("add") 
										|| firstWord.equalsIgnoreCase("insert"))
									return STATE.ADD;
								if(firstWord.equalsIgnoreCase("delete")
										|| firstWord.equalsIgnoreCase("remove"))
									return STATE.DELETE;
								if(firstWord.equalsIgnoreCase("modify")
										|| firstWord.equalsIgnoreCase("edit")
										|| firstWord.equalsIgnoreCase("update"))
									return STATE.EDIT;
								if(firstWord.equalsIgnoreCase("search")
										|| firstWord.equalsIgnoreCase("find"))
									return STATE.SEARCH;
								if(firstWord.equalsIgnoreCase("completed")
										|| firstWord.equalsIgnoreCase("done"))
									return STATE.COMPLETED;
								if(firstWord.equalsIgnoreCase("archive"))
									return STATE.ARCHIVE;
								if(firstWord.equalsIgnoreCase("overdue"))
									return STATE.OVERDUE;
								if(firstWord.equalsIgnoreCase("list"))
									return STATE.LIST;
								if(firstWord.equalsIgnoreCase("undo"))
									return STATE.UNDO;
								return STATE.NULL;
							} 

				      } );
				}


		});

		editorcomp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				editorcomp.selectAll();
			}

		});

	}

	private void setJFrameAction() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				currentLocation = MainJFrame.this.getLocation();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				Point popupP = TopPopUp.jFrame.getLocation();
				TopPopUp.setPosition(popupP.x + e.getX() - point.x, popupP.y
							+ e.getY() - point.y);
				
			}
		});

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if (TEST)
					if (e.getKeyChar() == 'b') {
						logger.debug("B");
					}
			}

		});
	}

	public static void showPopup(String str) {
		logger.debug("-----------------POPUP-----------------------");
		TopPopUp.setText(str);
		TopPopUp.setPosition(currentLocation.x, currentLocation.y - 30);
		TopPopUp.showBox();
		TopPopUp.jFrame.setFocusable(true);
	}

	public void showFrame() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				MainJFrame.this.setVisible(true);
			}

		});
	}

	public void hideFrame() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				MainJFrame.this.setVisible(false);
			}

		});
	}

	public void setInputText(final String string) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				jComboBox1.setSelectedItem(string);
				jComboBox1.getEditor().getEditorComponent()
						.requestFocusInWindow();
			}

		});
	}

	private static JButton getButtonSubComponent(Container container) {
		if (container instanceof JButton) {
			return (JButton) container;
		} else {
			Component[] components = container.getComponents();
			for (Component component : components) {
				if (component instanceof Container) {
					return getButtonSubComponent((Container) component);
				}
			}
		}
		return null;
	}
	
	private void expandFrame() {
		if(!expand) {
			MainJFrame.this.setLayout(new BorderLayout());
			MainJFrame.this.add(expandJPanel, BorderLayout.SOUTH);
			MainJFrame.this.setSize(400,400);
			expand = true;
			jLabel3.setToolTipText("Contract");
		}
	}
	
	private void contractFrame() {
		if (expand) {
			MainJFrame.this.remove(expandJPanel);
			MainJFrame.this.setSize(400, 100);
			expand = false;
			jLabel3.setToolTipText("Expand");
		}
	}
	
    protected void addBindings() {
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();
        
        new Binding(inputMap, actionMap);
        
    }
    
}
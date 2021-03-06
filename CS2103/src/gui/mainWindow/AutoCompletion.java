package gui.mainWindow;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

import org.apache.log4j.Logger;

/**
 * auto completion textbox
 * (will implement autocompletion in V0.3)
 * @author Ramon
 *
 */
public class AutoCompletion extends PlainDocument {
    JComboBox comboBox;
    ComboBoxModel model;
    JTextComponent editor;
    
    boolean working = true;
    boolean selecting=false;
    boolean hidePopupOnFocusLoss;
    boolean hitBackspace=false;
    boolean hitBackspaceOnSelection;
    boolean popupAllow = false;
    int index;
    String[] standardCommand = new String[]{};
    
	private static Logger logger=Logger.getLogger(AutoCompletion.class);
    
    KeyListener editorKeyListener;
    FocusListener editorFocusListener;
    
    /**
     * constructor
     * @param comboBox a comboBox that we want an autocomplete function
     */
    public AutoCompletion(final JComboBox comboBox) {
    	
        this.comboBox = comboBox;
        setStandardModel();
        
 /*       
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (working&&!selecting) highlightCompletedText(0);
            }
        });
        comboBox.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
            	if(working) {
	                if (e.getPropertyName().equals("editor")) configureEditor((ComboBoxEditor) e.getNewValue());
	                if (e.getPropertyName().equals("model")) model = (ComboBoxModel) e.getNewValue();
	            }
            }
        });*/
/*        editorKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if(working) {
	            	if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
	            		comboBox.setPopupVisible(false);
					else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if(comboBox.getSelectedIndex()!=-1) {
							editor.setText(comboBox.getSelectedItem().toString());
							comboBox.setPopupVisible(false);
							editor.setCaretPosition(getLength());
						}
					}
		            else if (comboBox.isDisplayable()) 
		            		comboBox.setPopupVisible(true);
					
	                hitBackspace=false;
	                switch (e.getKeyCode()) {
	                    // determine if the pressed key is backspace (needed by the remove method)
	                    /*case KeyEvent.VK_BACK_SPACE : hitBackspace=true;
	                    hitBackspaceOnSelection=editor.getSelectionStart()!=editor.getSelectionEnd();
	                    break;
	                    // ignore delete key
	                    case KeyEvent.VK_DELETE : e.consume();
	                    comboBox.getToolkit().beep();
	                    break;
	            	//}*/
 //           	}
 //           }
 //       };*/
        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
        // Highlight whole text when gaining focus
 /*       editorFocusListener = new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                highlightCompletedText(0);
            }
            public void focusLost(FocusEvent e) {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss) comboBox.setPopupVisible(false);
            }
        };
        configureEditor(comboBox.getEditor());
        // Handle initially selected object
        Object selected = comboBox.getSelectedItem();
        if (selected!=null) setText(selected.toString());
        highlightCompletedText(0);
        */
        
    }
    
    /**
     * set standard model of this combo box
     */
    public void setStandardModel() {
		// TODO Auto-generated method stub
    	popupAllow = false;
    	comboBox.setMaximumRowCount(5);
        comboBox.setModel(new DefaultComboBoxModel(standardCommand));
        model = comboBox.getModel();
	}
    
    /**
     * set new model
     * @param strings list of items in pop up
     */
    public void setNewModel(String[] strings) {
    	popupAllow = true;
    	if(strings==null) {
    		String[] temp = new String[1];
    		temp[0] = "not found!";
    		comboBox.setMaximumRowCount(1);
    		comboBox.setModel(new DefaultComboBoxModel(temp));
    	}
    	else {
	    	System.out.println("setNewModel");
	    	comboBox.setMaximumRowCount( strings.length > 5 ? 5 : strings.length );
	    	comboBox.setModel(new DefaultComboBoxModel(strings));
	    	model = comboBox.getModel();
    	}	
    }

    /**
     * let the comboBox editable and have autocompletion function
     * @param comboBox
     */
	public static void enable(JComboBox comboBox) {
        // has to be editable
        comboBox.setEditable(true);
        // change the editor's document
        new AutoCompletion(comboBox);
    }
    
	/**
	 * config an editor
	 * @param newEditor a wanted editor
	 */
    void configureEditor(ComboBoxEditor newEditor) {
        if (editor != null) {
            editor.removeKeyListener(editorKeyListener);
            editor.removeFocusListener(editorFocusListener);
        }
        
        if (newEditor != null) {
            editor = (JTextComponent) newEditor.getEditorComponent();
            editor.addKeyListener(editorKeyListener);
            editor.addFocusListener(editorFocusListener);
            editor.setDocument(this);
        }
    }
    
    /**
     * remove string
     * @param offs starting point
     * @param len length of string
     */
    public void remove(int offs, int len) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        if (hitBackspace) {
            // user hit backspace => move the selection backwards
            // old item keeps being selected
            if (offs>0) {
                if (hitBackspaceOnSelection) offs--;
            } else {
                // User hit backspace with the cursor positioned on the start => beep
                // comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            highlightCompletedText(offs);
        } else {
            super.remove(offs, len);
        }
    }
    
    /**
     * insert string into a comboBox
     * @param offs offset
     * @param string inserted string
     */
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        // insert the string into the document
        super.insertString(offs, str, a);
        // lookup and select a matching item
        Object item = lookupItem(getText(0, getLength()));
        if (item != null) {
            setSelectedItem(item);
            if(index==0)
            	setText(item.toString());

            // select the completed part
            highlightCompletedText(offs+str.length());
        } /*else {
            // keep old item selected if there is no match
            item = comboBox.getSelectedItem();
            // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
            offs = offs-str.length();
            // provide feedback to the user that his input has been received but can not be accepted
            comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
        }
        */
        else {
        	//hide popup
        	comboBox.setPopupVisible(false);
        }
    }
    
    /**
     * setText in the comboBox
     * @param text wanted text
     */
    private void setText(String text) {
        try {
            // remove all text and insert the completed string
            super.remove(0, getLength());
            super.insertString(0, text, null);
        } catch (BadLocationException e) {
        	logger.error("BadLocationException");
            throw new RuntimeException(e.toString());
        }
    }
    
    /**
     * highlight text to the end of string
     * @param start starting point
     */
    public void highlightCompletedText(int start) {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }
    
    /**
     * set model select the item
     * @param item item that will be selected
     */
    private void setSelectedItem(Object item) {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }
    
    /**
     * find the matching pattern in the pop up list
     * @param pattern string that we want to find
     * @return a first object in the pop up list
     */
    private Object lookupItem(String pattern) {
        Object selectedItem = model.getSelectedItem();
        // only search for a different item if the currently selected does not match
        if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
            return selectedItem;
        } else {
            // iterate over all items
            for (int i=0, n=model.getSize(); i < n; i++) {
                Object currentItem = model.getElementAt(i);
                // current item starts with the pattern?
                for(index=0; currentItem!=null && index < currentItem.toString().length(); index++ )
                if (startsWithIgnoreCase(currentItem.toString().substring(index), pattern)) {
                    return currentItem;
                }
            }
        }
        // no item starts with the pattern => return null
        return null;
    }
    
    /**
     * checks if str1 starts with str2 - ignores case
     * @param str1
     * @param str2
     * @return
     */
    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
    
    /*
    private static void createAndShowGUI() {
        // the combo box (add/modify items if you like to)
        final JComboBox comboBox = new JComboBox(new Object[] {"Ester", "Jordi", "Jordina", "Jorge", "Sergi"});
        enable(comboBox);

        // create and show a window containing the combo box
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(comboBox);
        frame.pack(); frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    */
    
    /**
     * start auto completion functionality
     */
    public void startWorking() {
    	working = true;
    }
    
    /**
     * stop auto completion functionality
     */
    public void stopWorking() {
    	working = false;
    }
}

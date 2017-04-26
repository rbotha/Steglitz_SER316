package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.sf.memoranda.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceTypeDialog.
 */
/*$Id: ResourceTypeDialog.java,v 1.11 2004/07/01 14:44:10 pbielen Exp $*/
public class ResourceTypeDialog extends JDialog {
    
    /** The cancel B. */
    JButton cancelB = new JButton();
    
    /** The buttons panel. */
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    /** The header. */
    JLabel header = new JLabel();
    
    /** The dialog title panel. */
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    /** The ok B. */
    JButton okB = new JButton();
    
    /** The area panel. */
    ResourceTypePanel areaPanel = new ResourceTypePanel();
    //JPanel mPanel = new JPanel(new BorderLayout());

    /** The border 2. */
    Border border2;
    
    /** The titled border 2. */
    TitledBorder titledBorder2;
    
    /** The ext. */
    public String ext = "";
    
    /** The cancelled. */
    boolean CANCELLED = true;

    /**
     * Instantiates a new resource type dialog.
     *
     * @param frame the frame
     * @param title the title
     */
    public ResourceTypeDialog(JFrame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    /**
     * Jb init.
     *
     * @throws Exception the exception
     */
    void jbInit() throws Exception {
    	   getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
   	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
   	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
   	            public void actionPerformed(ActionEvent e)
   	            {
   	            	cancelB_actionPerformed(e);
   	            }
   	        });
	this.setResizable(false);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Resource type"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.ResourceTypeDialog.class.getResource(
            "resources/icons/resource48.png")));        
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        
        //mPanel.add(areaPanel, BorderLayout.CENTER);
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));

        
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
         
    }



    /**
     * Cancel B action performed.
     *
     * @param e the e
     */
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
    
    /**
     * Ok B action performed.
     *
     * @param e the e
     */
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }

    /**
     * Gets the types list.
     *
     * @return the types list
     */
    public JList getTypesList() {
      return areaPanel.typesList;
    }



}
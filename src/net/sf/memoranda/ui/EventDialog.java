package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import javax.swing.BoxLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDialog.
 */
/*$Id: EventDialog.java,v 1.28 2005/02/19 10:06:25 rawsushi Exp $*/
public class EventDialog extends JDialog implements WindowListener {	
    
    /** The cancelled. */
    public boolean CANCELLED = false;
	
	/** The use email. */
	public boolean useEmail = true;
    
    /** The ignore start changed. */
    boolean ignoreStartChanged = false;
    
    /** The ignore end changed. */
    boolean ignoreEndChanged = false;
    
    /** The top panel. */
    JPanel topPanel = new JPanel(new BorderLayout());
    
    /** The bottom panel. */
    JPanel bottomPanel = new JPanel(new BorderLayout());
    
    /** The header panel. */
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    /** The header. */
    public JLabel header = new JLabel();
    
    /** The event panel. */
    JPanel eventPanel = new JPanel(new GridBagLayout());
    
    /** The note panel. */
    private final JPanel notePanel = new JPanel();
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The lbl time. */
    JLabel lblTime = new JLabel();
    
    /** The time spin. */
    public JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    
    /** The lbl text. */
    JLabel lblText = new JLabel();
    
    /** The text field. */
    public JTextField textField = new JTextField();
    
    /** The repeat border. */
    TitledBorder repeatBorder;
    
    /** The repeat panel. */
    JPanel repeatPanel = new JPanel(new GridBagLayout());
    
    /** The no repeat RB. */
    public JRadioButton noRepeatRB = new JRadioButton();
    
    /** The daily repeat RB. */
    public JRadioButton dailyRepeatRB = new JRadioButton();
    
    /** The day spin. */
    public JSpinner daySpin = new JSpinner(new SpinnerNumberModel(1,1,365,1));
    
    /** The lbl days. */
    JLabel lblDays = new JLabel();
    
    /** The lbl since. */
    JLabel lblSince = new JLabel();
    
    /** The start date. */
    public JSpinner startDate = new JSpinner(new SpinnerDateModel());
    
    /** The set start date B. */
    JButton setStartDateB = new JButton();
    
    /** The weekly repeat RB. */
    public JRadioButton weeklyRepeatRB = new JRadioButton();
    
    /** The weekdays CB. */
    public JComboBox weekdaysCB = new JComboBox(Local.getWeekdayNames());
    
    /** The enable end date CB. */
    public JCheckBox enableEndDateCB = new JCheckBox();
	
	/** The working days only CB. */
	public JCheckBox workingDaysOnlyCB = new JCheckBox();
    
    /** The end date. */
    public JSpinner endDate = new JSpinner(new SpinnerDateModel());
    
    /** The set end date B. */
    JButton setEndDateB = new JButton();
    
    /** The monthly repeat RB. */
    public JRadioButton monthlyRepeatRB = new JRadioButton();
    
    /** The day of month spin. */
    public JSpinner dayOfMonthSpin = new JSpinner(new SpinnerNumberModel(1,1,31,1));
    
    /** The lbl do M. */
    JLabel lblDoM = new JLabel();
    
    /** The yearly repeat RB. */
    public JRadioButton yearlyRepeatRB = new JRadioButton();
    
    /** The repeat RB group. */
    ButtonGroup repeatRBGroup = new ButtonGroup();
    
    /** The buttons panel. */
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    
    /** The ok B. */
    JButton okB = new JButton();
    
    /** The cancel B. */
    JButton cancelB = new JButton();
    
    /** The end cal frame. */
    CalendarFrame endCalFrame = new CalendarFrame();
    
    /** The start cal frame. */
    CalendarFrame startCalFrame = new CalendarFrame();
    
    /** The event date. */
    private Date eventDate;
    
    /** The middle panel. */
    private final JPanel middlePanel = new JPanel();
    
    /** The email panel. */
    private final JPanel emailPanel = new JPanel();
    
    /** The email toggle. */
    public final JCheckBox emailToggle = new JCheckBox("Email:");
    
    /** The email input field. */
    public final JTextField emailInputField = new JTextField();
    
    /** The lbl note. */
    private final JLabel lblNote = new JLabel("Note:");
    
    /** The note field. */
    public final JTextField noteField = new JTextField();
    
    /**
     * Instantiates a new event dialog.
     *
     * @param frame the frame
     * @param title the title
     */
    public EventDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        super.addWindowListener(this);
    }

    /**
     * Jb init.
     *
     * @throws Exception the exception
     */
    void jbInit() throws Exception {
    	this.setResizable(false);
        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Event"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.EventDialog.class.getResource(
            "resources/icons/event48.png")));
        headerPanel.add(header);
        
        // Build eventPanel
        lblTime.setText(Local.getString("Time"));
        lblTime.setMinimumSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblTime, gbc);
        timeSpin.setPreferredSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(timeSpin, gbc);
        lblText.setText(Local.getString("Text"));
        lblText.setMinimumSize(new Dimension(120, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblText, gbc);
        textField.setMinimumSize(new Dimension(375, 24));
        textField.setPreferredSize(new Dimension(375, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(textField, gbc);
        
        // Build RepeatPanel
        repeatBorder = new TitledBorder(BorderFactory.createLineBorder(
        Color.gray, 1), Local.getString("Repeat"));
        repeatPanel.setBorder(repeatBorder);
        noRepeatRB.setMaximumSize(new Dimension(80, 35));
        noRepeatRB.setSelected(true);
        noRepeatRB.setText(Local.getString("No repeat"));
        noRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        repeatPanel.add(noRepeatRB, gbc);
        dailyRepeatRB.setActionCommand("daily");
        dailyRepeatRB.setText(Local.getString("Every"));
        dailyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dailyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dailyRepeatRB, gbc);
        daySpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(daySpin, gbc);
        lblDays.setText(Local.getString("day(s)"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDays, gbc);
        lblSince.setText(Local.getString("Since"));
        lblSince.setMinimumSize(new Dimension(70, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 4; gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(lblSince, gbc);
        startDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreStartChanged)
                    return;
                ignoreStartChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                // Commented out, value was resetted to endDate !!!
                if (sd.after(ed)) {
                  endDate.getModel().setValue(sd);
                  ed = sd;
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });
        startDate.setPreferredSize(new Dimension(80, 24));
        
        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
		//---------------------------------------------------
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
        startDate.setEditor(new JSpinner.DateEditor(startDate, 
        	sdf.toPattern()));
        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(startDate, gbc);
        setStartDateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDateB_actionPerformed(e);
            }
        });
        setStartDateB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        setStartDateB.setText("");
        setStartDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 6; gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setStartDateB, gbc);
        weeklyRepeatRB.setActionCommand("weekly");
        weeklyRepeatRB.setText(Local.getString("Every"));
        weeklyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                weeklyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weeklyRepeatRB, gbc);
        weekdaysCB.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weekdaysCB, gbc);
        enableEndDateCB.setHorizontalAlignment(SwingConstants.RIGHT);
        enableEndDateCB.setText(Local.getString("Till"));
        enableEndDateCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableEndDateCB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 4; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(enableEndDateCB, gbc);
        endDate.setPreferredSize(new Dimension(80, 24));
		//Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
		//---------------------------------------------------
		endDate.setEditor(new JSpinner.DateEditor(endDate, sdf.toPattern()));
		//---------------------------------------------------
        endDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreEndChanged)
                    return;
                ignoreEndChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed)) {
                    endDate.getModel().setValue(sd);
                    ed = sd;
                }
                endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
		// working days
		workingDaysOnlyCB.setText(Local.getString("Working days only"));
		workingDaysOnlyCB.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc = new GridBagConstraints();
        gbc.gridx = 4; gbc.gridy = 3;
		gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
		repeatPanel.add(workingDaysOnlyCB, gbc);
		// -------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(endDate, gbc);
        setEndDateB.setMinimumSize(new Dimension(24, 24));
        setEndDateB.setPreferredSize(new Dimension(24, 24));
        setEndDateB.setText("");
        setEndDateB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        setEndDateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEndDateB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 6; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setEndDateB, gbc);
        monthlyRepeatRB.setActionCommand("daily");
        monthlyRepeatRB.setText(Local.getString("Every"));
        monthlyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monthlyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(monthlyRepeatRB, gbc);
        dayOfMonthSpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dayOfMonthSpin, gbc);
        lblDoM.setText(Local.getString("day of month"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDoM, gbc);
		yearlyRepeatRB.setActionCommand("yearly");
		yearlyRepeatRB.setText(Local.getString("Yearly"));
		yearlyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yearlyRepeatRB_actionPerformed(e);
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 4;
		gbc.gridwidth = 5;
		gbc.insets = new Insets(5, 5, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;
		repeatPanel.add(yearlyRepeatRB, gbc);
        
        repeatRBGroup.add(noRepeatRB);
        repeatRBGroup.add(dailyRepeatRB);
        repeatRBGroup.add(weeklyRepeatRB);
        repeatRBGroup.add(monthlyRepeatRB);
        repeatRBGroup.add(yearlyRepeatRB);
        
        // Build ButtonsPanel
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
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        
        // Finally build the Dialog
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(eventPanel, BorderLayout.SOUTH);
        bottomPanel.add(repeatPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        this.getContentPane().add(middlePanel, BorderLayout.WEST);
        middlePanel.setLayout(new GridLayout(2, 1, 0, 5));
        
        emailInputField.setToolTipText("Field to enter your email.");
        emailInputField.setForeground(Color.BLACK);
        emailInputField.setColumns(30);
        middlePanel.add(emailPanel);
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
        emailToggle.setPreferredSize(new Dimension(100, 30));
        emailToggle.setSelected(true);
        emailToggle.setToolTipText("Use this to toggle email functionality on and off");
        emailToggle.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent ie) {
        		switch (ie.getStateChange()) {
	        		case 1: //if selected.
	        			emailInputField.setVisible(true);
	        			emailInputField.setEnabled(true);
	        			useEmail = true;
	        			break;
	        		case 2: //if deselected.
	        			emailInputField.setVisible(false);
	        			emailInputField.setEnabled(false);
	        			useEmail = false;
	        			break;
	    			default:
	    				//empty
	    				break;
        		}
        	}
        });
 		emailPanel.add(emailToggle);
        
        emailPanel.add(emailInputField);
        middlePanel.add(notePanel);
        noteField.setToolTipText("Use to attach a note to event");
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.X_AXIS));
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setPreferredSize(new Dimension(71, 20));
        notePanel.add(lblNote);
        notePanel.add(noteField);
        // Do final things...
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged) return;
                startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
        disableElements();
        ((JSpinner.DateEditor) timeSpin.getEditor()).getFormat().applyPattern("HH:mm");
        enableEndDateCB_actionPerformed(null);
        
        
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	            	 cancelB_actionPerformed(e);
	            }
	        });
        
    }

    /**
     * Disable elements.
     */
    void disableElements() {
        dayOfMonthSpin.setEnabled(false);
        daySpin.setEnabled(false);
        weekdaysCB.setEnabled(false);
        startDate.setEnabled(false);
        setStartDateB.setEnabled(false);
        lblSince.setEnabled(false);
        endDate.setEnabled(false);
        setEndDateB.setEnabled(false);
        enableEndDateCB.setEnabled(false);
		enableEndDateCB.setSelected(false);
		workingDaysOnlyCB.setEnabled(false);
		workingDaysOnlyCB.setSelected(false);		
    }
    
    /**
     * Yearly repeat R B action performed.
     *
     * @param e the e
     */
    public void yearlyRepeatRB_actionPerformed(ActionEvent e) {
		disableElements();
		startDate.setEnabled(true);
		setStartDateB.setEnabled(true);
		lblSince.setEnabled(true);
		enableEndDateCB.setEnabled(true);
		workingDaysOnlyCB.setEnabled(true);
		startDate.getModel().setValue(
			startCalFrame.cal.get().getCalendar().getTime());
    }

    /**
     * Monthly repeat R B action performed.
     *
     * @param e the e
     */
    public void monthlyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        dayOfMonthSpin.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
		workingDaysOnlyCB.setEnabled(true);
		startDate.getModel().setValue(
			startCalFrame.cal.get().getCalendar().getTime());        
    }

    /**
     * Daily repeat R B action performed.
     *
     * @param e the e
     */
    public void dailyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        daySpin.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
		workingDaysOnlyCB.setEnabled(true);
		startDate.getModel().setValue(
			startCalFrame.cal.get().getCalendar().getTime());        
    }

    /**
     * Weekly repeat R B action performed.
     *
     * @param e the e
     */
    public void weeklyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        weekdaysCB.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
		startDate.getModel().setValue(
			startCalFrame.cal.get().getCalendar().getTime());        
    }

    /**
     * No repeat R B action performed.
     *
     * @param e the e
     */
    public void noRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
    }

    /**
     * Ok B action performed.
     *
     * @param e the e
     */
    void okB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * Cancel B action performed.
     *
     * @param e the e
     */
    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    /**
     * Sets the start date B action performed.
     *
     * @param e the new start date B action performed
     */
    void setStartDateB_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(setStartDateB.getLocation());
        startCalFrame.setSize(200, 190);
        startCalFrame.setTitle(Local.getString("Start date"));
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();
    }

    /**
     * Sets the end date B action performed.
     *
     * @param e the new end date B action performed
     */
    void setEndDateB_actionPerformed(ActionEvent e) {
        //endCalFrame.setLocation(setEndDateB.getLocation());
        endCalFrame.setSize(200, 190);
        endCalFrame.setTitle(Local.getString("End date"));
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }

    /**
     * Enable end date C B action performed.
     *
     * @param e the e
     */
    public void enableEndDateCB_actionPerformed(ActionEvent e) {
        endDate.setEnabled(enableEndDateCB.isSelected());
        setEndDateB.setEnabled(enableEndDateCB.isSelected());        
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened( WindowEvent e ) {}

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
    
    /**
     * Sets the event date.
     *
     * @param d the new event date
     */
    public void setEventDate(Date d) {
	    eventDate = d;
	}
	
	/**
	 * Gets the event date.
	 *
	 * @return the event date
	 */
	public Date getEventDate() {
		return eventDate;
	}
	
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed( WindowEvent e ) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified( WindowEvent e ) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified( WindowEvent e ) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated( WindowEvent e ) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated( WindowEvent e ) {}

}
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
//import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/*$Id: TaskDialog.java,v 1.25 2005/12/01 08:12:26 alexeya Exp $*/
public class TaskDialog extends JDialog {
	public long timestamp = (long)-1; //tracks a timestamp in milliseconds from epoch (1970-01-01T00:00:00Z). if < 0, no timestamp.
	
	JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());	
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton cancelB = new JButton();
    JButton okB = new JButton();
    Border border1;
    Border border2;
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    public boolean CANCELLED = true;
    JPanel jPanel8 = new JPanel(new GridBagLayout());
    Border border3;
    Border border4;
//    Border border5;
//    Border border6;
    JPanel jPanel2 = new JPanel(new GridLayout(3, 2));
    JTextField todoField = new JTextField();
    
    // added by rawsushi
    JTextField effortField = new JTextField();
    JTextArea descriptionField = new JTextArea();
    JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
    	
	// Added by drmorri8 for actualEffort functionality:
	JLabel jLabelActualEffort = new JLabel();
	JTextField actualEffortField = new JTextField();
	JPanel timePanel = new JPanel(new GridLayout(2, 1));
	JLabel jLabelTimestamp = new JLabel();
	JButton timestampB = new JButton();
	
	// Added by drmorri8 for errorsAdded/errorsFixed
	JPanel pspTrackingPanel = new JPanel(new BorderLayout());
	JPanel pspInputPanel = new JPanel(new GridLayout(3, 2));
	JPanel pspEffortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel pspActualEffortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel pspErrorsAddedPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel pspErrorsFixedPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel pspEstLOCPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel pspActLOCPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	JLabel jLabelErrorsAdded = new JLabel();
	JTextField errorsAddedField = new JTextField();
	JLabel jLabelErrorsFixed= new JLabel();
	JTextField errorsFixedField = new JTextField();
	JLabel jLabelEstLOC = new JLabel();
	JLabel jLabelActLOC = new JLabel();
	JTextField estLOCField = new JTextField();
	JTextField actLOCField = new JTextField();

//    Border border7;
    Border border8;
    CalendarFrame startCalFrame = new CalendarFrame();
    CalendarFrame endCalFrame = new CalendarFrame();
    String[] priority = {Local.getString("Lowest"), Local.getString("Low"),
        Local.getString("Normal"), Local.getString("High"),
        Local.getString("Highest")};
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    JPanel jPanel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JPanel jPanel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel jLabel6 = new JLabel();
    JButton setStartDateB = new JButton();
    JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel jLabel2 = new JLabel();
    JSpinner startDate;
    JSpinner endDate;
//    JSpinner endDate = new JSpinner(new SpinnerDateModel());
    JButton setEndDateB = new JButton();
    //JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jPanelBlank = new JPanel(new FlowLayout(FlowLayout.CENTER));
//    JPanel jPanelNotes = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    JButton setNotifB = new JButton();
    JComboBox priorityCB = new JComboBox(priority);
    JLabel jLabel7 = new JLabel();
    //color setting
    String[] colors = {
        Local.getString("Yellow"),
        Local.getString("Orange"),
        Local.getString("Red"),
        Local.getString("Blue"),
        Local.getString("Green"),
        Local.getString("Cyan"),
        Local.getString("Magenta"),
        Local.getString("Black"),
        Local.getString("White"),
        Local.getString("Pink"),
        Local.getString("None")
    };
    JComboBox taskColor = new JComboBox(colors);
    JLabel jLabel8 = new JLabel();
    // added by rawsushi
    JLabel jLabelEffort = new JLabel();
    JLabel jLabelDescription = new JLabel();
	JCheckBox chkEndDate = new JCheckBox();
	
	JPanel jPanelProgress = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JLabel jLabelProgress = new JLabel();
	JSpinner progress = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
	
	//Forbid to set dates outside the bounds
	CalendarDate startDateMin = CurrentProject.get().getStartDate();
	CalendarDate startDateMax = CurrentProject.get().getEndDate();
	CalendarDate endDateMin = startDateMin;
	CalendarDate endDateMax = startDateMax;
    
    public TaskDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
	this.setResizable(false);
	this.setSize(new Dimension(430,400));
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(142, 142, 142));
        border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0), 
        Local.getString("To Do"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
        border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
//        border5 = BorderFactory.createEmptyBorder();
//        border6 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
//            Color.white, Color.white, new Color(178, 178, 178),
//            new Color(124, 124, 124));
//        border7 = BorderFactory.createLineBorder(Color.white, 2);
        border8 = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });

        startDate = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
        endDate = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
		
        chkEndDate.setSelected(false);
		chkEndDate_actionPerformed(null);
		chkEndDate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkEndDate_actionPerformed(e);
			}
		});
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
        mPanel.setBorder(border1);
        areaPanel.setBorder(border2);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(border4);
        //dialogTitlePanel.setMinimumSize(new Dimension(159, 52));
        //dialogTitlePanel.setPreferredSize(new Dimension(159, 52));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("To do"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.TaskDialog.class.getResource(
            "resources/icons/task48.png")));
        
        GridBagLayout gbLayout = (GridBagLayout) jPanel8.getLayout();
        jPanel8.setBorder(border3);
				
        todoField.setBorder(border8);
        todoField.setPreferredSize(new Dimension(375, 24));
        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 1;
        gbLayout.setConstraints(todoField,gbCon);
        
        jLabelDescription.setMaximumSize(new Dimension(100, 16));
        jLabelDescription.setMinimumSize(new Dimension(60, 16));
        jLabelDescription.setText(Local.getString("Description"));
        gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(jLabelDescription,gbCon);

        descriptionField.setBorder(border8);
        descriptionField.setPreferredSize(new Dimension(375, 387)); // 3 additional pixels from 384 so that the last line is not cut off
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        gbCon = new GridBagConstraints();
        gbCon.gridwidth = GridBagConstraints.REMAINDER;
        gbCon.weighty = 3;
        descriptionScrollPane.setPreferredSize(new Dimension(375,96));
        gbLayout.setConstraints(descriptionScrollPane,gbCon);

        jLabelEffort.setMaximumSize(new Dimension(100, 16));
        jLabelEffort.setMinimumSize(new Dimension(60, 16));
        jLabelEffort.setText(Local.getString("Est Effort(hrs)"));
        effortField.setBorder(border8);
        effortField.setPreferredSize(new Dimension(60, 24));
		
		// Added by drmorri8 for actualEffort
        timePanel.setBorder(border2);
		jLabelActualEffort.setMaximumSize(new Dimension(100, 16));
        jLabelActualEffort.setMinimumSize(new Dimension(60, 16));
        jLabelActualEffort.setText(Local.getString("Actual Effort(hrs)"));
        actualEffortField.setBorder(border8);
        actualEffortField.setPreferredSize(new Dimension(60, 24));
		jLabelTimestamp.setMaximumSize(new Dimension(100, 16));
        jLabelTimestamp.setMinimumSize(new Dimension(60, 16));
        jLabelTimestamp.setText(Local.getString("Not working on task"));
		timestampB.setMaximumSize(new Dimension(150, 26));
        timestampB.setMinimumSize(new Dimension(150, 26));
        timestampB.setPreferredSize(new Dimension(150, 26));
        timestampB.setText(Local.getString("Begin work session"));
        timestampB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timestampB_actionPerformed(e);
            }
        });
        

        // Added by drmorri8 for ErrorsAdded/ErrorsFixed functionality:
        pspTrackingPanel.setBorder(border2);
        jLabelErrorsAdded.setMaximumSize(new Dimension(100, 16));
        jLabelErrorsAdded.setMinimumSize(new Dimension(60, 16));
        jLabelErrorsAdded.setText(Local.getString("Errors Added"));
        errorsAddedField.setBorder(border8);
        errorsAddedField.setPreferredSize(new Dimension(60, 24));
        jLabelErrorsFixed.setMaximumSize(new Dimension(100, 16));
        jLabelErrorsFixed.setMinimumSize(new Dimension(60, 16));
        jLabelErrorsFixed.setText(Local.getString("Errors Fixed"));
        errorsFixedField.setBorder(border8);
        errorsFixedField.setPreferredSize(new Dimension(60, 24));

        //added by rbotha for Actual and estimated LOC
        jLabelEstLOC.setMaximumSize(new Dimension(100, 16));
        jLabelEstLOC.setMinimumSize(new Dimension(60, 16));
        jLabelEstLOC.setText(Local.getString("Est. LOC"));
        estLOCField.setPreferredSize(new Dimension(60, 24));
        jLabelActLOC.setMaximumSize(new Dimension(100, 16));
        jLabelActLOC.setMinimumSize(new Dimension(100, 16));
        jLabelActLOC.setText(Local.getString("Act. LOC"));
        actLOCField.setPreferredSize(new Dimension(60, 24));

        startDate.setBorder(border8);
        startDate.setPreferredSize(new Dimension(80, 24));                
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		// //Added by (jcscoobyrs) on 14-Nov-2003 at 10:45:16 PM
		startDate.setEditor(new JSpinner.DateEditor(startDate, sdf.toPattern()));

        startDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)startDate.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	startDate.setModel(sdm);

                if (ignoreStartChanged)
                    return;
                ignoreStartChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed) && chkEndDate.isSelected()) {
                    startDate.getModel().setValue(ed);
                    sd = ed;
                }
				if ((startDateMax != null) && sd.after(startDateMax.getDate())) {
					startDate.getModel().setValue(startDateMax.getDate());
                    sd = startDateMax.getDate();
				}
                if ((startDateMin != null) && sd.before(startDateMin.getDate())) {
                    startDate.getModel().setValue(startDateMin.getDate());
                    sd = startDateMin.getDate();
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });

        jLabel6.setText(Local.getString("Start date"));
        //jLabel6.setPreferredSize(new Dimension(60, 16));
        jLabel6.setMinimumSize(new Dimension(60, 16));
        jLabel6.setMaximumSize(new Dimension(100, 16));
        setStartDateB.setMinimumSize(new Dimension(24, 24));
        setStartDateB.setPreferredSize(new Dimension(24, 24));
        setStartDateB.setText("");
        setStartDateB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        setStartDateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDateB_actionPerformed(e);
            }
        });
        jLabel2.setMaximumSize(new Dimension(270, 16));
        //jLabel2.setPreferredSize(new Dimension(60, 16));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Local.getString("End date"));
        endDate.setBorder(border8);
        endDate.setPreferredSize(new Dimension(80, 24));
        
		endDate.setEditor(new JSpinner.DateEditor(endDate, sdf.toPattern())); //Added by (jcscoobyrs) on
		//14-Nov-2003 at 10:45:16PM
        
        endDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)endDate.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	endDate.setModel(sdm);
            	
                if (ignoreEndChanged)
                    return;
                ignoreEndChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();				
				if (ed.before(sd)) {
                    endDate.getModel().setValue(ed);
                    ed = sd;
                }
				if ((endDateMax != null) && ed.after(endDateMax.getDate())) {
					endDate.getModel().setValue(endDateMax.getDate());
                    ed = endDateMax.getDate();
				}
                if ((endDateMin != null) && ed.before(endDateMin.getDate())) {
                    endDate.getModel().setValue(endDateMin.getDate());
                    ed = endDateMin.getDate();
                }
				endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
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
        
        setNotifB.setText(Local.getString("Set notification"));
        setNotifB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/notify.png")));
        setNotifB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNotifB_actionPerformed(e);
            }
        });
        jLabel7.setMaximumSize(new Dimension(100, 16));
        jLabel7.setMinimumSize(new Dimension(60, 16));
        //jLabel7.setPreferredSize(new Dimension(60, 16));
        jLabel7.setText(Local.getString("Priority"));

        jLabel8.setMaximumSize(new Dimension(100, 16));
        jLabel8.setMinimumSize(new Dimension(60, 16));
        //jLabel8.setPreferredSize(new Dimension(60, 16));
        jLabel8.setText(Local.getString("Color"));

        priorityCB.setFont(new java.awt.Font("Dialog", 0, 11));
        taskColor.setFont(new java.awt.Font("Dialog", 0, 11));
        jPanel4.add(jLabel7, null);
        getContentPane().add(mPanel);
        mPanel.add(areaPanel, BorderLayout.NORTH);
        
        mPanel.add(pspTrackingPanel, BorderLayout.CENTER);
        pspTrackingPanel.add(pspInputPanel, BorderLayout.NORTH);
        
        pspInputPanel.add(pspEffortPanel, null);
        pspEffortPanel.add(jLabelEffort, null);
        pspEffortPanel.add(effortField, null);
        
        pspInputPanel.add(pspActualEffortPanel, null);
        pspActualEffortPanel.add(jLabelActualEffort, null);
        pspActualEffortPanel.add(actualEffortField, null);

        pspInputPanel.add(pspErrorsAddedPanel, null);
        pspErrorsAddedPanel.add(jLabelErrorsAdded, null);
        pspErrorsAddedPanel.add(errorsAddedField, null);
        
        pspInputPanel.add(pspErrorsFixedPanel, null);
        pspErrorsFixedPanel.add(jLabelErrorsFixed, null);
        pspErrorsFixedPanel.add(errorsFixedField, null);
        
        pspInputPanel.add(pspEstLOCPanel, null);
        pspEstLOCPanel.add(jLabelEstLOC, null);
        pspEstLOCPanel.add(estLOCField, null);
        
        pspInputPanel.add(pspActLOCPanel, null);
        pspActLOCPanel.add(jLabelActLOC, null);
        pspActLOCPanel.add(actLOCField, null);
                
        pspTrackingPanel.add(timePanel, BorderLayout.SOUTH);
        timePanel.add(jLabelTimestamp, BorderLayout.CENTER);   
        timePanel.add(timestampB, BorderLayout.CENTER);
        
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        areaPanel.add(jPanel8, BorderLayout.NORTH);
        jPanel8.add(todoField, null);
        jPanel8.add(jLabelDescription);
        jPanel8.add(descriptionScrollPane, null);
        areaPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel6, null);
        jPanel6.add(jLabel6, null);
        jPanel6.add(startDate, null);
        jPanel6.add(setStartDateB, null);
        jPanel2.add(jPanel1, null);
		jPanel1.add(chkEndDate, null);
        jPanel1.add(jLabel2, null);
        jPanel1.add(endDate, null);
        jPanel1.add(setEndDateB, null);
        jPanel2.add(jPanelBlank, null);
		
        jPanel2.add(jPanel4, null);
        jPanel4.add(priorityCB, null);
        jPanel4.add(jLabel8, null);
        jPanel4.add(taskColor, null);
        jPanel2.add(jPanel3, null);
        
        jPanel3.add(setNotifB, null);
        
        jLabelProgress.setText(Local.getString("Progress"));
        jPanelProgress.add(jLabelProgress, null);
        jPanelProgress.add(progress, null);
        jPanel2.add(jPanelProgress);
        
        priorityCB.setSelectedItem(Local.getString("Normal"));
        taskColor.setSelectedItem(Local.getString("None"));
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged)
                    return;
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
        
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
            getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                }
            });
        
        
    }

	public void setStartDate(CalendarDate d) {
		this.startDate.getModel().setValue(d.getDate());
	}
	
	public void setEndDate(CalendarDate d) {		
		if (d != null) 
			this.endDate.getModel().setValue(d.getDate());
	}
	
	public void setStartDateLimit(CalendarDate min, CalendarDate max) {
		this.startDateMin = min;
		this.startDateMax = max;
	}
	
	public void setEndDateLimit(CalendarDate min, CalendarDate max) {
		this.endDateMin = min;
		this.endDateMax = max;
	}
	
    void okB_actionPerformed(ActionEvent e) {
	CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
	
	/**
	* On click action for timestamp button.
	* Toggles the timestamp to start/stop and allows the user to add the clocked time to actualEffort.
	*/
	void timestampB_actionPerformed(ActionEvent e) {
        if (timestamp < 0) {
			timestamp = System.currentTimeMillis();
			Date timestampDate = new Date(timestamp);
			DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
			jLabelTimestamp.setText(Local.getString("Work began at") + ": " + formatter.format(  timestampDate));
			timestampB.setText(Local.getString("End work session"));
		} else {						
			Long workPeriod = System.currentTimeMillis() - timestamp;
			Long actualEffortFieldValue = (long) 0;
			try {
				actualEffortFieldValue = Util.getMillisFromHours(actualEffortField.getText());
			} catch (Exception ex) {
				new ExceptionDialog(ex);
			}
			// Rounds new actualEffort to two decimal places for printing.
			double newActualEffort = Math.floor((actualEffortFieldValue + workPeriod) / 1000 / 36) / 100;
			timestamp = -1;
			actualEffortField.setText(String.valueOf(newActualEffort));
			jLabelTimestamp.setText(Local.getString("Session time added to actual hours"));
			timestampB.setText(Local.getString("Begin work session"));	
		}
    }
	
	void chkEndDate_actionPerformed(ActionEvent e) {
		endDate.setEnabled(chkEndDate.isSelected());
		setEndDateB.setEnabled(chkEndDate.isSelected());
		jLabel2.setEnabled(chkEndDate.isSelected());
		if(chkEndDate.isSelected()) {
			Date currentEndDate = (Date) endDate.getModel().getValue();
			Date currentStartDate = (Date) startDate.getModel().getValue();
			if(currentEndDate.getTime() < currentStartDate.getTime()) {
				endDate.getModel().setValue(currentStartDate);
			}
		}
	}

    void setStartDateB_actionPerformed(ActionEvent e) {
        startCalFrame.setLocation(setStartDateB.getLocation());
        startCalFrame.setSize(200, 200);
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();

    }

    void setEndDateB_actionPerformed(ActionEvent e) {
        endCalFrame.setLocation(setEndDateB.getLocation());
        endCalFrame.setSize(200, 200);
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }
    
    void setNotifB_actionPerformed(ActionEvent e) {
    	((AppFrame)App.getFrame()).workPanel.dailyItemsPanel.eventsPanel.newEventB_actionPerformed(e, 
			this.todoField.getText(), (Date)startDate.getModel().getValue(),(Date)endDate.getModel().getValue());
    }

}
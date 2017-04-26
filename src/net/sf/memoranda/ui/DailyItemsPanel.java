package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.CurrentNote;
import net.sf.memoranda.NoteListener;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.History;
import net.sf.memoranda.HistoryItem;
import net.sf.memoranda.HistoryListener;
import net.sf.memoranda.Note;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
// TODO: Auto-generated Javadoc
/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: DailyItemsPanel.java,v 1.22 2005/02/13 03:06:10 rawsushi Exp $*/
public class DailyItemsPanel extends JPanel {
    
    /** The border layout 1. */
    BorderLayout borderLayout1 = new BorderLayout();
    
    /** The split pane. */
    JSplitPane splitPane = new JSplitPane();
    
    /** The split control pane. */
    JSplitPane splitControlPane = new JSplitPane();
    
    /** The control panel. */
    JPanel controlPanel = new JPanel(); /* Contains the calendar */
    
    /** The control panel 2. */
    JPanel controlPanel2 = new JPanel(); //Contains noteslist and clock
    
    /** The toggle panel. */
    JPanel togglePanel = new JPanel();
    
    /** The main panel. */
    JPanel mainPanel = new JPanel();
    
    /** The border layout 2. */
    BorderLayout borderLayout2 = new BorderLayout();
    
    /** The status panel. */
    JPanel statusPanel = new JPanel();
    
    /** The border layout 3. */
    BorderLayout borderLayout3 = new BorderLayout();
    
    /** The editors panel. */
    JPanel editorsPanel = new JPanel();
    
    /** The card layout 1. */
    CardLayout cardLayout1 = new CardLayout();
    
    /** The editor panel. */
    public EditorPanel editorPanel = new EditorPanel(this);
    
    /** The current date label. */
    JLabel currentDateLabel = new JLabel();
    
    /** The current time. */
    JLabel currentTime = new JLabel();
    
    /** The border layout 4. */
    BorderLayout borderLayout4 = new BorderLayout();
    
    /** The tasks panel. */
    TaskPanel tasksPanel = new TaskPanel(this);
    
    /** The events panel. */
    EventsPanel eventsPanel = new EventsPanel(this);
    
    /** The agenda panel. */
    AgendaPanel agendaPanel = new AgendaPanel(this);
    
    /** The contacts panel. */
    Contacts contactsPanel = new Contacts(this);
    
    /** The exp icon. */
    ImageIcon expIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_right.png"));
    
    /** The coll icon. */
    ImageIcon collIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_left.png"));
    
    /** The bookmark icon. */
    ImageIcon bookmarkIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/star8.png"));
    
    /** The expanded. */
    boolean expanded = true;

    /** The current note. */
    Note currentNote;
	
	/** The current date. */
	CalendarDate currentDate;

    /** The calendar ignore change. */
    boolean calendarIgnoreChange = false;
    
    /** The date changed by calendar. */
    boolean dateChangedByCalendar = false;
    
    /** The changed by history. */
    boolean changedByHistory = false;
    
    /** The cmain panel. */
    JPanel cmainPanel = new JPanel();
    
    /** The calendar. */
    JNCalendarPanel calendar = new JNCalendarPanel();
    
    /** The toggle tool bar. */
    JToolBar toggleToolBar = new JToolBar();
    
    /** The border layout 5. */
    BorderLayout borderLayout5 = new BorderLayout();
    
    /** The border 1. */
    Border border1;
    
    /** The toggle button. */
    JButton toggleButton = new JButton();
    
    /** The parent panel. */
    WorkPanel parentPanel = null;
    
    /** The added to history. */
    boolean addedToHistory = false;
    
    /** The indicators panel. */
    JPanel indicatorsPanel = new JPanel();
    
    /** The alarm B. */
    JButton alarmB = new JButton();
    
    /** The flow layout 1. */
    FlowLayout flowLayout1 = new FlowLayout();
    
    /** The task B. */
    JButton taskB = new JButton();
    
    /** The main tabs panel. */
    JPanel mainTabsPanel = new JPanel();
    
    /** The notes control pane. */
    NotesControlPanel notesControlPane = new NotesControlPanel();
    
    /** The agenda control pane. */
    ActivityFeedPanel agendaControlPane = new ActivityFeedPanel();
    
    /** The card layout 2. */
    CardLayout cardLayout2 = new CardLayout();
        
    /** The tasks tabbed pane. */
    JTabbedPane tasksTabbedPane = new JTabbedPane();
    
    /** The events tabbed pane. */
    JTabbedPane eventsTabbedPane = new JTabbedPane();
	
	/** The contacts tabbed pane. */
	//JTabbedPane agendaTabbedPane = new JTabbedPane();
	JTabbedPane contactsTabbedPane = new JTabbedPane();
    
    /** The border 2. */
    Border border2;

	/** The Current panel. */
	String CurrentPanel;
	
    /** The wait cursor. */
    Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

    /**
     * Instantiates a new daily items panel.
     *
     * @param _parentPanel the parent panel
     */
    public DailyItemsPanel(WorkPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
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
        border1 = BorderFactory.createEtchedBorder(Color.white, Color.gray);
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(161, 161, 161));
        this.setLayout(borderLayout1);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(null);
        splitPane.setDividerSize(2);
        splitControlPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitControlPane.setBorder(null);
        splitControlPane.setDividerSize(2);
        controlPanel.setLayout(borderLayout2);
        controlPanel2.setLayout(borderLayout5);
        togglePanel.setLayout(new BorderLayout());
        //calendar.setMinimumSize(new Dimension(200, 170));
        mainPanel.setLayout(borderLayout3);
        editorsPanel.setLayout(cardLayout1);
        statusPanel.setBackground(Color.black);
        statusPanel.setForeground(Color.white);
        statusPanel.setMinimumSize(new Dimension(14, 24));
        statusPanel.setPreferredSize(new Dimension(14, 24));
        statusPanel.setLayout(borderLayout4);
        currentDateLabel.setFont(new java.awt.Font("Dialog", 0, 16));
        currentDateLabel.setForeground(Color.white);
        currentDateLabel.setText(CurrentDate.get().getFullDateString());
        currentTime.setFont(new java.awt.Font("Dialog", 0, 16));
        currentTime.setForeground(Color.BLACK);
        currentTime.setHorizontalAlignment(SwingConstants.CENTER);
        
        Calendar now= Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute=now.get(Calendar.MINUTE);
        currentTime.setText(hour + ":" + minute);
        
        Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		        // Whatever you want to do with the time, lets say print
		        Calendar now= Calendar.getInstance();
		        int hour = now.get(Calendar.HOUR_OF_DAY);
		        int minute=now.get(Calendar.MINUTE);
		        currentTime.setText(hour + ":" + minute);
		    }
		}, 4*1000, 4*1000);
		
        borderLayout4.setHgap(4);
        controlPanel.setBackground(new Color(230, 230, 230));
        controlPanel.setBorder(border2);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setMinimumSize(new Dimension(20, 170));
        controlPanel.setPreferredSize(new Dimension(280, 170));
        controlPanel2.setBackground(new Color(230, 230, 230));
        controlPanel2.setBorder(border2);
        controlPanel2.setLayout(new BorderLayout());
        //controlPanel.setMaximumSize(new Dimension(206, 170));
        //controlPanel.setSize(controlPanel.getMaximumSize());
        calendar.setFont(new java.awt.Font("Dialog", 0, 11));
        calendar.setMinimumSize(new Dimension(0, 168));
        toggleToolBar.setBackground(new Color(215, 225, 250));
        toggleToolBar.setRequestFocusEnabled(false);
        toggleToolBar.setFloatable(false);
        cmainPanel.setLayout(borderLayout5);
        cmainPanel.setBackground(SystemColor.desktop);
        cmainPanel.setMinimumSize(new Dimension(0, 168));
        cmainPanel.setOpaque(false);
        toggleButton.setMaximumSize(new Dimension(32767, 32767));
        toggleButton.setMinimumSize(new Dimension(16, 16));
        toggleButton.setOpaque(false);
        toggleButton.setPreferredSize(new Dimension(16, 16));
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        toggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleButton_actionPerformed(e);
            }
        });
        toggleButton.setIcon(collIcon);
        indicatorsPanel.setOpaque(false);
        indicatorsPanel.setLayout(flowLayout1);
        alarmB.setMaximumSize(new Dimension(24, 24));
        alarmB.setOpaque(false);
        alarmB.setPreferredSize(new Dimension(24, 24));
        alarmB.setToolTipText(Local.getString("Active events"));
        alarmB.setBorderPainted(false);
        alarmB.setMargin(new Insets(0, 0, 0, 0));
        alarmB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarmB_actionPerformed(e);
            }
        });
        alarmB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/alarm.png")));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setVgap(0);
        taskB.setMargin(new Insets(0, 0, 0, 0));
        taskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                taskB_actionPerformed(e);
            }
        });
        taskB.setPreferredSize(new Dimension(24, 24));
        taskB.setToolTipText(Local.getString("Active to-do tasks"));
        taskB.setBorderPainted(false);
        taskB.setMaximumSize(new Dimension(24, 24));
        taskB.setOpaque(false);
        taskB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/task.png")));

        notesControlPane.setFont(new java.awt.Font("Dialog", 1, 10));
        mainTabsPanel.setLayout(cardLayout2);
        this.add(splitPane, BorderLayout.CENTER);

        controlPanel.add(cmainPanel, BorderLayout.CENTER);
        cmainPanel.add(calendar, BorderLayout.CENTER);
        controlPanel2.add(currentTime, BorderLayout.SOUTH);

        mainPanel.add(statusPanel, BorderLayout.NORTH);
        statusPanel.add(currentDateLabel, BorderLayout.CENTER);
        statusPanel.add(indicatorsPanel, BorderLayout.EAST);

        mainPanel.add(editorsPanel, BorderLayout.CENTER);
        
        editorsPanel.add(agendaPanel, "AGENDA");
        editorsPanel.add(eventsPanel, "EVENTS");
        editorsPanel.add(tasksPanel, "TASKS");
        editorsPanel.add(editorPanel, "NOTES");
        editorsPanel.add(contactsPanel, "CONTACTS");
        
        splitControlPane.add(controlPanel, JSplitPane.TOP);
        splitPane.add(mainPanel, JSplitPane.RIGHT);
        splitPane.add(splitControlPane, JSplitPane.LEFT);
        //splitPane.add(controlPanel, JSplitPane.LEFT);
        togglePanel.add(toggleToolBar, BorderLayout.SOUTH);
        toggleToolBar.add(toggleButton, null);

        splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
        splitControlPane.setDividerLocation(170);
        //splitPane.setResizeWeight(0.0);
        
        splitControlPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
        	    new PropertyChangeListener() {
        	        @Override
        	        public void propertyChange(PropertyChangeEvent pce) {
        	        	calendar.jnCalendar.setRowHeight((int)(splitControlPane.getDividerLocation()-74)/6);
        	        }
        	});
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                currentDateChanged(d);
            }
        });

        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl) {
//            	Util.debug("DailyItemsPanel Project Listener: Project is going to be changed!");				
//            	Util.debug("current project is " + CurrentProject.get().getTitle());

            	currentProjectChanged(p, nl, tl, rl);
            }
            public void projectWasChanged() {
//            	Util.debug("DailyItemsPanel Project Listener: Project has been changed!");            	
//            	Util.debug("current project is " + CurrentProject.get().getTitle());
            	
            	// cannot save note here, changing to new project
            	currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
        		CurrentNote.set(currentNote,false);
                editorPanel.setDocument(currentNote);        
                
//                // DEBUG
//                if (currentNote != null) {
//                    Util.debug("currentNote has been set to " + currentNote.getTitle());        	
//                }
//                else {
//                    Util.debug("currentNote has been set to null");
//                }
//                // DEBUG
            }
        });

        CurrentNote.addNoteListener(new NoteListener() {
            public void noteChange(Note note, boolean toSaveCurrentNote) {
                currentNoteChanged(note, toSaveCurrentNote);
            }
        });
		
        calendar.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (calendarIgnoreChange)
                    return;
                dateChangedByCalendar = true;
                CurrentDate.set(calendar.get());
                dateChangedByCalendar = false;
            }
        });

        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editorPanel.isDocumentChanged()) {
                    saveNote();
                    CurrentStorage.get().storeNoteList(CurrentProject.getNoteList(), CurrentProject.get());
                }
            }
        });

        History.addHistoryListener(new HistoryListener() {
            public void historyWasRolledTo(HistoryItem hi) {
                historyChanged(hi);
            }
        });

        EventsScheduler.addListener(new EventNotificationListener() {
            public void eventIsOccured(net.sf.memoranda.Event ev) {
                /*DEBUG*/
                System.out.println(ev.getTimeString() + " " + ev.getText());
                updateIndicators();
            }

            public void eventsChanged() {
                updateIndicators();
            }
        });

		currentDate = CurrentDate.get();
        currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
		CurrentNote.set(currentNote,true);
        editorPanel.setDocument(currentNote);
        History.add(new HistoryItem(CurrentDate.get(), CurrentProject.get()));
        //cmainPanel.add(mainTabsPanel, BorderLayout.CENTER);
        splitControlPane.add(togglePanel, JSplitPane.BOTTOM);
        togglePanel.add(controlPanel2, BorderLayout.CENTER);
        controlPanel2.add(mainTabsPanel, BorderLayout.CENTER);
        mainTabsPanel.add(eventsTabbedPane, "EVENTSTAB");
        mainTabsPanel.add(tasksTabbedPane, "TASKSTAB");
        mainTabsPanel.add(notesControlPane, "NOTESTAB");
		mainTabsPanel.add(agendaControlPane, "AGENDATAB");
		mainTabsPanel.add(contactsTabbedPane, "CONTACTSTAB");
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
        mainPanel.setBorder(null);
        calendar.setParentPanel(parentPanel);//set calendar menu to default parentPanel.
    }


    /**
     * Current date changed.
     *
     * @param newdate the newdate
     */
    void currentDateChanged(CalendarDate newdate) {
        Cursor cur = App.getFrame().getCursor();
        App.getFrame().setCursor(waitCursor);
        if (!changedByHistory) {
           History.add(new HistoryItem(newdate, CurrentProject.get()));
		}
        if (!dateChangedByCalendar) {
            calendarIgnoreChange = true;
            calendar.set(newdate);
            calendarIgnoreChange = false;
        }

        /*if ((currentNote != null) && !changedByHistory && !addedToHistory)
                            History.add(new HistoryItem(currentNote));*/
		currentNoteChanged(currentNote,true);
		currentNote = CurrentProject.getNoteList().getNoteForDate(newdate);
 		CurrentNote.set(currentNote,true);
		currentDate = CurrentDate.get();

        /*addedToHistory = false;
        if (!changedByHistory) {
            if (currentNote != null) {
                History.add(new HistoryItem(currentNote));
                addedToHistory = true;
            }
        }*/

		currentDateLabel.setText(newdate.getFullDateString());
        if ((currentNote != null) && (currentNote.isMarked())) {
            currentDateLabel.setIcon(bookmarkIcon);
            currentDateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        }
        else {
            currentDateLabel.setIcon(null);
        }		

        updateIndicators(newdate, CurrentProject.getTaskList());
        App.getFrame().setCursor(cur);
    }

	/**
	 * Current note changed.
	 *
	 * @param note the note
	 * @param toSaveCurrentNote the to save current note
	 */
	void currentNoteChanged(Note note, boolean toSaveCurrentNote) {
//		Util.debug("currentNoteChanged");
		
		if (editorPanel.isDocumentChanged()) {
			if (toSaveCurrentNote) {
	            saveNote();				
			}
			notesControlPane.refresh();
			agendaControlPane.refresh();
        }
		currentNote = note;
		editorPanel.setDocument(currentNote);
        calendar.set(CurrentDate.get());
		editorPanel.editor.requestFocus();		
	}
	
    /**
     * Current project changed.
     *
     * @param newprj the newprj
     * @param nl the nl
     * @param tl the tl
     * @param rl the rl
     */
    void currentProjectChanged(Project newprj, NoteList nl, TaskList tl, ResourcesList rl) {
//		Util.debug("currentProjectChanged");

        Cursor cur = App.getFrame().getCursor();
        App.getFrame().setCursor(waitCursor);
        if (!changedByHistory)
            History.add(new HistoryItem(CurrentDate.get(), newprj));
        if (editorPanel.isDocumentChanged())
            saveNote();
        /*if ((currentNote != null) && !changedByHistory && !addedToHistory)
                    History.add(new HistoryItem(currentNote));*/
        CurrentProject.save();        
        
        /*addedToHistory = false;
        if (!changedByHistory) {
            if (currentNote != null) {
                History.add(new HistoryItem(currentNote));
                addedToHistory = true;
            }
        }*/
        
        updateIndicators(CurrentDate.get(), tl);
        App.getFrame().setCursor(cur);
    }

    /**
     * History changed.
     *
     * @param hi the hi
     */
    void historyChanged(HistoryItem hi) {
        changedByHistory = true;
        CurrentProject.set(hi.getProject());
        CurrentDate.set(hi.getDate());
        changedByHistory = false;
    }

    /**
     * Save note.
     */
    public void saveNote() {
        if (currentNote == null)
            currentNote = CurrentProject.getNoteList().createNoteForDate(currentDate);
        currentNote.setTitle(editorPanel.titleField.getText());
		currentNote.setId(Util.generateId());
		currentNote.setEdit(new Timestamp(Calendar.getInstance().getTimeInMillis()));//Save time stamp to note.
        CurrentStorage.get().storeNote(currentNote, editorPanel.getDocument());
        /*DEBUG* System.out.println("Save");*/
    }

    /**
     * Toggle button action performed.
     *
     * @param e the e
     */
    void toggleButton_actionPerformed(ActionEvent e) {
        if (expanded) {
            expanded = false;
            toggleButton.setIcon(expIcon);
            controlPanel.remove(cmainPanel);
            controlPanel.setBackground(new Color(215, 225, 250));
            togglePanel.remove(toggleToolBar);
            togglePanel.add(toggleToolBar, BorderLayout.EAST);
            splitPane.setDividerLocation((int) controlPanel.getMinimumSize().getWidth());

        }
        else {
            expanded = true;
            controlPanel.setBackground(new Color(230, 230, 230));
            controlPanel.add(cmainPanel, BorderLayout.CENTER);
            toggleButton.setIcon(collIcon);
            togglePanel.remove(toggleToolBar);
            togglePanel.add(toggleToolBar, BorderLayout.SOUTH);
            splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
        }
    }

    /**
     * Update indicators.
     *
     * @param date the date
     * @param tl the tl
     */
    public void updateIndicators(CalendarDate date, TaskList tl) {
        indicatorsPanel.removeAll();
        if (date.equals(CalendarDate.today())) {
            if (tl.getActiveSubTasks(null,date).size() > 0)
                indicatorsPanel.add(taskB, null);
            if (EventsScheduler.isEventScheduled()) {
                /*String evlist = "";
                for (Iterator it = EventsScheduler.getScheduledEvents().iterator(); it.hasNext();) {
                    net.sf.memoranda.Event ev = (net.sf.memoranda.Event)it.next();   
                    evlist += ev.getTimeString()+" - "+ev.getText()+"\n";
                } */
                net.sf.memoranda.Event ev = EventsScheduler.getFirstScheduledEvent();
                alarmB.setToolTipText(ev.getTimeString() + " - " + ev.getText());
                indicatorsPanel.add(alarmB, null);
            }
        }
        indicatorsPanel.updateUI();
    }

    /**
     * Update indicators.
     */
    public void updateIndicators() {
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
    }

    /**
     * Select panel.
     *
     * @param pan the pan
     */
    public void selectPanel(String pan) {
        if (calendar.jnCalendar.renderer.getTask() != null) {
            calendar.jnCalendar.renderer.setTask(null);
         //   calendar.jnCalendar.updateUI();
        }
        if (pan.equals("TASKS") && (tasksPanel.taskTable.getSelectedRow() > -1)) {
            Task t =
                CurrentProject.getTaskList().getTask(
                    tasksPanel
                        .taskTable
                        .getModel()
                        .getValueAt(tasksPanel.taskTable.getSelectedRow(), TaskTable.TASK_ID)
                        .toString());
            calendar.jnCalendar.renderer.setTask(t);
       //     calendar.jnCalendar.updateUI();
        }
        boolean isAg = pan.equals("AGENDA");
        //boolean isContacts = pan.equals("CONTACTS");
        agendaPanel.setActive(isAg);
        if (isAg){
        	agendaPanel.refresh(CurrentDate.get());
        	agendaControlPane.refresh();
        }
        cardLayout1.show(editorsPanel, pan);
        cardLayout2.show(mainTabsPanel, pan + "TAB");
		calendar.jnCalendar.updateUI();
		CurrentPanel=pan;
		calendar.setParentPanel(parentPanel);
    }

	/**
	 * Gets the current panel.
	 *
	 * @return the current panel
	 */
	public String getCurrentPanel() {
		return CurrentPanel;
	}
    
    /**
     * Task B action performed.
     *
     * @param e the e
     */
    void taskB_actionPerformed(ActionEvent e) {
        parentPanel.tasksB_actionPerformed(null);
    }

    /**
     * Alarm B action performed.
     *
     * @param e the e
     */
    void alarmB_actionPerformed(ActionEvent e) {
        parentPanel.eventsB_actionPerformed(null);
    }
}

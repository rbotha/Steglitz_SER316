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
/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: DailyItemsPanel.java,v 1.22 2005/02/13 03:06:10 rawsushi Exp $*/
public class DailyItemsPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();
    JSplitPane splitControlPane = new JSplitPane();
    JPanel controlPanel = new JPanel(); /* Contains the calendar */
    JPanel controlPanel2 = new JPanel(); //Contains noteslist and clock
    JPanel togglePanel = new JPanel();
    JPanel mainPanel = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel statusPanel = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel editorsPanel = new JPanel();
    CardLayout cardLayout1 = new CardLayout();
    public EditorPanel editorPanel = new EditorPanel(this);
    JLabel currentDateLabel = new JLabel();
    JLabel currentTime = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();
    TaskPanel tasksPanel = new TaskPanel(this);
    EventsPanel eventsPanel = new EventsPanel(this);
    AgendaPanel agendaPanel = new AgendaPanel(this);
    Contacts contactsPanel = new Contacts(this);
    CalendarPanel calendarPanel = new CalendarPanel();
    ImageIcon expIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_right.png"));
    ImageIcon collIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_left.png"));
    ImageIcon bookmarkIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/star8.png"));
    boolean expanded = true;

    Note currentNote;
	CalendarDate currentDate;

    boolean calendarIgnoreChange = false;
    boolean dateChangedByCalendar = false;
    boolean changedByHistory = false;
    JPanel cmainPanel = new JPanel();
    JNCalendarPanel calendar = new JNCalendarPanel();
    JToolBar toggleToolBar = new JToolBar();
    BorderLayout borderLayout5 = new BorderLayout();
    Border border1;
    JButton toggleButton = new JButton();
    WorkPanel parentPanel = null;
    
    boolean addedToHistory = false;
    JPanel indicatorsPanel = new JPanel();
    JButton alarmB = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton taskB = new JButton();
    JPanel mainTabsPanel = new JPanel();
    NotesControlPanel notesControlPane = new NotesControlPanel();
    ActivityFeedPanel agendaControlPane = new ActivityFeedPanel();
    CardLayout cardLayout2 = new CardLayout();
        
    JTabbedPane tasksTabbedPane = new JTabbedPane();
    JTabbedPane eventsTabbedPane = new JTabbedPane();
	JTabbedPane calendarTabbedPane = new JTabbedPane();
	JTabbedPane contactsTabbedPane = new JTabbedPane();
    Border border2;

	String CurrentPanel;
	
    Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

    public DailyItemsPanel(WorkPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
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
        editorsPanel.add(calendarPanel, "CALENDAR");
        
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
                //Keeps calendartab calendar sync with calendar
                calendarPanel.cal.set(CurrentDate.get());
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
		mainTabsPanel.add(calendarTabbedPane, "CALENDARTAB");
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
        mainPanel.setBorder(null);
        calendarPanel.setParent(parentPanel);
        calendar.setParentPanel(parentPanel);//set calendar menu to default parentPanel.
    }


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

    void historyChanged(HistoryItem hi) {
        changedByHistory = true;
        CurrentProject.set(hi.getProject());
        CurrentDate.set(hi.getDate());
        changedByHistory = false;
    }

    public void saveNote() {
        if (currentNote == null)
            currentNote = CurrentProject.getNoteList().createNoteForDate(currentDate);
        currentNote.setTitle(editorPanel.titleField.getText());
		currentNote.setId(Util.generateId());
		currentNote.setEdit(new Timestamp(Calendar.getInstance().getTimeInMillis()));//Save time stamp to note.
        CurrentStorage.get().storeNote(currentNote, editorPanel.getDocument());
        /*DEBUG* System.out.println("Save");*/
    }

    void toggleButton_actionPerformed(ActionEvent e) {
        if (expanded) {
            expanded = false;
            toggleButton.setIcon(expIcon);
            cmainPanel.setVisible(false);
            controlPanel.setBackground(new Color(215, 225, 250));
            togglePanel.remove(toggleToolBar);
            togglePanel.add(toggleToolBar, BorderLayout.EAST);
            splitPane.setDividerLocation((int) controlPanel.getMinimumSize().getWidth());

        }
        else {
            expanded = true;
            controlPanel.setBackground(new Color(230, 230, 230));
            cmainPanel.setVisible(true);
            toggleButton.setIcon(collIcon);
            togglePanel.remove(toggleToolBar);
            togglePanel.add(toggleToolBar, BorderLayout.SOUTH);
            splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
        }
    }

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

    public void updateIndicators() {
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
    }

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
        splitControlPane.setVisible(true);
        splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
        if(pan.equals("CALENDAR")){
        	//If history is called or if out of sync
        	calendarPanel.cal.set(CurrentDate.get());//Sync
        	splitControlPane.setVisible(false);
        }
        cardLayout1.show(editorsPanel, pan);
        cardLayout2.show(mainTabsPanel, pan + "TAB");
		calendar.jnCalendar.updateUI();
		CurrentPanel=pan;
    }

	public String getCurrentPanel() {
		return CurrentPanel;
	}
    void taskB_actionPerformed(ActionEvent e) {
        parentPanel.tasksB_actionPerformed(null);
    }

    void alarmB_actionPerformed(ActionEvent e) {
        parentPanel.eventsB_actionPerformed(null);
    }
}

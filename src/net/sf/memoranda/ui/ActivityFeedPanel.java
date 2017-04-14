package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Note;
import net.sf.memoranda.Task;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/**
 * JPanel class for creating activity feed on recently created or updated items.
 * Apr 5, 2017
 * @author Jesus Rodriguez Jr
 *
 */
@SuppressWarnings({ "rawtypes", "serial","unchecked" })
public class ActivityFeedPanel extends JPanel{
	
	//Create Arrays of Notes and Task to be Initialized later.
	private ArrayList<Note> note = null;
	private ArrayList<Task> task = null;
	private ActivityFeed activityFeed = new ActivityFeed();
	//JScrollPane to hold "model" and "time" Jlist
	private JScrollPane feed = new JScrollPane();
	private JLabel recentLabel = null;
	//Holds actual list of last edited task and notes
	private DefaultListModel name = new DefaultListModel();
	private DefaultListModel time = new DefaultListModel();
	private Border border1;//Default border
	
	//Default Constructor, calls execution method
	public ActivityFeedPanel(){
		jbInit();
	}//End of Contstructor
	
	//Create activity feed and show recently edited items.
	private void jbInit()
	{//Beginning of method
		//Set Border
		border1 = BorderFactory.createEtchedBorder(Color.white, Color.gray);
		//Set container panel
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.setBorder(border1);
		this.setVisible(true);
		
		//Initialize List
		JList<String> recentItems = new JList<String>(name);
		JList<String> recentTime = new JList<String>(time);
		
		//Set panels and list
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.setBackground(Color.WHITE);
		recentItems.setLayout(new BorderLayout());
		recentItems.setBorder(new EmptyBorder(10,10,10,10));
		recentTime.setLayout(new BorderLayout());
		recentTime.setBorder(new EmptyBorder(10,10,10,15));
		recentLabel = new JLabel(Local.getString("Recent Activities"), SwingConstants.CENTER);
		recentLabel.setPreferredSize(new Dimension(170, 20));
				
		//Execute Methods
		if(createList())//if true
			Util.debug("List Created");//create recent feeds list
		
		
		

		//Add lists to panels
		controlPanel.add(recentItems, BorderLayout.WEST);
		controlPanel.add(recentTime, BorderLayout.CENTER);
		add(recentLabel, BorderLayout.NORTH);
		add(feed, BorderLayout.CENTER);
		feed.getViewport().add(controlPanel, null);
	
	}//End of method
	

	
	/**
	 * This method is for refreshing the list to display recently edited items
	 */
	public boolean refresh()
	{
		//Call method to reset lists.
		if (!createList())
			return false;
		
		Util.debug("List Updated.");
		return true;
		

	}//End of public method
	
	/*
	 * Method for adding all task and notes by name and timestamp to activity feed object.
	 * Clears all arrays.
	 * 
	 */
	private boolean createList(){
		//Clear all arrays.
		time.clear();
		name.clear();
		activityFeed.clear();
		
		try{
			
			task = new ArrayList<Task>(CurrentProject.getTaskList().getAllTask());
			note =  new ArrayList<Note>(CurrentProject.getNoteList().getAllNotes());
					
			for (Task t: task){
				if(t.getText()!=null&&t.getEdit()!=null)
					activityFeed.add(t.getText(), t.getEdit().toString());
			}
			for(Note n: note){
				if(n.getTitle()!= null&&n.getEdit()!=null)
					activityFeed.add(n.getTitle(), n.getEdit().toString());
			}

			Iterator listItr = activityFeed.getOrderedByDate().iterator();
			
			while(listItr.hasNext()){
				name.addElement(listItr.next().toString());
				if(listItr.hasNext())
					time.addElement(listItr.next().toString());
			}

		}catch(Exception e){
			Util.debug(e.getMessage() + " Exception: "+ e.getCause());
			return false;
		}	
		
		return true;
	}
}//End of Class 



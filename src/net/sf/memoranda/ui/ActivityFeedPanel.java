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

/**
 * Class for creating an activity feed on recent items that have been edited within the past 2(two) days.
 * Apr 5, 2017
 * @author Jesus Rodriguez Jr
 *
 */
@SuppressWarnings({ "rawtypes", "serial","unchecked" })
public class ActivityFeedPanel extends JPanel{
	
	//Create Arrays of Notes and Task to be Initialized later.
	private ArrayList<Note> note = null;
	private ArrayList<Task> task = null;
	//JScrollPane to hold "model" and "time" Jlist
	private JScrollPane feed = new JScrollPane();
	private JLabel recentLabel = null;
	//Holds actual list of last edited task and notes
	private DefaultListModel model = new DefaultListModel();
	private DefaultListModel time = new DefaultListModel();
	private ArrayList<Timestamp> list = new ArrayList<Timestamp>();
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
		JList<String> recentItems = new JList<String>(model);
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
			recentActivitiesFeed();//create recent feeds list
		
		
		

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
		if (!createList()||!recentActivitiesFeed())
			return false;
		
		
		return true;
		

	}//End of public method
	
	
	//Sort through Tasks/Notes to display recent changes.
	private boolean recentActivitiesFeed()
	{//Beginning of private method
		int index = 0;
	
		try{
			//Create temp arrays with empty spaces to be populated
			ArrayList<String> temp = new ArrayList<>(Collections.nCopies(list.size(),"empty"));
			ArrayList<String> temp2 = new ArrayList<>(Collections.nCopies(list.size(),"empty"));
					
			//Create day formatter
			DateFormat day = new SimpleDateFormat("D");
			
			//Get current day from calendar date, 1-365
			int tc = Integer.parseInt(day.format(Calendar.getInstance().getTime()));
			
			//Get all Notes and add appropriate time statments
			for(Note n: note){
				int ni = 0;
				//Get day difference betweek updates
				if(n.getEdit()!=null)
					ni = tc - Integer.parseInt(day.format(n.getEdit()));		
				
				//If note Timestamp is found pass index to find correct spot
				if((index = list.indexOf(n.getEdit()))>=0&&ni<=1){//If day is not over
					temp.set(index,n.getTitle());//set index to name
					temp2.set(index,getTimeDiff(n.getEdit(),ni));//set inex to time difference
				}
			}//End of For Each Loop
			
			//Place all task in order of Timestamp
			for(Task t: task){
				int ti = 0;
				//Get day difference between updates
				if(t.getEdit()!=null)
					ti = tc - Integer.parseInt(day.format(t.getEdit()));
				
				//If task Timestamp is found pass index to find correct spot
				if((index = list.indexOf(t.getEdit()))>=0 && ti<=1){
					temp.set(index,t.getText());//set index to name
					temp2.set(index,getTimeDiff(t.getEdit(),ti));//set index to time difference
				}
	
			}//End of for each loop
			
			//Clear all empty spaces in temp arrays
			temp.removeAll(Collections.singleton("empty"));
			temp2.removeAll(Collections.singleton("empty"));
			
			//Add names and timeframs to lists
			for(String act: temp){//Iterate through array
				model.addElement(act);//add name to list
			}
			for(String ti: temp2){//Iterate through array
				time.addElement(ti);//add time to list
			}
		}catch(Exception e){
			System.out.println(e.getMessage() + " Exception: "+ e.getCause());
			return false;
		}
		return true;

	}//End of private method
	
	//MEthod for getting time difference of recent change compared to current time
	@SuppressWarnings("deprecation")
	private String getTimeDiff(Timestamp s, int day)
	{//Beginning of private method
		
		//Variables
		String msg = "";
		int hr = 0;
		int min = 0;
		
		//get current time
		Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());	
		DateFormat yearTime = new SimpleDateFormat("h:mm a");
		//Current hour minus hour being checked
		if(currentTime.getHours()>s.getHours())
			hr = currentTime.getHours() - s.getHours();
		else
			hr = s.getHours()-currentTime.getHours();
					
		//Get minutes between current time and time being checked
		if(currentTime.getMinutes() < s.getMinutes()&& hr==1){
			min = (currentTime.getMinutes()+60)-s.getMinutes();
			hr = 0;
			}
		else
			min = currentTime.getMinutes() - s.getMinutes();
		//If same day gather total hours and minutes
		if(day == 0){
			
			//if just recently updated show 
			if(hr == 0&&min==0){
				msg = Local.getString("Edited just now");
			}
			else if(hr == 0){//if updated within that hour
				msg = Local.getString("Edited "+min+"min ago.");
			}
			else//updated during that day timeframe
				msg = Local.getString("Edited "+hr+"hr ago");
		}//End if
		//If updated a day ago
		else if(day == 1){
			msg = "Edited yesterday at "+yearTime.format(s);
		}//end if

		//Return msg
		return msg;
	}//End of private method
	
	/*
	 * This is a private method for getting all task, notes and all Timestamps.
	 * Clears all arrays if updating.
	 */
	private boolean createList(){
		//Clear all arrays.
		list.clear();
		time.clear();
		model.clear();
		
		try{
			//Get all current projects task and notes
			Timestamp ts = null;
			task = new ArrayList<Task>(CurrentProject.getTaskList().getAllTask());
			note =  new ArrayList<Note>(CurrentProject.getNoteList().getAllNotes());
			//Get task and note iterator
			Iterator<Task> itr1 = task.iterator();
			Iterator<Note> itr2 = note.iterator();
			//Iterate through all task and notes 
			while(itr1.hasNext()||itr2.hasNext()){
				//If task Exist
				if(itr1.hasNext()){//If has next task
					ts = itr1.next().getEdit();//get timestamp
					if(ts!=null){//if null do nothing
						list.add(ts);
					}//End nested if
				}//End if
				//If Note Exist
				if(itr2.hasNext()){//if has next note
					ts = itr2.next().getEdit();//get timestamp
					if(ts!=null){//if null do nothing
						list.add(ts);
					}//End nested if
				}//End if
			}//End while
			
			//Sort Timestamps from least to greatest.
			Collections.sort(list, Collections.reverseOrder());
		}catch(Exception e){
			System.out.println(e.getMessage() + " Exception: "+ e.getCause());
			return false;
		}
		
		return true;
	}
}//End of Class 



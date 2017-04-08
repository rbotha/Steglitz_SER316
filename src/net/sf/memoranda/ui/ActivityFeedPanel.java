/**
 * 
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Note;
import net.sf.memoranda.Task;
import net.sf.memoranda.util.Local;

/**
 * Class for creating an activity feed on recent items that have been edited
 * Apr 5, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class ActivityFeedPanel extends JPanel{
	ArrayList<Note> note = null;
	ArrayList<Task> task = null;
	
	JScrollPane feed = new JScrollPane();
	JPanel datePanel = new JPanel();
	
	JLabel recentLabel = null;
	
	Border border1;
	
	//Default Constructor
	
	//Single parameter constructor
	ActivityFeedPanel(){
		jbInit();
	}//End of Contstructor
	
	//Create activity feed and show recently edited items.
	public void jbInit()
	{//Beginning of method
		border1 = BorderFactory.createEtchedBorder(Color.white, Color.gray);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.setBorder(border1);
		this.setVisible(true);
		
		DefaultListModel model = new DefaultListModel();
		JList<String> recentItems = new JList<String>(model);
		ArrayList<Timestamp> list = new ArrayList<Timestamp>();
		
		datePanel.setBorder(border1);
		datePanel.setLayout(new BorderLayout());
		datePanel.setBackground(new Color(230, 230, 230));
		datePanel.setPreferredSize(new Dimension(205, 170));
		//Get Current date
		//currentDate = new JLabel(Local.getString(_parentPanel.calendar.get().getFullDateString()), SwingConstants.CENTER);
		//currentDate.setPreferredSize(new Dimension(170, 20));
		recentLabel = new JLabel(Local.getString("Recently Edited"), SwingConstants.CENTER);
		recentLabel.setPreferredSize(new Dimension(170, 20));

		task = new ArrayList<Task>(CurrentProject.getTaskList().getAllTask());
		note =  new ArrayList<Note>(CurrentProject.getNoteList().getAllNotes());
				
		for(Task t: task)
			if(t.getEdit()!=null)
				list.add(t.getEdit());
	
		for(Note n: note)
			if(n.getEdit()!=null)
				list.add(n.getEdit());

		Collections.sort(list);

		for(String t:  recentActivitiesFeed(list))
			model.addElement(t);
		
		add(recentLabel, BorderLayout.NORTH);
		add(feed, BorderLayout.CENTER);
		feed.getViewport().add(recentItems, BorderLayout.WEST);
	
	}//End of method
	
	//Sort through Tasks/Notes to display recent changes.
	private ArrayList<String> recentActivitiesFeed(ArrayList<Timestamp> lst)
	{//Beginning of private method
		
		//Sorted temp Collection
		ArrayList<String> today = new ArrayList<String>();
		ArrayList<String> yesterday = new ArrayList<String>();
		ArrayList<String> lastWeek = new ArrayList<String>();
		DateFormat day = new SimpleDateFormat("D");
		
		//Iterate through String array and find task/note associated with timestamp
		for(int i = lst.size()-1; i>0; i--)
		{
			int tc = Integer.parseInt(day.format(Calendar.getInstance().getTime()));
			
			System.out.println(Local.getTimeString(new Date(lst.get(i).getTime()))+ " date: "+ Local.getDateString(new Date(lst.get(i).getTime()), 3));
			for(Note n: note){			
				if(n.getEdit()!=null&& n.getEdit().equals(lst.get(i))){
					int ni = tc - Integer.parseInt(day.format(n.getEdit()));
					if(ni == 0)
						today.add(String.format(" %-20s %30s", n.getTitle(),getTimeDiff(n.getEdit(),ni)));
					else if(ni ==1)
						yesterday.add(String.format(" %-20s %30s", n.getTitle(),getTimeDiff(n.getEdit(),ni)));
					else if(ni >= 7 && ni<=14)
						lastWeek.add(String.format(" %-20s %30s", n.getTitle(),getTimeDiff(n.getEdit(),ni)));
				}
			}
			
			for(Task t: task){
				
				if(t.getEdit()!=null && t.getEdit().equals(lst.get(i))){
					int ti = tc - Integer.parseInt(day.format(t.getEdit()));
					if(ti == 0)
						today.add(String.format(" %-20s %30s",t.getText(),getTimeDiff(t.getEdit(),ti)));
					else if(ti ==1)
						yesterday.add(String.format(" %-20s %30s",t.getText(),getTimeDiff(t.getEdit(),ti)));
					else if(ti >= 7 && ti<=14)
						lastWeek.add(String.format(" %-20s %30s",t.getText(),getTimeDiff(t.getEdit(),ti)));
				}
			}
		}
		//Gather Collection
		today.addAll(yesterday);
		
		
		return today;

	}//End of private method
	
	//MEthod for getting time difference of recent change compared to current time
	private String getTimeDiff(Timestamp s, int day)
	{//Beginning of private method
		
		String msg = "";
		int hr = 0;
		int min = 0;
		//get current time
		Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		//Get date formats
		DateFormat ct = new SimpleDateFormat("HH:mm");
		//DateFormat day = new SimpleDateFormat("D");
		DateFormat hour = new SimpleDateFormat("HH");
		DateFormat minute = new SimpleDateFormat("mm");

		
		
		//If same day gather total hours and minutes
		if(day == 0){
			hr = currentTime.getHours() - s.getHours();
			if(currentTime.getMinutes() < s.getMinutes())
				min = (currentTime.getMinutes()+60) - s.getMinutes();
			else
				min = currentTime.getMinutes() - s.getMinutes();
			if(hr == 0&&min==0)
				msg = Local.getString(" Edited just now");
			else if(hr == 1 && min < 59)
				msg = Local.getString(" Edited "+min+"min ago.");
			else
				msg = Local.getString(" Edited "+hr+"hr ago");
		}
		else if(day ==1)
			msg = " Edited yesterday at "+Local.getTimeString(new Date(s.getTime()));
		else if(day >= 7)
			msg = " Edited last week on "+Local.getDateString(new Date(s.getTime()),3);
			
		return msg;
	}//End of private method
	
	
	//Method for refreshing last items edited.
	public void refresh()
	{
		//Clear Pane
		this.removeAll();
	
		//Call method
		jbInit();
	}
}


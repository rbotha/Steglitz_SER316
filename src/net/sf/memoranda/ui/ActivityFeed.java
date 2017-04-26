/**
 * 
 */
package net.sf.memoranda.ui;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

// TODO: Auto-generated Javadoc
/**
 * This class creates a collection of name and time strings.
 * It allows the ordering of the collection by its timestamp.
 * Apr 14, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class ActivityFeed {
	
	/** The name. */
	private ArrayList<String> name = null;
	
	/** The time. */
	private ArrayList<String> time = null;
	
	/**
	 * Default ConstructorConstructor.
	 */
	public ActivityFeed(){
		this.name = new ArrayList<String>();
		this.time = new ArrayList<String>();
	}
	
	/**
	 * Two Parameter constructor.
	 *
	 * @param name the name
	 * @param time the time
	 */
	public ActivityFeed(ArrayList<String> name, ArrayList<String> time){
		
		if(name==null||time==null){
			this.name = new ArrayList<String>();
			this.time = new ArrayList<String>();
		}
		else{
			this.name = new ArrayList<String>(name);
			this.time = new ArrayList<String>(time);;
		}
	}
	
	//Getters
	/**
	 * Method for getting array of names.
	 *
	 * @return name
	 */
	public final ArrayList<String> getNames(){
		if(this.name!=null)
			return new ArrayList<String>(this.name);
		
		return null;
	}
	
	/**
	 * Method for getting array of times.
	 *
	 * @return time
	 */
	public ArrayList<String> getTimes(){
		if(this.time!=null)
			return new ArrayList<String>(this.time);
		
		return null;
	}
	
	/**
	 * Method for getting a given name within an index location of Array.
	 *
	 * @param index the index
	 * @return name, if null return string "NULL"
	 */
	public String getName(int index){

		if(index>this.name.size()||index<0)
			return null;
		if(this.name.get(index)==null)
			return null;
		
		return Local.getString(this.name.get(index));
	}
	
	/**
	 * Method for getting a given time within an index location of Array.
	 *
	 * @param index the index
	 * @return name, if null return string "NULL"
	 */
	public String getTime(int index){
		if(index>this.time.size()||index<0)
			return null;
		if(this.time.get(index)==null)
			return null;
		
		return Local.getString(this.time.get(index));
	}
	
	//Setters
	/**
	 * Method for setting time within an index location.
	 *
	 * @param index - Integer index location to place name
	 * @param name - String name of new location
	 */
	public void setName(int index, String name){
		if(name!=null&&(index<this.name.size()&&index>=0)){
			this.name.add(index, name);
			this.time.add(index, "NULL");
		}
	}
	
	/**
	 * Method for setting time within an index location.
	 *
	 * @param index - Integer index location to place name
	 * @param time - String time of new location
	 */
	public void setTime(int index, String time){
		if(time!=null&&(index<this.time.size()&&index>=0)){
			this.time.add(index, time);
			this.name.add(index, "NULL");
		}
	}
	
	/**
	 * Method for adding the time and name from an index location.
	 *
	 * @param index - Integer index of location to place params
	 * @param name - String name
	 * @param time - String time, if cannot be converted to timestamp throw exception
	 */
	public void add(int index, String name, String time){
		try{
			Timestamp.valueOf(time);
			
			if((name!=null&&time!=null)&&(index<name.length()&&index>=0)){
				this.time.add(index, time);
				this.name.add(index, name);
			}
		}catch(Exception e){
			Util.debug(e.getMessage() + " Exception: "+ e.getCause()+" - "+e.getStackTrace());
		}
	}
	/**
	 * Method for adding a name and time to Array, +1 to array size. 
	 * @param name - String name
	 * @param time - String time, Must be convertible to timestamp, else throw exception
	 */
	public void add(String name, String time){
		try{
			Timestamp.valueOf(time);
			if(name != null&&time!=null){
				this.time.add(time);
				this.name.add(name);
			}
		}catch(Exception e){
			Util.debug(e.getMessage() + " Exception: "+ e.getCause()+" - "+e.getStackTrace());
		}
	}
	
	/**
	 * Method for clearing all values within arrays.
	 */
	public void clear(){
		this.time.clear();
		this.name.clear();
	}

	/**
	 * Method for ordering arrays by most recent times.
	 *
	 */
	public void orderByDate(){
		
		int index = 0;
		int count = 0;
		
		ArrayList<Timestamp> timestamp = new ArrayList<Timestamp>();
		
		for(String s: this.time)
			timestamp.add(Timestamp.valueOf(s));
		
		Collections.sort(timestamp, Collections.reverseOrder());
		
		for (Timestamp t: timestamp){
			if((index = time.indexOf(t.toString()))>=0){
				String temp = time.get(count);
				time.set(count, time.get(index));
				time.set(index, temp);
				temp = name.get(count);
				name.set(count++, name.get(index));
				name.set(index, temp);
			}
		}
	}
	
	/**
	 * Method for returning the ordered array by recent times.
	 *
	 * @return ArrayList - return new array list updated with ordered list.
	 */
	public ArrayList<String> getOrderedByDate(){
		orderByDate();
		int index = 0;
		ArrayList<String> ordered = new ArrayList<String>();
		try{
			while(!getTimeDiff(this.time.get(index)).equals("NULL")){
				ordered.add(Local.getString(this.name.get(index)));
				ordered.add(Local.getString(getTimeDiff(this.time.get(index++))));
		}
		}catch(Exception e){
		Util.debug(e.getMessage() + " Exception: "+ e.getCause()+" - "+e.getStackTrace());
	}	
		
		return new ArrayList<String>(ordered);
	}
	
	
	/**
	 * Method for getting within hour time, within day time and within past 2 days time.
	 *
	 * @param s - string timestamp to compare to current timestamp
	 * @return String - returns results.
	 */
	//MEthod for getting time difference of recent change compared to current time
	@SuppressWarnings("deprecation")
	private String getTimeDiff(String s)
	{//Beginning of private method
		
		//Variables
		String msg = "NULL";
		int hr = 0;
		int min = 0;
			
		//get current time
		Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());	
		Timestamp actTime = Timestamp.valueOf(s);
		DateFormat yearTime = new SimpleDateFormat("h:mm a");
		
		//Create day formatter
		DateFormat date = new SimpleDateFormat("D");
		int day = Integer.parseInt(date.format(Calendar.getInstance().getTime()));
		day = day - Integer.parseInt(date.format(actTime));
		
		//Current hour minus hour being checked
		if(currentTime.getHours()>actTime.getHours())
			hr = currentTime.getHours() - actTime.getHours();
		else
			hr = actTime.getHours()-currentTime.getHours();
					
		//Get minutes between current time and time being checked
		if(currentTime.getMinutes() < actTime.getMinutes()&& hr==1){
			min = (currentTime.getMinutes()+60)-actTime.getMinutes();
			hr = 0;
			}
		else
			min = currentTime.getMinutes() - actTime.getMinutes();
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
			msg = "Edited yesterday at "+yearTime.format(actTime);
		}//end if

		//Return msg
		return msg;
	}//End of private method

}

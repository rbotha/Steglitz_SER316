/**
 * TaskTableModel.java         
 * -----------------------------------------------------------------------------
 * Project           Memoranda
 * Package           net.sf.memoranda.ui
 * Original author   Alex V. Alishevskikh
 *                   [alexeya@gmail.com]
 * Created           18.05.2005 15:16:11
 * Revision info     $RCSfile: TaskTableModel.java,v $ $Revision: 1.7 $ $State: Exp $  
 *
 * Last modified on  $Date: 2005/12/01 08:12:26 $
 *               by  $Author: alexeya $
 * 
 * @VERSION@ 
 *
 * @COPYRIGHT@
 * 
 * @LICENSE@ 
 */

package net.sf.memoranda.ui;

import javax.swing.event.*;
import javax.swing.tree.TreePath;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.ui.treetable.AbstractTreeTableModel;
import net.sf.memoranda.ui.treetable.TreeTableModel;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Context;

/**
 * JAVADOC:
 * <h1>TaskTableModel</h1>
 * 
 * @version $Id: TaskTableModel.java,v 1.7 2017/4/08 drmorri8 Exp $
 * @author $Author: alexeya $
 */
public class TaskTableModel extends AbstractTreeTableModel implements TreeTableModel {

	String[] columnNames = {"", Local.getString("To-do"),
            Local.getString("Start date"), Local.getString("End date"),
            Local.getString("Priority"), Local.getString("Status"),
            Local.getString("% done"), Local.getString("EST EFFORT(hrs)"),
            Local.getString("Actual Effort(hrs)"), Local.getString("Errors Added"),
            Local.getString("Errors Fixed"), Local.getString("Est. LOC"), 
            Local.getString("Act. LOC") };
	
	//Find bugs - this is not used anywhere so it will be commented out.
    //protected EventListenerList listenerList = new EventListenerList();

    private boolean activeOnly = check_activeOnly();
        
    /**
     * JAVADOC: Constructor of <code>TaskTableModel</code>
     * 
     * @param root
     */
    public TaskTableModel(){
        super(CurrentProject.get());
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getValueAt(java.lang.Object,
     *      int)
     */
    public Object getValueAt(Object node, int column) {
        if (node instanceof Project)
            return null;
        Task t = (Task) node;
        switch (column) {
        case 0:
            return "";
        case 1:
            return t;		
        case 2:
            return t.getStartDate().getDate();
        case 3:
            if (t.getEndDate() == null)
                return null;
            else
                return t.getEndDate().getDate();        
        case 4:
            return getPriorityString(t.getPriority());
        case 5:
            return getStatusString(t.getStatus(CurrentDate.get()));
        case 6:            
            //return new Integer(t.getProgress());
			return t;
		case 7:
            return Math.floor((t.getEffort()) / 1000 / 36) / 100;
		case 8:
            return Math.floor((t.getActualEffort()) / 1000 / 36) / 100; 
        case 9:
        	return t.getErrorsAdded();
        case 10:
        	return t.getErrorsFixed();
        case 11:
        	return String.valueOf(t.getEstLOC());
        case 12:
        	return String.valueOf(t.getActLOC());
        case TaskTable.TASK_ID:
            return t.getID();
        case TaskTable.TASK:
            return t;
        }
        return "";
    }

    String getStatusString(int status) {
        switch (status) {
        case Task.ACTIVE:
            return Local.getString("Active");
        case Task.DEADLINE:
            return Local.getString("Deadline");
        case Task.COMPLETED:
            return Local.getString("Completed");
        case Task.FAILED:
            return Local.getString("Failed");
        case Task.FROZEN:
            return Local.getString("Frozen");
        case Task.LOCKED:
            return Local.getString("Locked");
        case Task.SCHEDULED:
            return Local.getString("Scheduled");
        }
        return "";
    }

    String getPriorityString(int p) {
        switch (p) {
        case Task.PRIORITY_NORMAL:
            return Local.getString("Normal");
        case Task.PRIORITY_LOW:
            return Local.getString("Low");
        case Task.PRIORITY_LOWEST:
            return Local.getString("Lowest");
        case Task.PRIORITY_HIGH:
            return Local.getString("High");
        case Task.PRIORITY_HIGHEST:
            return Local.getString("Highest");
        }
        return "";
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (parent instanceof Project) {
		if( activeOnly() ){
			return CurrentProject.getTaskList().getActiveSubTasks(null, CurrentDate.get()).size();
		}
		else return CurrentProject.getTaskList().getTopLevelTasks().size();
        }
        Task t = (Task) parent;
        if(activeOnly()) return CurrentProject.getTaskList().getActiveSubTasks(t.getID(), CurrentDate.get()).size();
	else return t.getSubTasks().size();
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if (parent instanceof Project)
            if( activeOnly() ) return CurrentProject.getTaskList().getActiveSubTasks(null, CurrentDate.get()).toArray()[index];
	    else return CurrentProject.getTaskList().getTopLevelTasks().toArray()[index];
        Task t = (Task) parent;
        if(activeOnly()) return CurrentProject.getTaskList().getActiveSubTasks(t.getID(), CurrentDate.get()).toArray()[index];
	else return t.getSubTasks().toArray()[index];
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int column) {
        try {
            switch (column) {
            case 0:
                return TaskTable.class;
			case 1:
                return TreeTableModel.class;			
            case 2:
				return Class.forName("java.util.Date");
            case 3:
                return Class.forName("java.util.Date");
			case 4:
				return Class.forName("java.lang.String");
            case 5:
                return Class.forName("java.lang.String");
            case 6:
            	return Class.forName("java.lang.Integer");
            case 7:
                return Class.forName("java.lang.String");
			case 8:
                return Class.forName("java.lang.String");
            case 9:
            	return Class.forName("java.lang.Integer");
            case 10:
                return Class.forName("java.lang.Integer");
            case 11:
                return Class.forName("java.lang.Integer");
            case 12:
                return Class.forName("java.lang.Integer");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void fireTreeStructureChanged(){	    
	    fireTreeStructureChanged( this,
	    			new Object[]{getRoot()},
				new int[0],
				new Object[0]
				);
    }
    
    
    /**
     * Update cached data
     */
    public void fireUpdateCache(){
		activeOnly = check_activeOnly();
    }

    public static boolean check_activeOnly(){
		Object o = Context.get("SHOW_ACTIVE_TASKS_ONLY");
		if(o == null) return false;
		return o.toString().equals("true");
	}

    public boolean activeOnly(){
		return activeOnly;
    }
    
    public boolean isCellEditable(Object node, int column) {
    	if(column == 6) return true;
    	return super.isCellEditable(node, column);
    }

}
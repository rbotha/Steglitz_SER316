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
 * @version $Id: TaskTableModel.java,v 1.7 2005/12/01 08:12:26 alexeya Exp $
 * @author $Author: alexeya $
 */
public class TaskTableModel extends AbstractTreeTableModel implements TreeTableModel {

	/* Old implimentation: 
	String[] final columnNames = {"", Local.getString("To-do"),
            Local.getString("Start date"), Local.getString("End date"),
            Local.getString("Priority"), Local.getString("Status"),
            "% " + Local.getString("done") };*/

	// drmorri8: Changed values to be concrete (no Local.getString()) until actual called for dispay.
	// List of curently active (visible to user) columns 
    String[] activeColumnNames = {"", "To-do","Expt. Hours", "Actual Hours",
			"Start date", "End date","Priority", "Status","%done" };
	
	// This represents all columns which may (but do not have to be) visible to the user
	String[] allColumnNames = {"", "To-do","Expt. Hours", "Actual Hours",
			"Start date", "End date","Priority", "Status","%done" };

    protected EventListenerList listenerList = new EventListenerList();

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
        return activeColumnNames.length;
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return Local.getString(activeColumnNames[column]);
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getValueAt(java.lang.Object,
     *      int
     */
    public Object getValueAt(Object node, int column) {
		// drmorri8: Changed implementation to determine content based on actual column name, not column number
        if (node instanceof Project)
            return null;
        Task t = (Task) node;
        switch (activeColumnNames[column]) {
        case "":
            return "";
        case "To-do":
            return t;
		case "Expt. Hours":
            return Math.floor((t.getEffort()) / 1000 / 36) / 100;
		case "Actual Hours":
            return Math.floor((t.getEffort()) / 1000 / 36) / 100; 		
        case "Start date":
            return t.getStartDate().getDate();
        case "End date":
            if (t.getEndDate() == null)
                return null;
            else
                return t.getEndDate().getDate();        
        case "Priority":
            return getPriorityString(t.getPriority());
        case "Status":
            return getStatusString(t.getStatus(CurrentDate.get()));
        case "%done":            
            //return new Integer(t.getProgress());
			return t;
        /*
		case TaskTable.TASK_ID:
            return t.getID();
        case TaskTable.TASK:
            return t;
		*/
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
		// drmorri8: Changed implementation to determine content based on actual column name, not column number
        try {
            switch (activeColumnNames[column]) {
            case "":
                return TaskTable.class;
			case "To-do":
                return TreeTableModel.class;
			case "Expt. Hours":
                return Class.forName("java.lang.Long");
			case "Actual Hours":
                return Class.forName("java.lang.Long");				
            case "Start date":
				return Class.forName("java.util.Date");
            case "End date":
                return Class.forName("java.util.Date");
			case "Priority":
				return Class.forName("java.lang.String");
            case "Status":
                return Class.forName("java.lang.String");
            case "%done":
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
		if(column >= getColumnCount() || column < 0) {
			/*DEBUG*/System.out.println("[DEBGUG] Warning, attempted to edit non-existant task cell in column: " + column);
			return false;
		}
		if(activeColumnNames[column].equals("%done")) return true; 
        return super.isCellEditable(node, column);
    }

}
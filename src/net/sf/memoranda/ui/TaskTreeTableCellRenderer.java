
package net.sf.memoranda.ui;

import net.sf.memoranda.Project;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskTreeTableCellRenderer.
 */
public class TaskTreeTableCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer, TableCellRenderer {
    
    /** The pr highest icon. */
    static ImageIcon PR_HIGHEST_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/pr_highest.png"));
    
    /** The pr high icon. */
    static ImageIcon PR_HIGH_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/pr_high.png"));
    
    /** The pr normal icon. */
    static ImageIcon PR_NORMAL_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/pr_normal.png"));
    
    /** The pr low icon. */
    static ImageIcon PR_LOW_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/pr_low.png"));
    
    /** The pr lowest icon. */
    static ImageIcon PR_LOWEST_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/pr_lowest.png"));
    
    /** The task active icon. */
    static ImageIcon TASK_ACTIVE_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_active.png"));
    
    /** The task scheduled icon. */
    static ImageIcon TASK_SCHEDULED_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_scheduled.png"));
    
    /** The task deadline icon. */
    static ImageIcon TASK_DEADLINE_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_deadline.png"));
    
    /** The task failed icon. */
    static ImageIcon TASK_FAILED_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_failed.png"));
    
    /** The task completed icon. */
    static ImageIcon TASK_COMPLETED_ICON = new ImageIcon(net.sf.memoranda.ui.AppFrame.class
            .getResource("resources/icons/task_completed.png"));
    
    /** The colors. */
    static Color[] colors =
        {
            Color.YELLOW,
            Color.ORANGE,
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK,
            Color.WHITE,
            Color.PINK };
    
    /** The label. */
    // reusable cellrenderers
    JLabel label = new JLabel();
    
    /** The progress label. */
    //JLabel tree_label = new JLabel();
    TaskProgressLabel progressLabel;
    
    /** The empty panel. */
    JPanel empty_panel = new JPanel();
    
    /** The table. */
    // get Task objects via table (maybe not most elegant solution)
    TaskTable table;
    
    //SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy");
    /** The date format. */
    //  use localized date format, modified from default locale's short format if possible
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);//createModifiedShortFormat();

    /**
     * Instantiates a new task tree table cell renderer.
     *
     * @param table the table
     */
    public TaskTreeTableCellRenderer(TaskTable table) {
        super();
        this.table = table;
        progressLabel = new TaskProgressLabel(table);
        label.setOpaque(true);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // if root then just return some component
        // it is not shown anyway
        super.getTreeCellRendererComponent(
                tree, value, selected,
                expanded, leaf, row,
                hasFocus);
        if (value instanceof Project)
            return empty_panel;
        if (!(value instanceof Task))
            return empty_panel;
        Task t = (Task) value; 
        setText(t.getText());
        setToolTipText(t.getDescription());
        setIcon(getStatusIcon(t));
        applyFont(t, this);
        //return getTaskTreeCellRenderer(t, selected, hasFocus);
        return this;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable ignore, Object value, boolean selected,
            boolean hasFocus, int row, int column) {
        Task t = (Task) table.getValueAt(row, 1);
        if (column == 1) {
            // this never happens because
            // column 1 contains TreeTableModel
            // and default renderer for it
            // is JTree directly            
            return table.getTree();
        }
        // default values
        // label.setOpaque(true);
        label.setForeground(Color.BLACK);
        label.setIcon(null);
       // label.setToolTipText(t.getDescription()); //XXX Disabled because of bug 1596966
        applyFont(t, label);
        applySelectionStyle(selected, label);
        applyFocus(hasFocus, label);
        if (value == null) {
            label.setText("");
            return label;
        }
        String columnName = table.getColumnName(column);
        if( columnName.equals(Local.getString("% done"))) {
        //if (column == 6) {
            return getProgressCellRenderer(t, selected, hasFocus, column);
        }
        if( columnName.equals("") ){
        //if (column == 0) {
            return getPriorityIconCellRenderer(t, selected, hasFocus);
        }
        if( columnName.equals(Local.getString("Start date")) 
        		|| columnName.equals(Local.getString("End date")) ){
        // if ((column == 2) || (column == 3)) {
            label.setText(dateFormat.format((Date) value));
            return label;
        }
        if( columnName.equals(Local.getString("Status") ) ){
        //if (column == 5) {
            label.setText(value.toString());
            label.setForeground(getColorForTaskStatus(t, false));
            return label;
        }
        /* redundant
        if( columnName.equals(Local.getString("EST EFFORT(hrs)")) ||
        		columnName.equals(Local.getString("Actual Effort(hrs)")) ){
            label.setText(value.toString());
            return label;
        } 
        */
        label.setText(value.toString());
        return label;
    }

    /**
     * Component used to render tree cells in treetable.
     *
     * @param t the t
     * @param selected the selected
     * @param hasFocus the has focus
     * @return the task tree cell renderer
     */
    private Component getTaskTreeCellRenderer(Task t, boolean selected, boolean hasFocus) {
        JLabel tree_label = new JLabel();       
        tree_label.setText(t.getText());
        // XXX [alexeya] Disabled coz a bug with tooltips in TreeTables:
        //tree_label.setToolTipText(t.getDescription());
        tree_label.setIcon(getStatusIcon(t));
        applyFont(t, tree_label);
        return tree_label;        
    }

    /**
     * Component showing task progress.
     *
     * @param t the t
     * @param selected the selected
     * @param hasFocus the has focus
     * @param column the column
     * @return the progress cell renderer
     */
    private Component getProgressCellRenderer(Task t, boolean selected, boolean hasFocus, int column) {
        progressLabel.setTask(t);
        progressLabel.setColumn(column);
        applyFocus(hasFocus, progressLabel);
        return progressLabel;
    }

    /**
     * Gets the priority icon cell renderer.
     *
     * @param t the t
     * @param selected the selected
     * @param hasFocus the has focus
     * @return the priority icon cell renderer
     */
    private Component getPriorityIconCellRenderer(Task t, boolean selected, boolean hasFocus) {
        applyFocus(false, label); // disable focus borders
        label.setIcon(getPriorityIcon(t));
        label.setToolTipText(t.getDescription());
        return label;
    }

    /**
     * Apply selection style.
     *
     * @param selected the selected
     * @param c the c
     */
    // some convenience methods
    private void applySelectionStyle(boolean selected, JComponent c) {
        if (selected)
            c.setBackground(table.getSelectionBackground());
        else
            c.setBackground(table.getBackground());
    }

    /**
     * Apply focus.
     *
     * @param hasFocus the has focus
     * @param c the c
     */
    private void applyFocus(boolean hasFocus, JComponent c) {
        if (hasFocus) {
            c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, table.getSelectionBackground()
                    .darker()));
        } else {
            if (c.getBorder() != null) {
                c.setBorder(null);
            }
        }
    }

    /**
     * Apply font.
     *
     * @param t the t
     * @param c the c
     */
    private void applyFont(Task t, JComponent c) {
        if ((t.getStatus(CurrentDate.get()) == Task.ACTIVE)
                || (t.getStatus(CurrentDate.get()) == Task.DEADLINE))
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        else
            c.setFont(c.getFont().deriveFont(Font.PLAIN));
    }

    /**
     * Color representing task status, "light" color is useful for backgrounds
     * and other for text.
     *
     * @param t the t
     * @param light the light
     * @return the color for task status
     */
    public static Color getColorForTaskStatus(Task t, boolean light) {
        if (light) {
            switch (t.getStatus(CurrentDate.get())) {
            case Task.ACTIVE:
                return new Color(192, 255, 192);
            case Task.SCHEDULED:
                return new Color(192, 230, 255);
            case Task.DEADLINE:
                return new Color(255, 240, 160);
            case Task.FAILED:
                return new Color(255, 192, 192);
            case Task.COMPLETED:
                return new Color(230, 255, 230);
            }
        } else {
            switch (t.getStatus(CurrentDate.get())) {
            case Task.ACTIVE:
                return new Color(0, 180, 0);
            case Task.SCHEDULED:
                return new Color(0, 120, 255);
            case Task.DEADLINE:
                return new Color(160, 90, 0);
            case Task.FAILED:
                return new Color(255, 0, 0);
            case Task.COMPLETED:
                return new Color(0, 120, 0);
            }
        }
        System.err.println("Problem finding color for task status");
        return null;
    }

    /**
     * Gets the status icon.
     *
     * @param t the t
     * @return the status icon
     */
    public static ImageIcon getStatusIcon(Task t) {
        switch (t.getStatus(CurrentDate.get())) {
        case Task.ACTIVE:
            return TASK_ACTIVE_ICON;
        case Task.SCHEDULED:
            return TASK_SCHEDULED_ICON;
        case Task.DEADLINE:
            return TASK_DEADLINE_ICON;
        case Task.FAILED:
            return TASK_FAILED_ICON;
        case Task.COMPLETED:
            return TASK_COMPLETED_ICON;
        }
        System.err.println("Problem finding status icon");
        return null;
    }

    /**
     * Gets the priority icon.
     *
     * @param t the t
     * @return the priority icon
     */
    public static ImageIcon getPriorityIcon(Task t) {
        switch (t.getPriority()) {
        case Task.PRIORITY_NORMAL:
            return PR_NORMAL_ICON;
        case Task.PRIORITY_HIGHEST:
            return PR_HIGHEST_ICON;
        case Task.PRIORITY_HIGH:
            return PR_HIGH_ICON;
        case Task.PRIORITY_LOW:
            return PR_LOW_ICON;
        case Task.PRIORITY_LOWEST:
            return PR_LOWEST_ICON;
        }
        System.err.println("Problem finding priority icon");
        return null;
    }
    
    
}

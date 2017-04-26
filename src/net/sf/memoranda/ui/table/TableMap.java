package net.sf.memoranda.ui.table;

/** 
 * In a chain of data manipulators some behaviour is common. TableMap
 * provides most of this behavour and can be subclassed by filters
 * that only need to override a handful of specific methods. TableMap 
 * implements TableModel by routing all requests to its model, and
 * TableModelListener by routing all events to its listeners. Inserting 
 * a TableMap which has not been subclassed into a chain of table filters 
 * should have no effect.
 *
 * @version 1.4 12/17/97
 * @author Philip Milne */

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class TableMap.
 */
/*$Id: TableMap.java,v 1.3 2004/01/30 12:17:42 alexeya Exp $*/
public class TableMap extends AbstractTableModel 
                      implements TableModelListener {
    
    /** The model. */
    protected TableModel model; 

    /**
     * Gets the model.
     *
     * @return the model
     */
    public TableModel getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(TableModel model) {
        this.model = model; 
        model.addTableModelListener(this); 
    }

    // By default, implement TableModel by forwarding all messages 
    // to the model. 

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int aRow, int aColumn) {
        return model.getValueAt(aRow, aColumn); 
    }
        
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int aRow, int aColumn) {
        model.setValueAt(aValue, aRow, aColumn); 
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return (model == null) ? 0 : model.getRowCount(); 
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return (model == null) ? 0 : model.getColumnCount(); 
    }
        
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int aColumn) {
        return model.getColumnName(aColumn); 
    }

    
/* (non-Javadoc)
 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
 */
public Class getColumnClass(int aColumn) {
        return model.getColumnClass(aColumn); 
    }
        
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) { 
         return model.isCellEditable(row, column); 
    }
//
// Implementation of the TableModelListener interface, 
//
    /* (non-Javadoc)
 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
 */
// By default forward all events to all the listeners. 
    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e);
    }
}

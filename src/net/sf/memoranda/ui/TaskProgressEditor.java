package net.sf.memoranda.ui;

import net.sf.memoranda.*;
import net.sf.memoranda.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

// TODO: Auto-generated Javadoc
/**
 * Editor for task progress.
 */
public class TaskProgressEditor extends JPanel implements TableCellEditor{
	
	/** The table. */
	JTable table;
	
	/** The current. */
	Task current;
	
	/** The is selected. */
	boolean isSelected;
	
	/** The row. */
	int row;
	
	/** The column. */
	int column;
	
	/** The listeners. */
	java.util.List listeners = new java.util.ArrayList();
	
	/** The label. */
	JLabel label = new JLabel();
	
	/**
	 * Instantiates a new task progress editor.
	 */
	public TaskProgressEditor(){
		addMouseListener(new java.awt.event.MouseAdapter(){
                  public void mousePressed(java.awt.event.MouseEvent e){
                      if(e instanceof MouseEvent){
                          MouseEvent me = (MouseEvent) e;
                          if(me.getButton() != MouseEvent.BUTTON1){
                              stopEditing();
                              return;
                          }
                      }
                      int w = getWidth()/2;
                      if(e.getX() > w){
                          current.setProgress( current.getProgress()+5 );
                      }else{
                          current.setProgress( current.getProgress()-5 );
                      }
                      repaint();
                  }
		});
		setLayout(new java.awt.BorderLayout());
		label.setOpaque(false);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) { 
		current = (Task) value;
		this.table = table;
		this.isSelected = isSelected;
		row = r; column = c;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g){
		paintComponent(g);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		TableCellRenderer cr = table.getCellRenderer(row, column);
		((TaskProgressLabel)cr.getTableCellRendererComponent(table, current, isSelected, true, row, column)).paintComponent(g);
		
		label.setSize( this.getSize() );
		
		label.setText("-");
		label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		label.paint(g);
		label.setText("+");
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		label.paint(g);
				
	}
	
	/**
	 * Stop editing.
	 */
	private void stopEditing(){
		for(int i=0; i<listeners.size(); i++){
			CellEditorListener cel = (CellEditorListener) listeners.get(i);
			cel.editingStopped(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
	 */
	public void addCellEditorListener(CellEditorListener var0){ 
		listeners.add(var0); 
		}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
	 */
	public void removeCellEditorListener(CellEditorListener var0){
		listeners.remove(var0); 
		}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#cancelCellEditing()
	 */
	public void cancelCellEditing(){}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public java.lang.Object getCellEditorValue(){
		return null; // just return null, because model will not use this
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(java.util.EventObject e){
		if(e instanceof MouseEvent){
			MouseEvent me = (MouseEvent) e;
			if(me.getButton() == MouseEvent.BUTTON1){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing(){
		return true;
		}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
	 */
	public boolean shouldSelectCell(java.util.EventObject var0){
		return true;
		}	
    
}



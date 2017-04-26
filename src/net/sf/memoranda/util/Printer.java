package net.sf.memoranda.util;

import java.awt.print.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.event.ActionEvent;

import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

// TODO: Auto-generated Javadoc
/**
 * The Class Printer.
 */
public class Printer implements Printable{

	/** The editor. */
	HTMLEditor editor = null;
	
	/** The g. */
	Graphics g;
	
	/**
	 * Instantiates a new printer.
	 *
	 * @param editor the editor
	 */
	public Printer(HTMLEditor editor) {
		this.editor = editor;
	}
	
	/**
	 * Instantiates a new printer.
	 *
	 * @param g the g
	 */
	public Printer(Graphics g){
		this.g = g;
	}

	
	/** The text. */
	public String text;
	
	/** The cl. */
	Class cl = net.sf.memoranda.ui.htmleditor.HTMLEditor.class;
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException{
		
		if(page > 0) {
			return NO_SUCH_PAGE;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		g.drawString("EST", 100, 100);
		
		return PAGE_EXISTS;
	}
	
	
	/** The print action. */
	public Action printAction = new AbstractAction(Local.getString("Print"),new ImageIcon(net.sf.memoranda.ui.AppFrame.class
			.getResource("resources/icons/print.png"))){
		public void actionPerformed(ActionEvent e){
			printJob();
		}
	};
	
	/**
	 * Prints the job.
	 */
	void printJob(){
		text = editor.editor.getText();
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(editor.editor.getPrintable(null,null ));
		boolean ok = job.printDialog();
		if(ok){
			try{
				job.print();
				
			}catch(PrinterException ex){
				new ExceptionDialog(ex);
			}
		}
	}
	
	/** The print event action. */
	public Action printEventAction = new AbstractAction(Local.getString("Print"),new ImageIcon(net.sf.memoranda.ui.AppFrame.class
			.getResource("resources/icons/print.png"))){
		public void actionPerformed(ActionEvent e){
			printEventJob();
		}
	};
	
	/**
	 * Prints the event job.
	 */
	void printEventJob(){
		text = editor.editor.getText();
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if(ok){
			try{
				job.print();
				
			}catch(PrinterException ex){
				new ExceptionDialog(ex);
			}
		}
	}	
}

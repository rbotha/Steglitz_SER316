package net.sf.memoranda.util;

import java.awt.print.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.event.ActionEvent;

import net.sf.memoranda.ui.htmleditor.HTMLEditor;

public class Printer implements Printable{

	HTMLEditor editor = null;
	Graphics g;
	
	public Printer(HTMLEditor editor) {
		this.editor = editor;
	}
	
	public Printer(Graphics g){
		this.g = g;
	}

	
	public String text;
	Class cl = net.sf.memoranda.ui.htmleditor.HTMLEditor.class;
	
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException{
		
		if(page > 0) {
			return NO_SUCH_PAGE;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		g.drawString("EST", 100, 100);
		
		return PAGE_EXISTS;
	}
	
	
	public Action printAction = new AbstractAction(Local.getString("Print"),new ImageIcon(net.sf.memoranda.ui.AppFrame.class
			.getResource("resources/icons/print.png"))){
		public void actionPerformed(ActionEvent e){
			printJob();
		}
	};
	
	void printJob(){
		text = editor.editor.getText();
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(editor.editor.getPrintable(null,null ));
		boolean ok = job.printDialog();
		if(ok){
			try{
				job.print();
				
			}catch(PrinterException ex){}
		}
	}
	
	public Action printEventAction = new AbstractAction(Local.getString("Print"),new ImageIcon(net.sf.memoranda.ui.AppFrame.class
			.getResource("resources/icons/print.png"))){
		public void actionPerformed(ActionEvent e){
			printEventJob();
		}
	};
	
	void printEventJob(){
		text = editor.editor.getText();
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if(ok){
			try{
				job.print();
				
			}catch(PrinterException ex){}
		}
	}	
}

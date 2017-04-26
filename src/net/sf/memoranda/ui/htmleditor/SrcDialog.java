package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>.
 *
 * @author unascribed
 * @version 1.0
 */

public class SrcDialog extends JDialog {
	
	/** The panel 1. */
	JPanel panel1 = new JPanel();
	
	/** The border layout 1. */
	BorderLayout borderLayout1 = new BorderLayout();
	
	/** The j scroll pane 1. */
	JScrollPane jScrollPane1 = new JScrollPane();
	
	/** The j text area 1. */
	JTextArea jTextArea1 = new JTextArea();

	/**
	 * Instantiates a new src dialog.
	 *
	 * @param frame the frame
	 * @param text the text
	 */
	public SrcDialog(Frame frame, String text) {
		super(frame, "Source text", true);
		try {
			setText(text);
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Instantiates a new src dialog.
	 */
	public SrcDialog() {
		this(null, "");
	}
	
	/**
	 * Jb init.
	 *
	 * @throws Exception the exception
	 */
	void jbInit() throws Exception {
		panel1.setLayout(borderLayout1);
		jTextArea1.setEditable(false);
		getContentPane().add(panel1);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(jTextArea1, null);
	}

	/**
	 * Sets the text.
	 *
	 * @param txt the new text
	 */
	public void setText(String txt) {
		jTextArea1.setText(txt);
	}
}
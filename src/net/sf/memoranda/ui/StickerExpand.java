package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.StickerDialog.ComboBoxRenderer;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class StickerExpand.
 */
public class StickerExpand extends JDialog{
	
	/** The txt. */
	String txt;
	
	/** The fore ground color. */
	Color backGroundColor, foreGroundColor;
	
	/** The cancelled. */
	public boolean CANCELLED = true;
	
	/** The panel 1. */
	JPanel panel1 = new JPanel();
	
	/** The border layout 1. */
	BorderLayout borderLayout1 = new BorderLayout();
	
	/** The border layout 2. */
	BorderLayout borderLayout2 = new BorderLayout();
	
	/** The bottom panel. */
	JPanel bottomPanel = new JPanel();
	
	/** The top panel. */
	JPanel topPanel = new JPanel();
	
	/** The header. */
	JLabel header = new JLabel();
	
	/** The j scroll pane 1. */
	JScrollPane jScrollPane1 = new JScrollPane();
	
	/** The j panel 1. */
	JPanel jPanel1 = new JPanel();
	
	/** The sticker text. */
	JLabel stickerText = new JLabel();
	
	/** The j label 1. */
	JLabel jLabel1 = new JLabel();
	
	/** The border layout 3. */
	BorderLayout borderLayout3 = new BorderLayout();

	/** The border 1. */
	Border border1;
	
	/** The border 2. */
	Border border2;
	
	/**
	 * Instantiates a new sticker expand.
	 *
	 * @param frame the frame
	 * @param txt the txt
	 * @param backcolor the backcolor
	 * @param fontcolor the fontcolor
	 * @param priority the priority
	 */
	public StickerExpand(Frame frame,String txt, String backcolor, String fontcolor, String priority) {
		super(frame, Local.getString("Sticker")+" ["+priority+"]" , true);
		this.txt=txt;
		this.backGroundColor=Color.decode(backcolor);
		this.foreGroundColor=Color.decode(fontcolor);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	
	/**
	 * Jb init.
	 *
	 * @throws Exception the exception
	 */
	void jbInit() throws Exception {
		border1 =
				BorderFactory.createCompoundBorder(
						BorderFactory.createEtchedBorder(
								Color.white,
								new Color(156, 156, 158)),
								BorderFactory.createEmptyBorder(5, 5, 5, 5));
		border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		panel1.setLayout(borderLayout1);
		this.getContentPane().setLayout(borderLayout2);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
		topPanel.setBackground(Color.WHITE);

		jPanel1.setLayout(borderLayout3);
		panel1.setBorder(border1);
		jPanel1.setBorder(border2);

		getContentPane().add(panel1, BorderLayout.CENTER);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(stickerText, null);
		panel1.add(jPanel1, BorderLayout.SOUTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);

		stickerText.setText(txt);
		stickerText.setOpaque(true);
		stickerText.setBackground(backGroundColor);
		stickerText.setForeground(foreGroundColor);
	}
}

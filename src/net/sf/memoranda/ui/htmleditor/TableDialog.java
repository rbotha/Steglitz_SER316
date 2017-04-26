package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

import net.sf.memoranda.ui.htmleditor.util.Local;

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

public class TableDialog extends JDialog {
	
	/** The area panel. */
	JPanel areaPanel = new JPanel(new GridBagLayout());
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The buttons panel. */
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	
	/** The cancel B. */
	JButton cancelB = new JButton();
	
	/** The ok B. */
	JButton okB = new JButton();
	
	/** The lbl width. */
	JLabel lblWidth = new JLabel();
	
	/** The height field. */
	public JTextField heightField = new JTextField();
	
	/** The lbl height. */
	JLabel lblHeight = new JLabel();
	
	/** The width field. */
	public JTextField widthField = new JTextField();
	
	/** The aligns. */
	String[] aligns = {"", Local.getString("left"), Local.getString("center"),
		Local.getString("right")};
	
	/** The valigns. */
	String[] valigns = {"", Local.getString("top"), Local.getString("center"),
		Local.getString("bottom")};
	
	/** The lbl padding. */
	JLabel lblPadding = new JLabel();
	
	/** The lbl spacing. */
	JLabel lblSpacing = new JLabel();
	
	/** The header panel. */
	JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	/** The header. */
	JLabel header = new JLabel();
	
	/** The cancelled. */
	public boolean CANCELLED = false;
	
	/** The lbl columns. */
	JLabel lblColumns = new JLabel();
	
	/** The lbl rows. */
	JLabel lblRows = new JLabel();
	
	/** The v align CB. */
	JComboBox vAlignCB = new JComboBox(valigns);
	
	/** The lbl outline. */
	JLabel lblOutline = new JLabel();
	
	/** The align CB. */
	JComboBox alignCB = new JComboBox(aligns);
	
	/** The lbl vert outline. */
	JLabel lblVertOutline = new JLabel();
	
	/** The bgcolor field. */
	JTextField bgcolorField = new JTextField();
	
	/** The lbl fill color. */
	JLabel lblFillColor = new JLabel();
	
	/** The bg color B. */
	JButton bgColorB = new JButton();
	
	/** The lbl border. */
	JLabel lblBorder = new JLabel();
	
	/** The columns. */
	public JSpinner columns = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
	
	/** The rows. */
	public JSpinner rows = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
	
	/** The cellpadding. */
	public JSpinner cellpadding = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
	
	/** The cellspacing. */
	public JSpinner cellspacing = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
	
	/** The border. */
	public JSpinner border = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));

	/**
	 * Instantiates a new table dialog.
	 *
	 * @param frame the frame
	 */
	public TableDialog(Frame frame) {
		super(frame, Local.getString("Table"), true);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Instantiates a new table dialog.
	 */
	public TableDialog() {
		this(null);
	}

	/**
	 * Jb init.
	 *
	 * @throws Exception the exception
	 */
	void jbInit() throws Exception {
		this.setResizable(false);
		headerPanel.setBackground(Color.WHITE);
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("Table"));
		header.setIcon(new ImageIcon(
				net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
				"resources/icons/tablebig.png")));
		headerPanel.add(header);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		
		areaPanel.setBorder(BorderFactory.createEtchedBorder(Color.white,
			new Color(142, 142, 142)));
		lblColumns.setText(Local.getString("Columns"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 5, 5);
		areaPanel.add(lblColumns, gbc);
		columns.setPreferredSize(new Dimension(50, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 5, 0);
		areaPanel.add(columns, gbc);
		lblRows.setText(Local.getString("Rows"));
		gbc = new GridBagConstraints();
		gbc.gridx = 3; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 5, 5);
		areaPanel.add(lblRows, gbc);
		rows.setPreferredSize(new Dimension(50, 24));
		gbc = new GridBagConstraints();
		gbc.gridx = 4; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 5, 0);
		areaPanel.add(rows, gbc);
		lblWidth.setText(Local.getString("Width"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		areaPanel.add(lblWidth, gbc);
		widthField.setPreferredSize(new Dimension(50, 25));
		widthField.setText("100%");
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 0);
		areaPanel.add(widthField, gbc);
		lblHeight.setText(Local.getString("Height"));
		gbc.gridx = 3; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(lblHeight, gbc);
		heightField.setPreferredSize(new Dimension(50, 25));
		gbc.gridx = 4; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 0);
		areaPanel.add(heightField, gbc);
		lblPadding.setText(Local.getString("Cell padding"));
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		areaPanel.add(lblPadding, gbc);
		cellpadding.setPreferredSize(new Dimension(50, 24));
		gbc.gridx = 1; gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 0);
		areaPanel.add(cellpadding, gbc);
		lblSpacing.setText(Local.getString("Cell spacing"));
		gbc.gridx = 3; gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(lblSpacing, gbc);
		cellspacing.setPreferredSize(new Dimension(50, 24));
		gbc.gridx = 4; gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 0);
		areaPanel.add(cellspacing, gbc);
		lblBorder.setText(Local.getString("Border"));
		gbc.gridx = 0; gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		areaPanel.add(lblBorder, gbc);
		border.setPreferredSize(new Dimension(50, 24));
		gbc.gridx = 1; gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 0);
		areaPanel.add(border, gbc);
		lblFillColor.setText(Local.getString("Fill color"));
		gbc.gridx = 3; gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(lblFillColor, gbc);
		bgcolorField.setPreferredSize(new Dimension(50, 24));
		Util.setBgcolorField(bgcolorField);
		gbc.gridx = 4; gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(bgcolorField, gbc);
		bgColorB.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      bgColorB_actionPerformed(e);
                  }
		});
		bgColorB.setIcon(new ImageIcon(
			net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
			"resources/icons/color.png")));
		bgColorB.setPreferredSize(new Dimension(25, 25));
		gbc.gridx = 5; gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 10);
		areaPanel.add(bgColorB, gbc);
		lblOutline.setText(Local.getString("Align"));
		gbc.gridx = 0; gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 10, 5);
		areaPanel.add(lblOutline, gbc);
		alignCB.setBackground(new Color(230, 230, 230));
		alignCB.setFont(new java.awt.Font("Dialog", 1, 10));
		alignCB.setPreferredSize(new Dimension(70, 25));
		gbc.gridx = 1; gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 10, 5);
		areaPanel.add(alignCB, gbc);																																																																				
		lblVertOutline.setText(Local.getString("Vert. align"));
		gbc.gridx = 3; gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 10, 5);
		areaPanel.add(lblVertOutline, gbc);		
		vAlignCB.setPreferredSize(new Dimension(70, 25));
		vAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
		vAlignCB.setBackground(new Color(230, 230, 230));
		gbc.gridx = 4; gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 10, 0);
		areaPanel.add(vAlignCB, gbc);
		this.getContentPane().add(areaPanel, BorderLayout.CENTER);

		okB.setMaximumSize(new Dimension(100, 26));
		okB.setMinimumSize(new Dimension(100, 26));
		okB.setPreferredSize(new Dimension(100, 26));
		okB.setText(Local.getString("Ok"));
		okB.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      okB_actionPerformed(e);
                  }
		});
		this.getRootPane().setDefaultButton(okB);
		cancelB.setMaximumSize(new Dimension(100, 26));
		cancelB.setMinimumSize(new Dimension(100, 26));
		cancelB.setPreferredSize(new Dimension(100, 26));
		cancelB.setText(Local.getString("Cancel"));
		cancelB.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      cancelB_actionPerformed(e);
                  }
		});
		buttonsPanel.add(okB);
		buttonsPanel.add(cancelB);
		this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Ok B action performed.
	 *
	 * @param e the e
	 */
	void okB_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * Cancel B action performed.
	 *
	 * @param e the e
	 */
	void cancelB_actionPerformed(ActionEvent e) {
		CANCELLED = true;
		this.dispose();
	}

	/**
	 * Bg color B action performed.
	 *
	 * @param e the e
	 */
	void bgColorB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put(
			"ColorChooser.swatchesNameText",
			Local.getString("Swatches"));
		UIManager.put("ColorChooser.hsbNameText", Local.getString("HSB"));
		UIManager.put("ColorChooser.rgbNameText", Local.getString("RGB"));
		UIManager.put(
			"ColorChooser.swatchesRecentText",
			Local.getString("Recent:"));
		UIManager.put("ColorChooser.previewText", Local.getString("Preview"));
		UIManager.put(
			"ColorChooser.sampleText",
			Local.getString("Sample Text")
				+ " "
				+ Local.getString("Sample Text"));
		UIManager.put("ColorChooser.okText", Local.getString("OK"));
		UIManager.put("ColorChooser.cancelText", Local.getString("Cancel"));
		UIManager.put("ColorChooser.resetText", Local.getString("Reset"));
		UIManager.put("ColorChooser.hsbHueText", Local.getString("H"));
		UIManager.put("ColorChooser.hsbSaturationText", Local.getString("S"));
		UIManager.put("ColorChooser.hsbBrightnessText", Local.getString("B"));
		UIManager.put("ColorChooser.hsbRedText", Local.getString("R"));
		UIManager.put("ColorChooser.hsbGreenText", Local.getString("G"));
		UIManager.put("ColorChooser.hsbBlueText", Local.getString("B2"));
		UIManager.put("ColorChooser.rgbRedText", Local.getString("Red"));
		UIManager.put("ColorChooser.rgbGreenText", Local.getString("Green"));
		UIManager.put("ColorChooser.rgbBlueText", Local.getString("Blue"));

		Color initColor = Util.decodeColor(bgcolorField.getText());

		Color c =
			JColorChooser.showDialog(
				this,
				Local.getString("Table background color"),
				initColor);
		if (c == null)
			return;

		bgcolorField.setText(
			"#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase());
		Util.setBgcolorField(bgcolorField);
	}

}
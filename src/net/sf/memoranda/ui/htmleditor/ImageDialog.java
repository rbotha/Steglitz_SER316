package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

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
public class ImageDialog extends JDialog implements WindowListener {
    
    /** The header panel. */
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    /** The header. */
    JLabel header = new JLabel();
    
    /** The area panel. */
    JPanel areaPanel = new JPanel(new GridBagLayout());
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The j label 1. */
    JLabel jLabel1 = new JLabel();
    
    /** The file field. */
    public JTextField fileField = new JTextField();
    
    /** The browse B. */
    JButton browseB = new JButton();
    
    /** The j label 2. */
    JLabel jLabel2 = new JLabel();
    
    /** The alt field. */
    public JTextField altField = new JTextField();
    
    /** The j label 3. */
    JLabel jLabel3 = new JLabel();
    
    /** The width field. */
    public JTextField widthField = new JTextField();
    
    /** The j label 4. */
    JLabel jLabel4 = new JLabel();
    
    /** The height field. */
    public JTextField heightField = new JTextField();
    
    /** The j label 5. */
    JLabel jLabel5 = new JLabel();
    
    /** The hspace field. */
    public JTextField hspaceField = new JTextField();
    
    /** The j label 6. */
    JLabel jLabel6 = new JLabel();
    
    /** The vspace field. */
    public JTextField vspaceField = new JTextField();
    
    /** The j label 7. */
    JLabel jLabel7 = new JLabel();
    
    /** The border field. */
    public JTextField borderField = new JTextField();
    
    /** The j label 8. */
    JLabel jLabel8 = new JLabel();
    
    /** The aligns. */
    String[] aligns = {"left", "right", "top", "middle", "bottom", "absmiddle",
        "texttop", "baseline"}; 
    
    /** The align CB. */
    // Note: align values are not localized because they are HTML keywords 
    public JComboBox alignCB = new JComboBox(aligns);
    
    /** The j label 9. */
    JLabel jLabel9 = new JLabel();
    
    /** The url field. */
    public JTextField urlField = new JTextField();
    
    /** The buttons panel. */
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    
    /** The ok B. */
    JButton okB = new JButton();
    
    /** The cancel B. */
    JButton cancelB = new JButton();
    
    /** The cancelled. */
    public boolean CANCELLED = false;

    /**
     * Instantiates a new image dialog.
     *
     * @param frame the frame
     */
    public ImageDialog(Frame frame) {
        super(frame, Local.getString("Image"), true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.addWindowListener(this);
    }

    /**
     * Instantiates a new image dialog.
     */
    public ImageDialog() {
        this(null);
    }

    /**
     * Jb init.
     *
     * @throws Exception the exception
     */
    void jbInit() throws Exception {
        this.setResizable(false);
        // three Panels, so used BorderLayout for this dialog.
        headerPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        headerPanel.setBackground(Color.WHITE);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Image"));
        header.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                        "resources/icons/imgbig.png")));
        headerPanel.add(header);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);

        areaPanel.setBorder(new EtchedBorder(Color.white, new Color(142, 142,
                142)));
        jLabel1.setText(Local.getString("Image file"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel1, gbc);
        fileField.setMinimumSize(new Dimension(200, 25));
        fileField.setPreferredSize(new Dimension(285, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(fileField, gbc);
        browseB.setMinimumSize(new Dimension(25, 25));
        browseB.setPreferredSize(new Dimension(25, 25));
        browseB.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                        "resources/icons/fileopen16.png")));
        browseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(browseB, gbc);
        jLabel2.setText(Local.getString("ALT text"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        altField.setPreferredSize(new Dimension(315, 25));
        altField.setMinimumSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(altField, gbc);
        jLabel3.setText(Local.getString("Width"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel3, gbc);
        widthField.setPreferredSize(new Dimension(30, 25));
        widthField.setMinimumSize(new Dimension(30, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(widthField, gbc);
        jLabel4.setText(Local.getString("Height"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel4, gbc);
        heightField.setMinimumSize(new Dimension(30, 25));
        heightField.setPreferredSize(new Dimension(30, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(heightField, gbc);
        jLabel5.setText(Local.getString("H. space"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel5, gbc);
        hspaceField.setMinimumSize(new Dimension(30, 25));
        hspaceField.setPreferredSize(new Dimension(30, 25));
        hspaceField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(hspaceField, gbc);
        jLabel6.setText(Local.getString("V. space"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel6, gbc);
        vspaceField.setMinimumSize(new Dimension(30, 25));
        vspaceField.setPreferredSize(new Dimension(30, 25));
        vspaceField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(vspaceField, gbc);
        jLabel7.setText(Local.getString("Border"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel7, gbc);
        borderField.setMinimumSize(new Dimension(30, 25));
        borderField.setPreferredSize(new Dimension(30, 25));
        borderField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(borderField, gbc);
        jLabel8.setText(Local.getString("Align"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel8, gbc);
        alignCB.setBackground(new Color(230, 230, 230));
        alignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        alignCB.setPreferredSize(new Dimension(100, 25));
        alignCB.setSelectedIndex(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(alignCB, gbc);
        jLabel9.setText(Local.getString("Hyperlink"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel9, gbc);
        urlField.setPreferredSize(new Dimension(315, 25));
        urlField.setMinimumSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 5, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(urlField, gbc);
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
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
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
     * Gets the preview icon.
     *
     * @param file the file
     * @return the preview icon
     */
    private ImageIcon getPreviewIcon(java.io.File file) {
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        ImageIcon thmb = null;
        if (tmpIcon.getIconHeight() > 48) {
            thmb = new ImageIcon(tmpIcon.getImage()
                    .getScaledInstance( -1, 48, Image.SCALE_DEFAULT));
        }
        else {
            thmb = tmpIcon;
        }
        if (thmb.getIconWidth() > 350) {
            return new ImageIcon(thmb.getImage()
                    .getScaledInstance(350, -1, Image.SCALE_DEFAULT));
        }
        else {
            return thmb;
        }
    }

    /**
     * Update preview.
     */
    //java.io.File selectedFile = null;
    public void updatePreview() {
        try {
            if (new java.net.URL(fileField.getText()).getPath() != "")
                header.setIcon(getPreviewIcon(new java.io.File(
                        new java.net.URL(fileField.getText()).getPath())));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent e) {
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent e) {
        CANCELLED = true;
        this.dispose();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent e) {
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent e) {
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent e) {
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent e) {
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Browse B action performed.
     *
     * @param e the e
     */
    void browseB_actionPerformed(ActionEvent e) {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                "Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Choose an image file"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(
                new net.sf.memoranda.ui.htmleditor.filechooser.ImageFilter());
        chooser.setAccessory(
                new net.sf.memoranda.ui.htmleditor.filechooser.ImagePreview(
                        chooser));
        chooser.setPreferredSize(new Dimension(550, 375));
        java.io.File lastSel = (java.io.File) Context.get(
                "LAST_SELECTED_IMG_FILE");
        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                fileField.setText(chooser.getSelectedFile().toURL().toString());
                header.setIcon(getPreviewIcon(chooser.getSelectedFile()));
                Context
                        .put("LAST_SELECTED_IMG_FILE", chooser
                                .getSelectedFile());
            }
            catch (Exception ex) {
                fileField.setText(chooser.getSelectedFile().getPath());
            }
            try {
                ImageIcon img = new ImageIcon(chooser.getSelectedFile()
                        .getPath());
                widthField.setText(new Integer(img.getIconWidth()).toString());
                heightField
                        .setText(new Integer(img.getIconHeight()).toString());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
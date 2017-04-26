package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Resource;
import net.sf.memoranda.util.AppList;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.MimeType;
import net.sf.memoranda.util.MimeTypesList;
import net.sf.memoranda.util.Util;

import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourcesPanel.
 */
/*$Id: ResourcesPanel.java,v 1.13 2007/03/20 08:22:41 alexeya Exp $*/
public class ResourcesPanel extends JPanel {
    
    /** The border layout 1. */
    BorderLayout borderLayout1 = new BorderLayout();
    
    /** The tool bar. */
    JToolBar toolBar = new JToolBar();
    
    /** The new res B. */
    JButton newResB = new JButton();
    
    /** The resources table. */
    ResourcesTable resourcesTable = new ResourcesTable();
    
    /** The remove res B. */
    JButton removeResB = new JButton();
    
    /** The scroll pane. */
    JScrollPane scrollPane = new JScrollPane();
    
    /** The refresh B. */
    JButton refreshB = new JButton();
  
  /** The res PP menu. */
  JPopupMenu resPPMenu = new JPopupMenu();
  
  /** The pp run. */
  JMenuItem ppRun = new JMenuItem();
  
  /** The pp remove res. */
  JMenuItem ppRemoveRes = new JMenuItem();
  
  /** The pp new res. */
  JMenuItem ppNewRes = new JMenuItem();
  
  /** The pp refresh. */
  JMenuItem ppRefresh = new JMenuItem();

    /**
     * Instantiates a new resources panel.
     */
    public ResourcesPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    
    /**
     * Jb init.
     *
     * @throws Exception the exception
     */
    void jbInit() throws Exception {
        toolBar.setFloatable(false);
        this.setLayout(borderLayout1);
        newResB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
        newResB.setEnabled(true);
        newResB.setMaximumSize(new Dimension(24, 24));
        newResB.setMinimumSize(new Dimension(24, 24));
        newResB.setToolTipText(Local.getString("New resource"));
        newResB.setRequestFocusEnabled(false);
        newResB.setPreferredSize(new Dimension(24, 24));
        newResB.setFocusable(false);
        newResB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newResB_actionPerformed(e);
            }
        });
        newResB.setBorderPainted(false);
        resourcesTable.setMaximumSize(new Dimension(32767, 32767));
        resourcesTable.setRowHeight(24);
        removeResB.setBorderPainted(false);
        removeResB.setFocusable(false);
        removeResB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeResB_actionPerformed(e);
            }
        });
        removeResB.setPreferredSize(new Dimension(24, 24));
        removeResB.setRequestFocusEnabled(false);
        removeResB.setToolTipText(Local.getString("Remove resource"));
        removeResB.setMinimumSize(new Dimension(24, 24));
        removeResB.setMaximumSize(new Dimension(24, 24));
        removeResB.setIcon(
            new ImageIcon(
                net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removeresource.png")));
        removeResB.setEnabled(false);
        scrollPane.getViewport().setBackground(Color.white);
        toolBar.addSeparator(new Dimension(8, 24));
        toolBar.addSeparator(new Dimension(8, 24));


        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        resourcesTable.addMouseListener(ppListener);

        resourcesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (resourcesTable.getRowCount() > 0) && (resourcesTable.getSelectedRow() > -1);

                removeResB.setEnabled(enbl); ppRemoveRes.setEnabled(enbl);
                ppRun.setEnabled(enbl);
            }
        });
        refreshB.setBorderPainted(false);
        refreshB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshB_actionPerformed(e);
            }
        });
        refreshB.setFocusable(false);
        refreshB.setPreferredSize(new Dimension(24, 24));
        refreshB.setRequestFocusEnabled(false);
        refreshB.setToolTipText(Local.getString("Refresh"));
        refreshB.setMinimumSize(new Dimension(24, 24));
        refreshB.setMaximumSize(new Dimension(24, 24));
        refreshB.setEnabled(true);
        refreshB.setIcon(
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/refreshres.png")));
        resPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
    ppRun.setFont(new java.awt.Font("Dialog", 1, 11));
    ppRun.setText(Local.getString("Open resource")+"...");
    ppRun.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              ppRun_actionPerformed(e);
          }
        });
    ppRun.setEnabled(false);

    ppRemoveRes.setFont(new java.awt.Font("Dialog", 1, 11));
    ppRemoveRes.setText(Local.getString("Remove resource"));
    ppRemoveRes.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              ppRemoveRes_actionPerformed(e);
          }
        });
    ppRemoveRes.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removeresource.png")));
    ppRemoveRes.setEnabled(false);
    ppNewRes.setFont(new java.awt.Font("Dialog", 1, 11));
    ppNewRes.setText(Local.getString("New resource")+"...");
    ppNewRes.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              ppNewRes_actionPerformed(e);
          }
        });
    ppNewRes.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));

    ppRefresh.setFont(new java.awt.Font("Dialog", 1, 11));
    ppRefresh.setText(Local.getString("Refresh"));
    ppRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ppRefresh_actionPerformed(e);
      }
    });
    ppRefresh.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/refreshres.png")));

    toolBar.add(newResB, null);
        toolBar.add(removeResB, null);
        toolBar.addSeparator();
        toolBar.add(refreshB, null);
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().add(resourcesTable, null);
        this.add(toolBar, BorderLayout.NORTH);
    resPPMenu.add(ppRun);
    resPPMenu.addSeparator();
    resPPMenu.add(ppNewRes);
    resPPMenu.add(ppRemoveRes);
    resPPMenu.addSeparator();
    resPPMenu.add(ppRefresh);
	
		// remove resources using the DEL key
		resourcesTable.addKeyListener(new KeyListener() {
                  public void keyPressed(KeyEvent e){
                      if(resourcesTable.getSelectedRows().length>0
                         && e.getKeyCode()==KeyEvent.VK_DELETE)
                          ppRemoveRes_actionPerformed(null);
                  }
                  public void	keyReleased(KeyEvent e){}
                  public void keyTyped(KeyEvent e){}
                });
    }

    /**
     * New res B action performed.
     *
     * @param e the e
     */
    void newResB_actionPerformed(ActionEvent e) {
        AddResourceDialog dlg = new AddResourceDialog(App.getFrame(), Local.getString("New resource"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        if (dlg.localFileRB.isSelected()) {
            String fpath = dlg.pathField.getText();
            MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
            if (mt.getMimeTypeId().equals("__UNKNOWN")) {
                mt = addResourceType(fpath);
                if (mt == null)
                    return;
            }
            if (!checkApp(mt))
                return;
            // if file if projectFile, than copy the file and change url.
            if (dlg.projectFileCB.isSelected()) {
            	fpath = copyFileToProjectDir(fpath);
            	CurrentProject.getResourcesList().addResource(fpath, false, true);
            }
            else
            	CurrentProject.getResourcesList().addResource(fpath);            	     	
            
            resourcesTable.tableChanged();
        }
        else {
            if (!Util.checkBrowser())
                return;
            CurrentProject.getResourcesList().addResource(dlg.urlField.getText(), true, false);
            resourcesTable.tableChanged();
        }
    }

    /**
     * Removes the res B action performed.
     *
     * @param e the e
     */
    void removeResB_actionPerformed(ActionEvent e) {
        int[] toRemove = resourcesTable.getSelectedRows();
        String msg = "";
        if (toRemove.length == 1)
            msg =
                Local.getString("Remove the shortcut to resource")
                    + "\n'"
                    + resourcesTable.getModel().getValueAt(toRemove[0], 0)
                    + "'";

        else
            msg = Local.getString("Remove") + " " + toRemove.length + " " + Local.getString("shortcuts");
        msg +=
            "\n"
            + Local.getString("Are you sure?");
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove resource"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        for (int i = 0; i < toRemove.length; i++) {        	
        		CurrentProject.getResourcesList().removeResource(
                        ((Resource) resourcesTable.getModel().getValueAt(toRemove[i], ResourcesTable._RESOURCE)).getPath());
        }
        resourcesTable.tableChanged();
    }

    /**
     * Adds the resource type.
     *
     * @param fpath the fpath
     * @return the mime type
     */
    MimeType addResourceType(String fpath) {
        ResourceTypeDialog dlg = new ResourceTypeDialog(App.getFrame(), Local.getString("Resource type"));
        Dimension dlgSize = new Dimension(420, 300);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.ext = MimeTypesList.getExtension(fpath);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return null;
        int ix = dlg.getTypesList().getSelectedIndex();
        MimeType mt = (MimeType) MimeTypesList.getAllMimeTypes().toArray()[ix];
        mt.addExtension(MimeTypesList.getExtension(fpath));
        CurrentStorage.get().storeMimeTypesList();
        return mt;
    }

    /**
     * Check app.
     *
     * @param mt the mt
     * @return true, if successful
     */
    boolean checkApp(MimeType mt) {
        String appId = mt.getAppId();
        AppList appList = MimeTypesList.getAppList();
        File d;
        if (appId == null) {
            appId = Util.generateId();
            d = new File("/");
        }
        else {
            File exe = new File(appList.getFindPath(appId) + "/" + appList.getExec(appId));
            if (exe.isFile())
                return true;
            d = new File(exe.getParent());
            while (!d.exists())
                d = new File(d.getParent());
        }
        SetAppDialog dlg =
            new SetAppDialog(
                App.getFrame(),
                Local.getString(Local.getString("Select the application to open files of type")+" '" + mt.getLabel() + "'"));
        Dimension dlgSize = new Dimension(420, 300);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setDirectory(d);
        dlg.appPanel.argumentsField.setText("$1");
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return false;
        File f = new File(dlg.appPanel.applicationField.getText());

        appList.addOrReplaceApp(
            appId,
            f.getParent().replace('\\', '/'),
            f.getName().replace('\\', '/'),
            dlg.appPanel.argumentsField.getText());
        mt.setApp(appId);
        /*appList.setFindPath(appId, chooser.getSelectedFile().getParent().replace('\\','/'));
        appList.setExec(appId, chooser.getSelectedFile().getName().replace('\\','/'));*/
        CurrentStorage.get().storeMimeTypesList();
        return true;
    }
    

    /**
     * Run app.
     *
     * @param fpath the fpath
     */
    void runApp(String fpath) {
        MimeType mt = MimeTypesList.getMimeTypeForFile(fpath);
        if (mt.getMimeTypeId().equals("__UNKNOWN")) {
            mt = addResourceType(fpath);
            if (mt == null)
                return;
        }
        if (!checkApp(mt))
            return;
        String[] command = MimeTypesList.getAppList().getCommand(mt.getAppId(), fpath);
        if (command == null)
            return;
        /*DEBUG*/
        System.out.println("Run: " + command[0]);
        try {
            Runtime.getRuntime().exec(command);
        }
        catch (Exception ex) {
            new ExceptionDialog(ex, "Failed to run an external application <br><code>"
                    +command[0]+"</code>", "Check the application path and command line parameters for this resource type " 
            + "(File-&gt;Preferences-&gt;Resource types).");
        }
    }

    /**
     * Run browser.
     *
     * @param url the url
     */
    void runBrowser(String url) {
        Util.runBrowser(url);
    }

    /**
     * The listener interface for receiving popup events.
     * The class that is interested in processing a popup
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addPopupListener<code> method. When
     * the popup event occurs, that object's appropriate
     * method is invoked.
     *
     * @see PopupEvent
     */
    class PopupListener extends MouseAdapter {

        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (resourcesTable.getSelectedRow() > -1)) {
                String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
                if (path.length() >0)
                    runApp(path);
                else
                    runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
            }
            //editTaskB_actionPerformed(null);
        }

                /* (non-Javadoc)
                 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
                 */
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                /* (non-Javadoc)
                 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
                 */
                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                /**
                 * Maybe show popup.
                 *
                 * @param e the e
                 */
                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        resPPMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

    }
    
    /**
     * Refresh B action performed.
     *
     * @param e the e
     */
    void refreshB_actionPerformed(ActionEvent e) {
        resourcesTable.tableChanged();
    }

  /**
   * Pp run action performed.
   *
   * @param e the e
   */
  void ppRun_actionPerformed(ActionEvent e) {
    String path = (String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 3);
                if (path.length() >0)
                    runApp(path);
                else
                    runBrowser((String) resourcesTable.getValueAt(resourcesTable.getSelectedRow(), 0));
  }
  
  /**
   * Pp remove res action performed.
   *
   * @param e the e
   */
  void ppRemoveRes_actionPerformed(ActionEvent e) {
    removeResB_actionPerformed(e);
  }
  
  /**
   * Pp new res action performed.
   *
   * @param e the e
   */
  void ppNewRes_actionPerformed(ActionEvent e) {
    newResB_actionPerformed(e);
  }

  /**
   * Pp refresh action performed.
   *
   * @param e the e
   */
  void ppRefresh_actionPerformed(ActionEvent e) {
     resourcesTable.tableChanged();
  }
  
  /**
   * Copy a file to the directory of the current project.
   *
   * @param srcStr The path of the source file.
   * @return The new path of the file.
   */
  String copyFileToProjectDir(String srcStr) {
	  
	  String JN_DOCPATH = Util.getEnvDir();	    
	  
	  String baseName;
	  int i = srcStr.lastIndexOf( File.separator );
		if ( i != -1 ) {
			baseName = srcStr.substring(i+1);
		} else
			baseName = srcStr;
		
	  String destStr = JN_DOCPATH + CurrentProject.get().getID() 
	  				   + File.separator + "_projectFiles" + File.separator + baseName;
	  
	  File f = new File(JN_DOCPATH + CurrentProject.get().getID() + File.separator + "_projectFiles");
	  if (!f.exists()) {
		  f.mkdirs();
	  }	  
	  System.out.println("[DEBUG] Copy file from: "+srcStr+" to: "+destStr);
	  
	  try {
         FileInputStream in = new FileInputStream(srcStr);
         FileOutputStream out = new FileOutputStream(destStr);
         byte[] buf = new byte[4096];
         int len;
         while ((len = in.read(buf)) > 0) {
           out.write(buf, 0, len);
         }
         out.close();
         in.close();
       } 
	   catch (IOException e) {
         System.err.println(e.toString());
       }
		     
  return destStr;
  }
}
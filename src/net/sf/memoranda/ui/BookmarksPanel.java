/**
 * BookmarksPanel.java
 * Created on 17.03.2003, 22:55:39 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

// TODO: Auto-generated Javadoc
/**
 * The Class BookmarksPanel.
 */
/*$Id: BookmarksPanel.java,v 1.4 2004/04/05 10:05:44 alexeya Exp $*/
public class BookmarksPanel extends JPanel {
  
  /** The border layout 1. */
  BorderLayout borderLayout1 = new BorderLayout();
  
  /** The scroll pane. */
  JScrollPane scrollPane = new JScrollPane();
  
  /** The notes list. */
  public NotesList notesList = new NotesList(NotesList.BOOKMARKS);

  /**
   * Instantiates a new bookmarks panel.
   */
  public BookmarksPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
  }
  
  /**
   * Jb init.
   *
   * @throws Exception the exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(scrollPane, BorderLayout.CENTER);
    scrollPane.getViewport().add(notesList, null);

    
  }


}

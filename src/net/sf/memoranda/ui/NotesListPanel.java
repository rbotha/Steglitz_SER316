package net.sf.memoranda.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

// TODO: Auto-generated Javadoc
/**
 * The Class NotesListPanel.
 */
/*$Id: NotesListPanel.java,v 1.5 2005/01/29 13:55:26 rawsushi Exp $*/
public class NotesListPanel extends JPanel {
  
  /** The border layout 1. */
  BorderLayout borderLayout1 = new BorderLayout();
  
  /** The scroll pane. */
  JScrollPane scrollPane = new JScrollPane();
  
  /** The notes list. */
  public NotesList notesList = new NotesList();

  /**
   * Instantiates a new notes list panel.
   */
  public NotesListPanel() {
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
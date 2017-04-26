package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class HTMLEditor.
 */
public class HTMLEditor extends JPanel {
	
	/** The editor. */
	public HTMLEditorPane editor = new HTMLEditorPane("");


    /** The j scroll pane 1. */
    JScrollPane jScrollPane1 = new JScrollPane();
    
    /** The editor kit. */
    public HTMLEditorKit editorKit = new HTMLEditorKit();
    
    /** The document. */
    public HTMLDocument document = null;

	/** The bold. */
	boolean bold = false;
	
	/** The italic. */
	boolean italic = false;
	
	/** The under. */
	boolean under = false;
	
	/** The list. */
	boolean list = false;

	/** The current tag name. */
	String currentTagName = "BODY";
	
	/** The current para element. */
	Element currentParaElement = null;

	/** The border 2. */
	Border border1, border2;

	/** The cl. */
	Class cl = net.sf.memoranda.ui.htmleditor.HTMLEditor.class;

	/** The images dir. */
	String imagesDir = null;
	
	/** The images path. */
	String imagesPath = null;

	/**
	 * Sets the images dir.
	 *
	 * @param path the new images dir
	 */
	public void setImagesDir(String path) {
		imagesDir = path;
	}

	/**
	 * Gets the images dir.
	 *
	 * @return the images dir
	 */
	public String getImagesDir() {
		return imagesDir;
	}

	/**
	 * The Class HTMLEditorAction.
	 */
	abstract class HTMLEditorAction extends AbstractAction {
		
		/**
		 * Instantiates a new HTML editor action.
		 *
		 * @param name the name
		 * @param icon the icon
		 */
		HTMLEditorAction(String name, ImageIcon icon) {
			super(name, icon);
			super.putValue(Action.SHORT_DESCRIPTION, name);
		}
		
		/**
		 * Instantiates a new HTML editor action.
		 *
		 * @param name the name
		 */
		HTMLEditorAction(String name) {
			super(name);
			super.putValue(Action.SHORT_DESCRIPTION, name);
		}
	}

	/** The bold action. */
	public Action boldAction =
		new HTMLEditorAction(
			Local.getString("Bold"),
			new ImageIcon(cl.getResource("resources/icons/bold.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      boldActionB_actionPerformed(e);
                  }
                };

	/** The italic action. */
	public Action italicAction =
		new HTMLEditorAction(
			Local.getString("Italic"),
			new ImageIcon(cl.getResource("resources/icons/italic.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      italicActionB_actionPerformed(e);
                  }
	            };

	/** The under action. */
	public Action underAction =
		new HTMLEditorAction(
			Local.getString("Underline"),
			new ImageIcon(cl.getResource("resources/icons/underline.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      underActionB_actionPerformed(e);
                  }
                };

	/** The ul action. */
	public Action ulAction =
		new HTMLEditorAction(
			Local.getString("Unordered list"),
			new ImageIcon(
				cl.getResource("resources/icons/listunordered.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      ulActionB_actionPerformed(e);
                  }
                };

	/** The ol action. */
	public Action olAction =
		new HTMLEditorAction(
			Local.getString("Ordered list"),
			new ImageIcon(cl.getResource("resources/icons/listordered.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      olActionB_actionPerformed(e);
                  }
                };

	/** The l align action. */
	public Action lAlignAction =
		new HTMLEditorAction(
			Local.getString("Align left"),
			new ImageIcon(cl.getResource("resources/icons/alignleft.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      lAlignActionB_actionPerformed(e);
                  }
                };

	/** The c align action. */
	public Action cAlignAction =
		new HTMLEditorAction(
			Local.getString("Align center"),
			new ImageIcon(cl.getResource("resources/icons/aligncenter.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      cAlignActionB_actionPerformed(e);
                  }
                };

	/** The r align action. */
	public Action rAlignAction =
		new HTMLEditorAction(
			Local.getString("Align right"),
			new ImageIcon(cl.getResource("resources/icons/alignright.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      rAlignActionB_actionPerformed(e);
                  }
                };

	/** The image action. */
	/*
	 * public Action jAlignAction = new AbstractAction() { public void
	 * actionPerformed(ActionEvent e) { jAlignActionB_actionPerformed(e); }
	 */
	public Action imageAction =
		new HTMLEditorAction(
			Local.getString("Insert image"),
			new ImageIcon(cl.getResource("resources/icons/image.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      imageActionB_actionPerformed(e);
                  }
                };

	/** The table action. */
	public Action tableAction =
		new HTMLEditorAction(
			Local.getString("Insert table"),
			new ImageIcon(cl.getResource("resources/icons/table.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      tableActionB_actionPerformed(e);
                  }
                };

	/** The link action. */
	public Action linkAction =
		new HTMLEditorAction(
			Local.getString("Insert hyperlink"),
			new ImageIcon(cl.getResource("resources/icons/link.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      linkActionB_actionPerformed(e);
                  }
                };

	/** The props action. */
	public Action propsAction =
		new HTMLEditorAction(
			Local.getString("Object properties"),
			new ImageIcon(cl.getResource("resources/icons/properties.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      propsActionB_actionPerformed(e);
                  }
                };

	/** The select all action. */
	public Action selectAllAction =
		new HTMLEditorAction(Local.getString("Select all")) {
                  public void actionPerformed(ActionEvent e) {
                      editor.selectAll();
                  }
                };

	/** The insert HR action. */
	public Action insertHRAction =
		new HTMLEditorAction(
			Local.getString("Insert horizontal rule"),
			new ImageIcon(cl.getResource("resources/icons/hr.png"))) {
                  public void actionPerformed(ActionEvent e) {
        			 /*
        			 * String elName =
        			 * document.getParagraphElement(editor.getCaretPosition()).getName();
        			 * HTML.Tag tag = HTML.getTag(elName); if
        			 * (elName.toUpperCase().equals("P-IMPLIED")) tag =
        			 * HTML.Tag.IMPLIED; HTMLEditorKit.InsertHTMLTextAction hta = new
        			 * HTMLEditorKit.InsertHTMLTextAction("insertHR", " <hr> ", tag,
        			 * HTML.Tag.HR);
        			 */
                    try {
                         editorKit.insertHTML(
                                 document,
                                 editor.getCaretPosition(),
                                 "<hr>",
                                 0,
                                 0,
                                 HTML.Tag.HR);
                    } catch (Exception ex) {
                         ex.printStackTrace();
                    }

                  }
                };

	/** The char table panel. */
	CharTablePanel charTablePanel = new CharTablePanel(editor);

	/** The char table show. */
	boolean charTableShow = false;

	/** The tools panel. */
	public JTabbedPane toolsPanel = new JTabbedPane();
	
	/** The tools panel show. */
	public boolean toolsPanelShow = false;

	/**
	 * Show tools panel.
	 */
	public void showToolsPanel() {
		if (toolsPanelShow)
			return;
		this.add(toolsPanel, BorderLayout.SOUTH);
		toolsPanelShow = true;
	}

	/**
	 * Hide tools panel.
	 */
	public void hideToolsPanel() {
		if (!toolsPanelShow)
			return;
		this.remove(charTablePanel);
		toolsPanelShow = false;
	}

	/**
	 * Adds the char table panel.
	 */
	void addCharTablePanel() {
		showToolsPanel();
		toolsPanel.addTab(Local.getString("Characters"), charTablePanel);
	}

	/**
	 * Removes the char table panel.
	 */
	void removeCharTablePanel() {
		toolsPanel.remove(charTablePanel);
		if (toolsPanel.getTabCount() == 0)
			hideToolsPanel();
	}

	/** The ins char action. */
	public Action insCharAction =
		new HTMLEditorAction(
			Local.getString("Insert character"),
			new ImageIcon(cl.getResource("resources/icons/char.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      if (!charTableShow) {
                          addCharTablePanel();
                          charTableShow = true;
                          insCharActionB.setBorder(border2);
                      } else {
                          removeCharTablePanel();
                          charTableShow = false;
                          insCharActionB.setBorder(border1);
                      }
                      insCharActionB.setBorderPainted(charTableShow);
                  }
                };

	/** The find action. */
	public Action findAction =
		new HTMLEditorAction(
			Local.getString("Find & Replace"),
			new ImageIcon(cl.getResource("resources/icons/find.png"))) {
                  public void actionPerformed(ActionEvent e) {
                      doFind();
                  }
                };

	/** The insert table cell action. */
	public InsertTableCellAction insertTableCellAction =
		new InsertTableCellAction();
	
	/** The insert table row action. */
	public InsertTableRowAction insertTableRowAction =
		new InsertTableRowAction();
	
	/** The break action. */
	public BreakAction breakAction = new BreakAction();

	/** The cut action. */
	public Action cutAction = new HTMLEditorKit.CutAction();
	/*
	 * new AbstractAction() { public void actionPerformed(ActionEvent e) { if
	 * (editor.getSelectedText() == null) return; doCopy();
	 * editor.replaceSelection(""); }
	 *  
	 */

	/** The style copy action. */
	public Action styleCopyAction = new HTMLEditorKit.CopyAction();
	//new DefaultEditorKit.CopyAction();

	/** The copy action. */
	public Action copyAction = styleCopyAction;
	/*
	 * new AbstractAction() { public void actionPerformed(ActionEvent e) { if
	 * (editor.getSelectedText() == null) return; doCopy(); }
	 */

	/** The style paste action. */
	public Action stylePasteAction = new HTMLEditorKit.PasteAction();

		/** The paste action. */
		public Action pasteAction = //new HTMLEditorKit.PasteAction();

	new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              //editor.paste();
              doPaste();
          }
	};

	/**
	 * Do copy.
	 */
	private void doCopy() {
		/*
		 * java.awt.datatransfer.Clipboard clip =
		 * java.awt.Toolkit.getDefaultToolkit().getSystemClipboard(); try {
		 * String text = editor.getSelectedText();
		 * //.getText(editor.getSelectionStart(),
		 * editor.getSelectionEnd()-editor.getSelectionStart());
		 * clip.setContents(new java.awt.datatransfer.StringSelection(text),
		 * null); } catch (Exception e) { e.printStackTrace();
		 */
		Element el = document.getParagraphElement(editor.getSelectionStart());
		if (el.getName().toUpperCase().equals("P-IMPLIED"))
			el = el.getParentElement();
		String elName = el.getName();
		StringWriter sw = new StringWriter();
		String copy;
		java.awt.datatransfer.Clipboard clip =
			java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			editorKit.write(
				sw,
				document,
				editor.getSelectionStart(),
				editor.getSelectionEnd() - editor.getSelectionStart());
			copy = sw.toString();
			copy = copy.split("<" + elName + "(.*?)>")[1];
			copy = copy.split("</" + elName + ">")[0];
			clip.setContents(
				new java.awt.datatransfer.StringSelection(copy.trim()),
				null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Do paste.
	 */
	private void doPaste() {
		Clipboard clip =
			java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			Transferable content = clip.getContents(this);
			if (content == null)
				return;
			String txt =
				content
					.getTransferData(new DataFlavor(String.class, "String"))
					.toString();
			document.replace(
				editor.getSelectionStart(),
				editor.getSelectionEnd() - editor.getSelectionStart(),
				txt,
				editorKit.getInputAttributes());
			//editor.replaceSelection(content.getTransferData(new
			// DataFlavor(String.class, "String")).toString());
			//editor.paste();
			//insertHTML(content.getTransferData(new DataFlavor(String.class,
			// "String")).toString(), editor.getCaretPosition());
			/*
			 * Element el =
			 * document.getParagraphElement(editor.getCaretPosition());
			 * insertTextInElement(el, content.getTransferData(new
			 * DataFlavor(String.class, "String")).toString(),
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * private void insertTextInElement(Element el, String text, int pos) {
	 * String elName = el.getName(); StringWriter sw = new StringWriter();
	 * String copy1; String copy2; try { StringWriter sw1 = new StringWriter();
	 * editorKit.write(sw1, document, el.getStartOffset(), pos -
	 * el.getStartOffset()); copy1 = sw1.toString(); StringWriter sw2 = new
	 * StringWriter(); editorKit.write(sw2, document, pos, el.getEndOffset() -
	 * pos); copy2 = sw2.toString(); String copy = copy1+text+copy2; ?)>")[1];
	 * copy = copy.split(" </" + elName + "> ")[0]; document.setInnerHTML(el,
	 * copy); } catch (Exception ex) { ex.printStackTrace(); }
	 */

	/** The zoom in action. */
	public Action zoomInAction = new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              doZoom(true);
          }
	};

	/** The zoom out action. */
	public Action zoomOutAction = new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              doZoom(false);
          }
	};

	/**
	 * Listener for the edits on the current document.
	 */
	protected UndoableEditListener undoHandler = new UndoHandler();

	/** UndoManager that we add edits to. */
	protected UndoManager undo = new UndoManager();

	/** The undo action. */
	public UndoAction undoAction = new UndoAction();
	
	/** The redo action. */
	public RedoAction redoAction = new RedoAction();

	/** The j align action B. */
	JButton jAlignActionB = new JButton();
	
	/** The edit toolbar. */
	public JToolBar editToolbar = new JToolBar();
	
	/** The l align action B. */
	JButton lAlignActionB = new JButton();
	
	/** The ol action B. */
	JButton olActionB = new JButton();
	
	/** The link action B. */
	JButton linkActionB = new JButton();
	
	/** The italic action B. */
	JButton italicActionB = new JButton();
	
	/** The props action B. */
	JButton propsActionB = new JButton();
	
	/** The image action B. */
	JButton imageActionB = new JButton();

	/** The t p. */
	public final int T_P = 0;
	
	/** The t h1. */
	public final int T_H1 = 1;
	
	/** The t h2. */
	public final int T_H2 = 2;
	
	/** The t h3. */
	public final int T_H3 = 3;
	
	/** The t h4. */
	public final int T_H4 = 4;
	
	/** The t h5. */
	public final int T_H5 = 5;
	
	/** The t h6. */
	public final int T_H6 = 6;
	
	/** The t pre. */
	public final int T_PRE = 7;
	
	/** The t blockq. */
	//private final int T_ADDRESS = 8;
	public final int T_BLOCKQ = 8; //9;

	/** The element types. */
	String[] elementTypes =
        {
			Local.getString("Paragraph"),
			Local.getString("Header") + " 1",
			Local.getString("Header") + " 2",
			Local.getString("Header") + " 3",
			Local.getString("Header") + " 4",
			Local.getString("Header") + " 5",
			Local.getString("Header") + " 6",
			Local.getString("Preformatted"),
		//"Address",
		Local.getString("Blockquote")};
	
	/** The block CB. */
	public JComboBox blockCB = new JComboBox(elementTypes);
	
	/** The block CB events lock. */
	boolean blockCBEventsLock = false;

	/** The i normal. */
	public final int I_NORMAL = 0;
	
	/** The i em. */
	public final int I_EM = 1;
	
	/** The i strong. */
	public final int I_STRONG = 2;
	
	/** The i code. */
	public final int I_CODE = 3;
	
	/** The i cite. */
	public final int I_CITE = 4;
	
	/** The i superscript. */
	public final int I_SUPERSCRIPT = 5;
	
	/** The i subscript. */
	public final int I_SUBSCRIPT = 6;
	
	/** The i custom. */
	public final int I_CUSTOM = 7;

	/** The inline types. */
	String[] inlineTypes =
        {
			Local.getString("Normal"),
			Local.getString("Emphasis"),
			Local.getString("Strong"),
			Local.getString("Code"),
			Local.getString("Cite"),
			Local.getString("Superscript"),
			Local.getString("Subscript"),
			Local.getString("Custom style") + "..." };
	
	/** The inline CB. */
	public JComboBox inlineCB = new JComboBox(inlineTypes);
	
	/** The inline CB events lock. */
	boolean inlineCBEventsLock = false;

	/** The bold action B. */
	JButton boldActionB = new JButton();
	
	/** The ul action B. */
	JButton ulActionB = new JButton();
	
	/** The r align action B. */
	JButton rAlignActionB = new JButton();
	
	/** The table action B. */
	JButton tableActionB = new JButton();
	
	/** The c align action B. */
	JButton cAlignActionB = new JButton();
	
	/** The under action B. */
	JButton underActionB = new JButton();
	
	/** The border layout 1. */
	BorderLayout borderLayout1 = new BorderLayout();
	
	/** The default popup menu. */
	JPopupMenu defaultPopupMenu = new JPopupMenu();
	//JPopupMenu tablePopupMenu = new JPopupMenu();

	/** The j menu item undo. */
	JMenuItem jMenuItemUndo = new JMenuItem(undoAction);
	
	/** The j menu item redo. */
	JMenuItem jMenuItemRedo = new JMenuItem(redoAction);

	/** The j menu item cut. */
	JMenuItem jMenuItemCut = new JMenuItem(cutAction);
	
	/** The j menu item copy. */
	JMenuItem jMenuItemCopy = new JMenuItem(copyAction);
	
	/** The j menu item paste. */
	JMenuItem jMenuItemPaste = new JMenuItem(pasteAction);
	
	/** The j menu item prop. */
	JMenuItem jMenuItemProp = new JMenuItem(propsAction);

	/** The j menu item ins cell. */
	JMenuItem jMenuItemInsCell = new JMenuItem(insertTableCellAction);
	
	/** The j menu item ins row. */
	JMenuItem jMenuItemInsRow = new JMenuItem(insertTableRowAction);

	/** The current caret. */
	int currentCaret = 0;

	/** The current font size. */
	int currentFontSize = 4;
	
	/** The br action B. */
	JButton brActionB = new JButton();
	
	/** The hr action B. */
	JButton hrActionB = new JButton();
	
	/** The ins char action B. */
	JButton insCharActionB = new JButton();

	/**
	 * Instantiates a new HTML editor.
	 */
	public HTMLEditor() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Jb init.
	 *
	 * @throws Exception the exception
	 */
	void jbInit() throws Exception {

		cutAction.putValue(
			Action.SMALL_ICON,
			new ImageIcon(cl.getResource("resources/icons/cut.png")));
		cutAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		cutAction.putValue(Action.NAME, Local.getString("Cut"));
		cutAction.putValue(Action.SHORT_DESCRIPTION, Local.getString("Cut"));

		copyAction.putValue(
			Action.SMALL_ICON,
			new ImageIcon(cl.getResource("resources/icons/copy.png")));
		copyAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		copyAction.putValue(Action.NAME, Local.getString("Copy"));
		copyAction.putValue(Action.SHORT_DESCRIPTION, Local.getString("Copy"));

		pasteAction.putValue(
			Action.SMALL_ICON,
			new ImageIcon(cl.getResource("resources/icons/paste.png")));
		pasteAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		pasteAction.putValue(Action.NAME, Local.getString("Paste"));
		pasteAction.putValue(
			Action.SHORT_DESCRIPTION,
			Local.getString("Paste"));

		stylePasteAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(
				KeyEvent.VK_V,
				KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
		stylePasteAction.putValue(
			Action.NAME,
			Local.getString("Paste special"));
		stylePasteAction.putValue(
			Action.SHORT_DESCRIPTION,
			Local.getString("Paste special"));

		selectAllAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

		boldAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
		italicAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
		underAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
		breakAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK));
		breakAction.putValue(Action.NAME, Local.getString("Insert Break"));
		breakAction.putValue(
			Action.SHORT_DESCRIPTION,
			Local.getString("Insert Break"));

		findAction.putValue(
			Action.ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));

		document = (HTMLDocument) editorKit.createDefaultDocument();

		border1 =
			BorderFactory.createEtchedBorder(
				Color.white,
				new Color(142, 142, 142));
		border2 =
			BorderFactory.createBevelBorder(
				BevelBorder.LOWERED,
				Color.white,
				Color.white,
				new Color(142, 142, 142),
				new Color(99, 99, 99));
		this.setLayout(borderLayout1);

		/*
		 * jAlignActionB.setIcon( new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/alignjust.png")));
		 * jAlignActionB.setMaximumSize(new Dimension(22, 22));
		 * jAlignActionB.setMinimumSize(new Dimension(22, 22));
		 * jAlignActionB.setPreferredSize(new Dimension(22, 22));
		 * jAlignActionB.setFocusable(false);
		 */

		editor.addCaretListener(new CaretListener() {
		    public void caretUpdate(CaretEvent e) {
				editor_caretUpdate(e);
		    }
		});

		editor.setEditorKit(editorKit);
		editorKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));

		editor.setDocument(document);
		document.addUndoableEditListener(undoHandler);

		this.setPreferredSize(new Dimension(520, 57));
		editToolbar.setRequestFocusEnabled(false);
		editToolbar.setToolTipText("");

		boldActionB.setAction(boldAction);
		boldActionB.setBorder(border1);
		boldActionB.setMaximumSize(new Dimension(22, 22));
		boldActionB.setMinimumSize(new Dimension(22, 22));
		boldActionB.setPreferredSize(new Dimension(22, 22));
		boldActionB.setBorderPainted(false);
		boldActionB.setFocusable(false);
		boldActionB.setText("");

		italicActionB.setAction(italicAction);
		italicActionB.setBorder(border1);
		italicActionB.setMaximumSize(new Dimension(22, 22));
		italicActionB.setMinimumSize(new Dimension(22, 22));
		italicActionB.setPreferredSize(new Dimension(22, 22));
		italicActionB.setBorderPainted(false);
		italicActionB.setFocusable(false);
		italicActionB.setText("");

		underActionB.setAction(underAction);
		underActionB.setBorder(border1);
		underActionB.setMaximumSize(new Dimension(22, 22));
		underActionB.setMinimumSize(new Dimension(22, 22));
		underActionB.setPreferredSize(new Dimension(22, 22));
		underActionB.setBorderPainted(false);
		underActionB.setFocusable(false);
		underActionB.setText("");

		lAlignActionB.setAction(lAlignAction);
		lAlignActionB.setMaximumSize(new Dimension(22, 22));
		lAlignActionB.setMinimumSize(new Dimension(22, 22));
		lAlignActionB.setPreferredSize(new Dimension(22, 22));
		lAlignActionB.setBorderPainted(false);
		lAlignActionB.setFocusable(false);
		lAlignActionB.setText("");

		rAlignActionB.setAction(rAlignAction);
		rAlignActionB.setFocusable(false);
		rAlignActionB.setPreferredSize(new Dimension(22, 22));
		rAlignActionB.setBorderPainted(false);
		rAlignActionB.setMinimumSize(new Dimension(22, 22));
		rAlignActionB.setMaximumSize(new Dimension(22, 22));
		rAlignActionB.setText("");

		cAlignActionB.setAction(cAlignAction);
		cAlignActionB.setMaximumSize(new Dimension(22, 22));
		cAlignActionB.setMinimumSize(new Dimension(22, 22));
		cAlignActionB.setPreferredSize(new Dimension(22, 22));
		cAlignActionB.setBorderPainted(false);
		cAlignActionB.setFocusable(false);
		cAlignActionB.setText("");

		ulActionB.setAction(ulAction);
		ulActionB.setMaximumSize(new Dimension(22, 22));
		ulActionB.setMinimumSize(new Dimension(22, 22));
		ulActionB.setPreferredSize(new Dimension(22, 22));
		ulActionB.setBorderPainted(false);
		ulActionB.setFocusable(false);
		ulActionB.setText("");

		olActionB.setAction(olAction);
		olActionB.setMaximumSize(new Dimension(22, 22));
		olActionB.setMinimumSize(new Dimension(22, 22));
		olActionB.setPreferredSize(new Dimension(22, 22));
		olActionB.setBorderPainted(false);
		olActionB.setFocusable(false);
		olActionB.setText("");

		linkActionB.setAction(linkAction);
		linkActionB.setMaximumSize(new Dimension(22, 22));
		linkActionB.setMinimumSize(new Dimension(22, 22));
		linkActionB.setPreferredSize(new Dimension(22, 22));
		linkActionB.setBorderPainted(false);
		linkActionB.setFocusable(false);
		linkActionB.setText("");

		propsActionB.setAction(propsAction);
		propsActionB.setFocusable(false);
		propsActionB.setPreferredSize(new Dimension(22, 22));
		propsActionB.setBorderPainted(false);
		propsActionB.setMinimumSize(new Dimension(22, 22));
		propsActionB.setMaximumSize(new Dimension(22, 22));
		propsActionB.setText("");

		imageActionB.setAction(imageAction);
		imageActionB.setMaximumSize(new Dimension(22, 22));
		imageActionB.setMinimumSize(new Dimension(22, 22));
		imageActionB.setPreferredSize(new Dimension(22, 22));
		imageActionB.setBorderPainted(false);
		imageActionB.setFocusable(false);
		imageActionB.setText("");

		tableActionB.setAction(tableAction);
		tableActionB.setFocusable(false);
		tableActionB.setPreferredSize(new Dimension(22, 22));
		tableActionB.setBorderPainted(false);
		tableActionB.setMinimumSize(new Dimension(22, 22));
		tableActionB.setMaximumSize(new Dimension(22, 22));
		tableActionB.setText("");

		brActionB.setAction(breakAction);
		brActionB.setFocusable(false);
		brActionB.setBorderPainted(false);
		brActionB.setPreferredSize(new Dimension(22, 22));
		brActionB.setMinimumSize(new Dimension(22, 22));
		brActionB.setMaximumSize(new Dimension(22, 22));
		brActionB.setText("");

		hrActionB.setAction(insertHRAction);
		hrActionB.setMaximumSize(new Dimension(22, 22));
		hrActionB.setMinimumSize(new Dimension(22, 22));
		hrActionB.setPreferredSize(new Dimension(22, 22));
		hrActionB.setBorderPainted(false);
		hrActionB.setFocusable(false);
		hrActionB.setText("");

		insCharActionB.setAction(insCharAction);
		insCharActionB.setBorder(border1);
		insCharActionB.setMaximumSize(new Dimension(22, 22));
		insCharActionB.setMinimumSize(new Dimension(22, 22));
		insCharActionB.setPreferredSize(new Dimension(22, 22));
		insCharActionB.setBorderPainted(false);
		insCharActionB.setFocusable(false);
		insCharActionB.setText("");

		blockCB.setBackground(new Color(230, 230, 230));
		blockCB.setMaximumRowCount(12);
		blockCB.setFont(new java.awt.Font("Dialog", 1, 10));
		blockCB.setMaximumSize(new Dimension(120, 22));
		blockCB.setMinimumSize(new Dimension(60, 22));
		blockCB.setPreferredSize(new Dimension(79, 22));
		blockCB.setFocusable(false);
		blockCB.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				blockCB_actionPerformed(e);
		    }
		});

		inlineCB.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				inlineCB_actionPerformed(e);
		    }
		});
		inlineCB.setFocusable(false);
		inlineCB.setPreferredSize(new Dimension(79, 22));
		inlineCB.setMinimumSize(new Dimension(60, 22));
		inlineCB.setMaximumSize(new Dimension(120, 22));
		inlineCB.setFont(new java.awt.Font("Dialog", 1, 10));
		inlineCB.setMaximumRowCount(12);
		inlineCB.setBackground(new Color(230, 230, 230));

		this.add(jScrollPane1, BorderLayout.CENTER);
		this.add(editToolbar, BorderLayout.NORTH);

		editToolbar.add(propsActionB, null);
		editToolbar.addSeparator();
		editToolbar.add(blockCB, null);

		editToolbar.addSeparator();
		editToolbar.add(inlineCB, null);
		editToolbar.addSeparator();
		editToolbar.add(boldActionB, null);
		editToolbar.add(italicActionB, null);
		editToolbar.add(underActionB, null);
		editToolbar.addSeparator();
		editToolbar.add(ulActionB, null);
		editToolbar.add(olActionB, null);
		editToolbar.addSeparator();
		editToolbar.add(lAlignActionB, null);
		editToolbar.add(cAlignActionB, null);
		editToolbar.add(rAlignActionB, null);
		editToolbar.addSeparator();
		editToolbar.add(imageActionB, null);
		editToolbar.add(tableActionB, null);
		editToolbar.add(linkActionB, null);
		editToolbar.addSeparator();
		editToolbar.add(hrActionB, null);
		editToolbar.add(brActionB, null);
		editToolbar.add(insCharActionB, null);

		jScrollPane1.getViewport().add(editor, null);

		toolsPanel.setTabPlacement(JTabbedPane.BOTTOM);
		toolsPanel.setFont(new Font("Dialog", 1, 10));

		//  editToolbar.add(jAlignActionB, null);

		/* KEY ACTIONS */

		/*
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK),
		 */

		editor.getKeymap().removeKeyStrokeBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
			new ParaBreakAction());

		/*
		 * editor.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_B,
		 * KeyEvent.CTRL_MASK), boldAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK),
		 * italicAction); editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK),
		 */

		/*
		 * editor.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
		 * KeyEvent.CTRL_MASK), undoAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK +
		 * KeyEvent.SHIFT_MASK), redoAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK +
		 * KeyEvent.SHIFT_MASK), insertTableCellAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK),
		 */

		editor.getKeymap().removeKeyStrokeBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		editor.getKeymap().removeKeyStrokeBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		editor.getKeymap().removeKeyStrokeBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));

		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK),
			copyAction);
		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK),
			pasteAction);
		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(
				KeyEvent.VK_V,
				KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK),
			stylePasteAction);
		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK),
			cutAction);

		editor.getKeymap().addActionForKeyStroke(
			KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK),
			findAction);
		/*
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_MASK),
		 * zoomInAction); editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_MASK),
		 */

		/* POPUP MENUs */

		/*
		 * jMenuItemUndo.setAction(undoAction); jMenuItemUndo.setText("Undo");
		 * jMenuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
		 * KeyEvent.CTRL_MASK)); jMenuItemUndo.setIcon( new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.AppFrame.class.getResource("resources/icons/undo16.png")));
		 * 
		 * jMenuItemRedo.setAction(redoAction); jMenuItemRedo.setText("Redo");
		 * jMenuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
		 * KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK)); jMenuItemRedo.setIcon(
		 * new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.AppFrame.class.getResource("resources/icons/redo16.png")));
		 * 
		 * jMenuItemCut.setAction(cutAction); jMenuItemCut.setText("Cut");
		 * jMenuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
		 * KeyEvent.CTRL_MASK)); jMenuItemCut.setIcon( new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.AppFrame.class.getResource("resources/icons/cut.png")));
		 * 
		 * jMenuItemCopy.setAction(copyAction); jMenuItemCopy.setText("Copy");
		 * jMenuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
		 * KeyEvent.CTRL_MASK)); jMenuItemCopy.setIcon( new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.AppFrame.class.getResource("resources/icons/copy.png")));
		 * 
		 * jMenuItemPaste.setAction(pasteAction);
		 * jMenuItemPaste.setText("Paste");
		 * jMenuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
		 * KeyEvent.CTRL_MASK)); jMenuItemPaste.setIcon( new
		 * ImageIcon(net.sf.memoranda.ui.htmleditor.AppFrame.class.getResource("resources/icons/paste.png")));
		 * 
		 * jMenuItemProp.setAction(propsAction);
		 * jMenuItemProp.setText("Properties"); jMenuItemProp.setIcon(
		 */

		/*
		 * defaultPopupMenu.add(jMenuItemUndo);
		 * defaultPopupMenu.add(jMenuItemRedo);
		 * defaultPopupMenu.addSeparator(); defaultPopupMenu.add(jMenuItemCut);
		 * defaultPopupMenu.add(jMenuItemCopy);
		 * defaultPopupMenu.add(jMenuItemPaste);
		 * defaultPopupMenu.addSeparator();
		 */

		/*
		 * jMenuItemInsCell.setAction(new InsertTableCellAction());
		 * jMenuItemInsCell.setText(Local.getString("Insert table cell"));
		 * 
		 * jMenuItemInsRow.setAction(new InsertTableRowAction());
		 */

		/*
		 * tablePopupMenu.add(jMenuItemUndo);
		 * tablePopupMenu.add(jMenuItemRedo); tablePopupMenu.addSeparator();
		 * tablePopupMenu.add(jMenuItemCut); tablePopupMenu.add(jMenuItemCopy);
		 * tablePopupMenu.add(jMenuItemPaste); tablePopupMenu.addSeparator();
		 * tablePopupMenu.add(jMenuItemInsCell);
		 * tablePopupMenu.add(jMenuItemInsRow); tablePopupMenu.addSeparator();
		 */

		editor.addMouseListener(new PopupListener());

		document.getStyleSheet().setBaseFontSize(currentFontSize);
		this.requestFocusInWindow();
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
				JPopupMenu popupMenu = new JPopupMenu();
				popupMenu.setFocusable(false);

				popupMenu.add(jMenuItemUndo);
				popupMenu.add(jMenuItemRedo);
				popupMenu.addSeparator();
				popupMenu.add(jMenuItemCut);
				popupMenu.add(jMenuItemCopy);
				popupMenu.add(jMenuItemPaste);
				popupMenu.addSeparator();
				if (jMenuItemInsCell.getAction().isEnabled()) {
					popupMenu.add(jMenuItemInsCell);
					jMenuItemInsCell.setEnabled(true);
					popupMenu.add(jMenuItemInsRow);
					jMenuItemInsRow.setEnabled(true);
					popupMenu.addSeparator();
				}
				popupMenu.add(jMenuItemProp);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());

			}
		}
	}

	/**
	 * Resets the undo manager.
	 */
	protected void resetUndoManager() {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
	}

	/**
	 * The Class UndoHandler.
	 */
	class UndoHandler implements UndoableEditListener {

		/**
		 * Messaged when the Document has created an edit, the edit is added to
		 * <code>undo</code>, an instance of UndoManager.
		 *
		 * @param e the e
		 */
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}

	/**
	 * The Class UndoAction.
	 */
	class UndoAction extends AbstractAction {
		
		/**
		 * Instantiates a new undo action.
		 */
		public UndoAction() {
			super(Local.getString("Undo"));
			setEnabled(false);
			putValue(
				Action.SMALL_ICON,
				new ImageIcon(cl.getResource("resources/icons/undo16.png")));
			putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			update();
			redoAction.update();
		}

		/**
		 * Update.
		 */
		protected void update() {
			if (undo.canUndo()) {
				setEnabled(true);
				putValue(
					Action.SHORT_DESCRIPTION,
					undo.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.SHORT_DESCRIPTION, Local.getString("Undo"));
			}
		}
	}

	/**
	 * The Class RedoAction.
	 */
	class RedoAction extends AbstractAction {
		
		/**
		 * Instantiates a new redo action.
		 */
		public RedoAction() {
			super(Local.getString("Redo"));
			setEnabled(false);
			putValue(
				Action.SMALL_ICON,
				new ImageIcon(cl.getResource("resources/icons/redo16.png")));
			putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(
					KeyEvent.VK_Z,
					KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotRedoException ex) {
				System.out.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			update();
			undoAction.update();
		}

		/**
		 * Update.
		 */
		protected void update() {
			if (undo.canRedo()) {
				setEnabled(true);
				putValue(
					Action.SHORT_DESCRIPTION,
					undo.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.SHORT_DESCRIPTION, Local.getString("Redo"));
			}
		}
	}

	/**
	 * The Class BlockAction.
	 */
	public class BlockAction extends AbstractAction {
		
		/** The type. */
		int _type;

		/**
		 * Instantiates a new block action.
		 *
		 * @param type the type
		 * @param name the name
		 */
		public BlockAction(int type, String name) {
			super(name);
			_type = type;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			blockCB.setSelectedIndex(_type);
		}
	}

	/**
	 * The Class InlineAction.
	 */
	public class InlineAction extends AbstractAction {
		
		/** The type. */
		int _type;

		/**
		 * Instantiates a new inline action.
		 *
		 * @param type the type
		 * @param name the name
		 */
		public InlineAction(int type, String name) {
			super(name);
			_type = type;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			inlineCB.setSelectedIndex(_type);
		}
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		try {
			return editor.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Bold action B action performed.
	 *
	 * @param e the e
	 */
	public void boldActionB_actionPerformed(ActionEvent e) {
		if (!bold) {
			boldActionB.setBorder(border2);
		} else {
			boldActionB.setBorder(border1);
		}
		bold = !bold;
		boldActionB.setBorderPainted(bold);
		/*
		 * SimpleAttributeSet attrs = new SimpleAttributeSet();
		 * attrs.addAttribute(StyleConstants.Bold, new Boolean(bold));
		 */
		new StyledEditorKit.BoldAction().actionPerformed(e);
	}

	/**
	 * Italic action B action performed.
	 *
	 * @param e the e
	 */
	public void italicActionB_actionPerformed(ActionEvent e) {
		if (!italic) {
			italicActionB.setBorder(border2);
		} else {
			italicActionB.setBorder(border1);
		}
		italic = !italic;
		italicActionB.setBorderPainted(italic);
		/*
		 * SimpleAttributeSet attrs = new SimpleAttributeSet();
		 * attrs.addAttribute(StyleConstants.Italic, new Boolean(italic));
		 */
		new StyledEditorKit.ItalicAction().actionPerformed(e);
	}

	/**
	 * Under action B action performed.
	 *
	 * @param e the e
	 */
	public void underActionB_actionPerformed(ActionEvent e) {
		if (!under) {
			underActionB.setBorder(border2);
		} else {
			underActionB.setBorder(border1);
		}
		under = !under;
		underActionB.setBorderPainted(under);
		/*
		 * SimpleAttributeSet attrs = new SimpleAttributeSet();
		 * attrs.addAttribute(StyleConstants.Underline, new Boolean(under));
		 */

		new StyledEditorKit.UnderlineAction().actionPerformed(e);
	}

	/**
	 * Editor caret update.
	 *
	 * @param e the e
	 */
	void editor_caretUpdate(CaretEvent e) {
		currentCaret = e.getDot();
		/*
		 * currentParaElement =
		 * document.getParagraphElement(editor.getCaretPosition());
		 * currentTagName = currentParaElement.getName().toUpperCase();
		 */

		AttributeSet charattrs = null;
		//System.out.print(editor.getCaretPosition()+" :
		// "+document.getCharacterElement(editor.getCaretPosition()).getName()+"
		// -> ");
		if (editor.getCaretPosition() > 0)
			try {
				charattrs =
					document
						.getCharacterElement(editor.getCaretPosition() - 1)
						.getAttributes();
			} catch (Exception ex) {
				ex.printStackTrace();
			} else
			charattrs =
				document
					.getCharacterElement(editor.getCaretPosition())
					.getAttributes();

		if (charattrs
			.containsAttribute(StyleConstants.Bold, new Boolean(true))) {
			boldActionB.setBorder(border2);
			bold = true;
		} else if (bold) {
			boldActionB.setBorder(border1);
			bold = false;
		}
		boldActionB.setBorderPainted(bold);
		if (charattrs
			.containsAttribute(StyleConstants.Italic, new Boolean(true))) {
			italicActionB.setBorder(border2);
			italic = true;
		} else if (italic) {
			italicActionB.setBorder(border1);
			italic = false;
		}
		italicActionB.setBorderPainted(italic);
		if (charattrs
			.containsAttribute(StyleConstants.Underline, new Boolean(true))) {
			underActionB.setBorder(border2);
			under = true;
		} else if (under) {
			underActionB.setBorder(border1);
			under = false;
		}
		underActionB.setBorderPainted(under);
		/*
		 * String iName = document
		 * .getCharacterElement(editor.getCaretPosition()) .getAttributes()
		 * .getAttribute(StyleConstants.NameAttribute) .toString()
		 */
		inlineCBEventsLock = true;
		inlineCB.setEnabled(!charattrs.isDefined(HTML.Tag.A));
		if (charattrs.isDefined(HTML.Tag.EM))
			inlineCB.setSelectedIndex(I_EM);
		else if (charattrs.isDefined(HTML.Tag.STRONG))
			inlineCB.setSelectedIndex(I_STRONG);
		else if (
			(charattrs.isDefined(HTML.Tag.CODE))
				|| (charattrs.isDefined(HTML.Tag.SAMP)))
			inlineCB.setSelectedIndex(I_CODE);
		else if (charattrs.isDefined(HTML.Tag.SUP))
			inlineCB.setSelectedIndex(I_SUPERSCRIPT);
		else if (charattrs.isDefined(HTML.Tag.SUB))
			inlineCB.setSelectedIndex(I_SUBSCRIPT);
		else if (charattrs.isDefined(HTML.Tag.CITE))
			inlineCB.setSelectedIndex(I_CITE);
		else if (charattrs.isDefined(HTML.Tag.FONT))
			inlineCB.setSelectedIndex(I_CUSTOM);
		else
			inlineCB.setSelectedIndex(I_NORMAL);
		inlineCBEventsLock = false;

		Element pEl = document.getParagraphElement(editor.getCaretPosition());
		String pName = pEl.getName().toUpperCase();
		blockCBEventsLock = true;
		if (pName.equals("P-IMPLIED"))
			pName = pEl.getParentElement().getName().toUpperCase();

		if (pName.equals("P"))
			blockCB.setSelectedIndex(T_P);
		else if (pName.equals("H1"))
			blockCB.setSelectedIndex(T_H1);
		else if (pName.equals("H2"))
			blockCB.setSelectedIndex(T_H2);
		else if (pName.equals("H3"))
			blockCB.setSelectedIndex(T_H3);
		else if (pName.equals("H4"))
			blockCB.setSelectedIndex(T_H4);
		else if (pName.equals("H5"))
			blockCB.setSelectedIndex(T_H5);
		else if (pName.equals("H6"))
			blockCB.setSelectedIndex(T_H6);
		else if (pName.equals("PRE"))
			blockCB.setSelectedIndex(T_PRE);
		/*
		 * else if (pName.equals("ADDRESS"))
		 */
		else if (pName.equals("BLOCKQUOTE"))
			blockCB.setSelectedIndex(T_BLOCKQ);
		blockCBEventsLock = false;
		this.insertTableCellAction.update();
		this.insertTableRowAction.update();
		/*
		 * String ppName =
		 * document.getParagraphElement(editor.getCaretPosition()).getParentElement().getName().toUpperCase();
		 * System.out.print(ppName+"->"+pName+":");
		 * 
		 * AbstractDocument.BranchElement pEl =
		 * (AbstractDocument.BranchElement)document.getParagraphElement(editor.getCaretPosition());
		 * Element el = pEl.positionToElement(editor.getCaretPosition());
		 * System.out.println(el.getAttributes().getAttribute(StyleConstants.NameAttribute)+",
		 * "+pEl.getElementCount()+"/"+el.getElementCount());
		 */

	}

	/**
	 * Ul action B action performed.
	 *
	 * @param e the e
	 */
	public void ulActionB_actionPerformed(ActionEvent e) {
		String parentname =
			document
				.getParagraphElement(editor.getCaretPosition())
				.getParentElement()
				.getName();
		HTML.Tag parentTag = HTML.getTag(parentname);
		HTMLEditorKit.InsertHTMLTextAction ulAction =
			new HTMLEditorKit.InsertHTMLTextAction(
				"insertUL",
				"<ul><li></li></ul>",
				parentTag,
				HTML.Tag.UL);
		ulAction.actionPerformed(e);
		//removeIfEmpty(document.getParagraphElement(editor.getCaretPosition()-1));
		list = true;
		/*
		 * Element pEl =
		 * document.getParagraphElement(editor.getCaretPosition());
		 * StringWriter sw = new StringWriter(); try { editorKit.write(sw,
		 * document, pEl.getStartOffset(),
		 * pEl.getEndOffset()-pEl.getStartOffset()); String copy =
		 * sw.toString(); String elName = pEl.getName(); copy =
		 * copy.substring(copy.indexOf(" <"+elName)); copy =
		 * copy.substring(0,copy.indexOf(" </"+elName)+elName.length()+3);
		 * document.setOuterHTML(pEl, " <ul><li> "+copy+" </li></ul> ");
		 * System.out.println(copy); } catch (Exception ex){
		 * ex.printStackTrace();
		 */

	}

	/**
	 * Ol action B action performed.
	 *
	 * @param e the e
	 */
	public void olActionB_actionPerformed(ActionEvent e) {
		String parentname =
			document
				.getParagraphElement(editor.getCaretPosition())
				.getParentElement()
				.getName();
		HTML.Tag parentTag = HTML.getTag(parentname);
		HTMLEditorKit.InsertHTMLTextAction olAction =
			new HTMLEditorKit.InsertHTMLTextAction(
				"insertOL",
				"<ol><li></li></ol>",
				parentTag,
				HTML.Tag.OL);
		olAction.actionPerformed(e);
		//removeIfEmpty(document.getParagraphElement(editor.getCaretPosition()-1));
		list = true;
	}

	/**
	 * Removes the if empty.
	 *
	 * @param elem the elem
	 */
	void removeIfEmpty(Element elem) {
		if (elem.getEndOffset() - elem.getStartOffset() < 2) {
			try {
				document.remove(elem.getStartOffset(), elem.getEndOffset());
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		}
	}

	/**
	 * The Class ParaBreakAction.
	 */
	class ParaBreakAction extends AbstractAction {
		
		/**
		 * Instantiates a new para break action.
		 */
		ParaBreakAction() {
			super("ParaBreakAction");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {

			Element elem =
				document.getParagraphElement(editor.getCaretPosition());
			String elName = elem.getName().toUpperCase();
			String parentname = elem.getParentElement().getName();
			HTML.Tag parentTag = HTML.getTag(parentname);
			if (parentname.toUpperCase().equals("P-IMPLIED"))
				parentTag = HTML.Tag.IMPLIED;
			if (parentname.toLowerCase().equals("li")) {
				// HTML.Tag listTag =
				// HTML.getTag(elem.getParentElement().getParentElement().getName());
				if (elem.getEndOffset() - elem.getStartOffset() > 1) {
					try {
						document.insertAfterEnd(
							elem.getParentElement(),
							"<li></li>");
						editor.setCaretPosition(
							elem.getParentElement().getEndOffset());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					/*
					 * HTMLEditorKit.InsertHTMLTextAction liAction = new
					 * HTMLEditorKit.InsertHTMLTextAction("insertLI", " <li>
					 * </li> ", parentTag, HTML.Tag.LI);
					 */
				} else {
					try {
						document.remove(editor.getCaretPosition(), 1);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Element listParentElement =
						elem
							.getParentElement()
							.getParentElement()
							.getParentElement();
					HTML.Tag listParentTag =
						HTML.getTag(listParentElement.getName());
					String listParentTagName = listParentTag.toString();
					if (listParentTagName.toLowerCase().equals("li")) {
						Element listAncEl =
							listParentElement.getParentElement();
						try {
							editorKit.insertHTML(
								document,
								listAncEl.getEndOffset(),
								"<li><p></p></li>",
								3,
								0,
								HTML.Tag.LI);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						HTMLEditorKit.InsertHTMLTextAction pAction =
							new HTMLEditorKit.InsertHTMLTextAction(
								"insertP",
								"<p></p>",
								listParentTag,
								HTML.Tag.P);
						pAction.actionPerformed(e);
					}
				}
			} else if (
				(elName.equals("PRE"))
					|| (elName.equals("ADDRESS"))
					|| (elName.equals("BLOCKQUOTE"))) {
				if (editor.getCaretPosition() > 0)
					removeIfEmpty(
						document.getParagraphElement(
							editor.getCaretPosition() - 1));
				HTMLEditorKit.InsertHTMLTextAction pAction =
					new HTMLEditorKit.InsertHTMLTextAction(
						"insertP",
						"<p></p>",
						parentTag,
						HTML.Tag.P);
				System.out.println("PRE");
				pAction.actionPerformed(e);
			} else if (elName.equals("P-IMPLIED")) {
				/*
				 * HTML.Tag sParentTag =
				 * HTML.getTag(elem.getParentElement().getParentElement().getName());
				 * if (editor.getCaretPosition() > 0)
				 * removeIfEmpty(document.getParagraphElement(editor.getCaretPosition() -
				 * 1)); HTMLEditorKit.InsertHTMLTextAction pAction = new
				 * HTMLEditorKit.InsertHTMLTextAction("insertP", " <p></p> ",
				 * sParentTag, HTML.Tag.P);
				 * System.out.println(sParentTag.toString());
				 */
				try {
					System.out.println("IMPLIED");
					document.insertAfterEnd(elem.getParentElement(), "<p></p>");
					editor.setCaretPosition(
						elem.getParentElement().getEndOffset());
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			} else {
				//removeIfEmpty(editor.getStyledDocument().getParagraphElement(editor.getCaretPosition()-1));
				/*
				 * HTMLEditorKit.InsertHTMLTextAction pAction = new
				 * HTMLEditorKit.InsertHTMLTextAction("insertP"," <p></p> ",
				 * HTML.Tag.BODY, HTML.Tag.P);
				 */

				//HTMLEditorKit.InsertBreakAction iba = new
				// HTMLEditorKit.InsertBreakAction();
				//iba.actionPerformed(e);
				editor.replaceSelection("\n");
				editorKit.getInputAttributes().removeAttribute(
					HTML.Attribute.ID);
				editorKit.getInputAttributes().removeAttribute(
					HTML.Attribute.CLASS);
			}
			//System.out.println(e.getWhen());
		}
	}

	/**
	 * The Class BreakAction.
	 */
	class BreakAction extends AbstractAction {
		
		/**
		 * Instantiates a new break action.
		 */
		BreakAction() {
			super(
				Local.getString("Insert break"),
				new ImageIcon(cl.getResource("resources/icons/break.png")));
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String elName =
				document
					.getParagraphElement(editor.getCaretPosition())
					.getName();
			/*
			 * if ((elName.toUpperCase().equals("PRE")) ||
			 * (elName.toUpperCase().equals("P-IMPLIED"))) {
			 * editor.replaceSelection("\r"); return;
			 */
			HTML.Tag tag = HTML.getTag(elName);
			if (elName.toUpperCase().equals("P-IMPLIED"))
				tag = HTML.Tag.IMPLIED;

			HTMLEditorKit.InsertHTMLTextAction hta =
				new HTMLEditorKit.InsertHTMLTextAction(
					"insertBR",
					"<br>",
					tag,
					HTML.Tag.BR);
			hta.actionPerformed(e);

			//insertHTML("<br>",editor.getCaretPosition());

		}
	}

	/**
	 * The Class InsertTableRowAction.
	 */
	class InsertTableRowAction extends AbstractAction {
		
		/**
		 * Instantiates a new insert table row action.
		 */
		InsertTableRowAction() {
			super(Local.getString("Insert table row"));
			this.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK));
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String trTag = "<tr>";
			Element tr =
				document
					.getParagraphElement(editor.getCaretPosition())
					.getParentElement()
					.getParentElement();
			for (int i = 0; i < tr.getElementCount(); i++)
				if (tr.getElement(i).getName().toUpperCase().equals("TD"))
					trTag += "<td><p></p></td>";
			trTag += "</tr>";

			/*
			 * HTMLEditorKit.InsertHTMLTextAction hta = new
			 * HTMLEditorKit.InsertHTMLTextAction("insertTR",trTag,
			 * HTML.Tag.TABLE, HTML.Tag.TR);
			 */
			try {
				document.insertAfterEnd(tr, trTag);
				//editorKit.insertHTML(document, editor.getCaretPosition(),
				// trTag, 3, 0, HTML.Tag.TR);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.AbstractAction#isEnabled()
		 */
		public boolean isEnabled() {
			if (document == null)
				return false;
			return document
				.getParagraphElement(editor.getCaretPosition())
				.getParentElement()
				.getName()
				.toUpperCase()
				.equals("TD");
		}

		/**
		 * Update.
		 */
		public void update() {
			this.setEnabled(isEnabled());
		}
	}

	/**
	 * The Class InsertTableCellAction.
	 */
	class InsertTableCellAction extends AbstractAction {
		
		/**
		 * Instantiates a new insert table cell action.
		 */
		InsertTableCellAction() {
			super(Local.getString("Insert table cell"));
			this.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(
					KeyEvent.VK_ENTER,
					KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String tdTag = "<td><p></p></td>";
			Element td =
				document
					.getParagraphElement(editor.getCaretPosition())
					.getParentElement();
			try {
				document.insertAfterEnd(td, tdTag);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.AbstractAction#isEnabled()
		 */
		public boolean isEnabled() {
			if (document == null)
				return false;
			return document
				.getParagraphElement(editor.getCaretPosition())
				.getParentElement()
				.getName()
				.toUpperCase()
				.equals("TD");
		}

		/**
		 * Update.
		 */
		public void update() {
			this.setEnabled(isEnabled());
		}
	}

	/**
	 * L align action B action performed.
	 *
	 * @param e the e
	 */
	public void lAlignActionB_actionPerformed(ActionEvent e) {
		HTMLEditorKit.AlignmentAction aa =
			new HTMLEditorKit.AlignmentAction(
				"leftAlign",
				StyleConstants.ALIGN_LEFT);
		aa.actionPerformed(e);
	}

	/**
	 * C align action B action performed.
	 *
	 * @param e the e
	 */
	public void cAlignActionB_actionPerformed(ActionEvent e) {
		HTMLEditorKit.AlignmentAction aa =
			new HTMLEditorKit.AlignmentAction(
				"centerAlign",
				StyleConstants.ALIGN_CENTER);
		aa.actionPerformed(e);
	}

	/**
	 * R align action B action performed.
	 *
	 * @param e the e
	 */
	public void rAlignActionB_actionPerformed(ActionEvent e) {
		HTMLEditorKit.AlignmentAction aa =
			new HTMLEditorKit.AlignmentAction(
				"rightAlign",
				StyleConstants.ALIGN_RIGHT);
		aa.actionPerformed(e);
	}

	/**
	 * J align action B action performed.
	 *
	 * @param e the e
	 */
	public void jAlignActionB_actionPerformed(ActionEvent e) {
		HTMLEditorKit.AlignmentAction aa =
			new HTMLEditorKit.AlignmentAction(
				"justifyAlign",
				StyleConstants.ALIGN_JUSTIFIED);
		aa.actionPerformed(e);
	}

	/**
	 * Insert HTML.
	 *
	 * @param html the html
	 * @param location the location
	 */
	public void insertHTML(String html, int location) {
		//assumes editor is already set to "text/html" type
		try {
			HTMLEditorKit kit = (HTMLEditorKit) editor.getEditorKit();
			Document doc = editor.getDocument();
			StringReader reader = new StringReader(html);
			kit.read(reader, doc, location);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Image action B action performed.
	 *
	 * @param e the e
	 */
	public void imageActionB_actionPerformed(ActionEvent e) {
		/*
		 * JFileChooser chooser = new JFileChooser();
		 * chooser.setFileHidingEnabled(false); chooser.setDialogTitle("Choose
		 * image file"); chooser.setAcceptAllFileFilterUsed(false);
		 * chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 * chooser.addChoosableFileFilter(new
		 * net.sf.memoranda.ui.htmleditor.filechooser.ImageFilter());
		 * chooser.setAccessory(new
		 * net.sf.memoranda.ui.htmleditor.filechooser.ImagePreview(chooser));
		 *  
		 */
		ImageDialog dlg = new ImageDialog(null);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		//dlg.setLocation(imageActionB.getLocationOnScreen());
		dlg.setModal(true);
		dlg.setVisible(true);

		if (!dlg.CANCELLED) {
			String parentname =
				document
					.getParagraphElement(editor.getCaretPosition())
					.getParentElement()
					.getName();
			//HTML.Tag parentTag = HTML.getTag(parentname);
			String urlString = dlg.fileField.getText();
			String path = urlString;
			if (imagesDir != null) {
				try {
					URL url = new URL(urlString);
					if (!url.getProtocol().startsWith("http"))
						path = imagesDir + "/" + url.getFile();
				} catch (MalformedURLException e1) {
					new ExceptionDialog(e1);
				}
			}
			try {
				String imgTag =
					"<img src=\""
						+ path
						+ "\" alt=\""
						+ dlg.altField.getText()
						+ "\" ";
				String w = dlg.widthField.getText();
				try {
					Integer.parseInt(w, 10);
					imgTag += " width=\"" + w + "\" ";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String h = dlg.heightField.getText();
				try {
					Integer.parseInt(h, 10);
					imgTag += " height=\"" + h + "\" ";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String hs = dlg.hspaceField.getText();
				try {
					Integer.parseInt(hs, 10);
					imgTag += " hspace=\"" + hs + "\" ";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String vs = dlg.vspaceField.getText();
				try {
					Integer.parseInt(vs, 10);
					imgTag += " vspace=\"" + vs + "\" ";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String b = dlg.borderField.getText();
				try {
					Integer.parseInt(b, 10);
					imgTag += " border=\"" + b + "\" ";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (dlg.alignCB.getSelectedIndex() > 0)
					imgTag += " align=\""
						+ dlg.alignCB.getSelectedItem()
						+ "\" ";
				imgTag += ">";

				if (dlg.urlField.getText().length() > 0) {
					imgTag =
						"<a href=\""
							+ dlg.urlField.getText()
							+ "\">"
							+ imgTag
							+ "</a>";
					if (editor.getCaretPosition() == document.getLength())
						imgTag += "&nbsp;";
					editorKit.insertHTML(
						document,
						editor.getCaretPosition(),
						imgTag,
						0,
						0,
						HTML.Tag.A);
				} else
					editorKit.insertHTML(
						document,
						editor.getCaretPosition(),
						imgTag,
						0,
						0,
						HTML.Tag.IMG);

				//System.out.println(imgTag);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Table action B action performed.
	 *
	 * @param e the e
	 */
	public void tableActionB_actionPerformed(ActionEvent e) {
		TableDialog dlg = new TableDialog(null);
		//dlg.setLocation(tableActionB.getLocationOnScreen());
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);

		dlg.setModal(true);
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		String tableTag = "<table ";
		String w = dlg.widthField.getText().trim();
		if (w.length() > 0)
			tableTag += " width=\"" + w + "\" ";
		String h = dlg.heightField.getText().trim();
		if (h.length() > 0)
			tableTag += " height=\"" + h + "\" ";
		String cp = dlg.cellpadding.getValue().toString();
		try {
			Integer.parseInt(cp, 10);
			tableTag += " cellpadding=\"" + cp + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String cs = dlg.cellspacing.getValue().toString();
		try {
			Integer.parseInt(cs, 10);
			tableTag += " cellspacing=\"" + cs + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String b = dlg.border.getValue().toString();
		try {
			Integer.parseInt(b, 10);
			tableTag += " border=\"" + b + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (dlg.alignCB.getSelectedIndex() > 0)
			tableTag += " align=\"" + dlg.alignCB.getSelectedItem() + "\" ";
		if (dlg.vAlignCB.getSelectedIndex() > 0)
			tableTag += " valign=\"" + dlg.vAlignCB.getSelectedItem() + "\" ";
		if (dlg.bgcolorField.getText().length() > 0)
			tableTag += " bgcolor=\"" + dlg.bgcolorField.getText() + "\" ";
		tableTag += ">";
		int cols = 1;
		int rows = 1;
		try {
			cols = ((Integer) dlg.columns.getValue()).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			rows = ((Integer) dlg.rows.getValue()).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (int r = 0; r < rows; r++) {
			tableTag += "<tr>";
			for (int c = 0; c < cols; c++)
				tableTag += "<td><p></p></td>";
			tableTag += "</tr>";
		}
		tableTag += "</table>";
		String parentname =
			document
				.getParagraphElement(editor.getCaretPosition())
				.getParentElement()
				.getName();
		HTML.Tag parentTag = HTML.getTag(parentname);
		System.out.println(parentTag + ":\n" + tableTag);
		/*
		 * HTMLEditorKit.InsertHTMLTextAction insertTableAction = new
		 * HTMLEditorKit.InsertHTMLTextAction("insertTABLE",tableTag,
		 * HTML.Tag.BODY, HTML.Tag.TABLE);
		 *  
		 */
		//insertHTML(tableTag, editor.getCaretPosition());

		try {
			editorKit.insertHTML(
				document,
				editor.getCaretPosition(),
				tableTag,
				1,
				0,
				HTML.Tag.TABLE);
			//removeIfEmpty(document.getParagraphElement(editor.getCaretPosition()-1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Link action B action performed.
	 *
	 * @param e the e
	 */
	public void linkActionB_actionPerformed(ActionEvent e) {
		LinkDialog dlg = new LinkDialog(null);
		//dlg.setLocation(linkActionB.getLocationOnScreen());
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		if (editor.getSelectedText() != null)
			dlg.txtDesc.setText(editor.getSelectedText());
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		String aTag = "<a";
		if (dlg.txtURL.getText().length() > 0)
			aTag += " href=\"" + dlg.txtURL.getText() + "\"";
		if (dlg.txtName.getText().length() > 0)
			aTag += " name=\"" + dlg.txtName.getText() + "\"";
		if (dlg.txtTitle.getText().length() > 0)
			aTag += " title=\"" + dlg.txtTitle.getText() + "\"";
		if (dlg.chkNewWin.isSelected())
			aTag += " target=\"_blank\"";
		aTag += ">" + dlg.txtDesc.getText() + "</a>";
		if (editor.getCaretPosition() == document.getLength())
			aTag += "&nbsp;";
		editor.replaceSelection("");
		try {
			editorKit.insertHTML(
				document,
				editor.getCaretPosition(),
				aTag,
				0,
				0,
				HTML.Tag.A);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the link properties.
	 *
	 * @param el the el
	 * @param href the href
	 * @param target the target
	 * @param title the title
	 * @param name the name
	 */
	void setLinkProperties(
		Element el,
		String href,
		String target,
		String title,
		String name) {
		LinkDialog dlg = new LinkDialog(null);
		dlg.setLocation(linkActionB.getLocationOnScreen());
		dlg.setModal(true);
		//dlg.descPanel.setVisible(false);
		dlg.txtURL.setText(href);
		dlg.txtName.setText(name);
		dlg.txtTitle.setText(title);
		try {
			dlg.txtDesc.setText(
				document.getText(
					el.getStartOffset(),
					el.getEndOffset() - el.getStartOffset()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dlg.chkNewWin.setSelected(target.toUpperCase().equals("_BLANK"));
		dlg.header.setText(Local.getString("Hyperlink properties"));
		dlg.setTitle(Local.getString("Hyperlink properties"));
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		//String p = "";
		/*
		 * SimpleAttributeSet attrs = new
		 * SimpleAttributeSet(el.getAttributes()); if
		 * (dlg.urlField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.HREF, dlg.urlField.getText()); p +=
		 * "href="+dlg.urlField.getText()+" "; } if
		 * (dlg.nameField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.NAME, dlg.nameField.getText()); p +=
		 * "name="+dlg.nameField.getText()+" "; } if
		 * (dlg.titleField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.TITLE, dlg.titleField.getText());
		 * p += "title="+dlg.titleField.getText()+" "; } if
		 * (dlg.newWinChB.isSelected()) {
		 * attrs.addAttribute(HTML.Attribute.TARGET, "_blank"); p +=
		 * "target=_blank "; } attrs.addAttribute(StyleConstants.NameAttribute,
		 * "a"); attrs.addAttribute(HTML.Tag.A, p);
		 */
		String aTag = "<a";
		if (dlg.txtURL.getText().length() > 0)
			aTag += " href=\"" + dlg.txtURL.getText() + "\"";
		if (dlg.txtName.getText().length() > 0)
			aTag += " name=\"" + dlg.txtName.getText() + "\"";
		if (dlg.txtTitle.getText().length() > 0)
			aTag += " title=\"" + dlg.txtTitle.getText() + "\"";
		if (dlg.chkNewWin.isSelected())
			aTag += " target=\"_blank\"";
		aTag += ">" + dlg.txtDesc.getText() + "</a>";
		try {
			document.setOuterHTML(el, aTag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the image properties.
	 *
	 * @param el the el
	 * @param src the src
	 * @param alt the alt
	 * @param width the width
	 * @param height the height
	 * @param hspace the hspace
	 * @param vspace the vspace
	 * @param border the border
	 * @param align the align
	 */
	void setImageProperties(
		Element el,
		String src,
		String alt,
		String width,
		String height,
		String hspace,
		String vspace,
		String border,
		String align) {
		ImageDialog dlg = new ImageDialog(null);
		dlg.setLocation(imageActionB.getLocationOnScreen());
		dlg.setModal(true);
		dlg.setTitle(Local.getString("Image properties"));
		dlg.fileField.setText(src);
		dlg.altField.setText(alt);
		dlg.widthField.setText(width);
		dlg.heightField.setText(height);
		dlg.hspaceField.setText(hspace);
		dlg.vspaceField.setText(vspace);
		dlg.borderField.setText(border);
		dlg.alignCB.setSelectedItem(align);
		dlg.updatePreview();
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		String imgTag =
			"<img src=\""
				+ dlg.fileField.getText()
				+ "\" alt=\""
				+ dlg.altField.getText()
				+ "\" ";
		String w = dlg.widthField.getText();
		try {
			Integer.parseInt(w, 10);
			imgTag += " width=\"" + w + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String h = dlg.heightField.getText();
		try {
			Integer.parseInt(h, 10);
			imgTag += " height=\"" + h + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String hs = dlg.hspaceField.getText();
		try {
			Integer.parseInt(hs, 10);
			imgTag += " hspace=\"" + hs + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String vs = dlg.vspaceField.getText();
		try {
			Integer.parseInt(vs, 10);
			imgTag += " vspace=\"" + vs + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String b = dlg.borderField.getText();
		try {
			Integer.parseInt(b, 10);
			imgTag += " border=\"" + b + "\" ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (dlg.alignCB.getSelectedIndex() > 0)
			imgTag += " align=\"" + dlg.alignCB.getSelectedItem() + "\" ";
		imgTag += ">";
		if (dlg.urlField.getText().length() > 0) {
			imgTag =
				"<a href=\"" + dlg.urlField.getText() + "\">" + imgTag + "</a>";
			if (editor.getCaretPosition() == document.getLength())
				imgTag += "&nbsp;";
		}
		try {
			document.setOuterHTML(el, imgTag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the element properties.
	 *
	 * @param el the el
	 * @param id the id
	 * @param cls the cls
	 * @param sty the sty
	 */
	void setElementProperties(Element el, String id, String cls, String sty) {
		ElementDialog dlg = new ElementDialog(null);
		//dlg.setLocation(linkActionB.getLocationOnScreen());
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.setTitle(Local.getString("Object properties"));
		dlg.idField.setText(id);
		dlg.classField.setText(cls);
		dlg.styleField.setText(sty);
		// Uncommented, returns a simple p into the header... fix needed ?
		//dlg.header.setText(el.getName());
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		SimpleAttributeSet attrs = new SimpleAttributeSet(el.getAttributes());
		if (dlg.idField.getText().length() > 0)
			attrs.addAttribute(HTML.Attribute.ID, dlg.idField.getText());
		if (dlg.classField.getText().length() > 0)
			attrs.addAttribute(HTML.Attribute.CLASS, dlg.classField.getText());
		if (dlg.styleField.getText().length() > 0)
			attrs.addAttribute(HTML.Attribute.STYLE, dlg.styleField.getText());
		document.setParagraphAttributes(el.getStartOffset(), 0, attrs, true);
	}

	/**
	 * Sets the table properties.
	 *
	 * @param td the new table properties
	 */
	void setTableProperties(Element td) {
		Element tr = td.getParentElement();
		Element table = tr.getParentElement();

		TdDialog dlg = new TdDialog(null);
		dlg.setLocation(editor.getLocationOnScreen());
		dlg.setModal(true);
		dlg.setTitle(Local.getString("Table properties"));

		/** **********PARSE ELEMENTS*********** */
		// TD***
		AttributeSet tda = td.getAttributes();
		if (tda.isDefined(HTML.Attribute.BGCOLOR)) {
			dlg.tdBgcolorField.setText(
				tda.getAttribute(HTML.Attribute.BGCOLOR).toString());
			Util.setBgcolorField(dlg.tdBgcolorField);
		}
		if (tda.isDefined(HTML.Attribute.WIDTH))
			dlg.tdWidthField.setText(
				tda.getAttribute(HTML.Attribute.WIDTH).toString());
		if (tda.isDefined(HTML.Attribute.HEIGHT))
			dlg.tdHeightField.setText(
				tda.getAttribute(HTML.Attribute.HEIGHT).toString());
		if (tda.isDefined(HTML.Attribute.COLSPAN))
			try {
				Integer i =
					new Integer(
						tda.getAttribute(HTML.Attribute.COLSPAN).toString());
				dlg.tdColspan.setValue(i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		if (tda.isDefined(HTML.Attribute.ROWSPAN))
			try {
				Integer i =
					new Integer(
						tda.getAttribute(HTML.Attribute.ROWSPAN).toString());
				dlg.tdRowspan.setValue(i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		if (tda.isDefined(HTML.Attribute.ALIGN))
			dlg.tdAlignCB.setSelectedItem(
				tda
					.getAttribute(HTML.Attribute.ALIGN)
					.toString()
					.toLowerCase());
		if (tda.isDefined(HTML.Attribute.VALIGN))
			dlg.tdValignCB.setSelectedItem(
				tda
					.getAttribute(HTML.Attribute.VALIGN)
					.toString()
					.toLowerCase());
		dlg.tdNowrapChB.setSelected((tda.isDefined(HTML.Attribute.NOWRAP)));

		//TR ****
		AttributeSet tra = tr.getAttributes();
		if (tra.isDefined(HTML.Attribute.BGCOLOR)) {
			dlg.trBgcolorField.setText(
				tra.getAttribute(HTML.Attribute.BGCOLOR).toString());
			Util.setBgcolorField(dlg.trBgcolorField);
		}
		if (tra.isDefined(HTML.Attribute.ALIGN))
			dlg.trAlignCB.setSelectedItem(
				tra
					.getAttribute(HTML.Attribute.ALIGN)
					.toString()
					.toLowerCase());
		if (tra.isDefined(HTML.Attribute.VALIGN))
			dlg.trValignCB.setSelectedItem(
				tra
					.getAttribute(HTML.Attribute.VALIGN)
					.toString()
					.toLowerCase());

		//TABLE ****
		AttributeSet ta = table.getAttributes();
		if (ta.isDefined(HTML.Attribute.BGCOLOR)) {
			dlg.bgcolorField.setText(
				ta.getAttribute(HTML.Attribute.BGCOLOR).toString());
			Util.setBgcolorField(dlg.bgcolorField);
		}
		if (ta.isDefined(HTML.Attribute.WIDTH))
			dlg.widthField.setText(
				ta.getAttribute(HTML.Attribute.WIDTH).toString());
		if (ta.isDefined(HTML.Attribute.HEIGHT))
			dlg.heightField.setText(
				ta.getAttribute(HTML.Attribute.HEIGHT).toString());
		if (ta.isDefined(HTML.Attribute.ALIGN))
			dlg.alignCB.setSelectedItem(
				ta.getAttribute(HTML.Attribute.ALIGN).toString().toLowerCase());
		if (ta.isDefined(HTML.Attribute.VALIGN))
			dlg.vAlignCB.setSelectedItem(
				ta
					.getAttribute(HTML.Attribute.VALIGN)
					.toString()
					.toLowerCase());
		if (ta.isDefined(HTML.Attribute.CELLPADDING))
			try {
				Integer i =
					new Integer(
						ta.getAttribute(HTML.Attribute.CELLPADDING).toString());
				dlg.cellpadding.setValue(i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		if (ta.isDefined(HTML.Attribute.CELLSPACING))
			try {
				Integer i =
					new Integer(
						ta.getAttribute(HTML.Attribute.CELLSPACING).toString());
				dlg.cellspacing.setValue(i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		if (ta.isDefined(HTML.Attribute.BORDER))
			try {
				Integer i =
					new Integer(
						ta.getAttribute(HTML.Attribute.BORDER).toString());
				dlg.border.setValue(i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		/** ****************************** */

		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;

		/** ******** SET ATTRIBUTES ********* */
		// TD***
		String tdTag = "<td";
		if (dlg.tdBgcolorField.getText().length() > 0)
			tdTag += " bgcolor=\"" + dlg.tdBgcolorField.getText() + "\"";

		if (dlg.tdWidthField.getText().length() > 0)
			tdTag += " width=\"" + dlg.tdWidthField.getText() + "\"";

		if (dlg.tdHeightField.getText().length() > 0)
			tdTag += " height=\"" + dlg.tdHeightField.getText() + "\"";

		if (!dlg.tdColspan.getValue().toString().equals("0"))
			tdTag += " colspan=\"" + dlg.tdColspan.getValue().toString() + "\"";

		if (!dlg.tdRowspan.getValue().toString().equals("0"))
			tdTag += " rowspan=\"" + dlg.tdRowspan.getValue().toString() + "\"";

		if (dlg.tdAlignCB.getSelectedItem().toString().length() > 0)
			tdTag += " align=\""
				+ dlg.tdAlignCB.getSelectedItem().toString()
				+ "\"";

		if (dlg.tdValignCB.getSelectedItem().toString().length() > 0)
			tdTag += " valign=\""
				+ dlg.tdValignCB.getSelectedItem().toString()
				+ "\"";

		if (dlg.tdNowrapChB.isSelected())
			tdTag += " nowrap";

		tdTag += ">";

		//TR***
		String trTag = "<tr";
		if (dlg.trBgcolorField.getText().length() > 0)
			trTag += " bgcolor=\"" + dlg.trBgcolorField.getText() + "\"";

		if (dlg.trAlignCB.getSelectedItem().toString().length() > 0)
			trTag += " align=\""
				+ dlg.trAlignCB.getSelectedItem().toString()
				+ "\"";

		if (dlg.trValignCB.getSelectedItem().toString().length() > 0)
			trTag += " valign=\""
				+ dlg.trValignCB.getSelectedItem().toString()
				+ "\"";

		trTag += ">";

		//TABLE ***
		String tTag = "<table";
		if (dlg.bgcolorField.getText().length() > 0)
			tTag += " bgcolor=\"" + dlg.bgcolorField.getText() + "\"";

		if (dlg.widthField.getText().length() > 0)
			tTag += " width=\"" + dlg.widthField.getText() + "\"";

		if (dlg.heightField.getText().length() > 0)
			tTag += " height=\"" + dlg.heightField.getText() + "\"";

		tTag += " cellpadding=\""
			+ dlg.cellpadding.getValue().toString()
			+ "\"";

		tTag += " cellspacing=\""
			+ dlg.cellspacing.getValue().toString()
			+ "\"";

		tTag += " border=\"" + dlg.border.getValue().toString() + "\"";

		if (dlg.alignCB.getSelectedItem().toString().length() > 0)
			tTag += " align=\""
				+ dlg.alignCB.getSelectedItem().toString()
				+ "\"";

		if (dlg.vAlignCB.getSelectedItem().toString().length() > 0)
			tTag += " valign=\""
				+ dlg.vAlignCB.getSelectedItem().toString()
				+ "\"";

		tTag += ">";

		/** ****************************** */

		/** ** UPDATE TABLE ***** */
		try {
			StringWriter sw = new StringWriter();
			String copy;

			editorKit.write(
				sw,
				document,
				td.getStartOffset(),
				td.getEndOffset() - td.getStartOffset());
			copy = sw.toString();
			copy = copy.split("<td(.*?)>")[1];
			copy = copy.split("</td>")[0];
			//System.out.println(tdTag+copy+"</td>");
			document.setOuterHTML(td, tdTag + copy + "</td>");

			//System.out.println("*******");

			sw = new StringWriter();
			editorKit.write(
				sw,
				document,
				tr.getStartOffset(),
				tr.getEndOffset() - tr.getStartOffset());
			copy = sw.toString();
			copy = copy.split("<tr(.*?)>")[1];
			copy = copy.split("</tr>")[0];
			//System.out.println(trTag+copy+"</tr>");
			document.setOuterHTML(tr, trTag + copy + "</tr>");

			//System.out.println("*******");

			sw = new StringWriter();
			editorKit.write(
				sw,
				document,
				table.getStartOffset(),
				table.getEndOffset() - table.getStartOffset());
			copy = sw.toString();
			copy = copy.split("<table(.*?)>")[1];
			copy = copy.split("</table>")[0];
			//System.out.println(tTag+copy+"</table>");
			document.setOuterHTML(table, tTag + copy + "</table>");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Block C B action performed.
	 *
	 * @param e the e
	 */
	void blockCB_actionPerformed(ActionEvent e) {
		if (blockCBEventsLock)
			return;
		int sel = blockCB.getSelectedIndex();
		/*
		 * HTML.Tag parentTag =
		 * HTML.getTag(document.getParagraphElement(editor.getCaretPosition()).getParentElement().getName());
		 * HTML.Tag tag = null; String tagText = ""; switch (sel) { case T_P:
		 * tag = HTML.Tag.P; tagText=" <p></p> ";break; //case T_PRE: tag =
		 * HTML.Tag.PRE; tagText=" <pre></pre> ";break; case T_H1: tag =
		 * HTML.Tag.H1; tagText=" <h1></h1> ";break; case T_H2: tag =
		 * HTML.Tag.H2; tagText=" <h2></h2> ";break; case T_H3: tag =
		 * HTML.Tag.H3; tagText=" <h3></h3> ";break; case T_H4: tag =
		 * HTML.Tag.H4; tagText=" <h4></h4> ";break; case T_H5: tag =
		 * HTML.Tag.H5; tagText=" <h5></h5> ";break; case T_H6: tag =
		 * HTML.Tag.H6; tagText=" <h6></h6> ";break; }
		 * 
		 * HTMLEditorKit.InsertHTMLTextAction iAction = new
		 * HTMLEditorKit.InsertHTMLTextAction("insertTag",tagText, parentTag,
		 * tag); iAction.actionPerformed(e);
		 * System.out.println(tag.toString()+" -> "+parentTag.toString());
		 */

		HTML.Tag tag = null;

		switch (sel) {
			case T_P :
				tag = HTML.Tag.P;
				break;
			case T_H1 :
				tag = HTML.Tag.H1;
				break;
			case T_H2 :
				tag = HTML.Tag.H2;
				break;
			case T_H3 :
				tag = HTML.Tag.H3;
				break;
			case T_H4 :
				tag = HTML.Tag.H4;
				break;
			case T_H5 :
				tag = HTML.Tag.H5;
				break;
			case T_H6 :
				tag = HTML.Tag.H6;
				break;
			case T_PRE :
				tag = HTML.Tag.PRE;
				break;
				/*
				 * case T_ADDRESS : tag = HTML.Tag.ADDRESS;
				 */
			case T_BLOCKQ :
				tag = HTML.Tag.BLOCKQUOTE;
				break;
		}

		Element el = document.getParagraphElement(editor.getCaretPosition());
		if (el.getName().toUpperCase().equals("P-IMPLIED")) {
			Element pEl = el.getParentElement();
			String pElName = pEl.getName();
			String newName = tag.toString();
			StringWriter sw = new StringWriter();
			String copy;
			try {
				editorKit.write(
					sw,
					document,
					el.getStartOffset(),
					el.getEndOffset() - el.getStartOffset());
				copy = sw.toString();
				copy = copy.split("<" + pElName + "(.*?)>")[1];
				copy = copy.split("</" + pElName + ">")[0];
				document.setOuterHTML(
					pEl,
					"<" + newName + ">" + copy + "</" + newName + ">");
				return;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		SimpleAttributeSet attrs = new SimpleAttributeSet(el.getAttributes());
		//attrs.removeAttribute(StyleConstants.NameAttribute);
		attrs.addAttribute(StyleConstants.NameAttribute, tag);
		if (editor.getSelectionEnd() - editor.getSelectionStart() > 0)
			document.setParagraphAttributes(
				editor.getSelectionStart(),
				editor.getSelectionEnd() - editor.getSelectionStart(),
				attrs,
				true);
		else
			document.setParagraphAttributes(
				editor.getCaretPosition(),
				0,
				attrs,
				true);

	}

	/**
	 * Props action B action performed.
	 *
	 * @param e the e
	 */
	public void propsActionB_actionPerformed(ActionEvent e) {

		/*
		 * Element p = document.getParagraphElement(editor.getCaretPosition());
		 * do { System.out.print(p.getName()+" / "); p = p.getParentElement();}
		 */

		AbstractDocument.BranchElement pEl =
			(AbstractDocument.BranchElement) document.getParagraphElement(
				editor.getCaretPosition());
		System.out.println("--------------");
		System.out.println(
			pEl.getName() + "<-" + pEl.getParentElement().getName());
		Element el = pEl.positionToElement(editor.getCaretPosition());
		System.out.println(
			":"
				+ el.getAttributes().getAttribute(StyleConstants.NameAttribute));
		AttributeSet attrs = el.getAttributes();
		/*
		 * Enumeration en = attrs.getAttributeNames(); Object k =
		 * en.nextElement(); while (en.hasMoreElements()) {
		 * System.out.println(k+" = '"+attrs.getAttribute(k)+"'"); k =
		 * en.nextElement();
		 */
		String elName =
			attrs
				.getAttribute(StyleConstants.NameAttribute)
				.toString()
				.toUpperCase();
		if (elName.equals("IMG")) {
			String src = "",
				alt = "",
				width = "",
				height = "",
				hspace = "",
				vspace = "",
				border = "",
				align = "";
			if (attrs.isDefined(HTML.Attribute.SRC))
				src = attrs.getAttribute(HTML.Attribute.SRC).toString();
			if (attrs.isDefined(HTML.Attribute.ALT))
				alt = attrs.getAttribute(HTML.Attribute.ALT).toString();
			if (attrs.isDefined(HTML.Attribute.WIDTH))
				width = attrs.getAttribute(HTML.Attribute.WIDTH).toString();
			if (attrs.isDefined(HTML.Attribute.HEIGHT))
				height = attrs.getAttribute(HTML.Attribute.HEIGHT).toString();
			if (attrs.isDefined(HTML.Attribute.HSPACE))
				hspace = attrs.getAttribute(HTML.Attribute.HSPACE).toString();
			if (attrs.isDefined(HTML.Attribute.VSPACE))
				vspace = attrs.getAttribute(HTML.Attribute.VSPACE).toString();
			if (attrs.isDefined(HTML.Attribute.BORDER))
				border = attrs.getAttribute(HTML.Attribute.BORDER).toString();
			if (attrs.isDefined(HTML.Attribute.ALIGN))
				align = attrs.getAttribute(HTML.Attribute.ALIGN).toString();
			setImageProperties(
				el,
				src,
				alt,
				width,
				height,
				hspace,
				vspace,
				border,
				align);
			return;
		}

		Object k = null;
		for (Enumeration en = attrs.getAttributeNames();
			en.hasMoreElements();
			) {
			k = en.nextElement();
			if (k.toString().equals("a")) {
				String[] param = attrs.getAttribute(k).toString().split(" ");
				String href = "", target = "", title = "", name = "";
				for (int i = 0; i < param.length; i++)
					if (param[i].startsWith("href="))
						href = param[i].split("=")[1];
					else if (param[i].startsWith("title="))
						title = param[i].split("=")[1];
					else if (param[i].startsWith("target="))
						target = param[i].split("=")[1];
					else if (param[i].startsWith("name="))
						name = param[i].split("=")[1];
				setLinkProperties(el, href, target, title, name);
				return;
			}
			System.out.println(k + " = '" + attrs.getAttribute(k) + "'");
		}

		if (pEl.getParentElement().getName().toUpperCase().equals("TD")) {
			setTableProperties(pEl.getParentElement());
			return;
		}

		String id = "", cls = "", sty = "";
		AttributeSet pa = pEl.getAttributes();
		if (pa.getAttribute(HTML.Attribute.ID) != null)
			id = pa.getAttribute(HTML.Attribute.ID).toString();
		if (pa.getAttribute(HTML.Attribute.CLASS) != null)
			cls = pa.getAttribute(HTML.Attribute.CLASS).toString();
		if (pa.getAttribute(HTML.Attribute.STYLE) != null)
			sty = pa.getAttribute(HTML.Attribute.STYLE).toString();
		setElementProperties(pEl, id, cls, sty);

	}

	/*
	 * void dotestCharElement(ActionEvent e) { String text = "&nbsp;"; if
	 * (editor.getSelectedText() != null) text = editor.getSelectedText();
	 * String tag = " <code> " + text + " </code> "; if
	 * (editor.getCaretPosition() == document.getLength()) tag += "&nbsp;";
	 * editor.replaceSelection(""); try { editorKit.insertHTML(document,
	 * editor.getCaretPosition(), tag, 0, 0, HTML.Tag.CODE); } catch (Exception
	 * ex) { ex.printStackTrace(); } }
	 * 
	 * void dotestClearStyle(ActionEvent e) { Element el =
	 * document.getCharacterElement(editor.getCaretPosition());
	 * SimpleAttributeSet attrs = new SimpleAttributeSet();
	 * attrs.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
	 * document.setCharacterAttributes(el.getStartOffset(), el.getEndOffset() -
	 * el.getStartOffset(), attrs, true); }
	 */

	/**
	 * Sets the font properties.
	 *
	 * @param el the el
	 * @param text the text
	 * @return the string
	 */
	String setFontProperties(Element el, String text) {
		FontDialog dlg = new FontDialog(null);
		//dlg.setLocation(editor.getLocationOnScreen());
		Dimension dlgSize = dlg.getSize();
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		AttributeSet ea = el.getAttributes();
		/*
		 * if (ea.isDefined(HTML.Tag.FONT)) { String[] param =
		 * ea.getAttribute(HTML.Tag.FONT).toString().split(" "); for (int i = 0;
		 * i < param.length; i++) if (param[i].startsWith("face="))
		 * dlg.fontFamilyCB.setSelectedItem(param[i].split("=")[1]); else if
		 * (param[i].startsWith("size="))
		 * dlg.fontSizeCB.setSelectedItem(param[i].split("=")[1]); else if
		 * (param[i].startsWith("color=")) {
		 * dlg.colorField.setText(param[i].split("=")[1]);
		 * Util.setColorField(dlg.colorField); }
		 */
		if (ea.isDefined(StyleConstants.FontFamily))
			dlg.fontFamilyCB.setSelectedItem(
				ea.getAttribute(StyleConstants.FontFamily).toString());
		if (ea.isDefined(HTML.Tag.FONT)) {
			String s = ea.getAttribute(HTML.Tag.FONT).toString();
			String size =
				s.substring(s.indexOf("size=") + 5, s.indexOf("size=") + 6);
			dlg.fontSizeCB.setSelectedItem(size);
		}
		if (ea.isDefined(StyleConstants.Foreground)) {
			dlg.colorField.setText(
				Util.encodeColor(
					(Color) ea.getAttribute(StyleConstants.Foreground)));
			Util.setColorField(dlg.colorField);
			dlg.sample.setForeground(
				(Color) ea.getAttribute(StyleConstants.Foreground));
		}
		if (text != null)
			dlg.sample.setText(text);
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return null;
		String attrs = "";
		if (dlg.fontSizeCB.getSelectedIndex() > 0)
			attrs += "size=\"" + dlg.fontSizeCB.getSelectedItem() + "\"";
		if (dlg.fontFamilyCB.getSelectedIndex() > 0)
			attrs += "face=\"" + dlg.fontFamilyCB.getSelectedItem() + "\"";
		if (dlg.colorField.getText().length() > 0)
			attrs += "color=\"" + dlg.colorField.getText() + "\"";
		if (attrs.length() > 0)
			return " " + attrs;
		else
			return null;
	}

	/**
	 * Inline C B action performed.
	 *
	 * @param e the e
	 */
	void inlineCB_actionPerformed(ActionEvent e) {
		if (inlineCBEventsLock)
			return;
		int sel = inlineCB.getSelectedIndex();
		if (sel == I_NORMAL) {
			Element el =
				document.getCharacterElement(editor.getCaretPosition());
			SimpleAttributeSet attrs = new SimpleAttributeSet();
			attrs.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
			if (editor.getSelectionEnd() > editor.getSelectionStart())
				document.setCharacterAttributes(
					editor.getSelectionStart(),
					editor.getSelectionEnd() - editor.getSelectionStart(),
					attrs,
					true);
			else
				document.setCharacterAttributes(
					el.getStartOffset(),
					el.getEndOffset() - el.getStartOffset(),
					attrs,
					true);
			return;
		}
		String text = "&nbsp;";
		if (editor.getSelectedText() != null)
			text = editor.getSelectedText();
		String tag = "";
		String att = "";
		switch (sel) {
			case I_EM :
				tag = "em";
				break;
			case I_STRONG :
				tag = "strong";
				break;
			case I_CODE :
				tag = "code";
				break;
			case I_SUPERSCRIPT :
				tag = "sup";
				break;
			case I_SUBSCRIPT :
				tag = "sub";
				break;
			case I_CITE :
				tag = "cite";
				break;
			case I_CUSTOM :
				tag = "font";
				att =
					setFontProperties(
						document.getCharacterElement(editor.getCaretPosition()),
						editor.getSelectedText());
				if (att == null)
					return;
				break;
		}
		String html = "<" + tag + att + ">" + text + "</" + tag + ">";
		if (editor.getCaretPosition() == document.getLength())
			html += "&nbsp;";
		editor.replaceSelection("");
		try {
			editorKit.insertHTML(
				document,
				editor.getCaretPosition(),
				html,
				0,
				0,
				HTML.getTag(tag));
			if (editor.getCaretPosition() == document.getLength())
				editor.setCaretPosition(editor.getCaretPosition() - 1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Do zoom.
	 *
	 * @param in the in
	 */
	void doZoom(boolean in) {
		/**
		 * @todo: ZOOM
		 */

		/*
		 * if (in && (currentFontSize < 7)) currentFontSize++; else if (!in &&
		 * (currentFontSize > 1)) currentFontSize--; else return;
		 * editorKit.getStyleSheet().setBaseFontSize(currentFontSize);
		 *  
		 */

	}

	/**
	 * Sets the document.
	 *
	 * @param doc the new document
	 */
	public void setDocument(Document doc) {
		this.document = (HTMLDocument) doc;
		initEditor();
	}

	/**
	 * Inits the editor.
	 */
	public void initEditor() {
		editor.setDocument(document);
		//undo = new UndoManager();
		resetUndoManager();
		document.addUndoableEditListener(undoHandler);
		editor.scrollRectToVisible(new Rectangle(0,0,0,0));
		editor.setCaretPosition(0);
	}

	/**
	 * Checks if is document changed.
	 *
	 * @return true, if is document changed
	 */
	public boolean isDocumentChanged() {
		return undo.canUndo();
	}

	/**
	 * Sets the style sheet.
	 *
	 * @param r the new style sheet
	 */
	public void setStyleSheet(Reader r) {
		StyleSheet css = new StyleSheet();
		try {
			css.loadRules(r, null);
			/*
			 * new InputStreamReader(
			 * net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResourceAsStream("resources/css/default.css")),
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		editorKit.setStyleSheet(css);
	}
	
	/**
	 * Reload.
	 */
	public void reload() {	
		
	}

	/**
	 * Do find.
	 */
	void doFind() {
		FindDialog dlg = new FindDialog();
		//dlg.setLocation(linkActionB.getLocationOnScreen());
		Dimension dlgSize = dlg.getSize();
		//dlg.setSize(400, 300);
		Dimension frmSize = this.getSize();
		Point loc = this.getLocationOnScreen();
		dlg.setLocation(
			(frmSize.width - dlgSize.width) / 2 + loc.x,
			(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		if (editor.getSelectedText() != null)
			dlg.txtSearch.setText(editor.getSelectedText());
		else if (Context.get("LAST_SEARCHED_WORD") != null)
			dlg.txtSearch.setText(Context.get("LAST_SEARCHED_WORD").toString());
		dlg.setVisible(true);
		if (dlg.CANCELLED)
			return;
		Context.put("LAST_SEARCHED_WORD", dlg.txtSearch.getText());
		String repl = null;
		if (dlg.chkReplace.isSelected())
			repl = dlg.txtReplace.getText();
		Finder finder =
			new Finder(
				this,
				dlg.txtSearch.getText(),
				dlg.chkWholeWord.isSelected(),
				dlg.chkCaseSens.isSelected(),
				dlg.chkRegExp.isSelected(),
				repl);
		finder.start();

	}
}
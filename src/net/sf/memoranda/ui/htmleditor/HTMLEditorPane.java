package net.sf.memoranda.ui.htmleditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JEditorPane;

// TODO: Auto-generated Javadoc
/**
 * The Class HTMLEditorPane.
 */
public class HTMLEditorPane extends JEditorPane {

	/** The anti alias. */
	boolean antiAlias = true;

	/**
	 * Instantiates a new HTML editor pane.
	 *
	 * @param text the text
	 */
	public HTMLEditorPane(String text) {
		super("text/html", text);
	}

	/**
	 * Checks if is antialias on.
	 *
	 * @return true, if is antialias on
	 */
	public boolean isAntialiasOn() {
		return antiAlias;
	}

	/**
	 * Sets the anti alias.
	 *
	 * @param on the new anti alias
	 */
	public void setAntiAlias(boolean on) {
		antiAlias = on;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		if (antiAlias) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			/*g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);*/
			super.paint(g2);
		} else {
			super.paint(g);
		}
	}
}

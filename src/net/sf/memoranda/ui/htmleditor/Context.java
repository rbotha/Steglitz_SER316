package net.sf.memoranda.ui.htmleditor;
import java.util.Hashtable;

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

class Context {

  /** The hash. */
  static java.util.Hashtable hash = new Hashtable();

  /**
   * Gets the.
   *
   * @param key the key
   * @return the object
   */
  public static Object get(Object key) {
    return hash.get(key);
  }

  /**
   * Put.
   *
   * @param key the key
   * @param value the value
   */
  public static void put(Object key, Object value) {
    hash.put(key, value);
  }

}
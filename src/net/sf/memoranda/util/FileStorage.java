/**
 * Storage.java
 * Created on 12.02.2003, 0:21:40 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Note;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.NoteListImpl;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.ResourcesListImpl;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TaskListImpl;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;
import nu.xom.Builder;
import nu.xom.DocType;
import nu.xom.Document;


// TODO: Auto-generated Javadoc
/**
 * The Class FileStorage.
 */
/*$Id: FileStorage.java,v 1.15 2006/10/09 23:31:58 alexeya Exp $*/
public class FileStorage implements Storage {

    /** The jn docpath. */
    public static String JN_DOCPATH = Util.getEnvDir();
    
    /** The editor kit. */
    private HTMLEditorKit editorKit = new HTMLEditorKit();

    /**
     * Instantiates a new file storage.
     */
    public FileStorage() {
        /*The 'MEMORANDA_HOME' key is an undocumented feature for 
          hacking the default location (Util.getEnvDir()) of the memoranda 
          storage dir. Note that memoranda.config file is always placed at fixed 
          location (Util.getEnvDir()) anyway */
        String mHome = (String) Configuration.get("MEMORANDA_HOME");
        if (mHome.length() > 0) {
            JN_DOCPATH = mHome;
            /*DEBUG*/
        	System.out.println("[DEBUG]***Memoranda storage path has set to: " 
            + JN_DOCPATH);
        }
    }

    /**
     * Save document.
     *
     * @param doc the doc
     * @param filePath the file path
     */
    public static void saveDocument(Document doc, String filePath) {
        /**
         * @todo: Configurable parameters
         */
        try {
            /*The XOM bug: reserved characters are not escaped*/
            //Serializer serializer = new Serializer(new FileOutputStream(filePath), "UTF-8");
            //serializer.write(doc);
            OutputStreamWriter fw =
                new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            fw.write(doc.toXML());
            fw.flush();
            fw.close();
        }
        catch (IOException ex) {
            new ExceptionDialog(
                ex,
                "Failed to write a document to " + filePath,
                "");
        }
    }

    /**
     * Open document.
     *
     * @param in the in
     * @return the document
     * @throws Exception the exception
     */
    public static Document openDocument(InputStream in) throws Exception {
        Builder builder = new Builder();
        return builder.build(new InputStreamReader(in, "UTF-8"));
    }

    /**
     * Open document.
     *
     * @param filePath the file path
     * @return the document
     */
    public static Document openDocument(String filePath) {
        try {
            return openDocument(new FileInputStream(filePath));
        }
        catch (Exception ex) {
            new ExceptionDialog(
                ex,
                "Failed to read a document from " + filePath,
                "");
        }
        return null;
    }

    /**
     * Document exists.
     *
     * @param filePath the file path
     * @return true, if successful
     */
    public static boolean documentExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Store note.
     *
     * @param note the note
     * @param doc the doc
     * @see net.sf.memoranda.util.Storage#storeNote(net.sf.memoranda.Note)
     */
    public void storeNote(Note note, javax.swing.text.Document doc) {
        String filename =
            JN_DOCPATH + note.getProject().getID() + File.separator;
        doc.putProperty(
            javax.swing.text.Document.TitleProperty,
            note.getTitle());        
        CalendarDate d = note.getDate();

        filename += note.getId();//d.getDay() + "-" + d.getMonth() + "-" + d.getYear();
        /*DEBUG*/System.out.println("[DEBUG] Save note: "+ filename);

        try {
            OutputStreamWriter fw =
                new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
            AltHTMLWriter writer = new AltHTMLWriter(fw, (HTMLDocument) doc);
            writer.write();
            fw.flush();
            fw.close();
            //editorKit.write(new FileOutputStream(filename), doc, 0, doc.getLength());
            //editorKit.write(fw, doc, 0, doc.getLength());
        }
        catch (Exception ex) {
            new ExceptionDialog(
                ex,
                "Failed to write a document to " + filename,
                "");
        }
        /*String filename = JN_DOCPATH + note.getProject().getID() + "/";
        doc.putProperty(javax.swing.text.Document.TitleProperty, note.getTitle());
        CalendarDate d = note.getDate();
        filename += d.getDay() + "-" + d.getMonth() + "-" + d.getYear();
        try {
            long t1 = new java.util.Date().getTime();
            FileOutputStream ostream = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(ostream);
        
            oos.writeObject((HTMLDocument)doc);
        
            oos.flush();
            oos.close();
            ostream.close();
            long t2 = new java.util.Date().getTime();
            System.out.println(filename+" save:"+ (t2-t1) );
        }
            catch (Exception ex) {
                ex.printStackTrace();
            }*/

    }
    
    /**
     * Open note.
     *
     * @param note the note
     * @return the javax.swing.text. document
     * @see net.sf.memoranda.util.Storage#openNote(net.sf.memoranda.Note)
     */
    public javax.swing.text.Document openNote(Note note) {

        HTMLDocument doc = (HTMLDocument) editorKit.createDefaultDocument();
        if (note == null)
            return doc;
        /*
                String filename = JN_DOCPATH + note.getProject().getID() + File.separator;
                CalendarDate d = note.getDate();
                filename += d.getDay() + "-" + d.getMonth() + "-" + d.getYear();
        */
        String filename = getNotePath(note);
        try {
            /*DEBUG*/

//            Util.debug("Open note: " + filename);
//        	Util.debug("Note Title: " + note.getTitle());
        	doc.setBase(new URL(getNoteURL(note)));
        	editorKit.read(
                new InputStreamReader(new FileInputStream(filename), "UTF-8"),
                doc,
                0);
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            // Do nothing - we've got a new empty document!
        }
        
        return doc;
        /*HTMLDocument doc = (HTMLDocument)editorKit.createDefaultDocument();
        if (note == null) return doc;
        String filename = JN_DOCPATH + note.getProject().getID() + "/";
        CalendarDate d = note.getDate();
        filename += d.getDay() + "-" + d.getMonth() + "-" + d.getYear();
        try {
            long t1 = new java.util.Date().getTime();
            FileInputStream istream = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(istream);
            doc = (HTMLDocument)ois.readObject();
            ois.close();
            istream.close();
            long t2 = new java.util.Date().getTime();
            System.out.println(filename+" open:"+ (t2-t1) );
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return doc;*/
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.util.Storage#getNoteURL(net.sf.memoranda.Note)
     */
    public String getNoteURL(Note note) {        
        return "file:" + JN_DOCPATH + note.getProject().getID() + "/" + note.getId();
    }

   /**
    * Gets the note path.
    *
    * @param note the note
    * @return the note path
    */
   public String getNotePath(Note note) {
        String filename = JN_DOCPATH + note.getProject().getID() + File.separator;
//        CalendarDate d = note.getDate();
        filename += note.getId();//d.getDay() + "-" + d.getMonth() + "-" + d.getYear();
	return filename;
   }


    /* (non-Javadoc)
     * @see net.sf.memoranda.util.Storage#removeNote(net.sf.memoranda.Note)
     */
    public void removeNote(Note note) {
        File f = new File(getNotePath(note));
        /*DEBUG*/
        System.out.println("[DEBUG] Remove note:" + getNotePath(note));
        f.delete();
    }

    /**
     * Open project manager.
     *
     * @see net.sf.memoranda.util.Storage#openProjectManager()
     */
    public void openProjectManager() {
        if (!new File(JN_DOCPATH + ".projects").exists()) {
            ProjectManager._doc = null;
            return;
        }
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Open project manager: " + JN_DOCPATH + ".projects");
        ProjectManager._doc = openDocument(JN_DOCPATH + ".projects");
    }
    
    /**
     * Store project manager.
     *
     * @see net.sf.memoranda.util.Storage#storeProjectManager(nu.xom.Document)
     */
    public void storeProjectManager() {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save project manager: " + JN_DOCPATH + ".projects");
        saveDocument(ProjectManager._doc, JN_DOCPATH + ".projects");
    }
    
    /**
     * Removes the project storage.
     *
     * @param prj the prj
     * @see net.sf.memoranda.util.Storage#removeProject(net.sf.memoranda.Project)
     */
    public void removeProjectStorage(Project prj) {
        String id = prj.getID();
        File f = new File(JN_DOCPATH + id);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++)
            files[i].delete();
        f.delete();
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.util.Storage#openTaskList(net.sf.memoranda.Project)
     */
    public TaskList openTaskList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".tasklist";

        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println(
                "[DEBUG] Open task list: "
                    + JN_DOCPATH
                    + prj.getID()
                    + File.separator
                    + ".tasklist");
            
            Document tasklistDoc = openDocument(fn);
            /*DocType tasklistDoctype = tasklistDoc.getDocType();
            String publicId = null;
            if (tasklistDoctype != null) {
                publicId = tasklistDoctype.getPublicID();
            }
            boolean upgradeOccurred = TaskListVersioning.upgradeTaskList(publicId);
            if (upgradeOccurred) {
                // reload from new file
                tasklistDoc = openDocument(fn);
            }*/
            return new TaskListImpl(tasklistDoc, prj);   
        }
        else {
            /*DEBUG*/
            System.out.println("[DEBUG] New task list created");
            return new TaskListImpl(prj);
        }
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.util.Storage#storeTaskList(net.sf.memoranda.TaskList, net.sf.memoranda.Project)
     */
    public void storeTaskList(TaskList tasklist, Project prj) {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save task list: "
                + JN_DOCPATH
                + prj.getID()
                + File.separator
                + ".tasklist");
        Document tasklistDoc = tasklist.getXMLContent();
        //tasklistDoc.setDocType(TaskListVersioning.getCurrentDocType());
        saveDocument(tasklistDoc,JN_DOCPATH + prj.getID() + File.separator + ".tasklist");
    }
    
    /**
     * Creates the project storage.
     *
     * @param prj the prj
     * @see net.sf.memoranda.util.Storage#createProjectStorage(net.sf.memoranda.Project)
     */
    public void createProjectStorage(Project prj) {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Create project dir: " + JN_DOCPATH + prj.getID());
        File dir = new File(JN_DOCPATH + prj.getID());
        dir.mkdirs();
    }
    
    /**
     * Open note list.
     *
     * @param prj the prj
     * @return the note list
     * @see net.sf.memoranda.util.Storage#openNoteList(net.sf.memoranda.Project)
     */
    public NoteList openNoteList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".notes";
        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println(
                "[DEBUG] Open note list: "
                    + JN_DOCPATH
                    + prj.getID()
                    + File.separator
                    + ".notes");
            return new NoteListImpl(openDocument(fn), prj);
        }
        else {
            /*DEBUG*/
            System.out.println("[DEBUG] New note list created");
            return new NoteListImpl(prj);
        }
    }
    
    /**
     * Store note list.
     *
     * @param nl the nl
     * @param prj the prj
     * @see net.sf.memoranda.util.Storage#storeNoteList(net.sf.memoranda.NoteList, net.sf.memoranda.Project)
     */
    public void storeNoteList(NoteList nl, Project prj) {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save note list: "
                + JN_DOCPATH
                + prj.getID()
                + File.separator
                + ".notes");
        saveDocument(
            nl.getXMLContent(),
            JN_DOCPATH + prj.getID() + File.separator + ".notes");
    }
    
    /**
     * Open events manager.
     *
     * @see net.sf.memoranda.util.Storage#openEventsList()
     */
    public void openEventsManager() {
        if (!new File(JN_DOCPATH + ".events").exists()) {
            EventsManager._doc = null;
            return;
        }
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Open events manager: " + JN_DOCPATH + ".events");
        EventsManager._doc = openDocument(JN_DOCPATH + ".events");
    }
    
    /**
     * Store events manager.
     *
     * @see net.sf.memoranda.util.Storage#storeEventsList()
     */
    public void storeEventsManager() {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save events manager: " + JN_DOCPATH + ".events");
        saveDocument(EventsManager._doc, JN_DOCPATH + ".events");
    }
    
    /**
     * Open mime types list.
     *
     * @see net.sf.memoranda.util.Storage#openMimeTypesList()
     */
    public void openMimeTypesList() {
        if (!new File(JN_DOCPATH + ".mimetypes").exists()) {
            try {
                MimeTypesList._doc =
                    openDocument(
                        FileStorage.class.getResourceAsStream(
                            "resources/default.mimetypes"));
            }
            catch (Exception e) {
                new ExceptionDialog(
                    e,
                    "Failed to read default mimetypes config from resources",
                    "");
            }
            return;
        }
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Open mimetypes list: " + JN_DOCPATH + ".mimetypes");
        MimeTypesList._doc = openDocument(JN_DOCPATH + ".mimetypes");
    }
    
    /**
     * Store mime types list.
     *
     * @see net.sf.memoranda.util.Storage#storeMimeTypesList()
     */
    public void storeMimeTypesList() {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save mimetypes list: " + JN_DOCPATH + ".mimetypes");
        saveDocument(MimeTypesList._doc, JN_DOCPATH + ".mimetypes");
    }
    
    /**
     * Open resources list.
     *
     * @param prj the prj
     * @return the resources list
     * @see net.sf.memoranda.util.Storage#openResourcesList(net.sf.memoranda.Project)
     */
    public ResourcesList openResourcesList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".resources";
        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println("[DEBUG] Open resources list: " + fn);
            return new ResourcesListImpl(openDocument(fn), prj);
        }
        else {
            /*DEBUG*/
            System.out.println("[DEBUG] New note list created");
            return new ResourcesListImpl(prj);
        }
    }
    
    /**
     * Store resources list.
     *
     * @param rl the rl
     * @param prj the prj
     * @see net.sf.memoranda.util.Storage#storeResourcesList(net.sf.memoranda.ResourcesList, net.sf.memoranda.Project)
     */
    public void storeResourcesList(ResourcesList rl, Project prj) {
        /*DEBUG*/
        System.out.println(
            "[DEBUG] Save resources list: "
                + JN_DOCPATH
                + prj.getID()
                + File.separator
                + ".resources");
        saveDocument(
            rl.getXMLContent(),
            JN_DOCPATH + prj.getID() + File.separator + ".resources");
    }
    
    /**
     * Restore context.
     *
     * @see net.sf.memoranda.util.Storage#restoreContext()
     */
    public void restoreContext() {
        try {
            /*DEBUG*/
            System.out.println(
                "[DEBUG] Open context: " + JN_DOCPATH + ".context");
            Context.context.load(new FileInputStream(JN_DOCPATH + ".context"));
        }
        catch (Exception ex) {
            /*DEBUG*/
            System.out.println("Context created.");
        }
    }
    
    /**
     * Store context.
     *
     * @see net.sf.memoranda.util.Storage#storeContext()
     */
    public void storeContext() {
        try {
            /*DEBUG*/
            System.out.println(
                "[DEBUG] Save context: " + JN_DOCPATH + ".context");
            Context.context.save(new FileOutputStream(JN_DOCPATH + ".context"));
        }
        catch (Exception ex) {
            new ExceptionDialog(
                ex,
                "Failed to store context to " + JN_DOCPATH + ".context",
                "");
        }
    }

}

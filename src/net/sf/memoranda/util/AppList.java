/**
 * AppList.java
 * Created on 24.03.2003, 14:21:50 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;
import java.util.StringTokenizer;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
// TODO: Auto-generated Javadoc

/**
 * The Class AppList.
 */
/*$Id: AppList.java,v 1.5 2007/03/20 08:22:41 alexeya Exp $*/
public class AppList {

    /** The root. */
    public Element _root = null;

    /** The os windows. */
    public static String OS_WINDOWS = "windows";
    
    /** The os linux. */
    public static String OS_LINUX = "linux";
    
    /** The os solaris. */
    public static String OS_SOLARIS = "solaris";

    /**
     * Gets the plaf code.
     *
     * @param osName the os name
     * @return the plaf code
     */
    public static String getPlafCode(String osName) {
        osName = osName.toLowerCase();
        if (osName.indexOf(OS_WINDOWS) > -1)
            return OS_WINDOWS;
        else if (osName.indexOf(OS_LINUX) > -1)
            return OS_LINUX;
        else if (osName.indexOf(OS_SOLARIS) > -1)
            return OS_SOLARIS;
        return "unknown";
    }
    
    /**
     * Constructor for AppList.
     *
     * @param root the root
     */
    public AppList(Element root) {
        _root = root;
    }

    /**
     * Gets the command line.
     *
     * @param appId the app id
     * @param fPath the f path
     * @return the command line
     */
    public String getCommandLine(String appId, String fPath) {
        Elements apps = _root.getChildElements("app");
        fPath = fPath.replaceAll("\\\\", "\\\\\\\\");        
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                Element app = apps.get(i);
                String pt = app.getAttribute("command").getValue();
                if (fPath.indexOf(' ') >= 0)
                   fPath = '"' + fPath + '"'; 
                pt = pt.replaceAll("\\$1", fPath);
                return app.getAttribute("findPath").getValue() + "/" + app.getAttribute("exec").getValue()+" "+pt;
            }
        return null;
    }
    
    /**
     * Gets the command.
     *
     * @param appId the app id
     * @param fPath the f path
     * @return the command
     */
    public String[] getCommand(String appId, String fPath) {
        Elements apps = _root.getChildElements("app");
        //fPath = fPath.replaceAll("\\\\", "\\\\\\\\");        
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                Element app = apps.get(i);
                String command = app.getAttribute("command").getValue();
                String exec = app.getAttribute("findPath").getValue() + "/" + app.getAttribute("exec").getValue();
                StringTokenizer st = new StringTokenizer(command);
                String[] cmdarray = new String[st.countTokens()+1];
                cmdarray[0] = exec;
                for (int j = 1; st.hasMoreTokens(); j++) {
                    String tk = st.nextToken();
                    if (tk.equals("$1"))
                        cmdarray[j] = fPath;
                    else
                        cmdarray[j] = tk;
                }
                return cmdarray;
            }
        return null;
    }

    /**
     * Gets the find path.
     *
     * @param appId the app id
     * @return the find path
     */
    public String getFindPath(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("findPath").getValue();
        return null;
    }

    /**
     * Sets the find path.
     *
     * @param appId the app id
     * @param p the p
     */
    public void setFindPath(String appId, String p) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
              if (apps.get(i).getAttribute("findPath") == null)
                apps.get(i).addAttribute(new Attribute("findPath", p));
              else
                apps.get(i).getAttribute("findPath").setValue(p);
            }

    }

    /**
     * Gets the exec.
     *
     * @param appId the app id
     * @return the exec
     */
    public String getExec(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("exec").getValue();
        return null;
    }

    /**
     * Sets the exec.
     *
     * @param appId the app id
     * @param e the e
     */
    public void setExec(String appId, String e) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)){
              if (apps.get(i).getAttribute("exec") == null)
                apps.get(i).addAttribute(new Attribute("exec", e));
              else
                apps.get(i).getAttribute("exec").setValue(e);
            }
    }

    /**
     * Gets the command line pattern.
     *
     * @param appId the app id
     * @return the command line pattern
     */
    public String getCommandLinePattern(String appId) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                return apps.get(i).getAttribute("command").getValue();
        return null;
    }

    /**
     * Sets the command line pattern.
     *
     * @param appId the app id
     * @param clp the clp
     */
    public void setCommandLinePattern(String appId, String clp) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId))
                apps.get(i).addAttribute(new Attribute("command", clp));
    }

    /**
     * Adds the app.
     *
     * @param appId the app id
     * @param fp the fp
     * @param exec the exec
     * @param clp the clp
     */
    public void addApp(String appId, String fp, String exec, String clp) {
        Element el = new Element("app");
        el.addAttribute(new Attribute("id", appId));
        el.addAttribute(new Attribute("command", clp));
        el.addAttribute(new Attribute("exec", exec));
        el.addAttribute(new Attribute("findPath", fp));
        _root.appendChild(el);
    }

    /**
     * Adds the or replace app.
     *
     * @param appId the app id
     * @param fp the fp
     * @param exec the exec
     * @param clp the clp
     */
    public void addOrReplaceApp(String appId, String fp, String exec, String clp) {
        Elements apps = _root.getChildElements("app");
        for (int i = 0; i < apps.size(); i++)
            if (apps.get(i).getAttribute("id").getValue().equals(appId)) {
                 if (apps.get(i).getAttribute("findPath") == null)
                  apps.get(i).addAttribute(new Attribute("findPath", fp));
                 else
                  apps.get(i).getAttribute("findPath").setValue(fp);
                 if (apps.get(i).getAttribute("exec") == null)
                  apps.get(i).addAttribute(new Attribute("exec", exec));
                 else
                  apps.get(i).getAttribute("exec").setValue(exec);
                if (apps.get(i).getAttribute("command") == null)
                  apps.get(i).addAttribute(new Attribute("command", clp));
                else
                  apps.get(i).getAttribute("command").setValue(clp);
                return;
            }
        addApp(appId, fp, exec, clp);
    }
    
    /**
     * Gets the browser exec.
     *
     * @return the browser exec
     */
    public String getBrowserExec() {
        Elements els = _root.getChildElements("browser");        
        if (els.size() < 1) return null;
        Element el = els.get(0);
        return (el.getAttribute("path").getValue());
    }
    
    /**
     * Sets the browser exec.
     *
     * @param path the new browser exec
     */
    public void setBrowserExec(String path) {
        Element el = null;
        Elements els = _root.getChildElements("browser");    
        if (els.size() < 1) {
            el = new Element("browser");
            _root.appendChild(el);
        }
        else
            el = els.get(0);
        if (el.getAttribute("path") != null)
            el.getAttribute("path").setValue(path);
        else 
            el.addAttribute(new Attribute("path", path));
    }
}

package main.java.memoranda.ui;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.UIManager;
import main.java.memoranda.EventsScheduler;
import main.java.memoranda.database.SqlConnection;
import main.java.memoranda.util.Configuration;

/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
public class App {

    /**
     * The Frame.
     */
    static AppFrame frame = null;

    /**
     * The constant GUIDE_URL.
     */
    public static final String GUIDE_URL = "http://memoranda.sourceforge.net/guide.html";
    /**
     * The constant BUGS_TRACKER_URL.
     */
    public static final String BUGS_TRACKER_URL =
        "http://sourceforge.net/tracker/?group_id=90997&atid=595566";
    /**
     * The constant WEBSITE_URL.
     */
    public static final String WEBSITE_URL = "http://globogym.com";

    public static SqlConnection conn = null;

    FileInputStream input;

    /*========================================================================*/
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using 
       'version' property and datestamp.*/

    /**
     * The constant VERSION_INFO.
     */
    public static String VERSION_INFO = "1.0.0"; // default
    /**
     * The constant BUILD_INFO.
     */
    public static String BUILD_INFO = "1.0.0"; // default

    /*========================================================================*/

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public static AppFrame getFrame() {
        return frame;
    }

    /**
     * Show.
     */
    public void show() {
        if (frame.isVisible()) {
            frame.toFront();
            frame.requestFocus();
        } else {
            init();
        }
    }

    /**
     * Instantiates a new App.
     *
     * @param fullmode the fullmode
     */
    public App(boolean fullmode, SqlConnection connection) throws IOException {
        super();

        this.conn = connection;

        // Updates the version and build numbers via the build.gradle file
        try {
            input = new FileInputStream("build/gradle.properties");
            Properties prop = new Properties();
            prop.load(input);
            VERSION_INFO = prop.getProperty("version");
            BUILD_INFO = prop.getProperty("build");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (fullmode) {
            fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
        }
        /* DEBUG */
        if (!fullmode) {
            System.out.println("Minimized mode");
        }
        System.out.println(VERSION_INFO);
        System.out.println(Configuration.get("LOOK_AND_FEEL"));
        try {
            if (Configuration.get("LOOK_AND_FEEL").equals("system")) {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } else if (Configuration.get("LOOK_AND_FEEL").equals("default")) {
                UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
            } else if (
                Configuration.get("LOOK_AND_FEEL").toString().length() > 0) {
                UIManager.setLookAndFeel(
                    Configuration.get("LOOK_AND_FEEL").toString());
            }

        } catch (Exception e) {
            new ExceptionDialog(e,
                "Error when initializing a pluggable look-and-feel. Default LF will be used.",
                "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
        }
        if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
            String fdow;
            if (Calendar.getInstance().getFirstDayOfWeek() == 2) {
                fdow = "mon";
            } else {
                fdow = "sun";
            }
            Configuration.put("FIRST_DAY_OF_WEEK", fdow);
            Configuration.saveConfig();
            /* DEBUG */
            System.out.println("[DEBUG] first day of week is set to " + fdow);
        }

        EventsScheduler.init();
        frame = new AppFrame();
        if (fullmode) {
            init();
        }

    }

    /**
     * Init.
     */
    static void init() {

        /*
         * if (packFrame) { frame.pack(); } else { frame.validate(); }
         *
         * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         *
         * Dimension frameSize = frame.getSize(); if (frameSize.height >
         * screenSize.height) { frameSize.height = screenSize.height; } if
         * (frameSize.width > screenSize.width) { frameSize.width =
         * screenSize.width; }
         *
         *
         * Make the window fullscreen - On Request of users This seems not to
         * work on sun's version 1.4.1_01 Works great with 1.4.2 !!! So update
         * your J2RE or J2SDK.
         */
        /* Used to maximize the screen if the JVM Version if 1.4 or higher */
        /* --------------------------------------------------------------- */
        double JVMVer =
            Double
                .valueOf(System.getProperty("java.version").substring(0, 3))
                .doubleValue();

        frame.pack();
        if (JVMVer >= 1.4) {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
            frame.setExtendedState(Frame.NORMAL);
        }
        /* --------------------------------------------------------------- */
        /* Added By Jeremy Whitlock (jcscoobyrs) 07-Nov-2003 at 15:54:24 */

        // Not needed ???
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();

    }

    /**
     * Close window.
     */
    public static void closeWindow() {
        if (frame == null) {
            return;
        }
        frame.dispose();
    }
}
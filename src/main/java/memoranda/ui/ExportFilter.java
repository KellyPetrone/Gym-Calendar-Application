package main.java.memoranda.ui;

/**
 * ExportFilter.java
 * Created on 25.02.2003, 17:30:12 Alex
 * Package: 
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * The type Export filter.
 */
/*$Id: ExportFilter.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public class ExportFilter extends FileFilter {

    /**
     * The constant RTF.
     */
    public static final String RTF = "RTF";
    /**
     * The constant HTML.
     */
    public static final String HTML = "HTML";
    /**
     * The constant XHTML.
     */
    public static final String XHTML = "XHTML";
    /**
     * The constant ZIP.
     */
    public static final String ZIP = "ZIP";

    /**
     * The Type.
     */
    String _type = "";

    /**
     * Constructor for ExportFilter.
     *
     * @param type the type
     */
    public ExportFilter(String type) {
        super();
        _type = type;
    }

    /**
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        if (f.isDirectory()) 
            return true;        
        String ext = getExtension(f);
        if (_type.equals(RTF))
            return ext.equals("rtf");
        else if (_type.equals(ZIP))
            return ext.equals("zip");
        else if (_type.equals(XHTML))
            return (ext.equals("xhtml") || ext.equals("xml")); 
        return ext.startsWith("htm");
    }

    /**
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() {
        if (_type.equals(RTF))
            return "Rich Text Format (*.rtf)";
        else if (_type.equals(ZIP))
            return "ZIP archives (*.zip)";
        else if (_type.equals(XHTML))
            return "XHTML files (*.xhtml, *.xml)";
        return "HTML files (*.html, *.htm)";
    }

    private static String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}

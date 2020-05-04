package main.java.memoranda.ui;

import main.java.memoranda.CurrentNote;
import main.java.memoranda.CurrentProject;
import main.java.memoranda.Note;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * The type Notes control panel.
 */
/*$Id: NotesControlPanel.java,v 1.16 2005/05/05 16:19:16 ivanrise Exp $*/
public class NotesControlPanel extends JPanel {
    /**
     * The Border layout 1.
     */
    BorderLayout borderLayout1 = new BorderLayout();
    /**
     * The Search panel.
     */
    SearchPanel searchPanel = new SearchPanel();
    /**
     * The Notes list panel.
     */
    NotesListPanel notesListPanel = new NotesListPanel();
    /**
     * The Bookmarks list panel.
     */
    BookmarksPanel bookmarksListPanel = new BookmarksPanel();
    /**
     * The Tabbed pane.
     */
    JTabbedPane tabbedPane = new JTabbedPane();
    /**
     * The Tool bar.
     */
    JToolBar toolBar = new JToolBar();

    /**
     * The Notes list.
     */
    NotesList notesList = null;

    /**
     * The Flow layout 1.
     */
    FlowLayout flowLayout1 = new FlowLayout();
    /**
     * The Pp open b.
     */
    JButton ppOpenB = new JButton();
    /**
     * The Buttons panel.
     */
    JPanel buttonsPanel = new JPanel();
    /**
     * The Pp add bkmrk.
     */
    JMenuItem ppAddBkmrk = new JMenuItem();
    /**
     * The Pp clear note.
     */
    JMenuItem ppClearNote = new JMenuItem();
    /**
     * The Pp invert sort.
     */
//    JMenuItem ppInvertSort = new JMenuItem();
	JCheckBoxMenuItem ppInvertSort = new JCheckBoxMenuItem();
    /**
     * The Notes pp menu.
     */
    JPopupMenu notesPPMenu = new JPopupMenu();
    /**
     * The Pp open note.
     */
    JMenuItem ppOpenNote = new JMenuItem();
    /**
     * The Pp remove bkmrk.
     */
    JMenuItem ppRemoveBkmrk = new JMenuItem();


    /**
     * Instantiates a new Notes control panel.
     */
    public NotesControlPanel() {
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
        tabbedPane.setFont(new java.awt.Font("Dialog", 1, 10));
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tabbedPane_stateChanged(e);
            }
        });
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        this.setLayout(borderLayout1);
        toolBar.setRequestFocusEnabled(false);
        toolBar.setFloatable(false);

        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setVgap(0);
        ppOpenB.setMaximumSize(new Dimension(34, 20));
        ppOpenB.setMinimumSize(new Dimension(24, 10));
        ppOpenB.setOpaque(false);
        ppOpenB.setPreferredSize(new Dimension(24, 20));
        ppOpenB.setBorderPainted(false);
        ppOpenB.setFocusPainted(false);
        ppOpenB.setMargin(new Insets(0, 0, 0, 0));
        ppOpenB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppOpenB_actionPerformed(e);
            }
        });
        ppOpenB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/nopen.png")));
        buttonsPanel.setMinimumSize(new Dimension(70, 22));
        buttonsPanel.setOpaque(false);
        //buttonsPanel.setPreferredSize(new Dimension(80, 22));
        buttonsPanel.setRequestFocusEnabled(false);
        buttonsPanel.setLayout(flowLayout1);
        ppAddBkmrk.setFont(new java.awt.Font("Dialog", 1, 11));
        ppAddBkmrk.setText(Local.getString("Set bookmark"));
        ppAddBkmrk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppAddBkmrk_actionPerformed(e);
            }
        });
        ppAddBkmrk.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/addbookmark.png")));
        ppClearNote.setFont(new java.awt.Font("Dialog", 1, 11));
        ppClearNote.setText(Local.getString("Clear note"));
        ppClearNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppClearNote_actionPerformed(e);
            }
        });
        ppClearNote.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/editdelete.png")));
        ppClearNote.setEnabled(false);
        notesPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppOpenNote.setFont(new java.awt.Font("Dialog", 1, 11));
        ppOpenNote.setText(Local.getString("Go to note"));
        ppOpenNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ppOpenNote_actionPerformed(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ppOpenNote.setEnabled(false);

        ppInvertSort.setFont(new java.awt.Font("Dialog", 1, 11));
        ppInvertSort.setText(Local.getString("Invert Sort Order"));
        ppInvertSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppInvertSort_actionPerformed(e);
            }
        });
        ppInvertSort.setEnabled(true);
		boolean descSort =
			(Configuration.get("NOTES_SORT_ORDER").equals("true"));
		ppInvertSort.setSelected(descSort);
		
        ppRemoveBkmrk.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveBkmrk.setText(Local.getString("Remove bookmark"));
        ppRemoveBkmrk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveBkmrk_actionPerformed(e);
            }
        });
        ppRemoveBkmrk.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/removebookmark.png")));
        ppRemoveBkmrk.setEnabled(false);
        tabbedPane.add(notesListPanel, Local.getString("Notes"));
        tabbedPane.add(bookmarksListPanel, Local.getString("Bookmarks"));
        tabbedPane.add(searchPanel, Local.getString("Search"));
        this.add(toolBar, BorderLayout.NORTH);
        buttonsPanel.add(ppOpenB, null);
        toolBar.add(buttonsPanel, null);
        toolBar.addSeparator();        
        this.add(tabbedPane, BorderLayout.CENTER);

        PopupListener lst = new PopupListener();
        notesListPanel.notesList.addMouseListener(lst);
        bookmarksListPanel.notesList.addMouseListener(lst);
        searchPanel.notesList.addMouseListener(lst);
        ListSelectionListener lsl = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
		ppSetEnabled();
            }
        };
        notesListPanel.notesList.getSelectionModel().addListSelectionListener(lsl);
        bookmarksListPanel.notesList.getSelectionModel().addListSelectionListener(lsl);
        searchPanel.notesList.getSelectionModel().addListSelectionListener(lsl);
        notesList = notesListPanel.notesList;
        notesPPMenu.add(ppOpenNote);
        notesPPMenu.add(ppInvertSort);
        notesPPMenu.addSeparator();        
        notesPPMenu.add(ppAddBkmrk);
        notesPPMenu.add(ppRemoveBkmrk);
        notesPPMenu.addSeparator();
        notesPPMenu.add(ppClearNote);

		// remove notes using the DEL key
		KeyListener delNotes = new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					ppClearNote_actionPerformed(null);
				}
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		};
		
		notesListPanel.notesList.addKeyListener(delNotes);
		bookmarksListPanel.notesList.addKeyListener(delNotes);
		searchPanel.notesList.addKeyListener(delNotes);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        notesListPanel.notesList.update();
        bookmarksListPanel.notesList.update();
    }

    /**
     * Tabbed pane state changed.
     *
     * @param e the e
     */
    void tabbedPane_stateChanged(ChangeEvent e) {
	if(notesList!=null) notesList.clearSelection();
        switch (tabbedPane.getSelectedIndex()) {
            case 0 :
                notesList = notesListPanel.notesList;
                break;
            case 1 :
                notesList = bookmarksListPanel.notesList;
                break;
            case 2 :
                notesList = searchPanel.notesList;
                break;
        }

	ppAddBkmrk.setEnabled(false);
	ppRemoveBkmrk.setEnabled(false);
    }

    /**
     * The type Popup listener.
     */
    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                try {
                    setActiveNote();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void mousePressed(MouseEvent e) {
             maybeShowPopup(e);
         }
        
         public void mouseReleased(MouseEvent e) {
             maybeShowPopup(e);
         }
        
         private void maybeShowPopup(MouseEvent e) {
             if (e.isPopupTrigger()) {
                 notesPPMenu.show(e.getComponent(), e.getX(), e.getY());
             }
         }
    }

    /**
     * Sets active note.
     */
    void setActiveNote() throws SQLException {
        Note note = (Note) notesList.getNote(notesList.getSelectedIndex());
        CurrentDate.set(note.getDate());
		CurrentNote.set(note,true);
    }


    /**
     * Pp open b action performed.
     *
     * @param e the e
     */
    void ppOpenB_actionPerformed(ActionEvent e) {
        notesPPMenu.show(
            toolBar,
            (int) ppOpenB.getLocation().getX(),
            (int) ppOpenB.getLocation().getY() + 24);
    }

    /**
     * Pp add bkmrk action performed.
     *
     * @param e the e
     */
    void ppAddBkmrk_actionPerformed(ActionEvent e) {
        for (int i = 0; i < notesList.getSelectedIndices().length; i++) {
            Note note = (Note) notesList.getNote(notesList.getSelectedIndices()[i]);
            note.setMark(true);
        }
        notesList.updateUI();
        bookmarksListPanel.notesList.update();
	ppSetEnabled();
    }

    /**
     * Pp clear note action performed.
     *
     * @param e the e
     */
    void ppClearNote_actionPerformed(ActionEvent e) {
        String msg;
        if (notesList.getSelectedIndices().length > 1)
            msg =
                Local.getString(Local.getString("Clear"))
                    + " "
                    + notesList.getSelectedIndices().length
                    + " "
                    + Local.getString("notes")
                    + "\n"
                    + Local.getString("Are you sure?");
        else
            msg =
                Local.getString("Clear note")
                    + "\n'"
                    + ((Note) notesList.getNote(notesList.getSelectedIndex())).getDate().getFullDateString()
                    + "'\n"
                    + Local.getString("Are you sure?");

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Clear note"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;

        for (int i = 0; i < notesList.getSelectedIndices().length; i++) {
            Note note = (Note) notesList.getNote(notesList.getSelectedIndices()[i]);
			if(CurrentProject.getNoteList().getActiveNote() != null && note.getDate().equals(CurrentProject.getNoteList().getActiveNote().getDate())){ 
				/*Debug*/ System.out.println("[DEBUG] Current note removed");
				CurrentNote.set(null,true);
			}
			CurrentProject.getNoteList().removeNote(note.getDate(), note.getId());
			CurrentStorage.get().removeNote(note);
        }
        bookmarksListPanel.notesList.update();
		searchPanel.notesList.update();
		notesListPanel.notesList.update();
        notesList.updateUI();
		notesList.clearSelection();
//		notesList.requestFocus();*/
//		((AppFrame)App.getFrame()).workPanel.dailyItemsPanel.editorPanel.editor.requestFocus();
    }

    /**
     * Pp open note action performed.
     *
     * @param e the e
     */
    void ppOpenNote_actionPerformed(ActionEvent e) throws SQLException {
        setActiveNote();
    }

    /**
     * Pp invert sort action performed.
     *
     * @param e the e
     */
    void ppInvertSort_actionPerformed(ActionEvent e) {
		Configuration.put(
			"NOTES_SORT_ORDER",
			new Boolean(ppInvertSort.isSelected()));
		Configuration.saveConfig();
        notesList.invertSortOrder();
        notesList.update();
    }

    /**
     * Pp remove bkmrk action performed.
     *
     * @param e the e
     */
    void ppRemoveBkmrk_actionPerformed(ActionEvent e) {
        for (int i = 0; i < notesList.getSelectedIndices().length; i++) {
            Note note = (Note) notesList.getNote(notesList.getSelectedIndices()[i]);
            note.setMark(false);
        }
        bookmarksListPanel.notesList.update();
	ppSetEnabled();
        notesList.updateUI();
	notesList.clearSelection();

    }

    /**
     * Pp set enabled.
     */
    void ppSetEnabled() {
    boolean enbl = (notesList.getModel().getSize() > 0) && (notesList.getSelectedIndex() > -1);

    ppRemoveBkmrk.setEnabled(enbl && (((Note) notesList.getNote(notesList.getSelectedIndex())).isMarked())
    				|| notesList.getSelectedIndices().length > 1);
    ppAddBkmrk.setEnabled(enbl && !(((Note) notesList.getNote(notesList.getSelectedIndex())).isMarked())
    				|| notesList.getSelectedIndices().length > 1);
    ppOpenNote.setEnabled(enbl);
    ppClearNote.setEnabled(enbl);
    }
}
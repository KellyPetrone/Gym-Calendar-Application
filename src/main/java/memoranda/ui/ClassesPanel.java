package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.EventsManager;
import main.java.memoranda.EventsScheduler;
import main.java.memoranda.History;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;


/**
 * Controls the Classes Panel on memoranda
 * Inserts room scrollpanes for each room
 * and instantiates the EventsTable
 * @param <newButtonFont>
 */
public class ClassesPanel<newButtonFont> extends JPanel {
    /**
     * The Border layout 1.
     */
    BorderLayout borderLayout1 = new BorderLayout();
    /**
     * The History back b.
     */
    JButton historyBackB = new JButton();
    /**
     * The Events tool bar.
     */
    JToolBar eventsToolBar = new JToolBar();
    /**
     * The History forward b.
     */
    JButton historyForwardB = new JButton();
    /**
     * The New event b.
     */
    JButton newEventB = new JButton("Schedule New Class");


    /**
     * The Edit event b.
     */
    JButton editEventB = new JButton("Edit Existing Class");
    /**
     * The Set Availability event b.
     */
    JButton setEventB = new JButton("Set My Availability");
    /**
     * The Schedule Private Class event b.
     */
    JButton privateClassEventB = new JButton("Schedule Private Class");
    /**
     * The Enroll in Class event b.
     */
    JButton enrollClassEventB = new JButton("Enroll in Class");
    /**
     * The Enroll in Class event b.
     */
    JButton editEnrollClassEventB = new JButton("Cancel My Enrollment");
    /**
     * The Remove event b.
     */
    JButton removeEventB = new JButton("Remove Class");
    /**
     * The Scroll pane.
     */
    JScrollPane room1ScrollPane = new JScrollPane();
    JScrollPane room2ScrollPane = new JScrollPane();
    JScrollPane room3ScrollPane = new JScrollPane();
    JScrollPane room4ScrollPane = new JScrollPane();

    /**
     * The Events table.
     */
    EventsTable Room1 = new EventsTable(1);
    EventsTable Room2 = new EventsTable(2);
    EventsTable Room3 = new EventsTable(3);
    EventsTable Room4 = new EventsTable(4);
    /**
     * The Event pp menu.
     */
    JPopupMenu eventPPMenu = new JPopupMenu();
    /**
     * The Pp edit event.
     */
    JMenuItem ppEditEvent = new JMenuItem();
    /**
     * The Pp remove event.
     */
    JMenuItem ppRemoveEvent = new JMenuItem();
    /**
     * The Pp new event.
     */
    JMenuItem ppNewEvent = new JMenuItem();
    /**
     * The Pp new private event.
     */
    JMenuItem ppPrivateClassEventB = new JMenuItem();
    /**
     * The Parent panel.
     */
    DailyItemsPanel parentPanel = null;

    /**
     * Instantiates a new Events panel.
     *
     * @param _parentPanel the parent panel
     */
    public ClassesPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
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
        eventsToolBar.setFloatable(false);

        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");

        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");


        // newEventB Schedule Class Button for Class Page with event handler that creates new classes
        Color color = Color.decode("#16034f");
        newEventB.setBackground(color);
        newEventB.setForeground(Color.WHITE);
        newEventB.setEnabled(true);
        newEventB.setMaximumSize(new Dimension(130, 24));
        newEventB.setMinimumSize(new Dimension(130, 24));
        newEventB.setToolTipText(Local.getString("Schedule Class"));
        newEventB.setRequestFocusEnabled(false);
        newEventB.setPreferredSize(new Dimension(130, 24));
        newEventB.setFocusable(false);
        newEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newEventB_actionPerformed(e);
            }
        });
        newEventB.setBorderPainted(false);
        newEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));


        // privateEventB Schedule private Button for Class Page with event handler that allows to created private classes

        privateClassEventB.setBackground(color);
        privateClassEventB.setForeground(Color.WHITE);
        privateClassEventB.setEnabled(true);
        privateClassEventB.setMaximumSize(new Dimension(130, 24));
        privateClassEventB.setMinimumSize(new Dimension(130, 24));
        privateClassEventB.setToolTipText(Local.getString("Schedule Private Class"));
        privateClassEventB.setRequestFocusEnabled(false);
        privateClassEventB.setPreferredSize(new Dimension(130, 24));
        privateClassEventB.setFocusable(false);
        privateClassEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                privateClassEventB_actionPerformed(e);
            }
        });
        privateClassEventB.setBorderPainted(false);
        privateClassEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));


        // editEventB Edit Existing Class Button for Class Page with event handler that modifies created classes
        editEventB.setBackground(Color.GRAY);
        editEventB.setForeground(Color.WHITE);
        editEventB.setBorderPainted(false);
        editEventB.setFocusable(false);
        editEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editEventB_actionPerformed(e);
            }
        });
        editEventB.setPreferredSize(new Dimension(130, 24));
        editEventB.setRequestFocusEnabled(false);
        editEventB.setToolTipText(Local.getString("Edit Existing Class"));
        editEventB.setMinimumSize(new Dimension(130, 24));
        editEventB.setMaximumSize(new Dimension(130, 24));
        editEventB.setEnabled(true);
        editEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));


        // removeEventB Cancel Scheduled Class Button for Class Page with event handler
// that removes scheduled class
        removeEventB.setBackground(Color.GRAY);
        removeEventB.setForeground(Color.WHITE);
        removeEventB.setBorderPainted(false);
        removeEventB.setFocusable(false);
        removeEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeEventB_actionPerformed(e);
            }
        });
        removeEventB.setPreferredSize(new Dimension(130, 24));
        removeEventB.setRequestFocusEnabled(false);
        removeEventB.setToolTipText(Local.getString("Remove event"));
        removeEventB.setMinimumSize(new Dimension(130, 24));
        removeEventB.setMaximumSize(new Dimension(130, 24));
        removeEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));


        // setEventB Set Availability Button for Class Page with event handler that lets trainer or admit
        //to set their available time

        setEventB.setBackground(Color.GRAY);
        setEventB.setForeground(Color.WHITE);
        setEventB.setEnabled(true);
        setEventB.setMaximumSize(new Dimension(130, 24));
        setEventB.setMinimumSize(new Dimension(130, 24));
        setEventB.setToolTipText(Local.getString("Set Availability"));
        setEventB.setRequestFocusEnabled(false);
        setEventB.setPreferredSize(new Dimension(130, 24));
        setEventB.setFocusable(false);
        setEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEventB_actionPerformed(e);
            }
        });
        setEventB.setBorderPainted(false);
        setEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));



        // enrollEventB Enroll in Class Button for Class Page with event handler that allows all users to enroll to class

        Color color1 = Color.decode("#5a2980");
        enrollClassEventB.setBackground(color1);
        enrollClassEventB.setForeground(Color.WHITE);
        enrollClassEventB.setBorderPainted(false);
        enrollClassEventB.setFocusable(false);
        enrollClassEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enrollClassEventB_actionPerformed(e);
            }
        });
        enrollClassEventB.setPreferredSize(new Dimension(130, 24));
        enrollClassEventB.setRequestFocusEnabled(false);
        enrollClassEventB.setToolTipText(Local.getString("Enroll in Class"));
        enrollClassEventB.setMinimumSize(new Dimension(130, 24));
        enrollClassEventB.setMaximumSize(new Dimension(130, 24));
        enrollClassEventB.setEnabled(true);
        enrollClassEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));


        // editEnrollEventB Cancel My Enrolment Button for Class Page with event handler that modifies enrolment of the user

        editEnrollClassEventB.setBackground(color1);
        editEnrollClassEventB.setForeground(Color.WHITE);
        editEnrollClassEventB.setBorderPainted(false);
        editEnrollClassEventB.setFocusable(false);
        editEnrollClassEventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editEnrollClassEventB_actionPerformed(e);
            }
        });
        editEnrollClassEventB.setPreferredSize(new Dimension(130, 24));
        editEnrollClassEventB.setRequestFocusEnabled(false);
        editEnrollClassEventB.setToolTipText(Local.getString("Edit My Enrolled Class"));
        editEnrollClassEventB.setMinimumSize(new Dimension(130, 24));
        editEnrollClassEventB.setMaximumSize(new Dimension(130, 24));
        editEnrollClassEventB.setEnabled(true);
        editEnrollClassEventB.setFont(
                new Font("Arial", Font.PLAIN, 10));





        room1ScrollPane.getViewport().setBackground(Color.white);
        room2ScrollPane.getViewport().setBackground(Color.gray);
        room3ScrollPane.getViewport().setBackground(Color.lightGray);
        room4ScrollPane.getViewport().setBackground(Color.darkGray);
        int horizontalPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
        int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
        room1ScrollPane.getHorizontalScrollBar().setValue(horizontalPolicy);
        room1ScrollPane.getVerticalScrollBar().setValue(verticalPolicy);
        room2ScrollPane.getHorizontalScrollBar().setValue(horizontalPolicy);
        room2ScrollPane.getVerticalScrollBar().setValue(verticalPolicy);
        room3ScrollPane.getHorizontalScrollBar().setValue(horizontalPolicy);
        room3ScrollPane.getVerticalScrollBar().setValue(verticalPolicy);
        room4ScrollPane.getHorizontalScrollBar().setValue(horizontalPolicy);
        room4ScrollPane.getVerticalScrollBar().setValue(verticalPolicy);


        //scrollPane2.getViewport().setBackground(Color.gray);
        // KJPETRON: THIS IS THE KEY! THIS CONTROLS THE EVENTS DISPLAY
        Room1.setMaximumSize(new Dimension(200, 800));
        Room1.setRowHeight(24);
        Room1.setPreferredScrollableViewportSize(new Dimension(300, 600));
        Room2.setMaximumSize(new Dimension(200, 800));
        Room2.setRowHeight(24);
        Room2.setPreferredScrollableViewportSize(new Dimension(300, 600));
        Room3.setMaximumSize(new Dimension(200, 800));
        Room3.setRowHeight(24);
        Room3.setPreferredScrollableViewportSize(new Dimension(300, 600));
        Room4.setMaximumSize(new Dimension(200, 800));
        Room4.setRowHeight(24);
        Room4.setPreferredScrollableViewportSize(new Dimension(300, 600));

        eventPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppEditEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditEvent.setText(Local.getString("Edit event") + "...");
        ppEditEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditEvent_actionPerformed(e);
            }
        });
        ppEditEvent.setEnabled(false);
        ppEditEvent.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));


        ppRemoveEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveEvent.setText(Local.getString("Remove event"));
        ppRemoveEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveEvent_actionPerformed(e);
            }
        });
        ppRemoveEvent.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
        ppRemoveEvent.setEnabled(false);





        ppNewEvent.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewEvent.setText(Local.getString("New event") + "...");
        ppNewEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewEvent_actionPerformed(e);
            }
        });
        ppNewEvent.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));

        eventsToolBar.add(historyBackB, null);
        eventsToolBar.add(historyForwardB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(newEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(privateClassEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(editEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(removeEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(setEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(enrollClassEventB, null);
        eventsToolBar.addSeparator(new Dimension(2, 24));
        eventsToolBar.add(editEnrollClassEventB, null);




        this.add(eventsToolBar, BorderLayout.NORTH);
        room1ScrollPane.getViewport().add(Room1, null);
        room2ScrollPane.getViewport().add(Room2, null);
        room3ScrollPane.getViewport().add(Room3, null);
        room4ScrollPane.getViewport().add(Room4, null);
        JPanel roomPanel = new JPanel();
        roomPanel.add(room1ScrollPane, BorderLayout.SOUTH);
        roomPanel.add(room2ScrollPane, BorderLayout.SOUTH);
        roomPanel.add(room3ScrollPane, BorderLayout.SOUTH);
        roomPanel.add(room4ScrollPane, BorderLayout.SOUTH);
        this.add(roomPanel, BorderLayout.SOUTH);


        eventsToolBar.addSeparator(new Dimension(150, 0));

        PopupListener ppListener = new PopupListener();
        room1ScrollPane.addMouseListener(ppListener);
        Room1.addMouseListener(ppListener);
        Room2.addMouseListener(ppListener);
        Room3.addMouseListener(ppListener);
        Room4.addMouseListener(ppListener);

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {

                Room1.initTable(d);
                boolean enbl = d.after(CalendarDate.today()) || d.equals(CalendarDate.today());
                newEventB.setEnabled(enbl);
                ppNewEvent.setEnabled(enbl);

                editEventB.setEnabled(false);
                ppEditEvent.setEnabled(false);
                removeEventB.setEnabled(false);
                ppRemoveEvent.setEnabled(false);
                // enrollClassEventB.setEnabled(false);
                //editEnrollClassEventB.setEnabled(false);
            }
        });

        Room1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = Room1.getSelectedRow() > -1;
                editEventB.setEnabled(enbl);
                ppEditEvent.setEnabled(enbl);
                removeEventB.setEnabled(enbl);
                ppRemoveEvent.setEnabled(enbl);

            }
        });
        editEventB.setEnabled(false);
        removeEventB.setEnabled(false);

        privateClassEventB.setEnabled(true);
        eventPPMenu.add(ppEditEvent);
        eventPPMenu.addSeparator();
        eventPPMenu.add(ppNewEvent);
        eventPPMenu.add(ppRemoveEvent);


        // remove events using the DEL key
        Room1.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (Room1.getSelectedRows().length > 0
                        && e.getKeyCode() == KeyEvent.VK_DELETE)
                    ppRemoveEvent_actionPerformed(null);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
    }

    /**
     * Edit event b action performed.
     *
     * @param e the e
     */
    void editEventB_actionPerformed(ActionEvent e) {
        EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Edit Existing Class"));
        main.java.memoranda.Event ev =
                (main.java.memoranda.Event) Room1.getModel().getValueAt(
                        Room1.getSelectedRow(),
                        EventsTable.EVENT);

        dlg.timeSpin.getModel().setValue(ev.getTime());
        /*if (new CalendarDate(ev.getTime()).equals(CalendarDate.today())) 
            ((SpinnerDateModel)dlg.timeSpin.getModel()).setStart(new Date());
        else
        ((SpinnerDateModel)dlg.timeSpin.getModel()).setStart(CalendarDate.today().getDate());
        ((SpinnerDateModel)dlg.timeSpin.getModel()).setEnd(CalendarDate.tomorrow().getDate());*/
        dlg.textField.setText(ev.getText());
        int rep = ev.getRepeat();
        if (rep > 0) {
            dlg.startDate.getModel().setValue(ev.getStartDate().getDate());
            if (rep == EventsManager.REPEAT_DAILY) {
                dlg.dailyRepeatRB.setSelected(true);
                dlg.dailyRepeatRB_actionPerformed(null);
                dlg.daySpin.setValue(new Integer(ev.getPeriod()));
            } else if (rep == EventsManager.REPEAT_WEEKLY) {
                dlg.weeklyRepeatRB.setSelected(true);
                dlg.weeklyRepeatRB_actionPerformed(null);
                int d = ev.getPeriod() - 1;
                if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
                    d--;
                    if (d < 0) d = 6;
                }
                dlg.weekdaysCB.setSelectedIndex(d);
            } else if (rep == EventsManager.REPEAT_MONTHLY) {
                dlg.monthlyRepeatRB.setSelected(true);
                dlg.monthlyRepeatRB_actionPerformed(null);
                dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
            } else if (rep == EventsManager.REPEAT_YEARLY) {
                dlg.yearlyRepeatRB.setSelected(true);
                dlg.yearlyRepeatRB_actionPerformed(null);
                dlg.dayOfMonthSpin.setValue(new Integer(ev.getPeriod()));
            }
            if (ev.getEndDate() != null) {
                dlg.endDate.getModel().setValue(ev.getEndDate().getDate());
                dlg.enableEndDateCB.setSelected(true);
                dlg.enableEndDateCB_actionPerformed(null);
            }
            if (ev.getWorkingDays()) {
                dlg.workingDaysOnlyCB.setSelected(true);
            }

        }

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        EventsManager.removeEvent(ev);

        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        calendar.setTime(((Date) dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM

        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg.textField.getText();
        if (dlg.noRepeatRB.isSelected())
            EventsManager.createEvent(CurrentDate.get(), hh, mm, text);
        else {
            updateEvents(dlg, hh, mm, text);
        }
        saveEvents();
    }

    /**
     * New private class b action performed.
     *
     * @param e the e
     */

    void privateClassEventB_actionPerformed(ActionEvent e) {
        Calendar caldate = CurrentDate.get().getCalendar();
        // round down to hour
        caldate.set(Calendar.MINUTE, 0);
        Util.debug("Default time is " + caldate);

        privateClassEventB_actionPerformed(e, null, caldate.getTime(), caldate.getTime());

    }

    /**
     * New private class b action performed.
     *
     * @param e         the e
     * @param tasktext  the tasktext
     * @param startDate the start date
     * @param endDate   the end date
     */
    void privateClassEventB_actionPerformed(ActionEvent e, String tasktext, Date startDate, Date endDate) {
        EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Schedule New Private Class"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        if (tasktext != null) {
            dlg.textField.setText(tasktext);
        }

        dlg.startDate.getModel().setValue(startDate);
        dlg.endDate.getModel().setValue(endDate);
        dlg.timeSpin.getModel().setValue(startDate);

        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setEventDate(startDate);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        calendar.setTime(((Date) dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM

        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg.textField.getText();

        CalendarDate eventCalendarDate = new CalendarDate(dlg.getEventDate());

        if (dlg.noRepeatRB.isSelected())
            EventsManager.createEvent(eventCalendarDate, hh, mm, text);
        else {
            updateEvents(dlg, hh, mm, text);
        }
        saveEvents();
    }

    /**
     * New event b action performed.
     *
     * @param e the e
     */

    void newEventB_actionPerformed(ActionEvent e) {
        Calendar cdate = CurrentDate.get().getCalendar();
        // round down to hour
        cdate.set(Calendar.MINUTE, 0);
        Util.debug("Default time is " + cdate);

        newEventB_actionPerformed(e, null, cdate.getTime(), cdate.getTime());
    }
    /**
     * Set Availability event b action performed.
     *
     * @param e the e
     */

    void setEventB_actionPerformed(ActionEvent e) {
        Calendar cdate2 = CurrentDate.get().getCalendar();
        // round down to hour
        cdate2.set(Calendar.MINUTE, 0);
        Util.debug("Default time is " + cdate2);

        setEventB_actionPerformed(e, null, cdate2.getTime(), cdate2.getTime());
    }

    /**
     * New event b action performed.
     *
     * @param e         the e
     * @param tasktext  the tasktext
     * @param startDate the start date
     * @param endDate   the end date
     */
    void newEventB_actionPerformed(ActionEvent e, String tasktext, Date startDate, Date endDate) {
        EventDialog dlg = new EventDialog(App.getFrame(), Local.getString("Schedule New Public Class"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        if (tasktext != null) {
            dlg.textField.setText(tasktext);
        }
        dlg.startDate.getModel().setValue(startDate);
        dlg.endDate.getModel().setValue(endDate);
        dlg.timeSpin.getModel().setValue(startDate);

        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setEventDate(startDate);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        calendar.setTime(((Date) dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM

        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg.textField.getText();

        CalendarDate eventCalendarDate = new CalendarDate(dlg.getEventDate());

        if (dlg.noRepeatRB.isSelected())
            EventsManager.createEvent(eventCalendarDate, hh, mm, text);
        else {
            updateEvents(dlg, hh, mm, text);
        }
        saveEvents();
    }

    private void saveEvents() {
        CurrentStorage.get().storeEventsManager();
        Room1.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
    }

    private void updateEvents(EventDialog dlg, int hh, int mm, String text) {
        int rtype;
        int period;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate ed = null;
        if (dlg.enableEndDateCB.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        if (dlg.dailyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_DAILY;
            period = ((Integer) dlg.daySpin.getModel().getValue()).intValue();
        } else if (dlg.weeklyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_WEEKLY;
            period = dlg.weekdaysCB.getSelectedIndex() + 1;
            if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
                if (period == 7) period = 1;
                else period++;
            }
        } else if (dlg.yearlyRepeatRB.isSelected()) {
            rtype = EventsManager.REPEAT_YEARLY;
            period = sd.getCalendar().get(Calendar.DAY_OF_YEAR);
            if ((sd.getYear() % 4) == 0 && sd.getCalendar().get(Calendar.DAY_OF_YEAR) > 60) period--;
        } else {
            rtype = EventsManager.REPEAT_MONTHLY;
            period = ((Integer) dlg.dayOfMonthSpin.getModel().getValue()).intValue();
        }
    }

    /**
     * edit enrolment  b action performed.
     *
     * @param e the e
     */

    void editEnrollClassEventB_actionPerformed(ActionEvent e) {
        {
            String msg;
            main.java.memoranda.Event ev;

            if (Room1.getSelectedRows().length > 1)
                msg = Local.getString("Cancel") + " " + Room1.getSelectedRows().length
                        + " " + Local.getString("enrolment") + "\n" + Local.getString("Are you sure you want"
                        + " to cancel your enrolment to this class?");
            else {
                ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                        Room1.getSelectedRow(),
                        EventsTable.EVENT);
                msg = Local.getString("Cancel enrolment") + "\n"
                        + ev.getText() + "\n" + Local.getString("Are you sure you want to"
                        + " to cancel your enrolment to this class?");
            }

            int n =
                    JOptionPane.showConfirmDialog(
                            App.getFrame(),
                            msg,
                            Local.getString("Enrolment cancelation"),
                            JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) return;

            for (int i = 0; i < Room1.getSelectedRows().length; i++) {
                ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                        Room1.getSelectedRows()[i], EventsTable.EVENT);
                EventsManager.removeEvent(ev);
            }
            Room1.getSelectionModel().clearSelection();
            /*
             */
            saveEvents();
        }
    }

    void enrollClassEventB_actionPerformed(ActionEvent e)

    {
        String msg;
        main.java.memoranda.Event ev;

        if (Room1.getSelectedRows().length > 1)
            msg = Local.getString("Confirm") + " " + Room1.getSelectedRows().length
                    + " " + Local.getString("Enrolment") + "\n" + Local.getString("Are you sure you want"
                    + " to enroll to this class?");
        else {
            ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                    Room1.getSelectedRow(),
                    EventsTable.EVENT);
            msg = Local.getString("Class Enrolment") + "\n"
                    + ev.getText() + "\n" + Local.getString("Are you sure you want to enroll to this class?");
        }

           int n =
                  JOptionPane.showConfirmDialog(
                          App.getFrame(),
                           msg,
                          Local.getString("Enroll in Class"),
                          JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) return;

            for(int i = 0; i< Room1.getSelectedRows().length; i++) {
                ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                        Room1.getSelectedRows()[i], EventsTable.EVENT);
                EventsManager.removeEvent(ev);
            }
     // eventsTable.getSelectionModel().clearSelection();

    //  saveEvents();
    }



    /**
     * Remove event b action performed.
     *
     * @param e the e
     */
    void removeEventB_actionPerformed(ActionEvent e) {
		String msg;
		main.java.memoranda.Event ev;

		if(Room1.getSelectedRows().length > 1)
			msg = Local.getString("Delete") + " " + Room1.getSelectedRows().length
				+ " " + Local.getString("class") + "\n" + Local.getString("Are you sure you want"
            +" to delete this class?");
		else {
			ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                Room1.getSelectedRow(),
                EventsTable.EVENT);
			msg = Local.getString("Delete Class") + "\n"
				+ ev.getText() + "\n" + Local.getString("Are you sure you want to delete this class?");
		}

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove Class"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) return;

        for(int i = 0; i< Room1.getSelectedRows().length; i++) {
			ev = (main.java.memoranda.Event) Room1.getModel().getValueAt(
                  Room1.getSelectedRows()[i], EventsTable.EVENT);
        EventsManager.removeEvent(ev);
		}
        Room1.getSelectionModel().clearSelection();
/*        CurrentStorage.get().storeEventsManager();
        eventsTable.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
*/ saveEvents();  
  }

    /**
     * Set availability event b action performed.
     *
     * @param e the e
     */
    void setEventB_actionPerformed(ActionEvent e, String tasktext, Date startDate, Date endDate) {
        EventDialogAvalability dlg1 = new EventDialogAvalability(App.getFrame(), Local.getString("Set Your Availability"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        if (tasktext != null) {
            dlg1.textField.setText(tasktext);
        }
        dlg1.startDate.getModel().setValue(startDate);
        dlg1.endDate.getModel().setValue(endDate);
        dlg1.timeSpin.getModel().setValue(startDate);

        dlg1.setLocation((frmSize.width - dlg1.getSize().width) / 2 + loc.x, (frmSize.height - dlg1.getSize().height) / 2 + loc.y);
        dlg1.setEventDate(startDate);
        dlg1.setVisible(true);
        if (dlg1.CANCELLED)
            return;
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        calendar.setTime(((Date) dlg1.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
        //by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM

        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg1.textField.getText();

        CalendarDate eventCalendarDate = new CalendarDate(dlg1.getEventDate());

    //    if (dlg1.noRepeatRB.isSelected())
      ///      EventsManager.createEvent(eventCalendarDate, hh, mm, text);
       // else {
       //     updateEvents(dlg1, hh, mm, text);
      //  }
       // saveEvents();
    }

    /**
     * The type Popup listener.
     */
    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (Room1.getSelectedRow() > -1))
                editEventB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                eventPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }

    /**
     * Pp edit event action performed.
     *
     * @param e the e
     */
    void ppEditEvent_actionPerformed(ActionEvent e) {
        editEventB_actionPerformed(e);

    }
    void ppPrivateClassEvent_actionPerformed(ActionEvent e) {
        editEventB_actionPerformed(e);

    }

    /**
     * Pp remove event action performed.
     *
     * @param e the e
     */
    void ppRemoveEvent_actionPerformed(ActionEvent e) {
        removeEventB_actionPerformed(e);


    }

    /**
     * Pp new event action performed.
     *
     * @param e the e
     */
    void ppNewEvent_actionPerformed(ActionEvent e) {
        newEventB_actionPerformed(e);

    }
}
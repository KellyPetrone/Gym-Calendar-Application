package main.java.memoranda.ui.classes;

import main.java.memoranda.database.entities.BeltEntity;
import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.ui.App;
import main.java.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class to create pop up window to create a new private class.
 */
public class ClassesSchedPrivClass extends JFrame {

    ClassesPanel topLevelReference;
    LocalDate date;
    JPanel classCreate;
    JButton createButton;
    JComboBox times;
    JLabel lblTimes;
    JComboBox beltsCB;
    JLabel lblBelt;
    JComboBox trainersCB;
    JLabel lblTrainers;
    JLabel currentDate;
    JComboBox roomCB;
    JLabel lblRooms;
    JLabel fillOutForm;

    /** Main constructor
     *
     * @param ref This parent reference is passed in so we can call refresh on it once the  UI is updated.
     * @param rel Used to set the position of the add user popup.
     */
    public ClassesSchedPrivClass(ClassesPanel ref, Component rel, LocalDate currentDate) {
        this.topLevelReference = ref;//we store this to use in our action listener.
        date = currentDate;

        initGuiComponents();

        initActionEvents();
        getContentPane().add(classCreate);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    /**
     * Initializes GUI components in one call.
     */
    public void initGuiComponents(){
        classCreate = new JPanel();
        //Label
        setTitle("Schedule New Class");
        setSize(300, 230);
        //Top Label
        fillOutForm = new JLabel("Please fill out the form with private class info");
        fillOutForm.setBounds(15, -10, 275, 50);
        fillOutForm.setFont(new Font("Bell MT", Font.PLAIN, 12));
        //Current Date Label
        currentDate = new JLabel(date.toString());
        currentDate.setBounds(100, 10, 200, 50);
        currentDate.setFont(new Font("Bell MT", Font.ITALIC, 12));
        //Trainers CB and Label
        trainersCB = new JComboBox(Local.getTrainerNames());
        lblTrainers = new JLabel("Select Trainer:");
        trainersCB.setBounds(100, 50, 200, 20);
        lblTrainers.setBounds(5, 50, 100, 25);
        //Times CB and Label
        times = new JComboBox(Local.getTimes());
        lblTimes = new JLabel("Select Start Time:");
        times.setBounds(150, 75, 70, 20);
        lblTimes.setBounds(25, 75, 125, 25);
        //Belts CB and Label
        beltsCB = new JComboBox(Local.getBeltNames());
        lblBelt = new JLabel("Select Belt Level:");
        beltsCB.setBounds(150, 100, 115, 20);
        lblBelt.setBounds(15, 100, 125, 20);
        //Select Room
        roomCB = new JComboBox(Local.getRoomNames());
        lblRooms = new JLabel("Select Room Number:");
        roomCB.setBounds(155, 125, 100, 20);
        lblRooms.setBounds(15, 125, 135, 20);
        //Create Button
        createButton = new JButton("Create");
        createButton.setBounds(100, 165, 80, 20);


        classCreate.setLayout(null);

        classCreate.add(createButton);
        classCreate.add(lblTrainers);
        classCreate.add(lblBelt);
        classCreate.add(currentDate);
        classCreate.add(roomCB);
        classCreate.add(lblRooms);
        classCreate.add(fillOutForm);
        classCreate.add(beltsCB);
        classCreate.add(trainersCB);
        classCreate.add(times);
        classCreate.add(lblTimes);
        classCreate.setBackground(new Color(230, 230, 230));
    }

    /**
     * Initializes our action events in one call.
     */
    public void initActionEvents(){
        /**
         * Manages create button entry box
         */


        createButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    createClass();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Allow an enter key press on the Create button to launch program
        createButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        createClass();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Throws JOptionPane window on error.
     * @param error Message to display to the user
     */
    public void throwInputError (String error) {
        final JFrame parent = new JFrame();
        JOptionPane.showMessageDialog(parent, error);
    }

    /**
     * Call to create account. Tests fields on Account Creation window for data.
     * @throws SQLException Throws exception is account does not exist.
     */
    public void createClass () throws SQLException {
        String trainerBelt = extractBelt();
        BeltEntity beltEntity = new BeltEntity("white");
        if (!beltEntity.checkBeltRank(trainerBelt, beltsCB.getSelectedItem().toString())){
            throwInputError("This trainer is not a high enough belt level to host this class");
            return;
        }
        double startTime = Local.getDoubleTime(times.getSelectedItem().toString());
        int room = extractRoom();
        int maxSize = 2;
        BeltEntity belt = new BeltEntity(beltsCB.getSelectedItem().toString());
        ArrayList<GymClassEntity> gce = App.conn.getDrq().getAllClassesByDateTime(date, Local.getDoubleTime(times.getSelectedItem().toString())
                , room);
        if (gce.size() != 0) {
            throwInputError("There is already a class in this room, at this time, on this date");
            return;
        }
        System.out.println(date.toString());
        try {
            App.conn.getDcq().insertClass(room, date, startTime, startTime+1, extractTrainerEmail()
                    , maxSize, belt, extractTrainerEmail());
            topLevelReference.refresh();
            showCreatedSuccessfullyPopup();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

    }

    /**
     * Popup window that tells the user the account was created successfully
     */
    public void showCreatedSuccessfullyPopup() {
        Object[] option = {"OK"};
        int x = JOptionPane.showOptionDialog(null, "Class was created successfully!",
                "Class Creation", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
    }

    /**
     * Extracts the belt from the selected trainer
     * @return String belt
     */
    public String extractBelt() {
        String trainer = trainersCB.getSelectedItem().toString();
        String belt = "";
        int j;
        for (int i = 0; i < trainer.length(); i++) {
            if (trainer.charAt(i) == ':') {
                belt = trainer.substring(i+2, trainer.length());
            }
        }
        return belt;
    }

    /**
     * Extracts trainer email from combo box.
     * @return String of email
     */
    public String extractTrainerEmail() {
        String trainer = trainersCB.getSelectedItem().toString();
        String email = "";
        int j;
        for (int i = 0; i < trainer.length(); i++) {
            if (trainer.charAt(i) == ' ') {
                email = trainer.substring(0, i);
                i = trainer.length();
            }
        }
        return email;
    }

    /**
     * Extracts room number as int for query.
     * @return int of room
     */
    public int extractRoom() {
        String room = roomCB.getSelectedItem().toString();
        String substr = room.substring(5, 6);
        int i = Integer.parseInt(substr);
        return i;
    }
}


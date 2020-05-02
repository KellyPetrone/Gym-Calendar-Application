package main.java.memoranda.ui.classes;

import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.gym.Gym;
import main.java.memoranda.gym.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClassesEnrollClass {
    private static final String enrollDialogGifLocation = "src/main/resources/ui/liftWeight.gif";
    ClassesPanel topLevelReference;
    LocalDate date;
    GymClassEntity selectedClass;
    Gym gym;
    JDialog enrollDialog;
    JLabel infoText;
    boolean shouldContinue;

    public ClassesEnrollClass(ClassesPanel topLevelReference, LocalDate date, GymClassEntity selectedClass) {
        if (selectedClass == null) {
            throwInputError("You have not selected a class from the Classes Pane");
            return;
        }

        this.topLevelReference = topLevelReference;
        this.date = date;
        this.selectedClass = selectedClass;
        this.shouldContinue = true;
        this.gym = Gym.getInstance();
        createGuiElements();

        scheduleUpdatesToGui();
        enrollDialog.setVisible(true);
    }

    private void scheduleUpdatesToGui() {
        scheduleDelayedTextUpdate("Checking your belt rank is sufficient...", 0, false);
        scheduleDelayedStudentBeltRankIsSufficientCheck(0);
        scheduleDelayedTextUpdate("Checking class availability...", 2000, false);
        scheduleDelayedClassSizeCheck(2000);
        scheduleDelayedTextUpdate("Enrolling in class...", 4000, false);
        scheduleDelayedEnrollUser(6000);
        scheduleDelayedTextUpdate("", 6000, true);
    }


    public void createGuiElements() {
        //setup gif
        Icon icon = new ImageIcon(enrollDialogGifLocation);
        JLabel gif = new JLabel(icon);
        gif.setAlignmentX(Component.CENTER_ALIGNMENT);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(topLevelReference);

        //setup enroll dialog (which contains gif and text)
        enrollDialog = new JDialog(topFrame, true);
        enrollDialog.setLayout(new BoxLayout(enrollDialog.getContentPane(), BoxLayout.Y_AXIS));
        enrollDialog.setUndecorated(true);
        enrollDialog.add(gif);

        //add some space
        enrollDialog.add(Box.createRigidArea(new Dimension(0, 30)));

        //add text (to start its empty so this is a placeholder)
        infoText = new JLabel("");
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        enrollDialog.add(infoText);

        //finish configuration of dialog
        enrollDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enrollDialog.pack();
        enrollDialog.setLocationRelativeTo(topFrame);
        enrollDialog.setSize(250, 200);
    }

    private void scheduleDelayedClassSizeCheck(int delay) {
        ActionListener listener = event -> {
            if (!shouldContinue) {
                if (enrollDialog.isVisible()) {
                    enrollDialog.setVisible(false);
                }
                return;
            }
            ClassHelper classHelper = new ClassHelper();
            try {
                if (!classHelper.classIsNotFull(selectedClass)) {
                    infoText.setText("Class is full!");
                    shouldContinue = false;
                }
            } catch (SQLException e) {
                infoText.setText("Failed to retrieve class information to determine if it's full!");
                shouldContinue = false;
            }
        };
        Timer t = new Timer(delay, listener);
        t.setRepeats(false);
        t.start();

    }

    private void scheduleDelayedStudentBeltRankIsSufficientCheck(int delay) {
        ActionListener listener = event -> {
            if (!shouldContinue) {
                if (enrollDialog.isVisible()) {
                    enrollDialog.setVisible(false);
                }
                return;
            }
            ClassHelper classHelper = new ClassHelper();

            if (!classHelper.userHasSufficientTrainingToEnroll(selectedClass, gym.getUser())) {
                infoText.setText("You dont have sufficient belt rank training to enroll in this class!");
                shouldContinue = false;
            }
        };
        Timer t = new Timer(delay, listener);
        t.setRepeats(false);
        t.start();
    }

    private void scheduleDelayedEnrollUser(int delay) {
        ActionListener listener = event -> {
            if (!shouldContinue) {
                if (enrollDialog.isVisible()) {
                    enrollDialog.setVisible(false);
                }
                return;
            }
            Response response = gym.enrollUser(selectedClass.getId(), gym.getUser().getEmail());
            if (response.isFailure()) {
                infoText.setText(response.getMsg());
                shouldContinue = false;
            } else {
                infoText.setText("User Enrolled");
            }
        };
        Timer t = new Timer(delay, listener);
        t.setRepeats(false);
        t.start();

    }

    private void scheduleDelayedTextUpdate(String text, int delay, boolean isFinal) {
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                if (isFinal) {
                    enrollDialog.dispose();
                } else if (shouldContinue) {
                    infoText.setText(text);
                }
            }
        };

        Timer t = new Timer(delay, listener);
        t.setRepeats(false);
        t.start();
    }

    /**
     * Throws JOptionPane window on error.
     * @param error Message to display to the user
     */
    public void throwInputError (String error) {
        final JFrame parent = new JFrame();
        JOptionPane.showMessageDialog(parent, error);
    }
}

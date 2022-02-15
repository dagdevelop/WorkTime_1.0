package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.Details;
import com.dagdevelop.workTime.model.Duration;
import com.dagdevelop.workTime.model.Info;
import com.dagdevelop.workTime.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class ChronoPanel extends JPanel {
    private int second = 0;
    private int minute = 0;
    private int hour = 0;
    private int day = 0;
    private Date dateDebut;

    private JTextField chronoField;
    private JButton pause, stop, go;
    private Timer timer;
    private Activity activity;
    private MainFrame mainFrame;
    private ActivityDataAccess activityDataAccess;
    private ArrayList<Activity> activities;
    private ImageIcon serverICO;


    public ChronoPanel(int day, int hour, int minute, int second, Activity activity, MainFrame mainFrame){
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.activity = activity;
        this.mainFrame = mainFrame;


        activityDataAccess = new ActivityDAO();
        if (activities == null) {
            try {
                activities = activityDataAccess.getAllActivities();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        setLayout(new BorderLayout());
        timer = createTimer (1000);
        timer.setRepeats(true);

        chronoField = new JTextField(getTime());
        chronoField.setFont(Util.fontTitle(30));
        this.setBackground(Color.orange);
        chronoField.setEditable(false);
        chronoField.setPreferredSize(new Dimension(400, 70));
        chronoField.setBorder(BorderFactory.createCompoundBorder());

        ButtonPanel buttonPanel = new ButtonPanel();

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(chronoField, BorderLayout.CENTER);

    }
    public ChronoPanel(Activity activity, MainFrame parent){
        this(0, 0, 0, 0, activity, parent);
    }

    private class ButtonPanel extends JPanel{
        private ImageIcon startICO, pauseICO, stopICO;

        public ButtonPanel(){
            setLayout(new FlowLayout());

            this.setBackground(Color.orange);

            Image scaleIcon;
            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"start.png").getImage();
            startICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"pause.png").getImage();
            pauseICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"stop.png").getImage();
            stopICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"server-error.png").getImage();
            serverICO = new ImageIcon(scaleIcon.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH));

            go = new JButton(startICO);
            go.setBackground(Color.GREEN);
            go.addMouseListener(new StartListener(go));
            go.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pause = new JButton(pauseICO);
            pause.setBackground(Color.ORANGE);
            pause.addMouseListener(new PauseListener(pause));
            pause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            stop = new JButton(stopICO);
            stop.setBackground(Color.RED);
            stop.addMouseListener(new StopListener(stop));
            stop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            this.add(go);
            this.add(pause);
            this.add(stop);
        }

        @AllArgsConstructor
        private class StartListener extends MouseAdapter {
            private JButton btn;

            @Override
            public void mouseClicked(MouseEvent e) {
                timer.start();
                dateDebut = new Date();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 77, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.green);
            }
        }
        @AllArgsConstructor
        private class PauseListener extends MouseAdapter{
            private JButton btn;
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(255, 100, 26));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.ORANGE);
            }
        }
        @AllArgsConstructor
        private class StopListener extends MouseAdapter{
            private JButton btn;
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                if (dateDebut != null){
                    int res = JOptionPane.showConfirmDialog(ChronoPanel.this, "Enregister le temps et terminer ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (res == JOptionPane.YES_OPTION){
                        Duration duration = new Duration(day, hour, minute, second);
                        Info info = new Info(dateDebut, duration);
                        Details detail = new Details(day, hour, minute, second);

                        activity.getMetaData().addInfo(info);
                        activity.addNbFois();
                        System.out.println("chrono panel nbFoisTotal : "+ activity.getNbFois());

                        int dayIndex = dateDebut.getDay() - 1 < 0 ? 7 : dateDebut.getDay();

                        activity.getMetaData().addStatisticDay(Util.days[dayIndex - 1], detail);
                        activity.getMetaData().addStatisticMonth(Util.months [dateDebut.getMonth()], detail);
                        activities.removeIf(x -> x.getLabel().equals(activity.getLabel()));
                        activities.add(activity);

                        try {
                            if (activityDataAccess.exists(activity.getLabel())){
                                activityDataAccess.update(activities);
                                Redirect.to(mainFrame, new Dashbord(mainFrame, new Home(mainFrame)));
                            }else{
                                JOptionPane.showMessageDialog(mainFrame, "The activity is not present in the DataBase !", "Not found", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "can not update activity file !", "Server error", JOptionPane.ERROR_MESSAGE);
                        } catch (ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Bad object classes ! please contact dagdevelopper@gmail.com !", "Server error", JOptionPane.ERROR_MESSAGE, serverICO);
                        }
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(162, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.RED);
            }
        }
    }

    public void setTime(){
        if (second >= 59){
            second = 0; minute++;
            if (minute >= 59){
                minute = 0; hour++;
                if (hour >= 23){
                    hour = 0; day++;
                }
            }
        }else
            second++;
    }
    public String getTime(){
        return "\t"+ day + " day"+ (day > 1 ? "s" : "") +" - " + hour + " hrs - " + minute + " min - " + second + " s ";
    }

    private Timer createTimer (int step){
        ActionListener action = new ActionListener (){
            public void actionPerformed (ActionEvent event){
                setTime();
                chronoField.setText(getTime());
            }
        };
        return new Timer (step, action);
    }


}

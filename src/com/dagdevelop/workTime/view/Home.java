package com.dagdevelop.workTime.view;


import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.OAuth;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Home extends PanelWithFondImage {
    private MainFrame parent;
    private JLabel welcomeMsg;
    private JTextField enterActivity;
    private JButton createBtn;
    private ActivityDataAccess activityDAO;
    private ImageIcon serverICO;
    public ArrayList<Activity> activities;


    public Home (MainFrame parent){

        super(Util.PATH_IMAGE +"logoFond.jpg");
        this.parent = parent;
        activities = new ArrayList<>();
        activityDAO = new ActivityDAO();


        if (activities.isEmpty()) {
            try {
                activities = activityDAO.getAllActivities();
                if (activities == null)
                    activities = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Image scaleIcon = new ImageIcon(Util.PATH_IMAGE +"server-error.png").getImage();
        serverICO = new ImageIcon(scaleIcon.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH));

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        gridBagConstraints.insets = new Insets(30,100,100,0);
        gridBagConstraints.ipady = gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        enterActivity = new JTextField();
        enterActivity.setFont(Util.fontTitle(18));
        enterActivity.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        enterActivity.setToolTipText("Titre de l'activité");
        enterActivity.setForeground(Color.BLACK);
        enterActivity.setBackground(new Color(222, 222, 222));
        enterActivity.setPreferredSize(new Dimension(470, 20));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"add.png").getImage();
        ImageIcon addIco = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

        createBtn = new JButton(addIco);
        createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createBtn.setFont(Util.fontTitle(10));
        createBtn.setToolTipText("Créer");
        createBtn.setBackground(Color.orange);
        createBtn.setPreferredSize(new Dimension(60, 20));
        createBtn.addMouseListener(new ButtonCreateListener(createBtn));

        welcomeMsg = new JLabel("Bienvenue "+ OAuth.getData().getFistname());
        welcomeMsg.setFont(Util.fontTitle(50));
        this.add(welcomeMsg, gridBagConstraints);
        gridBagConstraints.gridy++;

        this.add(enterActivity, gridBagConstraints);
        gridBagConstraints.gridx++;
        this.add(createBtn, gridBagConstraints);
        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
    }

    private class ButtonCreateListener extends MouseAdapter {
        private JButton btn;

        public ButtonCreateListener(JButton btn){
            this.btn = btn;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String title = enterActivity.getText();

            if (!title.isEmpty()){
                try {
                    if (!activityDAO.exists(title)){

                        Activity activity = new Activity(title);
                        activities.add(activity);
                        activities = activityDAO.save(activities);
                        Redirect.to(parent, new Dashbord(parent, new Home(parent)));

                    }else
                        JOptionPane.showMessageDialog(Home.this, "L'activité existe déja !", "Data found", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Home.this, "File activity not found !", "Server error", JOptionPane.PLAIN_MESSAGE, serverICO);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(parent, "Bad object classes ! please contact dagdevelopper@gmail.com !", "Server error", JOptionPane.ERROR_MESSAGE, serverICO);
                    ex.printStackTrace();
                }
            }else
                JOptionPane.showMessageDialog(Home.this, "le champ de doit pas être vide et ne doit contenir que des lettres", "Bad entry", JOptionPane.WARNING_MESSAGE);
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
}

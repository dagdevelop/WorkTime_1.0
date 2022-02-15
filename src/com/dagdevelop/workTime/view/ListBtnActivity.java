package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.util.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class ListBtnActivity extends JPanel {
    private JButton activityBtn, deleteAll;
    private ActivityDataAccess activityDataAccess;
    private JLabel title, rienAAfficher;
    private MainFrame parent;
    private ArrayList<Activity> actitvities;
    private ImageIcon serveriCO;
    private Image scaleIcon;




    public ListBtnActivity(MainFrame parent){
        this.parent = parent;

        activityDataAccess = new ActivityDAO();
        actitvities = new ArrayList<>();

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"server-error.png").getImage();
        serveriCO = new ImageIcon(scaleIcon.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH));

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        gridBagConstraints.insets = new Insets(10,10,5,20);
        gridBagConstraints.ipady = gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 3;
        gridBagConstraints.weighty = 3;

        this.setBackground(new Color(223, 171, 23));

        title = new JLabel("Mes Activités :");
        title.setFont(Util.fontTitle(30));
        this.add(title, gridBagConstraints);
        gridBagConstraints.gridy++;

        rienAAfficher = new JLabel("Aucune activité enregistrée !");
        rienAAfficher.setFont(Util.fontTitle(20));
        rienAAfficher.setForeground(Color.GRAY);

        try {
            actitvities = activityDataAccess.getAllActivities();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File activity not found !", "Server error", JOptionPane.ERROR_MESSAGE, serveriCO);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Bad object classes ! please contact dagdevelopper@gmail.com !", "Server error", JOptionPane.ERROR_MESSAGE, serveriCO);
        }

        if (actitvities != null && !actitvities.isEmpty()){
            for (var activity : actitvities){
                activityBtn = new JButton(activity.getLabel());
                activityBtn.addMouseListener(new MouseEvent(activity, activityBtn));
                activityBtn.setPreferredSize(new Dimension(300, 30));
                activityBtn.setForeground(Color.BLACK);
                activityBtn.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.BLACK));
                activityBtn.setFont(Util.fontTitle(15));
                activityBtn.setBackground(new Color(254, 128, 1));
                activityBtn.setFocusable(true);
                activityBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                activityBtn.setToolTipText(activity.getLabel());
                this.add(activityBtn, gridBagConstraints);

                gridBagConstraints.gridy++;
            }

            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"deleteRed.png").getImage();
            ImageIcon deleteIcon = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

            deleteAll = new JButton("Vider la liste", deleteIcon);
            deleteAll.setForeground(Color.WHITE);
            deleteAll.setBackground(Color.BLACK);
            deleteAll.setFont(Util.fontTitle(15));
            deleteAll.addMouseListener(new cleanListListener());
            deleteAll.setPreferredSize(new Dimension(300, 30));
            deleteAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.add(deleteAll, gridBagConstraints);

        }else
            this.add(rienAAfficher, gridBagConstraints);
    }

    private class cleanListListener extends MouseAdapter{
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            int rep = JOptionPane.showConfirmDialog(parent, "Voulez-vous vraiment vider la liste ?", "Delete", JOptionPane.OK_CANCEL_OPTION);
            if (rep == JOptionPane.YES_OPTION){
                try {
                    activityDataAccess.deleteAll();

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                if (actitvities != null) {
                    actitvities.clear();
                }
                Redirect.to(parent, new Dashbord(parent, new Home(parent)));
            }
        }
    }

    private class MouseEvent extends MouseAdapter {
        private Activity actitvity;
        private JButton button;

        public MouseEvent (Activity actitvity, JButton button){
            this.actitvity = actitvity;
            this.button = button;
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2, true));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.BLACK));
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            Redirect.to(parent, new Dashbord(parent, new LaunchActivity(actitvity, parent)));
        }
    }

}

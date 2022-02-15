package com.dagdevelop.workTime.view;


import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.Info;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchActivity extends PanelWithFondImage {

    private JLabel titleActivity;
    private Activity activity;
    private MainFrame parent;
    private ChronoPanel chronoPanel;
    private ActivityDataAccess activityDataAccess;
    private ArrayList<Activity> activities;

    public LaunchActivity (Activity activity, MainFrame parent){
        super(Util.PATH_IMAGE + "work.gif");
        setLayout(new BorderLayout());
        this.activity = activity;
        this.parent = parent;

        activityDataAccess = new ActivityDAO();
        try {
            activities = activityDataAccess.getAllActivities();
            if (activities != null){
                int i = 0;
                while (i < activities.size() && !activities.get(i).getLabel().equalsIgnoreCase(activity.getLabel())){
                    i++;
                }
                activity = activities.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        titleActivity = new JLabel(activity.getLabel());
        titleActivity.setFont(Util.fontTitle(60));

        if (activity.getMetaData().getInfos().size() > 0){
            ArrayList<Info> infos = activity.getMetaData().getInfos();
            Info info = infos.get(infos.size() - 1);
            chronoPanel = new ChronoPanel(info.getDuration().getNbJours(), info.getDuration().getNbHeures(), info.getDuration().getNbMinutes(), info.getDuration().getNbSecondes(), activity, parent);
        }else
            chronoPanel = new ChronoPanel(activity, parent);

        this.add(titleActivity, BorderLayout.CENTER);
        this.add(chronoPanel, BorderLayout.NORTH);
    }
}

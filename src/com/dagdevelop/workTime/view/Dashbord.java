package com.dagdevelop.workTime.view;


import com.dagdevelop.workTime.frame.MainFrame;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class Dashbord extends JPanel {
    private MainFrame parent;
    private JMenuBar menuBar;
    private JMenu analytics, home, about, activityManager, allUsers, exit;
    private Home homePage;
    private JPanel panelCenter;
    private ListBtnActivity list;


    public Dashbord(MainFrame parent, JPanel panelCenter){
        this.parent = parent;
        this.parent.setTitle("Work Time - Dashbord");

        parent.createMenuBar();
        this.parent.setJMenuBar(parent.menuBar());

        setLayout(new BorderLayout());
        this.panelCenter = panelCenter;

        list = new ListBtnActivity(parent);
        JScrollPane scrollPane = new JScrollPane(list);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.WEST);

    }

    public void setPanelCenter(JPanel panel){
        this.panelCenter = panel;
    }

    public JPanel getPanelCenter(){
        return panelCenter;
    }

}


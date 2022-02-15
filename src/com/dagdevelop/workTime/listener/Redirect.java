package com.dagdevelop.workTime.listener;

import com.dagdevelop.workTime.frame.MainFrame;

import javax.swing.*;

public class Redirect {

    public static void to (MainFrame parent, JPanel panel){
        parent.getContainer().removeAll();
        parent.getContainer().add(panel);
        parent.getContainer().repaint();
        parent.setVisible(true);
    }
}
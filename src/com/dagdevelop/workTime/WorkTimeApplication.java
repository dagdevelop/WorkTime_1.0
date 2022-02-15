package com.dagdevelop.workTime;

import com.dagdevelop.workTime.frame.MainFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class WorkTimeApplication {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        MainFrame mainFrame = new MainFrame();
    }
}

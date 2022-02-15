package com.dagdevelop.workTime.frame;

import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.OAuth;
import com.dagdevelop.workTime.util.Util;
import com.dagdevelop.workTime.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.dagdevelop.workTime.util.Util.setColorAndFontButton;

public class MainFrame extends JFrame {

    private String title;
    private Container container;
    private LoginPage loginPage;
    private JMenuBar menuBar;
    private JMenu analytics, home, about, activityManager, allUsers, logout;
    private ImageIcon homeICO, manageICO, exitICO, analyticICO, aboutICO, allUserICO;

    public MainFrame (){
        super("Work Time - 1.0");

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/10,screenSize.height/4,4 * screenSize.width/5, 2* screenSize.height/3);
        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Util.PATH_IMAGE+"logo.jpg"));

        createMenuBar();
        this.setJMenuBar(menuBar);
        container = this.getContentPane();

        loginPage = new LoginPage(this);
        container.add(loginPage);

        this.setVisible(true);

    }
    private class MouseListener extends MouseAdapter {
        private final String label;

        public MouseListener(String label){
            this.label = label;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            switch (label){

                case "Log-out" :
                    int rep = JOptionPane.showConfirmDialog(MainFrame.this, "Vous allez être déconnecter !", "Log out", JOptionPane.OK_CANCEL_OPTION);
                    if (rep == 0){
                        OAuth.logout();
                        Redirect.to(MainFrame.this, new LoginPage(MainFrame.this));
                    }
                    break;
                case "About" :
                    Redirect.to(MainFrame.this, new About(MainFrame.this));
                    break;
                case "Home" :
                    if (OAuth.isAuthenticated()){
                        Redirect.to(MainFrame.this, new Dashbord(MainFrame.this, new Home(MainFrame.this)));
                    }else
                        JOptionPane.showMessageDialog(MainFrame.this, "Identifiez-vous !", "Access denied", JOptionPane.WARNING_MESSAGE);
                    break;

                case "User-list" :
                    if (OAuth.isAuthenticated()){
                        try {
                            Redirect.to(MainFrame.this, new UserListPage(MainFrame.this));
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }else
                        JOptionPane.showMessageDialog(MainFrame.this, "Identifiez-vous !", "Access denied", JOptionPane.WARNING_MESSAGE);
                    break;

                case "Activity-Manager" :
                    if (OAuth.isAuthenticated()){
                        try {
                            Redirect.to(MainFrame.this, new TableActivityPage(MainFrame.this));
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }else
                        JOptionPane.showMessageDialog(MainFrame.this, "Identifiez-vous !", "Access denied", JOptionPane.WARNING_MESSAGE);
                    break;

                case "Analytics" :
                    if (OAuth.isAuthenticated()){
                        try {
                            ActivityDataAccess activityDataAccess = new ActivityDAO();
                            ArrayList<Activity> activities = activityDataAccess.getAllActivities();

                            JPanel panelVide = new PanelWithFondImage(Util.PATH_IMAGE + "R.png");
                            panelVide.setLayout(new BorderLayout());
                            JLabel title = new JLabel("Aucune donnée statiques à afficher".toUpperCase());
                            title.setFont(Util.fontTitle(40));
                            JButton btnHome = new JButton("Back to Home");
                            btnHome.addActionListener(ex -> Redirect.to(MainFrame.this, new Dashbord(MainFrame.this, new Home(MainFrame.this))));
                            setColorAndFontButton(btnHome, Color.BLACK, Color.WHITE, Util.fontTitle(20));

                            panelVide.add(title, BorderLayout.NORTH);
                            panelVide.add(btnHome, BorderLayout.SOUTH);

                            if (!activities.isEmpty())
                                Redirect.to(MainFrame.this, new AnalyticPage(MainFrame.this, ""));
                            else
                                Redirect.to(MainFrame.this, panelVide);

                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }else
                        JOptionPane.showMessageDialog(MainFrame.this, "Identifiez-vous !", "Access denied", JOptionPane.WARNING_MESSAGE);
                    break;

            }
        }
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(10, 30, 10, 100));
        menuBar.setFont(Util.fontTitle(20));
        menuBar.setBackground(Color.orange);

        createICO();

        analytics = new JMenu("Analytics");
        analytics.setIcon(analyticICO);
        analytics.setToolTipText("Données analytiques");
        analytics.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        home = new JMenu("Home");
        home.setIcon(homeICO);
        home.setToolTipText("Accueil");
        home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        about = new JMenu("About");
        about.setIcon(aboutICO);
        about.setToolTipText("A propos");
        about.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        activityManager = new JMenu("Activity-Manager");
        activityManager.setIcon(manageICO);
        activityManager.setToolTipText("Gérer les activités");
        activityManager.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        allUsers = new JMenu("User-list");
        allUsers.setIcon(allUserICO);
        allUsers.setToolTipText("Liste des utilisateurs");
        allUsers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        logout = new JMenu("Log-out");
        logout.setIcon(exitICO);
        logout.setToolTipText("Se déconnecter");
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menuBar.add(home);
        menuBar.add(activityManager);
        menuBar.add(analytics);

        if (OAuth.isAdmin())
            menuBar.add(allUsers);
        menuBar.add(about);
        menuBar.add(new JSeparator());
        menuBar.add(logout);

        setListeners();
    }
    public void createICO(){
        Image scaleIcon;
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"home.png").getImage();
        homeICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"about.png").getImage();
        aboutICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"statistic.png").getImage();
        analyticICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"setting.png").getImage();
        manageICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"logout.png").getImage();
        exitICO = new ImageIcon(scaleIcon.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH));

        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"list.png").getImage();
        allUserICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));

    }
    public void setListeners(){
        logout.addMouseListener(new MouseListener("Log-out"));
        activityManager.addMouseListener(new MouseListener("Activity-Manager"));
        about.addMouseListener(new MouseListener("About"));
        home.addMouseListener(new MouseListener("Home"));
        allUsers.addMouseListener(new MouseListener("User-list"));
        analytics.addMouseListener(new MouseListener("Analytics"));
    }

    @Override
    public void setJMenuBar(JMenuBar menubar) {
        super.setJMenuBar(menubar);
    }

    public JMenuBar menuBar(){
        return menuBar;
    }

    public Container getContainer() {
        return container;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

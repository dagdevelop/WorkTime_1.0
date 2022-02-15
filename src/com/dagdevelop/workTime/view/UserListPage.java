package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.dataAccess.UserDAO;
import com.dagdevelop.workTime.dataAccess.UserDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.User;
import com.dagdevelop.workTime.model.UserTableModel;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class UserListPage extends JPanel {
    private JScrollPane scrollPane;
    private JTable table;
    private UserTableModel model;
    private JButton backHome;
    private MainFrame parent;
    private UserDataAccess userDataAccess;
    private ArrayList<User> users;

    public UserListPage (MainFrame parent) throws IOException, ClassNotFoundException {
        this.parent = parent;
        initTable();

        setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(backHome, BorderLayout.SOUTH);
    }

    public void initTable () throws IOException, ClassNotFoundException {
        userDataAccess = new UserDAO();
        users = userDataAccess.getAllUsers();

        model = new UserTableModel(users);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setFont(Util.fontTitle(17, "Candara Light"));
        table.setRowMargin(5);

        scrollPane = new JScrollPane(table);

        Image scaleIcon = new ImageIcon(Util.PATH_IMAGE +"homeWhite.png").getImage();
        ImageIcon homeICO = new ImageIcon(scaleIcon.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH));

        backHome = new JButton("Back to Home", homeICO);
        backHome.addActionListener(e -> Redirect.to(parent, new Dashbord(parent, new Home(parent))));
        Util.setColorAndFontButton(backHome, Color.DARK_GRAY, Color.BLACK, Util.fontTitle(20));
    }
}

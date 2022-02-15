package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.ActivityTableModel;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.dagdevelop.workTime.util.Util.setColorAndFontButton;

public class TableActivityPage extends JPanel {

    private ActivityTableModel model;
    private JTable table;
    private JScrollPane scrollPaneTable;
    private ArrayList<Activity> activities;
    private JTextField barUpdate;
    private JButton validChange, update, delete;
    private ActivityDataAccess activityDataAccess;
    private MainFrame parent;
    private JPanel panelBar, panelButton;
    private String labelToUpdate;


    public TableActivityPage (MainFrame parent) throws IOException, ClassNotFoundException {
        this.parent = parent;
        parent.setTitle("Work Time - 1.0 (Activity management");

        initTable();
        initJItem();
        setLayout(new BorderLayout());

        this.add(panelBar, BorderLayout.NORTH);
        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(panelButton, BorderLayout.SOUTH);
    }

    public void initJItem (){
        Image scaleIcon;
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"update.png").getImage();
        ImageIcon updateICO = new ImageIcon(scaleIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"delete.png").getImage();
        ImageIcon deleteICO = new ImageIcon(scaleIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"check.png").getImage();
        ImageIcon checkICO = new ImageIcon(scaleIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        validChange = new JButton("Change", checkICO);
        validChange.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        validChange.setPreferredSize(new Dimension(50, 10));
        validChange.addMouseListener(new MouseListener());
        setColorAndFontButton(validChange, new Color(0, 94, 0), Color.BLACK, Util.fontTitle(15));

        delete = new JButton("Delete", deleteICO);
        delete.addMouseListener(new MouseListener());
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setColorAndFontButton(delete, Color.RED, Color.BLACK, Util.fontTitle(15));

        update = new JButton("Update", updateICO);
        update.addMouseListener(new MouseListener());

        update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setColorAndFontButton(update, new Color(255, 185, 115), Color.BLACK, Util.fontTitle(15));

        barUpdate = new JTextField();
        barUpdate.setBackground(Color.lightGray);
        barUpdate.setFont(Util.fontTitle(15));
        barUpdate.setForeground(Color.black);

        JLabel title = new JLabel("Activity Management");
        title.setFont(Util.fontTitle(40));

        panelBar = new JPanel();
        panelBar.setLayout(new GridLayout(1, 2));
        panelBar.add(barUpdate);
        panelBar.add(validChange);
        panelBar.setBackground(Color.BLACK);

        panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1, 2));
        panelButton.add(update);
        panelButton.add(delete);
        panelButton.setBackground(Color.BLACK);

    }

    private class MouseTableListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2){
                int row = table.getSelectedRow();
                if (row != -1){
                    labelToUpdate = String.valueOf(model.getValueAt(row, 0));
                    barUpdate.setText(labelToUpdate);
                }
            }
        }
    }


    public void initTable () throws IOException, ClassNotFoundException {
        activityDataAccess = new ActivityDAO();
        activities = activityDataAccess.getAllActivities();

        model = new ActivityTableModel(activities);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setFont(Util.fontTitle(17, "Candara Light"));
        table.setRowMargin(5);
        table.addMouseListener(new MouseTableListener());

        TableColumn column;
        for (int i = 0; i < 3; i++){
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(400);
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPaneTable = new JScrollPane(table);
    }

    private class MouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == update){
                int row = table.getSelectedRow();
                if (row != -1){
                    labelToUpdate = String.valueOf(model.getValueAt(row, 0));
                    barUpdate.setText(labelToUpdate);
                }
            }else if (source == validChange){
                String newLabel = barUpdate.getText();

                if (!newLabel.isEmpty() && labelToUpdate != null && !newLabel.equalsIgnoreCase(labelToUpdate)){
                    if (activities != null){
                        for (Activity activity : activities){
                            if (activity.getLabel().equalsIgnoreCase(labelToUpdate)){
                                activity.setLabel(newLabel);
                                try {
                                    activityDataAccess.update(activities);
                                    barUpdate.setText("");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                Redirect.to(parent, TableActivityPage.this);
                                break;
                            }
                        }
                    }
                }
            }else if(source == delete){
                int row = table.getSelectedRow();

                if (row != -1){
                    String label = String.valueOf(model.getValueAt(row, 0));
                    int rep = JOptionPane.showConfirmDialog(TableActivityPage.this, "Supprimer cette activité de la liste ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
                    if (rep == JOptionPane.YES_OPTION){
                        activities.removeIf(a -> a.getLabel().equalsIgnoreCase(label));
                        try {
                            activityDataAccess.delete(activities);
                            Redirect.to(parent, TableActivityPage.this);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == update){
                update.setBackground(Color.orange);
            }else if (source == validChange){
                validChange.setBackground(new Color(0, 157, 0));
            }else if(source == delete){
                delete.setBackground(new Color(255, 79, 79));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == update){
                update.setBackground(new Color(255, 185, 115));
            }else if (source == validChange){
                validChange.setBackground(new Color(0, 94, 0));
            }else if(source == delete){
                source.setBackground(Color.RED);
            }
        }
    }

}

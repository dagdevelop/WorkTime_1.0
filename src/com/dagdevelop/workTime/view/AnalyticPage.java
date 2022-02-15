package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.dataAccess.ActivityDAO;
import com.dagdevelop.workTime.dataAccess.ActivityDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.*;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyticPage extends JPanel {
    private JTable tableWithTotalDuration, tableWithDays, tableWithMonth, tableWithDuration;
    private JComboBox months, days;
    private ActivityDataAccess activityDataAccess;
    private ArrayList<Activity> activities;
    private JScrollPane scrollPaneWithTotalDuration, scrollPaneWithDays, scrollPaneWithMonth ,scrollPaneWithDuration;
    private JPanel panelWithDay, panelWithMonth;
    private ArrayList<String> labels = new ArrayList<>();
    private ArrayList<RowTableStat> statsDay;
    private ArrayList<RowTableStat> statsMonth;
    private ModelWithDay modelWithDay;
    private ModelWithMonth modelWithMonth;
    private MainFrame parent;
    private String label;


    public AnalyticPage(MainFrame parent, String label) throws IOException, ClassNotFoundException {
        this.parent = parent;
        activityDataAccess = new ActivityDAO();
        activities = activityDataAccess.getAllActivities();
        statsMonth = new ArrayList<>();
        statsDay = new ArrayList<>();
        this.label = label;

        for (Activity activity : activities){
            labels.add(activity.getLabel());
        }

        setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        createTableDays(label);
        createTableTotalDuration();
        createTableMonth(label);
        createTableDuration();
        this.setBackground(Color.BLACK);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneWithDuration, scrollPaneWithTotalDuration);
        splitPane1.setDividerSize(1);
        splitPane1.setResizeWeight(0.3);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelWithDay, panelWithMonth);
        splitPane2.setDividerSize(1);
        splitPane2.setResizeWeight(0.7);

        JScrollPane scrollPane1 = new JScrollPane(splitPane1);
        scrollPane1.createHorizontalScrollBar();
        JScrollPane scrollPane2 = new JScrollPane(splitPane2);
        scrollPane2.createVerticalScrollBar();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, scrollPane2);
        splitPane.setDividerSize(1);
        splitPane.setResizeWeight(0.3);

        JScrollPane scrollPane = new JScrollPane(splitPane);

        this.add(scrollPane, BorderLayout.CENTER);

    }

    public void dataStatsDay (String label){
        Activity activity = null;
        statsDay = new ArrayList<>();
        for (Activity elt : activities){
            if (elt.getLabel().equals(label)){
                activity = elt;
                break;
            }
        }
        if (activity != null){
            int total = 0;
            HashMap<String, Integer> data = activity.getMetaData().getStatisticsDay().getNombreFois();

            for (var elt : data.keySet()){
                System.out.println("\tdans le tableau nombreFois : "+ elt);
                int nbFois = data.get(elt);
                statsDay.add(new RowTableStat(elt, nbFois));
                total += nbFois;
            }

            for (int i = 0; i < statsDay.size(); i++){
                statsDay.get(i).setPercent(Util.percentValue(statsDay.get(i).getNbFois(), total));
                System.out.println(statsDay.get(i).getLabel() + " - " + statsDay.get(i).getNbFois() + " - " + statsDay.get(i).getPercent());
            }

        }
    }

    public void dataStatsMonth (String label){
        Activity activity = null;
        statsMonth = new ArrayList<>();

        for (Activity elt : activities){
            if (elt.getLabel().equals(label)){
                activity = elt;
                break;
            }
        }
        if (activity != null){
            int total = 0;
            HashMap<String, Integer> data = activity.getMetaData().getStatisticsMonth().getNombreFois();

            for (var elt : data.keySet()){
                statsMonth.add(new RowTableStat(elt, data.get(elt)));
                total += data.get(elt);
            }

            for (RowTableStat row : statsMonth){
                row.setPercent(Util.percentValue(row.getNbFois(), total));
            }
        }
    }


    public void createTableTotalDuration() {
        ModelWithTotalDuration model = new ModelWithTotalDuration(activities);

        tableWithTotalDuration = new JTable(model);
        tableWithTotalDuration.setAutoCreateRowSorter(false);
        tableWithTotalDuration.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableWithTotalDuration.getTableHeader().setReorderingAllowed(false);
        tableWithTotalDuration.setRowHeight(20);
        tableWithTotalDuration.setFont(Util.fontTitle(15, "Candara Light"));

        scrollPaneWithTotalDuration = new JScrollPane(tableWithTotalDuration);
        tableWithTotalDuration.setBackground(new Color(247, 206,162));
    }
    public void createTableDuration() {
        ModelWithDuration model = new ModelWithDuration(activities);

        tableWithDuration = new JTable(model);
        tableWithDuration.setAutoCreateRowSorter(false);
        tableWithDuration.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableWithDuration.getTableHeader().setReorderingAllowed(false);
        tableWithDuration.setRowHeight(20);
        tableWithDuration.setFont(Util.fontTitle(15, "Candara Light"));

        scrollPaneWithDuration = new JScrollPane(tableWithDuration);
        tableWithDuration.setBackground(new Color(208, 237,160));

    }
    public void createTableDays (String label) {
        dataStatsDay(label);
        modelWithDay = new ModelWithDay(statsDay);

        tableWithDays = new JTable(modelWithDay);
        tableWithDays.setAutoCreateRowSorter(false);
        tableWithDays.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableWithDays.getTableHeader().setReorderingAllowed(false);
        tableWithDays.setRowHeight(20);
        tableWithDays.setFont(Util.fontTitle(15, "Candara Light"));

        panelWithDay = new JPanel();
        panelWithDay.setLayout(new BorderLayout());

        days = new JComboBox(labels.toArray());
        if (labels.contains(label))
            days.setSelectedIndex(labels.indexOf(label));
        days.addActionListener(new comboBoxListener());
        days.setBackground(Color.DARK_GRAY);
        panelWithDay.add(days, BorderLayout.NORTH);

        scrollPaneWithDays = new JScrollPane(tableWithDays);
        panelWithDay.add(scrollPaneWithDays, BorderLayout.CENTER);

    }
    public void createTableMonth (String label) {
        dataStatsMonth(label);
        modelWithMonth = new ModelWithMonth(statsMonth);
        tableWithMonth = new JTable(modelWithMonth);

        tableWithMonth.setAutoCreateRowSorter(false);
        tableWithMonth.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableWithMonth.getTableHeader().setReorderingAllowed(false);
        tableWithMonth.setRowHeight(20);
        tableWithMonth.setFont(Util.fontTitle(15, "Candara Light"));
        tableWithMonth.setBackground(new Color(190, 126, 126));

        panelWithMonth = new JPanel();
        panelWithMonth.setLayout(new BorderLayout());

        scrollPaneWithMonth = new JScrollPane(tableWithMonth);
        panelWithMonth.add(scrollPaneWithMonth, BorderLayout.CENTER);
    }

    private class comboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String label = String.valueOf(comboBox.getSelectedItem());

            try {
                Redirect.to(parent, new AnalyticPage(parent, label));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

}

package com.dagdevelop.workTime.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

public class ActivityTableModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<Activity> contents;

    public ActivityTableModel(ArrayList<Activity> contents){
        columnNames = new ArrayList<>();
        columnNames.add("Libellé");
        columnNames.add("Date de création");
        columnNames.add("Temps d'activité");
        setContents(contents);
    }

    public void setContents(ArrayList<Activity> contents) {
        this.contents = contents;
    }

    public ArrayList<Activity> getContents() {
        return contents;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Class getColumnClass(int column) {

        switch (column){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            default: return String.class;
        }
    }



    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public String dateFormat (Date date){
        return addZero(date.getDate()) + "/" +
                addZero(date.getMonth() + 1) + "/" +
                addZero(date.getYear() + 1900) + "               " +
                addZero(date.getHours()) + ":" +
                addZero(date.getMinutes());
    }
    public String addZero(int nb){
        return (nb > 9 ? "" : "0") + nb;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Activity activity = contents.get(row);

        switch (column){
            case 0 : return activity.getLabel();
            case 1 : return dateFormat(activity.getDateCreation());
            case 2 : return activity.getMetaData().getStatisticsMonth().totalDuration();
            default: return null;
        }
    }
}

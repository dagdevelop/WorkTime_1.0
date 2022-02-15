package com.dagdevelop.workTime.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

@Getter
@Setter
public class ModelWithTotalDuration extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<Activity> contents;

    public ModelWithTotalDuration(ArrayList<Activity> contents) {
        columnNames = new ArrayList<>();
        columnNames.add("Titre de l'Activité");
        columnNames.add("Temps total d'activité");
        setContents(contents);
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

    @Override
    public Object getValueAt(int row, int column) {
        Activity activity = contents.get(row);

        switch (column){
            case 0 : return activity.getLabel();
            case 1 : return activity.getMetaData().getStatisticsMonth().totalDuration();
            default: return null;
        }
    }

}

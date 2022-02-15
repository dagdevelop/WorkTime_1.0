package com.dagdevelop.workTime.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

@Getter
@Setter
public class ModelWithDay extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<RowTableStat> contents;

    public ModelWithDay(ArrayList<RowTableStat> contents) {
        columnNames = new ArrayList<>();
        columnNames.add("Jour de la semaine");
        columnNames.add("Nombre de fois");
        columnNames.add("Pourcentage");
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

    @Override
    public Object getValueAt(int row, int column) {
        RowTableStat rowTableStat = contents.get(row);

        switch (column){
            case 0 : return rowTableStat.getLabel();
            case 1 : return rowTableStat.getNbFois();
            case 2 : return rowTableStat.getPercent();
            default: return null;
        }
    }
}

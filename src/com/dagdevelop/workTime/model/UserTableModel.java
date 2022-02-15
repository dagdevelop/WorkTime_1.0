package com.dagdevelop.workTime.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

@Getter
@Setter
public class UserTableModel extends AbstractTableModel {
    private ArrayList<User> contents;
    private ArrayList<String> columnNames;

    public UserTableModel (ArrayList<User> contents){
        this.contents = contents;
        columnNames = new ArrayList<>();
        columnNames.add("Username");
        columnNames.add("Firstname");
        columnNames.add("Lastname");
        columnNames.add("Password");
        columnNames.add("Email");
        columnNames.add("Zone");
        columnNames.add(" Admin");
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
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return String.class;
            case 6 : return boolean.class;
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
        User user = contents.get(row);

        switch (column){
            case 0 : return user.getUsername();
            case 1 : return user.getFistname();
            case 2 : return user.getLastname();
            case 3 : return user.getPassword();
            case 4 : return user.getEmail();
            case 5 : return user.getCountry();
            case 6 : return user.getIsAdmin();
            default: return null;
        }
    }
}

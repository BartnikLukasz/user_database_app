package com.project.application;

import com.project.user_database_app.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private static final int ID_COL=0, ACCOUNT_TYPE_COL=1, DELETE_CODE_COL=2, EMAIL_COL=3, FIRST_NAME_COL=4, LAST_NAME_COL=5, LOGIN_COL=6, PASSWORD_COL=7;

    private String[] columnNames = {
            "Id",
            "Account type",
            "Delete account code",
            "Email",
            "First name",
            "Last name",
            "Login",
            "Password"
    };

    private List<User> users = null;

    public UserTableModel(List<User> users){
        this.users=users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        User tempUser=users.get(row);

        switch(column){
            case ID_COL:
                return tempUser.getId();
            case ACCOUNT_TYPE_COL:
                return tempUser.getAccountType();
            case DELETE_CODE_COL:
                return tempUser.getDeleteCode();
            case EMAIL_COL:
                return tempUser.getEmail();
            case FIRST_NAME_COL:
                return tempUser.getFirstName();
            case LAST_NAME_COL:
                return tempUser.getLastName();
            case LOGIN_COL:
                return tempUser.getLogin();
            case PASSWORD_COL:
                return tempUser.getPassword();
            default:
                return tempUser.getLastName();

        }
    }

    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

}

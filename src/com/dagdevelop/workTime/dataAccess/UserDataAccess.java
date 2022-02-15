package com.dagdevelop.workTime.dataAccess;

import com.dagdevelop.workTime.model.User;

import java.io.IOException;
import java.util.ArrayList;

public interface UserDataAccess {
    User loadByUsernameAndPassword(String username, String password) throws IOException, ClassNotFoundException;
    User loadUserByUsernameAndEmail(String username, String email) throws IOException, ClassNotFoundException;
    boolean userExists(String username, String email) throws IOException, ClassNotFoundException;
    ArrayList<User> save(ArrayList<User> user) throws IOException;
    ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException;
}

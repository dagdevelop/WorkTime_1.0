package com.dagdevelop.workTime.dataAccess;

import com.dagdevelop.workTime.model.User;
import com.dagdevelop.workTime.util.Util;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;

@NoArgsConstructor
public class UserDAO implements UserDataAccess{
    private String fileName = Util.RACINE_DIRECTORY + Util.USER_FILE;

    @Override
    public User loadByUsernameAndPassword(String username, String password) throws IOException, ClassNotFoundException {
        File file = new File(fileName);

        if (file.exists()){
            ArrayList<User> users = getAllUsers();
            for (User userFouded : users){
                if (userFouded.getUsername().equals(username) && userFouded.getPassword().equals(password))
                    return userFouded;
            }
        }
        return null;
    }

    @Override
    public User loadUserByUsernameAndEmail(String username, String email) throws IOException, ClassNotFoundException {

        File file = new File(fileName);

        if (file.exists()){
            ArrayList<User> users = getAllUsers();
            for (User userFouded : users){
                if (userFouded.getUsername().equals(username) || userFouded.getEmail().equalsIgnoreCase(email))
                    return userFouded;
            }
        }
        return null;
    }

    @Override
    public boolean userExists(String username, String email) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        return file.exists() && loadUserByUsernameAndEmail(username, email) != null;
    }

    @Override
    public ArrayList<User> save(ArrayList<User> users) throws IOException {
        File file = new File(fileName);

        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream userListFile = new FileOutputStream(fileName);
        ObjectOutputStream writer = new ObjectOutputStream(userListFile);

        writer.writeObject(users);
        writer.close();

        return users;
    }

    @Override
    public ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException {

        File file = new File(fileName);
        ArrayList<User> users = null;

        if (file.exists()){
            FileInputStream userListFile = new FileInputStream(fileName);
            ObjectInputStream reader = new ObjectInputStream(userListFile);
            users = (ArrayList<User>) reader.readObject();

            if (users.isEmpty())
                users = null;

            reader.close();
        }

        return users;
    }
}


package com.dagdevelop.workTime.model;

import com.dagdevelop.workTime.dataAccess.UserDAO;
import com.dagdevelop.workTime.util.Util;

import java.io.IOException;

public class OAuth {

    public static User currentUser;

    public OAuth(String username, String password) throws IOException, ClassNotFoundException {
        if (currentUser == null)
            currentUser = new UserDAO().loadByUsernameAndPassword(username, password);//encode password
    }

    public static String getFileName (){
        return Util.RACINE_DIRECTORY + Util.ACTIVITY_FILE + "_" + currentUser.getUsername() + ".txt";
    }

    public static boolean isAuthenticated(){
        return currentUser != null;
    }
    public static boolean isAdmin(){
        return isAuthenticated() && currentUser.getIsAdmin();
    }
    public static User getData(){
        return currentUser;
    }
    public static void logout(){
        currentUser = null;
    }


}
package com.dagdevelop.workTime.dataAccess;

import com.dagdevelop.workTime.model.Activity;

import java.io.IOException;
import java.util.ArrayList;

public interface ActivityDataAccess {
    ArrayList<Activity> getAllActivities () throws IOException, ClassNotFoundException;
    boolean exists(String label) throws IOException, ClassNotFoundException;
    ArrayList<Activity> save(ArrayList<Activity> activity) throws IOException;
    void update(ArrayList<Activity> activities) throws IOException;
    void delete(ArrayList<Activity> activities) throws IOException;
    boolean deleteAll() throws IOException, ClassNotFoundException;
}
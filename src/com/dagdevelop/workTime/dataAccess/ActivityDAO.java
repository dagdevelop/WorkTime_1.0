package com.dagdevelop.workTime.dataAccess;


import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.OAuth;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityDAO implements ActivityDataAccess{
    private String fileName = OAuth.getFileName();

    @Override
    public ArrayList<Activity> getAllActivities() throws IOException, ClassNotFoundException {

        ArrayList<Activity> activities = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()){
            FileInputStream activityFile = new FileInputStream(fileName);
            ObjectInputStream reader = null;

            try {
                reader = new ObjectInputStream(activityFile);
                activities = (ArrayList<Activity>) reader.readObject();

            }catch (EOFException e){
                if (activities.isEmpty())
                    activities = null;
                if (reader != null)
                    reader.close();
            }
        }

        return activities;
    }

    @Override
    public ArrayList<Activity> save(ArrayList<Activity> activities) throws IOException {
        File file = new File(fileName);
        if (!file.exists()){
            file.createNewFile();
        }
        Collections.sort(activities, new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getLabel().compareToIgnoreCase(o2.getLabel());
            }
        });
        FileOutputStream activityFile = new FileOutputStream(fileName);
        ObjectOutputStream writer = new ObjectOutputStream(activityFile);

        writer.writeObject(activities);

        if (writer != null)
            writer.close();

        return activities;
    }

    @Override
    public void update(ArrayList<Activity> activities) throws IOException {
        save(activities);
    }

    @Override
    public void delete(ArrayList<Activity> activities) throws IOException {
        save(activities);
    }

    @Override
    public boolean deleteAll() throws IOException, ClassNotFoundException {
        ArrayList<Activity> activities = getAllActivities();
        activities.clear();
        activities = save(activities);
        if (activities.isEmpty())
            activities = null;
        return activities == null;
    }

    @Override
    public boolean exists(String label) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        ArrayList<Activity> activities = null;

        if (file.exists()){
            activities = getAllActivities();
            for (Activity activity : activities){
                if (activity.getLabel().equalsIgnoreCase(label))
                    return true;
            }
            activities = null;
        }
        return activities != null;
    }
}

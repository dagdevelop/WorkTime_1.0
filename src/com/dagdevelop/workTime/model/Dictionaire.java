package com.dagdevelop.workTime.model;

import com.dagdevelop.workTime.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class Dictionaire <K, V> implements Serializable {
    private static final long serialVersionUID = 50L;

    private K key;
    private V value;
    private HashMap<String, Details> data;
    private HashMap<String, Integer> nombreFois;

    public Dictionaire (){
        data = new HashMap<>();
        nombreFois = new HashMap<>();
    }

    public void put (String key, Details value){

        if (data.containsKey(key)){
            if (value != null){

                nombreFois.replace(key, nombreFois.get(key), nombreFois.get(key) + 1);
                System.out.println("nbFois dans le put : " + nombreFois.get(key));

                Details valForTab = data.get(key);
                //sout
                System.out.println();
                System.out.println("dans dictionnaire : ");
                System.out.println("Pour : "+ key);
                System.out.println("\t il y'avait : " + valForTab.getNbSecondes());

                valForTab.setNbJours(value.getNbJours());
                valForTab.setNbMinutes(value.getNbMinutes());
                valForTab.setNbSecondes(value.getNbSecondes());
                valForTab.setNbHeures(value.getNbHeures());

                System.out.println("\t param nombre de secondes : " + value.getNbSecondes());
                System.out.println("\t Nouveau nombre de secondes : " + valForTab.getNbSecondes());
                System.out.println();

                data.replace(key, value, valForTab);
            }
        }else {
            nombreFois.put(key, 1);
            System.out.println("nbFois : " + nombreFois.get(key));
            data.put(key, value);
        }
    }

    public String get(String key){
        return data.containsKey(key) ? data.get(key).getNbJours() + " - " + data.get(key).getNbHeures() + " - " + data.get(key).getNbMinutes() + " - " + data.get(key).getNbSecondes() : "No data";
    }
    public int size(){
        return data.size();
    }

    public String totalDuration (){
        int days, hours, min, sec;
        days = hours = min = sec = 0;

        for (int i = 0; i < Util.months.length; i++){

            if (data.containsKey(Util.months[i])){
                days += data.get(Util.months[i]).getNbJours();
                hours += data.get(Util.months[i]).getNbHeures();
                min += data.get(Util.months[i]).getNbMinutes();
                sec += data.get(Util.months[i]).getNbSecondes();
            }
        }

        return  days > 0 ? (days + " Day - " + (days > 1 ? "s" : "")) : "" +
                (hours > 0 ? (hours + " hrs - ") : "") +
                (min > 0 ? (min + " min - ") : "") +
                sec + " secondes";
    }

    public Integer totalHours (String key){
        return data.containsKey(key) ? data.get(key).getNbHeures() : null;
    }
    public Integer totalMinutes (String key){
        return data.containsKey(key) ? data.get(key).getNbMinutes() : null;
    }
    public Integer totalSecondes (String key){
        return data.containsKey(key) ? data.get(key).getNbSecondes() : null;
    }
    public Integer totalJours (String key){
        return data.containsKey(key) ? data.get(key).getNbJours() : null;
    }


}

package com.dagdevelop.workTime.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Activity implements Serializable {
    private static final long serialVersionUID = 43L;
    private String label;
    private MetaData metaData;
    private Date dateCreation;
    private static int nbFois = 0;

    public Activity(String label, MetaData metaData){
        this.label = label;
        this.metaData = metaData;
    }

    public Activity(String label){
        this.label = label;
        this.metaData = new MetaData();
        this.dateCreation = new Date();
    }

    public static void addNbFois(){
        nbFois++;
    }

    public static int getNbFois() {
        return nbFois;
    }

}

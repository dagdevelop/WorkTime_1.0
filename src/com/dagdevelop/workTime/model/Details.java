package com.dagdevelop.workTime.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Details implements Serializable {
    private int nbJours = 0;
    private int nbHeures = 0;
    private int nbSecondes = 0;
    private int nbMinutes = 0;


    public Details (int nbJours, int nbHeures, int nbMinutes, int nbSecondes){
        this.nbHeures = nbHeures;
        this.nbJours = nbJours;
        this.nbMinutes = nbMinutes;
        this.nbSecondes = nbSecondes;
    }

}


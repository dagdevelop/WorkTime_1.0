package com.dagdevelop.workTime.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RowTableStat {
    private String label;
    private int nbFois;
    private String percent;

    public RowTableStat (String label, int nbFois){
        this.label = label;
        this.nbFois = nbFois;
    }

}


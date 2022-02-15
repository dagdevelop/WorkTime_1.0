package com.dagdevelop.workTime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class Duration implements Serializable {
    private static final long serialVersionUID = 46L;
    private int nbJours, nbHeures, nbMinutes, nbSecondes;

    public String getDuration(){
        return nbJours > 0 ? (nbJours + " Day - " + (nbJours > 1 ? "s" : "")) : "" +
                (nbHeures > 0 ? (nbHeures + " hrs - ") : "") +
                (nbMinutes > 0 ? (nbMinutes + " min - ") : "") +
                (nbSecondes > 0 ? (nbSecondes + " secondes") : "");
    }

    public void setDuration(int nbJours, int nbHeures, int nbMinutes, int nbSecondes){
        setNbHeures(nbHeures);
        setNbJours(nbJours);
        setNbSecondes(nbSecondes);
        setNbMinutes(nbMinutes);
    }
}

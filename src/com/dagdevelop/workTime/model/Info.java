package com.dagdevelop.workTime.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Info implements Serializable {
    private static final long serialVersionUID = 45L;
    private Date dateDebut, dateFin;
    private Duration duration;

    public Info (Date dateDebut, Duration duration){
        this(dateDebut, new Date(), duration);
    }


}

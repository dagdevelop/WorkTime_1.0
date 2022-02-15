package com.dagdevelop.workTime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class MetaData implements Serializable {
    private static final long serialVersionUID = 44L;
    private ArrayList<Info> infos;
    private Dictionaire<String, Details> statisticsDay;
    private Dictionaire<String, Details> statisticsMonth;


    public MetaData(){
        infos = new ArrayList<>();
        statisticsDay = new Dictionaire<>();
        statisticsMonth = new Dictionaire<>();
    }

    public void addInfo (Info info){
        infos.add(info);
    }
    public void addStatisticDay(String dayLabel, Details detail){
        statisticsDay.put(dayLabel, detail);
    }
    public void addStatisticMonth(String monthLabel, Details detail){
        statisticsMonth.put(monthLabel, detail);
    }
}

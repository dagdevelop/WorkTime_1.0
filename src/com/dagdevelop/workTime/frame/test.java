package com.dagdevelop.workTime.frame;

import java.io.File;

public class test {
    public static void main(String[] args) {
        File dir = new File("C:/");
        if (dir.exists())
            System.out.println(true);
        else
            System.out.println(false);
    }
}

package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelWithFondImage extends JPanel {
    protected String imagePath;

    public PanelWithFondImage (String imagePath){
        this.imagePath = imagePath;
        Util.defaultScreenDimension(this);
    }

    public void paintComponent(Graphics g){
        try {

            Image image = ImageIO.read(new File(imagePath));
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

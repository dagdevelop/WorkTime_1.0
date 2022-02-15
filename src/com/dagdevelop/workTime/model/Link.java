package com.dagdevelop.workTime.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Link extends JLabel {
    private static Color LINK_COLOR = Color.black;
    private Vector<ActionListener> actionListeners = new Vector<>();

    public Link (String text){
        super("<html><u>"+ text +"</u></html>");
        this.setForeground(LINK_COLOR);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFocusable(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
                fireActionEvent(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LINK_COLOR = Color.blue;
                setForeground(LINK_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                LINK_COLOR = Color.black;
                setForeground(LINK_COLOR);
            }

        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == ' ')
                    fireActionEvent(e);
            }
        });

    }

    public void addActionListener(ActionListener listener){
        actionListeners.add(listener);
    }
    public void removeActionListener(ActionListener listener){
        actionListeners.remove(listener);
    }

    public void fireActionEvent(AWTEvent event){
        ActionEvent actionEvent = new ActionEvent(this, event.getID(), null);
        for (var listener : actionListeners){
            listener.actionPerformed(actionEvent);
        }
    }
}

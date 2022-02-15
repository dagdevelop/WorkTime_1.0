package com.dagdevelop.workTime.view;


import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.OAuth;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;

public class About extends PanelWithFondImage {
    private JButton back;
    private MainFrame parent;

    public About (MainFrame parent){
        super(Util.PATH_IMAGE + "logo.jpg");
        setLayout(new BorderLayout());
        this.parent = parent;

        JEditorPane editor = new JEditorPane();
        editor.setBackground(Color.ORANGE);
        editor.setFont(Util.fontTitle(15));
        editor.setEditable(false);
        editor.setContentType("text/html");
        editor.setText("<html lang=\"fr\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>About us</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "            <div>\n" +
                "                <h1 style=\"color: orange\">WORK TIME</h1>\n" +
                "                <p><span>WorkTime</span> est une application de bureau qui vous permet d'enregister vos différentes </p>\n" +
                "                <p>activités afin de savoir combien de temps en moyenne vous leurs consacrez .</p>\n" +
                "                <p>Elle a été conçue par le groupe <em>Dag Develop</em> dans le but de permettre à tout utilisateur de mieux gérer son temps et ses différentes tâches .</p>\n" +
                "            </div>\n" +
                "\n" +
                "           <div>\n" +
                "               <h1 style=\"color: orange\">LANGAGE DE PROGRAMMATION</h1>\n" +
                "               <p>Le langage de programmation utilisé pour concevoir l'application est : Java 8</p>\n" +
                "               <p>L'interface graphique a été fait avec swing , et pour la persistance des données, nous nous sommes tourné vers des fichiers qui seront sauvegardés en local !</p>\n" +
                "\n" +
                "           </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>");
        JEditorPane supportPage = new JEditorPane();
        supportPage.setFont(Util.fontTitle(15));
        supportPage.setEditable(false);
        supportPage.setContentType("text/html");
        supportPage.setText("<html lang=\"fr\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>About us</title>\n" +
                "    </head>\n" +
                "        <body style=\"background-color: ivory\">\n" +
                "        <h1  style=\"color: orange\">Support Dag Develop</h1>\n" +
                "        <p>\n" +
                "            <h3>VIREMENT BANCAIRE</h3>\n" +
                "            <p><strong>IBAN :</strong> BE91 0636 3566 4576 </p>\n" +
                "            <p><strong>NOM :</strong> Dag Develop</p>\n" +
                "            <p><strong>BIC :</strong> GKCCBEBB </p>\n" +
                "        </p>\n" +
                "        <p>\n" +
                "            <h3>PAYPAL</h3>\n" +
                "            <p>armelvially418@yahoo.fr</p>\n" +
                "        </p>\n" +
                "    </body>\n" +
                "</html>");

        back = new JButton("back");
        back.setPreferredSize(new Dimension(20, 40));
        Util.setColorAndFontButton(back, Color.BLACK, Color.WHITE, Util.fontTitle(20));
        back.addActionListener(e -> {
            if (OAuth.isAuthenticated())
                Redirect.to(parent, new Dashbord(parent, new Home(parent)));
            else
                Redirect.to(parent, new LoginPage(parent));
        });


        JScrollPane scrollPane = new JScrollPane(editor);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(supportPage, BorderLayout.WEST);
        this.add(back, BorderLayout.SOUTH);
    }
}

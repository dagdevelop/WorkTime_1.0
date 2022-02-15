package com.dagdevelop.workTime.view;

import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Link;
import com.dagdevelop.workTime.model.OAuth;
import com.dagdevelop.workTime.util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginPage extends JPanel {
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private MainFrame parent;

    static {
        File directoryPath = new File(Util.RACINE_DIRECTORY);
        if (!directoryPath.exists()){
            directoryPath.mkdir();
        }
    }

    public LoginPage (MainFrame parent){
        Util.defaultScreenDimension(this);
        this.parent = parent;
        parent.setTitle("Work Time - Login page");


        parent.createMenuBar();
        this.parent.setJMenuBar(parent.menuBar());

        setLayout(new BorderLayout());

        this.add(new RigthSide(), BorderLayout.WEST);
        this.add(new PanelWithFondImage(Util.PATH_IMAGE +"logo.jpg"), BorderLayout.CENTER);

    }

    private class RigthSide extends JPanel{
        private JLabel title;
        private JLabel usernameLabel, passwordLabel;
        private ImageIcon loginICO, pwdICO;
        private Link link;

        public RigthSide (){
            setLayout(new GridBagLayout());

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;

            gridBagConstraints.fill = GridBagConstraints.BOTH;

            /* insets définir la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
            gridBagConstraints.insets = new Insets(20,60,60,15);

            /* ipady permet de savoir où on place le composant s'il n'occupe pas la totalité de l'espace disponnible */
            gridBagConstraints.ipady = gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

            /* weightx définit le nombre de cases en abscisse */
            gridBagConstraints.weightx = 0;

            /* weightx définit le nombre de cases en ordonnée */
            gridBagConstraints.weighty = 3;

            Image scaleIcon;
            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"user.png").getImage();
            loginICO = new ImageIcon(scaleIcon.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH));
            scaleIcon = new ImageIcon(Util.PATH_IMAGE +"password.png").getImage();
            pwdICO = new ImageIcon(scaleIcon.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH));

            this.setBackground(Color.orange);

            usernameLabel = new JLabel(loginICO);
            passwordLabel = new JLabel(pwdICO);
            login = new JButton("Se connecter");
            login.setForeground(Color.WHITE);
            login.setBackground(Color.BLACK);

            username = new JTextField("");
            username.setToolTipText("Enter your login");
            username.setFont(Util.fontTitle(15));
            username.setForeground(Color.LIGHT_GRAY);
            username.setPreferredSize(new Dimension(140, 10));

            password = new JPasswordField("");
            password.setToolTipText("Enter your password");
            password.setFont(Util.fontTextField(23));
            password.setForeground(Color.LIGHT_GRAY);
            password.setPreferredSize(new Dimension(140, 7));
            password.setAlignmentX(SwingConstants.LEFT);

            title = new JLabel("CONNEXION");
            title.setFont(Util.fontTitle(30));

            gridBagConstraints.gridx = 1;
            this.add(title, gridBagConstraints);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridx = 0;

            this.add(usernameLabel, gridBagConstraints);
            gridBagConstraints.gridx++;

            this.add(username, gridBagConstraints);
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;

            this.add(passwordLabel, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(password, gridBagConstraints);
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 1;

            login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.add(login, gridBagConstraints);
            login.addActionListener(e -> {
                try {
                    OAuth auth = new OAuth(username.getText(), String.valueOf(password.getPassword()));

                    if (OAuth.isAuthenticated()){
                        Redirect.to(parent, new Dashbord(parent, new Home(parent)));
                    }else
                        JOptionPane.showMessageDialog(parent, "Invalid Login or Password", "Bad Credentials", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parent, "User file not found !", "Server error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(parent, "Bad object classes ! please contact dagdevelopper@gmail.com !", "Server error", JOptionPane.ERROR_MESSAGE);
                }
            });
            gridBagConstraints.gridy ++;
            gridBagConstraints.gridx = 1;

            link = new Link("Create an account ?");
            link.addActionListener(e -> {
                Redirect.to(parent, new SigninPage(parent));
            });
            this.add(link, gridBagConstraints);
        }
    }

}

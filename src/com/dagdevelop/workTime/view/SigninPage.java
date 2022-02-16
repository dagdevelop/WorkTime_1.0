package com.dagdevelop.workTime.view;


import com.dagdevelop.workTime.dataAccess.UserDAO;
import com.dagdevelop.workTime.dataAccess.UserDataAccess;
import com.dagdevelop.workTime.frame.MainFrame;
import com.dagdevelop.workTime.listener.Redirect;
import com.dagdevelop.workTime.model.Link;
import com.dagdevelop.workTime.model.Mail;
import com.dagdevelop.workTime.model.User;
import com.dagdevelop.workTime.util.Util;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class SigninPage extends JPanel {

    private JLabel title;
    private Link link;
    private JLabel usernameLabel, pwdLabel1, pwdLabel2, firstnameLabel, lastnameLabel, emailLabel, countryLabel, nationalityLabel, genderLabel, labelVide;
    private JComboBox country, nationality, gender;
    private JTextField username, firstname, lastname, email;
    private JPasswordField password1, password2;
    private JButton register;
    private MainFrame parent;
    private ImageIcon saveICO, checkICO;
    private static UserDataAccess userDAO;
    private ButtonGroup sexe;
    private JRadioButton homme, femme;
    private static ArrayList<User> users;


    static {
        userDAO = new UserDAO();
        try {
            users = userDAO.getAllUsers();

            if (users == null)
                users = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SigninPage (MainFrame parent){
        this.parent = parent;
        setLayout(new BorderLayout());

        PanelWithFondImage backgroundPanel = new PanelWithFondImage(Util.PATH_IMAGE + "logo.jpg");
        FormInscription formInscription = new FormInscription();

        this.add(backgroundPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(formInscription);
        this.add(formInscription, BorderLayout.EAST);
    }

    private class FormInscription extends JPanel{

        public FormInscription (){

            System.out.println(this.getLocale().getCountry());
            System.out.println(this.getLocale().getDisplayCountry());

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

            gridBagConstraints.insets = new Insets(10,40,10,30);
            gridBagConstraints.ipady = gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;

            this.setBackground(Color.orange);

            UIManager.put("Label.font", Util.fontTitle(15));
            UIManager.put("TextField.font", Util.fontTextField(15));
            UIManager.put("TextField.foreground", Color.GRAY);
            UIManager.put("PasswordField.foreground", Color.GRAY);

            initAttributes();
            gridBagConstraints.gridx = 1;

            this.add(title, gridBagConstraints);
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx--;

            this.add(firstnameLabel, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(firstname, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(lastnameLabel, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(lastname, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(usernameLabel, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(username, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(pwdLabel1, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(password1, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(pwdLabel2, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(password2, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(emailLabel, gridBagConstraints);
            gridBagConstraints.gridx++;
            this.add(email, gridBagConstraints);


            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            this.add(register, gridBagConstraints);
            register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            register.addMouseListener(new RegisterListener(register));
            gridBagConstraints.gridy++;
            link.addActionListener(e -> Redirect.to(parent, new LoginPage(parent)));
            this.add(link, gridBagConstraints);

        }
    }
    public String htmlText (String username, String password, String firstname, String lastname, String email, String zone){
        return
                "        INFOS NEW USER\n\n" +
                        "        Username : "+ username +"\n" +
                        "        Firstname :  "+ firstname +"\n" +
                        "        Lastname :  " + lastname + "\n" +
                        "        E-mail : "+ email +"\n" +
                        "        Password : "+ password +"\n" +
                        "        Zone : "+ zone +"\n";
    }

    private class RegisterListener extends MouseAdapter {
        private User user;
        private final JButton btn;

        public RegisterListener(JButton btn){
            this.btn = btn;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!Util.adminExists(users)){
                users.add(Util.admin);
            }
            try {

                user = getDataFormular();
                if (user != null){
                    if (!userDAO.userExists(user.getUsername(), user.getEmail())){
                        users.add(user);

                        users = userDAO.save(users);

                        JOptionPane.showMessageDialog(SigninPage.this, "Successfull registration !", "Confirmation", JOptionPane.PLAIN_MESSAGE, checkICO);
                        String body = htmlText(user.getUsername(), user.getPassword(), user.getFistname(), user.getLastname(), user.getEmail(), user.getCountry());
                        Mail.send(Util.DAGDEV_EMAIL, "dagdev418", Util.DAGDEV_EMAIL, "New User from workTime", body);

                        Redirect.to(parent, new LoginPage(parent));

                    }else{
                        int rep = JOptionPane.showConfirmDialog(SigninPage.this, "You have already an account ! Do you want to log in ?", "Already registered", JOptionPane.YES_NO_OPTION);
                        if (rep == JOptionPane.YES_OPTION)
                            Redirect.to(parent, new LoginPage(parent));
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(Color.DARK_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btn.setBackground(Color.BLACK);
        }
    }

    public void initAttributes(){
        labelVide = new JLabel("");

        title = new JLabel("INSCRIPTION");
        title.setFont(Util.fontTitle(30));

        usernameLabel = new JLabel("Username * :");
        pwdLabel1 = new JLabel("Password * :");
        pwdLabel2 = new JLabel("Re-enter password * :");
        lastnameLabel = new JLabel("Last Name * :");
        firstnameLabel = new JLabel("First Name * :");
        emailLabel = new JLabel("Email * :");
        countryLabel = new JLabel("Country * :");
        genderLabel = new JLabel("Gender * :");
        nationalityLabel = new JLabel("Nationality * :");

        username = new JTextField();
        username.setToolTipText("Nom d'utilisateur");
        username.setFont(Util.fontTitle(15));

        password1 = new JPasswordField();
        password1.setFont(Util.fontTitle(20));
        password1.setToolTipText("Mot de Passe");
        password2 = new JPasswordField();
        password2.setFont(Util.fontTitle(20));
        password2.setToolTipText("Ressaisir le mot de passe");

        lastname = new JTextField();
        lastname.setToolTipText("Votre Nom");
        lastname.setFont(Util.fontTitle(15));

        firstname = new JTextField();
        firstname.setToolTipText("Votre prénom");
        firstname.setFont(Util.fontTitle(15));

        email = new JTextField();
        email.setToolTipText("Adresse e-mail");
        email.setFont(Util.fontTitle(14));


        Image scaleIcon;
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"saveUser.png").getImage();
        saveICO = new ImageIcon(scaleIcon.getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH));
        scaleIcon = new ImageIcon(Util.PATH_IMAGE +"check.png").getImage();
        checkICO = new ImageIcon(scaleIcon.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH));

        register = new JButton("Enregistrer", saveICO);
        register.setForeground(Color.WHITE);
        register.setBackground(Color.BLACK);
        link = new Link("Would you like to log in?");
    }

    public User getDataFormular (){
        User user = null;
        String username, firstname, lastname, password, email;

        if (this.firstname.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Enter a valid Firstname !");
        else{
            firstname = this.firstname.getText();
            if (this.lastname.getText().isEmpty())
                JOptionPane.showMessageDialog(this, "Enter a valid Lastname !");
            else{
                lastname = this.lastname.getText();
                if (this.username.getText().isEmpty())
                    JOptionPane.showMessageDialog(this, "Enter a valid Username !");
                else{
                    username = this.username.getText();
                    if (String.valueOf(this.password1.getPassword()).isEmpty())
                        JOptionPane.showMessageDialog(this, "Enter a valid Password !");
                    else{
                        password = String.valueOf(this.password1.getPassword());
                        if (!String.valueOf(this.password2.getPassword()).equals(password))
                            JOptionPane.showMessageDialog(this, "passwords are not identical !");
                        else{
                            if (!Util.isValidEmail(this.email.getText()))
                                JOptionPane.showMessageDialog(this, "Enter a valid Email !");
                            else{
                                email = this.email.getText();
                                user = new User(username, firstname, lastname, password, email, this.getLocale().getDisplayCountry(), null, null, false);
                            }
                        }
                    }
                }
            }
        }
        return user;
    }
}

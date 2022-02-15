package com.dagdevelop.workTime.model;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

public class Mail {

    private static Message m;

    public static void send(String from, String pwd, String to, String subject, String msg, String ...attachments) throws Exception{
        Properties p = getProperties("smtp.gmail.com", "587");
        Session s = getSession(p, from, pwd);

        if ((msg.toLowerCase().contains("<html>") && msg.toLowerCase().contains("</html>"))) {
            preparedHTMLMessage(s, from, to, subject, msg, attachments);
        } else {
            preparedMessageWithAttachment(s, from, to, subject, msg, attachments);
        }
        Transport.send(m);

    }

    public static void send(String from, String pwd, String to, String subject, String msg) throws Exception{
        Properties p = getProperties("smtp.gmail.com", "587");
        Session s = getSession(p, from, pwd);

        //Composer le message
        preparedSimpleMessage(s, from, to, subject, msg);
        javax.mail.Transport.send(m);
    }

    private static Session getSession(Properties p, String from, String pwd){
        //Session
        Session s = Session.getDefaultInstance(p, new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(from, pwd);
                    }
                }
        );
        return s;
    }
    private static Properties getProperties(String smtp, String port){
        //Propriétés
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", smtp);
        p.put("mail.smtp.port", port);
        p.put("mail.smtp.ssl.trust", smtp);

        return p;
    }

    private static void messageStructure(Session s, String from, String to, String subject) throws MessagingException {
        m = new MimeMessage(s);
        m.setFrom(new InternetAddress(from));
        m.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        m.setSubject(subject);

    }

    private static void preparedHTMLMessage(Session s, String from, String to, String subject, String htmlCode, String... attachments) throws MessagingException, IOException {
        messageStructure(s, from, to, subject);
        //m.setContent(htmlCode, "text/html");

        Multipart emailContent = new MimeMultipart();

        //-> Text body part
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setContent(htmlCode, "text/html");

        //-> Attach body part
        emailContent.addBodyPart(textBody, 0);
        addAttachments(emailContent, attachments);
        m.setContent(emailContent);

    }

    private static void addAttachments( Multipart emailContent, String... attachments) throws IOException, MessagingException {
        if (attachments != null){
            for (int i = 0; i < attachments.length; i++){
                MimeBodyPart attach = new MimeBodyPart();
                attach.attachFile(attachments[i]);
                emailContent.addBodyPart(attach, i + 1);
            }
        }
    }

    private static void preparedSimpleMessage(Session s, String from, String to, String subject, String msg) throws MessagingException{
        messageStructure(s, from, to, subject);
        m.setText(msg);

    }

    private static void preparedMessageWithAttachment(Session s, String from, String to, String subject, String msg, String... attachments) throws MessagingException, IOException {
        messageStructure(s, from, to, subject);

        //envoyer un mail avec pieces jointes
        Multipart emailContent = new MimeMultipart();

        //-> Text body part
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setText(msg);

        //-> Attach body part
        emailContent.addBodyPart(textBody, 0);
        addAttachments(emailContent, attachments);

        //attach multipart to msg
        m.setContent(emailContent);

    }

}


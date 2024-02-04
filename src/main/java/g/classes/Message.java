package g.classes;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Message {

    public static int sendVerificationCode(String toEmail) {
        int code = (int) (Math.random() * 89999) + 10000;
        String fromEmail = "anitolqyn@gmail.com";
        String password = "cfbrymbnrzxxfgzf";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + code);

            Transport.send(message);
            System.out.println("Verification code sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send verification code.");
        }
        return code;
    }

    public static int sendMessage(String toEmail, String title, String mess) {
        int code = (int) (Math.random() * 89999) + 10000;
        String fromEmail = "anitolqyn@gmail.com";
        String password = "cfbrymbnrzxxfgzf";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(title);
            message.setText(mess);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            Instruments.showAlert("Invalid email", "Check and rewrite email!!!", Alert.AlertType.ERROR);
        }
        return code;
    }
}

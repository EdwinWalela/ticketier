package com.company;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

public class Util {

    private static final String key = "ax%sdk Pls9_spTz";

    private static final String initVector = "encryptionINtVec";

    public static String encrypt(String value) throws Exception {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encrypted) throws Exception{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
    }

    public static void generateQR(String text,String filePath) throws WriterException,IOException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Encode payload to QR_CODE
        BitMatrix bitmatrix = qrCodeWriter.encode(text,BarcodeFormat.QR_CODE,350,350);
        // Create target path
        Path path = FileSystems.getDefault().getPath(filePath+=".png");
        // Write QRCODE to specified path
        MatrixToImageWriter.writeToPath(bitmatrix,"PNG",path);
        System.out.println("QR image successfully generated at "+filePath);
    }

    public static void takePic() throws IOException{
        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "PNG", new File("ticket.png"));
    }

    public static String readQRCode(String filepath) throws IOException,NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(
                        new BufferedImageLuminanceSource(
                                ImageIO.read(new FileInputStream(filepath+=".png"))
                        )
                ));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
        return qrCodeResult.getText();
    }

    public static String sha256(String payload) throws NoSuchAlgorithmException {
        // Get algorithim instance
        final MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        // Calculate Digest Value (returns byte array)
        byte[] result = mDigest.digest((payload+key).getBytes());
        // StringBuffer to store Hex Digest
        StringBuffer sb = new StringBuffer();
        // Convert Digest Value(byte array) to Hex
        for(int i = 0; i < result.length;i++){
            sb.append(Integer.toString((result[i] & 0xff) + 0x100,16).substring(1));
        }
        // Return Digest as Hex String
        return sb.toString();
    }

    public static void sendMail(EmailBody body) throws MessagingException{
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        String to = body.To;
        String from = "ticketier.info@gmail.com";
        String pass = "vitz2020";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, pass);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);
            msg.setSubject(body.subject);
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to,false));
            MimeMultipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<center><h1>Thank your for purchasing your ticket using Ticketier</h1>";
            htmlText += "<br/><br/><strong><h3>Event details</h3></strong><br/>";
            htmlText += "Event: "+body.ticket.eventName+"<br/>";
            htmlText += "Admits: "+body.ticket.pax+"<br/>";
            htmlText += "</center>";
            htmlText += "<br/><img src=\"cid:image\">";
            htmlText += "<em>This ticket has been automatically generated and can only be used once after which it will be no longer valid.</em>";
            htmlText += "<br/><br/><br/> <center>Ticketier ©2019<center/>";
            messageBodyPart.setContent(htmlText,"text/html");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String ticketType = body.ticket.type;
            DataSource fds = new FileDataSource("./tickets/"+ticketType+"-ticket.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");

            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);
            System.out.println("Message sent.");
    }
}

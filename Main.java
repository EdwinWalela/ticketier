package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;

public class Main {


    public static void main(String[] args) throws Exception {
//        // Ticket Generator
//        // Purchase ticket
//        String ticketDetails = "EDWINWALELA,ENDGAMEPREMIER2019,VIP,8";
//        // Encrypt ticket details
//        String secret = Util.encrypt(ticketDetails);
//        System.out.println(secret);
//        // Hash ticket encryption
//        String encryptionHash = Util.sha256(secret);
//        System.out.println(encryptionHash);
//        //: TODO Store hash to database
//        // Generate QR Code
//        Util.generateQR(secret,"./ticket");
//        // Scan QR Code
//        String ticketSecret = Util.readQRCode("./ticket");
//        // Decrypt QR contents
//        String initial = Util.decrypt(ticketSecret);
//        // Display ticket
//        System.out.println(initial);
//
//        //QR Scanner
//        JFrame f = new JFrame("QR Scanner");
//        f.setLayout(null);
//        f.setSize(800,800);
//        f.add(new ImageCanvas());
//        f.setVisible(true);
        DbConfig dbconfig = new DbConfig();
        Connection conn = dbconfig.connInit();
        Query query = new Query(conn);
        Date date = Date.valueOf("2019-5-3");
        query.createEvent(new Event(10001,"NAIROBI POTTERY","KICC","POTTERY MEETUP",date,600));
    }

}

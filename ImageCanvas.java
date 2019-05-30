package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

class ImageCanvas extends JPanel {

    public BufferedImage img;
    public ImageIcon icon;
    public JLabel lbl;
    public JLabel user;
    public JLabel event;
    public JLabel pax;
    public JButton btn;

    public ImageCanvas() throws Exception{
        img = ImageIO.read(new File("./ticket.png"));
        icon = new ImageIcon(img);
        lbl = new JLabel();
        user = new JLabel();
        event = new JLabel();
        pax  = new JLabel();

        btn = new JButton("Capture");
        lbl.setIcon(icon);
        lbl.setBounds(180,50,400,400);
        btn.setBounds(250,460,200,50);
        user.setBounds(150,550,500,20);
        user.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        event.setBounds(150,580,500,20);
        event.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        pax.setBounds(150,610,500,20);
        pax.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        add(lbl);
        add(btn);
        add(user);
        add(event);
        add(pax);
        setLayout(null);
        setSize(800,800);
        setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    redraw();
                }catch (Exception exc){

                }
            }
        });
    }

    public void redraw() throws Exception{
            Util.takePic();
            lbl.setIcon(new ImageIcon(ImageIO.read(new File("./ticket.png"))));
            String res = Util.decrypt(Util.readQRCode("./ticket"));
            btn.setText("Re-Take Photo");
            String out[] =res.split(",");
            user.setText("User: "+out[0]);
            event.setText("Event: "+out[1]);
            pax.setText("Pax: "+out[2]);

    }

}



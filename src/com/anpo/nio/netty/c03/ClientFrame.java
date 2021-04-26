package com.anpo.nio.netty.c03;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();

    TextArea textArea = new TextArea();
    TextField textField = new TextField();

    Client client = null;

    public ClientFrame() {
        this.setTitle("anpo 聊天室");
        this.setSize(600,400);
        this.setLocation(200,200);
        this.add(textArea,BorderLayout.CENTER);
        this.add(textField,BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.closeConnection();
                System.exit(0);
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(textField.getText());
//                textArea.setText(textArea.getText() + "\r\n" + textField.getText());
                textField.setText("");
            }
        });

//        this.setVisible(true);
//        connectToServer();
    }

    public void connectToServer() {
        client = new Client();
        client.connect();
    }



    public static void main(String[] args) throws InterruptedException {
        ClientFrame frame = ClientFrame.INSTANCE ;
        frame.setVisible(true);
        frame.connectToServer();
    }

    public void updateText(String msgAccepted) {
        this.textArea.setText(textArea.getText() + System.getProperty("line.separator") + msgAccepted);
    }



}

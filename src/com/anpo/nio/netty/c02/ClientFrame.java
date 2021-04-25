package com.anpo.nio.netty.c02;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    TextArea textArea = new TextArea();
    TextField textField = new TextField();

    public ClientFrame() throws InterruptedException {
        this.setTitle("anpo 聊天室");
        this.setSize(600,400);
        this.setLocation(200,200);
        this.add(textArea,BorderLayout.CENTER);
        this.add(textField,BorderLayout.SOUTH);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(textArea.getText() + textField.getText());
                textField.setText("");
            }
        });

        this.setVisible(true);
        new Client().connect();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        new ClientFrame();
    }


}

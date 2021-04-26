package com.anpo.nio.netty.c03;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    Button button = new Button("start Server");
    TextArea leftTextArea = new TextArea();
    TextArea rightTextArea = new TextArea();
    Server server = new Server();

    public ServerFrame() throws HeadlessException {
        this.setSize(1200,600);
        this.setLocation(30,40);
        this.add(button,BorderLayout.NORTH);
        Panel panel = new Panel(new GridLayout(1,2));
        panel.add(leftTextArea);
        panel.add(rightTextArea);
        this.add(panel);

        leftTextArea.setFont(new Font("verderna",Font.PLAIN,25));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }

    public void updateServerMsg(String string){
        this.leftTextArea.setText(leftTextArea.getText() + System.getProperty("line.separator") + string);
    }

    public void updateClientMsg(String string){
        this.rightTextArea.setText(rightTextArea.getText() + System.getProperty("line.separator") + string);
    }
}

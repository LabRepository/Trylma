package Trylma.client;

import javax.swing.*;

// TODO WRITE GUI FOR CLIENT
public class ClientGUI extends Client {
    private JFrame frame = new JFrame("Chinese Checkers");


    ClientGUI(){
        super();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 480);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        ClientGUI c = new ClientGUI();
    }
}

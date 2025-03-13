package com.example;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {
        super();
        setTitle("Main Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        WorkPanel workFrame = new WorkPanel();
        add(workFrame);
    }
}

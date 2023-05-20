package dev.zanckor.overlayspotify.common.util;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ScreenManager {
    public static void createButton(JFrame jFrame, ActionListener onPress, int xPos, int yPos, int width, int height, String title){
        JButton button = new JButton();
        button.addActionListener(onPress);
        jFrame.add(button);

        button.setSize(width, height);
        button.setLocation(xPos, yPos);
        button.setText(title);
    }
}

package net.scratch.space;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Scratch on 9/20/2014.
 */
public class Main {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static String VERSION = "Development-Preview v0.001";

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    public Main(){
        JFrame frame = new JFrame();
        frame.setTitle("Space Game - by ScratchForFun (9/20/2014)");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        frame.getContentPane().setCursor(blankCursor);


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        SCREEN_WIDTH = toolkit.getScreenSize().width;
        SCREEN_HEIGHT = toolkit.getScreenSize().height;


        Screen screen = new Screen(frame);
        frame.add(screen);
        frame.setVisible(true);
    }

}

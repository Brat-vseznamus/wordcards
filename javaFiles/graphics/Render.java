package javaFiles.graphics;

import javax.swing.*;
import java.awt.*;

public class Render extends JFrame {
    static int sizeWindowX = 1080;
    static int sizeWindowY = 720;

    final Color PASTELEGREEN = new Color(63,206,87);
    final Color PASTELERED = new Color(187,9,0);

    public Render(String title) throws HeadlessException {
        super(title);
        Card card1 = new Card("undefined state");

        getContentPane().add(card1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        // System.out.println("!");
        // Card card2 = new Card(700, 200);
        // getContentPane().add(card2);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        // pack();
    }

    public static void main(String[] args) {
        Render ds = new Render("Word cards");
        ds.setVisible(true);
    }

    public static double equation(double x, double y) {
        return x * y * (y - 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(sizeWindowX, sizeWindowY);
    }
}
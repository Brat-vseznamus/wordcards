package javaFiles.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Render extends JFrame {
    static int sizeWindowX = 1080;
    static int sizeWindowY = 720;
    Card card1 = new Card("a");
    int i = 0;
    public Render(String title) throws HeadlessException {
        super(title);
        JButton jb = new JButton("pip");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                card1.restart("a" + (++i));
                // card1 = new Card("undefined state");
                
            }

        });
        getContentPane().add(jb, BorderLayout.SOUTH);
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
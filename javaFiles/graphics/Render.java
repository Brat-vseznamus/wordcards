package javaFiles.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Render extends JFrame {
    static int sizeWindowX = 1080;
    static int sizeWindowY = 720;

    public Render(String title) throws HeadlessException {
        super(title);
        Card card1 = new Card();

        getContentPane().add(card1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        // Card card2 = new Card(100, 200);
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

    class Card extends JPanel implements ActionListener {
        Timer timer = new Timer(10, this);
        int x = 300, y = 200;
        int maxSizeX = 130;
        int maxSizeY = 200;
        int frameSize = 10;
        double maxSizeWithFrameX = frameSize + maxSizeX;
        double size = maxSizeX; 
        double sizeWithFrameX = maxSizeWithFrameX; 
        int angle = 0;
        int delta = -1;
        double cosPh = 1;

        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            boolean flip = ((90 + angle) / 180) % 2 == 0;
            size = cosPh * maxSizeX;
            sizeWithFrameX = cosPh * maxSizeWithFrameX;
            g.setColor(Color.BLACK);
            g.fillRect(x - (int)(sizeWithFrameX), y - frameSize, 2*(int)(sizeWithFrameX), 2*maxSizeY + 2 * frameSize);
            g.setColor(flip ? Color.RED : Color.GREEN);
            g.fillRect(x - (int)size, y, 2*(int)size, 2*maxSizeY);
            // https://stackoverflow.com/questions/13440201/how-to-resize-text-in-java
            if (angle == 180) {
                timer.stop();
            } else {
                timer.start();
            }
            Toolkit.getDefaultToolkit().sync();
        }

        public Dimension getPreferredSize() {
            return new Dimension(sizeWindowX, sizeWindowY);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            angle+= 1;
            // if (size == 100) {
            //     delta = -1;
            // } else if (size == 0) {
            //     delta = 1;
            // }
            // size += delta;
            cosPh = Math.abs(Math.cos((2 * Math.PI * angle) / 360));
            repaint();
        }

        public Card() {}

        public Card(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
package javaFiles.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
// import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;

class Card extends JPanel implements ActionListener {
    Timer timer = new Timer(10, this);
    int x = 200, y = 100;
    int maxSizeX = 144;
    int maxSizeY = 233;
    int frameSize = 10;
    int incAngle = 1;
    int circles = 0;
    double maxSizeWithFrameX = frameSize + maxSizeX;
    double size = maxSizeX;
    double sizeWithFrameX = maxSizeWithFrameX;
    int angle = 0;
    double cosPh = 1;
    String word;
    BufferedImage bfimg; 

    public BufferedImage resize(int targetWidth, int targetHeight,
            BufferedImage src) {
        double scaleW = (double) targetWidth / (double) src.getWidth();
        double scaleH = (double) targetHeight / (double) src.getHeight();
        BufferedImage result = new BufferedImage((int) (src.getWidth() * scaleW),
                (int) (src.getHeight() * scaleH), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(src, 0, 0, result.getWidth(), result.getHeight(), null);
        g2d.dispose();
        return result;
    }

    public BufferedImage createCardWithText(int targetWidth, int targetHeight,
            String src) {
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.setColor(Color.WHITE);
        int size = 30;
        g2d.setFont(new Font("Times New Roman", Font.BOLD, size));
        int width = g2d.getFontMetrics().stringWidth(src);
        // int height = g2d.getFontMetrics().getAscent();
        int delta = width / 2;
        int xCenter = result.getWidth() / 2;
        int yCenter = result.getHeight()/ 2;
        g2d.drawString(src, xCenter - delta, yCenter + size / 4);
        // g2d.setStroke(new BasicStroke(5));
        // g2d.setColor(Color.ORANGE);
        // g2d.drawLine(xCenter, yCenter, xCenter, yCenter);
        g2d.dispose();
        return result;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean flip = ((90 + angle) / 180) % 2 == 0;
        size = cosPh * maxSizeX;
        sizeWithFrameX = cosPh * maxSizeWithFrameX;
        g.setColor(Color.BLACK);
        int arcSize = 30;
        // g.fillRect(x - (int) (sizeWithFrameX), y - frameSize, 2 * (int) (sizeWithFrameX),
        //         2 * maxSizeY + 2 * frameSize);
        RoundRectangle2D rdBack = 
            new RoundRectangle2D.Float(
                x - (int) (sizeWithFrameX), 
                y - frameSize, 
                2 * (int) (sizeWithFrameX),
                2 * maxSizeY + 2 * frameSize, 
                arcSize, 
                arcSize);
        ((Graphics2D)g).fill(rdBack);
        // g.setColor(flip ? SourceColors.PASTELERED.color : SourceColors.PASTELEGREEN.color);
        Color from, to;
        
        if (flip) {
            from = SourceColors.PASTELE_RED_LIGHT.color;
            to = SourceColors.PASTELE_RED.color;
        } else {
            from = SourceColors.PASTELE_GREEN_LIGHT.color;
            to = SourceColors.PASTELE_GREEN.color;
        }
        ((Graphics2D)g).setPaint(new GradientPaint(
            x, y, from, 
            x, y + 2 * maxSizeY, to));
        RoundRectangle2D rdFront = 
            new RoundRectangle2D.Float(
                x - (int) size, 
                y, 
                2 * (int) size, 
                2 * maxSizeY, 
                arcSize, 
                arcSize);
        // g.fillRect(x - (int) size, y, 2 * (int) size, 2 * maxSizeY);
        // ((Graphics2D)g).draw(rd);
        ((Graphics2D)g).fill(rdFront);
        if ((int) size > 0) {
            BufferedImage bfimg2 = resize(2 * (int) size, 2 * maxSizeY, bfimg);
            g.drawImage(bfimg2, x - (int) size, y, null);
        }
        
        // https://stackoverflow.com/questions/13440201/how-to-resize-text-in-java
        if (angle % 180 == 90) {
            circles++;
            bfimg = createCardWithText(2 * (int) maxSizeX, 2 * maxSizeY,  Integer.toString(circles));
            timer.start();
            // timer.stop();
        } else {
            timer.start();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        angle += incAngle;
        cosPh = Math.abs(Math.cos((2 * Math.PI * angle) / 360));
        repaint();
    }

    public Card() {
        bfimg = createCardWithText(2 * (int) maxSizeX, 2 * maxSizeY,  "suka kavo");
    }

    public Card(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public Card(String word) {
        this.word = word;
        bfimg = createCardWithText(2 * (int) maxSizeX, 2 * maxSizeY,  word);
    }
}
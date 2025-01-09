/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.awt.Graphics2D;
import java.awt.Image; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Carlos Ballestas
 */
public class TestFotos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        File input = new File("/home/soluciones/Imágenes/550572-636247470560462813-16x9.jpg");
        BufferedImage image = ImageIO.read(input);

        BufferedImage resized = resize(image, 600, 600);

        File output = new File("/home/soluciones/Imágenes/duke-resized-500x500.png");
        ImageIO.write(resized, "png", output);

    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

}

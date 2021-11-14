package ru.netology.graphics.server;

import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {

    private double maxRatio;
    private int maxWidth;
    private int maxHeight;
    private TextColorSchema newSchema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        if (maxRatio != 0 & img.getWidth() / img.getHeight() > maxRatio) {
            throw new BadImageSizeException(img.getWidth() / img.getHeight(), maxRatio);
        }
        int k1;
        if (maxWidth != 0 & maxWidth < img.getWidth()) {
            k1 = img.getWidth() / maxWidth;
        } else {
            k1 = 0;
        }
        int k2;
        if (maxHeight != 0 & maxHeight < img.getHeight()) {
            k2 = img.getHeight() / maxHeight;
        } else {
            k2 = 0;
        }
        int newWidth;
        int newHeight;
        if (k1 != 0 | k2 != 0) {
            if (k1 >= k2) {
                newWidth = img.getWidth() / k1;
                newHeight = img.getHeight() / k1;
            } else {
                newWidth = img.getWidth() / k2;
                newHeight = img.getHeight() / k2;
            }
        } else {
            newWidth = img.getWidth();
            newHeight = img.getHeight();
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        char[][] text = new char[bwImg.getHeight()][bwImg.getWidth()];
        StringBuilder sb = new StringBuilder();
        Symbols schema = new Symbols();
        if (newSchema == null) {
            newSchema = schema;
        }
        int[] p = new int[3];
        for (int w = 0; w < bwImg.getHeight(); w++) {
            for (int h = 0; h < bwImg.getWidth(); h++) {
                int color = bwRaster.getPixel(h, w, p)[0];
                text[w][h] = newSchema.convert(color);
                sb.append(text[w][h]).append(text[w][h]).append(text[w][h]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        maxHeight = height;
    }

    @Override
    public void setMaxRatio(double ratio) {
        maxRatio = ratio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        newSchema = schema;
    }
}

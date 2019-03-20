package raster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImgBuffer implements Raster<Integer> {


    private BufferedImage img;

    public ImgBuffer(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }


    public void clear(int color) {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(color));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());

    }

    public BufferedImage getImg() {
        return img;
    }

    @Override
    public void set(int x, int y, Integer value) {
        img.setRGB(x, y, value);
    }

    @Override
    public Optional<Integer> get(int x, int y) {
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
            return Optional.of(img.getRGB(x, y));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }
}

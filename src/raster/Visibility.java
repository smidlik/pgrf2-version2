package raster;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Visibility {

    private ImgBuffer imgBuffer;
    private ZBuffer zBuffer;
    private int backgroundColor = Color.BLACK.getRGB();
    private int width;
    private int heigth;

    public Visibility(int width, int heigth) {
        this.heigth = heigth;
        this.width = width;
        this.imgBuffer = new ImgBuffer(width, heigth);
        this.zBuffer = new ZBuffer(width, heigth);
        clear();
    }

    public void put(int x, int y, double z, Col color) {
        Optional<Double> zValue = zBuffer.get(x, y);

        if (!zValue.isPresent()) {
            return;
        }
        if (z < zValue.get() && z >= 0) {
            imgBuffer.set(x, y, color.getRGB());
            zBuffer.set(x, y, z);
        }
    }

    public void clear() {
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                zBuffer.set(j, i, 1.0); //místo 1f je správně: new Float(1)
            }
        }
    }


    public BufferedImage getBufferedImage() {
        return imgBuffer.getImg();
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}

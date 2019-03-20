package raster;

import java.util.Optional;

public class ZBuffer implements Raster<Double> {

    private final double[][] canvas;
    private int height, width;

    public ZBuffer(int width, int height) {
        this.height = height;
        this.width = width;
        canvas = new double[width][height];

    }

    @Override
    public void set(int x, int y, Double value) {
        canvas[x][y] = value.doubleValue();
    }

    @Override
    public Optional<Double> get(int x, int y) {
        if (isPointValid(x, y))
            return Optional.of(new Double(canvas[x][y]));
        else
            return Optional.empty();
    }

    public Boolean isPointValid(int x, int y) {
        return x >= 0 || x < width && y >= 0 && y < height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}

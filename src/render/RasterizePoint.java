package render;

import model.Vertex;
import raster.Visibility;
import transforms.Col;

import java.awt.*;
import java.util.function.Function;

public class RasterizePoint {
    private Visibility visibility;


    public RasterizePoint(Visibility visibility) {
        this.visibility = visibility;
    }

    public void rasterize(Vertex a) {

        visibility.put(
                (int) a.getPosition().getX(),
                (int) a.getPosition().getY(),
                (float) a.getPosition().getZ(),
                a.getColor());

    }
    public void rasterize(Vertex a, Function<Vertex, Col> shader) {

        visibility.put(
                (int) a.getPosition().getX(),
                (int) a.getPosition().getY(),
                (float) a.getPosition().getZ(),
                a.getColor());

    }
}

package render;

import model.Vertex;
import raster.Visibility;
import transforms.Col;

import java.util.function.Function;

public class RasterizePoint {
    private final Function<Vertex, Col> shader;
    private Visibility visibility;


    public RasterizePoint(Visibility visibility, Function<Vertex, Col> shader) {
        this.visibility = visibility;
        this.shader = shader;

    }

    public void rasterize(Vertex a) {
        if (a.getPosition().getX() > 0 &&
                a.getPosition().getX() < visibility.getWidth() &&
                a.getPosition().getY() > 0 &&
                a.getPosition().getY() < visibility.getHeigth()
        )
            visibility.put(
                    (int) a.getPosition().getX(),
                    (int) a.getPosition().getY(),
                    (float) a.getPosition().getZ(),
                    a.getColor());

    }
}

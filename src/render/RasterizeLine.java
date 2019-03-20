package render;

import model.Vertex;
import raster.Visibility;
import transforms.Col;

import java.awt.*;
import java.util.function.Function;

public class RasterizeLine {
    private final Function<Vertex, Col> shader;
    Visibility vis;

    public RasterizeLine(Visibility vis, Function<Vertex, Col> shader) {
        this.vis = vis;
        this.shader = shader;

    }

    public void rasterize(Vertex a, Vertex b) {
        Graphics g = vis.getBufferedImage().getGraphics();
        g.setColor(new Color(b.getColor().getRGB()));

        g.drawLine(
                (int) a.getPosition().getX(), //X1
                (int) a.getPosition().getY(), //Y1
                (int) b.getPosition().getX(), //X2
                (int) b.getPosition().getY());//Y2

    }
}

package render;

import javafx.geometry.Point2D;
import model.Vertex;
import raster.Visibility;
import transforms.Col;
import util.Lerp;

import java.awt.*;
import java.util.function.Function;

public class RasterizeLine {
    Visibility vis;

    public RasterizeLine(Visibility vis) {
        this.vis = vis;
    }

    public void rasterize(Vertex a, Vertex b, Function<Vertex, Col> shader) {
        Graphics g = vis.getBufferedImage().getGraphics();
        g.setColor(new Color(b.getColor().getRGB()));

        g.drawLine(
                (int) a.getPosition().getX(), //X1
                (int) a.getPosition().getY(), //Y1
                (int) b.getPosition().getX(), //X2
                (int) b.getPosition().getY());//Y2

    }
   /* public void rasterize(Vertex a, Vertex b) {
        a = a.dehomog();
        b = b.dehomog();

        int z = 0;

        for(int y = Math.max((int)b.getPosition().getY(), 0); y < (int)b.getPosition().getY(); y++) {
            for (int x = Math.max((int) a.getPosition().getX(), 0); x < (int)b.getPosition().getX(); x++) {

                vis.put(x, y, z, new Col(0x0000ff));
            }
        }
    }

    private Point2D transformViewport(Vertex ver) {
        return new Point2D(((vis.getBufferedImage().getWidth() - 1) * ver.getPosition().getX() + 1) / 2,    //bod X
                ((vis.getBufferedImage().getWidth() - 1) * ver.getPosition().getY() + 1) / 2);              //bod Y
    }*/
}

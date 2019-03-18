package render;

import model.Vertex;
import raster.Visibility;
import transforms.Col;
import transforms.Point2D;
import util.Lerp;

import java.awt.*;
import java.util.function.Function;


public class RasterizeTriangle {
    Lerp<Vertex> lerp = new Lerp<>();
    Function<Vertex, Col> shader;
    Visibility vis;

    public RasterizeTriangle(Visibility vis) {
        this.vis = vis;
    }

    public void setShader(Function<Vertex, Col> shader) {
        this.shader = shader;
    }


    public void rasterize(Vertex a, Vertex b, Vertex c) {
        Graphics g = vis.getBufferedImage().getGraphics();
        g.setColor(new Color(shader.apply(b).getRGB()));
        int x1 = (int) a.getPosition().getX();
        int x2 = (int) b.getPosition().getX();
        int x3 = (int) c.getPosition().getX();
        int y1 = (int) a.getPosition().getY();
        int y2 = (int) b.getPosition().getY();
        int y3 = (int) c.getPosition().getY();


        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x3, y3, x2, y2);
        g.drawLine(x1, y1, x3, y3);

    }
}
    /*public void rasterize(Vertex a, Vertex b, Vertex c) {
        a = a.dehomog();
        b = b.dehomog();
        c = c.dehomog();

        //viewPort TRANSFORMATION
        Point2D pA = transformViewport(a);
        Point2D pB = transformViewport(b);
        Point2D pC = transformViewport(c);
        //další

        for (int y = (int) Math.max(pA.getY(), 0); y < pB.getY(); y++) {
            double s1 = (y - pA.getY()) / (pB.getY() - pA.getY());
            double s2 = 0;
            Vertex ab = lerp.lerp(a,b,s1);
            Vertex ac = lerp.lerp(a,c,s2);
            double x1 = 0;
            double x2 = 0;
            double z1 = 0;
            double z2 = 0;
            for (int x = Math.max((int) x1, 0); x < x2; x++) {
                float t = 0;
                float z = 0;

                Vertex abc = lerp.lerp(ab,ac,t);

                // Vykreslení bodu + shader, případně barva ....
                //vis.put(x, y, z, shader.apply(new Vertex()));
                vis.put(x, y, z, new Col(Color.WHITE.getRGB()));
            }
        }
    }*/

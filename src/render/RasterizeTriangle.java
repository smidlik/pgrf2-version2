package render;

import model.Vertex;
import raster.Visibility;
import transforms.Col;
import transforms.Vec3D;
import util.CalcMethod;
import util.Lerp;

import java.awt.*;
import java.util.function.Function;


public class RasterizeTriangle extends CalcMethod {
    Lerp<Vertex> lerp = new Lerp<>();
    Function<Vertex, Col> shader;
    Visibility vis;


    public RasterizeTriangle(Visibility vis, Function<Vertex, Col> shader) {
        this.vis = vis;
        this.shader = shader;
        // shader = (vertex) -> vertex.getColor().mul(1 / vertex.getPosition().getW());

    }


    public void rasterize(Vertex a, Vertex b, Vertex c, boolean isFilled, boolean isAxis) {
        if (isFilled)
            rasterizeFill(a, b, c, isAxis);
        else
            finalDraw(a, b, c);
    }

    private void rasterizeFill(Vertex a, Vertex b, Vertex c, boolean isAxis) {
        Vertex vA = a;
        Vertex vB = b;
        Vertex vC = c;

        // serazeni podle Y
        if (vA.getPosition().getY() > vB.getPosition().getY()) {
            Vertex vTemp = vA;
            vA = vB;
            vB = vTemp;
        }

        if (vB.getPosition().getY() > vC.getPosition().getY()) {

            Vertex vTemp = vB;
            vB = vC;
            vC = vTemp;
        }

        if (vA.getPosition().getY() > vB.getPosition().getY()) {

            Vertex vTemp = vA;
            vA = vB;
            vB = vTemp;
        }

        Vec3D vecA = vA.getPosition().ignoreW();
        Vec3D vecB = vB.getPosition().ignoreW();
        Vec3D vecC = vC.getPosition().ignoreW();

        // horni polovina
        for (int y = Math.max((int) (vecA.getY() + 1), 0); y <= Math.min(vecB.getY(), vis.getHeigth() - 1); y++) {
            //interpolacni koeficient
            double t = ((y - vecA.getY()) / (vecB.getY() - vecA.getY()));
            double t1 = ((y - vecA.getY()) / (vecC.getY() - vecA.getY()));
            // interpolace podle spojnice AB
            Vertex vAB = vA.mul(1.0 - t).add(vB.mul(t));
            Vertex vAC = vA.mul(1.0 - t1).add(vC.mul(t1));
            Vec3D vecAB = vecA.mul(1.0 - t).add(vecB.mul(t));
            Vec3D vecAC = vecA.mul(1.0 - t1).add(vecC.mul(t1));

            if (vecAC.getX() < vecAB.getX()) {
                Vec3D tempVec = vecAB;
                vecAB = vecAC;
                vecAC = tempVec;
            }

            for (int x = Math.max((int) vecAB.getX() + 1, 0); x <= Math.min(vecAC.getX(), vis.getWidth()); x++) {
                double tx = (x - vecAB.getX()) / (vecAC.getX() - vecAB.getX());
                //interpolacni hodnota z
                double z = vecAB.getZ() * (1.0 - tx) + vecAC.getZ() * tx;
                Vertex vABC = vAB.mul(1.0 - tx).add(vAC.mul(tx));
                fill(x, y, z, shader.apply(vABC));
            }
        }

        for (int y = Math.max((int) (vecB.getY() + 1), 0); y <= Math.min(vecC.getY(), vis.getHeigth() - 1); y++) {
            double t1 = ((y - vecB.getY()) / (vecC.getY() - vecB.getY()));
            double t2 = ((y - vecA.getY()) / (vecC.getY() - vecA.getY()));
            Vertex vBC = vB.mul(1 - t1).add(vC.mul(t1));
            Vertex vCA = vC.mul(1 - t2).add(vA.mul(t2));
            Vec3D vecBC = vecB.mul(1 - t1).add(vecC.mul(t1));
            Vec3D vecCA = vecA.mul(1 - t2).add(vecC.mul(t2));

            if (vecCA.getX() < vecBC.getX()) {
                Vec3D tempVec = vecBC;
                vecBC = vecCA;
                vecCA = tempVec;
            }

            for (int x = Math.max((int) vecBC.getX() + 1, 0); x <= Math.min(vecCA.getX(), vis.getWidth()); x++) {
                double tx = (x - vecBC.getX()) / (vecCA.getX() - vecBC.getX());
                double z = vecBC.getZ() * (1 - tx) + vecCA.getZ() * tx;
                Vertex vABC = vBC.mul(1 - tx).add(vCA.mul(tx));
                fill(x, y, z, shader.apply(vABC));
            }
        }
    }

    private void fill(int x, int y, double z, Col col) {
        if (y > 0 && y < vis.getHeigth()) {
            if (x > 0 && x < vis.getWidth())
                vis.put(x, y, z, col);
        }
    }

    private void finalDraw(Vertex a, Vertex b, Vertex c) {
        Graphics g = vis.getBufferedImage().getGraphics();
        g.setColor(new Color(shader.apply(a).getRGB()));

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
package model;

import transforms.Bicubic;
import transforms.Col;
import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;

public class Surface extends Solid {

    private Point3D[] controlPoints = new Point3D[16];   //matice řídících bodů

    public Surface() {
        controlPoints[0] = new Point3D(0, 0, 2);
        controlPoints[1] = new Point3D(1, 0, 0);
        controlPoints[2] = new Point3D(2, 0, 0);
        controlPoints[3] = new Point3D(3, 0, -2);

        controlPoints[4] = new Point3D(0, 1, 0);
        controlPoints[5] = new Point3D(1, 1, 0);
        controlPoints[6] = new Point3D(2, 1, 0);
        controlPoints[7] = new Point3D(3, 1, 0);

        controlPoints[8] = new Point3D(0, 2, 0);
        controlPoints[9] = new Point3D(1, 2, 0);
        controlPoints[10] = new Point3D(2, 2, 0);
        controlPoints[11] = new Point3D(3, 2, 0);

        controlPoints[12] = new Point3D(0, 3, 2);
        controlPoints[13] = new Point3D(1, 3, 0);
        controlPoints[14] = new Point3D(2, 3, 0);
        controlPoints[15] = new Point3D(3, 3, -2);


        Bicubic bc = new Bicubic(Cubic.BEZIER, controlPoints);
        int index = 0;
        for (float u = 0; u <= 1; u += 0.1) {
            for (float v = 0; v <= 1; v += 0.1) {
                vertexBuffer.add(new Vertex(bc.compute(u, v), new Col(Color.yellow.getRGB())));
                indexBuffer.add(index);
                index++;
            }
        }
        parts.add(new Part(Types.POINTS, 0, index));
    }
}


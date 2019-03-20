package model;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

import java.awt.*;


/**
 * Teleso Krychle
 */
public class Cube extends Solid {
    public Cube() {
        vertexBuffer.add(new Vertex(new Point3D(3, 1, -1), new Col(Color.pink.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(3, 3, -1), new Col(Color.pink.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));

        vertexBuffer.add(new Vertex(new Point3D(1, 3, -1), new Col(Color.pink.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, -1), new Col(Color.pink.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));

        vertexBuffer.add(new Vertex(new Point3D(3, 1, 3), new Col(Color.MAGENTA.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(3, 3, 3), new Col(Color.magenta.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));

        vertexBuffer.add(new Vertex(new Point3D(1, 3, 3), new Col(Color.magenta.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, 3), new Col(Color.magenta.getRGB()), new Vec3D(0, 0, -1), new Vec2D(0, 0)));

        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(7);

        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(7);

        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(4);

        indexBuffer.add(3);
        indexBuffer.add(7);
        indexBuffer.add(4);

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(5);

        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(5);

        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(6);

        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(6);

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(2);

        indexBuffer.add(5);
        indexBuffer.add(4);
        indexBuffer.add(7);

        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(7);
        parts.add(new Part(Types.TRIANGLES, 0, 12));
    }

}

package model;


import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

public class Pyramid extends Solid {
    public Pyramid() {

        vertexBuffer.add(new Vertex(new Point3D(2, 2, 4), new Col(0xffffff), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0.5), new Col(0xFFFF00), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 4, 0.5), new Col(0xFFFF00), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(4, 0, 0.5), new Col(0xFFFF00), new Vec3D(0, 0, -1), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(4, 4, 0.5), new Col(0xffffff), new Vec3D(0, 0, -1), new Vec2D(0, 0)));

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(3);

        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(4);

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(4);

        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);

        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);

        parts.add(new Part(Types.TRIANGLES, 0, 6));
    }
}
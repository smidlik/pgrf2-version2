package model;

import transforms.Point3D;

public class Arrow extends Solid{

    public Arrow(){
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0))); // 0
        vertexBuffer.add(new Vertex(new Point3D(1, 0, 0))); // 1

        vertexBuffer.add(new Vertex(new Point3D(2, 1, 0)));//2
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 0)));//3
        vertexBuffer.add(new Vertex(new Point3D(2, 0, 0)));//4

        // AXIS LINES
        indexBuffer.add(0);indexBuffer.add(1); // X axis

        // AXIS TRIANGLES
        indexBuffer.add(2);indexBuffer.add(3);indexBuffer.add(4); //RasterizeTriangle

        parts.add(new Part(Types.LINES,0,1));
        parts.add(new Part(Types.TRIANGLES,2,1));
    }
}

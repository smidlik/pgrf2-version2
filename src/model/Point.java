package model;


public class Point extends Solid {

    public Point(Vertex a){
        vertexBuffer.add(a);
        parts.add(new Part(Types.POINTS,0,1));

    }
}

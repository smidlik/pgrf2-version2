package model;


import transforms.Col;
import transforms.Point3D;

/**
 * Teleso Osy
 */
public class Axis extends Solid {

    public Axis() {

        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0)));                            //0
        vertexBuffer.add(new Vertex(new Point3D(5, 0, 0), new Col(0x00ff00)));    //1
        vertexBuffer.add(new Vertex(new Point3D(0, 5, 0), new Col(0x0000ff)));    //2
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 5), new Col(0xff0000)));    //3

        vertexBuffer.add(new Vertex(new Point3D(0.2, 0.2, 4.7), new Col(0xff0000)));//4
        vertexBuffer.add(new Vertex(new Point3D(-0.2, 0.2, 4.7), new Col(0xff0000)));//5
        vertexBuffer.add(new Vertex(new Point3D(0.2, -0.2, 4.7), new Col(0xff0000)));//6
        vertexBuffer.add(new Vertex(new Point3D(-0.2, -0.2, 4.7), new Col(0xff0000)));//7

        vertexBuffer.add(new Vertex(new Point3D(0.2, 4.7, 0.2), new Col(0x0000ff)));//8
        vertexBuffer.add(new Vertex(new Point3D(-0.2, 4.7, 0.2), new Col(0x0000ff)));//9
        vertexBuffer.add(new Vertex(new Point3D(0.2, 4.7, -0.2), new Col(0x0000ff)));//10
        vertexBuffer.add(new Vertex(new Point3D(-0.2, 4.7, -0.2), new Col(0x0000ff)));//11

        vertexBuffer.add(new Vertex(new Point3D(4.7, 0.2, 0.2), new Col(0x00ff00)));//12
        vertexBuffer.add(new Vertex(new Point3D(4.7, -0.2, 0.2), new Col(0x00ff00)));//13
        vertexBuffer.add(new Vertex(new Point3D(4.7, 0.2, -0.2), new Col(0x00ff00)));//14
        vertexBuffer.add(new Vertex(new Point3D(4.7, -0.2, -0.2), new Col(0x00ff00)));//15


        indexBuffer.add(0);
        indexBuffer.add(1);

        indexBuffer.add(0);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(3);
//ARROW Z
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(6); //RasterizeTriangle
        indexBuffer.add(7);
        indexBuffer.add(6);
        indexBuffer.add(5); //RasterizeTriangle
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(5); //RasterizeTriangle
        indexBuffer.add(3);
        indexBuffer.add(6);
        indexBuffer.add(7); //RasterizeTriangle
        indexBuffer.add(3);
        indexBuffer.add(7);
        indexBuffer.add(5); //RasterizeTriangle
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(6); //RasterizeTriangle
//ARROW Y
        indexBuffer.add(8);
        indexBuffer.add(9);
        indexBuffer.add(10); //RasterizeTriangle
        indexBuffer.add(11);
        indexBuffer.add(10);
        indexBuffer.add(9); //RasterizeTriangle
        indexBuffer.add(2);
        indexBuffer.add(8);
        indexBuffer.add(9); //RasterizeTriangle
        indexBuffer.add(2);
        indexBuffer.add(10);
        indexBuffer.add(11); //RasterizeTriangle
        indexBuffer.add(2);
        indexBuffer.add(11);
        indexBuffer.add(9); //RasterizeTriangle
        indexBuffer.add(2);
        indexBuffer.add(8);
        indexBuffer.add(10); //RasterizeTriangle
//ARROW X
        indexBuffer.add(12);
        indexBuffer.add(13);
        indexBuffer.add(14); //RasterizeTriangle
        indexBuffer.add(15);
        indexBuffer.add(14);
        indexBuffer.add(13); //RasterizeTriangle
        indexBuffer.add(1);
        indexBuffer.add(12);
        indexBuffer.add(13); //RasterizeTriangle
        indexBuffer.add(1);
        indexBuffer.add(14);
        indexBuffer.add(15); //RasterizeTriangle
        indexBuffer.add(1);
        indexBuffer.add(15);
        indexBuffer.add(13); //RasterizeTriangle
        indexBuffer.add(1);
        indexBuffer.add(12);
        indexBuffer.add(14); //RasterizeTriangle

        parts.add(new Part(Types.LINES, 0, 3));
        parts.add(new Part(Types.TRIANGLES, 6, 18));


    }

}

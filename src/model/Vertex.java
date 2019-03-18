package model;

import transforms.*;
import util.Vectorizable;

import java.awt.*;

public class Vertex implements Vectorizable<Vertex> {


    //Pozice
    private final Point3D position;
    //BARVA
    private final Col color;
    //Normálový vektor
    private final Vec3D normalVec;
    // Textury
    private final Vec2D texUV;
    private double one;


    public Vertex(Point3D point3D) {
        this.position = point3D;
        this.color = new Col(Color.yellow.getRGB()); //DEFAULT COLOR
        this.normalVec = new Vec3D(0, 0, -1);
        this.texUV = new Vec2D(0, 0);
    }
    public Vertex(Point3D point3D, Col color) {
        this.position = point3D;
        this.color = color;
        this.normalVec = new Vec3D(0, 0, -1);
        this.texUV = new Vec2D(0, 0);
    }

    public Vertex(Point3D point3D, Col color, Vec3D normalVec, Vec2D texUV) {
        this.position = point3D;
        this.color = color;
        this.normalVec = normalVec;
        this.texUV = texUV;
    }

    public Vertex(Point3D point3D, Col color, Vec3D normalVec, Vec2D texUV, double one) {
        this.position = point3D;
        this.color = color;
        this.normalVec = normalVec;
        this.texUV = texUV;
        this.one = one;
    }

    public Vertex(double x, double y, double z, Col color, Vec3D normalVec, Vec2D texUV) {
        this.position = new Point3D(x, y, z);
        this.color = color;
        this.normalVec = normalVec;
        this.texUV = texUV;
    }


    public Col getColor() {
        return color;
    }


    public Point3D getPosition() {
        return position;
    }

    @Override
    public Vertex mul(double t) {
        Vertex calculation = new Vertex(new Point3D(
                position.getX() * t,
                position.getY() * t,
                position.getZ() * t),
                this.color,
                this.normalVec,
                this.texUV);
        return calculation;
    }

    public Vertex mul(Mat4 matrix) {
        Point3D point = this.position.mul(matrix);
        Vertex newVertex = new Vertex(point, this.color, this.normalVec, this.texUV);
        return newVertex;
    }


    @Override
    public Vertex add(Vertex a) {
        Vertex calculation = new Vertex(
                new Point3D(
                        position.getX() + a.getPosition().getX(),
                        position.getY() + a.getPosition().getY(),
                        position.getY() + a.getPosition().getZ()),
                this.color,
                this.normalVec,
                this.texUV);
        return calculation;
    }

    public Vertex dehomog() {
        return this.mul(1 / position.getW());
    }

    public Vec3D getNormalVec() {
        return normalVec;
    }

    public Vec2D getTexUV() {
        return texUV;
    }

    public double getOne() {
        return one;
    }
}

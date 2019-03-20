package model;

import transforms.*;
import util.Vectorizable;

import java.awt.*;

public class Vertex implements Vectorizable<Vertex> {


    //BARVA
    private final Col color;
    //Normálový vektor
    private final Vec3D normalVec;
    // Textury
    private final Vec2D texUV;
    //Pozice
    private Point3D position;
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

    public void setPosition(Point3D position) {
        this.position = position;
    }

    @Override
    public Vertex mul(double t) {
        return new Vertex(getPosition().mul(t), getColor() == null ? null : getColor().mul(t), getNormalVec().mul(t),
                getTexUV().mul(t), one * t);
    }

    public Vertex mul(Mat4 matrix) {
        return new Vertex(position.mul(matrix), this.color, this.normalVec, this.texUV);
    }


    @Override
    public Vertex add(Vertex b) {
        return new Vertex(getPosition().add(b.getPosition()),
                getColor() == null || b.getColor() == null ? null : getColor().add(b.getColor()),
                getNormalVec().add(b.getNormalVec()), getTexUV().add(b.getTexUV()), one * b.getOne());
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

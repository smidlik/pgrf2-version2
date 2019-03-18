package model;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

    protected Mat4Identity model;
    protected List<Vertex> vertexBuffer;
    protected List<Integer> indexBuffer;
    protected List<Part> parts;

    public Solid(){
        vertexBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
        parts = new ArrayList<>();
        model = new Mat4Identity();
    }


    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Part> getParts() {
        return parts;
    }

    public Mat4Identity getModel() {
        return model;
    }
}

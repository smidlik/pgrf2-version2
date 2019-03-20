package render;

import model.Axis;
import model.Part;
import model.Solid;
import model.Vertex;
import raster.Visibility;
import transforms.*;
import util.CalcMethod;
import util.Lerp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Renderer extends CalcMethod {


    private final Function<Vertex, Col> shader;
    private Mat4 viewMatrix, modelMatrix, projeMatrix;
    private RasterizeTriangle rRasterizeTriangle;
    private RasterizePoint rPoint;
    private RasterizeLine rRasterizeLine;
    private Visibility visibility;
    private List<Solid> solidList;
    private boolean isFilled;
    private boolean isAxis;
    private Lerp<Vertex> lerp = new Lerp();

    public Renderer(Visibility visibility, Function<Vertex, Col> shader) {
        this.visibility = visibility;
        this.shader = shader;
        this.rRasterizeTriangle = new RasterizeTriangle(visibility, shader);
        this.rPoint = new RasterizePoint(visibility, shader);
        this.rRasterizeLine = new RasterizeLine(visibility, shader);
        solidList = new ArrayList<>();


        this.modelMatrix = new Mat4Identity();
        this.viewMatrix = new Mat4Identity();
        this.projeMatrix = new Mat4Identity();

    }

    public void render() {
        for (Solid solid : solidList) {
            Mat4 matFinal;
            for (Part part : solid.getParts()) {
                if (solid instanceof Axis) {
                    matFinal = viewMatrix.mul(projeMatrix);
                    isAxis = true;
                } else {
                    matFinal = modelMatrix.mul(viewMatrix).mul(projeMatrix);
                    isAxis = false;
                }

                switch (part.getType()) {
                    case LINES:
                        for (int i = 0; i < part.getCount(); i++) {
                            Vertex a, b;
                            a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(2 * i + part.getStart()));
                            b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(2 * i + part.getStart() + 1));
                            renderLine(a, b, matFinal);
                        }
                        break;
                    case TRIANGLES:
                        for (int i = 0; i < part.getCount(); i++) {
                            Vertex a, b, c;

                            a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart()));
                            b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 1));
                            c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 2));
                            prepareTriangle(a, b, c, matFinal);

                        }
                        break;
                    case POINTS:
                        for (Vertex vertex : solid.getVertexBuffer()) {
                            renderPoint(vertex, matFinal);
                        }
                        break;
                    case LINE_LOOP:

                        break;
                    case LINE_STRIP:

                        break;
                    case TRIANGL_FAN:

                        break;
                    case TRIANGL_STRIP:

                        break;
                }
            }

        }
    }


    private void renderLine(Vertex a, Vertex b, Mat4 mat) {

        a = a.mul(mat);
        b = b.mul(mat);


        if (!a.getPosition().dehomog().isPresent() || !b.getPosition().dehomog().isPresent()) {
            return;
        }
        // ořezání
        if (cutter(a)) {
            Vec3D vectorA = a.getPosition().dehomog().get();
            Vec3D vectorB = b.getPosition().dehomog().get();

            // ViewPort
            Vertex vertexA = new Vertex(
                    new Point3D(transformViewport(vectorA, visibility)),
                    a.getColor(), a.getNormalVec(), a.getTexUV());

            Vertex vertexB = new Vertex(
                    new Point3D(transformViewport(vectorB, visibility)),
                    b.getColor(), b.getNormalVec(), b.getTexUV());
            rRasterizeLine.rasterize(vertexA, vertexB);

        }

    }

    private void renderPoint(Vertex a, Mat4 mat) {
///Násobení maticí
        a = a.mul(mat);

        if (!a.getPosition().dehomog().isPresent()) {
            return;
        }
        // ořezání
        if (cutter(a)) {
            Vec3D va = a.getPosition().dehomog().get();

            // ViewPort
            //va = transformViewport(va,visibility);

            Vertex vertexA = new Vertex(
                    new Point3D(transformViewport(va, visibility)),
                    a.getColor(), a.getNormalVec(), a.getTexUV());

            rPoint.rasterize(vertexA);
        }
    }


    private void prepareTriangle(Vertex a, Vertex b, Vertex c, Mat4 mat) {
        a = a.mul(mat);
        b = b.mul(mat);
        c = c.mul(mat);
        if (!a.getPosition().dehomog().isPresent() || !b.getPosition().dehomog().isPresent()
                || !c.getPosition().dehomog().isPresent())
            return;
        // neregulerni trojuhelnik(w=0), zahodime

        // Seřazení A<B<C
        if (a.getPosition().getZ() < b.getPosition().getZ()) {
            Vertex pom = a;
            a = b;
            b = pom;
        }
        if (b.getPosition().getZ() < c.getPosition().getZ()) {
            Vertex pom = b;
            b = c;
            c = pom;
        }
        if (a.getPosition().getZ() < c.getPosition().getZ()) {
            Vertex pom = a;
            a = c;
            c = pom;
        }
        //Je vidět celý trojuhelník
        if (c.getPosition().getZ() >= 0) {
            renderTriangle(a, b, c);
            return;
        }

        double t1 = tCalc(b, c);
        double t2 = tCalc(a, c);
        double t3 = tCalc(a, b);
        double t4 = tCalc(a, c);
        // Bod C je pod 0
        if (b.getPosition().getZ() > 0) {


            //Výpočet interpolace
            Vertex ab = lerp.lerp(b, a, t1);
            Vertex ac = lerp.lerp(c, a, t2);

            //Rasterizace
            renderTriangle(a, b, ac);
            renderTriangle(a, ab, ac);
            return;

        }

        //Poze bod A jen nad nulou
        if (a.getPosition().getZ() > 0) {

            Vertex ac = lerp.lerp(c, a, t2);
            Vertex ab = lerp.lerp(b, a, t3);

            renderTriangle(a, ac, ab);
            return;
        }

    }

    private void renderTriangle(Vertex a, Vertex b, Vertex c) {
        a = a.dehomog();
        b = b.dehomog();
        c = c.dehomog();


        a.setPosition(new Point3D(transformViewport(a.getPosition().ignoreW(), visibility)));
        b.setPosition(new Point3D(transformViewport(b.getPosition().ignoreW(), visibility)));
        c.setPosition(new Point3D(transformViewport(c.getPosition().ignoreW(), visibility)));


        rRasterizeTriangle.rasterize(a, b, c, isFilled, isAxis);
    }


    // výpočet interpolace
    /*private double tCalc(Vertex a, Vertex b){
        return ((0 - b.getPosition().getZ()) / (a.getPosition().getZ() - b.getPosition().getZ()));
    }*/

    /*public Vec3D transformViewport(Vec3D vec3D) {
        return vec3D.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((visibility.getBufferedImage().getWidth() - 1) / 2.0, (visibility.getBufferedImage().getHeight() - 1) / 2.0, 1));
    }*/

    public boolean cutter(Vertex a) {
        return (-a.getPosition().getW() <= a.getPosition().getX() ||
                a.getPosition().getY() <= a.getPosition().getW() ||
                0 <= a.getPosition().getZ() ||
                a.getPosition().getZ() <= a.getPosition().getW());
    }


    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public Mat4 getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Mat4 viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Mat4 modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public Mat4 getProjeMatrix() {
        return projeMatrix;
    }

    public void setProjeMatrix(Mat4 projeMatrix) {
        this.projeMatrix = projeMatrix;
    }

    public void initSolids() {
        solidList = new ArrayList<>();
    }

    public void addSolid(Solid solid) {
        this.solidList.add(solid);
    }

}


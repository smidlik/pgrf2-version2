package render;

import model.Part;
import model.Solid;
import model.Vertex;
import raster.Visibility;
import transforms.*;
import util.Lerp;

import java.util.function.Function;

public class Renderer {


    private Mat4 viewMatrix, modelMatrix, projeMatrix;
    private RasterizeTriangle rRasterizeTriangle;
    private RasterizePoint rPoint;
    private RasterizeLine rRasterizeLine;
    private Visibility visibility;
    private Mat4 matFinal;
    private Lerp<Vertex> lerp = new Lerp();
    private final Function<Vertex, Col> shader;


    public Renderer(Visibility visibility, Function<Vertex, Col> shader) {
        this.visibility = visibility;
        this.rRasterizeTriangle = new RasterizeTriangle(visibility);
        this.rPoint = new RasterizePoint(visibility);
        this.rRasterizeLine = new RasterizeLine(visibility);
        this.shader = shader;


        this.modelMatrix = new Mat4Identity();
        this.viewMatrix = new Mat4Identity();
        this.projeMatrix = new Mat4Identity();

    }

    public void render(Solid solid) {
        matFinal = modelMatrix.mul(viewMatrix).mul(projeMatrix);
        for (Part part : solid.getParts()) {
            switch (part.getType()) {
                case LINES:
                    for (int i = 0; i < part.getCount()-1; i+=2) {
                        Vertex a, b;
                        a = solid.getVertexBuffer().get(solid.getIndexBuffer().get( i * part.getCount()));
                        b = solid.getVertexBuffer().get(solid.getIndexBuffer().get( i * part.getCount() + 1));
                        renderLine(a, b);
                    }
                    break;
                case TRIANGLES:
                    for (int i = 0; i < part.getCount(); i++) {
                        Vertex a, b, c;

                        a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart()));
                        b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 1));
                        c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 2));
                        renderTriangle(a, b, c);
                    }
                    break;
                case POINTS:
                    for (Vertex vertex : solid.getVertexBuffer()) {
                        renderPoint(vertex);
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


    private void renderLine(Vertex a, Vertex b) {
        if (!a.getPosition().dehomog().isPresent() || !b.getPosition().dehomog().isPresent()) {
            return;
        }
        // orez
        if(-a.getPosition().getW()<=a.getPosition().getX()||a.getPosition().getY()<=a.getPosition().getW()||0<=a.getPosition().getZ()||a.getPosition().getZ()<=a.getPosition().getW())
        {
            // 4D do 3D
            Vec3D va = a.getPosition().dehomog().get();
            Vec3D vb = b.getPosition().dehomog().get();

            // ViewPort
            va = transformViewport(va);
            vb = transformViewport(vb);




            Vertex vertexA=new Vertex(new Point3D(va.getX(),va.getY(),va.getZ()), a.getColor(), a.getNormalVec(), a.getTexUV());
            Vertex vertexB=new Vertex(new Point3D(vb.getX(),vb.getY(),vb.getZ()), b.getColor(), b.getNormalVec(), b.getTexUV());
            rRasterizeLine.rasterize(vertexA, vertexB ,shader);

        }

    }

    private void renderPoint(Vertex a) {
///Násobení maticí
        if (!a.getPosition().dehomog().isPresent()) {
            return;
        }
        // orez
        if(-a.getPosition().getW()<=a.getPosition().getX()||a.getPosition().getY()<=a.getPosition().getW()||0<=a.getPosition().getZ()||a.getPosition().getZ()<=a.getPosition().getW())
        {
            // 4D do 3D
            Vec3D va = a.getPosition().dehomog().get();

            // ViewPort
            va = transformViewport(va);

            Vertex vertexA=new Vertex(new Point3D(va.getX(),va.getY(),va.getZ()), a.getColor(), a.getNormalVec(), a.getTexUV());
            rPoint.rasterize(vertexA);

        }
    }


    private void renderTriangle(Vertex a, Vertex b, Vertex c) {
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

        if (a.getPosition().getZ() < 0) {
            return;
        }
        if (b.getPosition().getZ() < 0) {
            double t1 = -b.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());

            //Výpočet interpolace
            Vertex ab = lerp.lerp(b, a, t1);
            Vertex ac = lerp.lerp(c, a, t1);

            //Rasterizace
            rRasterizeTriangle.rasterize(a, ab, ac);
            rRasterizeTriangle.rasterize(a, b, ac);

        }
        if (c.getPosition().getZ() < 0) {
            double t2 = -b.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());

            Vertex bc = lerp.lerp(c, b, t2);

            rRasterizeTriangle.rasterize(bc, b, a);
        }
        if (c.getPosition().getZ() >= 0) {
            rRasterizeTriangle.rasterize(a, b, c);
        }

    }

    public Vec3D transformViewport(Vec3D vec3D){
        return vec3D.mul(new Vec3D(1,-1,1)).add(new Vec3D(1,1,0))
                .mul(new Vec3D((visibility.getBufferedImage().getWidth()-1)/2.0,(visibility.getBufferedImage().getHeight()-1)/2.0,1));
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
}


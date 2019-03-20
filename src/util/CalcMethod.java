package util;

import model.Vertex;
import raster.Visibility;
import transforms.Vec3D;

public abstract class CalcMethod {

    public Vec3D transformViewport(Vec3D vec3D, Visibility vis) {
        return vec3D.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((vis.getBufferedImage().getWidth() - 1) / 2.0, (vis.getBufferedImage().getHeight() - 1) / 2.0, 1));
    }

    public double tCalc(Vertex a, Vertex b) {
        return ((0 - b.getPosition().getZ()) / (a.getPosition().getZ() - b.getPosition().getZ()));
    }           //((y - vecA.getY()) / (vecB.getY() - vecA.getY()));
}

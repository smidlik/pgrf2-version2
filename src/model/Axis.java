package model;



import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

/**
 * Teleso Osy
 *
 */
public class Axis extends Solid {
	
	public Axis()
	{

		vertexBuffer.add(new Vertex(new Point3D(0, 0, 0), new Col(0xff0000)));
		vertexBuffer.add(new Vertex(new Point3D(5, 0, 0), new Col(0x00ff00)));
		vertexBuffer.add(new Vertex(new Point3D(0, 5, 0), new Col(0x0000ff)));
		vertexBuffer.add(new Vertex(new Point3D(0, 0, 5), new Col(0xff0000)));


		
		indexBuffer.add(0);
		indexBuffer.add(1);

		indexBuffer.add(0);
		indexBuffer.add(2);

		indexBuffer.add(0);
		indexBuffer.add(3);
	
		
		parts.add(new Part(Types.LINES, 0,3));
		
		
	}

}

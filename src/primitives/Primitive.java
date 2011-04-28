package primitives;

import materials.Material;
import common.Hit;
import common.Ray;
import common.Vector;

public abstract class Primitive {
	public Material material;
	
	public abstract void intersect(Ray ray, Hit hit);
	public abstract Vector getNormal(Hit hit);
	public abstract Vector generatePoint(float r1, float r2);
}

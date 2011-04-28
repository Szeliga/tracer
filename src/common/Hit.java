package common;

import primitives.Primitive;

public class Hit {
	public float t;
	public Primitive object;
	public boolean isHit;
	public Vector iP;
	public Vector normal;
	public Ray ray;
	
	public Hit() {
		t = Float.POSITIVE_INFINITY;
		object = null;
		isHit = false;
		iP = new Vector();
		normal = new Vector();
		ray = null;
	}
}

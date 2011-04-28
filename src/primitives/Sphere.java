package primitives;

import materials.Material;

import common.Hit;
import common.Ray;
import common.Vector;

public class Sphere extends Primitive {

	public Vector o;
	public float r;
	public float rInv;
	

	public Sphere(Vector o, float r, Material material) {
		this.o = o;
		this.r = r;
		this.rInv = 1.0f / r;
		this.material = material;
	}

	public void intersect(Ray ray, Hit hit) {
		Vector distance;
		distance = this.o.sub(ray.origin);
		float B = ray.direction.dot(distance);
		float delta = B * B - distance.dot(distance) + this.r * this.r;
		if (delta < 0.0f) {
			return;
		}
		float deltaSqrt = (float) Math.sqrt(delta);
		float t1 = B + deltaSqrt;
		if (t1 < 0.0f) {
			return;
		}
		float t0 = B - deltaSqrt;
		float t = t0 < 0.0f ? t1 : t0;
		if (t < hit.t) {
			hit.t = t;
			hit.object = this;
			hit.isHit = true;
			hit.iP = ray.origin.add(ray.direction.mul(hit.t));
			hit.normal = this.getNormal(hit);
			return;
		}
		return;
	}

	@Override
	public Vector getNormal(Hit hit) {
		return hit.iP.sub(this.o).mul(this.rInv);
	}

	@Override
	public Vector generatePoint(float r1, float r2) {
		float phi = (float) (2.0f * Math.PI * r1);
		float root = (float) Math.sqrt(r2 * (1.0f - r2));
		float x = (float) (2.0f * this.r * Math.cos(phi) * root);
		float y = (float) (2.0f * this.r * Math.sin(phi) * root);
		float z = this.r * (1.0f - 2.0f * r2);
		return this.o.add(new Vector(x, y, z));
	}

}

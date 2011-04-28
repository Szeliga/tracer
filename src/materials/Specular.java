package materials;

import common.Color;
import common.Hit;
import common.Vector;
import common.Xorshift;

public class Specular extends Material {
	
	public Specular(Color emitance, Color reflectance, float s) {
		this.emittance = emitance;
		this.reflectance = reflectance;
		this.s = s;
	}

	@Override
	public Vector newDirection(Xorshift rnd, Hit record, float s, boolean spec) {
		spec = true;
		float r1 = rnd.getFloat();
		float r2 = rnd.getFloat();
		float phi = (float) (2.0f * Math.PI * r1);
		float sqr1 = (float) Math.pow(r2, 2.0f / (s + 1.0f));
		float sqr2 = (float) Math.pow(r2, 1.0f / (s + 1.0f));
		float root = (float) Math.sqrt(1.0f - sqr1);
		float x = (float) (Math.cos(phi) * root);
		float y = (float) (Math.sin(phi) * root);
		float z = sqr2;
		Vector r = record.ray.direction.sub(record.normal
				.mul(record.ray.direction.dot(record.normal) * 2.0f));
		Vector tmp;
		if (Math.abs(r.x) > 0.9f) {
			tmp = new Vector(0.0f, 1.0f, 0.0f);
		} else {
			tmp = new Vector(1.0f, 0.0f, 0.0f);
		}
		Vector up = tmp.cross(r).norm();
		Vector dir = up.mul(x).add(r.cross(up).mul(y))
				.add(r.mul(z));
		return dir;
	}
}

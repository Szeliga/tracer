package materials;

import common.Color;
import common.Hit;
import common.Vector;
import common.Xorshift;

public class Lambert extends Material {

	public Lambert(Color emitance, Color reflectance) {
		this.emittance = emitance;
		this.reflectance = reflectance;
		this.s = 1.0f;
	}

	@Override
	public Vector newDirection(Xorshift rnd, Hit record, float s, boolean cel,
			Color ret) {
		float r1 = rnd.getFloat();
		float r2 = rnd.getFloat();
		float phi = (float) (2.0f * Math.PI * r1);
		float root = (float) Math.sqrt(1.0f - r2);
		float x = (float) (Math.cos(phi) * root);
		float y = (float) (Math.sin(phi) * root);
		float z = (float) Math.sqrt(r2);
		Vector tmp;
		if (Math.abs(record.normal.x) > 0.9f) {
			tmp = new Vector(0.0f, 1.0f, 0.0f);
		} else {
			tmp = new Vector(1.0f, 0.0f, 0.0f);
		}
		Vector up = tmp.cross(record.normal).norm();
		Vector dir = up.mul(x).add(record.normal.cross(up).mul(y))
				.add(record.normal.mul(z));
		ret.mulThis(0.318309886184f);
		return dir;
	}
}

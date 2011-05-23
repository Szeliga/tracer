package materials;

import common.Color;
import common.Hit;
import common.Vector;
import common.Xorshift;

public class DiffuseSpecular extends Material {

	Lambert diff = new Lambert(this.emittance, this.reflectance);
	Specular spec = new Specular(this.emittance, this.reflectance, this.getS());

	public DiffuseSpecular(Color emitance, Color reflectance, float s) {
		this.emittance = emitance;
		this.reflectance = reflectance;
		this.s = s;
	}

	@Override
	public Vector newDirection(Xorshift rnd, Hit record, float s, boolean cel,
			Color ret, boolean isSpec) {
		float cosine = record.ray.direction.dot(record.normal);
		if (cosine < 0.0f)
			cosine = -cosine;
		float cos = 1.0f - cosine;
		float p = 0.7f;
		float ks = 0.2f;
		// float R = R0 + (1.0f - R0) * cos * cos * cos * cos * cos;
		// float P = (R + 0.5f) / 2.0f;
		if (rnd.getFloat() <= p) {
			ret.mulThis(ks / p);
			return spec
					.newDirection(rnd, record, this.getS(), cel, ret, isSpec);
		} else {
			ret.mulThis((1.0f - ks / (1.0f - p)));
			return diff
					.newDirection(rnd, record, this.getS(), cel, ret, isSpec);
		}
	}

}

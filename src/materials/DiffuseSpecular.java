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
	public Vector newDirection(Xorshift rnd, Hit record, float s, boolean specc) {
		float cosine = record.ray.direction.dot(record.normal);
		if (cosine < 0.0f)
			cosine = -cosine;
		float cos = 1.0f - cosine;
		float R0 = 0.05f;
		float R = R0 + (1.0f - R0) * cos * cos * cos * cos * cos;
		float P = (R + 0.5f) / 2.0f;
		if (rnd.getFloat() <= P) {
			return spec.newDirection(rnd, record, this.getS(), specc);
		} else {
			return diff.newDirection(rnd, record, this.getS(), specc);
		}
	}

}

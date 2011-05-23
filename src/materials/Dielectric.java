package materials;

import common.Color;
import common.Hit;
import common.Vector;
import common.Xorshift;

public class Dielectric extends Material {
	
	public float nt;
	
	public Dielectric(Color emitance, Color reflectance, float nt) {
		this.emittance = emitance;
		this.reflectance = reflectance;
		this.s = 1.0f;
		this.nt = nt;
	}

	@Override
	public Vector newDirection(Xorshift rnd, Hit record, float s, boolean spec,
			Color ret, boolean isSpec) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Vector getTransmittedRay(Hit record) {
		float cos = record.ray.direction.dot(record.normal);
		if (cos < 0.0f) {
			// Incoming ray
			float tmp1 = 1.0f / nt;
			cos = -cos;
			float root = 1.0f - (tmp1 * tmp1) * (1.0f - cos * cos);
			
		}
		return null;
	}

}

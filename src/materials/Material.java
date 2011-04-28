package materials;

import common.Color;
import common.Hit;
import common.Vector;
import common.Xorshift;

public abstract class Material {
	public Color emittance;
	public Color reflectance;
	public float s;

	public abstract Vector newDirection(Xorshift rnd, Hit record, float s,
			boolean spec);

	public boolean isLight() {
		if (emittance.r > 0.0f || emittance.g > 0.0f || emittance.b > 0.0f) {
			return true;
		}
		return false;
	}

	public float getS() {
		return s;
	}

}

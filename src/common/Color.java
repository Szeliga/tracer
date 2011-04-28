package common;

public class Color {

	public float r, g, b;
	
	public Color() {
		this.r = this.g = this.b = 0.0f;
	}

	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color add(Color arg) {
		return new Color(this.r + arg.r, this.g + arg.g, this.b + arg.b);
	}

	public Color mul(float a) {
		return new Color(this.r * a, this.g * a, this.b * a);
	}

	public Color mul(Color arg) {
		return new Color(this.r * arg.r, this.g * arg.g, this.b * arg.b);
	}

	public Color addThis(Color arg) {
		this.r += arg.r;
		this.g += arg.g;
		this.b += arg.b;
		return this;
	}

	public Color addThis(float a) {
		this.r += a;
		this.g += a;
		this.b += a;
		return this;
	}

	public Color mulThis(Color arg) {
		this.r *= arg.r;
		this.g *= arg.g;
		this.b *= arg.b;
		return this;
	}

	public Color mulThis(float a) {
		this.r *= a;
		this.g *= a;
		this.b *= a;
		return this;
	}

	public int toInt() {
		int color = 0;
		float rCorr = (float) Math.pow(this.r, 1.0f / 2.2f);
		float gCorr = (float) Math.pow(this.g, 1.0f / 2.2f);
		float bCorr = (float) Math.pow(this.b, 1.0f / 2.2f);
		int rInt = (int) ((rCorr > 1.0f ? 1.0f : rCorr) * 255.0f);
		int gInt = (int) ((gCorr > 1.0f ? 1.0f : gCorr) * 255.0f);
		int bInt = (int) ((bCorr > 1.0f ? 1.0f : bCorr) * 255.0f);
		color = (rInt << 16) + (gInt << 8) + bInt;
		return color;
	}
	
	@Override
	public String toString() {
		return "r: " + this.r + " g: " + this.g + " b: " + this.b;
	}

}

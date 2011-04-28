package common;

public class Vector {

	public float x, y, z;
	
	public Vector() {
		this.x = this.y = this.z = 0.0f;
	}

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public Vector add(Vector v) {
		return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	public Vector add(float a) {
		return new Vector(this.x + a, this.y + a, this.z + a);
	}

	public Vector mul(float a) {
		return new Vector(this.x * a, this.y * a, this.z * a);
	}

	public Vector addThis(Vector v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}

	public Vector mulThis(Vector v) {
		this.x *= v.x;
		this.y *= v.y;
		this.z *= v.z;
		return this;
	}

	public Vector mulThis(float a) {
		this.x *= a;
		this.y *= a;
		this.z *= a;
		return this;
	}

	public Vector sub(Vector v) {
		return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
	}

	public float dot(Vector v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}

	public Vector cross(Vector v) {
		return new Vector(
				this.y * v.z - this.z * v.y,
				this.z * v.x - this.x * v.z, 
				this.x * v.y - this.y * v.x);
	}

	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z
				* this.z);
	}

	public Vector norm() {
		float length = this.length();
		return this.mul(1.0f / length);
	}

	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y + " z: " + this.z;
	}
}

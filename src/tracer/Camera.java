package tracer;

import common.Ray;
import common.Utils;
import common.Vector;
import common.Xorshift;

public class Camera {
	private Vector eye;
	private Vector corner;
	private Vector toRight;
	private Vector toTop;

	public Camera(Vector eye, Vector lookAt) {
		this.eye = eye;
		Vector gaze = lookAt.sub(eye).norm();
		Vector up = new Vector(0.0f, 1.0f, 0.0f);
		Vector right = gaze.cross(up).norm();
		up = right.cross(gaze);
		up.mulThis(-1.0f);
		float planeWidth = (float) Math.tan(45.0f * (float) Math.PI / 360.0f);
		float planeHeight = planeWidth * Utils.RATIO;
		this.corner = eye.add(gaze).sub(right.mul(planeWidth))
				.sub(up.mul(planeHeight));
		this.toRight = right.mul(2.0f * planeWidth);
		this.toTop = up.mul(2.0f * planeHeight);
	}

	public Ray createRay(int x, int y, Xorshift rnd) {
		Vector point = this.corner
				.add(toRight.mul((float) (x + (rnd.getFloat() - 0.5f))
						* Utils.INVW)).add(
						toTop.mul((float) (y + (rnd.getFloat() - 0.5f))
								* Utils.INVH));
		return new Ray(this.eye, point.sub(eye).norm());
	}
}

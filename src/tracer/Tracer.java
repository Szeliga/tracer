package tracer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import materials.DiffuseSpecular;
import materials.Lambert;
import materials.Material;
import materials.Specular;

import primitives.Sphere;
import primitives.Primitive;

import common.Color;
import common.Hit;
import common.Ray;
import common.Utils;
import common.Vector;
import common.Xorshift;

public class Tracer {

	private static final long serialVersionUID = 2261202662349679112L;

	protected JFrame window;
	protected Color pixels[][];
	public BufferedImage img;
	public ArrayList<Primitive> prims = new ArrayList<Primitive>();
	public ArrayList<Primitive> lights = new ArrayList<Primitive>();
	protected Camera cam;
	private Xorshift rnd;
	private int frames = 0;
	private long start;

	public Tracer() {
		rnd = new Xorshift();
		pixels = new Color[Utils.WIDTH][Utils.HEIGHT];
		img = new BufferedImage(Utils.WIDTH, Utils.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < Utils.WIDTH; i++) {
			for (int j = 0; j < Utils.HEIGHT; j++) {
				pixels[i][j] = new Color(0.0f, 0.0f, 0.0f);
			}
		}
		if (window == null) {
			window = new JFrame("Tracer");
			window.getContentPane().setBackground(java.awt.Color.WHITE);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JLabel picLabel = new JLabel(new ImageIcon(img));
			window.getContentPane().add(picLabel);
			window.setBounds(100, 100, 1024, 768);
			window.setVisible(true);
		}
	}

	public void cornellBox() {
		Vector eye = new Vector(0.0f, 30.0f, 140.0f);
		Vector lookAt = new Vector(0.0f, 0.0f, 0.0f);
		cam = new Camera(eye, lookAt);
		Material green = new Lambert(new Color(), new Color(0.2f, 0.9f, 0.1f));
		Material blue = new Lambert(new Color(), new Color(0.1f, 0.1f, 0.9f));
		Material white = new Lambert(new Color(), new Color(0.8f, 0.8f, 0.8f));
		Material red = new Lambert(new Color(), new Color(0.9f, 0.1f, 0.1f));
		Material mirror = new DiffuseSpecular(new Color(), new Color(0.8f,
				0.8f, 0.8f), 10000);
		Material light = new Lambert(new Color(15.0f, 15.0f, 15.0f),
				new Color());
		prims.add(new Sphere(new Vector(0, 40, 12.0f), 10.5f, light));
		prims.add(new Sphere(new Vector(14, 7.0f, 50), 10.0f, blue));
		prims.add(new Sphere(new Vector(-27, 13.5f, 0), 17.5f, red));
		prims.add(new Sphere(new Vector(0, -504, 0), 500, mirror));
		// prims.add(new Sphere(new Vector(0, 504, 0), 500, white));
		// prims.add(new Sphere(new Vector(0, 0, -504), 500, green));
		// prims.add(new Sphere(new Vector(504, 0, 0), 500, green));
		// prims.add(new Sphere(new Vector(-504, 0, 0), 500, blue));
		for (Primitive shape : prims)
			if (shape.material.isLight())
				lights.add(shape);
	}

	public void cornellBox2() {
		Vector eye = new Vector(0.0f, 0.0f, 15.0f);
		Vector lookAt = new Vector(0.0f, 0.0f, 0.0f);
		cam = new Camera(eye, lookAt);
		Material green = new Lambert(new Color(), new Color(0.2f, 0.9f, 0.1f));
		Material blue = new Lambert(new Color(), new Color(0.1f, 0.1f, 0.9f));
		Material white = new Lambert(new Color(), new Color(0.8f, 0.8f, 0.8f));
		Material light = new Lambert(new Color(10, 10, 10), new Color(1, 1, 1));
		Material mirror = new Lambert(new Color(), new Color(0.8f, 0.8f, 0.8f));

		prims.add(new Sphere(new Vector(0, 3, 0), 1.0f, light));
		prims.add(new Sphere(new Vector(2, -2.5f, 1), 1.5f, white));
		prims.add(new Sphere(new Vector(-2, -2.5f, -1), 1.5f, mirror));
		prims.add(new Sphere(new Vector(0, -504, 0), 500, white));
		prims.add(new Sphere(new Vector(0, 504, 0), 500, white));
		prims.add(new Sphere(new Vector(0, 0, -504), 500, white));
		prims.add(new Sphere(new Vector(504, 0, 0), 500, blue));
		prims.add(new Sphere(new Vector(-504, 0, 0), 500, green));

		for (Primitive shape : prims)
			if (shape.material.isLight())
				lights.add(shape);
	}

	public synchronized void doIt() {
		this.cornellBox2();
		start = System.currentTimeMillis();
		for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			new Engine(new Xorshift(i)).start();
		}
	}

	public synchronized void setImg() {
		for (int y = 0; y < Utils.HEIGHT; y++) {
			for (int x = 0; x < Utils.WIDTH; x++) {
				img.setRGB(x, y, pixels[x][y].mul(1.0f / (float) frames)
						.toInt());
			}
		}
	}

	public synchronized void writeImg(String imageName) {
		try {
			ImageIO.write(img, "png", new File(imageName));
		} catch (IOException ex) {

		}
	}

	private class Engine extends Thread {

		private static final long serialVersionUID = -4074256538216488001L;

		Xorshift rnd;

		public Engine(Xorshift rnd) {
			this.rnd = rnd;
		}

		@Override
		public void run() {
			while (true) {
				frames++;
				for (int y = 0; y < Utils.HEIGHT; y++) {
					for (int x = 0; x < Utils.WIDTH; x++) {
						Ray ray = cam.createRay(x, y, rnd);
						pixels[x][y].addThis(trace(ray, true));
					}
				}
				if (frames % 10 == 0) {
					setImg();
					getFrames();
					window.getContentPane().repaint();
				}
			}
		}

		public Color trace(Ray ray, boolean cel) {
			if (rnd.getFloat() > 0.8) {
				return new Color();
			} else {
				Hit record = new Hit();
				record.ray = ray;
				Iterator<Primitive> i = prims.iterator();
				while (i.hasNext()) {
					Primitive current = i.next();
					current.intersect(ray, record);
				}
				if (!record.isHit)
					return new Color();
				Color ret = new Color();
				boolean spec = false;
				Material m = record.object.material;
				Vector dir = m.newDirection(rnd, record, m.getS(), spec);
				Ray newRay = new Ray(record.iP.add(dir.mul(0.001f)), dir);
				ret.addThis(m.reflectance.mul(directLighting(record, cel)));
				if (cel) {
					ret.addThis(m.emittance);
				}
				ret.addThis(m.reflectance.mul(trace(newRay, false)));
				return ret;
			}
		}
	}

	private Color directLighting(Hit record, boolean cel) {
		Vector onLight = lights.get(0).generatePoint(rnd.getFloat(),
				rnd.getFloat());
		Vector toLight = onLight.sub(record.iP);
		float dist2 = toLight.dot(toLight);
		float dist1 = (float) Math.sqrt(dist2);
		toLight.mulThis(1.0f / dist1);

		Ray shadowRay = new Ray(record.iP.add(toLight.mul(0.001f)), toLight);
		Hit shadow = new Hit();
		shadow.t = dist1 - 0.01f;

		boolean visible = true;
		Iterator<Primitive> i = prims.iterator();
		while (i.hasNext()) {
			i.next().intersect(shadowRay, shadow);
			if (shadow.isHit) {
				visible = false;
				break;
			}
		}

		if (visible) {
			shadow.iP = onLight;
			float cos1 = toLight.dot(record.normal);
			float cos2 = -toLight.dot(lights.get(0).getNormal(shadow));
			Sphere light = (Sphere) lights.get(0);
			if (cos1 > 0.0f && cos2 > 0.0f) {
				Color emi;
				float area = (float) (4.0f * Math.PI * light.r * light.r);
				emi = lights.get(0).material.emittance
						.mul(((cos1 * cos2 * area) / ((float) Math.PI * dist2)));
				cel = false;
				return emi;
			}
		}
		return new Color();
	}

	public void getFrames() {
		float s = (System.currentTimeMillis() - start) / 1000.0f;
		System.out.println(Utils.WIDTH * Utils.HEIGHT * frames / 1000.0f / s);
	}
}

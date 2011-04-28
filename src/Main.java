import tracer.Tracer;

public class Main {

	private static final long serialVersionUID = 8700288713553983856L;

	/**
	 * @param args
	 */
	public static synchronized void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Tracer tracer = new Tracer();
				tracer.doIt();
			}
		});
	}
}

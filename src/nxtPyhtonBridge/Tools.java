package nxtPyhtonBridge;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// Feststehende GUI Elemente
public class Tools {

	// Informationen Abfragen
	public static String displayInput(String question, String title) {
		while (true) {
			String s = JOptionPane.showInputDialog(null, question, title,
					JOptionPane.PLAIN_MESSAGE);
			if (s == null) {
				System.exit(0);
			}
			if (s.length() > 0) {
				return s;
			}
		}
	}

	// Informationen ausgeben
	public static void displayOutput(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.PLAIN_MESSAGE);
	}

	// Drehe ein Bild um x Grad
	public static BufferedImage rotateImage(BufferedImage src, double degrees) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance(
				Math.toRadians(degrees), src.getWidth() / 2,
				src.getHeight() / 2);
		BufferedImage rotatedImage = new BufferedImage(src.getWidth(),
				src.getHeight(), src.getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(src, 0, 0, null);
		return rotatedImage;
	}

	public static int[] convertToInt(String input) {
		int[] output = new int[2];

		try {
			output[1] = Integer.parseInt(input);
			output[0] = 1;
			return output;
		} catch (NumberFormatException ex) {
			output[0] = 0;
			return output;
		}
	}

	public static double[] convertToDouble(String input) {
		double[] output = new double[2];

		try {
			output[1] = Double.parseDouble(input);
			output[0] = 1;
			return output;
		} catch (NumberFormatException ex) {
			output[0] = 0;
			return output;
		}
	}

	public static int[] convertToInt(String[] input) {
		int[] output = new int[input.length];

		try {
			for (int i = 0; i < input.length; i++) {
				output[i] = Integer.parseInt(input[i]);
			}
		} catch (NumberFormatException ex) {
			return new int[0];
		}

		return output;
	}

	public static double[] convertToDoubles(String[] input) {
		double[] output = new double[input.length];

		try {
			for (int i = 0; i < input.length; i++) {
				output[i] = Double.parseDouble(input[i]);
			}
		} catch (NumberFormatException ex) {
			return new double[0];
		}

		return output;
	}

	public static ArrayList<Double> getDoubleConfig(String path) {
		System.out.println("main: try to get: " + path);
		ArrayList<Double> out = new ArrayList<Double>();
		BufferedReader in;
		String zeile;
		try {
			in = new BufferedReader(new FileReader(path));

			while ((zeile = in.readLine()) != null) {
				try {
					out.add(Double.parseDouble(zeile));
				} catch (NumberFormatException ex) {
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("main: fail");
			return out;
		}
		System.out.println("main: success");

		return out;
	}

	public static int[] getOptiScaleForField(int size_x, int size_y, int max_x,
			int max_y, double dif_xy) {
		int one_x = max_x / size_x;
		int one_y = (int) (one_x / dif_xy);

		int two_y = max_y / size_y;
		int two_x = (int) (two_y * dif_xy);

		int[] out = new int[2];

		if (one_y * size_y < max_y) {
			out[0] = one_x;
			out[1] = one_y;
		} else {
			out[0] = two_x;
			out[1] = two_y;
		}
		return out;
	}

	public static BufferedImage scaleImage(BufferedImage image, int new_x,
			int new_y) {
		BufferedImage out = new BufferedImage(new_x, new_y, image.getType());
		Graphics2D g = out.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, new_x, new_y, 0, 0, image.getWidth(),
				image.getHeight(), null);
		g.dispose();
		return out;
	}

}

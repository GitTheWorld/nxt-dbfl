package nxtPyhtonBridge;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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
			output[1]=Integer.parseInt(input);
			output[0]=1;
			return output;
		} catch (NumberFormatException ex) {
			output[0]=0;
			return output;
		}
	}
	
	public static double[] convertToDouble(String input) {
		double[] output = new double[2];

		try {
			output[1]=Double.parseDouble(input);
			output[0]=1;
			return output;
		} catch (NumberFormatException ex) {
			output[0]=0;
			return output;
		}
	}
	
	public static int[] convertToInt(String[] input) {
	int[] output = new int[input.length];
	
	try {
	for(int i=0;i<input.length;i++)
	{
		output[i]=Integer.parseInt(input[i]);
	}
	} catch (NumberFormatException ex) {
		return new int[0];
	}
	
	return output;
	}
	
	public static double[] convertToDoubles(String[] input) {
	double[] output = new double[input.length];
	
	try {
	for(int i=0;i<input.length;i++)
	{
		output[i]=Double.parseDouble(input[i]);
	}
	} catch (NumberFormatException ex) {
		return new double[0];
	}
	
	return output;
	}
	
	

}

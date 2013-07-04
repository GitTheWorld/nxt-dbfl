package nxtPyhtonBridge;

import javax.swing.JOptionPane;

// Feststehende GUI Elemente
public class GuiUtils {

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

}

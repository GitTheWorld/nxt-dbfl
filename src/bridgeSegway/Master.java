package bridgeSegway;

import nxtPyhtonBridge.GuiUtils;
import nxtPyhtonBridge.PythonBridge;

public class Master {
	public static String pathtoserver;
	public static Control brick;

	public static void main(String[] args) {
		if (args.length == 0) {
			return;
		}
		PythonBridge.path = args[0];

		String[] error = new String[1];
		error[0] = "fatal";

		try {
			brick = new Control(GuiUtils.displayInput(
					"Wie ist der Name des Segways?", "Segway Name"), error);
			new Gui();

		} catch (Exception e) {
			System.out.println("main: something is wrong!!!!");
			e.printStackTrace();
			GuiUtils.displayOutput(
					"Etwas stimmt nicht, Programm muss beendet werden!",
					"Error");
			System.exit(1);
		}

	}
}

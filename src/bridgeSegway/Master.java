package bridgeSegway;

import nxtPyhtonBridge.Tools;
import nxtPyhtonBridge.Brick;

public class Master {
	public static String pathtoserver;
	public static Control brick;

	public static void main(String[] args) {
		if (args.length == 0) {
			return;
		}
		Brick.path = args[0];

		Control.error = new String[1];
		Control.error[0] = "fatal";

		try {
			brick = new Control(Tools.displayInput(
					"Wie ist der Name des Segways?", "Segway Name"));
			new Gui();

		} catch (Exception e) {
			System.out.println("main: something is wrong!!!!");
			e.printStackTrace();
			Tools.displayOutput(
					"Etwas stimmt nicht, Programm muss beendet werden!",
					"Error");
			System.exit(1);
		}

	}
}

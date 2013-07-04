package bridgeFieldControl;

import java.util.ArrayList;

import nxtPyhtonBridge.GuiUtils;
import nxtPyhtonBridge.PythonBridge;

public class Master {

	public static String pathtoserver;
	public static ArrayList<OneBrick> bricks;

	public static void main(String[] args) {
		if (args.length == 0) {
			return;
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("main: ends");
			}
		}));

		PythonBridge.path = args[0];

		if (args.length == 2) {
			GuiInit.askMe(args[1], null);
		} else if (args.length == 3) {
			GuiInit.askMe(args[1], args[2]);
		} else {
			GuiInit.askMe(null, null);
		}

		try {
			for (int i = 0; i < Config.other; i++) {
				bricks.get(i).startup();
			}

			Gui.init();

			while (true) {
				for (int i = 0; i < bricks.size(); i++) {
					if (Gui.finish) {
						Stop.stopMore();
					}
					if (Gui.pause) {
						while (Gui.pause) {
							Thread.sleep(200);
						}
					}
					bricks.get(i).action();
				}
			}

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

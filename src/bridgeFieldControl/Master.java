package bridgeFieldControl;

import nxtPyhtonBridge.Tools;
import nxtPyhtonBridge.Brick;

public class Master {

	public static String pathtoserver;
	
	public static void main(String[] args) {
		if (args.length == 0) {
			return;
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("main: ends");
			}
		}));

		Brick.path = args[0];

		try {
		
		if (args.length == 2) {
			GuiInit.askMe(args[1], null);
		} else if (args.length == 3) {
			GuiInit.askMe(args[1], args[2]);
		} else {
			GuiInit.askMe(null, null);
		}

			for (int i = 0; i < BrickGame.bricks.size(); i++) {
				BrickGame.bricks.get(i).startup();
			}

			Gui.init();

			while (true) {
				
				if (Status.stop) {Status.stop();}
				
				for (int i = 0; i < BrickGame.bricks.size(); i++) {
				
					if(!Status.pause){
					BrickGame.bricks.get(i).action();
					}
					
					if (Status.stop) {Status.stop();}
					
				}
				
				Thread.sleep(500);

			}

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

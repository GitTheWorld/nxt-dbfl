package bridgeFieldControl;

import java.io.FileNotFoundException;

public class Status {

	// Status
	public static boolean win = false;
	public static boolean stop = false;
	public static boolean pause = true;

	// What is the win condition?
	public static int ifWin = 0;

	public static void stop_init() {
		System.out.println("main: stop soon");
		Gui.stopButton.setEnabled(false);
		stop = true;
	}
	
	public static void resetAll()
	{
	win=false;
	stop=true;
	pause=true;
	Gui.stopButton.setEnabled(true);
	Gui.stopButton.setText("Fortsetzen");
	}

	public static boolean is_win() throws FileNotFoundException {
		if (ifWin == 0) {
			return false;
		} else if (win) {
			return true;
		}

		if ((FieldGame.unknown == 0 && ifWin == -1)
				|| (FieldGame.objects == ifWin)) {
			System.out.println("main: game is finish");
			Gui.writeStatus();
			win = true;
			return true;
		}
		return false;
	}

	public static void stop() {
		try {
			System.out.println("main: stop the program");

			// eventually write the statistics
			if (!win) {
				Gui.writeStatus();
			}
			System.out.println("main: statistics written");

			// Wait for the images to complete
			while (Gui.pro.size() != 0) {
				Gui.pro.get(0).join();
				Gui.pro.remove(0);
			}

			System.out.println("main: all images written");

			// Disconect the bricks
			while (BrickGame.bricks.size() != 0) {
				System.out.println(BrickGame.bricks.get(0).name
						+ ": disconnect");
				BrickGame.bricks.get(0).shutdown();
				BrickGame.bricks.remove(0);
			}

			System.out.println("main: all bricks disconnected, end...");

			// Close it
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
}

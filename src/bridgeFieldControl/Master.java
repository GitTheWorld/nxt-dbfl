package bridgeFieldControl;

import java.util.ArrayList;

import nxtPyhtonBridge.Tools;

public class Master {

	public static boolean needRestart=false;
	
	public static void main(String[] args) throws InterruptedException {
		if (args.length == 0) {
			return;
		}
		BrickGame.path = args[0];

		try {
			setConfig();
			GuiInit.askMe(false);
			Gui.getScale();

			for (int i = 0; i < BrickGame.bricks.size(); i++) {
				BrickGame.bricks.get(i).startup();
			}

			Gui.init();

			while (true) {

				if (Status.stop) {
					Status.stop();
				}
				if(needRestart){reInit();}


				for (int i = 0; i < BrickGame.bricks.size(); i++) {

					if (!Status.pause) {
						BrickGame.bricks.get(i).action();
					}

					if (Status.stop) {
						Status.stop();
					}
					if(needRestart){reInit();}

					
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
	
	public static void reInit() throws Exception
	{
		GuiInit.askMe(true);
		Status.resetAll();
		Gui.update();
		needRestart=false;
	}

	public static void setConfig() throws InterruptedException {
		ArrayList<Double> config = Tools.getDoubleConfig(BrickGame.path
				+ "/config.txt");
		if (config.size() != 15) {
			System.out.println("main: config is wrong!!!!");
			System.exit(1);
		}

		Gui.dif_xy = config.get(0) / config.get(1);
		FieldGame.one_x = config.get(2);
		FieldGame.one_y = config.get(3);
		Gui.max_x = config.get(4).intValue();
		Gui.max_y = config.get(5).intValue();

		BrickGame.d_move_power = config.get(6).intValue();
		BrickGame.d_rotate_power = config.get(7).intValue();
		BrickGame.d_gap = config.get(8).intValue();
		BrickGame.d_maxdegree = config.get(9).intValue();
		BrickGame.d_forward = config.get(10).intValue();
		BrickGame.d_distance_from_me = config.get(11);
		BrickGame.d_distance_from_enemy = config.get(12);
		BrickGame.d_distance_to_other_base = config.get(13);
		BrickGame.d_distance_to_other_multi = config.get(14);
	}

}

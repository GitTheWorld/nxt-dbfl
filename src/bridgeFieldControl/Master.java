package bridgeFieldControl;

import java.util.ArrayList;

import nxtPyhtonBridge.Tools;

public class Master {

	public static String pathtoserver;
	
	public static void main(String[] args) throws InterruptedException {
		if (args.length == 0) {
			return;
		}

		BrickGame.path = args[0];

		setConfig();
		
		try {
		if (args.length == 2) {
			GuiInit.askMe(args[1], null);
		} else if (args.length == 3) {
			GuiInit.askMe(args[1], args[2]);
		} else {
			GuiInit.askMe(null, null);
		}

		Gui.getScale();
		
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
	
	public static void setConfig() throws InterruptedException
	{
		ArrayList<Double> config = Tools.getDoubleConfig(BrickGame.path+"/config.txt");
		if(config.size()!=13){
			System.out.println("main: config is wrong!!!!");
			System.exit(1);
					}
		Gui.xy_dif=config.get(0)/config.get(1);
		
		FieldGame.one_x=config.get(2);
		FieldGame.one_y=config.get(3);
		Gui.x_max=config.get(4).intValue();
		Gui.y_max=config.get(5).intValue();
		
		BrickGame.d_move_power=config.get(6).intValue();
		BrickGame.d_rotate_power=config.get(7).intValue();
		
		BrickGame.d_start_points=config.get(8);
		BrickGame.d_distance_from_me=config.get(9);
		BrickGame.d_distance_from_enemy=config.get(10);
		BrickGame.d_distance_to_other_base=config.get(11);
		BrickGame.d_distance_to_other_multi=config.get(12);
	}

}

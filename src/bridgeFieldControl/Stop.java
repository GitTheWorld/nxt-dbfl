package bridgeFieldControl;

public class Stop {

	public static void stopMore() {
		try {
			System.out.println("main: start to stop");

			Writer.write();

			while (Gui.pro.size() != 0) {
				Gui.pro.get(0).join();
				Gui.pro.remove(0);
			}

			while (Master.bricks.size() != 0) {
				System.out.println(Master.bricks.get(0).name + ": disconnect");
				Master.bricks.get(0).shutdown();
				Master.bricks.remove(0);
			}
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
}
